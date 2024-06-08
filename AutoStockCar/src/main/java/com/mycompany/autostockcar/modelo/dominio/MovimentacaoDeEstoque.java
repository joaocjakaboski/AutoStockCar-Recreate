package com.mycompany.autostockcar.modelo.dominio;


public class MovimentacaoDeEstoque {
    private int idMovimentacao;
    private int entradaSaida;
    private int quantidadeMovimentacao;
    private String motivoMovimentacao;
    private Produtos produto;
    private Fabricantes fabricante;

    public MovimentacaoDeEstoque() {
    }

    public MovimentacaoDeEstoque(int idMovimentacao, int entradaSaida, int quantidadeMovimentacao, String motivoMovimentacao, Produtos produto, Fabricantes fabricante) {
        this.idMovimentacao = idMovimentacao;
        this.entradaSaida = entradaSaida;
        this.quantidadeMovimentacao = quantidadeMovimentacao;
        this.motivoMovimentacao = motivoMovimentacao;
        this.produto = produto;
        this.fabricante = fabricante;
    }

    public int getIdMovimentacao() {
        return idMovimentacao;
    }

    public void setIdMovimentacao(int idMovimentacao) {
        this.idMovimentacao = idMovimentacao;
    }

    public int getEntradaSaida() {
        return entradaSaida;
    }

    public void setEntradaSaida(int entradaSaida) {
        this.entradaSaida = entradaSaida;
    }

    public int getQuantidadeMovimentacao() {
        return quantidadeMovimentacao;
    }

    public void setQuantidadeMovimentacao(int quantidadeMovimentacao) {
        this.quantidadeMovimentacao = quantidadeMovimentacao;
    }

    public String getMotivoMovimentacao() {
        return motivoMovimentacao;
    }

    public void setMotivoMovimentacao(String motivoMovimentacao) {
        this.motivoMovimentacao = motivoMovimentacao;
    }

    public Produtos getProduto() {
        return produto;
    }

    public void setProduto(Produtos produto) {
        this.produto = produto;
    }

    public Fabricantes getFabricante() {
        return fabricante;
    }

    public void setFabricante(Fabricantes fabricante) {
        this.fabricante = fabricante;
    }
    
    
}
