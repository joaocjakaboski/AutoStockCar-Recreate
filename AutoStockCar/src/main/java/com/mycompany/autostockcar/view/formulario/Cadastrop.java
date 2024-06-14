package com.mycompany.autostockcar.view.formulario;

import com.mycompany.autostockcar.modelo.dao.CadastropDao;
import com.mycompany.autostockcar.modelo.dominio.Categorias;
import com.mycompany.autostockcar.modelo.dominio.Fabricantes;
import com.mycompany.autostockcar.modelo.dominio.Perfil;
import com.mycompany.autostockcar.modelo.dominio.Produtos;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;


public class Cadastrop extends javax.swing.JFrame {
    private Color corCampoDesativado = new Color (131, 135, 141);
    
    private MigLayout layout;
    
    String caminho = System.getProperty("user.dir") + "\\src\\main\\java\\com\\mycompany\\autostockcar\\view\\imagens\\";
   

    public Cadastrop(String nomeUsuario, Perfil perfil) {
        initComponents();
        setLocationRelativeTo(null);
        menu1.setPaiHerdado(this);
        menu1.setNomeUsuario(nomeUsuario);
        menu1.setPerfil(perfil);
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

    private Cadastrop() {
    }
        
    private void limparCampos() {
        txnome.setText("");
        txcodigofab.setText("");
        txcomplemento.setText("");
        txvalorcusto.setText("");
        txvalorvenda.setText("");
        txPrateleira.setText("");
        txgaveta.setText("");
        txvalorvendasemimposto.setText("");
        txfabricante.setText("");
        txcategoria.setText("");
        txidproduto.setText("");
        txImpostoProduto.setText("");
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
        cadastropDao.setQuantidadeEstoque(Integer.parseInt(txQuantidadeEstoque.getText()));
        cadastropDao.setIdCategoria(Integer.parseInt(txcategoria.getText()));
        cadastropDao.setImpostoDoProduto(Integer.parseInt(txImpostoProduto.getText()));
        
        // Após configurar todos os atributos
        cadastropDao.salvar();
        
        } catch (NumberFormatException e) {
        // Lidar com exceção de conversão de número
        JOptionPane.showMessageDialog(null, "Por favor, insira um valor numérico válido para os campos numéricos.");
        e.printStackTrace(); // Imprime o rastreamento da pilha para ajudar na depuração
        }
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
                txImpostoProduto.setText(String.valueOf(produtobusca.getImpostoDoProduto()));
                
                txfabricante.setText(String.valueOf(produtobusca.getIdfabricante().getIdFabricante()));
                txcategoria.setText(String.valueOf(produtobusca.getIdcategoria().getIdCategoria()));
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum produto encontrado com o ID especificado.");
            }
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
        produto.setImpostoDoProduto(Integer.parseInt(txImpostoProduto.getText()));
        
        Fabricantes fabricante = new Fabricantes();
        fabricante.setIdFabricante(Integer.parseInt(txfabricante.getText()));
        produto.setIdfabricante(fabricante);


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
     BotaoPesquisar();
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
        txgaveta = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        txcategoria = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        btlimpar = new com.mycompany.autostockcar.view.componentes.Botao();
        txImpostoProduto = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        ValorCusto1 = new javax.swing.JLabel();
        txvalorvendasemimposto = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        ValorCusto2 = new javax.swing.JLabel();
        btcalcular = new com.mycompany.autostockcar.view.componentes.Botao();
        btfabricante = new com.mycompany.autostockcar.view.componentes.Botao();
        btcategoria = new com.mycompany.autostockcar.view.componentes.Botao();
        txQuantidadeEstoque = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        ValorCusto3 = new javax.swing.JLabel();
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

