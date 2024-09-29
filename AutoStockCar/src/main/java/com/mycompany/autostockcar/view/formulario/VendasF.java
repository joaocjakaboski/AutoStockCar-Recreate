package com.mycompany.autostockcar.view.formulario;

import com.mycompany.autostockcar.controller.RegistrarVendaController;
import com.mycompany.autostockcar.modelo.conexao.Conexao;
import com.mycompany.autostockcar.modelo.conexao.ConexaoMysql;
import com.mycompany.autostockcar.modelo.dao.CadastropDao;
import com.mycompany.autostockcar.modelo.dao.ClienteDao;
import com.mycompany.autostockcar.modelo.dao.VendasDao;
import com.mycompany.autostockcar.modelo.dominio.ItensVenda;
import com.mycompany.autostockcar.modelo.dominio.Perfil;
import com.mycompany.autostockcar.modelo.dominio.Produtos;
import com.mycompany.autostockcar.modelo.dominio.Vendas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import org.json.JSONObject;

public class VendasF extends javax.swing.JFrame {

    private DefaultTableModel modeloDadosProdutos;
    ArrayList<ItensVenda> listaProdutos = new ArrayList<>();
    private ItensVenda produtos;

    private int idCliente=0;
    private int idProduto = 0;
    private String nome = "";
    private double quantidadeProduto = 0.0;
    private BigDecimal precoUnitario = BigDecimal.ZERO;
    private double porcentagemIva = 0.0;
    private int quantidade = 0;
    private BigDecimal subtotal = BigDecimal.ZERO;
    private BigDecimal desconto = BigDecimal.ZERO;
    private BigDecimal imposto = BigDecimal.ZERO;
    private BigDecimal totalPagar = BigDecimal.ZERO;
    private int auxIdDetalhe = 0;
    
    //variáveis cálculos globais
    private BigDecimal subTotalGeneral = BigDecimal.ZERO;
    private BigDecimal descontoGeneral = BigDecimal.ZERO;
    private BigDecimal impostoGeneral = BigDecimal.ZERO;
    private BigDecimal totalPagarGeneral = BigDecimal.ZERO;
    

    
    private Conexao conexao;
    private PreparedStatement pstm;

    ClienteDao cliente = new ClienteDao();
    CadastropDao produto = new CadastropDao();
    VendasDao vendas = new VendasDao();


