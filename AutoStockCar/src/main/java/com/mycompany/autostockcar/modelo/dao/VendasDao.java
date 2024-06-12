
package com.mycompany.autostockcar.modelo.dao;

import com.mycompany.autostockcar.modelo.dominio.Produtos;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class VendasDao {
    private static final String URL = "jdbc:mysql://localhost/autostockcar?useTimezone=true&serverTimezone=America/Sao_Paulo";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public Produtos obterDadosProduto(String nomeProduto) {
        String sql = "SELECT * FROM produtos WHERE NomeProduto LIKE ?";
        Produtos produto = null;

        try (Connection conn = conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nomeProduto);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int idProduto = rs.getInt("IdProduto");
                String nome = rs.getString("NomeProduto");
                int quantidade = rs.getInt("QuantidadeDisponivel");
                BigDecimal valorFinal = rs.getBigDecimal("ValorFinal");
                int ImpostoDoProduto = rs.getInt("ImpostoDoProduto");

               produto = new Produtos(idProduto, nome, quantidade, valorFinal, ImpostoDoProduto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produto;
    }
    
    
    
}
