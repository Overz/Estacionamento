package view.panels;

import controller.ControllerLostTicket;
import net.miginfocom.swing.MigLayout;
import util.constantes.ConstHelpers;
import util.constantes.ConstInicio;
import util.helpers.Util;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.util.ArrayList;

import static util.helpers.Modificacoes.addMask;

public class LostTicketView extends JPanel {

    private static final long serialVersionUID = -5438476599911734590L;
    private ControllerLostTicket control;

    private ArrayList<String> tiposPagamento;
    private JComboBox<Object> comboBox;
    private JTextField txtNome, txtCPF, txtPlaca, txtRenavam;
    private JButton btnSalvar, btnLimpar;

    public LostTicketView() {

        this.setBounds(100, 100, 902, 704);
        this.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        this.setLayout(new MigLayout("", "[10px][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][10px]", "[10px][grow][20px][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][10px]"));

        this.initialize();
    }

    public void initialize() {

        control = new ControllerLostTicket(this);

        setJLabels_JSeparator();

        setInputFields();

        setButtons();

        addListeners();

    }

    public void setJLabels_JSeparator() {
        JLabel lblRegistroDeTicket = new JLabel("Registro de Ticket/Cartão Perdido");
        lblRegistroDeTicket.setHorizontalAlignment(SwingConstants.CENTER);
        lblRegistroDeTicket.setFont(new Font("Arial", Font.BOLD, 30));
        lblRegistroDeTicket.setBackground(Color.WHITE);
        add(lblRegistroDeTicket, "cell 1 1 13 1,grow");

        JLabel lblNome = new JLabel("<html><body>Nome <a style=color:red>*</a></body></html>:");
        lblNome.setFont(new Font("Arial", Font.BOLD, 14));
        lblNome.setBackground(Color.WHITE);
        add(lblNome, "cell 2 4 2 1,grow");

        JLabel lblNewLabel = new JLabel("<html><body>CPF <a style=color:red>*</a></body></html>:");
        lblNewLabel.setFont(new Font("Arial", Font.BOLD, 14));
        lblNewLabel.setBackground(Color.WHITE);
        add(lblNewLabel, "cell 2 5 2 1,grow");

        JLabel lblPlaca = new JLabel("<html><boody>PLACA <a style=color:red>*</a></body></html>:");
        lblPlaca.setFont(new Font("Arial", Font.BOLD, 14));
        lblPlaca.setBackground(Color.WHITE);
        add(lblPlaca, "cell 2 6 2 1,grow");

        JLabel lblRenavam = new JLabel("<html><boody>RENAVAM <a style=color:red>*</a></body></html>:");
        lblRenavam.setFont(new Font("Arial", Font.BOLD, 14));
        lblRenavam.setBackground(Color.WHITE);
        add(lblRenavam, "cell 2 7 2 1,grow");

        JLabel lblValorASer = new JLabel("<html><body>VALOR A<br>SER PAGO:<br>(Meia Entrada)</body></html>");
        lblValorASer.setFont(new Font("Arial", Font.BOLD, 14));
        lblValorASer.setBackground(Color.WHITE);
        add(lblValorASer, "cell 2 8 2 1,grow");

        JLabel lblPgto = new JLabel("R$: " + Util.formatarValor(ConstHelpers.LOST_TICKET));
        lblPgto.setHorizontalAlignment(SwingConstants.CENTER);
        lblPgto.setFont(new Font("Arial", Font.BOLD, 32));
        lblPgto.setBackground(Color.BLACK);
        add(lblPgto, "cell 4 8 5 1,grow");

        JLabel lblformaDepagamento = new JLabel("<html><body>FORMA DE<br>PAGAMENTO <a style=color:red>*</a>:</body></html>");
        lblformaDepagamento.setFont(new Font("Arial", Font.BOLD, 14));
        lblformaDepagamento.setBackground(Color.WHITE);
        add(lblformaDepagamento, "cell 2 9 2 1,alignx left,growy");
    }

    public void setInputFields() {
        txtNome = new JTextField();
        txtNome.setFont(new Font("Arial", Font.BOLD, 14));
        txtNome.setBorder(new LineBorder(Color.BLACK, 1, true));
        add(txtNome, "cell 4 4 5 1,grow");
        txtNome.setColumns(10);

        txtCPF = new JFormattedTextField(addMask(new MaskFormatter(), ConstHelpers.MASK_RENAVAM_11, ""));
        txtCPF.setFont(new Font("Arial", Font.BOLD, 14));
        txtCPF.setBorder(new LineBorder(Color.BLACK, 1, true));
        txtCPF.setColumns(10);
        add(txtCPF, "cell 4 5 5 1,grow");

        txtPlaca = new JFormattedTextField(addMask(new MaskFormatter(), ConstHelpers.MASK_PLATE_7, ""));
        txtPlaca.setFont(new Font("Arial", Font.BOLD, 14));
        txtPlaca.setBorder(new LineBorder(Color.BLACK, 1, true));
        txtPlaca.setColumns(10);
        add(txtPlaca, "cell 4 6 5 1,grow");

        txtRenavam = new JFormattedTextField(addMask(new MaskFormatter(), ConstHelpers.MASK_CPF, ""));
        txtRenavam.setFont(new Font("Arial", Font.BOLD, 14));
        txtRenavam.setBorder(new LineBorder(Color.BLACK, 1, true));
        txtRenavam.setColumns(10);
        add(txtRenavam, "cell 4 7 5 1,grow");
    }

    public void setButtons() {
        tiposPagamento = new ArrayList<>();
        tiposPagamento.add(ConstInicio.VAZIO);
        tiposPagamento.add(ConstInicio.PGTO_DINHEIRO);
        tiposPagamento.add(ConstInicio.PGTO_CARTAO);

        comboBox = new JComboBox<>(tiposPagamento.toArray());
        comboBox.setFont(new Font("Arial", Font.BOLD, 14));
        comboBox.setBorder(new LineBorder(Color.BLACK, 1, true));
        comboBox.setBackground(Color.WHITE);
        add(comboBox, "cell 4 9 5 1,grow");

        btnSalvar = new JButton("Salvar");
        btnSalvar.setFont(new Font("Arial", Font.BOLD, 20));
        btnSalvar.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        add(btnSalvar, "cell 4 11 5 1,grow");

        btnLimpar = new JButton("Limpar");
        btnLimpar.setFont(new Font("Arial", Font.BOLD, 20));
        btnLimpar.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        add(btnLimpar, "cell 4 12 5 1, grow");
    }

    private void addListeners() {
        btnSalvar.addActionListener(e -> control.cadastrar());
        btnLimpar.addActionListener(e -> control.limparDados());
    }

    public JTextField getTxtNome() {
        return txtNome;
    }

    public JTextField getTxtCPF() {
        return txtCPF;
    }

    public JTextField getTxtPlaca() {
        return txtPlaca;
    }

    public JTextField getTxtRenavam() {
        return txtRenavam;
    }

    public JComboBox<Object> getComboBox() {
        return comboBox;
    }
}