    public VendasF(String nomeUsuario, Perfil perfil) {
        initComponents();
        setLocationRelativeTo(null);
        menu1.setPaiHerdado(this);
        menu1.setNomeUsuario(nomeUsuario);
        menu1.setPerfil(perfil);
        this.inicializarTabelaProdutos();
        
        txSubTotal.setText("0.0");
        txDesconto.setText("0.0");
        txTotal.setText("0.0");
        
        txIdCliente.setEditable(false);
        txIdProduto.setEditable(false);
        txSubTotal.setEditable(false);
        txTotal.setEditable(false);
        txQuantidadeDisponivel.setEditable(false);
    
        this.conexao = new ConexaoMysql();

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
        
        cbxNomeCliente.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (cbxNomeCliente.getSelectedItem() != null && !cbxNomeCliente.getSelectedItem().toString().isEmpty()) {
                try {
                    obterIdCliente();
                    txIdCliente.setText(String.valueOf(idCliente)); // txIdCliente é o campo onde o ID será exibido
                } catch (SQLException error) {
                    JOptionPane.showMessageDialog(null, "Erro ao obter ID do cliente: " + error);
                }
            }
        }
    });

        cbxNomeProduto.setEditable(true);
        cbxNomeProduto.removeAllItems();
        cbxNomeProduto.addItem("");

        cbxNomeProduto.getEditor().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Produtos> produtos = produto.buscarProdutosPeloNome((String) cbxNomeProduto.getEditor().getItem());
                cbxNomeProduto.removeAllItems();
                cbxNomeProduto.addItem("");
                for (Produtos p : produtos) {
                    cbxNomeProduto.addItem(p);
                }
                SwingUtilities.invokeLater(() -> cbxNomeProduto.setPopupVisible(true));
            }
        });
        
        cbxNomeProduto.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (cbxNomeProduto.getSelectedItem() != null && !cbxNomeProduto.getSelectedItem().toString().isEmpty()) {
                try {
                    obterIdProduto();
                    txIdProduto.setText(String.valueOf(idProduto)); // txIdCliente é o campo onde o ID será exibido
                    double quantidadeDisponivelDouble = obterQuantidadeDisponivel(idProduto);
                    int quantidadeDisponivel = (int) Math.round(quantidadeDisponivelDouble);
                    boolean alterado = false;
                    // Atualiza o JTextField com a quantidade convertida
                    if (!listaProdutos.isEmpty()) {
                        for (ItensVenda auxProduto : listaProdutos) {
                            if (idProduto == auxProduto.getIdProduto()) {
                                txQuantidadeDisponivel.setText(String.valueOf(quantidadeDisponivel - auxProduto.getQuantidadeItensVenda()));
                                alterado = true;
                            }
                        }
                    }
                    if (!alterado) {
                        txQuantidadeDisponivel.setText(String.valueOf(quantidadeDisponivel)); 
                    }
                } catch (SQLException error) {
                    JOptionPane.showMessageDialog(null, "Erro ao obter ID do cliente: " + error);
                }
            }
        }
    });
    }

    public VendasF() {
    }
    
    

    private void inicializarTabelaProdutos() {
        modeloDadosProdutos = new DefaultTableModel();

        modeloDadosProdutos.addColumn("N");
        modeloDadosProdutos.addColumn("Código Item");
        modeloDadosProdutos.addColumn("Descrição");
        modeloDadosProdutos.addColumn("Quantidade");
        modeloDadosProdutos.addColumn("Valor Parcial");
        modeloDadosProdutos.addColumn("Desconto");
        modeloDadosProdutos.addColumn("SubTotal");
       // modeloDadosProdutos.addColumn("Valor Total");

        this.jtProdutos.setModel(modeloDadosProdutos);
        this.jtProdutos.removeEditor();
    }
    
    public void listaTabelaProdutos(){
        this.modeloDadosProdutos.setRowCount(listaProdutos.size());
        for(int i =0; i<listaProdutos.size();i++){
            this.modeloDadosProdutos.setValueAt(i+1, i, 0);
            this.modeloDadosProdutos.setValueAt(listaProdutos.get(i).getIdProduto(), i, 1);
            this.modeloDadosProdutos.setValueAt(listaProdutos.get(i).getProduto(), i, 2);
            this.modeloDadosProdutos.setValueAt(listaProdutos.get(i).getQuantidadeItensVenda(), i, 3);
            this.modeloDadosProdutos.setValueAt(listaProdutos.get(i).getPrecoUnitario(), i, 4);
            this.modeloDadosProdutos.setValueAt(listaProdutos.get(i).getDesconto(), i, 5);
            this.modeloDadosProdutos.setValueAt(listaProdutos.get(i).getTotalAPagar(), i, 6);
            //this.modeloDadosProdutos.setValueAt("Eliminar", i, 7);
        }
        jtProdutos.setModel(modeloDadosProdutos);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        NomeCliente1 = new javax.swing.JLabel();
        NomeCliente2 = new javax.swing.JLabel();
        NomeCliente3 = new javax.swing.JLabel();
        NomeCliente4 = new javax.swing.JLabel();
        NomeCliente5 = new javax.swing.JLabel();
        NomeCliente6 = new javax.swing.JLabel();
        NomeCliente7 = new javax.swing.JLabel();
        NomeCliente8 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator1 = new javax.swing.JSeparator();
        CodigoCliente = new javax.swing.JLabel();
        NomeCliente = new javax.swing.JLabel();
        txIdCliente = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        txIdProduto = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        txSubTotal = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        txcodigocliente4 = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        txnome3 = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        CodigoCliente1 = new javax.swing.JLabel();
        CodigoCliente4 = new javax.swing.JLabel();
        CodigoCliente5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtProdutos = new javax.swing.JTable();
        CodigoCliente7 = new javax.swing.JLabel();
        txDesconto = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        CodigoCliente9 = new javax.swing.JLabel();
        txTotal = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        btnSalvar = new com.mycompany.autostockcar.view.componentes.Botao();
        jLabel1 = new javax.swing.JLabel();
        txQuantidadeDisponivel = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        cbxNomeCliente = new com.mycompany.autostockcar.view.componentes.ComboBoxPersonalizado();
        btnAdicionar = new com.mycompany.autostockcar.view.componentes.Botao();
        cbxNomeProduto = new com.mycompany.autostockcar.view.componentes.ComboBoxPersonalizado();
        btnAplicar = new com.mycompany.autostockcar.view.componentes.Botao();
        btnSalvar1 = new com.mycompany.autostockcar.view.componentes.Botao();
        txQuantidade = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        jLabel2 = new javax.swing.JLabel();
        menu1 = new com.mycompany.autostockcar.view.componentes.Menu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(new java.awt.Dimension(1280, 720));

        jLayeredPane1.setPreferredSize(new java.awt.Dimension(1280, 720));

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setPreferredSize(new java.awt.Dimension(1280, 720));

        jPanel2.setBackground(new java.awt.Color(220, 220, 220));
        jPanel2.setPreferredSize(new java.awt.Dimension(1164, 550));

        jPanel3.setBackground(new java.awt.Color(220, 220, 220));
        jPanel3.setPreferredSize(new java.awt.Dimension(1055, 192));

        NomeCliente1.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        NomeCliente1.setForeground(new java.awt.Color(30, 30, 30));
        NomeCliente1.setText("Cod Item:");

        NomeCliente2.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        NomeCliente2.setForeground(new java.awt.Color(30, 30, 30));
        NomeCliente2.setText("Descricao:");

        NomeCliente3.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        NomeCliente3.setForeground(new java.awt.Color(30, 30, 30));
        NomeCliente3.setText("UND");

        NomeCliente4.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        NomeCliente4.setForeground(new java.awt.Color(30, 30, 30));
        NomeCliente4.setText("Valor::");

        NomeCliente5.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        NomeCliente5.setForeground(new java.awt.Color(30, 30, 30));
        NomeCliente5.setText("Desconto:");

        NomeCliente6.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        NomeCliente6.setForeground(new java.awt.Color(30, 30, 30));
        NomeCliente6.setText("Estoque a disposicao:");

        NomeCliente7.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        NomeCliente7.setForeground(new java.awt.Color(30, 30, 30));
        NomeCliente7.setText("Valor total:");

        NomeCliente8.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        NomeCliente8.setForeground(new java.awt.Color(30, 30, 30));
        NomeCliente8.setText("Nome:");

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 1055, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(NomeCliente1)
                .addGap(3, 3, 3)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65)
                .addComponent(NomeCliente2)
                .addGap(88, 88, 88)
                .addComponent(NomeCliente3)
                .addGap(52, 52, 52)
                .addComponent(NomeCliente6)
                .addGap(98, 98, 98)
                .addComponent(NomeCliente4)
                .addGap(468, 468, 468)
                .addComponent(NomeCliente5)
                .addGap(68, 68, 68)
                .addComponent(NomeCliente7)
                .addGap(67, 67, 67)
                .addComponent(NomeCliente8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(NomeCliente1)
                            .addComponent(NomeCliente2)
                            .addComponent(NomeCliente3)
                            .addComponent(NomeCliente4)
                            .addComponent(NomeCliente5)
                            .addComponent(NomeCliente7)
                            .addComponent(NomeCliente8)
                            .addComponent(NomeCliente6))))
                .addGap(251, 251, 251)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        CodigoCliente.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        CodigoCliente.setForeground(new java.awt.Color(30, 30, 30));
        CodigoCliente.setText("Id Cliente:");

        NomeCliente.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        NomeCliente.setForeground(new java.awt.Color(30, 30, 30));
        NomeCliente.setText("Nome:");

        txIdCliente.setBackground(new java.awt.Color(169, 169, 169));
        txIdCliente.setCaretColor(new java.awt.Color(169, 169, 169));
        txIdCliente.setCorTexto(new java.awt.Color(158, 158, 158));
        txIdCliente.setDicas("");
        txIdCliente.setMinimumSize(new java.awt.Dimension(64, 30));
        txIdCliente.setPreferredSize(new java.awt.Dimension(143, 30));

        txIdProduto.setBackground(new java.awt.Color(169, 169, 169));
        txIdProduto.setCorTexto(new java.awt.Color(158, 158, 158));
        txIdProduto.setDicas("");
        txIdProduto.setMinimumSize(new java.awt.Dimension(64, 30));
        txIdProduto.setPreferredSize(new java.awt.Dimension(143, 30));

        txSubTotal.setActionCommand("");
        txSubTotal.setCorTexto(new java.awt.Color(158, 158, 158));
        txSubTotal.setDicas("");
        txSubTotal.setMinimumSize(new java.awt.Dimension(64, 30));
        txSubTotal.setPreferredSize(new java.awt.Dimension(143, 30));

        txcodigocliente4.setForeground(new java.awt.Color(131, 191, 205));
        txcodigocliente4.setText("campoDeTexto1");
        txcodigocliente4.setCor(new java.awt.Color(131, 191, 205));
        txcodigocliente4.setMinimumSize(new java.awt.Dimension(64, 30));
        txcodigocliente4.setPreferredSize(new java.awt.Dimension(143, 30));

        txnome3.setForeground(new java.awt.Color(131, 191, 205));
        txnome3.setText("campoDeTexto1");
        txnome3.setCor(new java.awt.Color(131, 191, 205));
        txnome3.setMinimumSize(new java.awt.Dimension(64, 30));
        txnome3.setPreferredSize(new java.awt.Dimension(143, 30));

        CodigoCliente1.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        CodigoCliente1.setForeground(new java.awt.Color(30, 30, 30));
        CodigoCliente1.setText("Id Produto:");

        CodigoCliente4.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        CodigoCliente4.setForeground(new java.awt.Color(30, 30, 30));
        CodigoCliente4.setText("SubTotal:");

        CodigoCliente5.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        CodigoCliente5.setForeground(new java.awt.Color(30, 30, 30));
        CodigoCliente5.setText("Produto:");

        jtProdutos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cód . Item", "Descrição", "UND", "Estoque", "Quantidade"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtProdutos.setShowHorizontalLines(true);
        jtProdutos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtProdutosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtProdutos);

        CodigoCliente7.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        CodigoCliente7.setForeground(new java.awt.Color(30, 30, 30));
        CodigoCliente7.setText("Desconto:");

        txDesconto.setActionCommand("");
        txDesconto.setCorTexto(new java.awt.Color(158, 158, 158));
        txDesconto.setDicas("");
        txDesconto.setMinimumSize(new java.awt.Dimension(64, 30));
        txDesconto.setPreferredSize(new java.awt.Dimension(143, 30));
        txDesconto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txDescontoKeyTyped(evt);
            }
        });

        CodigoCliente9.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        CodigoCliente9.setForeground(new java.awt.Color(30, 30, 30));
        CodigoCliente9.setText("Total:");

        txTotal.setActionCommand("");
        txTotal.setCorTexto(new java.awt.Color(158, 158, 158));
        txTotal.setDicas("");
        txTotal.setMinimumSize(new java.awt.Dimension(64, 30));
        txTotal.setPreferredSize(new java.awt.Dimension(143, 30));

        btnSalvar.setBackground(new java.awt.Color(220, 220, 220));
        btnSalvar.setForeground(new java.awt.Color(30, 30, 30));
        btnSalvar.setText("Salvar");
        btnSalvar.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        btnSalvar.setPreferredSize(new java.awt.Dimension(62, 30));
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(30, 30, 30));
        jLabel1.setText("Qtd:");

        txQuantidadeDisponivel.setBackground(new java.awt.Color(169, 169, 169));
        txQuantidadeDisponivel.setActionCommand("");
        txQuantidadeDisponivel.setCorTexto(new java.awt.Color(158, 158, 158));
        txQuantidadeDisponivel.setDicas("");
        txQuantidadeDisponivel.setMinimumSize(new java.awt.Dimension(64, 30));
        txQuantidadeDisponivel.setPreferredSize(new java.awt.Dimension(143, 30));

        cbxNomeCliente.setForeground(new java.awt.Color(0, 0, 0));
        cbxNomeCliente.setToolTipText("");
        cbxNomeCliente.setCor(new java.awt.Color(255, 255, 255));

        btnAdicionar.setBackground(new java.awt.Color(220, 220, 220));
        btnAdicionar.setForeground(new java.awt.Color(30, 30, 30));
        btnAdicionar.setText("Adicionar");
        btnAdicionar.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });

        cbxNomeProduto.setForeground(java.awt.Color.black);
        cbxNomeProduto.setToolTipText("");
        cbxNomeProduto.setCor(new java.awt.Color(255, 255, 255));

        btnAplicar.setBackground(new java.awt.Color(220, 220, 220));
        btnAplicar.setForeground(new java.awt.Color(30, 30, 30));
        btnAplicar.setText("Aplicar");
        btnAplicar.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        btnAplicar.setPreferredSize(new java.awt.Dimension(62, 30));
        btnAplicar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAplicarActionPerformed(evt);
            }
        });

        btnSalvar1.setBackground(new java.awt.Color(220, 220, 220));
        btnSalvar1.setForeground(new java.awt.Color(30, 30, 30));
        btnSalvar1.setText("Cancelar");
        btnSalvar1.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        btnSalvar1.setPreferredSize(new java.awt.Dimension(62, 30));
        btnSalvar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvar1ActionPerformed(evt);
            }
        });

        txQuantidade.setActionCommand("");
        txQuantidade.setCorTexto(new java.awt.Color(158, 158, 158));
        txQuantidade.setDicas("");
        txQuantidade.setMinimumSize(new java.awt.Dimension(64, 30));
        txQuantidade.setPreferredSize(new java.awt.Dimension(143, 30));
        txQuantidade.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txQuantidadeKeyTyped(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(30, 30, 30));
        jLabel2.setText("Qtd Disponivel:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(11, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txcodigocliente4, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(CodigoCliente1, javax.swing.GroupLayout.Alignment.TRAILING))
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(cbxNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(323, 323, 323))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                    .addGap(98, 98, 98)
                                                    .addComponent(txnome3, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                    .addGap(18, 18, 18)
                                                    .addComponent(NomeCliente))
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                    .addGap(18, 18, 18)
                                                    .addComponent(CodigoCliente5)))
                                            .addGap(242, 242, 242))))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(1, 1, 1)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 611, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(txIdProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(cbxNomeProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(txQuantidadeDisponivel, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGap(39, 39, 39)
                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                    .addComponent(txQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(18, 18, 18)
                                                    .addComponent(btnAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                    .addGap(45, 45, 45)))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(CodigoCliente4)
                                .addComponent(CodigoCliente7)
                                .addComponent(CodigoCliente9)
                                .addComponent(txTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(txDesconto, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(btnAplicar, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addComponent(CodigoCliente))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnSalvar1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CodigoCliente)
                            .addComponent(NomeCliente))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CodigoCliente1)
                            .addComponent(CodigoCliente5)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txIdProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxNomeProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txQuantidadeDisponivel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(67, 67, 67)
                        .addComponent(CodigoCliente4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(CodigoCliente7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txDesconto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAplicar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(CodigoCliente9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(92, 92, 92)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalvar1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(73, 73, 73)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txcodigocliente4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txnome3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(64, 64, 64)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menu1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(59, 59, 59))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(menu1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        jLayeredPane1.setLayer(jPanel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
    try {
        obterIdCliente(); // Chama o método para buscar o cliente pelo ID antes de salvar
        obterIdProduto(); // Chama o método para buscar o ID do produto antes de salvar
        // Continue com as operações de salvar os dados da venda
        Vendas venda = new Vendas(); 
        ItensVenda itenVenda = new ItensVenda();
        RegistrarVendaController controleVenda = new RegistrarVendaController();
        Date date = new Date();
        String fechaAtual = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        
        if (!cbxNomeCliente.getSelectedItem().equals("Selecione cliente:")) {
            if (listaProdutos.size() > 0) {
                try {
                    this.obterIdCliente();
                    
                    // Verificar se o idCliente é válido
                    if (idCliente == 0) {
                        JOptionPane.showMessageDialog(null, "Erro: Cliente inválido!");
                        return;
                    }
                    
                    venda.setIdVenda(0);
                    venda.setCliente(idCliente);
                    BigDecimal valorTotalVenda = new BigDecimal(txTotal.getText());
                    venda.setValorTotalVenda(valorTotalVenda);
                    
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    LocalDateTime dataCompra = LocalDateTime.parse(fechaAtual, formatter);
                    venda.setDataCompra(dataCompra);
                    
                    if (controleVenda.guardar(venda)) {
                        JOptionPane.showMessageDialog(null, "Venda Registrada!");
                        
                        for (ItensVenda elemento : listaProdutos) {
                            itenVenda.setIdItensVenda(0);
                            itenVenda.setIdvenda(0); // Ajuste para o ID correto da venda recém-criada
                            itenVenda.setIdProduto(elemento.getIdProduto());
                            itenVenda.setQuantidadeItensVenda(elemento.getQuantidadeItensVenda());
                            itenVenda.setPrecoUnitario(elemento.getPrecoUnitario());
                            itenVenda.setValorParcial(elemento.getValorParcial());
                            itenVenda.setDesconto(elemento.getDesconto());
                            itenVenda.setTotalAPagar(elemento.getTotalAPagar());
                            
                            if (controleVenda.guardarDetalhe(itenVenda)) {
                                System.out.println("Detalhes da venda registrado!");
                                
                                txSubTotal.setText("0.0");
                                txDesconto.setText("0.0");
                                txTotal.setText("0.0");
                                auxIdDetalhe = 1;
                                
                                this.restaurarStock(elemento.getIdProduto(), elemento.getQuantidadeItensVenda());
                            } else {
                                JOptionPane.showMessageDialog(null, "Erro ao guardar detalhes da venda!");
                            }
                        }
                        listaProdutos.clear();
                        listaTabelaProdutos();
                    } else {
                        JOptionPane.showMessageDialog(null, "Erro ao guardar venda!");
                    }  
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro ao obter id do cliente: " + e.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Selecione um produto!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecione um cliente!");
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao buscar cliente por ID: " + e.getMessage());
    }
        limparCampos();
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void limparCampos() {
    cbxNomeCliente.setSelectedIndex(0); // Reseta o combo box do cliente
    cbxNomeProduto.setSelectedIndex(0); // Reseta o combo box do produto
    txIdCliente.setText("");
    txSubTotal.setText("");
    txDesconto.setText("");
    txTotal.setText("");
    txQuantidade.setText("");
    txQuantidadeDisponivel.setText("");
}
    
    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        String combo = this.cbxNomeProduto.getSelectedItem().toString();
        if (combo.equalsIgnoreCase("Selecione produto:")) {
            JOptionPane.showMessageDialog(null, "Selecione um produto");
        } else {
            if (!txQuantidadeDisponivel.getText().isEmpty()) {
                if (validar(txQuantidadeDisponivel.getText())) {
                    int quantidade = Integer.parseInt(txQuantidade.getText());
                    if (quantidade > 0) {
                        boolean alterado = false;
                        String produtoSelecionado = cbxNomeProduto.getSelectedItem().toString();
                        Produtos produtoRecebido = vendas.obterDadosProduto(produtoSelecionado);
                        double quantidadeProduto = produtoRecebido.getQuantidadeDisponivel();
                        if (!listaProdutos.isEmpty()) {
                            for (ItensVenda auxProduto : listaProdutos) {
                                if (auxProduto.getIdProduto() == produtoRecebido.getIdProduto()) {
                                    quantidade += auxProduto.getQuantidadeItensVenda();
                                    if (quantidade <= quantidadeProduto) {
                                        BigDecimal qtd = new BigDecimal(quantidade);
                                        BigDecimal precoUnitario = produtoRecebido.getValorFinal();
                                        BigDecimal subtotal = qtd.multiply(precoUnitario);
                                        BigDecimal desconto = BigDecimal.ZERO;
                                        BigDecimal totalPagar = subtotal.subtract(desconto);

                                        ItensVenda produto = new ItensVenda(
                                            auxIdDetalhe, 
                                            1, 
                                            produtoRecebido.getIdProduto(),
                                            subtotal, // valorParcial é o subtotal original
                                            quantidade, 
                                            produtoRecebido, 
                                            precoUnitario, 
                                            desconto, 
                                            imposto, 
                                            totalPagar
                                        );
                                        listaProdutos.remove(auxProduto);
                                        listaProdutos.add(produto);
                                        JOptionPane.showMessageDialog(null, "Produto Agregado");
                                        auxIdDetalhe++;
                                        txQuantidadeDisponivel.setText("");
                                        txQuantidade.setText("");
                                        this.calcularTotalPagar();

                                        txIdProduto.setText("");
                                        cbxNomeProduto.setSelectedItem("");
                                        alterado = true;
                                    }
                                }
                            }
                        }
                        if (!alterado) {
                            if (quantidade <= quantidadeProduto) {
                                BigDecimal qtd = new BigDecimal(quantidade);
                                BigDecimal precoUnitario = produtoRecebido.getValorFinal();
                                BigDecimal subtotal = qtd.multiply(precoUnitario);
                                BigDecimal desconto = BigDecimal.ZERO;
                                BigDecimal totalPagar = subtotal.subtract(desconto);

                                ItensVenda produto = new ItensVenda(
                                    auxIdDetalhe, 
                                    1, 
                                    produtoRecebido.getIdProduto(),
                                    subtotal, // valorParcial é o subtotal original
                                    quantidade, 
                                    produtoRecebido, 
                                    precoUnitario, 
                                    desconto, 
                                    imposto, 
                                    totalPagar
                                );
                                listaProdutos.add(produto);
                                JOptionPane.showMessageDialog(null, "Produto Agregado");
                                auxIdDetalhe++;
                                txQuantidadeDisponivel.setText("");
                                txQuantidade.setText("");
                                this.calcularTotalPagar();

                                txIdProduto.setText("");
                                cbxNomeProduto.setSelectedItem("");
                            } else {
                                JOptionPane.showMessageDialog(null, "Quantidade acima do estoque disponível");
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Quantidade deve ser maior que zero");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Na quantidade tem algum caracter não numérico");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Digite a quantidade de produtos");
            }
        }
        this.listaTabelaProdutos();
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void btnAplicarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAplicarActionPerformed
        this.aplicarDesconto();
    }//GEN-LAST:event_btnAplicarActionPerformed

    
    private void cancelarOperacao() {
    int confirmacao = JOptionPane.showConfirmDialog(this, "Você tem certeza que deseja cancelar a operação? Todas as informações serão apagadas.", "Confirmação", JOptionPane.YES_NO_OPTION);
    if (confirmacao == JOptionPane.YES_OPTION) {
        // Limpar todos os campos e listas
        txIdCliente.setText("");
        cbxNomeCliente.setSelectedItem("");
        txIdProduto.setText("");
        cbxNomeProduto.setSelectedItem("");
        txQuantidadeDisponivel.setText("");
        txSubTotal.setText("0.0");
        txDesconto.setText("0.0");
        txTotal.setText("0.0");

        listaProdutos.clear();
        listaTabelaProdutos();
    }
}
    
    int idArrayList = 0;
    private void jtProdutosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtProdutosMouseClicked
        int filaPoint = jtProdutos.rowAtPoint(evt.getPoint());
        int colunaPoint = 0;
        
        if(filaPoint > -1) {
            idArrayList = (int) modeloDadosProdutos.getValueAt(filaPoint, colunaPoint);
        }
        int opcao = JOptionPane.showConfirmDialog(null, "Eliminar Produto?");
        
        switch (opcao) {
            case 0:
                listaProdutos.remove(idArrayList -1);
                this.calcularTotalPagar();
                this.listaTabelaProdutos();
                break;
            case 1:
                break;
            default:
        }
    }//GEN-LAST:event_jtProdutosMouseClicked

    private void btnSalvar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvar1ActionPerformed
        this.cancelarOperacao();
    }//GEN-LAST:event_btnSalvar1ActionPerformed

    private void txQuantidadeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txQuantidadeKeyTyped
        String caracteres = "0123456789";
        if(!caracteres.contains(evt.getKeyChar() + "")) {
            evt.consume();
        }
    }//GEN-LAST:event_txQuantidadeKeyTyped

    private void txDescontoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txDescontoKeyTyped
        String caracteres = "0123456789.";
        if(!caracteres.contains(evt.getKeyChar() + "")) {
            evt.consume();
        }
    }//GEN-LAST:event_txDescontoKeyTyped

    private boolean validar(String valor) {
        try {
            int num = Integer.parseInt(valor);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

   private void calcularTotalPagar() {
    BigDecimal subTotalGeneral = BigDecimal.ZERO;
    BigDecimal descontoGeneral = BigDecimal.ZERO;
    BigDecimal totalPagarGeneral = BigDecimal.ZERO;

    // Calcular totais a partir da lista de produtos
    for (ItensVenda produtos : listaProdutos) {
        subTotalGeneral = subTotalGeneral.add(produtos.getValorParcial());
        descontoGeneral = descontoGeneral.add(produtos.getDesconto());
        totalPagarGeneral = totalPagarGeneral.add(produtos.getTotalAPagar());
    }

    // Chamar a função MySQL calcularTotais
    /*try (PreparedStatement stmt = conexao.obterConexao().prepareStatement("SELECT calcularTotais() AS totais")) {
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            String totaisJson = rs.getString("totais");
            JSONObject json = new JSONObject(totaisJson);
            subTotalGeneral = json.getBigDecimal("subTotal");
            descontoGeneral = json.getBigDecimal("desconto");
            totalPagarGeneral = json.getBigDecimal("total");
        }
    } catch (SQLException ex) {
        Logger.getLogger(VendasF.class.getName()).log(Level.SEVERE, null, ex);
    }*/

    // Atualizar os campos de texto com os totais
    txSubTotal.setText(String.valueOf(subTotalGeneral));
    txDesconto.setText(String.valueOf(descontoGeneral));
    txTotal.setText(String.valueOf(totalPagarGeneral));
}
    
   private void aplicarDesconto() {
    try {
        String descontoText = txDesconto.getText().replace(",", ".");
        BigDecimal descontoValor = new BigDecimal(descontoText).setScale(2, RoundingMode.HALF_UP);

        // Calcula o total atual da venda
        BigDecimal totalVenda = BigDecimal.ZERO;
        for (ItensVenda produto : listaProdutos) {
            totalVenda = totalVenda.add(produto.getValorParcial());
        }

        // Verifica se o desconto é maior ou igual ao total da venda
        if (descontoValor.compareTo(totalVenda) >= 0) {
            JOptionPane.showMessageDialog(null, "O valor do desconto não pode ser maior ou igual ao total da venda.");
            txDesconto.setText("0.00");

            for (ItensVenda produto : listaProdutos) {
                produto.setDesconto(BigDecimal.ZERO);
                produto.setTotalAPagar(produto.getValorParcial());
            }

            calcularTotalPagar();
            listaTabelaProdutos();
            return;
        }

        // Chama a função no MySQL
        BigDecimal descontoAplicado = BigDecimal.ZERO;
        try (PreparedStatement stmt = conexao.obterConexao().prepareStatement("SELECT calcularDesconto(?, ?)")) {
             
            stmt.setBigDecimal(1, totalVenda);
            stmt.setBigDecimal(2, descontoValor);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                descontoAplicado = rs.getBigDecimal(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VendasF.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Aplica o desconto em dinheiro uma vez
        for (ItensVenda produto : listaProdutos) {
            BigDecimal valorParcial = produto.getValorParcial();
            BigDecimal proporcaoDesconto = valorParcial.divide(totalVenda, 10, RoundingMode.HALF_UP);
            BigDecimal descontoProduto = descontoValor.multiply(proporcaoDesconto).setScale(2, RoundingMode.HALF_UP);
            produto.setDesconto(descontoProduto);

            BigDecimal totalComDesconto = valorParcial.subtract(descontoProduto).setScale(2, RoundingMode.HALF_UP);
            produto.setTotalAPagar(totalComDesconto);
        }

        calcularTotalPagar();
        listaTabelaProdutos();

        // Formata o valor do desconto para exibir com duas casas decimais
        txDesconto.setText(descontoValor.setScale(2, RoundingMode.HALF_UP).toString());

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Digite um valor válido para o desconto.");
        txDesconto.setText("0.00");

        for (ItensVenda produto : listaProdutos) {
            produto.setDesconto(BigDecimal.ZERO);
            produto.setTotalAPagar(produto.getValorParcial());
        }

        calcularTotalPagar();
        listaTabelaProdutos();
    }
}



    
    private void obterIdCliente() throws SQLException {
    String clienteSelecionado = cbxNomeCliente.getSelectedItem().toString();
    String sql = "{CALL obter_id_cliente(?)}";
    
    System.out.println("Cliente selecionado: " + clienteSelecionado); // Log de depuração
    
    try (PreparedStatement stmt = conexao.obterConexao().prepareStatement(sql))
        
           {
       stmt.setString(1, clienteSelecionado);
        System.out.println("sql: " + stmt.toString()); // Log de depuração
        
        try (ResultSet resultSet = stmt.executeQuery()) {
            if (resultSet.next()) {
                idCliente = resultSet.getInt("IdCliente");
                System.out.println("ID do cliente encontrado: " + idCliente); // Log de depuração
            } else {
                idCliente = 0;
                System.out.println("Cliente não encontrado."); // Log de depuração
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw new SQLException("Erro ao obter id do cliente: " + e.getMessage());
    }
}
    
    private void obterIdProduto() throws SQLException {
    String produtoSelecionado = cbxNomeProduto.getSelectedItem().toString();
    String sql = "{CALL obterIdProduto(?)}";
    
    System.out.println("Cliente selecionado: " + produtoSelecionado); // Log de depuração
    
    try (PreparedStatement stmt = conexao.obterConexao().prepareStatement(sql))
           {
       stmt.setString(1, produtoSelecionado);
        System.out.println("sql: " + stmt.toString()); // Log de depuração
        
        try (ResultSet resultSet = stmt.executeQuery()) {
            if (resultSet.next()) {
                idProduto = resultSet.getInt("IdProduto");
                System.out.println("ID do Produto encontrado: " + idProduto); // Log de depuração
            } else {
                idProduto = 0;
                System.out.println("Produto não encontrado."); // Log de depuração
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw new SQLException("Erro ao obter id do produto: " + e.getMessage());
    }
}
   
     private void atualizarDesconto() {
        try {
        String descontoText = txDesconto.getText();
        BigDecimal desconto = new BigDecimal(descontoText);

        for (ItensVenda item : listaProdutos) {
            item.setDesconto(desconto);
            BigDecimal valorParcial = item.getValorParcial();
            BigDecimal totalComDesconto = valorParcial.subtract(desconto);
            item.setTotalAPagar(totalComDesconto);
        }

        // Atualiza a tabela para refletir os novos valores
        listaTabelaProdutos();

    } catch (NumberFormatException e) {
        // Se o texto não é um número válido, ignore a atualização
    }
    }
    private void restaurarStock(int idProduto , int quantidade){
        int quantidadeBanco = 0;
        try{
        String sql = "select IdProduto, QuantidadeDisponivel from produtos where IdProduto ='" + idProduto + "'";
        PreparedStatement stmt = conexao.obterConexao().prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            quantidadeBanco = rs.getInt("QuantidadeDisponivel");
        }
        //stmt.close();
        }catch (SQLException e){
            System.out.println("Erro alterar quantidade 1, " + e);
        }
        
        try{
            Connection cn = conexao.obterConexao();
            PreparedStatement consulta = cn.prepareStatement("update produtos set QuantidadeDisponivel=? where IdProduto= '" + idProduto+"'");
            int quantidadeNova = quantidadeBanco - quantidade;
            consulta.setInt(1, quantidadeNova);
            if(consulta.executeUpdate() > 0){
                System.out.println("Tudo certo");
            }
            //cn.close();
        }catch (SQLException e){
            System.out.println("Erro ao alterar quantidade " + e);
        }
    }
    
    private double obterQuantidadeDisponivel(int idProduto) throws SQLException {
    double quantidadeDisponivel = 0.0;
    String sql = "{CALL obterQuantidadeDisponivel(?)}";
    
    try (PreparedStatement stmt = conexao.obterConexao().prepareStatement(sql)) {
        stmt.setInt(1, idProduto);
        try (ResultSet resultSet = stmt.executeQuery()) {
            if (resultSet.next()) {
                quantidadeDisponivel = resultSet.getDouble("QuantidadeDisponivel");
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw new SQLException("Erro ao obter quantidade disponível do produto: " + e.getMessage());
    }
    
    return quantidadeDisponivel;
}

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
            java.util.logging.Logger.getLogger(Vendas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Vendas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Vendas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Vendas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VendasF().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel CodigoCliente;
    private javax.swing.JLabel CodigoCliente1;
    private javax.swing.JLabel CodigoCliente4;
    private javax.swing.JLabel CodigoCliente5;
    private javax.swing.JLabel CodigoCliente7;
    private javax.swing.JLabel CodigoCliente9;
    private javax.swing.JLabel NomeCliente;
    private javax.swing.JLabel NomeCliente1;
    private javax.swing.JLabel NomeCliente2;
    private javax.swing.JLabel NomeCliente3;
    private javax.swing.JLabel NomeCliente4;
    private javax.swing.JLabel NomeCliente5;
    private javax.swing.JLabel NomeCliente6;
    private javax.swing.JLabel NomeCliente7;
    private javax.swing.JLabel NomeCliente8;
    private com.mycompany.autostockcar.view.componentes.Botao btnAdicionar;
    private com.mycompany.autostockcar.view.componentes.Botao btnAplicar;
    private com.mycompany.autostockcar.view.componentes.Botao btnSalvar;
    private com.mycompany.autostockcar.view.componentes.Botao btnSalvar1;
    private com.mycompany.autostockcar.view.componentes.ComboBoxPersonalizado cbxNomeCliente;
    private com.mycompany.autostockcar.view.componentes.ComboBoxPersonalizado cbxNomeProduto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable jtProdutos;
    private com.mycompany.autostockcar.view.componentes.Menu menu1;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txDesconto;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txIdCliente;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txIdProduto;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txQuantidade;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txQuantidadeDisponivel;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txSubTotal;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txTotal;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txcodigocliente4;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txnome3;
    // End of variables declaration//GEN-END:variables
}

