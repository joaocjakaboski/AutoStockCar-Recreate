package com.mycompany.autostockcar.view.formulario;

import com.mycompany.autostockcar.modelo.dao.CategoriaDao;
import com.mycompany.autostockcar.modelo.dominio.Categorias;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Categoria extends javax.swing.JFrame {

    public Categoria() {
        initComponents();
        setLocationRelativeTo(null);
        BtNovo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BotaoNovo();
            }
        });
        BtExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BotaoExcluir();
            }
        });
        BtPesquisar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BotaoPesquisar();
            }
        });
    }
    
    public void LimparCampos(){
        TxtNome.setText("");
        TxtDescricao.setText("");
        TxtCodigo.setText("");
    }

    public void BotaoNovo(){
        CategoriaDao categoriaDao = new CategoriaDao();
        
        try{
        categoriaDao.setNomeCategoria(TxtNome.getText());
        categoriaDao.setDescricao(TxtDescricao.getText());
            
        categoriaDao.salvar();
        }catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Por favor, insira um valor numérico válido para os campos numéricos.");
        e.printStackTrace();
        } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Erro ao salvar a categoria no banco de dados");
        e.printStackTrace();
        }finally{
            LimparCampos();
        }
    }
    
    public void BotaoExcluir(){
        CategoriaDao categoriaDao = new CategoriaDao();
        
        try{
            int idCategoria = Integer.parseInt(TxtCodigo.getText());
            categoriaDao.excluir(idCategoria);
          
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Por favor, insira um Codigo válido.");
            e.printStackTrace();
        }
        LimparCampos();
    }
    
    public void BotaoPesquisar(){
            CategoriaDao categoriaDao = new CategoriaDao();

            try{
                int idCategoria = Integer.parseInt(TxtCodigo.getText());
                Categorias categoriabusca = categoriaDao.buscarPorId(idCategoria);

                if (categoriabusca != null) {
                    TxtNome.setText(categoriabusca.getNomeCategoria());
                    TxtDescricao.setText(categoriabusca.getDescricaoCategoria());
                    
            }else {
                    JOptionPane.showMessageDialog(null, "Nenhum produto encontrado com o ID especificado.");
            }
        }catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Por favor, insira um Código válido.");
            ex.printStackTrace();
        }      
            
    }
        
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        TxtCodigo = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        BtPesquisar = new com.mycompany.autostockcar.view.componentes.Botao();
        jLabel2 = new javax.swing.JLabel();
        TxtNome = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        jLabel3 = new javax.swing.JLabel();
        BtExcluir = new com.mycompany.autostockcar.view.componentes.Botao();
        BtNovo = new com.mycompany.autostockcar.view.componentes.Botao();
        jLabel5 = new javax.swing.JLabel();
        TxtDescricao = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        BtNovo1 = new com.mycompany.autostockcar.view.componentes.Botao();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(131, 191, 205));

        jPanel2.setBackground(new java.awt.Color(147, 211, 225));

        jPanel3.setBackground(new java.awt.Color(0, 204, 204));

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Cadastro de Categorias");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(225, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(225, 225, 225))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel1)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        TxtCodigo.setForeground(new java.awt.Color(0, 0, 0));
        TxtCodigo.setCor(new java.awt.Color(110, 202, 224));
        TxtCodigo.setDicas("Código");
        TxtCodigo.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        TxtCodigo.setPreferredSize(new java.awt.Dimension(180, 30));
        TxtCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtCodigoActionPerformed(evt);
            }
        });

        BtPesquisar.setBackground(new java.awt.Color(131, 191, 205));
        BtPesquisar.setForeground(new java.awt.Color(0, 0, 0));
        BtPesquisar.setText("Pesquisar");
        BtPesquisar.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        BtPesquisar.setMaximumSize(new java.awt.Dimension(65, 30));
        BtPesquisar.setMinimumSize(new java.awt.Dimension(65, 30));
        BtPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtPesquisarActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Codigo");

        TxtNome.setForeground(new java.awt.Color(0, 0, 0));
        TxtNome.setCor(new java.awt.Color(110, 202, 224));
        TxtNome.setDicas("Nome da Categoria");
        TxtNome.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        TxtNome.setPreferredSize(new java.awt.Dimension(180, 30));

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Nome");

        BtExcluir.setBackground(new java.awt.Color(131, 191, 205));
        BtExcluir.setForeground(new java.awt.Color(0, 0, 0));
        BtExcluir.setText("Excluir");
        BtExcluir.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        BtExcluir.setMaximumSize(new java.awt.Dimension(65, 30));
        BtExcluir.setMinimumSize(new java.awt.Dimension(65, 30));
        BtExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtExcluirActionPerformed(evt);
            }
        });

        BtNovo.setBackground(new java.awt.Color(131, 191, 205));
        BtNovo.setForeground(new java.awt.Color(0, 0, 0));
        BtNovo.setText("Novo");
        BtNovo.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        BtNovo.setMaximumSize(new java.awt.Dimension(65, 30));
        BtNovo.setMinimumSize(new java.awt.Dimension(65, 30));
        BtNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtNovoActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Descrição");

        TxtDescricao.setForeground(new java.awt.Color(0, 0, 0));
        TxtDescricao.setCor(new java.awt.Color(110, 202, 224));
        TxtDescricao.setDicas(" ");
        TxtDescricao.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        TxtDescricao.setPreferredSize(new java.awt.Dimension(180, 30));
        TxtDescricao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtDescricaoActionPerformed(evt);
            }
        });

        BtNovo1.setBackground(new java.awt.Color(131, 191, 205));
        BtNovo1.setForeground(new java.awt.Color(0, 0, 0));
        BtNovo1.setText("Limpar");
        BtNovo1.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        BtNovo1.setMaximumSize(new java.awt.Dimension(65, 30));
        BtNovo1.setMinimumSize(new java.awt.Dimension(65, 30));
        BtNovo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtNovo1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TxtDescricao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(BtNovo1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(BtExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(BtNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TxtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, 0)
                                .addComponent(jLabel2)))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(TxtNome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TxtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TxtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(TxtDescricao, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtNovo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtNovo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtPesquisarActionPerformed

    }//GEN-LAST:event_BtPesquisarActionPerformed

    private void TxtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtCodigoActionPerformed

    private void BtExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtExcluirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtExcluirActionPerformed

    private void BtNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtNovoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtNovoActionPerformed

    private void TxtDescricaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtDescricaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtDescricaoActionPerformed

    private void BtNovo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtNovo1ActionPerformed
       LimparCampos();
       
    }//GEN-LAST:event_BtNovo1ActionPerformed

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
            java.util.logging.Logger.getLogger(Categoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Categoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Categoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Categoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Categoria().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.mycompany.autostockcar.view.componentes.Botao BtExcluir;
    private com.mycompany.autostockcar.view.componentes.Botao BtNovo;
    private com.mycompany.autostockcar.view.componentes.Botao BtNovo1;
    private com.mycompany.autostockcar.view.componentes.Botao BtPesquisar;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto TxtCodigo;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto TxtDescricao;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto TxtNome;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    // End of variables declaration//GEN-END:variables
}
