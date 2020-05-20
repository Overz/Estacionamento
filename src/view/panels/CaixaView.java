package view.panels;

import controller.ControllerCaixa;
import net.miginfocom.swing.MigLayout;
import util.Constantes;
import util.Modificacoes;
import view.mainFrame.MainView;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CaixaView extends JPanel {

    private static final long serialVersionUID = -4789193934965387787L;
    private ControllerCaixa control;
    private final Modificacoes modificacao = new Modificacoes();

    private JTable table;
    private JLabel lblSaldoEmDinheiror, lblSaldoEmCarto, lblTotalCaixa, lblModificacao;

    public CaixaView() {

        this.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        this.setBounds(100, 100, 910, 689);
        this.setLayout(new MigLayout("", "[10px][grow][grow][grow][grow][grow][grow][grow][grow][5px][grow][grow][5px][grow][grow][5px][grow][grow][10px]", "[10px][80px][20px][35px][20px][grow][grow][grow][grow][grow][grow][grow][10px][grow][10px][10px]"));

        this.initialize();
    }

    private void initialize() {

        MainView.jopLocale();

        control = new ControllerCaixa(this);

        this.setJLabels_JSeparator();

        this.setButtons();

        this.setJTable();

        if (table.getColumnCount() == 0 || table.getRowCount() == 0) {
            control.atualizarTabela();
        }

    }

    private void setJLabels_JSeparator() {

        JLabel lblCaixa = new JLabel("Caixa");
        lblCaixa.setFont(new Font("Arial", Font.BOLD, 20));
        add(lblCaixa, "cell 1 1 2 1,grow");

        JLabel lblDados = new JLabel("Dados:");
        lblDados.setFont(new Font("Arial", Font.BOLD, 14));
        lblDados.setBackground(Color.WHITE);
        add(lblDados, "cell 1 3,grow");

        lblSaldoEmDinheiror = new JLabel(Constantes.LBL_TEXT_CAIXA_DINHEIRO);
        lblSaldoEmDinheiror.setFont(new Font("Arial", Font.BOLD, 14));
        lblSaldoEmDinheiror.setForeground(Color.BLACK);
        lblSaldoEmDinheiror.setBackground(Color.WHITE);
        add(lblSaldoEmDinheiror, "cell 10 3 2 1,grow");

        lblSaldoEmCarto = new JLabel(Constantes.LBL_TEXT_CAIXA_CARTAO);
        lblSaldoEmCarto.setFont(new Font("Arial", Font.BOLD, 14));
        lblSaldoEmCarto.setForeground(Color.BLACK);
        lblSaldoEmCarto.setBackground(Color.WHITE);
        add(lblSaldoEmCarto, "cell 13 3 2 1,grow");

        lblTotalCaixa = new JLabel(Constantes.LBL_TEXT_CAIXA_TOTAL);
        lblTotalCaixa.setForeground(Color.BLACK);
        lblTotalCaixa.setFont(new Font("Arial", Font.BOLD, 14));
        lblTotalCaixa.setBackground(Color.WHITE);
        add(lblTotalCaixa, "cell 16 3 2 1,grow");

    }

    private void setButtons() {

//        String stringRelatorio = "<html><body>Relatorio do<br align=Center>Último Caixa</body></html>";
//        JButton btnRelatorio = new JButton(stringRelatorio);
//        btnRelatorio.setIcon(new ImageIcon(CaixaView.class.getResource("/img/icons8-enviar-para-a-impressora-50.png")));
////		btnRelatorioDoltimo.setBackground(new Color(100, 149, 237));
//        btnRelatorio.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
//        btnRelatorio.setFont(new Font("Arial", Font.BOLD, 16));
//        add(btnRelatorio, "cell 7 1 2 1,grow");
//        btnRelatorio.addActionListener(e -> control.imprimirRelatorio());

        JButton btnAdicionarValor = new JButton("Adicionar Valor");
        btnAdicionarValor.setIcon(new ImageIcon(CaixaView.class.getResource("/img/icons8-mais-50.png")));
        btnAdicionarValor.setBackground(Color.decode("#35D073"));
        btnAdicionarValor.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        btnAdicionarValor.setFont(new Font("Arial", Font.BOLD, 16));
        add(btnAdicionarValor, "cell 10 1 2 1,grow");
        btnAdicionarValor.addActionListener(e -> {
            Constantes.FLAG = 1;
            control.showInputDialog();
        });

        JButton btnRetirarValor = new JButton("Retirar Valor");
        btnRetirarValor.setIcon(new ImageIcon(CaixaView.class.getResource("/img/icons8-menos-50.png")));
        btnRetirarValor.setBackground(Color.decode("#F85C50"));
        btnRetirarValor.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        btnRetirarValor.setFont(new Font("Arial", Font.BOLD, 16));
        add(btnRetirarValor, "cell 13 1 2 1,grow");
        btnRetirarValor.addActionListener(e -> {
            Constantes.FLAG = 0;
            control.showInputDialog();
        });

        JButton btnFecharCaixa = new JButton("Fechar Caixa");
        btnFecharCaixa.setIcon(new ImageIcon(CaixaView.class.getResource("/img/icons8-cadeado-2-50.png")));
        btnFecharCaixa.setBackground(new Color(100, 149, 237));
        btnFecharCaixa.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        btnFecharCaixa.setFont(new Font("Arial", Font.BOLD, 16));
        add(btnFecharCaixa, "cell 16 1 2 1,grow");
        btnFecharCaixa.addActionListener(e -> control.fecharCaixa());

        String text = "<html><body align=Center>Imprimir Comprovante<br>(Linha Selecionada)</body></html>";
        JButton btnImprimirComprovante = new JButton(text);
        btnImprimirComprovante.setIcon(new ImageIcon(CaixaView.class.getResource("/img/icons8-impressora-de-porta-aberta-50.png")));
        btnImprimirComprovante.setBackground(Color.WHITE);
        btnImprimirComprovante.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        btnImprimirComprovante.setFont(new Font("Arial", Font.BOLD, 20));
        add(btnImprimirComprovante, "cell 7 13 7 1,grow");
        btnImprimirComprovante.addActionListener(e -> control.imprimirComprovante());

    }

    private void setJTable() {

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBackground(Color.WHITE);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(null);
        add(scrollPane, "cell 1 5 17 7,grow");

        table = new JTable(new DefaultTableModel()) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = modificacao.tableLookAndFiel(table);
        scrollPane.setViewportView(table);

    }

    public JTable getTable() {
        return table;
    }

    public JLabel getLblSaldoEmDinheiror() {
        return lblSaldoEmDinheiror;
    }

    public JLabel getLblSaldoEmCarto() {
        return lblSaldoEmCarto;
    }

    public JLabel getLblTotalCaixa() {
        return lblTotalCaixa;
    }

    public JLabel getLblModificacao() {
        return lblModificacao;
    }

    public Modificacoes getModificacao() {
        return modificacao;
    }

}