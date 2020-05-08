package view.panels.cadastro;

import model.vo.veiculo.CarroVO;
import net.miginfocom.swing.MigLayout;
import util.Modificacoes;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.ArrayList;

public class DadosCadastroView extends JPanel {

    private static final long serialVersionUID = 8795512428702538815L;
    private final Modificacoes modificacoes = new Modificacoes();
    private JFormattedTextField ftfNome, ftfCPF, ftfRG, txtTelefone;
    private JTextField txtEmail;
    private JTable table;
    private MaskFormatter mfNome, mfCPF, mfRG, mfFone;
    private ArrayList<CarroVO> linhas;

    public DadosCadastroView() {

        this.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        this.setBackground(Color.WHITE);
        this.setLayout(new MigLayout("", "[grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow]",
                "[grow][grow][grow][grow][grow][grow][grow][grow][grow][30px][grow][10px][grow][grow][grow][grow][grow][grow]"));

        this.initialize();
    }

    public void initialize() {

        maskAndPlaceHolder();

        setJLabels_JSeparator();

        setInputFields();

        setButtons();

        setJTable();

        setCheckBox();

    }

    public void setJLabels_JSeparator() {
        JLabel lblNome = new JLabel("Nome *");
        lblNome.setHorizontalAlignment(SwingConstants.CENTER);
        lblNome.setFont(new Font("Arial", Font.BOLD, 14));
        this.add(lblNome, "cell 0 1 2 1,grow");

        JLabel lblCpf = new JLabel("CPF *");
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
        this.add(lblEmail, "cell 0 5 2 1,grow");

        JLabel lblTelefone = new JLabel("Fone *");
        lblTelefone.setHorizontalAlignment(SwingConstants.CENTER);
        lblTelefone.setFont(new Font("Arial", Font.BOLD, 14));
        this.add(lblTelefone, "cell 0 6 2 1,grow");

        JLabel lblAdicionarVeculos = new JLabel("Adicionar Veículos:");
        lblAdicionarVeculos.setHorizontalAlignment(SwingConstants.CENTER);
        lblAdicionarVeculos.setFont(new Font("Arial", Font.BOLD, 26));
        add(lblAdicionarVeculos, "cell 2 10 3 1,grow");
    }

    public void setInputFields() {
        ftfNome = new JFormattedTextField();
        ftfNome.setBorder(new LineBorder(Color.BLACK, 1, true));
        ftfNome.setFont(new Font("Arial", Font.BOLD, 14));
        this.add(ftfNome, "cell 2 1 3 1,grow");
        ftfNome.setColumns(10);

        ftfCPF = new JFormattedTextField();
        ftfCPF.setBorder(new LineBorder(Color.BLACK, 1, true));
        ftfCPF.setFont(new Font("Arial", Font.BOLD, 14));
        ftfCPF.setHorizontalAlignment(SwingConstants.LEFT);
        this.add(ftfCPF, "cell 2 2 3 1,grow");
        ftfCPF.setColumns(10);

        ftfRG = new JFormattedTextField();
        ftfRG.setFont(new Font("Arial", Font.BOLD, 14));
        ftfRG.setBorder(new LineBorder(Color.BLACK, 1, true));
        this.add(ftfRG, "cell 2 3 3 1,grow");
        ftfRG.setColumns(10);

        txtEmail = new JTextField();
        txtEmail.setBorder(new LineBorder(Color.BLACK, 1, true));
        txtEmail.setFont(new Font("Arial", Font.BOLD, 14));
        this.add(txtEmail, "cell 2 5 3 1,grow");
        txtEmail.setColumns(10);

        txtTelefone = new JFormattedTextField();
        txtTelefone.setFont(new Font("Arial", Font.BOLD, 14));
        txtTelefone.setBorder(new LineBorder(Color.BLACK, 1, true));
        this.add(txtTelefone, "cell 2 6 3 1,grow");
        txtTelefone.setColumns(10);

    }

    public void setButtons() {

        JButton btnAddRow = new JButton("Add Row Test");
        add(btnAddRow, "cell 0 10 2 1,grow");
        btnAddRow.addActionListener(e -> {

            linhas = new ArrayList<CarroVO>();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] data = linhas.toArray();
            model.addRow(data);

        });

    }

    public void setJTable() {

        JScrollPane scrollPane = new JScrollPane();
        add(scrollPane, "cell 0 12 12 6,grow");

        scrollPane.setBackground(Color.WHITE);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(new LineBorder(Color.BLACK, 1, true));

        String[] colunmName = {"Placa", "Marca", "Modelo", "Descrição", "Codígo do Cartão"};
        Object[][] data = {{}};

        table = new JTable(new DefaultTableModel(data, colunmName));

        modificacoes.tableLookAndFiel(table);
        modificacoes.mostrarComboBoxJTabel_MarcaVO(table, table.getColumnModel().getColumn(1));
        modificacoes.mostrarComboBoxJTable_ModeloVO(table, table.getColumnModel().getColumn(2));
        scrollPane.setViewportView(table);
        modificacoes.maskFormJTable(table, table.getColumnModel().getColumn(0));


    }

    private void addLinhaPorClick() {
        Object[][] data = {{}};

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == MouseEvent.BUTTON2) {

                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    model.addRow(data);

//					linhas = new ArrayList<>();
//					DefaultTableModel model = (DefaultTableModel) table.getModel();
//					Object[] data = linhas.toArray();
//					model.addRow(data);

                }
            }
        });
    }

    private void setCheckBox() {

        JCheckBox chckbxBloquear = new JCheckBox("Bloquear?");
        chckbxBloquear.setBackground(Color.WHITE);
        chckbxBloquear.setHorizontalAlignment(SwingConstants.CENTER);
        chckbxBloquear.setFont(new Font("Arial", Font.BOLD, 14));
        this.add(chckbxBloquear, "cell 0 8 2 1,grow");

    }

    private void maskAndPlaceHolder() {

        try {
            mfNome = new MaskFormatter("LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL"
                    + "LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");
            mfNome.setPlaceholder("Digite o CPF");

            mfCPF = new MaskFormatter("##############");
            mfCPF.setPlaceholder("Digite o CPF");

            mfRG = new MaskFormatter("##############");
            mfRG.setPlaceholder("Digite o RG");

            mfFone = new MaskFormatter("(##) #-####-####");
            mfFone.setPlaceholder("Digite o Telefone");

        } catch (ParseException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

}
