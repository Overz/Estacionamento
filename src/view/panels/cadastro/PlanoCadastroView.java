package view.panels.cadastro;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.Font;
import javax.swing.SwingConstants;

public class PlanoCadastroView extends JPanel {

	private static final long serialVersionUID = -8178837282155450083L;
	private JComboBox<Object> cbPlano;
	private JComboBox<String> cbFormaPgto;

	public PlanoCadastroView() {
		
		this.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		this.setLayout(new MigLayout("", "[grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow]", "[grow][grow][grow][grow][grow][grow][grow][grow]"));
		
		this.initialize();
	}

	private void initialize() {
		
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
}
