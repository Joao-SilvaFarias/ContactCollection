package com.example.contactcollection;

public class Contato {
    String nome, email, tvPerfil, telefone, id, favorito;

    public Contato(String nome, String email, char c, String telefone, String id){
    }

    public Contato(String nome, String email, String tvPerfil, String telefone, String id, String favorito) {
        this.nome = nome;
        this.email = email;
        this.tvPerfil = tvPerfil;
        this.telefone = telefone;
        this.id = id;
        this.favorito = favorito;
    }

    public String getFavorito() {
        return favorito;
    }

    public void setFavorito(String favorito) {
        this.favorito = favorito;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTvPerfil() {
        return tvPerfil;
    }

    public void setTvPerfil(String imgPerfil) {
        this.tvPerfil = imgPerfil;
    }
}
