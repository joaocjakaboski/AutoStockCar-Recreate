package com.mycompany.autostockcar.modelo.dao;

import com.mycompany.autostockcar.modelo.conexao.Conexao;
import com.mycompany.autostockcar.modelo.conexao.ConexaoMysql;
import com.mycompany.autostockcar.modelo.dominio.Categorias;
import com.mycompany.autostockcar.modelo.dominio.Fabricantes;
import com.mycompany.autostockcar.modelo.dominio.Produtos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ConsultaDao {
    int idProduto;
    String nomeProduto;
    String obsProduto;
    float valorCustoProduto;
    float valorFinal;
    String prateleira;    
    String gaveta;
    int idFabricante;
    String codigoFabricante;
    String nomeFabriante;
    int idEstoque;
    int idCategoria;
    String nomeCategoria;
    
    private final Conexao conexao;
    
    public ConsultaDao() {
        this.conexao = new ConexaoMysql();
    }
    
    public Produtos buscarPorId(int idProduto) {
        String sql = String.format("{CALL buscarProdutoPeloId (?)}");

        try {
            Connection connection = conexao.obterConexao();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idProduto);
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                return getProdutos(result);
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum produto encontrado com o ID especificado.");
            }

            //result.close();
            //stmt.close();
            //connection.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar produto por ID \n" + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }
    
        public Produtos buscarProdutosPeloNome(String consulta) {
        
        String sql = String.format("{CALL buscarProdutosPeloNome ('%%%s%%')}", consulta);
        
        try {
            ResultSet result = conexao.obterConexao().prepareStatement(sql).executeQuery();
            
            if (result.next()) {
                return getProdutos(result);
            }
        }catch (SQLException e) {
            System.out.println(String.format("Error: ", e.getMessage()));
        }
        
        return null;
    }
    
    public ResultSet buscarProdutoNomePeloNome(String consulta) {
        String sql = String.format("{CALL buscarProdutoNomePeloNome ('%%%s%%')}", consulta);

        try {
            Connection connection = conexao.obterConexao();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt = conexao.obterConexao().prepareCall(sql);
            return stmt.executeQuery();

        }catch (SQLException e) {
            System.out.println(String.format("Error: ", e.getMessage()));
            return null;
        } 
    }
    
        public Integer buscarCodigoPeloNome(String nomeProduto) {
        String sql = "{CALL buscarCodigoPeloNome (?)}";
        try (Connection connection = conexao.obterConexao();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, nomeProduto);
            try (ResultSet result = stmt.executeQuery()) {
                if (result.next()) {
                    return result.getInt("IdProduto");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar código pelo nome: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    private Produtos getProdutos(ResultSet result) throws SQLException {
       Produtos produto = new Produtos();

    produto.setIdProduto(result.getInt("IdProduto"));
    produto.setNomeProduto(result.getString("NomeProduto"));
    produto.setCodigoFabricante(result.getString("CodigoFabricante"));
    produto.setObsProduto(result.getString("ObsProduto"));
    produto.setValorCustoProduto(result.getBigDecimal("ValorCustoProduto"));
    produto.setValorFinal(result.getBigDecimal("ValorFinal"));
    produto.setPrateleira(result.getString("Prateleira"));
    produto.setGaveta(result.getString("Gaveta"));
    produto.setQuantidadeDisponivel(result.getInt("QuantidadeDisponivel"));

    Fabricantes fabricante = new Fabricantes();
    fabricante.setIdFabricante(result.getInt("IdFabricante"));
    produto.setIdfabricante(fabricante);
    
    Fabricantes fabricantee = new Fabricantes();
    //fabricante.setNomeFabricante(result.getString("NomeFabricante"));
    produto.setNomeFabricantes(fabricantee);
    
    Categorias categoria = new Categorias();
    categoria.setIdCategoria(result.getInt("IdCategoria"));
    produto.setIdcategoria(categoria);

    
    Categorias categoriaa = new Categorias();
    //categoriaa.setNomeCategoria(result.getString("NomeCategoria"));
    produto.setNomeCategorias(categoriaa);

    return produto;
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

    public float getValorCustoProduto() {
        return valorCustoProduto;
    }

    public void setValorCustoProduto(float valorCustoProduto) {
        this.valorCustoProduto = valorCustoProduto;
    }

    public float getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(float valorFinal) {
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

    public int getIdFabricante() {
        return idFabricante;
    }

    public void setIdFabricante(int idFabricante) {
        this.idFabricante = idFabricante;
    }

    public String getNomeFabriante() {
        return nomeFabriante;
    }

    public void setNomeFabriante(String nomeFabriante) {
        this.nomeFabriante = nomeFabriante;
    }

    public int getIdEstoque() {
        return idEstoque;
    }

    public void setIdEstoque(int idEstoque) {
        this.idEstoque = idEstoque;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }
    
    
}
