package view.panels;

import model.banco.BaseDAO;
import model.dao.movientos.MovimentoDAO;
import model.vo.movimentos.MovimentoVO;
import net.miginfocom.swing.MigLayout;
import util.Constantes;
import util.Modificacoes;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class CaixaView extends JPanel {

    private static final long serialVersionUID = -4789193934965387787L;
    private final Modificacoes modificacao = new Modificacoes();

    private BaseDAO<MovimentoVO> daoM;

    private DefaultTableModel model;
    private JTable table;

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

        this.atualizarTabela();

    }

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

    public void setInputFields() {
    }

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

    public void setJTable() {

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBackground(Color.WHITE);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(null);
        add(scrollPane, "cell 1 5 17 7,grow");

        table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modificacao.tableLookAndFiel(table);
        scrollPane.setViewportView(table);

    }

    private void atualizarTabela() {
        daoM = new MovimentoDAO();
        ArrayList<MovimentoVO> lista = daoM.consultarTodos();

        limparTabela();

        model = (DefaultTableModel) table.getModel();

        Object[] novaLinha = new Object[6];
        for (MovimentoVO movimento : lista) {
            novaLinha[0] = movimento.getTicket().getNumero();
            novaLinha[1] = movimento.getTicket().getCliente().getNome();
            novaLinha[2] = movimento.getHr_entrada().format(Constantes.dtf);
            novaLinha[3] = movimento.getHr_saida().format(Constantes.dtf);
            novaLinha[4] = movimento.getTicket().getTipo();
            novaLinha[5] = movimento.getTicket().getValor();

            model.addRow(novaLinha);
        }
    }

    private void limparTabela() {
        table.setModel(new DefaultTableModel(new Object[][]{}, Constantes.COLUNAS_CAIXA));
    }
}