package com.mycompany.autostockcar.controller;

import com.mycompany.autostockcar.modelo.dao.AutenticacaoDao;
import com.mycompany.autostockcar.modelo.dominio.InfoMenu;
import com.mycompany.autostockcar.modelo.dominio.Usuarios;
import com.mycompany.autostockcar.view.componentes.Mensagem;
import com.mycompany.autostockcar.view.formulario.Dashboard;
import com.mycompany.autostockcar.view.formulario.Login;
import com.mycompany.autostockcar.view.modelo.LoginDto;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;


public class LoginController implements ActionListener{
    
    private final Login login;
    private AutenticacaoDao autenticacaoDao;    
    
    public LoginController(Login login) {
        this.login = login;
        this.autenticacaoDao = new AutenticacaoDao();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String acao = ae.getActionCommand().toLowerCase();
        //System.out.println(acao);
        switch(acao) {
            case "login" : login(); break;
            case "cancelar" : cancelar(); break;
        }
    }

    private void login() {
         String usuario = this.login.getTextLoginUsuario().getText();
         String senha = this.login.getTextLoginSenha().getText();
         
         if (usuario.equals("") || senha.equals("")) {
             login.getMensagemUtil().mostrarMensagem(Mensagem.TipoMensagem.ERRO,"Usuario e senha devem ser preenchidos.");
             return;
         }
         
         LoginDto loginDto = new LoginDto(usuario, senha);
         
         Usuarios usuarioTemp = this.autenticacaoDao.login(loginDto);
         
         if (usuarioTemp != null) {
             login.getMensagemUtil().mostrarMensagem(Mensagem.TipoMensagem.SUCESSO, "Login com sucesso!");
             login.getCarregar().setVisible(true);
             
             new Thread (() -> {
                 try {
                    Thread.sleep(2000);
                    limpaCampos();
                    login.setVisible(false);
                    new Dashboard(usuarioTemp.getNomeUsuario(), usuarioTemp.getAdmCategoria()).setVisible(true);
                 }catch (Exception e) {
                 }
             }).start();
             
         } else {
             login.getMensagemUtil().mostrarMensagem(Mensagem.TipoMensagem.ERRO ,"Usuario e/ou senha incorreto(s)!");
         }
    }

    private void cancelar() {
        int confirmar = JOptionPane.showConfirmDialog(login, "Tem certeza que deseja sair?", "Sair do sistema", JOptionPane.YES_NO_OPTION);
        
        if (confirmar == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
    
    private void limpaCampos() {
        this.login.getTextLoginUsuario().setText("");
        this.login.getTextLoginSenha().setText("");
    }
    
}