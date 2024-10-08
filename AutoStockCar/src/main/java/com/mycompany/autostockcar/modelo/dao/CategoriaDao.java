package com.mycompany.autostockcar.modelo.dao;

import com.mycompany.autostockcar.modelo.conexao.Conexao;
import com.mycompany.autostockcar.modelo.conexao.ConexaoMysql;
import com.mycompany.autostockcar.modelo.dominio.Categorias;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class CategoriaDao {

    private String nomeCategoria;
    private String descricao;
    private final Conexao conexao;

    public CategoriaDao() {
        this.conexao = new ConexaoMysql();
    }
    

    public void salvar() {
        String sql = "{CALL salvar_categoria(?, ?)}";
        try (Connection connection = conexao.obterConexao();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, nomeCategoria);
            stmt.setString(2, descricao);

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Categoria cadastrada com sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar com o banco de dados para inclusão");
            ex.printStackTrace();
        }
    }


    public void excluir(int idCategoria) {
        String sql = "{CALL excluir_categoria(?)}";
        try (Connection connection = conexao.obterConexao();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, idCategoria);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Categoria excluída com sucesso.");
            } else {
                JOptionPane.showMessageDialog(null, "Nenhuma categoria encontrada com o ID especificado.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar com o banco de dados para exclusão");
            ex.printStackTrace();
        }
    }


    public Categorias buscarPorId(int idCategoria) {
        String sql = "{CALL buscarCategoriaPeloId (?) }";
        try (Connection connection = conexao.obterConexao();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, idCategoria);
            try (ResultSet result = stmt.executeQuery()) {
                if (result.next()) {
                    return getCategorias(result);
                } else {
                    JOptionPane.showMessageDialog(null, "Nenhuma categoria encontrada com o ID especificado.");
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar categoria por ID");
            ex.printStackTrace();
        }
        return null;
    }

   /* public List<Categorias> buscarPeloNome() {
        String sql = "SELECT * FROM categorias";
        List<Categorias> categorias = new ArrayList<>();
        try (Connection connection = conexao.obterConexao();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet result = stmt.executeQuery()) {

            while (result.next()) {
                categorias.add(getCategorias(result));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar pelo nome: " + e.getMessage());
            e.printStackTrace();
        }
        return categorias;
    }

    public List<String> buscarCategoriaPeloNome(String nomeCategoria) {
        String sql = "SELECT NomeCategoria FROM categorias WHERE NomeCategoria LIKE ?";
        List<String> categorias = new ArrayList<>();
        try (Connection connection = conexao.obterConexao();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, "%" + nomeCategoria + "%");
            try (ResultSet result = stmt.executeQuery()) {
                while (result.next()) {
                    categorias.add(result.getString("NomeCategoria"));
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar categoria pelo nome: " + e.getMessage());
            e.printStackTrace();
        }
        return categorias;
    }
    
        public Integer buscarCodigoPeloNome(String nomeCategoria) {
        String sql = "SELECT IdCategoria FROM categorias WHERE NomeCategoria = ?";
        try (Connection connection = conexao.obterConexao();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, nomeCategoria);
            try (ResultSet result = stmt.executeQuery()) {
                if (result.next()) {
                    return result.getInt("IdCategoria");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar código pelo nome: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }*/

    private Categorias getCategorias(ResultSet result) throws SQLException {
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
