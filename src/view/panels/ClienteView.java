package view.panels;

import model.banco.BaseDAO;
import model.dao.movientos.MovimentoDAO;
import model.vo.movimentos.MovimentoVO;
import net.miginfocom.swing.MigLayout;
import util.Constantes;
import util.Modificacoes;
import view.mainFrame.MainView;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ClienteView extends JPanel {

    private static final long serialVersionUID = 3752138783055180091L;
    private final Modificacoes modificacao = new Modificacoes();
    private BaseDAO<MovimentoVO> daoM;
    private ArrayList<MovimentoVO> lista;

    private JTextField txtProcurar;
    private JLabel lblSelecioneUmaLinha;
    private JButton btnExcluir, btnAtualizar;
    private JTable table;
    private DefaultTableModel model;

    public ClienteView() {

        this.setBounds(100, 100, 1028, 747);
        this.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        this.setLayout(new MigLayout("", "[10px][grow][grow][grow][grow][grow][grow][grow][10px]", "[10px][80px][20px][grow][20px][20px][grow][grow][grow][grow][grow][grow][10px]"));


        this.initialize();
    }

    public void initialize() {

        this.setJLabels_JSeparator();

        this.setInputFields();

        this.setCheckBox();

        this.setButtons();

        this.setJTable();

        this.atualizarTabela();

    }

    private void setCheckBox() {

        JCheckBox chckbxDesejaEscluirOs = new JCheckBox("<html><body>Deseja Excluir o<br>Cliente Selecionado?</body></html>");
        chckbxDesejaEscluirOs.setFont(new Font("Arial", Font.BOLD, 12));
        chckbxDesejaEscluirOs.setBackground(Color.WHITE);
        add(chckbxDesejaEscluirOs, "cell 1 3,grow");
        chckbxDesejaEscluirOs.addActionListener(e -> {

            if (chckbxDesejaEscluirOs.isSelected()) {
                btnExcluir.setEnabled(true);
                btnExcluir.setBackground(Color.decode("#FF7F50"));
                lblSelecioneUmaLinha.setEnabled(true);
            } else {
                btnExcluir.setEnabled(false);
                btnExcluir.setBackground(Color.WHITE);
                lblSelecioneUmaLinha.setEnabled(false);
            }

        });
    }

    public void setJLabels_JSeparator() {

        String text = "<html><body>Cadastrar um Novo Cliente<p text-align:center> Ou </p><p>Selecione a Linha para Atualizar</p></body></html>";
        JLabel lblSelecionar = new JLabel(text);
        lblSelecionar.setBackground(Color.WHITE);
        lblSelecionar.setHorizontalAlignment(SwingConstants.LEFT);
        lblSelecionar.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblSelecionar, "flowx,cell 1 1,alignx left,growy");

        JLabel lblTotalDeClientes = new JLabel("Total de Clientes Cadastrados:");
        lblTotalDeClientes.setBackground(Color.WHITE);
        lblTotalDeClientes.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblTotalDeClientes, "cell 1 4,grow");

        lblSelecioneUmaLinha = new JLabel("Selecione Uma Linha por Vez");
        lblSelecioneUmaLinha.setHorizontalAlignment(SwingConstants.LEFT);
        lblSelecioneUmaLinha.setFont(new Font("Arial", Font.BOLD, 12));
        lblSelecioneUmaLinha.setEnabled(false);
        add(lblSelecioneUmaLinha, "cell 2 4,growx,aligny top");

    }

    public void setInputFields() {

        txtProcurar = new JTextField();
        txtProcurar.setFont(new Font("Arial", Font.BOLD, 16));
        txtProcurar.setBorder(new LineBorder(Color.BLACK, 1, true));
        txtProcurar.setBackground(Color.WHITE);
        txtProcurar.setText("Pesquisar");
        add(txtProcurar, "cell 5 1 2 1,grow");
        txtProcurar.setColumns(10);

    }

    public void setButtons() {

        JButton btnCadastrar = new JButton("Cadatrar");
        btnCadastrar.setIcon(new ImageIcon(ClienteView.class.getResource("/img/atutalizacao-50.png")));
        btnCadastrar.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        btnCadastrar.setFont(new Font("Arial", Font.BOLD, 20));
        add(btnCadastrar, "cell 2 1,grow");
        btnCadastrar.addActionListener(e -> {

            MainView topFrame = (MainView) SwingUtilities.getWindowAncestor(this);
            CadastroView cadastroView = new CadastroView();
            MainView.swithPanel(cadastroView);

        });

        btnAtualizar = new JButton("Atualizar");
        btnAtualizar.setIcon(new ImageIcon(ClienteView.class.getResource("/img/atutalizacao-50.png")));
        btnAtualizar.setFont(new Font("Arial", Font.BOLD, 20));
        btnAtualizar.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        btnAtualizar.setEnabled(false);
        add(btnAtualizar, "cell 3 1,grow");
        btnAtualizar.addActionListener(e -> {


        });

        btnExcluir = new JButton("Excluir");
        btnExcluir.setIcon(new ImageIcon(ClienteView.class.getResource("/img/icons8-apagar-para-sempre-38.png")));
        btnExcluir.setFont(new Font("Arial", Font.BOLD, 20));
        btnExcluir.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        btnExcluir.setBackground(Color.WHITE);
        btnExcluir.setEnabled(false);
        add(btnExcluir, "cell 2 3,grow");
        btnExcluir.addActionListener(e -> {

//			Padronizar os Labels ?

//			String mensagem = "Excluido's com Sucesso";
//			JLabel lblExcluir = new JLabel();
//			lblExcluir.setText(mensagem);
//			JOptionPane.showMessageDialog(null, lblExcluir);
//			JOptionPane.showConfirmDialog(this, lblExcluir, "CONFIRMAR EXCLUSÃO", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_CANCEL_OPTION, null);

        });

        JButton btnProcurar = new JButton("Pesquisar");
        btnProcurar.setFont(new Font("Arial", Font.BOLD, 16));
        btnProcurar.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        add(btnProcurar, "cell 7 1,grow");
        btnProcurar.addActionListener(e -> {


        });

    }

    public void setJTable() {

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBackground(Color.WHITE);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane, "cell 1 6 7 6,grow");

        table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == MouseEvent.BUTTON1) {
                    Object o = table.getModel().getValueAt(table.getSelectedRow(), table.getSelectedColumn());
                    int i = table.getSelectedRow();
                    if (o != null) {
                        btnAtualizar.setEnabled(true);
                    }
                }
                /**
                 * Remove o focus e as linhas selecionadas
                 * da tabela e trasnfere para o campo de procura
                 * apois 20 mili segundos
                 */
                if (table.hasFocus()) {
                    ActionListener event = e1 -> txtProcurar.requestFocus();
                    Timer timer = new Timer(20000, event);
                    timer.start();
//                    if (timer.getDelay() == 20000) {
//                        table.getSelectionModel().clearSelection();
//                    }
                }
            }
        });

        modificacao.tableLookAndFiel(table);
        scrollPane.setViewportView(table);
    }

    private void atualizarTabela() {

        limparTabela();

        daoM = new MovimentoDAO();
        lista = daoM.consultarTodos();

        model = (DefaultTableModel) table.getModel();

        Object[] novaLinha = new Object[4];
        for (MovimentoVO movimento : lista) {
            novaLinha[0] = movimento.getPlano().getCliente().getId();
            novaLinha[1] = movimento.getPlano().getCliente().getNome();
            novaLinha[2] = movimento.getPlano().getTipo();
            novaLinha[3] = calcularVencimento();

            model.addRow(novaLinha);

        }
    }

    private void limparTabela() {
        table.setModel(new DefaultTableModel(new Object[][]{}, Constantes.COLUNAS_CLIENTE));
    }

    private String calcularVencimento() {
        return "TESTE";
    }
}
