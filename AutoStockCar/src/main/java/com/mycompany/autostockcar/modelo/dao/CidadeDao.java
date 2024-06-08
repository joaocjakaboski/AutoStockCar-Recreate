
package com.mycompany.autostockcar.modelo.dao;

import com.mycompany.autostockcar.modelo.conexao.Conexao;
import com.mycompany.autostockcar.modelo.conexao.ConexaoMysql;
import com.mycompany.autostockcar.modelo.dominio.Cidades;
import com.mycompany.autostockcar.modelo.dominio.Perfil;
import com.mycompany.autostockcar.modelo.dominio.Usuarios;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CidadeDao {
    private final Conexao conexao;
    private PreparedStatement pstm;
    
    public CidadeDao() {
        this.conexao = new ConexaoMysql();
    }
    
    public ResultSet buscarCidadeNomePeloNome(String cidade) {
        String sql = String.format("SELECT NomeCidade FROM Cidade WHERE NomeCidade  '%%%s%%'", cidade);
        
        try {
            pstm = conexao.obterConexao().prepareCall(sql);
            return pstm.executeQuery();

        }catch (SQLException e) {
            System.out.println(String.format("Error: ", e.getMessage()));
            return null;
        } 
    }
    
    public ResultSet buscarCidades(int idEstado) throws SQLException {
        String sql = String.format("SELECT NomeCidade FROM Cidades WHERE IdEstado = %d", idEstado);
        
        try {
            pstm = conexao.obterConexao().prepareCall(sql);
            return pstm.executeQuery();

        }catch (SQLException e) {
            System.out.println(String.format("Error: ", e.getMessage()));
            return null;
        } 
    }
    
    public ResultSet buscarCidadeIdPeloNome(String cidade) {
        String sql = String.format("SELECT IdCidade FROM Cidades WHERE NomeCidade = '%s'", cidade);
        
        try {
            pstm = conexao.obterConexao().prepareCall(sql);
            return pstm.executeQuery();

        }catch (SQLException e) {
            System.out.println(String.format("Error: ", e.getMessage()));
            return null;
        } 
    }
    
    public Cidades buscarCidadePeloId(int idCidade) {
        String sql = String.format("SELECT * FROM Cidades WHERE IdCidade = %d", idCidade);
        try {
            ResultSet result = conexao.obterConexao().prepareStatement(sql).executeQuery();
            
            if (result.next()) {
                return getCidade(result);
            }
        }catch (SQLException e) {
            System.out.println(String.format("Error: ", e.getMessage()));
        }
        
        return null;
    }
    
    private Cidades getCidade(ResultSet result) throws SQLException {
        Cidades cidade = new Cidades();
        
        cidade.setIdCidade(result.getInt("IdCidade"));
        cidade.setNomeCidade(result.getString("NomeCidade"));
        cidade.setIdEstado(result.getInt("IdEstado"));
        
        return cidade;
    }
}
