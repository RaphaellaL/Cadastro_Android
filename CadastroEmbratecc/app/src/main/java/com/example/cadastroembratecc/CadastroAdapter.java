package com.example.cadastroembratecc;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CadastroAdapter  extends BaseAdapter {

    private Context ctx;
    private List<Cadastro> lista;

    public CadastroAdapter(Context ctx2, List<Cadastro> lista2){
        ctx =ctx2;
        lista=lista2;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Cadastro getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View v=null;

        if(convertView ==null){
            LayoutInflater inflater=((Activity)ctx).getLayoutInflater();
            v=inflater.inflate(R.layout.item_lista,null);

        }else{
            v=convertView;
        }
        Cadastro c=getItem(position);

        TextView itemNome=(TextView)v.findViewById(R.id.itemNome);
        TextView itemCpf=(TextView)v.findViewById(R.id.itemCpf);
        TextView itemTelefone=(TextView)v.findViewById(R.id.itemTelefone);
        TextView itemEmail=(TextView)v.findViewById(R.id.itemEmail);

        itemNome.setText(c.getNome());
        itemCpf.setText(c.getCpf());
        itemTelefone.setText(c.getTelefone());
        itemEmail.setText(c.getEmail());



        return v;
    }
}
