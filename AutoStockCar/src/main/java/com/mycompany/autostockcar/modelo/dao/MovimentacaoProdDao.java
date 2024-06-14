package com.mycompany.autostockcar.modelo.dao;

import com.mycompany.autostockcar.modelo.conexao.Conexao;
import com.mycompany.autostockcar.modelo.conexao.ConexaoMysql;
import com.mycompany.autostockcar.modelo.dominio.Fabricantes;
import com.mycompany.autostockcar.modelo.dominio.MovimentacaoDeEstoque;
import com.mycompany.autostockcar.modelo.dominio.Produtos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class MovimentacaoProdDao {
    int idMovimentacao;
    int entradaSaida;
    int quantidadeMovimentacao;
    String motivoMovimentacao;
    int idFabricante;
    int IdProduto;
    
    private final Conexao conexao;
    
    public MovimentacaoProdDao() {
        this.conexao = new ConexaoMysql();
    }
 
    public void alterar(MovimentacaoDeEstoque movimentacao) {
        String sql = "UPDATE movimentacaodeestoque SET EntradaSaida = ?, QuantidadeMovimentacao = ?, MotivoMovimentacao = ? WHERE IdMovimentacao = ?";
        try (Connection connection = conexao.obterConexao(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, movimentacao.getEntradaSaida());
            stmt.setInt(2, movimentacao.getQuantidadeMovimentacao());
            stmt.setString(3, movimentacao.getMotivoMovimentacao());
            stmt.setInt(4, movimentacao.getIdMovimentacao());
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Movimentação alterada com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Nenhuma movimentação foi alterada.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao alterar movimentação: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

public MovimentacaoDeEstoque buscarPorId(int idMovimentacao) {
    String sql = "SELECT * FROM MovimentacaoDeEstoque WHERE IdMovimentacao = ?";
    
    try {
        Connection connection = conexao.obterConexao();
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, idMovimentacao);
        ResultSet result = stmt.executeQuery();
        
        if (result.next()) {
            // Criar e retornar um objeto MovimentacaoDeEstoque a partir do ResultSet
            MovimentacaoDeEstoque movimentacao = new MovimentacaoDeEstoque();
            movimentacao.setIdMovimentacao(result.getInt("IdMovimentacao"));
            //movimentacao.setProduto(buscarProdutoPorId(result.getInt("IdProduto"))); // Certifique-se de que este método está correto
            movimentacao.setQuantidadeMovimentacao(result.getInt("QuantidadeMovimentacao"));
            movimentacao.setMotivoMovimentacao(result.getString("MotivoMovimentacao"));
            //movimentacao.setFabricante(buscarFabricantePorId(result.getInt("IdFabricante"))); // Certifique-se de que este método está correto
            
            return movimentacao;
        } else {
            JOptionPane.showMessageDialog(null, "Nenhuma movimentação encontrada com o ID especificado.");
        }
        
        result.close();
        stmt.close();
        connection.close();
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Erro ao buscar movimentação por ID");
        ex.printStackTrace();
    }
    return null;
}
 

    private MovimentacaoDeEstoque getMovimentacaoDeEstoque(ResultSet result) throws SQLException {
        MovimentacaoDeEstoque movimentacao = new MovimentacaoDeEstoque();
        movimentacao.setIdMovimentacao(result.getInt("IdMovimentacao"));
        movimentacao.setEntradaSaida(result.getInt("EntradaSaida"));
        movimentacao.setQuantidadeMovimentacao(result.getInt("QuantidadeMovimentacao"));
        movimentacao.setMotivoMovimentacao(result.getString("MotivoMovimentacao"));
        
        Fabricantes fabricante = new Fabricantes();
        fabricante.setIdFabricante(result.getInt("IdFabricante"));
        movimentacao.setFabricante(fabricante);

        Produtos produto = new Produtos();
        produto.setIdProduto(result.getInt("IdProduto"));
        movimentacao.setProduto(produto);

        return movimentacao;
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

    public int getIdFabricante() {
        return idFabricante;
    }

    public void setIdFabricante(int idFabricante) {
        this.idFabricante = idFabricante;
    }

    public int getIdProduto() {
        return IdProduto;
    }

    public void setIdProduto(int IdProduto) {
        this.IdProduto = IdProduto;
    }
    

}
