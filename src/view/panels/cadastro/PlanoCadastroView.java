package view.panels.cadastro;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class PlanoCadastroView extends JPanel {

    private static final long serialVersionUID = -8178837282155450083L;
    private JComboBox<Object> cbPlano;
    private JComboBox<String> cbFormaPgto;

    public PlanoCadastroView() {

        this.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        this.setLayout(new MigLayout("", "[grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow]", "[grow][grow][grow][grow][grow][grow][grow][grow]"));

        this.initialize();
    }

    public void initialize() {

        setJLabels_JSeparator();

        setInputFields();

        setButtons();

        setComboBox();

        setJTable();
    }

    public void setJLabels_JSeparator() {

        JLabel lblPlano = new JLabel("Plano");
        lblPlano.setHorizontalAlignment(SwingConstants.CENTER);
        lblPlano.setFont(new Font("Arial", Font.BOLD, 16));
        lblPlano.setBackground(Color.WHITE);
        add(lblPlano, "cell 0 3 2 1,grow");

        JLabel lblFormaPgto = new JLabel("<html><body>Forma de<br align=Center>Pagamento</body></html>");
        lblFormaPgto.setHorizontalAlignment(SwingConstants.CENTER);
        lblFormaPgto.setFont(new Font("Arial", Font.BOLD, 16));
        lblFormaPgto.setBackground(Color.WHITE);
        add(lblFormaPgto, "cell 0 4 2 1,grow");

    }

    public void setComboBox() {

        cbPlano = new JComboBox<Object>();
        cbPlano.setFont(new Font("Arial", Font.BOLD, 16));
        cbPlano.setBorder(new LineBorder(Color.BLACK, 1, true));
        cbPlano.setBackground(Color.WHITE);
        add(cbPlano, "cell 2 3 4 1,grow");

        cbFormaPgto = new JComboBox<String>();
        cbFormaPgto.setFont(new Font("Arial", Font.BOLD, 16));
        cbFormaPgto.setBorder(new LineBorder(Color.BLACK, 1, true));
        cbFormaPgto.setBackground(Color.WHITE);
        add(cbFormaPgto, "cell 2 4 4 1,grow");

    }

    public void setInputFields() {

    }

    public void setButtons() {

    }

    public void setJTable() {

    }
}
