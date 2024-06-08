package com.mycompany.autostockcar.modelo.dominio;

import java.math.BigDecimal;


public class ItensVenda {
    private int idItensVenda;
    private int idvenda;
    private BigDecimal valorParcial;
    private int quantidadeItensVenda;
    private Vendas venda;
    private Produtos produto;
    private BigDecimal precoUnitario;
    private BigDecimal desconto;
    private double iva;
    private BigDecimal totalAPagar;

    public ItensVenda() {
        this.idItensVenda = 0;
        this.idvenda = 0;
        this.valorParcial = BigDecimal.ZERO;
        this.quantidadeItensVenda = 0;
        this.venda = new Vendas();
        this.produto = new Produtos();
        this.precoUnitario = BigDecimal.ZERO;
        this.desconto = BigDecimal.ZERO;
        this.iva = 0.0;
        this.totalAPagar = BigDecimal.ZERO;
    }

    public ItensVenda(int idItensVenda, int idvenda, BigDecimal valorParcial, int quantidadeItensVenda, Vendas venda, Produtos produto, BigDecimal precoUnitario, BigDecimal desconto, double iva, BigDecimal totalAPagar) {
        this.idItensVenda = idItensVenda;
        this.idvenda = idvenda;
        this.valorParcial = valorParcial;
        this.quantidadeItensVenda = quantidadeItensVenda;
        this.venda = venda;
        this.produto = produto;
        this.precoUnitario = precoUnitario;
        this.desconto = desconto;
        this.iva = iva;
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

    public Vendas getVenda() {
        return venda;
    }

    public void setVenda(Vendas venda) {
        this.venda = venda;
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

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public BigDecimal getTotalAPagar() {
        return totalAPagar;
    }

    public void setTotalAPagar(BigDecimal totalAPagar) {
        this.totalAPagar = totalAPagar;
    }

    @Override
    public String toString() {
        return "ItensVenda{" + "idItensVenda=" + idItensVenda + ", idvenda=" + idvenda + ", valorParcial=" + valorParcial + ", quantidadeItensVenda=" + quantidadeItensVenda + ", venda=" + venda + ", produto=" + produto + ", precoUnitario=" + precoUnitario + ", desconto=" + desconto + ", iva=" + iva + ", totalAPagar=" + totalAPagar + '}';
    }
    
    
    
    
    
    
}
