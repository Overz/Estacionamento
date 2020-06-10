package view.panels.mainView;

import controller.ControllerInicio;
import net.miginfocom.swing.MigLayout;
import util.constantes.ConstHelpers;
import util.constantes.ConstInicio;
import util.helpers.Modificacoes;
import util.helpers.Util;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.Objects;

public class InicioView extends JPanel {

    private static final long serialVersionUID = -8394009250133780042L;

    private ControllerInicio control;
    private Modificacoes modificacao;

    private JComboBox cbFormaPgto, cbxProcurar;
    private JTable table;
    private JButton btnCancelar, btnValidar, btnGerarTicket, btnProcurar,
            btnImprimirComprovanteTabela, btnRemover, btnAbrirEntrada, btnAbrirSaida;
    private JTextField txtTicket;
    private JLabel lblTotalDeVeiculos, lblCancelaEntrada, lblCancelaSaída, lblModificacao;
    private JTextField txtProcurar;

    public InicioView() {

        this.setBounds(100, 100, 1122, 789);
        this.setBackground(Color.WHITE);
        this.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        this.setLayout(new MigLayout("", "[10px][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][10px]",
                "[10px][grow][grow][grow][grow][grow][grow][20px][grow][20px][grow][grow][grow][grow][grow][grow][grow][10px][grow][grow][10px]"));

        this.initialize();
    }

    private void initialize() {
        ConstHelpers.FLAG = 0;
        ConstHelpers.SUB_FLAG = 0;

        modificacao = new Modificacoes();
        control = new ControllerInicio(this);

        setJLabels_JSeparator();

        setInputFields();

        setButtons();

        setJTable();

        setComboBox();

        if (table.getRowCount() == 0 || table.getColumnCount() == 0) {
            control.atualizarTabela();
        }

        addListeners();

    }

    private void setJLabels_JSeparator() {
        JSeparator separatorCima = new JSeparator();
        separatorCima.setBackground(Color.BLACK);
        separatorCima.setForeground(Color.BLACK);
        add(separatorCima, "flowx,cell 1 1 2 1,growx,aligny bottom");

        JSeparator separatorMeio = new JSeparator();
        separatorMeio.setBackground(Color.BLACK);
        separatorMeio.setForeground(Color.BLACK);
        separatorMeio.setOrientation(SwingConstants.VERTICAL);
        add(separatorMeio, "cell 3 1 1 19,alignx center,growy");

        JSeparator separatorBaixo = new JSeparator();
        separatorBaixo.setForeground(Color.BLACK);
        separatorBaixo.setBackground(Color.BLACK);
        add(separatorBaixo, "cell 1 3 2 1,growx,aligny top");

        JLabel lblRegistroSaida = new JLabel("Registro de Saída");
        lblRegistroSaida.setHorizontalAlignment(SwingConstants.CENTER);
        lblRegistroSaida.setFont(new Font("Arial", Font.BOLD, 20));
        add(lblRegistroSaida, "cell 1 2 2 1,grow");

        JLabel lblImprimirLinhaSelecionada = new JLabel("Remover Linha Selecionada:");
        lblImprimirLinhaSelecionada.setFont(new Font("Arial", Font.BOLD, 16));
        lblImprimirLinhaSelecionada.setBackground(Color.WHITE);
        add(lblImprimirLinhaSelecionada, "cell 4 18 4 2,grow");

        lblTotalDeVeiculos = new JLabel("Total de Veiculos Atuais:");
        lblTotalDeVeiculos.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblTotalDeVeiculos, "cell 4 1 2 1,grow");

