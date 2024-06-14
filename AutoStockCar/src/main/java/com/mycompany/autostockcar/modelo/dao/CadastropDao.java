package com.mycompany.autostockcar.modelo.dao;

import com.mycompany.autostockcar.modelo.conexao.Conexao;
import com.mycompany.autostockcar.modelo.conexao.ConexaoMysql;
import com.mycompany.autostockcar.modelo.dominio.Categorias;
import com.mycompany.autostockcar.modelo.dominio.Estoques;
import com.mycompany.autostockcar.modelo.dominio.Fabricantes;
import com.mycompany.autostockcar.modelo.dominio.Produtos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;


public class CadastropDao {
    int idProduto;
    String nomeProduto;
    String codigoFabricante;
    String obsProduto;
    float valorCustoProduto;
    float valorFinal;
    String prateleira;    
    String gaveta;
    int idFabricante;
    int idCategoria;
    int impostoDoProduto;
    
    
    
    private final Conexao conexao;
    
    public CadastropDao() {
        this.conexao = new ConexaoMysql();
    }
    
    public void salvar() {
        try {
            
            String sql = "INSERT INTO Produtos(NomeProduto, CodigoFabricante, ObsProduto, ValorCustoProduto, ValorFinal, Prateleira, Gaveta, ImpostoDoProduto, IdFabricante, IdCategoria) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            PreparedStatement stmt = conexao.obterConexao().prepareStatement(sql);
            
            stmt.setString(1, nomeProduto);
            stmt.setString(2, codigoFabricante);
            stmt.setString(3, obsProduto);
            stmt.setFloat(4, valorCustoProduto);
            stmt.setFloat(5, valorFinal);
            stmt.setString(6, prateleira);
            stmt.setString(7, gaveta);
            stmt.setInt(8, impostoDoProduto );
            stmt.setInt(9, idFabricante);
            stmt.setInt(10, idCategoria);

            // Execute a inserção
            stmt.executeUpdate();

            // Feche o PreparedStatement e a conexão
            stmt.close();
            conexao.obterConexao().close();
            // Caso dê tudo certo
            JOptionPane.showMessageDialog(null, "Produto Cadastrado com Sucesso");
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro de conexão com o banco de dados de Inclusão\n" + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public void excluir(int idProduto) {
        try{
            
            String sql = "DELETE FROM produtos WHERE IdProduto = ?";
            
            PreparedStatement stmt = conexao.obterConexao().prepareStatement(sql);
            stmt.setInt(1, idProduto);
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {     
                JOptionPane.showMessageDialog(null, "Produto excluído com sucesso.");
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum produto encontrado com o ID especificado.");
            }
            
            stmt.close();
            
            conexao.obterConexao().close();
            
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro de conexão com o banco de dados de exclusão");
            ex.printStackTrace();
        }
        
    }
    
    public Produtos buscarPorId(int idProduto) {
            String sql = String.format ("SELECT * FROM Produtos WHERE IdProduto = ?");

 
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
        
        result.close();
        stmt.close();
        connection.close();
        
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Erro ao buscar produto por ID");
        ex.printStackTrace();
    }
    return null;
    }   
    
    public void alterar(Produtos produto) {
    try {
        String sql = "UPDATE Produtos SET NomeProduto = ?, CodigoFabricante = ?, ObsProduto = ?, ValorCustoProduto = ?, ValorFinal = ?, Prateleira = ?, Gaveta = ?, ImpostoDoProduto = ? WHERE IdProduto = ?";
        Connection connection = conexao.obterConexao();
        PreparedStatement stmt = connection.prepareStatement(sql);

        stmt.setString(1, produto.getNomeProduto());
        stmt.setString(2, produto.getCodigoFabricante());
        stmt.setString(3, produto.getObsProduto());
        stmt.setBigDecimal(4, produto.getValorCustoProduto());
        stmt.setBigDecimal(5, produto.getValorFinal());
        stmt.setString(6, produto.getPrateleira());
        stmt.setString(7, produto.getGaveta());
        stmt.setInt(8, produto.getImpostoDoProduto());
        stmt.setInt(9, produto.getIdProduto());

        int rowsAffected = stmt.executeUpdate();

        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(null, "Produto alterado com sucesso");
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum produto foi alterado");
        }

        stmt.close();
        connection.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro de conexão com o banco de dados para alteração\n" + ex.getMessage());
            ex.printStackTrace();
        }
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
        produto.setImpostoDoProduto(result.getInt("ImpostoDoProduto"));
        
        Fabricantes fabricante = new Fabricantes();
        fabricante.setIdFabricante(result.getInt("IdFabricante"));
        produto.setIdfabricante(fabricante);

        Estoques estoque = new Estoques();
        estoque.setIdEstoque(result.getInt("IdEstoque"));
        produto.setIdestoque(estoque);

        Categorias categoria = new Categorias();
        categoria.setIdCategoria(result.getInt("IdCategoria"));
        produto.setIdcategoria(categoria);

        return produto;
    }

    public List<Produtos> buscarProdutosPeloNome(String nomeProduto) {
        List<Produtos> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produtos WHERE NomeProduto LIKE ?";
    
        try {
            Connection connection = conexao.obterConexao();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, "%" + nomeProduto + "%");
            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                produtos.add(getProdutos(result));
            }
        } catch (SQLException e) {
            System.out.println(String.format("Error: %s", e.getMessage()));
        }
    
        return produtos;
    }
    
    public Produtos buscarDadosDoProduto(String produto) {
        String sql = String.format("SELECT * FROM Produtos WHERE NomeProduto = '%s'", produto);
        try {
            ResultSet result = conexao.obterConexao().prepareStatement(sql).executeQuery();
            
            while (result.next()) {
                return getProdutos(result);
            }
        }catch (SQLException e) {
            System.out.println(String.format("Error: ", e.getMessage()));
        }
        
        return null;
    }

//Getter
    public int getIdProduto() {
        return idProduto;
    }
    public String getNomeProduto() {
        return nomeProduto;
    }
    public String getCodigoFabricante() {
        return codigoFabricante;
    }
    public String getObsProduto() {
        return obsProduto;
    }
    public float getValorCustoProduto() {
        return valorCustoProduto;
    }
    public float getValorFinal() {
        return valorFinal;
    }
    public String getPrateleira() {
        return prateleira;
    }
    public String getGaveta() {
        return gaveta;
    }
    public Conexao getConexao() {
        return conexao;
    }
    public int getIdFabricante() {
        return idFabricante;
    }
    public int getIdCategoria() {
        return idCategoria;
    }
    public int getImpostoDoProduto() {
        return impostoDoProduto;
    }
//Setter
    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }   
    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }
    public void setCodigoFabricante(String codigoFabricante) {
        this.codigoFabricante = codigoFabricante;
    }
    public void setObsProduto(String obsProduto) {
        this.obsProduto = obsProduto;
    }
    public void setValorCustoProduto(float valorCustoProduto) {
        this.valorCustoProduto = valorCustoProduto;
    }
    public void setValorFinal(float valorFinal) {
        this.valorFinal = valorFinal;
    }
    public void setPrateleira(String prateleira) {
        this.prateleira = prateleira;
    }
    public void setGaveta(String gaveta) {
        this.gaveta = gaveta;
    }
    public void setIdFabricante(int idFabricante) {
        this.idFabricante = idFabricante;
    }
    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }
    public void setImpostoDoProduto(int impostoDoProduto) {
        this.impostoDoProduto = impostoDoProduto;
    }
    
}
