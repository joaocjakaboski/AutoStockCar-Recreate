package com.mycompany.autostockcar.view.formulario;

import com.mycompany.autostockcar.modelo.dao.CidadeDao;
import com.mycompany.autostockcar.modelo.dao.EstadoDao;
import com.mycompany.autostockcar.modelo.dao.FabricanteDao;
import com.mycompany.autostockcar.modelo.dominio.Cidades;
import com.mycompany.autostockcar.modelo.dominio.Estados;
import com.mycompany.autostockcar.modelo.dominio.Fabricantes;
import com.mycompany.autostockcar.view.modelo.FabricanteDto;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Fabricante extends javax.swing.JFrame {
    private Color corCampoDesativado = new Color (131, 135, 141);
    private Color corCampoAtivado = new Color (110, 202, 224);
    int id;

    String caminho = System.getProperty("user.dir") + "\\src\\main\\java\\com\\mycompany\\autostockcar\\view\\imagens\\";
    
    public Fabricante() {
        initComponents();
        setLocationRelativeTo(null);
        jPanel1.setLayout(null);
        jPanel2.setLayout(null);
        cbxNome.setEditable(true);
        
        btnConsultaPeloNome.setIcon(new ImageIcon(caminho + "pesquisar.png"));
        btnAtualizar.setIcon(new ImageIcon(caminho + "atualizar.png"));
        btnSalvar.setVisible(false);
        btnExcluir.setEnabled(false);
        btnExcluir.setBackground(corCampoDesativado);
        btnAlterar.setVisible(false);
        btnCancelar.setVisible(false);
        cbxNome.removeAllItems();
        cbxNome.addItem("");
        
        desativarCampos();
        
        FabricanteDao fabricante = new FabricanteDao();
        EstadoDao estado = new EstadoDao();
        CidadeDao cidade = new CidadeDao();
        
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
                    ResultSet result = (ResultSet) fabricante.buscarFabricanteNomePeloNome(currentText);
                    cbxNome.removeAllItems();
                    cbxNome.addItem("");
                    while (result.next()) {
                        items.add(result.getString("NomeFabricante"));
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
        
        btnAtualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String itemSelecionado = cbxUfEstado.getSelectedItem().toString();
                ResultSet result = (ResultSet) estado.buscarIdEstadoPeloUf(itemSelecionado);
                int idEstado = 0;
                try {
                    while (result.next()) {
                        idEstado = result.getInt("IdEstado");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                List<String> items = new ArrayList<>();
                try {
                    ResultSet resultCidades = (ResultSet) cidade.buscarCidades(idEstado);
                    cbxNomeCidade.removeAllItems();
                    cbxNomeCidade.addItem("");
                    while (resultCidades.next()) {
                        items.add(resultCidades.getString("NomeCidade"));
                    }
                    Collections.sort(items);
                    for (String item : items) {
                        cbxNomeCidade.addItem(item);
                    }
                } catch (SQLException error) {
                    JOptionPane.showMessageDialog(null, "Carregar dados cbx: " + error);
                }
            }
        });
        
        btnConsultaPeloNome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomeBusca = cbxNome.getSelectedItem().toString();
                if (nomeBusca == "") {
                    limparCampos();
                    btnExcluir.setEnabled(false);
                    btnExcluir.setBackground(corCampoDesativado);
                    btnAlterar.setVisible(false);
                } else {
                    Fabricantes fabricanteBusca = fabricante.buscarFabricantePeloNome(nomeBusca);
                    
                    txNome.setText(fabricanteBusca.getNomeFabricante());
                    txCelular.setText(fabricanteBusca.getTelefoneFabricante());
                    txEmail.setText(fabricanteBusca.getEmailFabricante());
                    txEndereco.setText(fabricanteBusca.getEnderecoFabricante());
                    txCNPJ.setText(fabricanteBusca.getCnpjFabricante());
                    txObservacoes.setText(fabricanteBusca.getObsFabricante());
                    txCodigo.setText(String.valueOf(fabricanteBusca.getIdFabricante()));
                    
                    ///Date sqlDate = clienteBusca.getData();
                    ///SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    ///String dataString = formatter.format(sqlDate);
                    ///txDataCadastro.setText(dataString);
                    
                    Cidades cidadeRecebida = cidade.buscarCidadePeloId(fabricanteBusca.getIdCidade());
                    String nomeCidade = cidadeRecebida.getNomeCidade();
                    cbxNomeCidade.addItem(nomeCidade);
                    cbxNomeCidade.setSelectedItem(nomeCidade);
                    Estados estadoRecebido = estado.buscarEstadoPeloId(cidadeRecebida.getIdEstado());
                    String ufEstado = estadoRecebido.getUfEstado();
                    cbxUfEstado.addItem(ufEstado);                    
                    cbxUfEstado.setSelectedItem(ufEstado);
                    
                    btnAlterar.setVisible(true);
                    btnExcluir.setEnabled(true);
                    btnExcluir.setBackground(new Color (131, 191, 205));
                    cbxNome.removeAllItems();
                    cbxNome.addItem("");
                }
            }
        });
        
        btnExcluir.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int excluir = JOptionPane.showConfirmDialog(null, "Deseja excluir o fabricante?", "Excluir", JOptionPane.YES_NO_OPTION);
                if (excluir == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(null, fabricante.excluirFabricante(Integer.valueOf(txCodigo.getText())));
                    limparCampos();
                    desativarCampos();
                    ativarCabecalho();
                    btnAlterar.setVisible(false);
                }
            }
        });
        
        btnNovo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnNovo.setVisible(false);
                btnExcluir.setVisible(false);
                btnAlterar.setVisible(false);
                
                Rectangle boundsNovo = btnNovo.getBounds();
                Rectangle boundsExcluir = btnExcluir.getBounds();
                btnSalvar.setBounds(boundsNovo);
                btnCancelar.setBounds(boundsExcluir);
                
                btnSalvar.setVisible(true);
                btnCancelar.setVisible(true);
                id = 0;
                
                desativarCabecalho();
                limparCampos();
                ativarCampos();
                
                List<String> items = new ArrayList<>();
                try {
                    ResultSet result = (ResultSet) estado.buscarUfEstados();
                    cbxUfEstado.removeAllItems();
                    cbxUfEstado.addItem("");
                    while (result.next()) {
                        items.add(result.getString("UfEstado"));
                    }
                    Collections.sort(items);
                    for (String item : items) {
                        cbxUfEstado.addItem(item);
                    }
                } catch (SQLException error) {
                    JOptionPane.showMessageDialog(null, "Carregar dados cbx: " + error);
                }
            }
        });
        
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String itemSelecionado = cbxNomeCidade.getSelectedItem().toString();
                ResultSet resultCidade = (ResultSet) cidade.buscarCidadeIdPeloNome(itemSelecionado);
                int idCidade = 0;
                
                try {
                    while (resultCidade.next()) {
                        idCidade = resultCidade.getInt("IdCidade");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (txNome.getText().isEmpty() || txCelular.getText().isEmpty()|| 
                    txEndereco.getText().isEmpty() || txEmail.getText().isEmpty() || 
                    txCNPJ.getText().isEmpty() || idCidade == 0) {
                    JOptionPane.showMessageDialog(null, "Todos os campos obrigatórios devem ser preenchidos.");
                } else {
                    Fabricantes fabricante = new Fabricantes(txNome.getText(), txEmail.getText(), 
                                          txEndereco.getText(), txCelular.getText(), txCNPJ.getText(), 
                                           txObservacoes.getText(), idCidade);
                    FabricanteDao frabricanteDao = new FabricanteDao();
                    try {
                        String feedBack = frabricanteDao.salvar(fabricante);
                        
                        if (feedBack == "Cliente adicionado com sucesso" || feedBack == "Cliente editado com sucesso") {
                            limparCampos();
                            desativarCampos();
                            ativarCabecalho();

                            btnSalvar.setVisible(false);
                            btnCancelar.setVisible(false);
                            btnNovo.setVisible(true);
                            btnExcluir.setVisible(true);
                            btnAlterar.setVisible(false);
                        }
                        
                        JOptionPane.showMessageDialog(null, feedBack);
                        
                    } catch (SQLException ex) {
                        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int cancelar = JOptionPane.showConfirmDialog(null, "Deseja cancelar?", "Cancelar", JOptionPane.YES_NO_OPTION);
                if (cancelar == JOptionPane.YES_OPTION) {
                    btnExcluir.setVisible(true);
                    btnSalvar.setVisible(false);
                    btnNovo.setVisible(true);
                    btnCancelar.setVisible(false);
                    ativarCabecalho();
                    desativarCampos();
                    limparCampos();
                    btnExcluir.setEnabled(false);
                    btnExcluir.setBackground(corCampoDesativado);
                }
            }
        });
        btnAlterar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                desativarCabecalho();
                ativarCampos();
                btnNovo.setVisible(false);
                btnExcluir.setVisible(false);
                btnAlterar.setVisible(false);
                
                Rectangle boundsNovo = btnNovo.getBounds();
                Rectangle boundsExcluir = btnExcluir.getBounds();
                btnSalvar.setBounds(boundsNovo);
                btnCancelar.setBounds(boundsExcluir);
                
                btnSalvar.setVisible(true);
                btnCancelar.setVisible(true);
                
                Fabricantes fabricanteBusca = fabricante.buscarFabricantePeloNome(txNome.getText());
                id = fabricanteBusca.getIdFabricante();
                
                Cidades cidadeRecebida = cidade.buscarCidadePeloId(fabricanteBusca.getIdCidade());
                Estados estadoRecebido = estado.buscarEstadoPeloId(cidadeRecebida.getIdEstado());
                String ufEstado = estadoRecebido.getUfEstado();                 
                
                List<String> items = new ArrayList<>();
                try {
                    ResultSet result = (ResultSet) estado.buscarUfEstados();
                    cbxUfEstado.removeAllItems();
                    cbxUfEstado.addItem("");
                    while (result.next()) {
                        items.add(result.getString("UfEstado"));
                    }
                    Collections.sort(items);
                    for (String item : items) {
                        cbxUfEstado.addItem(item);
                    }
                } catch (SQLException error) {
                    JOptionPane.showMessageDialog(null, "Carregar dados cbx: " + error);
                }
                cbxUfEstado.setSelectedItem(ufEstado);
            }
        });
        
    }
        /*
        btnAlterar.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e) {
        FabricanteDto obj = new FabricanteDto(); 
        
        obj.setNomeFabricante(txNome.getText());
        obj.setEmailFabricante(txEmail.getText());
        obj.setTelefoneFabricante(txCelular.getText());
        obj.setEnderecoFabricante(txEndereco.getText());
        obj.setCnpjFabricante(txCNPJ.getText());
        obj.setObservacaoFabricante(txObservacoes.getText());
        obj.setIdFabricante(Integer.valueOf(txCodigo.getText()));
        
        FabricanteDao dao = new FabricanteDao();
        //dao.Editar(obj);
        limparCampos();
    }
    });
    */ 
        
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txCodigo = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        btnSalvar = new com.mycompany.autostockcar.view.componentes.Botao();
        jLabel8 = new javax.swing.JLabel();
        txNome = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        jLabel9 = new javax.swing.JLabel();
        txEndereco = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        btnExcluir = new com.mycompany.autostockcar.view.componentes.Botao();
        btnAlterar = new com.mycompany.autostockcar.view.componentes.Botao();
        btnNovo = new com.mycompany.autostockcar.view.componentes.Botao();
        jLabel11 = new javax.swing.JLabel();
        txCNPJ = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        txEmail = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        txCelular = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txObservacoes = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        btnBuscar = new com.mycompany.autostockcar.view.componentes.Botao();
        cbxNome = new com.mycompany.autostockcar.view.componentes.ComboBoxPersonalizado();
        btnConsultaPeloNome = new com.mycompany.autostockcar.view.componentes.Botao();
        cbxNomeCidade = new com.mycompany.autostockcar.view.componentes.ComboBoxPersonalizado();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        cbxUfEstado = new com.mycompany.autostockcar.view.componentes.ComboBoxPersonalizado();
        btnAtualizar = new com.mycompany.autostockcar.view.componentes.Botao();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel17 = new javax.swing.JLabel();
        btnCancelar = new com.mycompany.autostockcar.view.componentes.Botao();

        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Nome");

        jLabel7.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Nome");

        jLabel10.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("CNPJ");

        jSeparator3.setForeground(new java.awt.Color(30, 30, 30));

        jSeparator4.setForeground(new java.awt.Color(30, 30, 30));

        jSeparator5.setForeground(new java.awt.Color(30, 30, 30));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Formulário de Fabricantes");

        jPanel1.setBackground(new java.awt.Color(131, 191, 205));

        jPanel2.setBackground(new java.awt.Color(147, 211, 225));

        jPanel3.setBackground(new java.awt.Color(110, 202, 224));
        jPanel3.setForeground(new java.awt.Color(0, 153, 153));

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 28)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Cadastro de Fabricantes");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(230, 230, 230))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        jLabel6.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Nome");

        txCodigo.setEditable(false);
        txCodigo.setForeground(new java.awt.Color(0, 0, 0));
        txCodigo.setCor(new java.awt.Color(110, 202, 224));
        txCodigo.setDicas("Código");
        txCodigo.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txCodigo.setPreferredSize(new java.awt.Dimension(180, 30));

        btnSalvar.setBackground(new java.awt.Color(131, 191, 205));
        btnSalvar.setForeground(new java.awt.Color(0, 0, 0));
        btnSalvar.setText("Salvar");
        btnSalvar.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        btnSalvar.setMaximumSize(new java.awt.Dimension(65, 30));
        btnSalvar.setMinimumSize(new java.awt.Dimension(65, 30));

        jLabel8.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Código");

        txNome.setForeground(new java.awt.Color(0, 0, 0));
        txNome.setCor(new java.awt.Color(110, 202, 224));
        txNome.setDicas("Nome ");
        txNome.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txNome.setPreferredSize(new java.awt.Dimension(180, 30));

        jLabel9.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("CNPJ");

        txEndereco.setForeground(new java.awt.Color(0, 0, 0));
        txEndereco.setCor(new java.awt.Color(110, 202, 224));
        txEndereco.setDicas("Endereço");
        txEndereco.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txEndereco.setPreferredSize(new java.awt.Dimension(180, 30));

        btnExcluir.setBackground(new java.awt.Color(131, 191, 205));
        btnExcluir.setForeground(new java.awt.Color(0, 0, 0));
        btnExcluir.setText("Excluir");
        btnExcluir.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        btnExcluir.setMaximumSize(new java.awt.Dimension(65, 30));
        btnExcluir.setMinimumSize(new java.awt.Dimension(65, 30));

        btnAlterar.setBackground(new java.awt.Color(131, 191, 205));
        btnAlterar.setForeground(new java.awt.Color(0, 0, 0));
        btnAlterar.setText("Alterar");
        btnAlterar.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        btnAlterar.setMaximumSize(new java.awt.Dimension(65, 30));
        btnAlterar.setMinimumSize(new java.awt.Dimension(65, 30));

        btnNovo.setBackground(new java.awt.Color(131, 191, 205));
        btnNovo.setForeground(new java.awt.Color(0, 0, 0));
        btnNovo.setText("Novo");
        btnNovo.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        btnNovo.setMaximumSize(new java.awt.Dimension(65, 30));
        btnNovo.setMinimumSize(new java.awt.Dimension(65, 30));

        jLabel11.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Endereço");

        txCNPJ.setForeground(new java.awt.Color(0, 0, 0));
        txCNPJ.setCor(new java.awt.Color(110, 202, 224));
        txCNPJ.setDicas("CNPJ");
        txCNPJ.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txCNPJ.setPreferredSize(new java.awt.Dimension(180, 30));

        txEmail.setForeground(new java.awt.Color(0, 0, 0));
        txEmail.setCor(new java.awt.Color(110, 202, 224));
        txEmail.setDicas("E-Mail");
        txEmail.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txEmail.setPreferredSize(new java.awt.Dimension(180, 30));

        txCelular.setForeground(new java.awt.Color(0, 0, 0));
        txCelular.setCor(new java.awt.Color(110, 202, 224));
        txCelular.setDicas("Celular");
        txCelular.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txCelular.setPreferredSize(new java.awt.Dimension(180, 30));

        jLabel12.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Celular");

        jLabel13.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Endereço de E-Mail");

        jLabel14.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText("Observações");

        txObservacoes.setForeground(new java.awt.Color(0, 0, 0));
        txObservacoes.setCor(new java.awt.Color(110, 202, 224));
        txObservacoes.setDicas("Observações");
        txObservacoes.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txObservacoes.setPreferredSize(new java.awt.Dimension(180, 30));

        btnBuscar.setBackground(new java.awt.Color(131, 191, 205));
        btnBuscar.setForeground(new java.awt.Color(0, 0, 0));
        btnBuscar.setText("Buscar");
        btnBuscar.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        btnBuscar.setMaximumSize(new java.awt.Dimension(65, 30));
        btnBuscar.setMinimumSize(new java.awt.Dimension(65, 30));

        cbxNome.setForeground(new java.awt.Color(0, 0, 0));
        cbxNome.setToolTipText("");

        btnConsultaPeloNome.setBackground(new java.awt.Color(131, 191, 205));
        btnConsultaPeloNome.setForeground(new java.awt.Color(28, 181, 223));

        cbxNomeCidade.setEditable(true);
        cbxNomeCidade.setForeground(new java.awt.Color(0, 0, 0));

        jLabel15.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setText("Cidade");

        jLabel16.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setText("Estado");

        cbxUfEstado.setEditable(true);
        cbxUfEstado.setForeground(new java.awt.Color(0, 0, 0));

        btnAtualizar.setBackground(new java.awt.Color(131, 191, 205));
        btnAtualizar.setForeground(new java.awt.Color(28, 181, 223));

        jSeparator6.setForeground(new java.awt.Color(30, 30, 30));

        jLabel17.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 0));
        jLabel17.setText("Nome");

        btnCancelar.setBackground(new java.awt.Color(131, 191, 205));
        btnCancelar.setForeground(new java.awt.Color(0, 0, 0));
        btnCancelar.setText("Cancelar");
        btnCancelar.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        btnCancelar.setMaximumSize(new java.awt.Dimension(65, 30));
        btnCancelar.setMinimumSize(new java.awt.Dimension(65, 30));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txNome, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txCNPJ, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel16)))
                        .addGap(138, 138, 138))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txObservacoes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel14)
                                            .addComponent(jLabel13))
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addGap(18, 18, 18)
                                .addComponent(txCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(226, 226, 226))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(txCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel17)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(cbxNome, javax.swing.GroupLayout.PREFERRED_SIZE, 613, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnConsultaPeloNome, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 8, Short.MAX_VALUE)))
                        .addGap(26, 26, 26))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(287, 287, 287)
                                .addComponent(jLabel9))
                            .addComponent(jLabel8)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(cbxNomeCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cbxUfEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnAtualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 659, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel8))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel17)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnConsultaPeloNome, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(jLabel9)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txCNPJ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxUfEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAtualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(jLabel12))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txObservacoes, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(cbxNomeCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 614, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
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
    private void limparCampos() {
        txCodigo.setText("");
        txNome.setText("");
        txEndereco.setText("");
        txCNPJ.setText("");
        txEmail.setText("");
        txCelular.setText("");
        txObservacoes.setText("");
        txCelular.setText("");
        cbxUfEstado.removeAllItems();
        cbxUfEstado.addItem("");
        cbxNomeCidade.removeAllItems();
        cbxNomeCidade.addItem("");
    }
    
    

    /*    */
     private void desativarCampos() {        
        txCodigo.setEnabled(false);
        txCodigo.setCor(corCampoDesativado);
        txCelular.setEnabled(false);
        txCelular.setCor(corCampoDesativado);
        cbxNomeCidade.setEnabled(false);
        cbxNomeCidade.setBackground(corCampoDesativado);
        cbxNomeCidade.setCor(corCampoDesativado);
        txCNPJ.setEnabled(false);
        txCNPJ.setCor(corCampoDesativado);
        txEmail.setEnabled(false);
        txEmail.setCor(corCampoDesativado);
        txNome.setEnabled(false);
        txNome.setCor(corCampoDesativado);
        txEmail.setEnabled(false);
        txEmail.setCor(corCampoDesativado);
        txObservacoes.setEnabled(false);
        txObservacoes.setCor(corCampoDesativado);
        txEndereco.setEnabled(false);
        txEndereco.setCor(corCampoDesativado);
        cbxUfEstado.setEnabled(false);
        cbxUfEstado.setBackground(corCampoDesativado);
        cbxUfEstado.setCor(corCampoDesativado);
        btnAtualizar.setEnabled(false);
        btnAtualizar.setBackground(corCampoDesativado);
    }
    
        private void ativarCampos() {
        txEndereco.setEnabled(true);
        txEndereco.setCor(corCampoAtivado);
        txCelular.setEnabled(true);
        txCelular.setCor(corCampoAtivado);
        cbxNomeCidade.setEnabled(true);
        cbxNomeCidade.setBackground(corCampoAtivado);
        cbxNomeCidade.setCor(corCampoAtivado);
        txCNPJ.setEnabled(true);
        txCNPJ.setCor(corCampoAtivado);
        txNome.setEnabled(true);
        txNome.setCor(corCampoAtivado);
        txEmail.setEnabled(true);
        txEmail.setCor(corCampoAtivado);
        txObservacoes.setEnabled(true);
        txObservacoes.setCor(corCampoAtivado);
        cbxUfEstado.setEnabled(true);
        cbxUfEstado.setBackground(corCampoAtivado);
        cbxUfEstado.setCor(corCampoAtivado);
        btnAtualizar.setEnabled(true);
        btnAtualizar.setBackground(corCampoAtivado);
    }
    
    private void desativarCabecalho() {
        cbxNome.setEnabled(false);
        cbxNome.setBackground(corCampoDesativado);
        cbxNome.setCor(corCampoDesativado);
        cbxNome.removeAllItems();
        cbxNome.addItem("");
        btnConsultaPeloNome.setEnabled(false);
        btnConsultaPeloNome.setBackground(corCampoDesativado);
    }
    
    private void ativarCabecalho() {
        cbxNome.setEnabled(true);
        cbxNome.setBackground(corCampoAtivado);
        cbxNome.setCor(corCampoAtivado);
        btnConsultaPeloNome.setEnabled(true);
        btnConsultaPeloNome.setBackground(corCampoAtivado);
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
            java.util.logging.Logger.getLogger(Fabricante.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Fabricante.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Fabricante.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Fabricante.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Fabricante().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.mycompany.autostockcar.view.componentes.Botao btnAlterar;
    private com.mycompany.autostockcar.view.componentes.Botao btnAtualizar;
    private com.mycompany.autostockcar.view.componentes.Botao btnBuscar;
    private com.mycompany.autostockcar.view.componentes.Botao btnCancelar;
    private com.mycompany.autostockcar.view.componentes.Botao btnConsultaPeloNome;
    private com.mycompany.autostockcar.view.componentes.Botao btnExcluir;
    private com.mycompany.autostockcar.view.componentes.Botao btnNovo;
    private com.mycompany.autostockcar.view.componentes.Botao btnSalvar;
    private com.mycompany.autostockcar.view.componentes.ComboBoxPersonalizado cbxNome;
    private com.mycompany.autostockcar.view.componentes.ComboBoxPersonalizado cbxNomeCidade;
    private com.mycompany.autostockcar.view.componentes.ComboBoxPersonalizado cbxUfEstado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txCNPJ;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txCelular;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txCodigo;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txEmail;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txEndereco;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txNome;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txObservacoes;
    // End of variables declaration//GEN-END:variables
}
