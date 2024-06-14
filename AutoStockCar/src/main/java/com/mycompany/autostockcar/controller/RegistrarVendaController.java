package com.mycompany.autostockcar.controller;

import com.mycompany.autostockcar.modelo.dominio.ItensVenda;
import com.mycompany.autostockcar.modelo.dominio.Produtos;
import com.mycompany.autostockcar.modelo.dominio.Vendas;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;


public class RegistrarVendaController {
    public static int idVendaRegistrada;
    java.math.BigDecimal idColVar;
    private Connection conn;
    private static final String URL = "jdbc:mysql://localhost/autostockcar?useTimezone=true&serverTimezone=America/Sao_Paulo";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
   
    public boolean guardar(Vendas objeto){
    boolean resposta = false;
    try {
            Connection conn = conectar();
            PreparedStatement consulta = conn.prepareStatement("insert into vendas values(?,?,?,?)",
            Statement.RETURN_GENERATED_KEYS);
            consulta.setInt(1, 0);//id
            consulta.setBigDecimal(2,objeto.getValorTotalVenda());
            LocalDateTime dataCompra = objeto.getDataCompra();
            Timestamp timestamp = Timestamp.valueOf(dataCompra);
            consulta.setTimestamp(3, timestamp);
            consulta.setInt(4, objeto.getCliente());
            
            if (consulta.executeUpdate() > 0){
                resposta = true;
            }
            
            ResultSet rs = consulta.getGeneratedKeys();
            while(rs.next()){
                idColVar = rs.getBigDecimal(1);
                idVendaRegistrada = idColVar.intValue();
            }
    }catch (SQLException e) {
            System.out.println("Erro ao guardar venda!" + e);
    }
        return resposta;
    } 
    
    public boolean guardarDetalhe(ItensVenda objeto){
    boolean resposta = false;
    try {
            Connection conn = conectar();
            PreparedStatement consulta = conn.prepareStatement("insert into Itensvenda values(?,?,?,?,?,?,?,?,?)");
            consulta.setInt(1,0);//id
            consulta.setInt(2, objeto.getQuantidadeItensVenda());
            consulta.setBigDecimal(3, objeto.getPrecoUnitario());
            consulta.setBigDecimal(4, objeto.getValorParcial());
            consulta.setBigDecimal(5, objeto.getDesconto());
            consulta.setBigDecimal(6, objeto.getTotalAPagar());
            consulta.setBigDecimal(7, new BigDecimal(100));
            consulta.setInt(8, idVendaRegistrada);
            consulta.setInt(9, objeto.getIdProduto());
            
            
            if (consulta.executeUpdate() > 0){
                resposta = true;
            }
            
            conn.close();
    }catch (SQLException e) {
            System.out.println("Erro ao guardar detalhes venda!" + e);
    }
        return resposta;
    }
     
    public boolean atualizar(Vendas objeto, int idVenda) {
    boolean resposta = false;
    Connection conn = null;
    
    try {
        conn = conectar();
        PreparedStatement stmt = conn.prepareStatement("update vendas set IdCliente = ? where IdVenda = ?");
        stmt.setInt(1, objeto.getCliente());
        stmt.setInt(2, idVenda);

        if (stmt.executeUpdate() > 0) {
            resposta = true;
        }
    } catch (SQLException e) {
        System.out.println("Erro ao atualizar venda: " + e);
    } finally {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar conexão: " + e);
            }
        }
    }
    
    return resposta;
    }
    
    
}