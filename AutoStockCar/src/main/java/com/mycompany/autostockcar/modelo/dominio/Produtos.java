package com.mycompany.autostockcar.modelo.dominio;

import java.math.BigDecimal;


public class Produtos {
    private int idProduto;
    private String nomeProduto;
    private String codigoFabricante;
    private String obsProduto;
    private BigDecimal valorCustoProduto;
    private BigDecimal valorFinal;
    private String prateleira;
    private String gaveta;
    private int ImpostoDoProduto;
    private Fabricantes Idfabricante;
    private Fabricantes NomeFabricantes;
    private Estoques Idestoque;
    private Categorias Idcategoria;
    private Categorias NomeCategorias;
    private double QuantidadeDisponivel;

    public Produtos() {
    }

    public Produtos(int idProduto, String nomeProduto, String codigoFabricante, String obsProduto, BigDecimal valorCustoProduto, BigDecimal valorFinal, String prateleira, String gaveta, int ImpostoDoProduto, Fabricantes idfabricante, Fabricantes NomeFabricantes, Estoques idestoque, Categorias idcategoria, Categorias NomeCategorias) {
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.codigoFabricante = codigoFabricante;
        this.obsProduto = obsProduto;
        this.valorCustoProduto = valorCustoProduto;
        this.valorFinal = valorFinal;
        this.prateleira = prateleira;
        this.gaveta = gaveta;
        this.ImpostoDoProduto = ImpostoDoProduto;
        this.Idfabricante = idfabricante;
        this.NomeFabricantes = NomeFabricantes;
        this.Idestoque = idestoque;
        this.Idcategoria = idcategoria;
        this.NomeCategorias = NomeCategorias;
    }
    
    public Produtos(int idProduto, String nomeProduto, int QuantidadeDisponivel, BigDecimal valorFinal, int ImpostoDoProduto) {
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.QuantidadeDisponivel = QuantidadeDisponivel;
        this.valorFinal = valorFinal;
        this.ImpostoDoProduto = ImpostoDoProduto;
    }
    
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

    public String getCodigoFabricante() {
        return codigoFabricante;
    }

    public void setCodigoFabricante(String codigoFabricante) {
        this.codigoFabricante = codigoFabricante;
    }

    public String getObsProduto() {
        return obsProduto;
    }

    public void setObsProduto(String obsProduto) {
        this.obsProduto = obsProduto;
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

    public String getPrateleira() {
        return prateleira;
    }

    public void setPrateleira(String prateleira) {
        this.prateleira = prateleira;
    }

    public String getGaveta() {
        return gaveta;
    }

    public void setGaveta(String gaveta) {
        this.gaveta = gaveta;
    }

    public int getImpostoDoProduto() {
        return ImpostoDoProduto;
    }

    public void setImpostoDoProduto(int ImpostoDoProduto) {
        this.ImpostoDoProduto = ImpostoDoProduto;
    }
    
    public Fabricantes getIdfabricante() {
        return Idfabricante;
    }

    public void setIdfabricante(Fabricantes Idfabricante) {
        this.Idfabricante = Idfabricante;
    }

    public Estoques getIdestoque() {
        return Idestoque;
    }

    public void setIdestoque(Estoques Idestoque) {
        this.Idestoque = Idestoque;
    }

    public Categorias getIdcategoria() {
        return Idcategoria;
    }

    public void setIdcategoria(Categorias Idcategoria) {
        this.Idcategoria = Idcategoria;
    }

    public Fabricantes getNomeFabricantes() {
        return NomeFabricantes;
    }

    public void setNomeFabricantes(Fabricantes NomeFabricantes) {
        this.NomeFabricantes = NomeFabricantes;
    }

    public Categorias getNomeCategorias() {
        return NomeCategorias;
    }

    public void setNomeCategorias(Categorias NomeCategorias) {
        this.NomeCategorias = NomeCategorias;
    }
    
    public double getQuantidadeDisponivel() {
        return QuantidadeDisponivel;
    }

    public void setQuantidadeDisponivel(Double QuantidadeDisponivel) {
        this.QuantidadeDisponivel = QuantidadeDisponivel;
    }

    @Override
    public String toString() {
        return nomeProduto ;
    }
}
