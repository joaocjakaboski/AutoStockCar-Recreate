package com.mycompany.autostockcar.modelo.dominio;


public class Estoques {
    private int idEstoque;
    private int quantidadeEstoque;

    public Estoques() {
    }

    public Estoques(int idEstoque, int quantidadeEstoque) {
        this.idEstoque = idEstoque;
        this.quantidadeEstoque =  quantidadeEstoque;
    }

    public int getIdEstoque() {
        return idEstoque;
    }

    public void setIdEstoque(int idEstoque) {
        this.idEstoque = idEstoque;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    } 
}
