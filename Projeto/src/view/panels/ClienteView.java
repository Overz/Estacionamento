package view.panels;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;
import util.Modificacoes;

public class ClienteView extends JPanel {

	private static final long serialVersionUID = 3752138783055180091L;
	private Modificacoes modificacao = new Modificacoes();
	private JScrollPane scrollPane;
	private JTable table;

	public ClienteView() {
		
		this.setBounds(100, 100, 1028, 747);
		this.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		this.setLayout(new MigLayout("", "[10px][grow][grow][grow][grow][grow][grow][grow][10px]", "[10px][80px][20px][grow][][20px][20px][grow][grow][grow][grow][grow][grow][10px]"));
		
		
		this.initialize();
	}

	private void initialize() {
		
		JLabel lblSelecionar = new JLabel("<html><body>Selecione o Cliente<br>para Cadastrar ou Atualizar</body></html>");
		lblSelecionar.setBackground(Color.WHITE);
		lblSelecionar.setHorizontalAlignment(SwingConstants.LEFT);
		lblSelecionar.setFont(new Font("Arial", Font.BOLD, 16));
		add(lblSelecionar, "flowx,cell 1 1,alignx left,growy");
		
		JButton btnCadastrar = new JButton("<html><body>Cadastrar<br>Atualizar</body></html>");
		btnCadastrar.setIcon(new ImageIcon(ClienteView.class.getResource("/img/atutalizacao-50.png")));
		btnCadastrar.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		btnCadastrar.setFont(new Font("Arial", Font.BOLD, 20));
		add(btnCadastrar, "cell 2 1,grow");
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.setIcon(new ImageIcon(ClienteView.class.getResource("/img/icons8-apagar-para-sempre-38.png")));
		btnExcluir.setFont(new Font("Arial", Font.BOLD, 14));
		btnExcluir.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		btnExcluir.setBackground(Color.WHITE);
		btnExcluir.setEnabled(false);
		add(btnExcluir, "cell 2 3,grow");
		btnExcluir.addActionListener(e -> {
			
//			String mensagem = "Excluido's com Sucesso";
//			JLabel lblExcluir = new JLabel();
//			lblExcluir.setText(mensagem);
//			JOptionPane.showMessageDialog(null, lblExcluir);
//			JOptionPane.showConfirmDialog(this, lblExcluir, "CONFIRMAR EXCLUSÃO", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_CANCEL_OPTION, null);
			
		});
		
		JCheckBox chckbxDesejaEscluirOs = new JCheckBox("<html><body>Deseja Excluir os<br>Clientes Selecionados?</body></html>");
		chckbxDesejaEscluirOs.setFont(new Font("Arial", Font.BOLD, 12));
		chckbxDesejaEscluirOs.setBackground(Color.WHITE);
		add(chckbxDesejaEscluirOs, "cell 1 3,grow");
		chckbxDesejaEscluirOs.addActionListener(e -> {
			
			if (chckbxDesejaEscluirOs.isSelected()) {
				btnExcluir.setEnabled(true);
				btnExcluir.setBackground(Color.decode("#FF7F50"));
			} else {
				btnExcluir.setEnabled(false);
				btnExcluir.setBackground(Color.WHITE);
			}
			
		});
		
		JLabel lblTotalDeClientes = new JLabel("Total de Clientes Cadastrados:");
		lblTotalDeClientes.setBackground(Color.WHITE);
		lblTotalDeClientes.setFont(new Font("Arial", Font.BOLD, 14));
		add(lblTotalDeClientes, "cell 1 5,grow");
		
		
		JTextField txtProcurar = new JTextField();
		txtProcurar.setFont(new Font("Arial", Font.BOLD, 16));
		txtProcurar.setBorder(new LineBorder(Color.BLACK, 1, true));
		txtProcurar.setBackground(Color.WHITE);
		txtProcurar.setText("Pesquisar");
		add(txtProcurar, "cell 5 1 2 1,grow");
		txtProcurar.setColumns(10);
		
		JButton btnProcurar = new JButton("Pesquisar");
		btnProcurar.setFont(new Font("Arial", Font.BOLD, 16));
		btnProcurar.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		add(btnProcurar, "cell 7 1,grow");
		
		scrollPane = new JScrollPane();
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setBorder(null);
		scrollPane.getViewport().setBackground(Color.WHITE);
		add(scrollPane, "cell 1 7 7 6,grow");
		
		String[] colunmName = { "#", "Nome", "Plano", "Vencimento" };
		Object[][] data = {};
		
		DefaultTableModel model = new DefaultTableModel(data, colunmName);
		table = new JTable(model);
		modificacao.tabelaConfig(table);
		scrollPane.setViewportView(table);
	
	}
}
