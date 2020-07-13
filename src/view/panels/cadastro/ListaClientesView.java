package view.panels.cadastro;

import controller.ControllerListaClientes;
import net.miginfocom.swing.MigLayout;
import util.constantes.ConstHelpers;
import util.helpers.Modificacoes;
import util.helpers.Util;
import view.panels.mainView.MainView;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class ListaClientesView extends JPanel {

    private static final long serialVersionUID = 3752138783055180091L;
    private ControllerListaClientes control;
    private Modificacoes modificacao;

    private JLabel lblSelecioneUmaLinha, lblTotalDeClientes;
    private JTextField txtProcurar;
    private JButton btnExcluir, btnAtualizar, btnCadastrar, btnProcurar;
    private JTable table;
    private JCheckBox cbConfirmaExclusao;

    public ListaClientesView() {

        this.setBounds(100, 100, 1028, 747);
        this.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        this.setLayout(new MigLayout("", "[10px][grow][grow][grow][grow][grow][grow][grow][10px]", "[10px][80px][20px][grow][20px][20px][grow][grow][grow][grow][grow][grow][10px]"));


        this.initialize();
    }

    private void initialize() {

        ConstHelpers.FLAG = 1;
        control = new ControllerListaClientes(this);
        modificacao = new Modificacoes();

        this.setJLabels_JSeparator();

        this.setInputFields();

        this.setCheckBox();

        this.setButtons();

        this.setJTable();

        this.addListeners();

        if (table.getRowCount() == 0 || table.getColumnCount() == 0) {
            control.atualizarTabela();
        }

    }

    private void setCheckBox() {
        cbConfirmaExclusao = new JCheckBox("<html><body>Deseja Excluir o<br>Cliente Selecionado?</body></html>");
        cbConfirmaExclusao.setFont(new Font("Arial", Font.BOLD, 12));
        cbConfirmaExclusao.setBackground(Color.WHITE);
        add(cbConfirmaExclusao, "cell 1 3,grow");
    }

    private void setJLabels_JSeparator() {

        String text = "<html><body>Cadastrar um Novo Cliente<p text-align:center> Ou </p><p>Selecione a Linha para Atualizar</p></body></html>";
        JLabel lblSelecionar = new JLabel(text);
        lblSelecionar.setBackground(Color.WHITE);
        lblSelecionar.setHorizontalAlignment(SwingConstants.LEFT);
        lblSelecionar.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblSelecionar, "flowx,cell 1 1,alignx left,growy");

        lblSelecioneUmaLinha = new JLabel("Selecione Uma Linha por Vez");
        lblSelecioneUmaLinha.setHorizontalAlignment(SwingConstants.LEFT);
        lblSelecioneUmaLinha.setFont(new Font("Arial", Font.BOLD, 12));
        lblSelecioneUmaLinha.setEnabled(false);
        add(lblSelecioneUmaLinha, "cell 2 4,growx,aligny top");

        lblTotalDeClientes = new JLabel("Total de Clientes Cadastrados:");
        lblTotalDeClientes.setBackground(Color.WHITE);
        lblTotalDeClientes.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblTotalDeClientes, "cell 1 5,grow");

    }

    private void setInputFields() {

        txtProcurar = new JTextField();
        txtProcurar.setFont(new Font("Arial", Font.BOLD, 16));
        txtProcurar.setBorder(new LineBorder(Color.BLACK, 1, true));
        txtProcurar.setBackground(Color.WHITE);
        txtProcurar.setText("Pesquisar");
        txtProcurar.setColumns(10);
        add(txtProcurar, "cell 5 1 2 1,grow");

    }

    private void setButtons() {

        btnCadastrar = new JButton("Cadatrar");
        btnCadastrar.setBackground(Color.decode("#35D073"));
        btnCadastrar.setIcon(new ImageIcon(ListaClientesView.class.getResource("/img/atualizacao-50.png")));
        btnCadastrar.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        btnCadastrar.setFont(new Font("Arial", Font.BOLD, 20));
        add(btnCadastrar, "cell 2 1,grow");

        btnAtualizar = new JButton("Atualizar");
        btnAtualizar.setBackground(Color.WHITE);
        btnAtualizar.setIcon(new ImageIcon(ListaClientesView.class.getResource("/img/atualizacao-50.png")));
        btnAtualizar.setFont(new Font("Arial", Font.BOLD, 20));
        btnAtualizar.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        btnAtualizar.setEnabled(false);
        add(btnAtualizar, "cell 3 1,grow");

        btnExcluir = new JButton("Excluir");
        btnExcluir.setIcon(new ImageIcon(ListaClientesView.class.getResource("/img/icons8-apagar-para-sempre-38.png")));
        btnExcluir.setFont(new Font("Arial", Font.BOLD, 20));
        btnExcluir.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        btnExcluir.setBackground(Color.WHITE);
        btnExcluir.setEnabled(false);
        add(btnExcluir, "cell 2 3,grow");

        btnProcurar = new JButton("Pesquisar");
        btnProcurar.setBackground(new Color(100, 149, 237));
        btnProcurar.setFont(new Font("Arial", Font.BOLD, 16));
        btnProcurar.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        add(btnProcurar, "cell 7 1,grow");

    }

    private void setJTable() {

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBackground(Color.WHITE);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane, "cell 1 6 7 6,grow");

        table = new JTable(new DefaultTableModel()) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = modificacao.tableLookAndFiel(table);
        scrollPane.setViewportView(table);
    }

    private void addListeners() {

        txtProcurar.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                txtProcurar.setText("");
                txtProcurar.setForeground(Color.BLACK);
            }
        });

        Util.habilitarOpcoes(table, btnAtualizar, "#FF8C00", 2, null);

        cbConfirmaExclusao.addActionListener(e -> {

            if (cbConfirmaExclusao.isSelected()) {
                btnExcluir.setEnabled(true);
                btnExcluir.setBackground(Color.decode("#F85C50"));
                lblSelecioneUmaLinha.setEnabled(true);
            } else {
                btnExcluir.setEnabled(false);
                btnExcluir.setBackground(Color.WHITE);
                lblSelecioneUmaLinha.setEnabled(false);
            }

        });

        btnCadastrar.addActionListener(e -> {
            MainView.limparDadosDasTelasCadastro();
            MainView.swithPanel(MainView.getCadastroView());
        });

        btnAtualizar.addActionListener(e -> {
            MainView.limparDadosDasTelasCadastro();
            ConstHelpers.FLAG = 1;
            control.atualizarObjeto();
        });

        btnExcluir.addActionListener(e -> control.removeSelectedRow());

        btnProcurar.addActionListener(e -> control.consultar());
    }

    public JLabel getLblTotalDeClientes() {
        return lblTotalDeClientes;
    }

    public JTextField getTxtProcurar() {
        return txtProcurar;
    }

    public JButton getBtnExcluir() {
        return btnExcluir;
    }

    public JButton getBtnAtualizar() {
        return btnAtualizar;
    }

    public JButton getBtnCadastrar() {
        return btnCadastrar;
    }

    public JButton getBtnProcurar() {
        return btnProcurar;
    }

    public JTable getTable() {
        return table;
    }

    public JCheckBox getCbConfirmaExclusao() {
        return cbConfirmaExclusao;
    }

    public Modificacoes getModificacao() {
        return modificacao;
    }

}
