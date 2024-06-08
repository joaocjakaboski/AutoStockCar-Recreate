package com.mycompany.autostockcar.modelo.dao;

import com.mycompany.autostockcar.modelo.dominio.Perfil;
import com.mycompany.autostockcar.modelo.dominio.Usuarios;
import com.mycompany.autostockcar.modelo.exception.NegocioException;
import com.mycompany.autostockcar.view.modelo.LoginDto;
import javax.swing.JOptionPane;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class AutenticacaoDao {
    
    private final UsuarioDao usuarioDao;

    public AutenticacaoDao() {
        this.usuarioDao = new UsuarioDao();
    }
    
    public boolean temPermissao(Usuarios usuario) {
        try {
            permissao(usuario);
            return true;
        }catch(Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Usuario sem permissao!", 0);
            return false;
        }
    }
    
    private void permissao(Usuarios usuario) {
        if (!Perfil.ADMIN.equals(usuario.getAdmCategoria())) {
            throw new NegocioException("Sem permissao para realizar essa acao");
        }
    }
    
    public Usuarios login(LoginDto login) {
        Usuarios usuario = usuarioDao.buscarUsuarioPeloNome(login.getUsuario());
        
        if (usuario == null) return null;
        
        if (validaSenha(usuario.getSenhaUsuario(), login.getSenha())) {
            return usuario;
        }
        return null;
    }

    private boolean validaSenha(String senhaUsuario, String senhaLogin) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(senhaLogin, senhaUsuario);
    }
    
}
