package com.mycompany.autostockcar.modelo.dominio;

import java.math.BigDecimal;


public class ItensVenda {
    private int idItensVenda;
    private int idvenda;
    private BigDecimal valorParcial;
    private int quantidadeItensVenda;
    private int idProduto;
    private Produtos produto;
    private BigDecimal precoUnitario;
    private BigDecimal desconto;
    private BigDecimal imposto;
    private BigDecimal totalAPagar;

    public ItensVenda() {
        this.idItensVenda = 0;
        this.idvenda = 0;
        this.valorParcial = BigDecimal.ZERO;
        this.quantidadeItensVenda = 0;
        this.produto = new Produtos();
        this.precoUnitario = BigDecimal.ZERO;
        this.desconto = BigDecimal.ZERO;
        this.imposto = BigDecimal.ZERO;
        this.totalAPagar = BigDecimal.ZERO;
    }

    public ItensVenda(int idItensVenda, int idvenda, int idProduto, BigDecimal valorParcial, int quantidadeItensVenda,  Produtos produto, BigDecimal precoUnitario, BigDecimal desconto, BigDecimal imposto, BigDecimal totalAPagar) {
        this.idItensVenda = idItensVenda;
        this.idvenda = idvenda;
        this.idProduto = idProduto;
        this.valorParcial = valorParcial;
        this.quantidadeItensVenda = quantidadeItensVenda;
        this.produto = produto;
        this.precoUnitario = precoUnitario;
        this.desconto = desconto;
        this.imposto = imposto;
        this.totalAPagar = totalAPagar;
    }

    public int getIdItensVenda() {
        return idItensVenda;
    }

    public void setIdItensVenda(int idItensVenda) {
        this.idItensVenda = idItensVenda;
    }

    public int getIdvenda() {
        return idvenda;
    }

    public void setIdvenda(int idvenda) {
        this.idvenda = idvenda;
    }

    public BigDecimal getValorParcial() {
        return valorParcial;
    }

    public void setValorParcial(BigDecimal valorParcial) {
        this.valorParcial = valorParcial;
    }

    public int getQuantidadeItensVenda() {
        return quantidadeItensVenda;
    }

    public void setQuantidadeItensVenda(int quantidadeItensVenda) {
        this.quantidadeItensVenda = quantidadeItensVenda;
    }

   

    public Produtos getProduto() {
        return produto;
    }

    public void setProduto(Produtos produto) {
        this.produto = produto;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public BigDecimal getImposto() {
        return imposto;
    }

    public void setImposto(BigDecimal  imposto) {
        this.imposto = imposto;
    }

    public BigDecimal getTotalAPagar() {
        return totalAPagar;
    }

    public void setTotalAPagar(BigDecimal totalAPagar) {
        this.totalAPagar = totalAPagar;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    

    @Override
    public String toString() {
        return "ItensVenda{" + "idItensVenda=" + idItensVenda + ", idvenda=" + idvenda + ", idProduto=" + idProduto + ",valorParcial=" + valorParcial + ", quantidadeItensVenda=" + quantidadeItensVenda + ", produto=" + produto + ", precoUnitario=" + precoUnitario + ", desconto=" + desconto + ", imposto=" + imposto + ", totalAPagar=" + totalAPagar + '}';
    }
    
    
    
    
    
    
}