        txvalorvenda.setEditable(false);
        txvalorvenda.setForeground(new java.awt.Color(0, 0, 0));
        txvalorvenda.setCor(new java.awt.Color(131, 191, 205));
        txvalorvenda.setDicas("Valor Venda");
        txvalorvenda.setMinimumSize(new java.awt.Dimension(64, 30));
        txvalorvenda.setPreferredSize(new java.awt.Dimension(143, 30));
        txvalorvenda.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        txvalorvenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txvalorvendaActionPerformed(evt);
            }
        });

        btsalvar.setBackground(new java.awt.Color(131, 191, 205));
        btsalvar.setForeground(new java.awt.Color(30, 30, 30));
        btsalvar.setText("Salvar");
        btsalvar.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        btsalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btsalvarActionPerformed(evt);
            }
        });

        btalterar.setBackground(new java.awt.Color(131, 191, 205));
        btalterar.setForeground(new java.awt.Color(30, 30, 30));
        btalterar.setText("Alterar");
        btalterar.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N

        btexcluir.setBackground(new java.awt.Color(131, 191, 205));
        btexcluir.setForeground(new java.awt.Color(30, 30, 30));
        btexcluir.setText("Excluir");
        btexcluir.setToolTipText("");
        btexcluir.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N

        btnovo.setBackground(new java.awt.Color(131, 191, 205));
        btnovo.setForeground(new java.awt.Color(30, 30, 30));
        btnovo.setText("Pesquisar");
        btnovo.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N

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

        txgaveta.setForeground(new java.awt.Color(0, 0, 0));
        txgaveta.setCor(new java.awt.Color(131, 191, 205));
        txgaveta.setDicas("Gaveta");
        txgaveta.setMinimumSize(new java.awt.Dimension(64, 30));
        txgaveta.setPreferredSize(new java.awt.Dimension(143, 30));
        txgaveta.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        txgaveta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txgavetaActionPerformed(evt);
            }
        });

        txcategoria.setForeground(new java.awt.Color(0, 0, 0));
        txcategoria.setCor(new java.awt.Color(131, 191, 205));
        txcategoria.setDicas("Categoria");
        txcategoria.setMinimumSize(new java.awt.Dimension(64, 30));
        txcategoria.setPreferredSize(new java.awt.Dimension(143, 30));

        btlimpar.setBackground(new java.awt.Color(131, 191, 205));
        btlimpar.setForeground(new java.awt.Color(30, 30, 30));
        btlimpar.setText("Limpar");
        btlimpar.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        btlimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btlimparActionPerformed(evt);
            }
        });

        txImpostoProduto.setForeground(new java.awt.Color(0, 0, 0));
        txImpostoProduto.setCor(new java.awt.Color(131, 191, 205));
        txImpostoProduto.setDicas("Imposto do Produto");
        txImpostoProduto.setMinimumSize(new java.awt.Dimension(64, 30));
        txImpostoProduto.setPreferredSize(new java.awt.Dimension(143, 30));
        txImpostoProduto.setSelectedTextColor(new java.awt.Color(0, 0, 0));

        ValorCusto1.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        ValorCusto1.setForeground(new java.awt.Color(30, 30, 30));
        ValorCusto1.setText("Imposto do Produto:");

        txvalorvendasemimposto.setForeground(new java.awt.Color(0, 0, 0));
        txvalorvendasemimposto.setCor(new java.awt.Color(131, 191, 205));
        txvalorvendasemimposto.setDicas("Valor venda sem Imposto");
        txvalorvendasemimposto.setMinimumSize(new java.awt.Dimension(64, 30));
        txvalorvendasemimposto.setPreferredSize(new java.awt.Dimension(143, 30));
        txvalorvendasemimposto.setSelectedTextColor(new java.awt.Color(0, 0, 0));

        ValorCusto2.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        ValorCusto2.setForeground(new java.awt.Color(30, 30, 30));
        ValorCusto2.setText("Valor sem Imposto");

        btcalcular.setBackground(new java.awt.Color(131, 191, 205));
        btcalcular.setForeground(new java.awt.Color(30, 30, 30));
        btcalcular.setText("Soma");
        btcalcular.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        btcalcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btcalcularActionPerformed(evt);
            }
        });

        btfabricante.setBackground(new java.awt.Color(131, 191, 205));
        btfabricante.setForeground(new java.awt.Color(30, 30, 30));
        btfabricante.setText("+");
        btfabricante.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        btfabricante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btfabricanteActionPerformed(evt);
            }
        });

        btcategoria.setBackground(new java.awt.Color(131, 191, 205));
        btcategoria.setForeground(new java.awt.Color(30, 30, 30));
        btcategoria.setText("+");
        btcategoria.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        btcategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btcategoriaActionPerformed(evt);
            }
        });

        txQuantidadeEstoque.setForeground(new java.awt.Color(0, 0, 0));
        txQuantidadeEstoque.setCor(new java.awt.Color(131, 191, 205));
        txQuantidadeEstoque.setDicas("Quantidade");

        ValorCusto3.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        ValorCusto3.setForeground(new java.awt.Color(30, 30, 30));
        ValorCusto3.setText("Quantidade Estoque:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Complemento, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(txidproduto, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnovo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(19, 19, 19))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(txcodigofab, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                                .addComponent(txvalorcusto, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                                .addComponent(ValorCusto, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(ID, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(txQuantidadeEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(ValorCusto3, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(100, 100, 100)))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txnome, javax.swing.GroupLayout.PREFERRED_SIZE, 850, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txvalorvendasemimposto, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(txfabricante, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btfabricante, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(74, 74, 74)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(txcategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btcategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(txImpostoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btcalcular, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addComponent(Nome, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txgaveta, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(ValorCusto2))
                                        .addGap(100, 100, 100)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txPrateleira, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(ValorCusto1)))))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btlimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btsalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btexcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btalterar, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txvalorvenda, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ValorVenda))))
                        .addGap(58, 58, 58)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txcomplemento, javax.swing.GroupLayout.PREFERRED_SIZE, 496, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(btnovo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txcodigofab, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txfabricante, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txcategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btfabricante, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btcategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(ValorCusto2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ValorCusto1)
                            .addComponent(ValorCusto)
                            .addComponent(ValorVenda))
                        .addGap(6, 6, 6)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txvalorcusto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txvalorvendasemimposto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txImpostoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btcalcular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txvalorvenda, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6)
                            .addComponent(ValorCusto3))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txPrateleira, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txgaveta, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txQuantidadeEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(Complemento)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txcomplemento, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btalterar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btexcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btlimpar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btsalvar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
        );

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
                .addComponent(jPanel2, 557, 557, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
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

    private void txgavetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txgavetaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txgavetaActionPerformed

    private void btsalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btsalvarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btsalvarActionPerformed

    private void txvalorvendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txvalorvendaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txvalorvendaActionPerformed

    private void btcalcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btcalcularActionPerformed
        double v1 = Double.parseDouble(txvalorvendasemimposto.getText());
        double v2 = Double.parseDouble(txImpostoProduto.getText());
        double result = (v1*(v2/100)) + v1;
        txvalorvenda.setText("" + result);
        
    }//GEN-LAST:event_btcalcularActionPerformed

    private void btfabricanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btfabricanteActionPerformed
        new Fabricante().setVisible(true);
    }//GEN-LAST:event_btfabricanteActionPerformed

    private void btcategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btcategoriaActionPerformed
       new Categoria().setVisible(true);
    }//GEN-LAST:event_btcategoriaActionPerformed


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
    private javax.swing.JLabel ValorCusto1;
    private javax.swing.JLabel ValorCusto2;
    private javax.swing.JLabel ValorCusto3;
    private javax.swing.JLabel ValorVenda;
    private javax.swing.JLayeredPane background;
    private com.mycompany.autostockcar.view.componentes.Botao btalterar;
    private com.mycompany.autostockcar.view.componentes.Botao btcalcular;
    private com.mycompany.autostockcar.view.componentes.Botao btcategoria;
    private com.mycompany.autostockcar.view.componentes.Botao btexcluir;
    private com.mycompany.autostockcar.view.componentes.Botao btfabricante;
    private com.mycompany.autostockcar.view.componentes.Botao btlimpar;
    private com.mycompany.autostockcar.view.componentes.Botao btnovo;
    private com.mycompany.autostockcar.view.componentes.Botao btsalvar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private com.mycompany.autostockcar.view.componentes.Menu menu1;
    public com.mycompany.autostockcar.view.componentes.CampoDeTexto txImpostoProduto;
    protected com.mycompany.autostockcar.view.componentes.CampoDeTexto txPrateleira;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txQuantidadeEstoque;
    public com.mycompany.autostockcar.view.componentes.CampoDeTexto txcategoria;
    public com.mycompany.autostockcar.view.componentes.CampoDeTexto txcodigofab;
    public com.mycompany.autostockcar.view.componentes.CampoDeTexto txcomplemento;
    public com.mycompany.autostockcar.view.componentes.CampoDeTexto txfabricante;
    public com.mycompany.autostockcar.view.componentes.CampoDeTexto txgaveta;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txidproduto;
    public com.mycompany.autostockcar.view.componentes.CampoDeTexto txnome;
    public com.mycompany.autostockcar.view.componentes.CampoDeTexto txvalorcusto;
    public com.mycompany.autostockcar.view.componentes.CampoDeTexto txvalorvenda;
    public com.mycompany.autostockcar.view.componentes.CampoDeTexto txvalorvendasemimposto;
    // End of variables declaration//GEN-END:variables

    private Connection ConexaoMysql() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
