package com.mycompany.autostockcar.modelo.dominio;


public class Fabricantes {
    private int idFabricante;
    private String nomeFabricante;
    private String emailFabricante;
    private String telefoneFabricante;
    private String enderecoFabricante;
    private String cnpjFabricante;
    private String obsFabricante;
    private int idCidade;

    public Fabricantes() {
    }

    public Fabricantes(int idFabricante, String nomeFabricante, String emailFabricante, String telefoneFabricante, String enderecoFabricante, String cnpjFabricante, String obsFabricante, int idCidade) {
        this.idFabricante = idFabricante;
        this.nomeFabricante = nomeFabricante;
        this.emailFabricante = emailFabricante;
        this.telefoneFabricante = telefoneFabricante;
        this.enderecoFabricante = enderecoFabricante;
        this.cnpjFabricante = cnpjFabricante;
        this.obsFabricante = obsFabricante;
        this.idCidade = idCidade;
    }


    
    public int getIdFabricante() {
        return idFabricante;
    }

    public void setIdFabricante(int idFabricante) {
        this.idFabricante = idFabricante;
    }

    public String getNomeFabricante() {
        return nomeFabricante;
    }

    public void setNomeFabricante(String nomeFabricante) {
        this.nomeFabricante = nomeFabricante;
    }

    public String getEmailFabricante() {
        return emailFabricante;
    }

    public void setEmailFabricante(String emailFabricante) {
        this.emailFabricante = emailFabricante;
    }

    public String getTelefoneFabricante() {
        return telefoneFabricante;
    }

    public void setTelefoneFabricante(String telefoneFabricante) {
        this.telefoneFabricante = telefoneFabricante;
    }

    public String getEnderecoFabricante() {
        return enderecoFabricante;
    }

    public void setEnderecoFabricante(String enderecoFabricante) {
        this.enderecoFabricante = enderecoFabricante;
    }

    public String getCnpjFabricante() {
        return cnpjFabricante;
    }

    public void setCnpjFabricante(String cnpjFabricante) {
        this.cnpjFabricante = cnpjFabricante;
    }

    public String getObsFabricante() {
        return obsFabricante;
    }

    public void setObsFabricante(String obsFabricante) {
        this.obsFabricante = obsFabricante;
    }

    public int getIdCidade() {
        return idCidade;
    }

    public void setIdCidade(int idCidade) {
        this.idCidade = idCidade;
    }

    
    
    
    
}
