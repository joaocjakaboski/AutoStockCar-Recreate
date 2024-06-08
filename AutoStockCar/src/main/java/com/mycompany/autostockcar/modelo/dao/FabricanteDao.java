package com.mycompany.autostockcar.modelo.dao;

import com.mycompany.autostockcar.modelo.conexao.Conexao;
import com.mycompany.autostockcar.modelo.conexao.ConexaoMysql;
import com.mycompany.autostockcar.view.modelo.FabricanteDto;
import com.mysql.cj.protocol.Resultset;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class FabricanteDao {
    private final Conexao conexao;
    
public FabricanteDao() {
    this.conexao = new ConexaoMysql();
    }

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
}