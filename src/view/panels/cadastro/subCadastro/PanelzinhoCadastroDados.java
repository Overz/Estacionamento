package view.panels.cadastro.subCadastro;

import controller.ControllerCadastroDados;
import model.vo.cliente.ClienteVO;
import net.miginfocom.swing.MigLayout;
import util.constantes.ConstHelpers;
import util.helpers.Modificacoes;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;

import static util.helpers.Modificacoes.addMask;
import static util.helpers.Modificacoes.addMyFocusListener;

public class PanelzinhoCadastroDados extends JPanel {

    private static final long serialVersionUID = 8795512428702538815L;
    private ControllerCadastroDados control;
    private Modificacoes modificacoes;
    private JTextField txtEmail, txtNome;
    private JFormattedTextField txtCPF, txtRG, txtTelefone, txtPlaca;
    private JCheckBox chckbxBloquear;
    private JComboBox cbCor;
    private JComboBox cbMarca;
    private JComboBox cbModelo;

    public PanelzinhoCadastroDados() {

        this.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        this.setBackground(Color.WHITE);
        this.setLayout(new MigLayout("", "[grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow]", "[10px][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][10px]"));

        this.initialize();
    }

    private void initialize() {

        modificacoes = new Modificacoes();
        control = new ControllerCadastroDados(this);

        setJLabels_JSeparator();

        setInputFields();

        setCheckBox();

        setComboBox();

    }

    private void setJLabels_JSeparator() {
        JLabel lblNome = new JLabel("<html><body>Nome <a style=color:red>*</a></body></html>");
        lblNome.setHorizontalAlignment(SwingConstants.CENTER);
        lblNome.setFont(new Font("Arial", Font.BOLD, 14));
        this.add(lblNome, "cell 0 1 2 1,grow");

        JLabel lblCpf = new JLabel("<html><body>CPF <a style=color:red>*</a></body></html>");
        lblCpf.setHorizontalAlignment(SwingConstants.CENTER);
        lblCpf.setFont(new Font("Arial", Font.BOLD, 14));
        this.add(lblCpf, "cell 0 2 2 1,grow");

        JLabel lblRg = new JLabel("RG");
        lblRg.setHorizontalAlignment(SwingConstants.CENTER);
        lblRg.setFont(new Font("Arial", Font.BOLD, 14));
        this.add(lblRg, "cell 0 3 2 1,grow");

        JLabel lblEmail = new JLabel("E-Mail");
        lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
        lblEmail.setFont(new Font("Arial", Font.BOLD, 14));
        this.add(lblEmail, "cell 0 4 2 1,grow");

        JLabel lblTelefone = new JLabel("<html><body>Fone <a style=color:red>*</a></body></html>");
        lblTelefone.setHorizontalAlignment(SwingConstants.CENTER);
        lblTelefone.setFont(new Font("Arial", Font.BOLD, 14));
        this.add(lblTelefone, "cell 0 5 2 1,grow");

        JLabel lblPlaca = new JLabel("<html><body>Placa <a style=color:red>*</a></body></html>");
        lblPlaca.setHorizontalAlignment(SwingConstants.CENTER);
        lblPlaca.setFont(new Font("Dialog", Font.BOLD, 14));
        add(lblPlaca, "cell 0 7 2 1,growx");

        JLabel lblCor = new JLabel("<html><body>Cor <a style=color:red>*</a></body></html>");
        lblCor.setHorizontalAlignment(SwingConstants.CENTER);
        lblCor.setFont(new Font("Dialog", Font.BOLD, 14));
        add(lblCor, "cell 0 8 2 1,grow");

        JLabel lblMarca = new JLabel("<html><body>Marca <a style=color:red>*</a></body></html>");
        lblMarca.setHorizontalAlignment(SwingConstants.CENTER);
        lblMarca.setFont(new Font("Dialog", Font.BOLD, 14));
        add(lblMarca, "cell 0 9 2 1,grow");

        JLabel lblModelo = new JLabel("<html><body>Modelo <a style=color:red>*</a></body></html>");
        lblModelo.setHorizontalAlignment(SwingConstants.CENTER);
        lblModelo.setFont(new Font("Dialog", Font.BOLD, 14));
        add(lblModelo, "cell 0 10 2 1,grow");
    }

