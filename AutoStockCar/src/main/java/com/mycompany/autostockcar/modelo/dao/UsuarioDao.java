package com.mycompany.autostockcar.modelo.dao;

import com.mycompany.autostockcar.modelo.conexao.Conexao;
import com.mycompany.autostockcar.modelo.conexao.ConexaoMysql;
import com.mycompany.autostockcar.modelo.dominio.Perfil;
import com.mycompany.autostockcar.modelo.dominio.Usuarios;
import com.mycompany.autostockcar.view.formulario.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class UsuarioDao {

    
    private final Conexao conexao;
    
    public UsuarioDao() {
        this.conexao = new ConexaoMysql();
    }

    public String salvar(Usuarios usuario)  {
        return usuario.getIdUsuario() == 0 ? adicionar(usuario) : editar(usuario);
    }

    private String adicionar(Usuarios usuario) {
        String sql = "INSERT INTO Usuarios(NomeUsuario, SenhaUsuario, AdmCategoria) VALUES (?, ?, ?)";
        
        Usuarios usuarioTemp = buscarUsuarioPeloNome(usuario.getNomeUsuario());
        
        if (usuarioTemp != null) {
            return String.format("Error: usuario %s ja existe no banco de dados", usuario.getNomeUsuario());
        }
        
        try {
            PreparedStatement preparedStatement = conexao.obterConexao().prepareStatement(sql);
            
            preencherValoresPreparedStatement(preparedStatement, usuario);
            
            int resultado = preparedStatement.executeUpdate();
            
            return resultado == 1 ? "Usuario adicionado com sucesso" : "Nao foi possivel adicionar usuario";
        } catch (SQLException e) {
            return String.format("Error: %s", e.getMessage());
        }
    }

    private String editar(Usuarios usuario) {
        String sql = "UPDATE Usuarios SET NomeUsuario = ?, SenhaUsuario = ?, AdmCategoria = ? WHERE IdUsuario = ?";
        try {
            PreparedStatement preparedStatement = conexao.obterConexao().prepareStatement(sql);
            
            preencherValoresPreparedStatement(preparedStatement, usuario);
            
            int resultado = preparedStatement.executeUpdate();
            
            return resultado == 1 ? "Usuario editado com sucesso" : "Nao foi possivel editar usuario";
        } catch (SQLException e) {
            return String.format("Error: %s", e.getMessage());
        }
    }

    private void preencherValoresPreparedStatement(PreparedStatement preparedStatement, Usuarios usuario) throws SQLException {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        
        String senhaCript = passwordEncoder.encode(usuario.getSenhaUsuario());
        
        preparedStatement.setString(1, usuario.getNomeUsuario());
        preparedStatement.setString(2, senhaCript);
        preparedStatement.setString(3, usuario.getAdmCategoria().name());
        
        if(usuario.getIdUsuario() != 0) {
            preparedStatement.setInt(4, usuario.getIdUsuario());
        }
    }
    
    public List<Usuarios> buscarTodosUsuarios() {
        String sql = "SELECT * FROM Usuarios";
        List<Usuarios> usuarios = new ArrayList<>();
        try {
            ResultSet result = conexao.obterConexao().prepareStatement(sql).executeQuery();
            
            while (result.next()) {
                usuarios.add(getUsuario(result));
            }
        }catch (SQLException e) {
            System.out.println(String.format("Error: ", e.getMessage()));
        }
        
        return usuarios;
    }
    
    public Usuarios buscarUsuarioPeloId(int id) {
        String sql = String.format("SELECT * FROM Usuarios WHERE IdUsuario = %d", id);
        try {
            ResultSet result = conexao.obterConexao().prepareStatement(sql).executeQuery();
            
            if (result.next()) {
                return getUsuario(result);
            }
        }catch (SQLException e) {
            System.out.println(String.format("Error: ", e.getMessage()));
        }
        
        return null;
    }
    
    public Usuarios buscarUsuarioPeloNome(String usuario) {
        String sql = String.format("SELECT * FROM Usuarios WHERE NomeUsuario = '%s'", usuario);
        try {
            ResultSet result = conexao.obterConexao().prepareStatement(sql).executeQuery();
            
            if (result.next()) {
                return getUsuario(result);
            }
        }catch (SQLException e) {
            System.out.println(String.format("Error: ", e.getMessage()));
        }
        
        return null;
    }
    
    private Usuarios getUsuario(ResultSet result) throws SQLException {
        Usuarios usuario = new Usuarios();
        
        usuario.setIdUsuario(result.getInt("IdUsuario"));
        usuario.setNomeUsuario(result.getString("NomeUsuario"));
        usuario.setSenhaUsuario(result.getString("SenhaUsuario"));
        usuario.setAdmCategoria(Perfil.valueOf(result.getString("AdmCategoria")));
        
        return usuario;
    }

    public String excluirUsuario(int id) {
        String sql = String.format("DELETE FROM Usuarios WHERE IdUsuario = ?", id);
        try {
            PreparedStatement preparedStatement = conexao.obterConexao().prepareStatement(sql);
            preparedStatement.setInt(1, id);

            int resultado = preparedStatement.executeUpdate();

            return resultado == 1 ? "Usuario excluido com sucesso" : "Nao foi possivel excluir usuario";
        } catch (SQLException e) {
            return String.format("Error: %s", e.getMessage());
        }
    }
 
}
