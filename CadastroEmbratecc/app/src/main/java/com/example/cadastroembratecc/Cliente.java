package com.example.cadastroembratecc;

public class Cliente {

    int cod;
    String nome;
    int cpf;
    String phone;
    String email;

    public Cliente(String nome, String cpf, String phone, String email){

    }
// construtor update
    public Cliente(int _cod, String _nome , int _cpf, String _phone, String _email){
        this.cod= _cod;
        this.nome= _nome;
        this.cpf= _cpf;
        this.phone= _phone;
        this.email= _email;

    }
// construtor insert
    public Cliente( String _nome , int _cpf, String _phone, String _email){
        this.nome= _nome;
        this.cpf= _cpf;
        this.phone= _phone;
        this.email= _email;

    }

    public Cliente() {

    }

    //getter and setter
    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCpf() {
        return cpf;
    }

    public void setCpf(int cpf) {
        this.cpf = cpf;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
