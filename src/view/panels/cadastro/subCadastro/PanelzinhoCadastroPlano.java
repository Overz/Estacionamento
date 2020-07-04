package view.panels.cadastro.subCadastro;

import controller.ControllerCadastroPlano;
import net.miginfocom.swing.MigLayout;
import util.constantes.ConstHelpers;
import util.constantes.ConstInicio;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;

import static util.helpers.Modificacoes.addMask;

public class PanelzinhoCadastroPlano extends JPanel {

    private static final long serialVersionUID = -8178837282155450083L;
    private ControllerCadastroPlano control;
    private JComboBox cbPlano;
    private JComboBox cbFormaPgto;
    private JFormattedTextField txtCartao;
    private JLabel lblMesValidade, lblHora;
    private JCheckBox chckbxBloquear;

    public PanelzinhoCadastroPlano() {
        control = new ControllerCadastroPlano(this);
        this.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        this.setLayout(new MigLayout("", "[grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow]", "[grow][grow][grow][grow][grow][grow][grow][grow]"));

        this.initialize();
    }

    private void initialize() {

        setJLabels_JSeparator();

        setComboBox();

        setInputField();

        setCheckbox();

        control.addValidade();

    }

    private void setJLabels_JSeparator() {

        JLabel lblPlano = new JLabel("Plano");
        lblPlano.setHorizontalAlignment(SwingConstants.CENTER);
        lblPlano.setFont(new Font("Arial", Font.BOLD, 16));
        lblPlano.setBackground(Color.WHITE);
        add(lblPlano, "cell 0 1 2 1,grow");

        JLabel lblFormaPgto = new JLabel("<html><body>Forma de<br align=Center>Pagamento</body></html>");
        lblFormaPgto.setHorizontalAlignment(SwingConstants.CENTER);
        lblFormaPgto.setFont(new Font("Arial", Font.BOLD, 16));
        lblFormaPgto.setBackground(Color.WHITE);
        add(lblFormaPgto, "cell 0 2 2 1,grow");

        JLabel lblCartao = new JLabel("<html><body>Número do Cartão <a style=color:red>*</a></body></html>");
        lblCartao.setHorizontalAlignment(SwingConstants.CENTER);
        lblCartao.setFont(new Font("Dialog", Font.BOLD, 16));
        lblCartao.setBackground(Color.WHITE);
        add(lblCartao, "cell 0 3 2 1,grow");

        JLabel lblAtivo = new JLabel("Bloquear?");
        lblAtivo.setHorizontalAlignment(SwingConstants.CENTER);
        lblAtivo.setFont(new Font("Dialog", Font.BOLD, 16));
        lblAtivo.setBackground(Color.WHITE);
        add(lblAtivo, "cell 0 4 2 1,grow");

        JLabel lblHoraAtual = new JLabel("Hora Atual");
        lblHoraAtual.setHorizontalAlignment(SwingConstants.CENTER);
        lblHoraAtual.setFont(new Font("Dialog", Font.BOLD, 16));
        lblHoraAtual.setBackground(Color.WHITE);
        add(lblHoraAtual, "cell 0 5 2 1,grow");

        JLabel lblValidade = new JLabel("Validade");
        lblValidade.setHorizontalAlignment(SwingConstants.CENTER);
        lblValidade.setFont(new Font("Dialog", Font.BOLD, 16));
        lblValidade.setBackground(Color.WHITE);
        add(lblValidade, "cell 0 6 2 1,grow");

        lblHora = new JLabel(control.calcularValidade().format(ConstHelpers.DTF));
        lblHora.setHorizontalAlignment(SwingConstants.LEFT);
        lblHora.setFont(new Font("Dialog", Font.BOLD, 16));
        add(lblHora, "cell 2 5 5 1,grow");

        lblMesValidade = new JLabel();
        lblMesValidade.setHorizontalAlignment(SwingConstants.LEFT);
        lblMesValidade.setFont(new Font("Dialog", Font.BOLD, 16));
        add(lblMesValidade, "cell 2 6 5 1,grow");

    }

    private void setComboBox() {
        cbPlano = new JComboBox(control.preencherCbx());
        cbPlano.setFont(new Font("Arial", Font.BOLD, 16));
        cbPlano.setBorder(new LineBorder(Color.BLACK, 1, true));
        cbPlano.setBackground(Color.WHITE);
        add(cbPlano, "cell 2 1 5 1,grow");

        cbFormaPgto = new JComboBox<>(ConstInicio.LISTA_FORMA_PGTO.toArray());
        cbFormaPgto.setFont(new Font("Arial", Font.BOLD, 16));
        cbFormaPgto.setBorder(new LineBorder(Color.BLACK, 1, true));
        cbFormaPgto.setBackground(Color.WHITE);
        add(cbFormaPgto, "cell 2 2 5 1,grow");

    }

    private void setCheckbox() {
        chckbxBloquear = new JCheckBox("Bloquear?");
        chckbxBloquear.setFont(new Font("Arial", Font.BOLD, 16));
        chckbxBloquear.setBackground(Color.WHITE);
        add(chckbxBloquear, "cell 2 4 5 1,grow");
    }

    private void setInputField() {
        txtCartao = new JFormattedTextField(addMask(new MaskFormatter(), ConstHelpers.MASK_TICKET_CARD_15, ""));
        txtCartao.setFont(new Font("Dialog", Font.BOLD, 14));
        txtCartao.setColumns(10);
        txtCartao.setBorder(new LineBorder(Color.BLACK, 1, true));
        add(txtCartao, "cell 2 3 5 1,grow");
    }

    public JComboBox<Object> getCbPlano() {
        return cbPlano;
    }

    public JComboBox<String> getCbFormaPgto() {
        return cbFormaPgto;
    }

    public JFormattedTextField getTxtCartao() {
        return txtCartao;
    }

    public JLabel getLblMesValidade() {
        return lblMesValidade;
    }

    public JLabel getLblHora() {
        return lblHora;
    }

    public JCheckBox getChckbxBloquear() {
        return chckbxBloquear;
    }
}
