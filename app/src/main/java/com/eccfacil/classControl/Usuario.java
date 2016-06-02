package com.eccfacil.classControl;

/**
 * Created by skullveira on 11/05/2016.
 */
public class Usuario {

    private String idUsuario;
    private String email;
    private String nome;
    private String senha;
    private String tokenId;

    public Usuario() {}

    public Usuario(String Email ,String Nome,String Senha,String TokenId)
    {
        this.email = email;
        this.nome = nome;
        this.senha = senha;
        this.tokenId = tokenId;
    }

    public String getIdUsuario() {
        return this.idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }
}
