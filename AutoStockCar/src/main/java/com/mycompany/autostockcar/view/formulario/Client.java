package com.mycompany.autostockcar.view.formulario;

import com.mycompany.autostockcar.modelo.dao.CidadeDao;
import com.mycompany.autostockcar.modelo.dao.ClienteDao;
import com.mycompany.autostockcar.modelo.dao.EstadoDao;
import com.mycompany.autostockcar.modelo.dominio.Cidades;
import com.mycompany.autostockcar.modelo.dominio.Clientes;
import com.mycompany.autostockcar.modelo.dominio.Estados;
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

/**
 *
 * @author lipem
 */
public class Client extends javax.swing.JFrame { 
    private Color corCampoDesativado = new Color (131, 135, 141);
    private Color corCampoAtivado = new Color (110, 202, 224);
    int id;
    
    String caminho = System.getProperty("user.dir") + "\\src\\main\\java\\com\\mycompany\\autostockcar\\view\\imagens\\";
    
    public Client() {
        initComponents();
        setLocationRelativeTo(null);
        jPanel2.setLayout(null);
        menu1.setPaiHerdado(this);
        cbxNome.setEditable(true);
        
        btnConsultaPeloCPF.setIcon(new ImageIcon(caminho + "pesquisar.png"));
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
        
        ClienteDao cliente = new ClienteDao();
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
                    ResultSet result = (ResultSet) cliente.buscarClienteNomePeloNome(currentText);
                    cbxNome.removeAllItems();
                    cbxNome.addItem("");
                    while (result.next()) {
                        items.add(result.getString("NomeCliente"));
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
                    Clientes clienteBusca = cliente.buscarClientePeloNome(nomeBusca);
                    
                    txNome.setText(clienteBusca.getNomeCliente());
                    txCelular.setText(clienteBusca.getTelefoneCliente());
                    txEmail.setText(clienteBusca.getEmailCliente());
                    txRua.setText(clienteBusca.getEnderecoCliente());
                    txCpf.setText(clienteBusca.getCpfCliente());
                    txObsCliente.setText(clienteBusca.getObsCliente());
                    txBairro.setText(clienteBusca.getBairroCliente());                    
                    Date sqlDate = clienteBusca.getData();
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    String dataString = formatter.format(sqlDate);
                    txDataCadastro.setText(dataString);
                    
                    Cidades cidadeRecebida = cidade.buscarCidadePeloId(clienteBusca.getIdCidade());
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
                    txConsultaCpf.setText("");
                    cbxNome.removeAllItems();
                    cbxNome.addItem("");
                }
            }
        });
        
        btnConsultaPeloCPF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cpf = txConsultaCpf.getText();
                if (cpf.equals("")) {
                    limparCampos();
                    btnExcluir.setEnabled(false);
                    btnExcluir.setBackground(corCampoDesativado);
                    btnAlterar.setVisible(false);
                } else {                    
                    Clientes clienteBusca = cliente.buscarClientePeloCpf(cpf);
                    if (clienteBusca != null) {
                        txNome.setText(clienteBusca.getNomeCliente());
                        txCelular.setText(clienteBusca.getTelefoneCliente());
                        txEmail.setText(clienteBusca.getEmailCliente());
                        txRua.setText(clienteBusca.getEnderecoCliente());
                        txCpf.setText(clienteBusca.getCpfCliente());
                        txObsCliente.setText(clienteBusca.getObsCliente());
                        txBairro.setText(clienteBusca.getBairroCliente());                    
                        Date sqlDate = clienteBusca.getData();
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        String dataString = formatter.format(sqlDate);
                        txDataCadastro.setText(dataString);

                        Cidades cidadeRecebida = cidade.buscarCidadePeloId(clienteBusca.getIdCidade());
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
                        txConsultaCpf.setText("");
                        cbxNome.removeAllItems();
                        cbxNome.addItem("");
                    } else {
                        JOptionPane.showMessageDialog(null, "Cliente não encontrado");
                    }
                }
            }
        });
        
        btnExcluir.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int excluir = JOptionPane.showConfirmDialog(null, "Deseja excluir o cliente?", "Excluir", JOptionPane.YES_NO_OPTION);
                if (excluir == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(null, cliente.excluirUsuario(txCpf.getText()));
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
                
                txConsultaCpf.setText("");
                
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
                    txRua.getText().isEmpty() || txBairro.getText().isEmpty() || 
                    txCpf.getText().isEmpty() || idCidade == 0) {
                    JOptionPane.showMessageDialog(null, "Todos os campos obrigatórios devem ser preenchidos.");
                } else {
                    Clientes cliente = new Clientes(id, txNome.getText(), txCelular.getText(), 
                                          txEmail.getText(), txRua.getText(), txBairro.getText(), 
                                           txCpf.getText(), txObsCliente.getText(), idCidade, null);
                    ClienteDao clienteDao = new ClienteDao();
                    try {
                        String feedBack = clienteDao.salvar(cliente);
                        
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
                
                Clientes clienteBusca = cliente.buscarClientePeloCpf(txCpf.getText());
                id = clienteBusca.getIdCliente();
                
                Cidades cidadeRecebida = cidade.buscarCidadePeloId(clienteBusca.getIdCidade());
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
    
    private void desativarCampos() {        
        txBairro.setEnabled(false);
        txBairro.setCor(corCampoDesativado);
        txCelular.setEnabled(false);
        txCelular.setCor(corCampoDesativado);
        cbxNomeCidade.setEnabled(false);
        cbxNomeCidade.setBackground(corCampoDesativado);
        cbxNomeCidade.setCor(corCampoDesativado);
        txCpf.setEnabled(false);
        txCpf.setCor(corCampoDesativado);
        txDataCadastro.setEnabled(false);
        txDataCadastro.setCor(corCampoDesativado);
        txEmail.setEnabled(false);
        txEmail.setCor(corCampoDesativado);
        txNome.setEnabled(false);
        txNome.setCor(corCampoDesativado);
        txObsCliente.setEnabled(false);
        txObsCliente.setCor(corCampoDesativado);
        txRua.setEnabled(false);
        txRua.setCor(corCampoDesativado);
        cbxUfEstado.setEnabled(false);
        cbxUfEstado.setBackground(corCampoDesativado);
        cbxUfEstado.setCor(corCampoDesativado);
        btnAtualizar.setEnabled(false);
        btnAtualizar.setBackground(corCampoDesativado);
    }
    
        private void ativarCampos() {
        txBairro.setEnabled(true);
        txBairro.setCor(corCampoAtivado);
        txCelular.setEnabled(true);
        txCelular.setCor(corCampoAtivado);
        cbxNomeCidade.setEnabled(true);
        cbxNomeCidade.setBackground(corCampoAtivado);
        cbxNomeCidade.setCor(corCampoAtivado);
        txCpf.setEnabled(true);
        txCpf.setCor(corCampoAtivado);
        txDataCadastro.setEnabled(true);
        txDataCadastro.setCor(corCampoAtivado);
        txEmail.setEnabled(true);
        txEmail.setCor(corCampoAtivado);
        txNome.setEnabled(true);
        txNome.setCor(corCampoAtivado);
        txObsCliente.setEnabled(true);
        txObsCliente.setCor(corCampoAtivado);
        txRua.setEnabled(true);
        txRua.setCor(corCampoAtivado);
        cbxUfEstado.setEnabled(true);
        cbxUfEstado.setBackground(corCampoAtivado);
        cbxUfEstado.setCor(corCampoAtivado);
        btnAtualizar.setEnabled(true);
        btnAtualizar.setBackground(corCampoAtivado);
    }
    
    private void desativarCabecalho() {
        txConsultaCpf.setEnabled(false);
        txConsultaCpf.setCor(corCampoDesativado);
        cbxNome.setEnabled(false);
        cbxNome.setBackground(corCampoDesativado);
        cbxNome.setCor(corCampoDesativado);
        cbxNome.removeAllItems();
        cbxNome.addItem("");
        btnConsultaPeloCPF.setEnabled(false);
        btnConsultaPeloCPF.setBackground(corCampoDesativado);
        btnConsultaPeloNome.setEnabled(false);
        btnConsultaPeloNome.setBackground(corCampoDesativado);
    }
    
    private void ativarCabecalho() {
        txConsultaCpf.setEnabled(true);
        txConsultaCpf.setCor(corCampoAtivado);
        cbxNome.setEnabled(true);
        cbxNome.setBackground(corCampoAtivado);
        cbxNome.setCor(corCampoAtivado);
        btnConsultaPeloCPF.setEnabled(true);
        btnConsultaPeloCPF.setBackground(corCampoAtivado);
        btnConsultaPeloNome.setEnabled(true);
        btnConsultaPeloNome.setBackground(corCampoAtivado);
    }
    
    private void limparCampos() {
        txBairro.setText("");
        txCelular.reiniciarCampo();
        cbxNomeCidade.removeAllItems();
        cbxNomeCidade.addItem("");
        txCpf.setText("");
        txDataCadastro.setText("");
        txEmail.setText("");
        txNome.setText("");
        txObsCliente.setText("");
        txRua.setText("");
        cbxUfEstado.removeAllItems();
        cbxUfEstado.addItem("");
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        txConsultaCpf = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        txEmail = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        jSeparator1 = new javax.swing.JSeparator();
        lbId = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txCpf = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        jLabel5 = new javax.swing.JLabel();
        txNome = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        txDataCadastro = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txRua = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        txBairro = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        jLabel16 = new javax.swing.JLabel();
        btnExcluir = new com.mycompany.autostockcar.view.componentes.Botao();
        btnAlterar = new com.mycompany.autostockcar.view.componentes.Botao();
        btnNovo = new com.mycompany.autostockcar.view.componentes.Botao();
        btnSalvar = new com.mycompany.autostockcar.view.componentes.Botao();
        jLabel17 = new javax.swing.JLabel();
        txObsCliente = new com.mycompany.autostockcar.view.componentes.CampoDeTexto();
        btnConsultaPeloCPF = new com.mycompany.autostockcar.view.componentes.Botao();
        btnConsultaPeloNome = new com.mycompany.autostockcar.view.componentes.Botao();
        txCelular = new com.mycompany.autostockcar.view.componentes.CampoDeTelefone();
        btnCancelar = new com.mycompany.autostockcar.view.componentes.Botao();
        cbxNome = new com.mycompany.autostockcar.view.componentes.ComboBoxPersonalizado();
        cbxNomeCidade = new com.mycompany.autostockcar.view.componentes.ComboBoxPersonalizado();
        cbxUfEstado = new com.mycompany.autostockcar.view.componentes.ComboBoxPersonalizado();
        btnAtualizar = new com.mycompany.autostockcar.view.componentes.Botao();
        menu1 = new com.mycompany.autostockcar.view.componentes.Menu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        background.setBackground(new java.awt.Color(131, 191, 205));
        background.setPreferredSize(new java.awt.Dimension(806, 625));

        jPanel2.setBackground(new java.awt.Color(147, 211, 225));
        jPanel2.setPreferredSize(new java.awt.Dimension(1162, 550));

        txConsultaCpf.setForeground(new java.awt.Color(0, 0, 0));
        txConsultaCpf.setDicas("CPF/CNPJ");

        txEmail.setForeground(new java.awt.Color(0, 0, 0));
        txEmail.setDicas("E-mail ");
        txEmail.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jSeparator1.setForeground(new java.awt.Color(30, 30, 30));

        lbId.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        lbId.setForeground(new java.awt.Color(0, 0, 0));
        lbId.setText("CPF/CNPJ");

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Nome");

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Dados");

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("CPF/CNPJ");

        txCpf.setForeground(new java.awt.Color(0, 0, 0));
        txCpf.setDicas("CPF/CNPJ");
        txCpf.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Nome");

        txNome.setForeground(new java.awt.Color(0, 0, 0));
        txNome.setCor(new java.awt.Color(110, 202, 224));
        txNome.setDicas("Nome ");
        txNome.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txNome.setPreferredSize(new java.awt.Dimension(180, 30));

        jSeparator2.setForeground(new java.awt.Color(30, 30, 30));
        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel6.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Celular");

        jLabel8.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("E-MAIL");

        jSeparator3.setForeground(new java.awt.Color(30, 30, 30));

        txDataCadastro.setForeground(new java.awt.Color(0, 0, 0));
        txDataCadastro.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txDataCadastro.setDicas("Data ");
        txDataCadastro.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txDataCadastro.setPreferredSize(new java.awt.Dimension(180, 30));

        jLabel10.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Data do Cadastro");

        jLabel11.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Endereço");

        jLabel13.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Cidade");

        jLabel14.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText("Estado");

        jLabel15.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setText("Rua");

        txRua.setForeground(new java.awt.Color(0, 0, 0));
        txRua.setDicas("Rua");
        txRua.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txRua.setPreferredSize(new java.awt.Dimension(180, 30));

        txBairro.setForeground(new java.awt.Color(0, 0, 0));
        txBairro.setDicas("Bairro ");
        txBairro.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txBairro.setPreferredSize(new java.awt.Dimension(180, 30));

        jLabel16.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setText("Bairro");

        btnExcluir.setBackground(new java.awt.Color(131, 191, 205));
        btnExcluir.setForeground(new java.awt.Color(0, 0, 0));
        btnExcluir.setText("Excluir");
        btnExcluir.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N

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

        btnSalvar.setBackground(new java.awt.Color(131, 191, 205));
        btnSalvar.setForeground(new java.awt.Color(0, 0, 0));
        btnSalvar.setText("Salvar");
        btnSalvar.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        btnSalvar.setMaximumSize(new java.awt.Dimension(65, 30));
        btnSalvar.setMinimumSize(new java.awt.Dimension(65, 30));

        jLabel17.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 0));
        jLabel17.setText("Observações - Dados Adicionais");
        jLabel17.setToolTipText("");

        txObsCliente.setForeground(new java.awt.Color(0, 0, 0));
        txObsCliente.setDicas("Observações");
        txObsCliente.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txObsCliente.setPreferredSize(new java.awt.Dimension(180, 30));

        btnConsultaPeloCPF.setBackground(new java.awt.Color(131, 191, 205));
        btnConsultaPeloCPF.setForeground(new java.awt.Color(28, 181, 223));

        btnConsultaPeloNome.setBackground(new java.awt.Color(131, 191, 205));
        btnConsultaPeloNome.setForeground(new java.awt.Color(28, 181, 223));

        txCelular.setForeground(new java.awt.Color(0, 0, 0));
        txCelular.setDicas("");
        txCelular.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txCelular.setOpaque(true);

        btnCancelar.setBackground(new java.awt.Color(131, 191, 205));
        btnCancelar.setForeground(new java.awt.Color(0, 0, 0));
        btnCancelar.setText("Cancelar");
        btnCancelar.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        btnCancelar.setMaximumSize(new java.awt.Dimension(65, 30));
        btnCancelar.setMinimumSize(new java.awt.Dimension(65, 30));

        cbxNome.setForeground(new java.awt.Color(0, 0, 0));
        cbxNome.setToolTipText("");

        cbxNomeCidade.setEditable(true);
        cbxNomeCidade.setForeground(new java.awt.Color(0, 0, 0));

        cbxUfEstado.setEditable(true);
        cbxUfEstado.setForeground(new java.awt.Color(0, 0, 0));

        btnAtualizar.setBackground(new java.awt.Color(131, 191, 205));
        btnAtualizar.setForeground(new java.awt.Color(28, 181, 223));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel17)
                            .addComponent(txObsCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 577, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txEmail, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel4)
                                        .addComponent(txCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel6))
                                    .addGap(61, 61, 61)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel5)
                                        .addComponent(txNome, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txDataCadastro, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(txCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txRua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txBairro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbxNomeCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel16))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(cbxUfEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnAtualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addComponent(jSeparator1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbId)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txConsultaCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnConsultaPeloCPF, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(35, 35, 35)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(cbxNome, javax.swing.GroupLayout.PREFERRED_SIZE, 764, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnConsultaPeloNome, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(22, 22, 22))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbId)
                    .addComponent(jLabel2))
                .addGap(4, 4, 4)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txConsultaCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnConsultaPeloNome, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxNome, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(38, 38, 38)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel5))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(txCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel6))
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel17)
                                .addGap(0, 0, 0)
                                .addComponent(txObsCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txDataCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel14))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(cbxNomeCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cbxUfEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel15)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txRua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel16)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(149, 149, 149)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(btnAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(btnAtualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(btnConsultaPeloCPF, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 30, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(87, 87, 87)
                .addComponent(jSeparator2)
                .addContainerGap())
        );

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundLayout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(menu1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(59, 59, 59))
        );
        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(menu1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(31, 31, 31))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, 1280, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, 720, Short.MAX_VALUE)
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
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Client().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel background;
    private com.mycompany.autostockcar.view.componentes.Botao btnAlterar;
    private com.mycompany.autostockcar.view.componentes.Botao btnAtualizar;
    private com.mycompany.autostockcar.view.componentes.Botao btnCancelar;
    private com.mycompany.autostockcar.view.componentes.Botao btnConsultaPeloCPF;
    private com.mycompany.autostockcar.view.componentes.Botao btnConsultaPeloNome;
    private com.mycompany.autostockcar.view.componentes.Botao btnExcluir;
    private com.mycompany.autostockcar.view.componentes.Botao btnNovo;
    private com.mycompany.autostockcar.view.componentes.Botao btnSalvar;
    private com.mycompany.autostockcar.view.componentes.ComboBoxPersonalizado cbxNome;
    private com.mycompany.autostockcar.view.componentes.ComboBoxPersonalizado cbxNomeCidade;
    private com.mycompany.autostockcar.view.componentes.ComboBoxPersonalizado cbxUfEstado;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel lbId;
    private com.mycompany.autostockcar.view.componentes.Menu menu1;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txBairro;
    private com.mycompany.autostockcar.view.componentes.CampoDeTelefone txCelular;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txConsultaCpf;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txCpf;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txDataCadastro;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txEmail;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txNome;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txObsCliente;
    private com.mycompany.autostockcar.view.componentes.CampoDeTexto txRua;
    // End of variables declaration//GEN-END:variables
}
