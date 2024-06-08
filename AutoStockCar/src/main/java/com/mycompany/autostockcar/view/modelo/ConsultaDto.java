package com.mycompany.autostockcar.view.modelo;

import java.math.BigDecimal;

public class ConsultaDto {
    private int idProduto;  
    private String nomeProduto;
    private String codigoFabrica;
    private String obsProduto;
    private String gaveta; 
    private String prateleira;
    private BigDecimal valorCustoProduto;
    private BigDecimal valorFinal; 

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getCodigoFabrica() {
        return codigoFabrica;
    }

    public void setCodigoFabrica(String codigoFabrica) {
        this.codigoFabrica = codigoFabrica;
    }

    public String getObsProduto() {
        return obsProduto;
    }

    public void setObsProduto(String obsProduto) {
        this.obsProduto = obsProduto;
    }

    public String getGaveta() {
        return gaveta;
    }

    public void setGaveta(String gaveta) {
        this.gaveta = gaveta;
    }

    public String getPrateleira() {
        return prateleira;
    }

    public void setPrateleira(String prateleira) {
        this.prateleira = prateleira;
    }

    public BigDecimal getValorCustoProduto() {
        return valorCustoProduto;
    }

    public void setValorCustoProduto(BigDecimal valorCustoProduto) {
        this.valorCustoProduto = valorCustoProduto;
    }

    public BigDecimal getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(BigDecimal valorFinal) {
        this.valorFinal = valorFinal;
    }

    
}
