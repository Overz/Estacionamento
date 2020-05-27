package view.panels;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import controller.ControllerMovimento;
import net.miginfocom.swing.MigLayout;
import util.Constantes;
import util.Modificacoes;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MovimentoView extends JPanel {

    private static final long serialVersionUID = -194366357031753318L;
    private Modificacoes modificacao;
    private ControllerMovimento control;

    private DatePicker dtInicio, dtFinal;
    private JTable table;
    private JButton btnPesquisar;
    private JLabel lblModificacao;

    public MovimentoView() {

        this.setBounds(100, 100, 1065, 812);
        this.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        this.setLayout(new MigLayout("",
                "[10px][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][10px]",
                "[10px][grow][20px][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][10px]"));

        this.initialize();
    }

    public void initialize() {

        Constantes.FLAG = 2;
        Constantes.INTERNAL_MESSAGE = 4;

        modificacao = new Modificacoes();
        control = new ControllerMovimento(this);

        setJLabels_JSeparator();

        setInputFields();

        setButtons();

        setJTable();

        addListeners();

        control.limparTabela();

        control.consultarDiaAtual();

    }

    public void setJLabels_JSeparator() {

        JLabel lblMovimento = new JLabel("Movimento:");
        lblMovimento.setHorizontalAlignment(SwingConstants.CENTER);
        lblMovimento.setFont(new Font("Arial", Font.BOLD, 18));
        lblMovimento.setBackground(Color.WHITE);
        this.add(lblMovimento, "cell 1 1 3 1,grow");

    }

    public void setInputFields() {
        DatePickerSettings dateSettings = new DatePickerSettings();
        dateSettings.setAllowKeyboardEditing(false);

        dtInicio = new DatePicker(dateSettings);
//		TXT
        dtInicio.getComponentDateTextField().setBackground(Color.WHITE);
        dtInicio.getComponentDateTextField().setFont(new Font("Arial", Font.BOLD, 16));
        dtInicio.getComponentDateTextField().setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        dtInicio.getComponentDateTextField().setHorizontalAlignment(SwingConstants.CENTER);
//		BOTAO
        dtInicio.getComponentToggleCalendarButton().setText("Início");
        dtInicio.getComponentToggleCalendarButton().setPreferredSize(new Dimension(50, 20));
        dtInicio.getComponentToggleCalendarButton().setFont(new Font("Arial", Font.BOLD, 16));
        dtInicio.getComponentToggleCalendarButton().setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        this.add(dtInicio, "cell 10 1,grow");

        dtFinal = new DatePicker();
//		TXT
        dtFinal.getComponentDateTextField().setBackground(Color.WHITE);
        dtFinal.getComponentDateTextField().setFont(new Font("Arial", Font.BOLD, 16));
        dtFinal.getComponentDateTextField().setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        dtFinal.getComponentDateTextField().setHorizontalAlignment(SwingConstants.CENTER);
//		BOTAO
        dtFinal.getComponentToggleCalendarButton().setText("Fim");
        dtFinal.getComponentToggleCalendarButton().setPreferredSize(new Dimension(50, 20));
        dtFinal.getComponentToggleCalendarButton().setFont(new Font("Arial", Font.BOLD, 16));
        dtFinal.getComponentToggleCalendarButton().setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        this.add(dtFinal, "cell 11 1,grow");
    }

    public void setButtons() {
        btnPesquisar = new JButton("Pesquisar");
        btnPesquisar.setPreferredSize(new Dimension(80, 25));
        btnPesquisar.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        btnPesquisar.setFont(new Font("Arial", Font.BOLD, 16));
        this.add(btnPesquisar, "cell 12 1 2 1,grow");
    }

    private void setJTable() {
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBackground(Color.WHITE);
        scrollPane.getViewport().setBackground(Color.WHITE);
        this.add(scrollPane, "cell 1 3 13 11,grow");

        table = new JTable(new DefaultTableModel()) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modificacao.tableLookAndFiel(table);
        scrollPane.setViewportView(table);

    }

    private void addListeners() {
        btnPesquisar.addActionListener(e -> {
            String dt1 = dtInicio.getDateStringOrEmptyString();
            String dt2 = dtFinal.getDateStringOrEmptyString();

            control.consultar(dt1, dt2);
            control.atualizarTabela();
        });
    }

    public Modificacoes getModificacao() {
        return modificacao;
    }

    public DatePicker getDtInicio() {
        return dtInicio;
    }

    public DatePicker getDtFinal() {
        return dtFinal;
    }

    public JTable getTable() {
        return table;
    }

    public JLabel getLblModificacao() {
        return lblModificacao;
    }
}
