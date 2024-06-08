package com.mycompany.autostockcar.view.formulario;

import com.mycompany.autostockcar.modelo.conexao.Conexao;
import com.mycompany.autostockcar.modelo.conexao.ConexaoMysql;
import com.mycompany.autostockcar.modelo.dao.CadastropDao;
import com.mycompany.autostockcar.modelo.dao.ClienteDao;
import com.mycompany.autostockcar.modelo.dominio.Produtos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class Vendas extends javax.swing.JFrame {

    private DefaultTableModel modeloDadosProdutos;
    ArrayList<DetalheVenda> listaProdutos = new ArrayList<>();
    private DetalheVenda produtos;

    private int idProduto = 0;
    private String nome = "";
    private int quantidadeProduto = 0;
    private BigDecimal precoUnitario = BigDecimal.ZERO;
    private double porcentagemIva;
    private int quantidade = 0;
    private BigDecimal subtotal = BigDecimal.ZERO;
    private BigDecimal desconto = BigDecimal.ZERO;
    private double iva = 0.0;
    private BigDecimal totalPagar = BigDecimal.ZERO;
   
    private int auxIdDetalhe = 0;

    private final Conexao conexao;
    private PreparedStatement pstm;

    ClienteDao cliente = new ClienteDao();
    CadastropDao produto = new CadastropDao();

    public Vendas() {
        initComponents();
        setLocationRelativeTo(null);
        menu1.setPaiHerdado(this);
        this.inicializarTabelaProdutos();

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

    }

    private void inicializarTabelaProdutos() {
        modeloDadosProdutos = new DefaultTableModel();

        modeloDadosProdutos.addColumn("N");
        modeloDadosProdutos.addColumn("Código Item");
        modeloDadosProdutos.addColumn("Descrição");
        modeloDadosProdutos.addColumn("Quantidade");
        modeloDadosProdutos.addColumn("Valor Parcial");
        modeloDadosProdutos.addColumn("Desconto");
        modeloDadosProdutos.addColumn("Iva");
        modeloDadosProdutos.addColumn("Valor Total");

        this.jtProdutos.setModel(modeloDadosProdutos);
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
        txcodigocliente1 = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        txIdProduto = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        txnome2 = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        txcodigocliente4 = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        txnome3 = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        CodigoCliente1 = new javax.swing.JLabel();
        CodigoCliente2 = new javax.swing.JLabel();
        CodigoCliente4 = new javax.swing.JLabel();
        CodigoCliente5 = new javax.swing.JLabel();
        CodigoCliente6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtProdutos = new javax.swing.JTable();
        txcodigocliente5 = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        txnome4 = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        txnome5 = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        CodigoCliente7 = new javax.swing.JLabel();
        CodigoCliente8 = new javax.swing.JLabel();
        txnome6 = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        CodigoCliente9 = new javax.swing.JLabel();
        txnome7 = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        btnSalvar = new com.mycompany.autostockcar.view.componentes.Botao();
        btnSalvar1 = new com.mycompany.autostockcar.view.componentes.Botao();
        jLabel1 = new javax.swing.JLabel();
        txQuantidade = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        btnSalvar2 = new com.mycompany.autostockcar.view.componentes.Botao();
        cbxNomeCliente = new com.mycompany.autostockcar.view.componentes.ComboBoxPersonalizado();
        btnAdicionar = new com.mycompany.autostockcar.view.componentes.Botao();
        cbxNomeProduto = new com.mycompany.autostockcar.view.componentes.ComboBoxPersonalizado();
        menu1 = new com.mycompany.autostockcar.view.componentes.Menu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(new java.awt.Dimension(1280, 720));

        jLayeredPane1.setPreferredSize(new java.awt.Dimension(1280, 720));

        jPanel1.setBackground(new java.awt.Color(131, 191, 205));
        jPanel1.setPreferredSize(new java.awt.Dimension(1280, 720));

        jPanel2.setBackground(new java.awt.Color(147, 211, 225));
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

        txcodigocliente1.setCor(new java.awt.Color(131, 191, 205));
        txcodigocliente1.setDicas("Código");
        txcodigocliente1.setMinimumSize(new java.awt.Dimension(64, 30));
        txcodigocliente1.setPreferredSize(new java.awt.Dimension(143, 30));

        txIdProduto.setCor(new java.awt.Color(131, 191, 205));
        txIdProduto.setDicas("Pagamento");
        txIdProduto.setMinimumSize(new java.awt.Dimension(64, 30));
        txIdProduto.setPreferredSize(new java.awt.Dimension(143, 30));

        txnome2.setActionCommand("");
        txnome2.setCor(new java.awt.Color(131, 191, 205));
        txnome2.setDicas("");
        txnome2.setMinimumSize(new java.awt.Dimension(64, 30));
        txnome2.setPreferredSize(new java.awt.Dimension(143, 30));

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

        CodigoCliente2.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        CodigoCliente2.setForeground(new java.awt.Color(30, 30, 30));
        CodigoCliente2.setText("Vendedor:");

        CodigoCliente4.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        CodigoCliente4.setForeground(new java.awt.Color(30, 30, 30));
        CodigoCliente4.setText("Operacao:");

        CodigoCliente5.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        CodigoCliente5.setForeground(new java.awt.Color(30, 30, 30));
        CodigoCliente5.setText("Produto:");

        CodigoCliente6.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        CodigoCliente6.setForeground(new java.awt.Color(30, 30, 30));
        CodigoCliente6.setText("Nome:");

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
        jScrollPane1.setViewportView(jtProdutos);

        txcodigocliente5.setCor(new java.awt.Color(131, 191, 205));
        txcodigocliente5.setDicas("Cód Vendedor");
        txcodigocliente5.setMinimumSize(new java.awt.Dimension(64, 30));
        txcodigocliente5.setPreferredSize(new java.awt.Dimension(143, 30));

        txnome4.setForeground(new java.awt.Color(131, 191, 205));
        txnome4.setText("campoDeTexto1");
        txnome4.setCor(new java.awt.Color(131, 191, 205));
        txnome4.setMinimumSize(new java.awt.Dimension(64, 30));
        txnome4.setPreferredSize(new java.awt.Dimension(143, 30));

        txnome5.setActionCommand("");
        txnome5.setCor(new java.awt.Color(131, 191, 205));
        txnome5.setDicas("");
        txnome5.setMinimumSize(new java.awt.Dimension(64, 30));
        txnome5.setPreferredSize(new java.awt.Dimension(143, 30));

        CodigoCliente7.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        CodigoCliente7.setForeground(new java.awt.Color(30, 30, 30));
        CodigoCliente7.setText("Subtotal:");

        CodigoCliente8.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        CodigoCliente8.setForeground(new java.awt.Color(30, 30, 30));
        CodigoCliente8.setText("Total:");

        txnome6.setActionCommand("");
        txnome6.setCor(new java.awt.Color(131, 191, 205));
        txnome6.setDicas("");
        txnome6.setMinimumSize(new java.awt.Dimension(64, 30));
        txnome6.setPreferredSize(new java.awt.Dimension(143, 30));

        CodigoCliente9.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        CodigoCliente9.setForeground(new java.awt.Color(30, 30, 30));
        CodigoCliente9.setText("Desconto:");

        txnome7.setActionCommand("");
        txnome7.setCor(new java.awt.Color(131, 191, 205));
        txnome7.setDicas("");
        txnome7.setMinimumSize(new java.awt.Dimension(64, 30));
        txnome7.setPreferredSize(new java.awt.Dimension(143, 30));

        btnSalvar.setBackground(new java.awt.Color(163, 225, 255));
        btnSalvar.setForeground(new java.awt.Color(30, 30, 30));
        btnSalvar.setText("Salvar");
        btnSalvar.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        btnSalvar.setPreferredSize(new java.awt.Dimension(62, 30));
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnSalvar1.setBackground(new java.awt.Color(163, 225, 255));
        btnSalvar1.setForeground(new java.awt.Color(30, 30, 30));
        btnSalvar1.setText("Procurar");
        btnSalvar1.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        btnSalvar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvar1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(30, 30, 30));
        jLabel1.setText("Qtd:");

        txQuantidade.setActionCommand("");
        txQuantidade.setCor(new java.awt.Color(131, 191, 205));
        txQuantidade.setDicas("");
        txQuantidade.setMinimumSize(new java.awt.Dimension(64, 30));
        txQuantidade.setPreferredSize(new java.awt.Dimension(143, 30));

        btnSalvar2.setBackground(new java.awt.Color(163, 225, 255));
        btnSalvar2.setForeground(new java.awt.Color(30, 30, 30));
        btnSalvar2.setText("Procurar");
        btnSalvar2.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        btnSalvar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvar2ActionPerformed(evt);
            }
        });

        cbxNomeCliente.setForeground(new java.awt.Color(0, 0, 0));
        cbxNomeCliente.setToolTipText("");

        btnAdicionar.setBackground(new java.awt.Color(163, 225, 255));
        btnAdicionar.setForeground(new java.awt.Color(30, 30, 30));
        btnAdicionar.setText("Adicionar");
        btnAdicionar.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });

        cbxNomeProduto.setForeground(new java.awt.Color(30, 30, 30));
        cbxNomeProduto.setToolTipText("");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 611, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txcodigocliente1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(CodigoCliente1)
                                            .addComponent(txcodigocliente4, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(CodigoCliente2)
                                            .addComponent(txcodigocliente5, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txIdProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(98, 98, 98)
                                                .addComponent(txnome3, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(NomeCliente)
                                                    .addComponent(CodigoCliente6)
                                                    .addComponent(txnome4, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addGap(18, 18, 18)
                                                        .addComponent(cbxNomeProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(btnSalvar2, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addGap(17, 17, 17)
                                                        .addComponent(CodigoCliente5)))
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(cbxNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(194, 194, 194)))
                                        .addComponent(btnAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(121, 121, 121)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txnome2, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(CodigoCliente4)
                                    .addComponent(CodigoCliente7)
                                    .addComponent(txnome6, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(CodigoCliente9)
                                    .addComponent(txnome7, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(CodigoCliente8)
                                    .addComponent(txnome5, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(CodigoCliente)
                                .addGap(315, 315, 315)
                                .addComponent(btnSalvar1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 48, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CodigoCliente)
                    .addComponent(NomeCliente))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txcodigocliente1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalvar1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CodigoCliente2)
                    .addComponent(CodigoCliente6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txcodigocliente5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txnome4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(CodigoCliente1)
                        .addComponent(CodigoCliente5)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txIdProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSalvar2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxNomeProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(CodigoCliente4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txnome2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(CodigoCliente7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txnome6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(CodigoCliente9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txnome7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(CodigoCliente8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txnome5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        //readJTable();
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnSalvar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvar1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSalvar1ActionPerformed

    private void btnSalvar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvar2ActionPerformed

    }//GEN-LAST:event_btnSalvar2ActionPerformed

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        String combo = this.cbxNomeProduto.getSelectedItem().toString();
        //validar a selecão de um produto
        if (combo.equalsIgnoreCase("Selecione produto:")) {
            JOptionPane.showMessageDialog(null, "Selecione um produto");
        } else {
            //validar quantidade
            if (!txQuantidade.getText().isEmpty()) {
                //validar caracter numérico
                boolean validacao = validar(txQuantidade.getText());
                if (validacao == true) {
                    //validar quantidade maior que zero
                    if (Integer.parseInt(txQuantidade.getText()) > 0) {
                        quantidade = Integer.parseInt(txQuantidade.getText());
                        String produtoSelecionado = cbxNomeProduto.getSelectedItem().toString();
                        Produtos produtoRecebido = produto.buscarDadosDoProduto(produtoSelecionado);

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
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private boolean validar(String valor) {
        try {
            int num = Integer.parseInt(valor);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


        
        
        
        
        
        
        
        
        
        /* String sql = "SELECT * FROM Produtos WHERE NomeProduto = ?";

        // Verifica se o objeto cbxNomeProduto está selecionado e se a conexão está disponível
        if (this.cbxNomeProduto.getSelectedItem() != null && conexao != null) {
            try (Connection cn = conexao.obterConexao(); PreparedStatement pst = cn.prepareStatement(sql)) {

                // Define o parâmetro do SQL com base no item selecionado no ComboBox
                pst.setString(1, this.cbxNomeProduto.getSelectedItem().toString());

                // Executar a consulta e usar o ResultSet dentro do try-with-resources
                try (ResultSet rs = pst.executeQuery()) {
                    while (rs.next()) {
                        // Extrair dados de cada coluna do resultado
                        idProduto = rs.getInt("IdProduto");
                        nome = rs.getString("NomeProduto");
                        valorParcial = rs.getDouble("ValorFinal");

                    }
                }
            } catch (SQLException e) {
                // Lidar com exceções de SQL    
            }
        } else {
            System.err.println("Erro: ComboBox não selecionado ou conexão não disponível.");
        }
    }*/
    
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
                new Vendas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel CodigoCliente;
    private javax.swing.JLabel CodigoCliente1;
    private javax.swing.JLabel CodigoCliente2;
    private javax.swing.JLabel CodigoCliente4;
    private javax.swing.JLabel CodigoCliente5;
    private javax.swing.JLabel CodigoCliente6;
    private javax.swing.JLabel CodigoCliente7;
    private javax.swing.JLabel CodigoCliente8;
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
    private com.mycompany.autostockcar.view.componentes.Botao btnSalvar;
    private com.mycompany.autostockcar.view.componentes.Botao btnSalvar1;
    private com.mycompany.autostockcar.view.componentes.Botao btnSalvar2;
    private com.mycompany.autostockcar.view.componentes.ComboBoxPersonalizado cbxNomeCliente;
    private com.mycompany.autostockcar.view.componentes.ComboBoxPersonalizado cbxNomeProduto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable jtProdutos;
    private com.mycompany.autostockcar.view.componentes.Menu menu1;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txIdProduto;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txQuantidade;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txcodigocliente1;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txcodigocliente4;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txcodigocliente5;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txnome2;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txnome3;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txnome4;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txnome5;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txnome6;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txnome7;
    // End of variables declaration//GEN-END:variables

    private static class DetalheVenda {

        public DetalheVenda() {
        }
    }

}

