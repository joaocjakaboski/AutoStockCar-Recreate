package com.mycompany.autostockcar.view.formulario;

import com.mycompany.autostockcar.controller.LoginController;
import com.mycompany.autostockcar.modelo.conexao.ConexaoMysql;
import com.mycompany.autostockcar.modelo.dao.CadastropDao;
import com.mycompany.autostockcar.modelo.dominio.Categorias;
import com.mycompany.autostockcar.modelo.dominio.Estoques;
import com.mycompany.autostockcar.modelo.dominio.Fabricantes;
import com.mycompany.autostockcar.modelo.dominio.Produtos;
import com.mycompany.autostockcar.view.componentes.Botao;
import com.mycompany.autostockcar.view.componentes.CampoDeTexto;
import com.mycompany.autostockcar.view.componentes.Carregar;
import com.mycompany.autostockcar.view.componentes.PanelBoard;
import com.mycompany.autostockcar.view.util.MensagemUtil;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;


public class Cadastrop extends javax.swing.JFrame {
    private Color corCampoDesativado = new Color (131, 135, 141);
    
    private MigLayout layout;
    
    String caminho = System.getProperty("user.dir") + "\\src\\main\\java\\com\\mycompany\\autostockcar\\view\\imagens\\";
   

    public Cadastrop() {
        initComponents();
        setLocationRelativeTo(null);
        menu1.setPaiHerdado(this);
        btnovo.setIcon(new ImageIcon(caminho + "pesquisar.png"));
        
        btsalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BotaoSalvar();
            }
        });
        btexcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BotaoExcluir();
            }
        });
        btnovo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BotaoPesquisar();
            }
        });
        btalterar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                BotaoAlterar();
            }   
        });
    }

    private void limparCampos() {
        txnome.setText("");
        txcodigofab.setText("");
        txcomplemento.setText("");
        txvalorcusto.setText("");
        txvalorvenda.setText("");
        txPrateleira.setText("");
        txgaveta.setText("");
        txfabricante.setText("");
        txidestoque.setText("");
        txcategoria.setText("");
        txidestoque.setText("");
        txidproduto.setText("");
    }
    @Override
    public void paintComponents(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        super.paintComponents(g);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);//para arredendar
        
    }

    public JPanel getjPanel2() {
        return jPanel2;
    }

    public void setjPanel2(JPanel jPanel2) {
        this.jPanel2 = jPanel2;
    }
    
    
    public void BotaoSalvar(){
        CadastropDao cadastropDao = new CadastropDao();

       try {
        cadastropDao.setNomeProduto(txnome.getText());
        cadastropDao.setCodigoFabricante(txcodigofab.getText());
        cadastropDao.setObsProduto(txcomplemento.getText());
        cadastropDao.setValorCustoProduto(Float.parseFloat(txvalorcusto.getText()));
        cadastropDao.setValorFinal(Float.parseFloat(txvalorvenda.getText()));
        cadastropDao.setPrateleira(txPrateleira.getText());
        cadastropDao.setGaveta(txgaveta.getText());
        cadastropDao.setIdFabricante(Integer.parseInt(txfabricante.getText()));
        cadastropDao.setIdEstoque(Integer.parseInt(txidestoque.getText()));
        cadastropDao.setIdCategoria(Integer.parseInt(txcategoria.getText()));
        
        // Após configurar todos os atributos
        cadastropDao.salvar();
        
        } catch (NumberFormatException e) {
        // Lidar com exceção de conversão de número
        JOptionPane.showMessageDialog(null, "Por favor, insira um valor numérico válido para os campos numéricos.");
        e.printStackTrace(); // Imprime o rastreamento da pilha para ajudar na depuração
        }
       
       limparCampos();
       desativarcampo();
    }
    
    public void BotaoExcluir(){
        CadastropDao cadastropDao = new CadastropDao();
        
        try{
            int idProduto = Integer.parseInt(txidproduto.getText());
            cadastropDao.excluir(idProduto);
          
        }catch(NumberFormatException e){
        JOptionPane.showMessageDialog(null, "Por favor, insira um Codigo válido.");
        e.printStackTrace();
        }
        
          limparCampos();
            desativarcampo();
    }
    
    public void BotaoPesquisar(){
        CadastropDao cadastropDao = new CadastropDao();
        
        try {
            int idProduto = Integer.parseInt(txidproduto.getText());
            Produtos produtobusca = cadastropDao.buscarPorId(idProduto);

            if (produtobusca != null) {
                txnome.setText(produtobusca.getNomeProduto());
                txcodigofab.setText(produtobusca.getCodigoFabricante());
                txcomplemento.setText(produtobusca.getObsProduto());
                txvalorcusto.setText(String.valueOf(produtobusca.getValorCustoProduto()));
                txvalorvenda.setText(String.valueOf(produtobusca.getValorFinal()));
                txPrateleira.setText(produtobusca.getPrateleira());
                txgaveta.setText(produtobusca.getGaveta());
                
                txfabricante.setText(String.valueOf(produtobusca.getIdfabricante().getIdFabricante()));
                txidestoque.setText(String.valueOf(produtobusca.getIdestoque().getIdEstoque()));
                txcategoria.setText(String.valueOf(produtobusca.getIdcategoria().getIdCategoria()));
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum produto encontrado com o ID especificado.");
            }
            desativarcampo();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Por favor, insira um Código válido.");
            ex.printStackTrace();
        }
    }
    
    public void BotaoAlterar() {
     try {
        Produtos produto = new Produtos();
        produto.setIdProduto(Integer.parseInt(txidproduto.getText()));
        produto.setNomeProduto(txnome.getText());
        produto.setCodigoFabricante(txcodigofab.getText());
        produto.setObsProduto(txcomplemento.getText());
        produto.setValorCustoProduto(new BigDecimal(txvalorcusto.getText()));
        produto.setValorFinal(new BigDecimal(txvalorvenda.getText()));
        produto.setPrateleira(txPrateleira.getText());
        produto.setGaveta(txgaveta.getText());
        
        Fabricantes fabricante = new Fabricantes();
        fabricante.setIdFabricante(Integer.parseInt(txfabricante.getText()));
        produto.setIdfabricante(fabricante);


        Estoques estoque = new Estoques();
        estoque.setIdEstoque(Integer.parseInt(txidestoque.getText()));
        produto.setIdestoque(estoque);

        Categorias categoria = new Categorias();
        categoria.setIdCategoria(Integer.parseInt(txcategoria.getText()));
        produto.setIdcategoria(categoria);


        CadastropDao cadastropDao = new CadastropDao();
        cadastropDao.alterar(produto);
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(null, "Por favor, insira valores válidos para os campos numéricos.\n" + ex.getMessage());
        ex.printStackTrace();
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(null, "Erro inesperado: " + ex.getMessage());
        ex.printStackTrace();
    }
}
    
    public void desativarcampo(){
    
    txalteradopor.setEnabled(false);
    txalteradopor.setCor(corCampoDesativado);
    txusuarioquecadastrou.setEnabled(false);
    txusuarioquecadastrou.setCor(corCampoDesativado);
}
    
    
    @SuppressWarnings("unchecked")//WARNING: Do NOT modify this code
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background = new javax.swing.JLayeredPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        txidproduto = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        ID = new javax.swing.JLabel();
        Nome = new javax.swing.JLabel();
        ValorCusto = new javax.swing.JLabel();
        ValorVenda = new javax.swing.JLabel();
        Complemento = new javax.swing.JLabel();
        txvalorcusto = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        jLabel1 = new javax.swing.JLabel();
        txnome = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        txcodigofab = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        txvalorvenda = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btsalvar = new com.mycompany.autostockcar.view.componentes.Botao();
        btalterar = new com.mycompany.autostockcar.view.componentes.Botao();
        btexcluir = new com.mycompany.autostockcar.view.componentes.Botao();
        btnovo = new com.mycompany.autostockcar.view.componentes.Botao();
        txcomplemento = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        jLabel4 = new javax.swing.JLabel();
        txPrateleira = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        jLabel5 = new javax.swing.JLabel();
        txfabricante = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txusuarioquecadastrou = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        txgaveta = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        txalteradopor = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        txcategoria = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        jLabel8 = new javax.swing.JLabel();
        txidestoque = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        btlimpar = new com.mycompany.autostockcar.view.componentes.Botao();
        menu1 = new com.mycompany.autostockcar.view.componentes.Menu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        background.setPreferredSize(new java.awt.Dimension(1280, 720));

        jPanel1.setBackground(new java.awt.Color(131, 191, 205));
        jPanel1.setPreferredSize(new java.awt.Dimension(1280, 720));

        jPanel2.setBackground(new java.awt.Color(147, 211, 225));
        jPanel2.setPreferredSize(new java.awt.Dimension(1164, 550));

        txidproduto.setForeground(new java.awt.Color(0, 0, 0));
        txidproduto.setCor(new java.awt.Color(131, 191, 205));
        txidproduto.setDicas("ID");
        txidproduto.setMinimumSize(new java.awt.Dimension(64, 30));
        txidproduto.setPreferredSize(new java.awt.Dimension(143, 30));

        ID.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        ID.setForeground(new java.awt.Color(0, 0, 0));
        ID.setText("ID:");

        Nome.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        Nome.setForeground(new java.awt.Color(30, 30, 30));
        Nome.setText("Nome:");
        Nome.setMaximumSize(new java.awt.Dimension(850, 30));
        Nome.setMinimumSize(new java.awt.Dimension(850, 30));
        Nome.setPreferredSize(new java.awt.Dimension(850, 30));

        ValorCusto.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        ValorCusto.setForeground(new java.awt.Color(30, 30, 30));
        ValorCusto.setText("Valor de Custo:");

        ValorVenda.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        ValorVenda.setForeground(new java.awt.Color(30, 30, 30));
        ValorVenda.setText("Valor de Venda:");

        Complemento.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        Complemento.setForeground(new java.awt.Color(30, 30, 30));
        Complemento.setText("Complemento:");

        txvalorcusto.setForeground(new java.awt.Color(0, 0, 0));
        txvalorcusto.setCor(new java.awt.Color(131, 191, 205));
        txvalorcusto.setDicas("Valor Custo");
        txvalorcusto.setMinimumSize(new java.awt.Dimension(64, 30));
        txvalorcusto.setPreferredSize(new java.awt.Dimension(143, 30));
        txvalorcusto.setSelectedTextColor(new java.awt.Color(0, 0, 0));

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(30, 30, 30));
        jLabel1.setText("Código Fabrica:");

        txnome.setForeground(new java.awt.Color(0, 0, 0));
        txnome.setCaretColor(new java.awt.Color(0, 0, 0));
        txnome.setCor(new java.awt.Color(131, 191, 205));
        txnome.setDicas("Nome");
        txnome.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txnome.setMinimumSize(new java.awt.Dimension(64, 30));
        txnome.setPreferredSize(new java.awt.Dimension(143, 30));
        txnome.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        txnome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txnomeActionPerformed(evt);
            }
        });

        txcodigofab.setForeground(new java.awt.Color(0, 0, 0));
        txcodigofab.setCor(new java.awt.Color(131, 191, 205));
        txcodigofab.setDicas("Cód Fábrica");
        txcodigofab.setMinimumSize(new java.awt.Dimension(64, 30));
        txcodigofab.setPreferredSize(new java.awt.Dimension(143, 30));
        txcodigofab.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        txcodigofab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txcodigofabActionPerformed(evt);
            }
        });

        txvalorvenda.setForeground(new java.awt.Color(0, 0, 0));
        txvalorvenda.setCor(new java.awt.Color(131, 191, 205));
        txvalorvenda.setDicas("Valor Venda");
        txvalorvenda.setMinimumSize(new java.awt.Dimension(64, 30));
        txvalorvenda.setPreferredSize(new java.awt.Dimension(143, 30));
        txvalorvenda.setSelectedTextColor(new java.awt.Color(0, 0, 0));

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(30, 30, 30));
        jLabel2.setText("Usuario que Cadastrou:");

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(30, 30, 30));
        jLabel3.setText("Alterado por:");

        btsalvar.setBackground(new java.awt.Color(163, 225, 255));
        btsalvar.setForeground(new java.awt.Color(30, 30, 30));
        btsalvar.setText("SALVAR");
        btsalvar.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N

        btalterar.setBackground(new java.awt.Color(163, 225, 255));
        btalterar.setForeground(new java.awt.Color(30, 30, 30));
        btalterar.setText("ALTERAR");
        btalterar.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N

        btexcluir.setBackground(new java.awt.Color(163, 225, 255));
        btexcluir.setForeground(new java.awt.Color(30, 30, 30));
        btexcluir.setText("EXCLUIR");
        btexcluir.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N

        btnovo.setBackground(new java.awt.Color(131, 191, 205));
        btnovo.setForeground(new java.awt.Color(30, 30, 30));
        btnovo.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        btnovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnovoActionPerformed(evt);
            }
        });

        txcomplemento.setForeground(new java.awt.Color(0, 0, 0));
        txcomplemento.setCor(new java.awt.Color(131, 191, 205));
        txcomplemento.setDicas(" ");
        txcomplemento.setMinimumSize(new java.awt.Dimension(456, 79));
        txcomplemento.setPreferredSize(new java.awt.Dimension(143, 30));

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(30, 30, 30));
        jLabel4.setText("Fabricante:");
        jLabel4.setToolTipText("");

        txPrateleira.setForeground(new java.awt.Color(0, 0, 0));
        txPrateleira.setCor(new java.awt.Color(131, 191, 205));
        txPrateleira.setDicas("Prateleira");
        txPrateleira.setMinimumSize(new java.awt.Dimension(64, 30));
        txPrateleira.setPreferredSize(new java.awt.Dimension(143, 30));
        txPrateleira.setSelectedTextColor(new java.awt.Color(0, 0, 0));

        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(30, 30, 30));
        jLabel5.setText("Categoria:");
        jLabel5.setToolTipText("");

        txfabricante.setForeground(new java.awt.Color(0, 0, 0));
        txfabricante.setCor(new java.awt.Color(131, 191, 205));
        txfabricante.setDicas("Fabricante");
        txfabricante.setMinimumSize(new java.awt.Dimension(64, 30));
        txfabricante.setPreferredSize(new java.awt.Dimension(143, 30));
        txfabricante.setSelectedTextColor(new java.awt.Color(0, 0, 0));

        jLabel6.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(30, 30, 30));
        jLabel6.setText("Gaveta:");

        jLabel7.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(30, 30, 30));
        jLabel7.setText("Prateleira:");

        txusuarioquecadastrou.setForeground(new java.awt.Color(0, 0, 0));
        txusuarioquecadastrou.setCor(new java.awt.Color(131, 191, 205));
        txusuarioquecadastrou.setDicas("   ");
        txusuarioquecadastrou.setMinimumSize(new java.awt.Dimension(64, 30));
        txusuarioquecadastrou.setPreferredSize(new java.awt.Dimension(143, 30));

        txgaveta.setForeground(new java.awt.Color(0, 0, 0));
        txgaveta.setCor(new java.awt.Color(131, 191, 205));
        txgaveta.setDicas("Gaveta");
        txgaveta.setMinimumSize(new java.awt.Dimension(64, 30));
        txgaveta.setPreferredSize(new java.awt.Dimension(143, 30));
        txgaveta.setSelectedTextColor(new java.awt.Color(0, 0, 0));

        txalteradopor.setForeground(new java.awt.Color(0, 0, 0));
        txalteradopor.setCor(new java.awt.Color(131, 191, 205));
        txalteradopor.setDicas("  ");
        txalteradopor.setMinimumSize(new java.awt.Dimension(64, 30));
        txalteradopor.setPreferredSize(new java.awt.Dimension(143, 30));

        txcategoria.setForeground(new java.awt.Color(0, 0, 0));
        txcategoria.setCor(new java.awt.Color(131, 191, 205));
        txcategoria.setDicas("Categoria");
        txcategoria.setMinimumSize(new java.awt.Dimension(64, 30));
        txcategoria.setPreferredSize(new java.awt.Dimension(143, 30));

        jLabel8.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(30, 30, 30));
        jLabel8.setText("ID do Estoque");

        txidestoque.setForeground(new java.awt.Color(0, 0, 0));
        txidestoque.setCor(new java.awt.Color(131, 191, 205));
        txidestoque.setDicas("  ");
        txidestoque.setMinimumSize(new java.awt.Dimension(64, 30));
        txidestoque.setPreferredSize(new java.awt.Dimension(143, 30));

        btlimpar.setBackground(new java.awt.Color(163, 225, 255));
        btlimpar.setForeground(new java.awt.Color(30, 30, 30));
        btlimpar.setText("LIMPAR");
        btlimpar.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        btlimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btlimparActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(Complemento, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(500, 500, 500)
                                .addComponent(txPrateleira, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(ValorVenda)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                            .addComponent(ID, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(259, 259, 259))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(txvalorvenda, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(txcodigofab, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                    .addComponent(txidproduto, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(18, 18, 18)
                                                    .addComponent(btnovo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGap(48, 48, 48)))
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txnome, javax.swing.GroupLayout.PREFERRED_SIZE, 850, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(txfabricante, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(txvalorcusto, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGap(118, 118, 118)
                                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(txcategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addComponent(ValorCusto, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(201, 201, 201)
                                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(txalteradopor, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txusuarioquecadastrou, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txidestoque, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(9, 9, 9)))
                                        .addComponent(Nome, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addGap(318, 318, 318)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(296, 296, 296)
                                    .addComponent(txgaveta, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(332, 332, 332)))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btlimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btexcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btalterar, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btsalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txcomplemento, javax.swing.GroupLayout.PREFERRED_SIZE, 496, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ID))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txidproduto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txnome, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnovo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txcodigofab, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txfabricante, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txusuarioquecadastrou, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txcategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ValorVenda)
                    .addComponent(ValorCusto)
                    .addComponent(jLabel7)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txvalorvenda, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txPrateleira, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txalteradopor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txvalorcusto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txgaveta, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txidestoque, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(Complemento)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txcomplemento, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btsalvar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btalterar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btexcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btlimpar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        btnovo.getAccessibleContext().setAccessibleName("");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(menu1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1162, Short.MAX_VALUE))
                .addContainerGap(59, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(menu1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        background.setLayer(jPanel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txcodigofabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txcodigofabActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txcodigofabActionPerformed

    private void txnomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txnomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txnomeActionPerformed

    private void btlimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btlimparActionPerformed
        limparCampos();
    }//GEN-LAST:event_btlimparActionPerformed

    private void btnovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnovoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnovoActionPerformed


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
            java.util.logging.Logger.getLogger(Cadastrop.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Cadastrop.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Cadastrop.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Cadastrop.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Cadastrop().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Complemento;
    private javax.swing.JLabel ID;
    private javax.swing.JLabel Nome;
    private javax.swing.JLabel ValorCusto;
    private javax.swing.JLabel ValorVenda;
    private javax.swing.JLayeredPane background;
    private com.mycompany.autostockcar.view.componentes.Botao btalterar;
    private com.mycompany.autostockcar.view.componentes.Botao btexcluir;
    private com.mycompany.autostockcar.view.componentes.Botao btlimpar;
    private com.mycompany.autostockcar.view.componentes.Botao btnovo;
    private com.mycompany.autostockcar.view.componentes.Botao btsalvar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private com.mycompany.autostockcar.view.componentes.Menu menu1;
    protected com.mycompany.autostockcar.view.componentes.CampoDeTexto txPrateleira;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txalteradopor;
    public com.mycompany.autostockcar.view.componentes.CampoDeTexto txcategoria;
    public com.mycompany.autostockcar.view.componentes.CampoDeTexto txcodigofab;
    public com.mycompany.autostockcar.view.componentes.CampoDeTexto txcomplemento;
    public com.mycompany.autostockcar.view.componentes.CampoDeTexto txfabricante;
    public com.mycompany.autostockcar.view.componentes.CampoDeTexto txgaveta;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txidestoque;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txidproduto;
    public com.mycompany.autostockcar.view.componentes.CampoDeTexto txnome;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txusuarioquecadastrou;
    public com.mycompany.autostockcar.view.componentes.CampoDeTexto txvalorcusto;
    public com.mycompany.autostockcar.view.componentes.CampoDeTexto txvalorvenda;
    // End of variables declaration//GEN-END:variables

    private Connection ConexaoMysql() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
