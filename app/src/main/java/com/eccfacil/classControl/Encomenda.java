package com.eccfacil.classControl;

import java.util.Date;

/**
 * Created by skullveira on 11/05/2016.
 */
public class Encomenda {

    private String idEncomenda;
    private String codRastreio;
    private Date dataAtualizacao;
    private Date dataCadastro;
    private String nome;
    //private boolean houveMudanca;
    private String status;
    private String idUsuario;
    private String local;
    private String informacoes;
    private String mensagemErro;

    public String getIdEncomenda() {
        return this.idEncomenda;
    }

    public void setIdEncomenda(String idEncomenda) {
        this.idEncomenda = idEncomenda;
    }

    public String getCodRastreio() {
        return this.codRastreio;
    }

    public void setCodRastreio(String codRastreio) {
        this.codRastreio = codRastreio;
    }

    public Date getDataAtualizacao() {
        return this.dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public Date getDataCadastro() {
        return this.dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    //public boolean isHouveMudanca() {
    //    return houveMudanca;
    //}

    //public void setHouveMudanca(boolean houveMudanca) {
    //    this.houveMudanca = houveMudanca;
    //}

    public String getInformacoes() {
        return informacoes;
    }

    public void setInformacoes(String informacoes) {
        this.informacoes = informacoes;
    }

    public String getMensagemErro() {
        return mensagemErro;
    }

    public void setMensagemErro(String mensagemErro) {
        this.mensagemErro = mensagemErro;
    }
}
