package com.mycompany.autostockcar.view.formulario;

import com.mycompany.autostockcar.modelo.dao.ConsultaDao;
import com.mycompany.autostockcar.modelo.dominio.Perfil;
import com.mycompany.autostockcar.modelo.dominio.Produtos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;


public class Consulta extends javax.swing.JFrame {
    ConsultaDao consulta = new ConsultaDao();
    String caminho = "/com/mycompany/autostockcar/view/imagens/";
    
    public Consulta(String nomeUsuario, Perfil perfil) {
        initComponents();
        setLocationRelativeTo(null);
        menu1.setPaiHerdado(this);
        menu1.setNomeUsuario(nomeUsuario);
        menu1.setPerfil(perfil);
        
        btpesquisar.setIcon(new ImageIcon(getClass().getResource(caminho + "pesquisar.png")));
        btnConsultaPeloNome.setIcon(new ImageIcon(getClass().getResource(caminho + "pesquisar.png")));
        btpesquisar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BotaoPesquisar();
            }
        });
        
        cbxNome.getEditor().getEditorComponent().addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                cbxNome.setPopupVisible(true);
            }

            @Override
            public void focusLost(FocusEvent e) {
                cbxNome.setPopupVisible(false);
            }
        });
           
        
        cbxNome.getEditor().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> items = new ArrayList<>();
                String currentText = (String) (cbxNome.getEditor().getItem());
                try {
                    ResultSet result = (ResultSet) consulta.buscarProdutoNomePeloNome(currentText);
                    cbxNome.removeAllItems();
                    cbxNome.addItem("");
                    while (result.next()) {
                        items.add(result.getString("NomeProduto"));
                    }
                    Collections.sort(items);
                    for (String item : items) {
                        cbxNome.addItem(item);
                    }
                } catch (SQLException error) {
                    JOptionPane.showMessageDialog(null, "Carregar dados cbx: " + error);
                }
                SwingUtilities.invokeLater(() -> cbxNome.setPopupVisible(true));
            }
        });
        
        cbxNome.getEditor().getEditorComponent().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    String selectedProduct = (String) cbxNome.getEditor().getItem();
                    Integer productId = consulta.buscarCodigoPeloNome(selectedProduct);
                    if (productId != null) {
                        txCodigoProduto.setText(productId.toString());
                        // Atualizar outros campos do produto, se necessário
                        Produtos produto = consulta.buscarPorId(productId);
                        if (produto != null) {
                            txNomeProduto.setText(produto.getNomeProduto());
                            txCodigoFabrica.setText(produto.getCodigoFabricante());
                            txObservacoes.setText(produto.getObsProduto());
                            txCustoCompra.setText(String.valueOf(produto.getValorCustoProduto()));
                            txValorVenda.setText(String.valueOf(produto.getValorFinal()));
                            txPrateleira.setText(produto.getPrateleira());
                            txGaveta.setText(produto.getGaveta());
                            txCodigoFabricante.setText(String.valueOf(produto.getIdfabricante().getIdFabricante()));
                            txNomeFabricante.setText(String.valueOf(produto.getNomeFabricantes().getNomeFabricante()));
                            txCodigoCategoria.setText(String.valueOf(produto.getIdcategoria().getIdCategoria()));
                            txNomeCategoria.setText(String.valueOf(produto.getNomeCategorias().getNomeCategoria()));
                        }
                    } else {
                        txCodigoProduto.setText("");
                        JOptionPane.showMessageDialog(null, "Produto não encontrado.");
                    }
                }
            }
        });
        
        btnConsultaPeloNome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomeBusca = cbxNome.getSelectedItem().toString();
                Produtos produtobusca = consulta.buscarProdutosPeloNome(nomeBusca);
                
                if (produtobusca != null) {
                    txNomeProduto.setText(produtobusca.getNomeProduto());
                    txCodigoFabrica.setText(produtobusca.getCodigoFabricante());
                    txObservacoes.setText(produtobusca.getObsProduto());
                    txCustoCompra.setText(String.valueOf(produtobusca.getValorCustoProduto()));
                    txValorVenda.setText(String.valueOf(produtobusca.getValorFinal()));
                    txPrateleira.setText(produtobusca.getPrateleira());
                    txGaveta.setText(produtobusca.getGaveta());
                    txCodigoFabricante.setText(String.valueOf(produtobusca.getIdfabricante().getIdFabricante()));
                    txNomeFabricante.setText(String.valueOf(produtobusca.getNomeFabricantes().getNomeFabricante()));
                    txCodigoCategoria.setText(String.valueOf(produtobusca.getIdcategoria().getIdCategoria()));
                    txNomeCategoria.setText(String.valueOf(produtobusca.getNomeCategorias().getNomeCategoria()));
                    txQuantEstoque.setText(String.valueOf(produtobusca.getQuantidadeDisponivel()));
                    
                    cbxNome.removeAllItems();
                    cbxNome.addItem("");
                } else {
                    JOptionPane.showMessageDialog(null, "Produto não encontrado.");
                }
            }
        });
    }

    public Consulta() {
    }
    
    public void BotaoPesquisar(){
        ConsultaDao consultaDao = new ConsultaDao();
        
        try {
            int idProduto = Integer.parseInt(txCodigoProduto.getText());
            Produtos produtobusca = consultaDao.buscarPorId(idProduto);

            if (produtobusca != null) {
                txNomeProduto.setText(produtobusca.getNomeProduto());
                txCodigoFabrica.setText(produtobusca.getCodigoFabricante());
                txObservacoes.setText(produtobusca.getObsProduto());
                txCustoCompra.setText(String.valueOf(produtobusca.getValorCustoProduto()));
                txValorVenda.setText(String.valueOf(produtobusca.getValorFinal()));
                txPrateleira.setText(produtobusca.getPrateleira());
                txGaveta.setText(produtobusca.getGaveta());
                
                txCodigoFabricante.setText(String.valueOf(produtobusca.getIdfabricante().getIdFabricante()));
                txNomeFabricante.setText(String.valueOf(produtobusca.getNomeFabricantes().getNomeFabricante()));
                txCodigoCategoria.setText(String.valueOf(produtobusca.getIdcategoria().getIdCategoria()));
                txNomeCategoria.setText(String.valueOf(produtobusca.getNomeCategorias().getNomeCategoria()));
                
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum produto encontrado com o ID especificado.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Por favor, insira um Código válido.");
            ex.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        txCodigoProduto = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        jLabel1 = new javax.swing.JLabel();
        btpesquisar = new com.mycompany.autostockcar.view.componentes.Botao();
        txNomeProduto = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        txUnidade = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txCustoCompra = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        jLabel6 = new javax.swing.JLabel();
        txValorVenda = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        txCodigoFabricante = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txNomeFabricante = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        txCodigoFabrica = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        jLabel9 = new javax.swing.JLabel();
        txObservacoes = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txQuantEstoque = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        jLabel14 = new javax.swing.JLabel();
        txNomeCategoria = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        txGaveta = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        txPrateleira = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txCodigoCategoria = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        jLabel16 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        cbxNome = new com.mycompany.autostockcar.view.componentes.ComboBoxPersonalizado();
        btnConsultaPeloNome = new com.mycompany.autostockcar.view.componentes.Botao();
        menu1 = new com.mycompany.autostockcar.view.componentes.Menu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setMaximumSize(new java.awt.Dimension(1280, 720));
        jPanel1.setMinimumSize(new java.awt.Dimension(1280, 720));
        jPanel1.setPreferredSize(new java.awt.Dimension(1280, 720));

        jPanel2.setBackground(new java.awt.Color(220, 220, 220));
        jPanel2.setMaximumSize(new java.awt.Dimension(1164, 550));
        jPanel2.setMinimumSize(new java.awt.Dimension(1164, 550));
        jPanel2.setPreferredSize(new java.awt.Dimension(1164, 550));

        txCodigoProduto.setForeground(new java.awt.Color(0, 0, 0));
        txCodigoProduto.setCorTexto(new java.awt.Color(158, 158, 158));
        txCodigoProduto.setDicas("Codigo");

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Codigo");

        btpesquisar.setBackground(new java.awt.Color(220, 220, 220));
        btpesquisar.setForeground(new java.awt.Color(28, 181, 223));

        txNomeProduto.setForeground(new java.awt.Color(0, 0, 0));
        txNomeProduto.setCorTexto(new java.awt.Color(158, 158, 158));
        txNomeProduto.setDicas("Nome do produto");

        txUnidade.setForeground(new java.awt.Color(0, 0, 0));
        txUnidade.setCorTexto(new java.awt.Color(158, 158, 158));
        txUnidade.setDicas("Unidade");

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Nome");

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("UN");

        jSeparator1.setForeground(new java.awt.Color(30, 30, 30));

        jSeparator2.setForeground(new java.awt.Color(30, 30, 30));

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Dados do Produto");

        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Custo da Ultima Compra");

        txCustoCompra.setForeground(new java.awt.Color(0, 0, 0));
        txCustoCompra.setCorTexto(new java.awt.Color(158, 158, 158));
        txCustoCompra.setDicas("Custo do produto");

        jLabel6.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Valor de Venda");

        txValorVenda.setForeground(new java.awt.Color(0, 0, 0));
        txValorVenda.setCorTexto(new java.awt.Color(158, 158, 158));
        txValorVenda.setDicas("Valor Venda ");

        txCodigoFabricante.setForeground(new java.awt.Color(0, 0, 0));
        txCodigoFabricante.setCorTexto(new java.awt.Color(158, 158, 158));
        txCodigoFabricante.setDicas("Codigo Fabricante");

        jLabel7.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Fabricante");

        jLabel8.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Codigo de Fabrica");

        txNomeFabricante.setForeground(new java.awt.Color(0, 0, 0));
        txNomeFabricante.setCorTexto(new java.awt.Color(158, 158, 158));
        txNomeFabricante.setDicas("Nome Fabricante");

        txCodigoFabrica.setForeground(new java.awt.Color(0, 0, 0));
        txCodigoFabrica.setCorTexto(new java.awt.Color(158, 158, 158));
        txCodigoFabrica.setDicas("Codigo de Fabrica");

        jLabel9.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Complemento");

        txObservacoes.setForeground(new java.awt.Color(0, 0, 0));
        txObservacoes.setCorTexto(new java.awt.Color(158, 158, 158));
        txObservacoes.setDicas("Observações");

        jLabel10.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Estoque");

        jLabel11.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Quantidade");

        txQuantEstoque.setForeground(new java.awt.Color(0, 0, 0));
        txQuantEstoque.setCorTexto(new java.awt.Color(158, 158, 158));
        txQuantEstoque.setDicas("Quantidade em Estoque ");

        jLabel14.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText("Categoria");
        jLabel14.setToolTipText("");

        txNomeCategoria.setForeground(new java.awt.Color(0, 0, 0));
        txNomeCategoria.setCorTexto(new java.awt.Color(158, 158, 158));
        txNomeCategoria.setDicas("Categoria");

        txGaveta.setForeground(new java.awt.Color(0, 0, 0));
        txGaveta.setCorTexto(new java.awt.Color(158, 158, 158));
        txGaveta.setDicas("Gaveta");

        txPrateleira.setBackground(new java.awt.Color(255, 255, 255));
        txPrateleira.setForeground(new java.awt.Color(0, 0, 0));
        txPrateleira.setCorTexto(new java.awt.Color(158, 158, 158));
        txPrateleira.setDicas("Prateleira");

        jLabel13.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Gaveta");

        jLabel15.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setText("Prateleira");

        txCodigoCategoria.setForeground(new java.awt.Color(0, 0, 0));
        txCodigoCategoria.setCorTexto(new java.awt.Color(158, 158, 158));
        txCodigoCategoria.setDicas("Codigo");

        jLabel16.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setText("Codigo");
        jLabel16.setToolTipText("");

        jLabel12.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Codigo");

        cbxNome.setEditable(true);
        cbxNome.setCor(new java.awt.Color(255, 255, 255));

        btnConsultaPeloNome.setBackground(new java.awt.Color(220, 220, 220));
        btnConsultaPeloNome.setForeground(new java.awt.Color(28, 181, 223));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(18, 18, 18)
                                        .addComponent(txNomeProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel1)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(txCodigoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btpesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(32, 32, 32)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(cbxNome, javax.swing.GroupLayout.PREFERRED_SIZE, 729, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnConsultaPeloNome, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(35, 35, 35)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3)
                                            .addComponent(txUnidade, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(txCustoCompra, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(txValorVenda, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                                .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING))
                                            .addComponent(jLabel11)
                                            .addComponent(txQuantEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txPrateleira, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel15))
                                        .addGap(53, 53, 53)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(txCodigoFabrica, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(txGaveta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jLabel8)
                                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                    .addComponent(txCodigoFabricante, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                    .addComponent(jLabel12))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                    .addComponent(jLabel7)
                                                                    .addComponent(txNomeFabricante, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                        .addGap(0, 107, Short.MAX_VALUE)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel9)
                                                    .addComponent(txObservacoes, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(3, 3, 3))
                                            .addComponent(jLabel13)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(txCodigoCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                                                .addGap(21, 21, 21)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel14)
                                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addComponent(txNomeCategoria, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addGap(549, 549, 549)))))))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txUnidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbxNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btpesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txCodigoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txNomeProduto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel5)
                                .addComponent(jLabel7)
                                .addComponent(jLabel12)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txCustoCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txCodigoFabricante, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txNomeFabricante, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(22, 22, 22)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txValorVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txCodigoFabrica, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(txObservacoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(20, 20, 20)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(jLabel14)
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txQuantEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txNomeCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txCodigoCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(jLabel15))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txGaveta, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txPrateleira, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(65, 65, 65))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnConsultaPeloNome, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(menu1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1162, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(59, 59, 59))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addComponent(menu1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 564, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
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
            java.util.logging.Logger.getLogger(Consulta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Consulta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Consulta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Consulta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Consulta().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.mycompany.autostockcar.view.componentes.Botao btnConsultaPeloNome;
    private com.mycompany.autostockcar.view.componentes.Botao btpesquisar;
    private com.mycompany.autostockcar.view.componentes.ComboBoxPersonalizado cbxNome;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private com.mycompany.autostockcar.view.componentes.Menu menu1;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txCodigoCategoria;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txCodigoFabrica;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txCodigoFabricante;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txCodigoProduto;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txCustoCompra;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txGaveta;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txNomeCategoria;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txNomeFabricante;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txNomeProduto;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txObservacoes;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txPrateleira;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txQuantEstoque;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txUnidade;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txValorVenda;
    // End of variables declaration//GEN-END:variables
}
