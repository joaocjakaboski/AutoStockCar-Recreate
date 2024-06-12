package com.mycompany.autostockcar.modelo.dominio;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public class Vendas {
    private int idVenda;
    private BigDecimal valorTotalVenda;
    private LocalDateTime dataCompra;
    private int cliente;

    public Vendas() {
    }

    public Vendas(int idVenda, BigDecimal valorTotalVenda, LocalDateTime dataCompra, int cliente) {
        this.idVenda = idVenda;
        this.valorTotalVenda = valorTotalVenda;
        this.dataCompra = dataCompra;
        this.cliente = cliente;
    }

    public int getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(int idVenda) {
        this.idVenda = idVenda;
    }

    public BigDecimal getValorTotalVenda() {
        return valorTotalVenda;
    }

    public void setValorTotalVenda(BigDecimal valorTotalVenda) {
        this.valorTotalVenda = valorTotalVenda;
    }

    public LocalDateTime getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(LocalDateTime dataCompra) {
        this.dataCompra = dataCompra;
    }

    public int getCliente() {
        return cliente;
    }

    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

    
}
