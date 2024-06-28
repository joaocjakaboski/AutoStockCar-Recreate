package com.mycompany.autostockcar.view.formulario;

import com.mycompany.autostockcar.modelo.dao.CadastropDao;
import com.mycompany.autostockcar.modelo.dao.MovimentacaoProdDao;
import com.mycompany.autostockcar.modelo.dominio.MovimentacaoDeEstoque;
import com.mycompany.autostockcar.modelo.dominio.Produtos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;


public class MovimentacaoProd extends javax.swing.JFrame {

   
    public MovimentacaoProd() {
        initComponents();
        setLocationRelativeTo(null);
        grupo.add(radioe);
        grupo.add(radios);
        
        btPesquisa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BotaoPesquisar();
            }
        });
    }
    
public void BotaoPesquisar() {
    MovimentacaoProdDao movimentacaoDao = new MovimentacaoProdDao();
    
    try {
        int idMovimentacao = Integer.parseInt(txId.getText());
        MovimentacaoDeEstoque movimentacao = movimentacaoDao.buscarPorId(idMovimentacao);

        if (movimentacao != null) {
            txNomeProduto.setText(movimentacao.getProduto().getNomeProduto()); // Obtém o nome do produto
            txEstoqueAtual.setText(String.valueOf(movimentacao.getQuantidadeMovimentacao()));
            txMotivodaMovimentacao.setText(movimentacao.getMotivoMovimentacao());
            txAjustedeEstoque.setText(String.valueOf(movimentacao.getQuantidadeMovimentacao()));
            txIdFabricante.setText(String.valueOf(movimentacao.getFabricante().getIdFabricante()));
        } else {
            JOptionPane.showMessageDialog(null, "Nenhuma movimentação encontrada com o ID especificado.\n" );
        }
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(null, "Por favor, insira um ID válido.");
        ex.printStackTrace();
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(null, "Erro ao buscar movimentação: " + ex.getMessage());
        ex.printStackTrace();
    }
}
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grupo = new javax.swing.ButtonGroup();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        txId = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txMotivodaMovimentacao = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        jLabel3 = new javax.swing.JLabel();
        txNomeProduto = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        jLabel4 = new javax.swing.JLabel();
        txEstoqueAtual = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        jLabel5 = new javax.swing.JLabel();
        txAjustedeEstoque = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        botao1 = new com.mycompany.autostockcar.view.componentes.Botao();
        radioe = new javax.swing.JRadioButton();
        radios = new javax.swing.JRadioButton();
        btPesquisa = new com.mycompany.autostockcar.view.componentes.Botao();
        txIdFabricante = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLayeredPane1.setBackground(new java.awt.Color(131, 191, 205));
        jLayeredPane1.setPreferredSize(new java.awt.Dimension(624, 375));

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setPreferredSize(new java.awt.Dimension(624, 375));

        jPanel2.setBackground(new java.awt.Color(220, 220, 220));
        jPanel2.setPreferredSize(new java.awt.Dimension(566, 322));

        txId.setCor(new java.awt.Color(255, 255, 255));
        txId.setDicas("ID");
        txId.setFont(new java.awt.Font("sanserif", 1, 12)); // NOI18N
        txId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txIdActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(30, 30, 30));
        jLabel1.setText("ID:");

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(30, 30, 30));
        jLabel2.setText("Nome Produto:");

        txMotivodaMovimentacao.setCor(new java.awt.Color(255, 255, 255));
        txMotivodaMovimentacao.setDicas("");
        txMotivodaMovimentacao.setFont(new java.awt.Font("sanserif", 1, 12)); // NOI18N
        txMotivodaMovimentacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txMotivodaMovimentacaoActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(30, 30, 30));
        jLabel3.setText("Motivo da Movimentação:");

        txNomeProduto.setCor(new java.awt.Color(255, 255, 255));
        txNomeProduto.setDicas("");
        txNomeProduto.setFont(new java.awt.Font("sanserif", 1, 12)); // NOI18N
        txNomeProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txNomeProdutoActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(30, 30, 30));
        jLabel4.setText("Estoque Atual:");

        txEstoqueAtual.setCor(new java.awt.Color(255, 255, 255));
        txEstoqueAtual.setDicas("");
        txEstoqueAtual.setFont(new java.awt.Font("sanserif", 1, 12)); // NOI18N
        txEstoqueAtual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txEstoqueAtualActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(30, 30, 30));
        jLabel5.setText("Ajuste de Estoque:");

        txAjustedeEstoque.setCor(new java.awt.Color(255, 255, 255));
        txAjustedeEstoque.setDicas("");
        txAjustedeEstoque.setFont(new java.awt.Font("sanserif", 1, 12)); // NOI18N
        txAjustedeEstoque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txAjustedeEstoqueActionPerformed(evt);
            }
        });

        botao1.setBackground(new java.awt.Color(220, 220, 220));
        botao1.setForeground(new java.awt.Color(30, 30, 30));
        botao1.setText("SALVAR");
        botao1.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        botao1.setPreferredSize(new java.awt.Dimension(62, 30));

        radioe.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        radioe.setForeground(new java.awt.Color(30, 30, 30));
        radioe.setText("Entrada");

        radios.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        radios.setForeground(new java.awt.Color(30, 30, 30));
        radios.setText("Saída");

        btPesquisa.setBackground(new java.awt.Color(220, 220, 220));

        txIdFabricante.setCor(new java.awt.Color(255, 255, 255));
        txIdFabricante.setDicas("");
        txIdFabricante.setFont(new java.awt.Font("sanserif", 1, 12)); // NOI18N
        txIdFabricante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txIdFabricanteActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(30, 30, 30));
        jLabel6.setText("ID Fabricante");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(botao1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(txEstoqueAtual, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(txAjustedeEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txId, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txNomeProduto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel2)
                                            .addComponent(txMotivodaMovimentacao, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 2, Short.MAX_VALUE)))
                                .addContainerGap(36, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txIdFabricante, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(radios, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(radioe, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(1, 1, 1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txId, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txEstoqueAtual, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txNomeProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txMotivodaMovimentacao, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txAjustedeEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txIdFabricante, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(radioe)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(radios)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(102, 102, 102)
                                .addComponent(botao1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(0, 0, Short.MAX_VALUE)))
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

        jLayeredPane1.setLayer(jPanel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txIdActionPerformed

    private void txMotivodaMovimentacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txMotivodaMovimentacaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txMotivodaMovimentacaoActionPerformed

    private void txNomeProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txNomeProdutoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txNomeProdutoActionPerformed

    private void txEstoqueAtualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txEstoqueAtualActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txEstoqueAtualActionPerformed

    private void txAjustedeEstoqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txAjustedeEstoqueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txAjustedeEstoqueActionPerformed

    private void txIdFabricanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txIdFabricanteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txIdFabricanteActionPerformed

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
            java.util.logging.Logger.getLogger(MovimentacaoProd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MovimentacaoProd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MovimentacaoProd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MovimentacaoProd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MovimentacaoProd().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.mycompany.autostockcar.view.componentes.Botao botao1;
    private com.mycompany.autostockcar.view.componentes.Botao btPesquisa;
    private javax.swing.ButtonGroup grupo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton radioe;
    private javax.swing.JRadioButton radios;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txAjustedeEstoque;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txEstoqueAtual;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txId;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txIdFabricante;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txMotivodaMovimentacao;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txNomeProduto;
    // End of variables declaration//GEN-END:variables
}
