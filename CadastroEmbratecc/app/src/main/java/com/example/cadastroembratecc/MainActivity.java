package com.example.cadastroembratecc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText editCod,editNome,editCpf,editPhone,editEmail;
    Button btnLimpar,btnSalvar,btnExcluir;
    ListView listViewClientes;
    BD db=  new BD(this);


    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editCod=(EditText)findViewById(R.id.editCod);
        editNome=(EditText)findViewById(R.id.editNome);
        editCpf=(EditText)findViewById(R.id.editCpf);
        editPhone=(EditText)findViewById(R.id.editPhone);
        editEmail=(EditText)findViewById(R.id.editEmail);

        btnLimpar=(Button)findViewById(R.id.btnLimpar);
        btnSalvar=(Button)findViewById(R.id.btnSalvar);
        btnExcluir=(Button)findViewById(R.id.btnExcluir);

        listViewClientes=(ListView)findViewById(R.id.listViewClientes);
        ListarClientes();

        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               limpaDados();
            }
        });
        listViewClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                String conteudo = (String) listViewClientes.getItemAtPosition(position);
                //Toast.makeText(MainActivity.this, "Selecionado ", Toast.LENGTH_SHORT).show();
                String codigo= conteudo.substring(0, conteudo.indexOf("-"));

                Cliente cliente =db.selecionar(Integer.parseInt(codigo));
                editCod.setText("" + cliente.getCod());
                editNome.setText(cliente.getNome());
                editCpf.setText(cliente.getCpf());
                editPhone.setText(cliente.getPhone());
                editEmail.setText(cliente.getEmail());
            }
        });


        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cod=editCod.getText().toString();
                String nome=editNome.getText().toString();
                String cpf=editCpf.getText().toString();
                String phone=editPhone.getText().toString();
                String email=editEmail.getText().toString();

                if(nome.isEmpty()) {
                    editNome.setError("Campo Obrigatório");
                }else if(cod.isEmpty()){
                    //insert
                    db.inserirCliente(new Cliente( nome,cpf,phone,email));
                    Toast.makeText(MainActivity.this, "Salvo  com Sucesso",Toast.LENGTH_LONG).show();
                    limpaDados();
                    ListarClientes();
                }

                else {
                    //update
                    db.atualizar(new Cliente(Integer.parseInt(cod),nome,Integer.parseInt(cpf),phone,email));
                    Toast.makeText(MainActivity.this, "Atualizado   com Sucesso",Toast.LENGTH_LONG).show();
                    limpaDados();
                    ListarClientes();

                }
            }
        });

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cod=editCod.getText().toString();

                if(cod.isEmpty()){
                    Toast.makeText(MainActivity.this, "Nenhum cleinete selecionado",Toast.LENGTH_LONG).show();
                }else{
                    Cliente cliente= new Cliente();
                    cliente.setCod(Integer.parseInt(cod));
                    db.excluir(cliente);
                    Toast.makeText(MainActivity.this, "Deletado    com Sucesso",Toast.LENGTH_LONG).show();
                    limpaDados();
                    ListarClientes();
                }
            }
        });

        /*teste crud*/

        /*db.inserirCliente(new Cliente( "Raphaella Lopes","14001480735","21998126656","raphaella_lopes@yahoo.com.br"));
        db.inserirCliente(new Cliente( "Luiz Felipe ","14001480735","21998126656","luizFelipe@yahoo.com.br"));
        db.inserirCliente(new Cliente( "Amanda ","14001480735","21998126656","amanda@yahoo.com.br"));
        db.inserirCliente(new Cliente( "Pedro  ","14001480735","21998126656","pedro@yahoo.com.br"));*/

        //Toast.makeText(MainActivity.this, "Salvo ",Toast.LENGTH_LONG).show();

        /*Cliente cliente= new Cliente();
        cliente.setCod(2);
        db.excluir(cliente);

       Toast.makeText(MainActivity.this, "Apagado ",Toast.LENGTH_LONG).show();*/


//        Cliente cliente= db.selecionar(3);
//
//
//        Log.d("Cliente selecionado", "Codigo: " +cliente.getCod() + "Nome:" +cliente.getNome() + "Cpf:" +cliente.getCpf());

//    Cliente cliente = new Cliente();
//    cliente.setCod(3);
//    cliente.setNome("Pedro da Silva");
//    cliente.setPhone("222222222");
//
//    db.atualizar(cliente);
//        Toast.makeText(MainActivity.this, "atualizado  ",Toast.LENGTH_LONG).show();
    }

    void limpaDados(){

        editCod.setText("");
        editNome.setText("");
        editCpf.setText("");
        editPhone.setText("");
        editEmail.setText("");

        editNome.requestFocus();


    }

    public void ListarClientes(){
        List<Cliente> clientes = db.listadeClientes();

        arrayList=new ArrayList<String>();
        adapter=new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1,arrayList);
        listViewClientes.setAdapter(adapter);


        for(Cliente c:clientes) {
            arrayList.add(c.getCod() + "-" + c.getNome());
            adapter.notifyDataSetChanged();
        }

    }
}