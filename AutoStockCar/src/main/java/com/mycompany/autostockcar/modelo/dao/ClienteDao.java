package com.mycompany.autostockcar.modelo.dao;

import com.mycompany.autostockcar.modelo.conexao.Conexao;
import com.mycompany.autostockcar.modelo.conexao.ConexaoMysql;
import com.mycompany.autostockcar.modelo.dominio.Clientes;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ClienteDao {
    private final Conexao conexao;
    private PreparedStatement pstm;
    
    public ClienteDao() {
        this.conexao = new ConexaoMysql();
    }

    public String salvar(Clientes cliente) throws SQLException  {
        return cliente.getIdCliente() == 0 ? adicionar(cliente) : editar(cliente);
    }

    private String adicionar(Clientes cliente) throws SQLException{
        String sql = "INSERT INTO Clientes(NomeCliente, TelefoneCliente, EmailCliente, EnderecoCliente, BairroCliente, CPFCliente, ObsCliente, IdCidade) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        Clientes clienteTemp = buscarClientePeloNome(cliente.getNomeCliente());
        
        if (clienteTemp != null) {
            return String.format("Error: cliente %s ja existe no banco de dados", cliente.getNomeCliente());
        }
        
        try {
            PreparedStatement preparedStatement = conexao.obterConexao().prepareStatement(sql);
            
            preencherValoresPreparedStatement(preparedStatement, cliente);
            
            int resultado = preparedStatement.executeUpdate();
            
            return resultado == 1 ? "Cliente adicionado com sucesso" : "Nao foi possivel adicionar cliente";
        } catch (SQLException e) {
            return String.format("Error: %s", e.getMessage());
        }
    }

    private String editar(Clientes cliente) throws SQLException{
        String sql = "UPDATE Clientes SET NomeCliente = ?, TelefoneCliente = ?, EmailCliente = ?, EnderecoCliente = ?, BairroCliente = ?, CPFCliente = ?, ObsCliente = ?, IdCidade = ? WHERE IdCliente = ?";
        try {
            PreparedStatement preparedStatement = conexao.obterConexao().prepareStatement(sql);
            
            preencherValoresPreparedStatement(preparedStatement, cliente);
            
            int resultado = preparedStatement.executeUpdate();
            
            return resultado == 1 ? "Cliente editado com sucesso" : "Nao foi possivel editar cliente";
        } catch (SQLException e) {
            return String.format("Error: %s", e.getMessage());
        }
    }

    private void preencherValoresPreparedStatement(PreparedStatement preparedStatement, Clientes cliente) throws SQLException {
        
        preparedStatement.setString(1, cliente.getNomeCliente());
        preparedStatement.setString(2, cliente.getTelefoneCliente());
        preparedStatement.setString(3, cliente.getEmailCliente());
        preparedStatement.setString(4, cliente.getEnderecoCliente());
        preparedStatement.setString(5, cliente.getBairroCliente());
        preparedStatement.setString(6, cliente.getCpfCliente());
        preparedStatement.setString(7, cliente.getObsCliente());
        preparedStatement.setInt(8, cliente.getIdCidade());
        
        if(cliente.getIdCliente() != 0) {
            preparedStatement.setInt(9, cliente.getIdCliente());
        }
    }
    
    public List<Clientes> buscarTodosclientes() {
        String sql = "SELECT * FROM clientes";
        List<Clientes> clientes = new ArrayList<>();
        try {
            ResultSet result = conexao.obterConexao().prepareStatement(sql).executeQuery();
            
            while (result.next()) {
                clientes.add(getCliente(result));
            }
        }catch (SQLException e) {
            System.out.println(String.format("Error: ", e.getMessage()));
        }
        
        return clientes;
    }
    
    public Clientes buscarClientePeloCpf(String cpf) {
        String sql = String.format("SELECT * FROM Clientes WHERE CPFCliente = %s", cpf);
        try {
            ResultSet result = conexao.obterConexao().prepareStatement(sql).executeQuery();
            
            if (result.next()) {
                return getCliente(result);
            }
        }catch (SQLException e) {
            System.out.println(String.format("Error: ", e.getMessage()));
        }
        
        return null;
    }
    
    public Clientes buscarClientePeloNome(String cliente) {
        
        String sql = String.format("SELECT * FROM clientes WHERE Nomecliente = '%s'", cliente);
        
        try {
            ResultSet result = conexao.obterConexao().prepareStatement(sql).executeQuery();
            
            if (result.next()) {
                return getCliente(result);
            }
        }catch (SQLException e) {
            System.out.println(String.format("Error: ", e.getMessage()));
        }
        
        return null;
    }
    
    public ResultSet buscarClienteNomePeloNome(String cliente) {
        String sql = String.format("SELECT NomeCliente FROM Clientes WHERE NomeCliente LIKE '%%%s%%'", cliente);
        
        try {
            pstm = conexao.obterConexao().prepareCall(sql);
            return pstm.executeQuery();

        }catch (SQLException e) {
            System.out.println(String.format("Error: ", e.getMessage()));
            return null;
        } 
    }
    
    private Clientes getCliente(ResultSet result) throws SQLException {
        Clientes cliente = new Clientes();
        
        cliente.setIdCliente(result.getInt("Idcliente"));
        cliente.setNomeCliente(result.getString("Nomecliente"));
        cliente.setTelefoneCliente(result.getString("TelefoneCliente"));
        cliente.setEmailCliente(result.getString("EmailCliente"));
        cliente.setEnderecoCliente(result.getString("EnderecoCliente"));
        cliente.setBairroCliente(result.getString("BairroCliente"));
        cliente.setCpfCliente(result.getString("CPFCliente"));
        cliente.setObsCliente(result.getString("ObsCliente"));
        cliente.setIdCidade(result.getInt("IdCidade"));
        cliente.setData(result.getDate("DataCadastroCliente"));
  
        return cliente;
    }
    
    public String excluirUsuario(String cpf) {
        String sql = String.format("DELETE FROM Clientes WHERE CPFCliente = %s", cpf);
        try {
            pstm = conexao.obterConexao().prepareCall(sql);
            int resultado = pstm.executeUpdate();

            return resultado == 1 ? "Usuario excluido com sucesso" : "Nao foi possivel excluir usuario";
        } catch (SQLException e) {
            return String.format("Error: %s", e.getMessage());
        }
    }
}