    private void setInputFields() {
        txtNome = new JTextField();
        txtNome.setBorder(new LineBorder(Color.BLACK, 1, true));
        txtNome.setFont(new Font("Arial", Font.BOLD, 14));
        this.add(txtNome, "cell 2 1 4 1,grow");
        txtNome.setColumns(10);

        txtCPF = new JFormattedTextField(addMask(new MaskFormatter(), ConstHelpers.MASK_CPF, ""));
        txtCPF.setBorder(new LineBorder(Color.BLACK, 1, true));
        txtCPF.setFont(new Font("Arial", Font.BOLD, 14));
        txtCPF.setHorizontalAlignment(SwingConstants.LEFT);
        this.add(txtCPF, "cell 2 2 4 1,grow");
        txtCPF.setColumns(10);

        txtRG = new JFormattedTextField(addMask(new MaskFormatter(), ConstHelpers.MASK_RG, ""));
        txtRG.setFont(new Font("Arial", Font.BOLD, 14));
        txtRG.setBorder(new LineBorder(Color.BLACK, 1, true));
        this.add(txtRG, "cell 2 3 4 1,grow");
        txtRG.setColumns(10);

        txtEmail = new JTextField();
        txtEmail.setBorder(new LineBorder(Color.BLACK, 1, true));
        txtEmail.setFont(new Font("Arial", Font.BOLD, 14));
        this.add(txtEmail, "cell 2 4 4 1,grow");
        txtEmail.setColumns(10);

        txtTelefone = new JFormattedTextField(addMask(new MaskFormatter(), ConstHelpers.MASK_FONE_11, ""));
        txtTelefone.setFont(new Font("Arial", Font.BOLD, 14));
        txtTelefone.setBorder(new LineBorder(Color.BLACK, 1, true));
        this.add(txtTelefone, "cell 2 5 4 1,grow");
        txtTelefone.setColumns(10);

        txtPlaca = new JFormattedTextField(addMask(new MaskFormatter(), ConstHelpers.MASK_PLATE_7, ""));
        txtPlaca.setFont(new Font("Dialog", Font.BOLD, 14));
        txtPlaca.setColumns(10);
        txtPlaca.setBorder(new LineBorder(Color.BLACK, 1, true));
        add(txtPlaca, "cell 2 7 4 1,grow");

        this.addListeners();
    }

    private void setCheckBox() {
        chckbxBloquear = new JCheckBox("Bloquear?");
        chckbxBloquear.setBackground(Color.WHITE);
        chckbxBloquear.setHorizontalAlignment(SwingConstants.CENTER);
        chckbxBloquear.setFont(new Font("Arial", Font.BOLD, 14));
        this.add(chckbxBloquear, "cell 0 6 2 1,grow");
    }

    private void setComboBox() {
        cbCor = new JComboBox(control.preencherCbx_Cores());
        cbCor.setBackground(Color.WHITE);
        add(cbCor, "cell 2 8 4 1,grow");

        cbMarca = new JComboBox(control.preencherCbx_Marca());
        cbMarca.setBackground(Color.WHITE);
        add(cbMarca, "cell 2 9 4 1,grow");

        cbModelo = new JComboBox(control.preencherCbx_Modelo());
        cbModelo.setBackground(Color.WHITE);
        add(cbModelo, "cell 2 10 4 1,grow");
    }

    private void addListeners() {
        txtCPF.addFocusListener(addMyFocusListener(txtCPF, ConstHelpers.REGEX_CPF));
        txtRG.addFocusListener(addMyFocusListener(txtRG, ConstHelpers.REGEX_NUMEROS));
        txtTelefone.addFocusListener(addMyFocusListener(txtTelefone, ConstHelpers.REGEX_TELEFONE));
    }

    public ClienteVO returnForm() {
        return control.getFormCliente();
    }

    public Modificacoes getModificacoes() {
        return modificacoes;
    }

    public JTextField getTxtEmail() {
        return txtEmail;
    }

    public JTextField getTxtNome() {
        return txtNome;
    }

    public JFormattedTextField getTxtCPF() {
        return txtCPF;
    }

    public JFormattedTextField getTxtRG() {
        return txtRG;
    }

    public JFormattedTextField getTxtTelefone() {
        return txtTelefone;
    }

    public JCheckBox getChckbxBloquear() {
        return chckbxBloquear;
    }

    public JFormattedTextField getTxtPlaca() {
        return txtPlaca;
    }

    public JComboBox getCbCor() {
        return cbCor;
    }

    public JComboBox getCbMarca() {
        return cbMarca;
    }

    public JComboBox getCbModelo() {
        return cbModelo;
    }
}