        lblCancelaEntrada = new JLabel("Cancela Fechada");
        lblCancelaEntrada.setBackground(Color.decode("#F85C50"));
        lblCancelaEntrada.setOpaque(true);
        lblCancelaEntrada.setFont(new Font("Arial", Font.BOLD, 14));
        lblCancelaEntrada.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblCancelaEntrada, "cell 1 14 2 1,grow");

        lblCancelaSaída = new JLabel("Cancela Fechada");
        lblCancelaSaída.setBackground(Color.decode("#F85C50"));
        lblCancelaSaída.setOpaque(true);
        lblCancelaSaída.setFont(new Font("Arial", Font.BOLD, 14));
        lblCancelaSaída.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblCancelaSaída, "cell 1 16 2 1,grow");
    }

    private void setInputFields() {
        txtTicket = new JTextField("Nº Ticket");
        txtTicket.setFont(new Font("Arial", Font.BOLD, 20));
        txtTicket.setBorder(new LineBorder(Color.BLACK));
        txtTicket.setBackground(Color.WHITE);
        txtTicket.setColumns(10);
        add(txtTicket, "cell 1 5 2 1,grow");

        txtProcurar = new JTextField("Procurar...");
        txtProcurar.setFont(new Font("Arial", Font.BOLD, 16));
        txtProcurar.setFocusAccelerator('u');
        txtProcurar.setColumns(10);
        txtProcurar.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        add(txtProcurar, "cell 6 2 7 1,grow");
    }

    private void setButtons() {
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setIcon(new ImageIcon(InicioView.class.getResource("/img/icons8-excluir-50.png")));
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 18));
        btnCancelar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCancelar.setBackground(Color.decode("#F85C50"));
        btnCancelar.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        add(btnCancelar, "cell 1 6,grow");

        btnValidar = new JButton("Validar");
        btnValidar.setIcon(new ImageIcon(InicioView.class.getResource("/img/icons8-selecionado-50.png")));
        btnValidar.setFont(new Font("Arial", Font.BOLD, 18));
        btnValidar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnValidar.setBackground(Color.decode("#35D073"));
        btnValidar.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        add(btnValidar, "cell 2 6,grow");

        btnRemover = new JButton("Remover Ticket / Cliente");
        btnRemover.setEnabled(false);
        btnRemover.setBackground(Color.WHITE);
        btnRemover.setFont(new Font("Arial", Font.BOLD, 16));
        btnRemover.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        btnRemover.setToolTipText("Remove os Clientes ou Tickets na Tabela");
        add(btnRemover, "cell 12 18 3 2,grow");

        btnGerarTicket = new JButton("Gerar Ticket");
        btnGerarTicket.setBackground(new Color(100, 149, 237));
        btnGerarTicket.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        btnGerarTicket.setIcon(new ImageIcon(InicioView.class.getResource("/img/icons8-enviar-para-a-impressora-50.png")));
        btnGerarTicket.setFont(new Font("Arial", Font.BOLD, 16));
        add(btnGerarTicket, "cell 1 10 2 1,grow");

        btnAbrirEntrada = new JButton("Abrir Entrada");
        btnAbrirEntrada.setBackground(Color.WHITE);
        btnAbrirEntrada.setIcon(new ImageIcon(InicioView.class.getResource("/img/icons8-sinal-de-aberto-54.png")));
        btnAbrirEntrada.setFont(new Font("Arial", Font.BOLD, 16));
        btnAbrirEntrada.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        add(btnAbrirEntrada, "cell 1 13 2 1,grow");

        btnAbrirSaida = new JButton("Abrir Saída");
        btnAbrirSaida.setBackground(Color.WHITE);
        btnAbrirSaida.setIcon(new ImageIcon(InicioView.class.getResource("/img/icons8-sinal-de-aberto-54.png")));
        btnAbrirSaida.setFont(new Font("Arial", Font.BOLD, 16));
        btnAbrirSaida.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        add(btnAbrirSaida, "cell 1 15 2 1,grow");

        btnProcurar = new JButton("Procurar");
        btnProcurar.setBackground(new Color(100, 149, 237));
        btnProcurar.setFont(new Font("Arial", Font.BOLD, 16));
        btnProcurar.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        add(btnProcurar, "cell 13 2 2 1,grow");

    }

    private void setJTable() {
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBackground(Color.WHITE);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(null);

        table = new JTable(new DefaultTableModel()) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = modificacao.tableLookAndFiel(table);
        scrollPane.setViewportView(table);
        add(scrollPane, "cell 4 3 11 14,grow");
    }

    private void setComboBox() {
        ArrayList<String> formaPgto = new ArrayList<>();
        formaPgto.add(ConstInicio.PGTO_DINHEIRO);
        formaPgto.add(ConstInicio.PGTO_CARTAO);

        cbFormaPgto = new JComboBox<>(formaPgto.toArray());
        cbFormaPgto.setFont(new Font("Arial", Font.BOLD, 20));
        cbFormaPgto.setBackground(Color.WHITE);
        add(cbFormaPgto, "cell 1 8 2 1,grow");

        ArrayList<String> tipoProcura = new ArrayList<>();
        tipoProcura.add(ConstInicio.PROCURA);
        tipoProcura.add(ConstInicio.PROCURA_CLIENTE);
        tipoProcura.add(ConstInicio.PROCURA_CARRO);
        tipoProcura.add(ConstInicio.PROCURA_TICKET_CARTAO);

        cbxProcurar = new JComboBox<>(tipoProcura.toArray());
        cbxProcurar.setFont(new Font("Arial", Font.BOLD, 18));
        cbxProcurar.setBackground(Color.WHITE);
        add(cbxProcurar, "cell 4 2 2 1,grow");
    }

    private void addListeners() {

        Util.habilitarOpcoes(table, btnRemover, "#FF8C00", 2);

        txtTicket.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                txtTicket.setText("");
                txtTicket.setForeground(Color.BLACK);
            }
        });
        txtProcurar.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                txtProcurar.setText("");
                txtProcurar.setForeground(Color.BLACK);
            }
        });

        btnCancelar.addActionListener(e -> txtTicket.setText("Nº Ticket"));

        btnValidar.addActionListener(e -> {
            String ticket = txtTicket.getText().trim();
            String tipoPgto = Objects.requireNonNull(cbFormaPgto.getSelectedItem()).toString();
            control.realizarSaida(tipoPgto, ticket);
            control.atualizarTabela();
            ConstHelpers.FLAG = 2;
        });

        btnProcurar.addActionListener(e -> {
            String tipo = (String) cbxProcurar.getSelectedItem();
            String valor = txtProcurar.getText();
            control.consultar(tipo, valor);
            control.atualizarTabela();
            ConstHelpers.FLAG = 0;
        });

        btnRemover.addActionListener(e -> control.removeSelectedRow());

        btnGerarTicket.addActionListener(e -> control.gerarTicket());

        control.controlarCancela(btnAbrirEntrada, lblCancelaEntrada);

        control.controlarCancela(btnAbrirSaida, lblCancelaSaída);
    }

    public JTable getTable() {
        return table;
    }

    public Modificacoes getModificacao() {
        return modificacao;
    }

    public JComboBox getCbFormaPgto() {
        return cbFormaPgto;
    }

    public JButton getBtnAbrirEntrada() {
        return btnAbrirEntrada;
    }

    public JButton getBtnAbrirSaida() {
        return btnAbrirSaida;
    }

    public JButton getBtnImprimirComprovanteTabela() {
        return btnImprimirComprovanteTabela;
    }

    public JTextField getTxtTicket() {
        return txtTicket;
    }

    public JTextField getTxtProcurar() {
        return txtProcurar;
    }

    public JLabel getLblTotalDeVeiculos() {
        return lblTotalDeVeiculos;
    }

    public JLabel getLblCancelaEntrada() {
        return lblCancelaEntrada;
    }

    public JLabel getLblCancelaSaída() {
        return lblCancelaSaída;
    }

    public JLabel getLblModificacao() {
        return lblModificacao;
    }

}