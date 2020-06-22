package view.panels.cadastro.subCadastro;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import controller.ControllerCadastroDados;
import model.vo.cliente.ClienteVO;
import net.miginfocom.swing.MigLayout;
import util.helpers.Modificacoes;

public class PanelzinhoCadastroDados extends JPanel {

	private static final long serialVersionUID = 8795512428702538815L;
	private ControllerCadastroDados control;
	private Modificacoes modificacoes;
	private JTextField txtEmail, txtNome, txtCPF, txtRG, txtTelefone, txtPlaca;
	private JCheckBox chckbxBloquear;
	private JComboBox cbMarca;
	private JComboBox cbModelo;
	private JComboBox cbCor;

	public PanelzinhoCadastroDados() {

		this.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		this.setBackground(Color.WHITE);
		this.setLayout(new MigLayout("", "[grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow]",
				"[10px][grow][grow][grow][grow][grow][grow][10px][grow][10px][grow][grow][grow][grow][grow][10px]"));

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
		JLabel lblAdicionarDadosPessoais = new JLabel("Adicionar Dados Pessoais:");
		lblAdicionarDadosPessoais.setHorizontalAlignment(SwingConstants.CENTER);
		lblAdicionarDadosPessoais.setFont(new Font("Dialog", Font.BOLD, 26));
		add(lblAdicionarDadosPessoais, "cell 0 1 5 1,grow");

		JLabel lblNome = new JLabel("<html><body>Nome <a style=color:red>*</a></body></html>");
		lblNome.setHorizontalAlignment(SwingConstants.CENTER);
		lblNome.setFont(new Font("Arial", Font.BOLD, 14));
		this.add(lblNome, "cell 0 2 2 1,grow");

		JLabel lblCpf = new JLabel("<html><body>CPF <a style=color:red>*</a></body></html>");
		lblCpf.setHorizontalAlignment(SwingConstants.CENTER);
		lblCpf.setFont(new Font("Arial", Font.BOLD, 14));
		this.add(lblCpf, "cell 0 3 2 1,grow");

		JLabel lblRg = new JLabel("RG");
		lblRg.setHorizontalAlignment(SwingConstants.CENTER);
		lblRg.setFont(new Font("Arial", Font.BOLD, 14));
		this.add(lblRg, "cell 0 4 2 1,grow");

		JLabel lblEmail = new JLabel("E-Mail");
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmail.setFont(new Font("Arial", Font.BOLD, 14));
		this.add(lblEmail, "cell 0 5 2 1,grow");

		JLabel lblTelefone = new JLabel("<html><body>Fone <a style=color:red>*</a></body></html>");
		lblTelefone.setHorizontalAlignment(SwingConstants.CENTER);
		lblTelefone.setFont(new Font("Arial", Font.BOLD, 14));
		this.add(lblTelefone, "cell 0 6 2 1,grow");

		JLabel lblAdicionarVeculo = new JLabel("Adicionar Veículo:");
		lblAdicionarVeculo.setHorizontalAlignment(SwingConstants.CENTER);
		lblAdicionarVeculo.setFont(new Font("Arial", Font.BOLD, 26));
		this.add(lblAdicionarVeculo, "cell 0 10 4 1,grow");

		JLabel lblModelo = new JLabel("<html><body>Modelo <a style=color:red>*</a></body></html>");
		lblModelo.setHorizontalAlignment(SwingConstants.CENTER);
		lblModelo.setFont(new Font("Dialog", Font.BOLD, 14));
		this.add(lblModelo, "cell 0 14 2 1,grow");

		JLabel lblPlaca = new JLabel("<html><body>Placa <a style=color:red>*</a></body></html>");
		lblPlaca.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlaca.setFont(new Font("Dialog", Font.BOLD, 14));
		add(lblPlaca, "cell 0 11 2 1,grow");

		JLabel lblCor = new JLabel("<html><body>Cor <a style=color:red>*</a></body></html>");
		lblCor.setHorizontalAlignment(SwingConstants.CENTER);
		lblCor.setFont(new Font("Dialog", Font.BOLD, 14));
		add(lblCor, "cell 0 12 2 1,grow");

		JLabel lblMarca = new JLabel("<html><body>Marca <a style=color:red>*</a></body></html>");
		lblMarca.setHorizontalAlignment(SwingConstants.CENTER);
		lblMarca.setFont(new Font("Dialog", Font.BOLD, 14));
		add(lblMarca, "cell 0 13 2 1,grow");
	}

