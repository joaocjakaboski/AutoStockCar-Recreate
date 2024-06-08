package com.mycompany.autostockcar.view.formulario;

import com.mycompany.autostockcar.modelo.dao.UsuarioDao;
import com.mycompany.autostockcar.modelo.dominio.Perfil;
import com.mycompany.autostockcar.modelo.dominio.Usuarios;
import com.mycompany.autostockcar.view.componentes.Mensagem;
import com.mycompany.autostockcar.view.util.MensagemUtil;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import net.miginfocom.swing.MigLayout;


public class Usuario extends javax.swing.JFrame {
    
    private UsuarioDao usuarioDao;
    private Perfil admCategoria;
    private TabelaUsuarios tabelaUsuarios;
    private MigLayout layout;
    private MensagemUtil mensagem;
   
    public Usuario(TabelaUsuarios tabelaUsuarios) {
        this.tabelaUsuarios = tabelaUsuarios;
        initComponents();
        setLocationRelativeTo(null);
        layout = new MigLayout("fill, insets");
        background.setLayout(layout);
        
        
        mensagem = new MensagemUtil(background, layout);
        
        btnAlterar.setVisible(false);     
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(chkAdmin.isSelected()){
                    admCategoria = Perfil.ADMIN;
                } else {
                    admCategoria = Perfil.PADRAO;
                }
                
                 cadastrarUsuario();
            }
        });
    }
    
    public Usuario(TabelaUsuarios tabelaUsuarios, int id, String nome, String perfil) {
    this.tabelaUsuarios = tabelaUsuarios;
    initComponents();
    setLocationRelativeTo(null);
    
    layout = new MigLayout("fill, insets");
    background.setLayout(layout);
    mensagem = new MensagemUtil(background, layout);
    
    txUsuario.setText(nome);
    txSenha.setText("");
    //txSenha.setEnabled(false);
    //txSenha.setCor(new Color (131, 135, 141));
    chkMostrarSenha.setEnabled(false);
    chkAdmin.setSelected("ADMIN".equals(perfil));
    btnSalvar.setVisible(false); // Ocultar botão salvar para edição
    btnAlterar.setVisible(true); // Mostrar botão alterar para edição

    btnAlterar.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            char[] senha = txSenha.getPassword();
            if(senha.length == 0){
                mensagem.mostrarMensagem(Mensagem.TipoMensagem.ERRO, "Campo de senha deve ser preenchido!");
                return;
            } else {
                alterarUsuario(id);
            }
        }
    });
}

