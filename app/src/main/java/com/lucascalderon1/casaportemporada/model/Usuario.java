package com.lucascalderon1.casaportemporada.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.lucascalderon1.casaportemporada.helper.FirebaseHelper;

public class Usuario {

    private String id;
    private String nome;
    private String emal;
    private String telefone;
    private String senha;





    public void salvar() {
        DatabaseReference reference = FirebaseHelper.getDatabaseReference()
                .child("usuarios")
                .child(this.getId());
        reference.setValue(this);

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmal() {
        return emal;
    }

    public void setEmal(String emal) {
        this.emal = emal;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }


    @Exclude
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
}
