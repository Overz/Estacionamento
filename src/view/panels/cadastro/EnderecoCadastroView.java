package view.panels.cadastro;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class EnderecoCadastroView extends JPanel {

    private static final long serialVersionUID = 6735598086664804404L;
    private JTextField txtRua, txtBairro, txtComplemento, txtNumero, txtCidade;

    public EnderecoCadastroView() {

        this.setBackground(Color.WHITE);
        this.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        this.setLayout(new MigLayout("", "[grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow]", "[grow][grow][grow][grow][grow][grow][grow][grow][grow]"));

        this.initialize();

    }

    public void initialize() {

        setJLabels_JSeparator();

        setInputFields();

        setButtons();

        setJTable();

    }

    public void setJLabels_JSeparator() {

        JLabel lblRua = new JLabel("Rua");
        lblRua.setHorizontalAlignment(SwingConstants.CENTER);
        lblRua.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblRua, "cell 0 2 2 1,grow");

        JLabel lblN = new JLabel("Nº");
        lblN.setHorizontalAlignment(SwingConstants.CENTER);
        lblN.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblN, "cell 0 3 2 1,grow");

        JLabel lblBairro = new JLabel("Bairro");
        lblBairro.setHorizontalAlignment(SwingConstants.CENTER);
        lblBairro.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblBairro, "cell 0 4 2 1,grow");

        JLabel lblCidade = new JLabel("Cidade");
        lblCidade.setHorizontalAlignment(SwingConstants.CENTER);
        lblCidade.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblCidade, "cell 0 5 2 1,grow");

        JLabel lbl = new JLabel("Complemento");
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 14));
        add(lbl, "cell 0 6 2 1,grow");

    }

    public void setInputFields() {

        txtRua = new JTextField();
        txtRua.setFont(new Font("Arial", Font.BOLD, 14));
        txtRua.setBorder(new LineBorder(Color.BLACK, 1, true));
        add(txtRua, "cell 2 2 3 1,grow");
        txtRua.setColumns(10);

        txtNumero = new JTextField();
        txtNumero.setFont(new Font("Arial", Font.BOLD, 14));
        txtNumero.setColumns(10);
        txtNumero.setBorder(new LineBorder(Color.BLACK, 1, true));
        add(txtNumero, "cell 2 3 3 1,grow");

        txtBairro = new JTextField();
        txtBairro.setFont(new Font("Arial", Font.BOLD, 14));
        txtBairro.setBorder(new LineBorder(Color.BLACK, 1, true));
        txtBairro.setColumns(10);
        add(txtBairro, "cell 2 4 3 1,grow");

        txtCidade = new JTextField();
        txtCidade.setFont(new Font("Arial", Font.BOLD, 14));
        txtCidade.setColumns(10);
        txtCidade.setBorder(new LineBorder(Color.BLACK, 1, true));
        add(txtCidade, "cell 2 5 3 1,grow");

        txtComplemento = new JTextField();
        txtComplemento.setFont(new Font("Arial", Font.BOLD, 14));
        txtComplemento.setBorder(new LineBorder(Color.BLACK, 1, true));
        txtComplemento.setColumns(10);
        add(txtComplemento, "cell 2 6 3 1,grow");

    }

    public void setButtons() {

    }

    public void setJTable() {

    }

}
