
package com.mycompany.autostockcar.view.formulario;

import com.mycompany.autostockcar.controller.RegistrarVendaController;
import com.mycompany.autostockcar.modelo.conexao.Conexao;
import com.mycompany.autostockcar.modelo.conexao.ConexaoMysql;
import com.mycompany.autostockcar.modelo.dao.ClienteDao;
import com.mycompany.autostockcar.modelo.dominio.Perfil;
import com.mycompany.autostockcar.modelo.dominio.Vendas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class GestaoVendas extends javax.swing.JFrame {

    private int idCliente = 0;
    private int idVenda;
    
    ClienteDao cliente = new ClienteDao();
    private Conexao conexao;
    private Connection conn;
    private PreparedStatement pstm;

    public GestaoVendas(String nomeUsuario, Perfil perfil) {
        initComponents();
        setLocationRelativeTo(null);
        menu1.setPaiHerdado(this);
        menu1.setNomeUsuario(nomeUsuario);
        menu1.setPerfil(perfil);
        //txData.setEditable(false);
        //txTotalPagar.setEditable(false);
        
        DefaultTableModel model = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Desabilita a edição de células
        }
    };
        
        this.conexao = new ConexaoMysql();
        try {
            this.CarregarTabelaVenda();
        } catch (SQLException e) {
            System.out.println("Erro ao carregar tabela de vendas: " + e.getMessage());
        }
        cbxNomeCliente.setEditable(true);
        cbxNomeCliente.removeAllItems();
        cbxNomeCliente.addItem("");

        cbxNomeCliente.getEditor().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> items = new ArrayList<>();
                String currentText = (String) (cbxNomeCliente.getEditor().getItem());
                try {
                    ResultSet result = (ResultSet) cliente.buscarClienteNomePeloNome(currentText);
                    cbxNomeCliente.removeAllItems();
                    cbxNomeCliente.addItem("");
                    while (result.next()) {
                        items.add(result.getString("NomeCliente"));
                    }
                    Collections.sort(items);
                    for (String item : items) {
                        cbxNomeCliente.addItem(item);
                    }
                } catch (SQLException error) {
                    JOptionPane.showMessageDialog(null, "Carregar dados cbx: " + error);
                }
                SwingUtilities.invokeLater(() -> cbxNomeCliente.setPopupVisible(true));
            }
        });
 
    }
    
   

    public GestaoVendas() {
    }
    
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane2 = new javax.swing.JLayeredPane();
        jPanel1 = new javax.swing.JPanel();
        menu1 = new com.mycompany.autostockcar.view.componentes.Menu();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTVendas = new javax.swing.JTable();
        cbxNomeCliente = new com.mycompany.autostockcar.view.componentes.ComboBoxPersonalizado();
        btnFiltrarNome = new com.mycompany.autostockcar.view.componentes.Botao();
        txIdCliente = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        btnFiltrarData = new com.mycompany.autostockcar.view.componentes.Botao();
        CodigoCliente = new javax.swing.JLabel();
        NomeCliente = new javax.swing.JLabel();
        NomeCliente1 = new javax.swing.JLabel();
        jFDate = new javax.swing.JFormattedTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setPreferredSize(new java.awt.Dimension(1280, 720));

        jPanel2.setBackground(new java.awt.Color(220, 220, 220));
        jPanel2.setPreferredSize(new java.awt.Dimension(1162, 550));

        jTVendas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTVendas);

        cbxNomeCliente.setForeground(new java.awt.Color(0, 0, 0));
        cbxNomeCliente.setToolTipText("");
        cbxNomeCliente.setCor(new java.awt.Color(255, 255, 255));

        btnFiltrarNome.setBackground(new java.awt.Color(220, 220, 220));
        btnFiltrarNome.setForeground(new java.awt.Color(30, 30, 30));
        btnFiltrarNome.setText("Filtrar");
        btnFiltrarNome.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        btnFiltrarNome.setPreferredSize(new java.awt.Dimension(62, 30));
        btnFiltrarNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFiltrarNomeActionPerformed(evt);
            }
        });

        txIdCliente.setBackground(new java.awt.Color(169, 169, 169));
        txIdCliente.setCaretColor(new java.awt.Color(169, 169, 169));
        txIdCliente.setCorTexto(new java.awt.Color(158, 158, 158));
        txIdCliente.setDicas("");
        txIdCliente.setMinimumSize(new java.awt.Dimension(64, 30));
        txIdCliente.setPreferredSize(new java.awt.Dimension(143, 30));

        btnFiltrarData.setBackground(new java.awt.Color(220, 220, 220));
        btnFiltrarData.setForeground(new java.awt.Color(30, 30, 30));
        btnFiltrarData.setText("Filtrar");
        btnFiltrarData.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        btnFiltrarData.setPreferredSize(new java.awt.Dimension(62, 30));
        btnFiltrarData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFiltrarDataActionPerformed(evt);
            }
        });

        CodigoCliente.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        CodigoCliente.setForeground(new java.awt.Color(30, 30, 30));
        CodigoCliente.setText("Id Cliente:");

        NomeCliente.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        NomeCliente.setForeground(new java.awt.Color(30, 30, 30));
        NomeCliente.setText("Nome:");

        NomeCliente1.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        NomeCliente1.setForeground(new java.awt.Color(30, 30, 30));
        NomeCliente1.setText("Data:");

        try {
            jFDate.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("####/##/##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFDate.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1026, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CodigoCliente))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(cbxNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnFiltrarNome, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(NomeCliente))
                        .addGap(346, 346, 346)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jFDate, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnFiltrarData, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(NomeCliente1))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CodigoCliente)
                    .addComponent(NomeCliente)
                    .addComponent(NomeCliente1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFiltrarNome, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFiltrarData, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(menu1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(62, 62, 62))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(menu1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE)
                .addGap(33, 33, 33))
        );

        jLayeredPane2.setLayer(jPanel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane2Layout = new javax.swing.GroupLayout(jLayeredPane2);
        jLayeredPane2.setLayout(jLayeredPane2Layout);
        jLayeredPane2Layout.setHorizontalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        jLayeredPane2Layout.setVerticalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 723, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane2)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane2)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFiltrarNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiltrarNomeActionPerformed
        filtrarClientesNome();
    }//GEN-LAST:event_btnFiltrarNomeActionPerformed

    private void btnFiltrarDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiltrarDataActionPerformed
        filtrarClientesData();
    }//GEN-LAST:event_btnFiltrarDataActionPerformed

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
            java.util.logging.Logger.getLogger(GestaoVendas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GestaoVendas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GestaoVendas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GestaoVendas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GestaoVendas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel CodigoCliente;
    private javax.swing.JLabel NomeCliente;
    private javax.swing.JLabel NomeCliente1;
    private com.mycompany.autostockcar.view.componentes.Botao btnFiltrarData;
    private com.mycompany.autostockcar.view.componentes.Botao btnFiltrarNome;
    private com.mycompany.autostockcar.view.componentes.ComboBoxPersonalizado cbxNomeCliente;
    private javax.swing.JFormattedTextField jFDate;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTVendas;
    private com.mycompany.autostockcar.view.componentes.Menu menu1;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txIdCliente;
    // End of variables declaration//GEN-END:variables

    private void Limpar() {
       // this.txTotalPagar.setText("");
        //this.txData.setText("");
       // this.cbxNomeCliente.setSelectedItem("Selecione cliente:");
        idCliente = 0;
    }

    private void CarregarTabelaVenda() throws SQLException {
        DefaultTableModel model = new DefaultTableModel();
        String sql = "{CALL ObterVendasDetalhadas()}";

        try {
            conn = conexao.obterConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            model.addColumn("N");
            model.addColumn("Cliente");
            model.addColumn("Total Pagar");
            model.addColumn("Data Venda");
            
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("clientes"),
                    rs.getDouble("total"),
                    rs.getDate("data")
                    
                });
            }
            jTVendas.setModel(model);
            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.out.println("Erro ao preencher tabela de vendas: " + e.getMessage());
        } 
        
        jTVendas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaPoint = jTVendas.rowAtPoint(e.getPoint());
                int colunaPoint = 0;
                
                if(filaPoint > -1) {
                    idVenda = (int) model.getValueAt(filaPoint, colunaPoint);
                    EnviarDadosVendaSelecionada(idVenda);
                }
            }
        });
    }
    
    private void EnviarDadosVendaSelecionada(int idVenda) {
     
        try {
            if (conn == null || conn.isClosed()) {
                conn = conexao.obterConexao();
            }
            String sql = "CALL ObterDetalhesVenda(?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idVenda);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
               // cbxNomeCliente.setSelectedItem(rs.getString("cliente"));
               // txTotalPagar.setText(rs.getString("valorPagar"));
               // txData.setText(rs.getString("dataVenda"));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Erro ao selecionar venda: " + e.getMessage());
        }
}
    
    public void filtrarClientesNome() {
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("N");
    model.addColumn("Cliente");
    model.addColumn("Total Pagar");
    model.addColumn("Data Venda");

    String nomeCliente = (String) cbxNomeCliente.getSelectedItem();
    model.setRowCount(0);
    String sql = "{CALL ObterVendasPorCliente(?)}";

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, nomeCliente);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getInt("id"),
                rs.getString("clientes"),
                rs.getDouble("total"),
                rs.getDate("data")
            });
        }
        jTVendas.setModel(model);
    } catch (SQLException ex) {
        Logger.getLogger(VendasF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void filtrarClientesData() {
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("N");
    model.addColumn("Cliente");
    model.addColumn("Total Pagar");
    model.addColumn("Data Venda");

    String data = (String) jFDate.getText();
    model.setRowCount(0);
    String sql = "{CALL ObterVendasPorData(?)}";

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, data);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getInt("id"),
                rs.getString("clientes"),
                rs.getDouble("total"),
                rs.getDate("data")
            });
        }
        jTVendas.setModel(model);
    } catch (SQLException ex) {
        Logger.getLogger(VendasF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
 
    
