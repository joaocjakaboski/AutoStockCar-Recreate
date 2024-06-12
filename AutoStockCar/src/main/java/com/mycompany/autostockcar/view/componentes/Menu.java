package com.mycompany.autostockcar.view.componentes;

import com.mycompany.autostockcar.modelo.dominio.Perfil;
import com.mycompany.autostockcar.view.formulario.Cadastrop;
import com.mycompany.autostockcar.view.formulario.Client;
import com.mycompany.autostockcar.view.formulario.Consulta;
import com.mycompany.autostockcar.view.formulario.Dashboard;
import com.mycompany.autostockcar.view.formulario.TabelaUsuarios;
import com.mycompany.autostockcar.view.formulario.VendasF;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;


public class Menu extends javax.swing.JPanel {
    private String nomeUsuario;
    private Perfil perfil;
    private JFrame paiHerdado;
    
    String caminho = System.getProperty("user.dir") + "\\src\\main\\java\\com\\mycompany\\autostockcar\\view\\imagens\\";
    
    public Menu() {
        initComponents();
        setOpaque(false);
        labelUsuario.setIcon(new ImageIcon(caminho + "user.png"));
        labelUsuario.setText(nomeUsuario);
        
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem menuItem1 = new JMenuItem("Gerenciar usuários");
        
        menuItem1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TabelaUsuarios().setVisible(true);
            }
        });
        
        popupMenu.add(menuItem1);
        
        labelUsuario.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (perfil.equals(Perfil.ADMIN)) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
        
        labelUsuario.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                labelUsuario.setBackground(new Color(131, 191, 205));
                labelUsuario.setForeground(Color.WHITE);
                labelUsuario.setOpaque(true);
            }
           
           @Override
           public void mouseExited(MouseEvent e) {
               labelUsuario.setOpaque(false);
               labelUsuario.setBackground(null);
               labelUsuario.setForeground(Color.WHITE);
           }
        });
    }
    
    @Override
    public void paintComponent (Graphics g) {
        Graphics2D grafico = (Graphics2D) g;
        
        grafico.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        GradientPaint pintura = new GradientPaint(0, 0, new Color(220,220,220), getWidth(), 0, new Color(147, 211, 225));
        grafico.setPaint(pintura);
        
        grafico.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
        grafico.fillRect(0, getHeight()-30, getWidth(), getHeight());
        
        super.paintComponent(g);
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        labelUsuario = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        btnMenuDashboard = new com.mycompany.autostockcar.view.componentes.Botao();
        btnMenuVendas = new com.mycompany.autostockcar.view.componentes.Botao();
        btnMenuProdutos = new com.mycompany.autostockcar.view.componentes.Botao();
        btnMenuClientes = new com.mycompany.autostockcar.view.componentes.Botao();
        btnMenuConsulta = new com.mycompany.autostockcar.view.componentes.Botao();

        setPreferredSize(new java.awt.Dimension(1162, 113));

        jPanel1.setOpaque(false);

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(30, 30, 30));
        jLabel1.setText("Auto StockCar");

        labelUsuario.setBackground(new java.awt.Color(255, 255, 255));
        labelUsuario.setFont(new java.awt.Font("SansSerif", 1, 8)); // NOI18N
        labelUsuario.setForeground(new java.awt.Color(30, 30, 30));
        labelUsuario.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jSeparator1.setForeground(new java.awt.Color(30, 30, 30));
        jSeparator1.setPreferredSize(new java.awt.Dimension(0, 5));

        btnMenuDashboard.setBackground(new java.awt.Color(163, 225, 255));
        btnMenuDashboard.setForeground(new java.awt.Color(30, 30, 30));
        btnMenuDashboard.setText("Dashboard");
        btnMenuDashboard.setActionCommand("btnMenuDashboard");
        btnMenuDashboard.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        btnMenuDashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuDashboardActionPerformed(evt);
            }
        });

        btnMenuVendas.setBackground(new java.awt.Color(163, 225, 255));
        btnMenuVendas.setForeground(new java.awt.Color(30, 30, 30));
        btnMenuVendas.setText("Vendas");
        btnMenuVendas.setActionCommand("btnMenuVendas");
        btnMenuVendas.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        btnMenuVendas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuVendasActionPerformed(evt);
            }
        });

        btnMenuProdutos.setBackground(new java.awt.Color(163, 225, 255));
        btnMenuProdutos.setForeground(new java.awt.Color(30, 30, 30));
        btnMenuProdutos.setText("Produtos");
        btnMenuProdutos.setActionCommand("btnMenuProdutos");
        btnMenuProdutos.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        btnMenuProdutos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuProdutosActionPerformed(evt);
            }
        });

        btnMenuClientes.setBackground(new java.awt.Color(163, 225, 255));
        btnMenuClientes.setForeground(new java.awt.Color(30, 30, 30));
        btnMenuClientes.setText("Clientes");
        btnMenuClientes.setActionCommand("btnMenuClientes");
        btnMenuClientes.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        btnMenuClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuClientesActionPerformed(evt);
            }
        });

        btnMenuConsulta.setBackground(new java.awt.Color(163, 225, 255));
        btnMenuConsulta.setForeground(new java.awt.Color(30, 30, 30));
        btnMenuConsulta.setText("Consulta");
        btnMenuConsulta.setActionCommand("btnMenuConsulta");
        btnMenuConsulta.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        btnMenuConsulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuConsultaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(295, Short.MAX_VALUE)
                .addComponent(btnMenuDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnMenuProdutos, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnMenuVendas, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnMenuConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnMenuClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(295, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnMenuDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMenuVendas, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMenuProdutos, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMenuConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMenuClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnMenuDashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuDashboardActionPerformed
        new Dashboard(nomeUsuario, perfil).setVisible(true);
        paiHerdado.dispose();
    }//GEN-LAST:event_btnMenuDashboardActionPerformed

    private void btnMenuProdutosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuProdutosActionPerformed
        new Cadastrop().setVisible(true);
        paiHerdado.dispose();
    }//GEN-LAST:event_btnMenuProdutosActionPerformed

    private void btnMenuVendasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuVendasActionPerformed
        new VendasF().setVisible(true);
        paiHerdado.dispose();
    }//GEN-LAST:event_btnMenuVendasActionPerformed

    private void btnMenuConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuConsultaActionPerformed
        new Consulta().setVisible(true);
        paiHerdado.dispose();
    }//GEN-LAST:event_btnMenuConsultaActionPerformed

    private void btnMenuClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuClientesActionPerformed
        new Client().setVisible(true);
        paiHerdado.dispose();
    }//GEN-LAST:event_btnMenuClientesActionPerformed

    public JFrame getPaiHerdado() {
        return paiHerdado;
    }

    public void setPaiHerdado(JFrame paiHerdado) {
        this.paiHerdado = paiHerdado;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.mycompany.autostockcar.view.componentes.Botao btnMenuClientes;
    private com.mycompany.autostockcar.view.componentes.Botao btnMenuConsulta;
    private com.mycompany.autostockcar.view.componentes.Botao btnMenuDashboard;
    private com.mycompany.autostockcar.view.componentes.Botao btnMenuProdutos;
    private com.mycompany.autostockcar.view.componentes.Botao btnMenuVendas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel labelUsuario;
    // End of variables declaration//GEN-END:variables

}