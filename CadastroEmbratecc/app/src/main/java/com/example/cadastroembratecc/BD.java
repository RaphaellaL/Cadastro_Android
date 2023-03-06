package com.example.cadastroembratecc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BD extends SQLiteOpenHelper {

    private static final int VERSAO=1; //versão do banco
    private static  final String BANCO_CLIENTE = "bd_clientes"; //nome do banco

    //tabela
    private static final String TABELA_CLIENTE ="tb_clientes";
    private static final String COLUNA_COD ="cod";
    private static final String COLUNA_NOME ="nome";
    private static final String COLUNA_CPF ="cpf";
    private static final String COLUNA_PHONE ="phone";
    private static final String COLUNA_EMAIL ="email";


    public BD(@Nullable Context context) {
        super(context, BANCO_CLIENTE,null , VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String QUERY_COLUNA= "CREATE TABLE " + TABELA_CLIENTE +
                "(" + COLUNA_COD + " INTEGER PRIMARY KEY, " + COLUNA_NOME + " TEXT," +
                COLUNA_CPF + " INTEGER," + COLUNA_PHONE + " INTEGER," + COLUNA_EMAIL  + " TEXT)";

        //execução
        sqLiteDatabase.execSQL(QUERY_COLUNA);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //crud

    void inserirCliente(Cliente cliente) {

        SQLiteDatabase db= this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUNA_NOME,cliente.getNome());
        values.put(COLUNA_CPF,cliente.getCpf());
        values.put(COLUNA_PHONE,cliente.getPhone());
        values.put(COLUNA_EMAIL,cliente.getEmail());

        db.insert(TABELA_CLIENTE,null,values);
        db.close();

    }

    void excluir(Cliente cliente){

        SQLiteDatabase db= this.getWritableDatabase();

        db.delete(TABELA_CLIENTE, COLUNA_COD + "= ?",new String[] {String.valueOf(cliente.getCod())});

    }

    Cliente selecionar (int cod){
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor = db.query(TABELA_CLIENTE, new String[]{COLUNA_COD,COLUNA_NOME,COLUNA_CPF,COLUNA_PHONE,COLUNA_EMAIL}, COLUNA_COD + "= ?",
                new String[] {String.valueOf(cod)},null,null,null,null);

        if(cursor !=null) {
            cursor.moveToFirst();
        }

        Cliente cliente =new Cliente(
                nome, cpf, phone, email);

        return cliente;
    }

    void atualizar (Cliente cliente) {
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUNA_NOME,cliente.getNome());
        values.put(COLUNA_CPF,cliente.getCpf());
        values.put(COLUNA_PHONE,cliente.getPhone());
        values.put(COLUNA_EMAIL,cliente.getEmail());

        db.update(TABELA_CLIENTE,values,COLUNA_COD + "= ?",
                new String[]{String.valueOf(cliente.getCod()) });

    }

    public List<Cliente> listadeClientes(){
        List<Cliente> listaClientes =new ArrayList<Cliente>();
        String query = "SELECT * FROM " + TABELA_CLIENTE;

        SQLiteDatabase db=this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()){
           do{
                Cliente cliente= new Cliente(nome, cpf, phone, email);
                cliente.setCod(Integer.parseInt(c.getString(0)));
                cliente.setNome(c.getString(1));
                cliente.setCpf(Integer.parseInt(c.getString(2)));
                cliente.setPhone(c.getString(3));
                cliente.setEmail(c.getString(4));
               listaClientes.add(cliente);
           }while (c.moveToNext());
        }
        return listaClientes;
    }


}
