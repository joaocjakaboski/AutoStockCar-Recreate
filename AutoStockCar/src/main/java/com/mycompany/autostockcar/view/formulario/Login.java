package com.mycompany.autostockcar.view.formulario;


import com.mycompany.autostockcar.controller.LoginController;
import com.mycompany.autostockcar.view.componentes.Carregar;
import com.mycompany.autostockcar.view.util.MensagemUtil;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;


public class Login extends javax.swing.JFrame {
    
    private int x;
    private int y;
    private LoginController loginController;
    private MigLayout layout;
    private Carregar carregar;
    private MensagemUtil mensagemUtil;
    
    String caminho = "/com/mycompany/autostockcar/view/imagens/";

    public Login() {
        initComponents();
        setLocationRelativeTo(null);
        setBackground(new Color(0,0,0,0));
        btnLoginLogin.setIcon(new ImageIcon(getClass().getResource(caminho + "login_16.png")));
        txLoginUsuario.setPrefixoIcon(new ImageIcon(getClass().getResource(caminho + "mail.png")));
        txLoginSenha.setPrefixoIcon(new ImageIcon(getClass().getResource(caminho + "pass.png")));
        
        
        carregar = new Carregar();
        
        layout = new MigLayout("fill, insets");
        background.setLayout(layout);
        background.add(carregar, "pos 0 0 100% 100%");
        background.add(panelBoard1, "pos 0 0 100% 100%");
        
        mensagemUtil = new MensagemUtil(background, layout);
        
        this.loginController = new LoginController(this);
        eventos();
        moveTelaLogin(this);
        fechaTela();
    }

    private void moveTelaLogin(JFrame frame) {
        frameMovimento.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                x = getX();
                y = getY();
            }
        });
        
        frameMovimento.addMouseMotionListener(new MouseAdapter() {
           @Override
           public void mouseDragged(MouseEvent e) {
               frame.setLocation(e.getXOnScreen(), e.getYOnScreen());
           }
        });
    }
    
    private void fechaTela() {
        labelFechar.addMouseListener(new MouseAdapter() {
           @Override
           public void mousePressed(MouseEvent e) {
               int opcao = JOptionPane.showConfirmDialog(null, "Confirmar saida", "Sair", JOptionPane.YES_NO_OPTION);
               if (opcao == JOptionPane.YES_OPTION)  {
                   System.exit(0);
               }
           }
        });
        
        labelFechar.addMouseListener(new MouseAdapter() {
           @Override
           public void mouseEntered(MouseEvent e) {
               labelFechar.setBackground(Color.RED);
               labelFechar.setForeground(Color.WHITE);
               labelFechar.setOpaque(true);
           }
           
           @Override
           public void mouseExited(MouseEvent e) {
               labelFechar.setOpaque(false);
               labelFechar.setBackground(null);
               labelFechar.setForeground(Color.WHITE);
           }
        });
    }
    
    
    
    public Carregar getCarregar() {
        return carregar;
    }

    public MensagemUtil getMensagemUtil() {
        return mensagemUtil;
    }
    
    
    
   private void eventos() {
       txLoginSenha.addActionListener(loginController);
       btnLoginLogin.addActionListener(loginController);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBoard1 = new com.mycompany.autostockcar.view.componentes.PanelBoard();
        frameMovimento = new javax.swing.JPanel();
        labelFechar = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txLoginSenha = new com.mycompany.autostockcar.view.componentes.CampoDeSenha();
        txLoginUsuario = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        btnLoginLogin = new com.mycompany.autostockcar.view.componentes.Botao();
        jSeparator1 = new javax.swing.JSeparator();
        background = new javax.swing.JLayeredPane();

        panelBoard1.setColor1(new java.awt.Color(221, 221, 221));
        panelBoard1.setColor2(new java.awt.Color(221, 221, 221));

        frameMovimento.setOpaque(false);

        labelFechar.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        labelFechar.setForeground(new java.awt.Color(255, 255, 255));
        labelFechar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelFechar.setText("X");

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 25)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(30, 30, 30));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Auto StockCar");

        javax.swing.GroupLayout frameMovimentoLayout = new javax.swing.GroupLayout(frameMovimento);
        frameMovimento.setLayout(frameMovimentoLayout);
        frameMovimentoLayout.setHorizontalGroup(
            frameMovimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, frameMovimentoLayout.createSequentialGroup()
                .addGap(0, 107, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62)
                .addComponent(labelFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        frameMovimentoLayout.setVerticalGroup(
            frameMovimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frameMovimentoLayout.createSequentialGroup()
                .addComponent(labelFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 14, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, frameMovimentoLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel2))
        );

        txLoginSenha.setForeground(new java.awt.Color(30, 30, 30));
        txLoginSenha.setActionCommand("login");
        txLoginSenha.setCor(new java.awt.Color(255, 255, 255));
        txLoginSenha.setDicas("Senha");
        txLoginSenha.setDisabledTextColor(new java.awt.Color(30, 30, 30));

        txLoginUsuario.setForeground(new java.awt.Color(30, 30, 30));
        txLoginUsuario.setToolTipText("");
        txLoginUsuario.setCorTexto(new java.awt.Color(200, 200, 200));
        txLoginUsuario.setDicas("Usuário");
        txLoginUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txLoginUsuarioActionPerformed(evt);
            }
        });

        btnLoginLogin.setBackground(new java.awt.Color(220, 220, 220));
        btnLoginLogin.setForeground(new java.awt.Color(30, 30, 30));
        btnLoginLogin.setText("Login");
        btnLoginLogin.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N

        jSeparator1.setBackground(new java.awt.Color(200, 200, 200));
        jSeparator1.setOpaque(true);

        javax.swing.GroupLayout panelBoard1Layout = new javax.swing.GroupLayout(panelBoard1);
        panelBoard1.setLayout(panelBoard1Layout);
        panelBoard1Layout.setHorizontalGroup(
            panelBoard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(frameMovimento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator1)
            .addGroup(panelBoard1Layout.createSequentialGroup()
                .addGap(106, 106, 106)
                .addGroup(panelBoard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txLoginSenha, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(txLoginUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBoard1Layout.createSequentialGroup()
                .addContainerGap(149, Short.MAX_VALUE)
                .addComponent(btnLoginLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(156, 156, 156))
        );
        panelBoard1Layout.setVerticalGroup(
            panelBoard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBoard1Layout.createSequentialGroup()
                .addComponent(frameMovimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)
                .addComponent(txLoginUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txLoginSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnLoginLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 38, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setSize(new java.awt.Dimension(412, 290));

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 412, Short.MAX_VALUE)
        );
        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 290, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txLoginUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txLoginUsuarioActionPerformed
        txLoginSenha.requestFocusInWindow();
    }//GEN-LAST:event_txLoginUsuarioActionPerformed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane background;
    private com.mycompany.autostockcar.view.componentes.Botao btnLoginLogin;
    private javax.swing.JPanel frameMovimento;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel labelFechar;
    private com.mycompany.autostockcar.view.componentes.PanelBoard panelBoard1;
    private com.mycompany.autostockcar.view.componentes.CampoDeSenha txLoginSenha;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txLoginUsuario;
    // End of variables declaration//GEN-END:variables

    public JTextField getTextLoginUsuario() {
        return txLoginUsuario;
    }
    
    public JPasswordField getTextLoginSenha() {
        return txLoginSenha;
    }
    
    public JButton getBotaoLoginLogin() {
        return btnLoginLogin;
    }
}