private void alterarUsuario(int id) {
    /*this.usuarioDao = new UsuarioDao();
    Perfil admCategoria = chkAdmin.isSelected() ? Perfil.ADMIN : Perfil.PADRAO;
    Usuarios usuarioAlterar = new Usuarios(id, this.txUsuario.getText(), this.txSenha.getText(), admCategoria);
    usuarioDao.salvar(usuarioAlterar);
    tabelaUsuarios.readJTable(); // Atualiza a tabela após a alteração
    dispose(); // Fecha a janela de edição*/
    
    this.usuarioDao = new UsuarioDao();
    Usuarios usuarioExistente = usuarioDao.buscarUsuarioPeloId(id); // Assumindo que esse método existe para buscar o usuário pelo ID

    if (usuarioExistente != null) {
        Perfil admCategoria = chkAdmin.isSelected() ? Perfil.ADMIN : Perfil.PADRAO;
        String senhaAtual = usuarioExistente.getSenhaUsuario(); // Pegue a senha existente do usuário

        Usuarios usuarioAlterar = new Usuarios(id, this.txUsuario.getText(), senhaAtual, admCategoria);
        usuarioDao.salvar(usuarioAlterar);
        mensagem.mostrarMensagem(Mensagem.TipoMensagem.SUCESSO, "Usuário alterado!");
        new Thread (() -> {
                 try {
                    Thread.sleep(2000);
                    tabelaUsuarios.readJTable(); // Atualiza a tabela após a alteração
                    dispose(); // Fecha a janela de edição
                 }catch (Exception e) {
                 }
        }).start();
        
    } else {
        // Tratamento de erro: Usuário não encontrado
        JOptionPane.showMessageDialog(this, "Usuário não encontrado", "Erro", JOptionPane.ERROR_MESSAGE);
    }
}

    
/*public void readJTable() {
    DefaultTableModel modelo = (DefaultTableModel) jtUsuarios.getModel();
    modelo.setRowCount(0); // Limpar tabela antes de preencher

    UsuarioDao usuarioDao = new UsuarioDao();
    List<Usuarios> listaUsuarios = usuarioDao.buscarTodosUsuarios();

    for (Usuarios usuario : listaUsuarios) {
        modelo.addRow(new Object[]{
            usuario.getIdUsuario(),
            usuario.getNomeUsuario(),
            usuario.getAdmCategoria().name()
        });
    }
}*/
    
    private void cadastrarUsuario() {
        this.usuarioDao = new UsuarioDao();
        Usuarios usuarioCadastro = new Usuarios(0, this.txUsuario.getText(),this.txSenha.getText(),admCategoria);
        usuarioDao.salvar(usuarioCadastro);
        mensagem.mostrarMensagem(Mensagem.TipoMensagem.SUCESSO, "Usuário cadastrado!");
        new Thread (() -> {
                 try {
                    Thread.sleep(2000);
                    tabelaUsuarios.readJTable(); // Atualiza a tabela após a alteração
                    dispose(); // Fecha a janela de edição
                 }catch (Exception e) {
                 }
        }).start();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background = new javax.swing.JLayeredPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        txSenha = new com.mycompany.autostockcar.view.componentes.CampoDeSenha();
        chkAdmin = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        txUsuario = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        jLabel3 = new javax.swing.JLabel();
        btnSalvar = new com.mycompany.autostockcar.view.componentes.Botao();
        btnAlterar = new com.mycompany.autostockcar.view.componentes.Botao();
        chkMostrarSenha = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        background.setPreferredSize(new java.awt.Dimension(624, 375));

        jPanel1.setBackground(new java.awt.Color(131, 191, 205));
        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel1.setName(""); // NOI18N

        jPanel2.setBackground(new java.awt.Color(147, 211, 225));
        jPanel2.setPreferredSize(new java.awt.Dimension(566, 322));

        txSenha.setDicas("");
        txSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txSenhaActionPerformed(evt);
            }
        });

        chkAdmin.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        chkAdmin.setForeground(new java.awt.Color(30, 30, 30));
        chkAdmin.setText("Admin");

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(30, 30, 30));
        jLabel2.setText("Login:");

        txUsuario.setDicas("Login");
        txUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txUsuarioActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(30, 30, 30));
        jLabel3.setText("Senha:");

        btnSalvar.setBackground(new java.awt.Color(163, 225, 255));
        btnSalvar.setForeground(new java.awt.Color(30, 30, 30));
        btnSalvar.setText("SALVAR");
        btnSalvar.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        btnSalvar.setPreferredSize(new java.awt.Dimension(62, 30));
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnAlterar.setBackground(new java.awt.Color(163, 225, 255));
        btnAlterar.setForeground(new java.awt.Color(30, 30, 30));
        btnAlterar.setText("ALTERAR");
        btnAlterar.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N

        chkMostrarSenha.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        chkMostrarSenha.setForeground(new java.awt.Color(30, 30, 30));
        chkMostrarSenha.setText("Mostrar Senha");
        chkMostrarSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMostrarSenhaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(chkMostrarSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGap(188, 188, 188)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3)
                                    .addComponent(txUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(chkAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 172, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkMostrarSenha)
                .addGap(18, 18, 18)
                .addComponent(chkAdmin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        background.setLayer(jPanel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txSenhaActionPerformed
    }//GEN-LAST:event_txSenhaActionPerformed

    private void txUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txUsuarioActionPerformed
    }//GEN-LAST:event_txUsuarioActionPerformed

    private void chkMostrarSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMostrarSenhaActionPerformed
        if(chkMostrarSenha.isSelected()){
            txSenha.setEchoChar((char)0);
        }
        else{
            txSenha.setFont(new Font("sanserif", 1, 15));
            txSenha.setEchoChar('*');
        }
    }//GEN-LAST:event_chkMostrarSenhaActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
       //readJTable();
    }//GEN-LAST:event_btnSalvarActionPerformed

   
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex){
            java.util.logging.Logger.getLogger(Usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Usuario(null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane background;
    private com.mycompany.autostockcar.view.componentes.Botao btnAlterar;
    private com.mycompany.autostockcar.view.componentes.Botao btnSalvar;
    private javax.swing.JCheckBox chkAdmin;
    private javax.swing.JCheckBox chkMostrarSenha;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private com.mycompany.autostockcar.view.componentes.CampoDeSenha txSenha;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txUsuario;
    // End of variables declaration//GEN-END:variables


}