	private void setInputFields() {
		txtNome = new JTextField();
		txtNome.setBorder(new LineBorder(Color.BLACK, 1, true));
		txtNome.setFont(new Font("Arial", Font.BOLD, 14));
		this.add(txtNome, "cell 2 2 4 1,grow");
		txtNome.setColumns(10);

		txtCPF = new JTextField();
		txtCPF.setBorder(new LineBorder(Color.BLACK, 1, true));
		txtCPF.setFont(new Font("Arial", Font.BOLD, 14));
		txtCPF.setHorizontalAlignment(SwingConstants.LEFT);
		this.add(txtCPF, "cell 2 3 4 1,grow");
		txtCPF.setColumns(10);

		txtRG = new JTextField();
		txtRG.setFont(new Font("Arial", Font.BOLD, 14));
		txtRG.setBorder(new LineBorder(Color.BLACK, 1, true));
		this.add(txtRG, "cell 2 4 4 1,grow");
		txtRG.setColumns(10);

		txtEmail = new JTextField();
		txtEmail.setBorder(new LineBorder(Color.BLACK, 1, true));
		txtEmail.setFont(new Font("Arial", Font.BOLD, 14));
		this.add(txtEmail, "cell 2 5 4 1,grow");
		txtEmail.setColumns(10);

		txtTelefone = new JTextField();
		txtTelefone.setFont(new Font("Arial", Font.BOLD, 14));
		txtTelefone.setBorder(new LineBorder(Color.BLACK, 1, true));
		this.add(txtTelefone, "cell 2 6 4 1,grow");
		txtTelefone.setColumns(10);

		txtPlaca = new JTextField();
		txtPlaca.setFont(new Font("Dialog", Font.BOLD, 14));
		txtPlaca.setColumns(10);
		txtPlaca.setBorder(new LineBorder(Color.BLACK, 1, true));
		this.add(txtPlaca, "cell 2 11 4 1,grow");
	}

	private void setCheckBox() {
		chckbxBloquear = new JCheckBox("Bloquear?");
		chckbxBloquear.setBackground(Color.WHITE);
		chckbxBloquear.setHorizontalAlignment(SwingConstants.CENTER);
		chckbxBloquear.setFont(new Font("Arial", Font.BOLD, 14));
		this.add(chckbxBloquear, "cell 0 8 2 1,grow");
	}

	private void setComboBox() {
		cbCor = new JComboBox(control.preencherCbx_Cores());
		cbCor.setFont(new Font("Dialog", Font.BOLD, 16));
		cbCor.setBackground(Color.WHITE);
		add(cbCor, "cell 2 12 4 1,grow");

		cbMarca = new JComboBox(control.preencherCbx_Marca());
		cbMarca.setBackground(Color.WHITE);
		cbMarca.setFont(new Font("Dialog", Font.BOLD, 16));
		add(cbMarca, "cell 2 13 4 1,grow");

		cbModelo = new JComboBox(control.preencherCbx_Modelo());
		cbModelo.setFont(new Font("Dialog", Font.BOLD, 16));
		cbModelo.setBackground(Color.WHITE);
		add(cbModelo, "cell 2 14 4 1,grow");
	}

//	public ClienteVO returnForm() {
//	return control.getFormCliente();
//}

//public Modificacoes getModificacoes() {
//	return modificacoes;
//}

	public JTextField getTxtEmail() {
		return txtEmail;
	}

	public void setTxtEmail(JTextField txtEmail) {
		this.txtEmail = txtEmail;
	}

	public JTextField getTxtNome() {
		return txtNome;
	}

	public void setTxtNome(JTextField txtNome) {
		this.txtNome = txtNome;
	}

	public JTextField getTxtCPF() {
		return txtCPF;
	}

	public void setTxtCPF(JTextField txtCPF) {
		this.txtCPF = txtCPF;
	}

	public JTextField getTxtRG() {
		return txtRG;
	}

	public void setTxtRG(JTextField txtRG) {
		this.txtRG = txtRG;
	}

	public JTextField getTxtTelefone() {
		return txtTelefone;
	}

	public void setTxtTelefone(JTextField txtTelefone) {
		this.txtTelefone = txtTelefone;
	}

	public JTextField getTxtPlaca() {
		return txtPlaca;
	}

	public void setTxtPlaca(JTextField txtPlaca) {
		this.txtPlaca = txtPlaca;
	}

	public JCheckBox getChckbxBloquear() {
		return chckbxBloquear;
	}

	public void setChckbxBloquear(JCheckBox chckbxBloquear) {
		this.chckbxBloquear = chckbxBloquear;
	}

	public JComboBox getCbMarca() {
		return cbMarca;
	}

	public void setCbMarca(JComboBox cbMarca) {
		this.cbMarca = cbMarca;
	}

	public JComboBox getCbModelo() {
		return cbModelo;
	}

	public void setCbModelo(JComboBox cbModelo) {
		this.cbModelo = cbModelo;
	}

	public JComboBox getCbCor() {
		return cbCor;
	}

	public void setCbCor(JComboBox cbCor) {
		this.cbCor = cbCor;
	}

}