package com.example.cadastroembratecc;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    EditText editNome,editCpf,editTelefone,editEmail,editId;
    Button btnNovo,btnSalvar,btnApagar;
    ListView listViewClientes;

    private String HOST="http://192.168.0.104/Embratecc";

    private int itemClicado;

    CadastroAdapter cadastroAdapter;
    List<Cadastro> lista;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            getSupportActionBar().hide();
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        editNome=(EditText)findViewById(R.id.editNome);
        editCpf=(EditText)findViewById(R.id.editCpf);
        editTelefone=(EditText)findViewById(R.id.editTelefone);
        editEmail=(EditText)findViewById(R.id.editEmail);
        editId=(EditText)findViewById(R.id.editId);

        btnNovo=(Button)findViewById(R.id.btnNovo);
        btnSalvar=(Button)findViewById(R.id.btnSalvar);
        btnApagar=(Button)findViewById(R.id.btnApagar);

       /* btnNovo.setBackgroundResource(R.color.red);
        btnSalvar.setBackgroundResource(R.color.red);
        btnApagar.setBackgroundResource(R.color.red);*/


        listViewClientes=(ListView)findViewById(R.id.listViewClientes);

              /*lista*/
        lista=new ArrayList<Cadastro>();
        cadastroAdapter= new CadastroAdapter(MainActivity.this,lista);
        listViewClientes.setAdapter(cadastroAdapter);

        listaCadastro();

        btnNovo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                limpaDados();
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String id= editId.getText().toString();
                final String nome=editNome.getText().toString();
                final String cpf=editCpf.getText().toString();
                final String telefone=editTelefone.getText().toString();
                final String email=editEmail.getText().toString();


                if(nome.isEmpty()){
                    editNome.setError("Digite o Nome por favor");
                } else if (id.isEmpty()) { //se o id estiver vazio ,inserir
                    String url=HOST + "/create.php";
                    Ion.with(MainActivity.this)
                            .load(url)
                            .setBodyParameter("nome" , nome )
                            .setBodyParameter("cpf" , cpf )
                            .setBodyParameter("telefone" , telefone )
                            .setBodyParameter("email" , email )
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    int idRetornado=Integer.parseInt(result.get("ID").getAsString());
                                    Cadastro c= new Cadastro();
                                    c.setId(idRetornado);
                                    c.setNome(nome);
                                    c.setCpf(cpf);
                                    c.setTelefone(telefone);
                                    c.setEmail(email);

                                    lista.add(c); //inserir na lista
                                    cadastroAdapter.notifyDataSetChanged(); //atualizar a lista ao inserir um novo cadastro


                                    if(result.get("CREATE").getAsString().equals("OK")) {

                                        limpaDados();
                                        Toast.makeText(MainActivity.this," Cadastro Salvo",Toast.LENGTH_LONG).show();

                                    }else{
                                        Toast.makeText(MainActivity.this,"Erro no Cadastro ",Toast.LENGTH_LONG).show();
                                    }

                                }


                            });

                }else{
                    //atualizar

                    String url=HOST + "/update.php";
                    Ion.with(MainActivity.this)
                            .load(url)
                            .setBodyParameter("id" , id )
                            .setBodyParameter("nome" , nome )
                            .setBodyParameter("cpf" , cpf )
                            .setBodyParameter("telefone" , telefone )
                            .setBodyParameter("email" , email )
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                        if(result.get("UPDATE").getAsString().equals("OK")) {

                                            Cadastro c= new Cadastro();
                                            c.setId(Integer.parseInt(id));
                                            c.setNome(nome);
                                            c.setCpf(cpf);
                                            c.setTelefone(telefone);
                                            c.setEmail(email);


                                             lista.set(itemClicado, c);
                                            cadastroAdapter.notifyDataSetChanged(); //atualizar a lista ao inserir um novo cadastro



                                            limpaDados();
                                            Toast.makeText(MainActivity.this," atualização Salva",Toast.LENGTH_LONG).show();

                                        }else{
                                        Toast.makeText(MainActivity.this,"Erro na atualização ",Toast.LENGTH_LONG).show();
                                        }

                                }


                            });
                }
            }


        });


        listViewClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Cadastro c= (Cadastro) adapterView.getAdapter().getItem(position);

                editId.setText(String.valueOf(c.getId()));
                editNome.setText(c.getNome());
                editCpf.setText(c.getCpf());
                editTelefone.setText(c.getTelefone());
                editEmail.setText(c.getEmail());

                itemClicado=position;
            }
        });

        btnApagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = editId.getText().toString();
                if(id.isEmpty()){
                    Toast.makeText(MainActivity.this,"Nenhum Cadastro selecionado ",Toast.LENGTH_LONG).show();
                }else{
                    String url=HOST + "/delete.php";
                    Ion.with(MainActivity.this)
                            .load(url)
                            .setBodyParameter("id" , id )
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    if (result.get("DELETE").getAsString().equals("OK")) {
                                        lista.remove(itemClicado);
                                        cadastroAdapter.notifyDataSetChanged(); //atualizar a lista ao inserir um novo cadastro

                                        limpaDados();
                                        Toast.makeText(MainActivity.this, " Excluido com sucesso", Toast.LENGTH_LONG).show();
                                    }else {
                                        Toast.makeText(MainActivity.this, "Erro ao Excluir ", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                }
            }
        });

    }

    public void limpaDados(){
        editId.setText("");
        editNome.setText("");
        editCpf.setText("");
        editTelefone.setText("");
        editEmail.setText("");

        editNome.requestFocus();

    }

    private void listaCadastro() {
        String url=HOST+ "/read.php";

        Ion.with(getBaseContext())
                .load(url)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        for(int i=0; i<result.size(); i++){
                            JsonObject obj =result.get(i).getAsJsonObject();
                            Cadastro c= new Cadastro();

                            c.setId(obj.get("id").getAsInt());
                            c.setNome(obj.get("nome").getAsString());
                            c.setCpf(obj.get("cpf").getAsString());
                            c.setTelefone(obj.get("telefone").getAsString());
                            c.setEmail(obj.get("email").getAsString());

                            lista.add(c);
                        }
                        cadastroAdapter.notifyDataSetChanged();
                    }
                });
    }
}