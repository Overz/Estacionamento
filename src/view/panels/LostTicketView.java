package view.panels;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

public class LostTicketView extends JPanel {

	private static final long serialVersionUID = -5438476599911734590L;
	private static final String SELECIONE = "~ SELECIONE ~";
	private static final String DINHEIRO = "1 - DINHEIRO";
	private static final String CARTAO = "2 - CARTÃO";
	
	private ArrayList<String> tiposPagamento;
	private JTextField txtNome;
	private JTextField txtCPF;
	private JTextField txtPlaca;
	private JTextField txtRenavam;

	/**
	 * Inicia a Tela
	 */
	public LostTicketView() {
		
		this.setBounds(100, 100, 902, 704);
		this.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		this.setLayout(new MigLayout("", "[10px][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][10px]", "[10px][grow][20px][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][10px]"));
		
		this.initialize();
	}

	/**
	 * Adiciona Todos os Componentes para a Tela
	 */
	private void initialize() {
		
		setJLabels_JSeparetors();
		
		setInputFields();
		
		setValidationButtons();
		
	}

	/**
	 * Adiciona os JLabels a tela & JSeparators
	 */
	private void setJLabels_JSeparetors() {
		JLabel lblRegistroDeTicket = new JLabel("Registro de Ticket Perdido");
		lblRegistroDeTicket.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistroDeTicket.setFont(new Font("Arial", Font.BOLD, 30));
		lblRegistroDeTicket.setBackground(Color.WHITE);
		add(lblRegistroDeTicket, "cell 1 1 13 1,grow");
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setFont(new Font("Arial", Font.BOLD, 14));
		lblNome.setBackground(Color.WHITE);
		add(lblNome, "cell 2 4 2 1,grow");
		
		JLabel lblNewLabel = new JLabel("CPF:");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 14));
		lblNewLabel.setBackground(Color.WHITE);
		add(lblNewLabel, "cell 2 5 2 1,grow");
		
		JLabel lblPlaca = new JLabel("PLACA:");
		lblPlaca.setFont(new Font("Arial", Font.BOLD, 14));
		lblPlaca.setBackground(Color.WHITE);
		add(lblPlaca, "cell 2 6 2 1,grow");
		
		JLabel lblRenavam = new JLabel("RENAVAM:");
		lblRenavam.setFont(new Font("Arial", Font.BOLD, 14));
		lblRenavam.setBackground(Color.WHITE);
		add(lblRenavam, "cell 2 7 2 1,grow");
		
		JLabel lblValorASer = new JLabel("<html><body>VALOR A<br>SER PAGO:</body></html>");
		lblValorASer.setFont(new Font("Arial", Font.BOLD, 14));
		lblValorASer.setBackground(Color.WHITE);
		add(lblValorASer, "cell 2 8 2 1,grow");
		
		JLabel lblPgto = new JLabel("valor a ser pago");
		lblPgto.setHorizontalAlignment(SwingConstants.CENTER);
		lblPgto.setFont(new Font("Arial", Font.BOLD, 18));
		lblPgto.setBackground(Color.BLACK);
		add(lblPgto, "cell 4 8 5 1,grow");
		
		JLabel lblformaDepagamento = new JLabel("<html><body>FORMA DE<br>PAGAMENTO:</body></html>");
		lblformaDepagamento.setFont(new Font("Arial", Font.BOLD, 14));
		lblformaDepagamento.setBackground(Color.WHITE);
		add(lblformaDepagamento, "cell 2 9 2 1,alignx left,growy");
	}
	
	/**
	 * Adiciona os campos de validação na tela;
	 */
	private void setInputFields() {
		txtNome = new JTextField();
		txtNome.setFont(new Font("Arial", Font.BOLD, 14));
		txtNome.setBorder(new LineBorder(Color.BLACK, 1, true));
		add(txtNome, "cell 4 4 5 1,grow");
		txtNome.setColumns(10);
		
		txtCPF = new JTextField();
		txtCPF.setFont(new Font("Arial", Font.BOLD, 14));
		txtCPF.setBorder(new LineBorder(Color.BLACK, 1, true));
		txtCPF.setColumns(10);
		add(txtCPF, "cell 4 5 5 1,grow");
		
		txtPlaca = new JTextField();
		txtPlaca.setFont(new Font("Arial", Font.BOLD, 14));
		txtPlaca.setBorder(new LineBorder(Color.BLACK, 1, true));
		txtPlaca.setColumns(10);
		add(txtPlaca, "cell 4 6 5 1,grow");
		
		txtRenavam = new JTextField();
		txtRenavam.setFont(new Font("Arial", Font.BOLD, 14));
		txtRenavam.setBorder(new LineBorder(Color.BLACK, 1, true));
		txtRenavam.setColumns(10);
		add(txtRenavam, "cell 4 7 5 1,grow");
	}
	
	/**
	 * Adiciona os Botões para validação dos campos de entrada
	 */
	private void setValidationButtons() {
		tiposPagamento = new ArrayList<String>();
		tiposPagamento.add(SELECIONE);
		tiposPagamento.add(DINHEIRO);
		tiposPagamento.add(CARTAO);
		
		JComboBox<Object> comboBox = new JComboBox<Object>(tiposPagamento.toArray());
		comboBox.setFont(new Font("Arial", Font.BOLD, 14));
		comboBox.setBorder(new LineBorder(Color.BLACK, 1, true));
		comboBox.setBackground(Color.WHITE);
		add(comboBox, "cell 4 9 5 1,grow");
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setFont(new Font("Arial", Font.BOLD, 20));
		btnSalvar.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		add(btnSalvar, "cell 4 11 5 1,grow");
		btnSalvar.addActionListener(e -> {
			
			
		});
	}
}
