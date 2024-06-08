package com.mycompany.autostockcar;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        /*String sql = "SELECT * FROM Categorias";
        
        Conexao conexao = new ConexaoMysql();
        
        Categorias categoria = new Categorias("Turbina moto ap", "Ap vai explodir");
        
        String inserirSQL = "INSERT INTO Categorias(NomeCategoria, DescricaoCategoria) VALUES(?, ?)";
        
        PreparedStatement preparedStatement = conexao.obterConexao().prepareStatement(inserirSQL);
        
        preparedStatement.setString(1, categoria.getNomeCategoria());
        preparedStatement.setString(2, categoria.getDescricaoCategoria());
        
        int resultadoDoCadastro = preparedStatement.executeUpdate();
        
        System.out.println(resultadoDoCadastro);
        
        ResultSet result = conexao.obterConexao().prepareStatement(sql).executeQuery();
        
        while(result.next()) {
            System.out.println(result.getString("NomeCategoria"));
        }*/
    }
}