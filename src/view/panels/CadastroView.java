package view.panels;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import net.miginfocom.swing.MigLayout;
import view.panels.cadastro.DadosCadastroView;
import view.panels.cadastro.EnderecoCadastroView;
import view.panels.cadastro.PlanoCadastroView;

public class CadastroView extends JPanel {

	private static final long serialVersionUID = -7538521065547926504L;
	private JLayeredPane layeredPane;
	private DadosCadastroView dadosCadastroView;

	/**
	 * Inicia a Tela
	 */
	public CadastroView() {
		this.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		this.setBounds(100, 100, 1145, 908);
		this.setLayout(new MigLayout("",
				"[10px][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][10px]",
				"[10px][50px][10px][50px][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][20px][35px][35px][10px]"));

		this.initialize();
	}

	/**
	 * Adiciona Todos os Componentes para a Tela
	 */
	private void initialize() {
		
		setJLabels_JSeparator();
		setValidationButtons();
		
		layeredPane = new JLayeredPane();
		layeredPane.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		layeredPane.setLayout(new MigLayout("", "[grow]", "[grow]"));
		add(layeredPane, "cell 1 4 14 11,grow");

		dadosCadastroView = new DadosCadastroView();
		dadosCadastroView.setBorder(null);
		layeredPane.add(dadosCadastroView, "cell 0 0,grow");

	}

	/**
	 * Adiciona os JLabels a tela & JSeparators
	 */
	private void setJLabels_JSeparator() {
		JLabel lblAdicionarCliente = new JLabel("Adicionar Cliente:");
		lblAdicionarCliente.setHorizontalAlignment(SwingConstants.CENTER);
		lblAdicionarCliente.setFont(new Font("Arial", Font.BOLD, 22));
		lblAdicionarCliente.setBackground(Color.WHITE);
		add(lblAdicionarCliente, "cell 1 1 6 1,grow");
	}
	
	/**
	 * Adiciona os Botões para validação dos campos de entrada
	 */
	private void setValidationButtons() {
		
		JButton btnDados = new JButton("Dados");
		btnDados.setFont(new Font("Arial", Font.BOLD, 20));
		btnDados.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		add(btnDados, "cell 1 3 2 1,grow");
		btnDados.addActionListener(e -> {

			dadosCadastroView = new DadosCadastroView();
			swithPanel(dadosCadastroView);
			if (dadosCadastroView.isShowing()) {
				if (btnDados.isSelected()) {
					btnDados.setBackground(Color.WHITE);
				} else {
					btnDados.setBackground(new JButton().getBackground());
				}
			}

			// TODO SALVAR OS DADOS ANTES DE MUDAR DE TELA

		});

		JButton btnEndereco = new JButton("Endereço");
		btnEndereco.setFont(new Font("Arial", Font.BOLD, 20));
		btnEndereco.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		add(btnEndereco, "cell 3 3 2 1,grow");
		btnEndereco.addActionListener(e -> {

			EnderecoCadastroView enderecoCadastroView = new EnderecoCadastroView();
			swithPanel(enderecoCadastroView);
			if (enderecoCadastroView.isShowing()) {
				if (btnDados.isSelected()) {
					btnDados.setBackground(Color.WHITE);
				} else {
					btnDados.setBackground(new JButton().getBackground());
				}
			}

			// TODO SALVAR OS DADOS ANTES DE MUDAR DE TELA

		});

		JButton btnPlano = new JButton("Plano");
		btnPlano.setFont(new Font("Arial", Font.BOLD, 20));
		btnPlano.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		add(btnPlano, "cell 5 3 2 1,grow");
		btnPlano.addActionListener(e -> {

			PlanoCadastroView planoCadastroView = new PlanoCadastroView();
			swithPanel(planoCadastroView);
			if (planoCadastroView.isShowing()) {
				if (btnDados.isSelected()) {
					btnDados.setBackground(Color.WHITE);
				} else {
					btnDados.setBackground(new JButton().getBackground());
				}
			}

			// TODO SALVAR OS DADOS ANTES DE MUDAR DE TELA

		});
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setFont(new Font("Arial", Font.BOLD, 20));
		btnSalvar.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		add(btnSalvar, "cell 5 16 3 2,grow");
		btnSalvar.addActionListener(e -> {

		});
	}
	
	/**
	 * Realiza a troca de Telas(JPanels), impedindo que fiquem abertas
	 * 
	 * @param panel
	 */
	public void swithPanel(JPanel panel) {

		layeredPane.removeAll();
		panel.setBorder(null);
		panel.setBackground(Color.WHITE);
		panel.repaint();
		panel.revalidate();
		layeredPane.add(panel, "grow");
		layeredPane.repaint();
		layeredPane.revalidate();

	}

}
