
package com.mycompany.autostockcar.modelo.dao;

import com.mycompany.autostockcar.modelo.conexao.Conexao;
import com.mycompany.autostockcar.modelo.conexao.ConexaoMysql;
import com.mycompany.autostockcar.modelo.dominio.Cidades;
import com.mycompany.autostockcar.modelo.dominio.Estados;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class EstadoDao {
    private final Conexao conexao;
    private PreparedStatement pstm;
    
    public EstadoDao() {
        this.conexao = new ConexaoMysql();
    }
    
    public ResultSet buscarUfEstados() {
        String sql = "{CALL buscarUfEstados}";
        
        try {
            pstm = conexao.obterConexao().prepareCall(sql);
            return pstm.executeQuery();

        }catch (SQLException e) {
            System.out.println(String.format("Error: ", e.getMessage()));
            return null;
        } 
    }
    
    public ResultSet buscarIdEstadoPeloUf(String uf) {
        String sql = String.format("{CALL buscarIdEstadoPeloUf ('%s')}", uf);
        try {
            pstm = conexao.obterConexao().prepareCall(sql);
            return pstm.executeQuery();

        }catch (SQLException e) {
            System.out.println(String.format("Error: ", e.getMessage()));
            return null;
        }
    }
    
    public Estados buscarEstadoPeloId(int idEstado) {
        String sql = String.format("{CALL buscarEstadoPeloId (%d)}", idEstado);
        try {
            ResultSet result = conexao.obterConexao().prepareStatement(sql).executeQuery();
            
            if (result.next()) {
                return getEstado(result);
            }
        }catch (SQLException e) {
            System.out.println(String.format("Error: ", e.getMessage()));
        }
        
        return null;
    }
    
    private Estados getEstado(ResultSet result) throws SQLException {
        Estados estado = new Estados();
        
        estado.setIdEstado(result.getInt("IdEstado"));
        estado.setNomeEstado(result.getString("NomeEstado"));
        estado.setUfEstado(result.getString("UfEstado"));
        
        return estado;
    }
}
