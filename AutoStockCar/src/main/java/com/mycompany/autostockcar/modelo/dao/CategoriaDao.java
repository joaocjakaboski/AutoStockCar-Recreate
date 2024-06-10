package com.mycompany.autostockcar.modelo.dao;

import com.mycompany.autostockcar.modelo.conexao.Conexao;
import com.mycompany.autostockcar.modelo.conexao.ConexaoMysql;
import com.mycompany.autostockcar.modelo.dominio.Categorias;
import com.mycompany.autostockcar.modelo.dominio.Produtos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class CategoriaDao {
    
    String nomeCategoria;
    String descricao;

    private final Conexao conexao;
    
    public CategoriaDao() {
        this.conexao = new ConexaoMysql();
    }
    
    public void salvar(){
        try{
            String sql = "INSERT INTO categorias(NomeCategoria, DescricaoCategoria) VALUES(?, ?)";
            
            PreparedStatement stmt = conexao.obterConexao().prepareStatement(sql);
            
            stmt.setString(1, nomeCategoria);
            stmt.setString(2, descricao);
            
            stmt.executeUpdate();

            stmt.close();
            conexao.obterConexao().close();
            JOptionPane.showMessageDialog(null, "Categoria Cadastrada com Sucesso");
            
        }catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro de conexão com o banco de dados de Inclusão");
            ex.printStackTrace();
        }
    }
    
        public void excluir(int idCategoria) {
        try{
            
            String sql = "DELETE FROM categorias WHERE IdCategoria = ?";
            
            PreparedStatement stmt = conexao.obterConexao().prepareStatement(sql);
            stmt.setInt(1, idCategoria);
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {     
                JOptionPane.showMessageDialog(null, "Categoria excluída com sucesso.");
            } else {
                JOptionPane.showMessageDialog(null, "Nenhuma categoria encontrada com o ID especificado.");
            }           
            stmt.close();
            
            conexao.obterConexao().close();
            
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro de conexão com o banco de dados de exclusão");
            ex.printStackTrace();
        }
        
    }
        
    public Categorias buscarPorId(int idCategorias) {
            String sql = String.format ("SELECT * FROM categorias WHERE IdCategoria = ?");

    try {
        Connection connection = conexao.obterConexao();
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, idCategorias);
        ResultSet result = stmt.executeQuery();

        if (result.next()) {
            return getCategorias(result);
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum produto encontrado com o ID especificado.");
        }
        
        result.close();
        stmt.close();
        connection.close();
        
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Erro ao buscar produto por ID");
        ex.printStackTrace();
    }
    return null;
    }   
    
    private Categorias getCategorias(ResultSet result) throws SQLException{
        Categorias categoria = new Categorias();
        
        categoria.setIdCategoria(result.getInt("IdCategoria"));
        categoria.setNomeCategoria(result.getString("NomeCategoria"));
        categoria.setDescricaoCategoria(result.getString("DescricaoCategoria"));
        
        return categoria;      
    }
    
    public String getNomeCategoria() {
        return nomeCategoria;
    }
    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
