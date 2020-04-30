package view.panels;

import net.miginfocom.swing.MigLayout;
import util.Modificacoes;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CaixaView extends JPanel implements BaseView{

	private static final long serialVersionUID = -4789193934965387787L;
	private final Modificacoes modificacao = new Modificacoes();
	
	private JTable table;
	private JScrollPane scrollPane;

	public CaixaView() {
		
		this.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		this.setBounds(100, 100, 910, 689);
		this.setLayout(new MigLayout("", "[10px][grow][grow][grow][grow][grow][grow][grow][grow][5px][grow][grow][5px][grow][grow][5px][grow][grow][10px]", "[10px][80px][20px][35px][20px][grow][grow][grow][grow][grow][grow][grow][10px][grow][10px][10px]"));
		
		this.initialize();
	}

	public void initialize() {

		this.setJLabels_JSeparator();

		this.setInputFields();

		this.setButtons();

		this.setJTable();

	}

	@Override
	public void setJLabels_JSeparator() {

		JLabel lblCaixa = new JLabel("Caixa");
		lblCaixa.setFont(new Font("Arial", Font.BOLD, 20));
		add(lblCaixa, "cell 1 1 2 1,grow");

		JLabel lblDados = new JLabel("Dados:");
		lblDados.setFont(new Font("Arial", Font.BOLD, 14));
		lblDados.setBackground(Color.WHITE);
		add(lblDados, "cell 1 3,grow");

		JLabel lblSaldoEmDinheiror = new JLabel("Saldo em Dinheiro(R$):");
		lblSaldoEmDinheiror.setFont(new Font("Arial", Font.BOLD, 14));
		lblSaldoEmDinheiror.setForeground(Color.BLACK);
		lblSaldoEmDinheiror.setBackground(Color.WHITE);
		add(lblSaldoEmDinheiror, "cell 10 3 2 1,grow");

		JLabel lblSaldoEmCarto = new JLabel("Saldo em Cartão:");
		lblSaldoEmCarto.setFont(new Font("Arial", Font.BOLD, 14));
		lblSaldoEmCarto.setForeground(Color.BLACK);
		lblSaldoEmCarto.setBackground(Color.WHITE);
		add(lblSaldoEmCarto, "cell 13 3 2 1,grow");

		JLabel lblTotalCaixa = new JLabel("Total(R$):");
		lblTotalCaixa.setForeground(Color.BLACK);
		lblTotalCaixa.setFont(new Font("Arial", Font.BOLD, 14));
		lblTotalCaixa.setBackground(Color.WHITE);
		add(lblTotalCaixa, "cell 16 3 2 1,grow");

	}

	@Override
	public void setInputFields() {
	}

	@Override
	public void setButtons() {

		String stringRelatorio = "<html><body>Relatorio do<br align=Center>Último Caixa</body></html>";
		JButton btnRelatorio = new JButton(stringRelatorio);
		btnRelatorio.setIcon(new ImageIcon(CaixaView.class.getResource("/img/icons8-enviar-para-a-impressora-50.png")));
//		btnRelatorioDoltimo.setBackground(new Color(100, 149, 237));
		btnRelatorio.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		btnRelatorio.setFont(new Font("Arial", Font.BOLD, 16));
		add(btnRelatorio, "cell 7 1 2 1,grow");
		btnRelatorio.addActionListener(e -> {


		});

		JButton btnAdicionarValor = new JButton("Adicionar Valor");
		btnAdicionarValor.setIcon(new ImageIcon(CaixaView.class.getResource("/img/icons8-mais-50.png")));
//		btnAdicionarValor.setBackground(new Color(100, 149, 237));
		btnAdicionarValor.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		btnAdicionarValor.setFont(new Font("Arial", Font.BOLD, 16));
		add(btnAdicionarValor, "cell 10 1 2 1,grow");
		btnAdicionarValor.addActionListener(e -> {


		});

		JButton btnRetirarValor = new JButton("Retirar Valor");
		btnRetirarValor.setIcon(new ImageIcon(CaixaView.class.getResource("/img/icons8-menos-50.png")));
//		btnRetirarValor.setBackground(new Color(100, 149, 237));
		btnRetirarValor.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		btnRetirarValor.setFont(new Font("Arial", Font.BOLD, 16));
		add(btnRetirarValor, "cell 13 1 2 1,grow");
		btnRetirarValor.addActionListener(e -> {


		});

		JButton btnFecharCaixa = new JButton("Fechar Caixa");
		btnFecharCaixa.setIcon(new ImageIcon(CaixaView.class.getResource("/img/icons8-cadeado-2-50.png")));
//		btnFecharCaixa.setBackground(new Color(100, 149, 237));
		btnFecharCaixa.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		btnFecharCaixa.setFont(new Font("Arial", Font.BOLD, 16));
		add(btnFecharCaixa, "cell 16 1 2 1,grow");
		btnFecharCaixa.addActionListener(e -> {


		});

		String text = "<html><body align=Center>Imprimir Comprovante<br>(Linha Selecionada)</body></html>";
		JButton btnImprimirComprovante = new JButton(text);
		btnImprimirComprovante.setIcon(new ImageIcon(CaixaView.class.getResource("/img/icons8-impressora-de-porta-aberta-50.png")));
		btnImprimirComprovante.setBackground(Color.WHITE);
		btnImprimirComprovante.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		btnImprimirComprovante.setFont(new Font("Arial", Font.BOLD, 20));
		add(btnImprimirComprovante, "cell 7 13 7 1,grow");

	}

	@Override
	public void setJTable() {

		scrollPane = new JScrollPane();
		scrollPane.setBackground(Color.WHITE);
		scrollPane.getViewport().setBackground(Color.WHITE);
		scrollPane.setBorder(null);
		add(scrollPane, "cell 1 5 17 7,grow");

		String[] colunmName = {"Ticket / Cartão", "Descrição","Hora de Entrada", "Hora de Validação", "Forma de Pagamento", "Valor"};
		Object[][] data = {};

		DefaultTableModel model = new DefaultTableModel(data, colunmName);
		table = new JTable(model);
		modificacao.tableLookAndFiel(table);
		scrollPane.setViewportView(table);

	}
}