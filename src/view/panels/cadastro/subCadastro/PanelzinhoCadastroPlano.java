package view.panels.cadastro.subCadastro;

import controller.ControllerCadastroPlano;
import model.banco.BaseDAO;
import model.dao.cliente.PlanoDAO;
import model.vo.cliente.PlanoVO;
import net.miginfocom.swing.MigLayout;
import util.constantes.ConstHelpers;
import util.constantes.ConstInicio;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

public class PanelzinhoCadastroPlano extends JPanel {

    private static final long serialVersionUID = -8178837282155450083L;
    private ControllerCadastroPlano control;
    private JComboBox<Object> cbPlano;
    private JComboBox cbFormaPgto;

    public PanelzinhoCadastroPlano() {

        this.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        this.setLayout(new MigLayout("", "[grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow]", "[grow][grow][grow][grow][grow][grow][grow][grow]"));

        this.initialize();
    }

    public void initialize() {
        control = new ControllerCadastroPlano(this);

        setJLabels_JSeparator();

        setComboBox();
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
        cbPlano = new JComboBox(control.preencherCbx());
        cbPlano.setFont(new Font("Arial", Font.BOLD, 16));
        cbPlano.setBorder(new LineBorder(Color.BLACK, 1, true));
        cbPlano.setBackground(Color.WHITE);
        add(cbPlano, "cell 2 3 4 1,grow");

        cbFormaPgto = new JComboBox<>(ConstInicio.LISTA_FORMA_PGTO.toArray());
        cbFormaPgto.setFont(new Font("Arial", Font.BOLD, 16));
        cbFormaPgto.setBorder(new LineBorder(Color.BLACK, 1, true));
        cbFormaPgto.setBackground(Color.WHITE);
        add(cbFormaPgto, "cell 2 4 4 1,grow");
    }

    public JComboBox<Object> getCbPlano() {
        return cbPlano;
    }

    public JComboBox<String> getCbFormaPgto() {
        return cbFormaPgto;
    }
}
