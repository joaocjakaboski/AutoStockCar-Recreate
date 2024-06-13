package com.mycompany.autostockcar.modelo.dao;

import com.mycompany.autostockcar.modelo.conexao.Conexao;
import com.mycompany.autostockcar.modelo.conexao.ConexaoMysql;
import com.mycompany.autostockcar.modelo.dominio.Clientes;
import com.mycompany.autostockcar.modelo.dominio.Fabricantes;
import com.mycompany.autostockcar.view.modelo.FabricanteDto;
import com.mysql.cj.protocol.Resultset;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class FabricanteDao {
    private final Conexao conexao;
    private PreparedStatement pstm;
    
public FabricanteDao() {
    this.conexao = new ConexaoMysql();
    }
    public String salvar(Fabricantes fabricante) throws SQLException  {
        return fabricante.getIdFabricante()== 0 ? adicionar(fabricante) : editar(fabricante);
    }
    
    private String adicionar(Fabricantes fabricante) throws SQLException{
        String sql = "INSERT INTO Fabricantes(NomeFabricante, EmailFabricante, TelefoneFabricante, EnderecoFabricante, CNPJFabricante, ObsFabricante, IdCidade) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        Fabricantes fabricanteTemp = buscarFabricantePeloNome(fabricante.getNomeFabricante());
        
        if (fabricanteTemp != null) {
            return String.format("Error: fabricante %s ja existe no banco de dados", fabricante.getNomeFabricante());
        }
        
        try {
            PreparedStatement preparedStatement = conexao.obterConexao().prepareStatement(sql);
            
            preencherValoresPreparedStatement(preparedStatement, fabricante);
            
            int resultado = preparedStatement.executeUpdate();
            
            return resultado == 1 ? "Fabricante adicionado com sucesso" : "Nao foi possivel adicionar fabricante";
        } catch (SQLException e) {
            return String.format("Error: %s", e.getMessage());
        }
    }

    private String editar(Fabricantes fabricante) throws SQLException{
        String sql = "UPDATE Fabricantes SET NomeFabricante = ?, EmailFabricante = ?, TelefoneFabricante = ?,  EnderecoFabricante = ?, CNPJFabricante = ?, ObsFabricante = ?, IdCidade = ? WHERE Idfabricante = ?";
        try {
            PreparedStatement preparedStatement = conexao.obterConexao().prepareStatement(sql);
            
            preencherValoresPreparedStatement(preparedStatement, fabricante);
            
            int resultado = preparedStatement.executeUpdate();
            
            return resultado == 1 ? "Fabricante editado com sucesso" : "Nao foi possivel editar Fabricante";
        } catch (SQLException e) {
            return String.format("Error: %s", e.getMessage());
        }
    }
    
    private void preencherValoresPreparedStatement(PreparedStatement preparedStatement, Fabricantes fabricante) throws SQLException {
        
        
        preparedStatement.setString(1, fabricante.getNomeFabricante());
        preparedStatement.setString(2, fabricante.getEmailFabricante());
        preparedStatement.setString(3, fabricante.getTelefoneFabricante());
        preparedStatement.setString(4, fabricante.getEnderecoFabricante());
        preparedStatement.setString(5, fabricante.getEnderecoFabricante());
        preparedStatement.setString(6, fabricante.getCnpjFabricante());
        preparedStatement.setString(7, fabricante.getObsFabricante());
        preparedStatement.setInt(8, fabricante.getIdCidade());
    
        
        if(fabricante.getIdFabricante()!= 0) {
            preparedStatement.setInt(9, fabricante.getIdFabricante());
        }
    }
    public List<Fabricantes> buscarTodosclientes() {
        String sql = "SELECT * FROM clientes";
        List<Fabricantes> fabricantes = new ArrayList<>();
        try {
            ResultSet result = conexao.obterConexao().prepareStatement(sql).executeQuery();
            
            while (result.next()) {
                fabricantes.add(getFabricante(result));
            }
        }catch (SQLException e) {
            System.out.println(String.format("Error: ", e.getMessage()));
        }
        
        return fabricantes;
    }
    
    public Fabricantes buscarFabricantePeloNome(String fabricante) {
        
        String sql = String.format("SELECT * FROM fabricantes WHERE NomeFabricante = '%s'", fabricante);
        
        try {
            ResultSet result = conexao.obterConexao().prepareStatement(sql).executeQuery();
            
            if (result.next()) {
                return getFabricante(result);
            }
        }catch (SQLException e) {
            System.out.println(String.format("Error: ", e.getMessage()));
        }
        
        return null;
    }
    
    public ResultSet buscarFabricanteNomePeloNome(String fabricante) {
        String sql = String.format("SELECT NomeFabricante FROM Fabricantes WHERE NomeFabricante LIKE '%%%s%%'", fabricante);
        
        try {
            pstm = conexao.obterConexao().prepareCall(sql);
            return pstm.executeQuery();

        }catch (SQLException e) {
            System.out.println(String.format("Error: ", e.getMessage()));
            return null;
        } 
    }
    
        private Fabricantes getFabricante(ResultSet result) throws SQLException {
        Fabricantes fabricante = new Fabricantes();
        
        fabricante.setIdFabricante(result.getInt("IdFabricante"));
        fabricante.setNomeFabricante(result.getString("NomeFabricante"));
        fabricante.setTelefoneFabricante(result.getString("TelefoneFabricante"));
        fabricante.setEmailFabricante(result.getString("EmailFabricante"));
        fabricante.setEnderecoFabricante(result.getString("EnderecoFabricante"));
        fabricante.setCnpjFabricante(result.getString("CNPJFabricante"));
        fabricante.setObsFabricante(result.getString("ObsFabricante"));
        fabricante.setIdCidade(result.getInt("IdCidade"));
      
        return fabricante;
    }
        
    public String excluirFabricante(int id) {
        String sql = String.format("DELETE FROM Fabricantes WHERE IdFabricante = %s", id);
        try {
            pstm = conexao.obterConexao().prepareCall(sql);
            int resultado = pstm.executeUpdate();

            return resultado == 1 ? "Fabricante excluido com sucesso" : "Nao foi possivel excluir fabricante";
        } catch (SQLException e) {
            return String.format("Error: %s", e.getMessage());
        }
    }
}
/*  
public FabricanteDto BuscarFabricante(String nome){
    try {
        String sql = "Select * from fabricantes where NomeFabricante = ?";
        PreparedStatement stmt = conexao.obterConexao().prepareStatement(sql);
        stmt.setString(1, nome);
        ResultSet rs = stmt.executeQuery();
        FabricanteDto obj = new FabricanteDto(); 
        if(rs.next()){
            obj.setIdFabricante(rs.getInt("IdFabricante"));
            obj.setNomeFabricante(rs.getString("NomeFabricante"));
            obj.setEmailFabricante(rs.getString("EmailFabricante"));
            obj.setTelefoneFabricante(rs.getString("TelefoneFabricante"));
            obj.setEnderecoFabricante(rs.getString("EnderecoFabricante"));
            obj.setCnpjFabricante(rs.getString("CNPJFabricante"));
            obj.setObservacaoFabricante(rs.getString("ObsFabricante"));
        }
        return obj;
        
    }catch(Exception erro){
        JOptionPane.showMessageDialog(null,"erro ao buscar cliente" + erro);
    }
    return null;
} 

public void Salvar (FabricanteDto obj){
    try{
      // Criar o SQl 
      String sql = "insert into fabricantes (NomeFabricante,EmailFabricante,TelefoneFabricante,enderecoFabricante,CNPJFabricante,ObsFabricante)"
              + "values(?,?,?,?,?,?)";
      // Preparar conexao SQL para se conectar com o banco 
      PreparedStatement stmt = conexao.obterConexao().prepareStatement(sql);
      stmt.setString(1,obj.getNomeFabricante());
      stmt.setString(2,obj.getEmailFabricante());
      stmt.setString(3,obj.getTelefoneFabricante());
      stmt.setString(4,obj.getEnderecoFabricante());
      stmt.setString(5,obj.getCnpjFabricante());
      stmt.setString(6,obj.getObservacaoFabricante());
      // Executar o sql com o obj 
      stmt.execute();
      // fechar conexao 
      stmt.close(); 
      JOptionPane.showMessageDialog(null,"Cliente salvo com sucesso!" );
      
    }
    catch(SQLException erro){
        JOptionPane.showMessageDialog(null, "Erro ao salvar o cliente" + erro);
    }  
}
public void Editar(FabricanteDto obj){
    try{
      // Criar o SQl 
      String sql = "update Fabricantes set nomeFabricante=?,EmailFabricante=?,TelefoneFabricante=?,"
              + "enderecoFabricante=?,CNPJFabricante=?,ObsFabricante=? where idFabricante=?";
      // Preparar conexao SQL para se conectar com o banco 
      PreparedStatement stmt = conexao.obterConexao().prepareStatement(sql);
      stmt.setString(1,obj.getNomeFabricante());
      stmt.setString(2,obj.getEmailFabricante());
      stmt.setString(3,obj.getTelefoneFabricante());
      stmt.setString(4,obj.getEnderecoFabricante());
      stmt.setString(5,obj.getCnpjFabricante());
      stmt.setString(6,obj.getObservacaoFabricante());
      stmt.setInt(7,obj.getIdFabricante());
      
      // Executar o sql com o obj 
      stmt.execute();
      // fechar conexao 
      stmt.close(); 
      JOptionPane.showMessageDialog(null,"Cliente alterado com sucesso!" );
      
    }
    catch(SQLException erro){
        JOptionPane.showMessageDialog(null, "Erro ao editar o cliente" + erro);
    }  
}
public void Excluir(FabricanteDto obj){
    try {
        String sql = "delete from Fabricantes where idFabricante=?";
        PreparedStatement stmt = conexao.obterConexao().prepareStatement(sql);
        stmt.setInt(1, obj.getIdFabricante());
        stmt.execute();
        stmt.close();
        JOptionPane.showMessageDialog(null,"Cliente excluído com sucesso");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null,"Erro ao excluir o cliente" + e);
    }
}
}*/