package view.panels;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import model.banco.BaseDAO;
import model.dao.movientos.MovimentoDAO;
import model.seletor.Seletor;
import model.vo.movimentos.MovimentoVO;
import net.miginfocom.swing.MigLayout;
import util.Constantes;
import util.Modificacoes;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Locale;

public class MovimentoView extends JPanel {

    private static final long serialVersionUID = -194366357031753318L;
    private final Modificacoes modificacao = new Modificacoes();

    private DatePicker dtInicio, dtFinal;
    private JTable table;

    private ArrayList<MovimentoVO> lista;
    private DefaultTableModel model;

    public MovimentoView() {

        this.setBounds(100, 100, 1065, 812);
        this.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        this.setLayout(new MigLayout("",
                "[10px][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][10px]",
                "[10px][grow][20px][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][10px]"));

        this.initialize();
    }

    public void initialize() {

        setJLabels_JSeparator();

        setInputFields();

        setButtons();

        setJTable();

        //TODO Trazer os Valores do Dia atual;

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
        JButton btnPesquisar = new JButton("Pesquisar");
        btnPesquisar.setPreferredSize(new Dimension(80, 25));
        btnPesquisar.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        btnPesquisar.setFont(new Font("Arial", Font.BOLD, 16));
        this.add(btnPesquisar, "cell 12 1 2 1,grow");
        btnPesquisar.addActionListener(e -> {

//            Instanciar as Classes usadas
            BaseDAO<MovimentoVO> bDAO = new MovimentoDAO();
            Seletor seletorMovimento = new Seletor();

//            Setar os valores da Tela no Seletor para criar Filtro
            DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder()
                    .parseCaseInsensitive().parseLenient()
                    .appendPattern("yyyy-MMM-dd")
                    .appendPattern("yyyy/MMM/dd")
                    .appendPattern("d-MM-yyyy")
                    .appendPattern("d-M-yy");
//            DateTimeFormatter dtf = builder.toFormatter(Locale.ENGLISH);
//            seletorMovimento.setDtInicio(String.format(dtInicio.getText(), dtf));
//            seletorMovimento.setDtFim(String.format(dtFinal.getText(), dtf));

            atualizarTabela(this.lista);

        });
    }

    public void setJTable() {
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBackground(Color.WHITE);
        scrollPane.getViewport().setBackground(Color.WHITE);
        this.add(scrollPane, "cell 1 3 13 11,grow");

        table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modificacao.tableLookAndFiel(table);
        scrollPane.setViewportView(table);

    }

    private void atualizarTabela(ArrayList<MovimentoVO> lista) {

//		 Limpa a tabela
        limparTabela();

//		 Obtém o model da tabela
        model = (DefaultTableModel) table.getModel();

//		 Percorre os empregados para adicionar linha a linha na tabela (JTable)
        Object[] novaLinha = new Object[7];
//		"Número", "Nome", "Plano", "Placa", "Valor", "Entrada", "Saída"
        for (MovimentoVO movimento : lista) {
            novaLinha[0] = movimento.getTicket().getNumero();
            novaLinha[1] = movimento.getTicket().getCliente().getNome();
            novaLinha[2] = movimento.getPlano().getTipo();
            novaLinha[3] = movimento.getTicket().getCliente().getCarro().getPlaca();
            novaLinha[4] = movimento.getTicket().getValor();
            novaLinha[5] = movimento.getHr_entrada().format(Constantes.dtf);
            novaLinha[6] = movimento.getHr_saida().format(Constantes.dtf);

//			 Adiciona a nova linha na tabela
            model.addRow(novaLinha);
        }
    }

    private void limparTabela() {
        table.setModel(new DefaultTableModel(new Object[][]{}, Constantes.COLUNAS_MOVIMENTO));
    }

}
