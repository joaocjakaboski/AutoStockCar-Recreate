
package com.mycompany.autostockcar.view.formulario;

import com.mycompany.autostockcar.modelo.conexao.Conexao;
import com.mycompany.autostockcar.modelo.conexao.ConexaoMysql;
import com.mycompany.autostockcar.modelo.dao.ClienteDao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import org.springframework.ui.Model;

public class GestaoVendas extends javax.swing.JFrame {

    private int idCliente = 0;
    private int idVenda;
    
    ClienteDao cliente = new ClienteDao();
    private Conexao conexao;
    private Connection conn;
    private PreparedStatement pstm;

    public GestaoVendas() {
        initComponents();
        setLocationRelativeTo(null);
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane2 = new javax.swing.JLayeredPane();
        jPanel1 = new javax.swing.JPanel();
        menu1 = new com.mycompany.autostockcar.view.componentes.Menu();
        jPanel2 = new javax.swing.JPanel();
        cbxNomeCliente = new com.mycompany.autostockcar.view.componentes.ComboBoxPersonalizado();
        btnSalvar1 = new com.mycompany.autostockcar.view.componentes.Botao();
        CodigoCliente4 = new javax.swing.JLabel();
        CodigoCliente5 = new javax.swing.JLabel();
        txData = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTVendas = new javax.swing.JTable();
        CodigoCliente6 = new javax.swing.JLabel();
        txTotalPagar = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(131, 191, 205));
        jPanel1.setPreferredSize(new java.awt.Dimension(1280, 720));

        jPanel2.setBackground(new java.awt.Color(147, 211, 225));
        jPanel2.setPreferredSize(new java.awt.Dimension(1162, 550));

        cbxNomeCliente.setForeground(new java.awt.Color(0, 0, 0));
        cbxNomeCliente.setToolTipText("");

        btnSalvar1.setBackground(new java.awt.Color(163, 225, 255));
        btnSalvar1.setForeground(new java.awt.Color(30, 30, 30));
        btnSalvar1.setText("Atualizar");
        btnSalvar1.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        btnSalvar1.setPreferredSize(new java.awt.Dimension(62, 30));
        btnSalvar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvar1ActionPerformed(evt);
            }
        });

        CodigoCliente4.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        CodigoCliente4.setForeground(new java.awt.Color(30, 30, 30));
        CodigoCliente4.setText("Total a Pagar:");

        CodigoCliente5.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        CodigoCliente5.setForeground(new java.awt.Color(30, 30, 30));
        CodigoCliente5.setText("Cliente:");

        txData.setActionCommand("");
        txData.setCor(new java.awt.Color(131, 191, 205));
        txData.setDicas("");
        txData.setMinimumSize(new java.awt.Dimension(64, 30));
        txData.setPreferredSize(new java.awt.Dimension(143, 30));

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
        ));
        jScrollPane1.setViewportView(jTVendas);

        CodigoCliente6.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        CodigoCliente6.setForeground(new java.awt.Color(30, 30, 30));
        CodigoCliente6.setText("Data da Venda:");

        txTotalPagar.setActionCommand("");
        txTotalPagar.setCor(new java.awt.Color(131, 191, 205));
        txTotalPagar.setDicas("");
        txTotalPagar.setMinimumSize(new java.awt.Dimension(64, 30));
        txTotalPagar.setPreferredSize(new java.awt.Dimension(143, 30));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(CodigoCliente4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txTotalPagar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(CodigoCliente6)
                                .addGap(18, 18, 18)
                                .addComponent(txData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(161, 161, 161)
                        .addComponent(CodigoCliente5)
                        .addGap(18, 18, 18)
                        .addComponent(cbxNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 809, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(btnSalvar1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(234, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(84, 84, 84)
                .addComponent(btnSalvar1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CodigoCliente5)
                    .addComponent(CodigoCliente4)
                    .addComponent(txTotalPagar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CodigoCliente6)
                    .addComponent(txData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(70, 70, 70))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menu1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(62, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(menu1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jLayeredPane2.setLayer(jPanel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane2Layout = new javax.swing.GroupLayout(jLayeredPane2);
        jLayeredPane2.setLayout(jLayeredPane2Layout);
        jLayeredPane2Layout.setHorizontalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jLayeredPane2Layout.setVerticalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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

    private void btnSalvar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvar1ActionPerformed

    }//GEN-LAST:event_btnSalvar1ActionPerformed

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
    private javax.swing.JLabel CodigoCliente4;
    private javax.swing.JLabel CodigoCliente5;
    private javax.swing.JLabel CodigoCliente6;
    private com.mycompany.autostockcar.view.componentes.Botao btnSalvar1;
    private com.mycompany.autostockcar.view.componentes.ComboBoxPersonalizado cbxNomeCliente;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTVendas;
    private com.mycompany.autostockcar.view.componentes.Menu menu1;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txData;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txTotalPagar;
    // End of variables declaration//GEN-END:variables

    private void Limpar() {
        this.txTotalPagar.setText("");
        this.txData.setText("");
        this.cbxNomeCliente.setSelectedItem("Selecione cliente:");
        idCliente = 0;
    }

    private void CarregarTabelaVenda() throws SQLException {
        DefaultTableModel model = new DefaultTableModel();
        String sql = "SELECT cv.IdVenda AS id, c.NomeCliente AS clientes, " 
                   + "cv.ValorTotalVenda AS total, cv.DataCompra AS data "
                   + "FROM vendas AS cv, clientes AS c "
                   + "WHERE cv.IdCliente = c.IdCliente";

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
            String sql = "SELECT cv.ValorTotalVenda AS valorPagar, c.NomeCliente AS cliente, "
                       + "cv.DataCompra AS dataVenda "
                       + "FROM vendas AS cv, clientes AS c "
                       + "WHERE cv.IdVenda = ? AND cv.IdCliente = c.IdCliente";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idVenda);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                cbxNomeCliente.setSelectedItem(rs.getString("cliente"));
                txTotalPagar.setText(rs.getString("valorPagar"));
                txData.setText(rs.getString("dataVenda"));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Erro ao selecionar venda: " + e.getMessage());
        }
}
}
 
    
