package view.panels;

import net.miginfocom.swing.MigLayout;
import view.panels.cadastro.DadosCadastroView;
import view.panels.cadastro.EnderecoCadastroView;
import view.panels.cadastro.PlanoCadastroView;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CadastroView extends JPanel {

    private static final long serialVersionUID = -7538521065547926504L;
    private DadosCadastroView dadosCadastroView;
    private EnderecoCadastroView enderecoCadastroView;
    private PlanoCadastroView planoCadastroView;

    private JLayeredPane layeredPane;

    private JButton btnPlano, btnDados, btnEndereco;

    public CadastroView() {
        this.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        this.setBounds(100, 100, 1145, 908);
        this.setLayout(new MigLayout("",
                "[10px][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][10px]",
                "[10px][50px][10px][50px][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][20px][35px][35px][10px]"));

        this.initialize();
    }

    public void initialize() {

        this.setJLabels_JSeparator();

        this.setInputFields();

        this.setButtons();

        layeredPane = new JLayeredPane();
        layeredPane.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        layeredPane.setLayout(new MigLayout("", "[grow]", "[grow]"));
        add(layeredPane, "cell 1 4 14 11,grow");

        dadosCadastroView = new DadosCadastroView();
        dadosCadastroView.setBorder(null);
        layeredPane.add(dadosCadastroView, "cell 0 0,grow");

    }

    public void setJLabels_JSeparator() {
        JLabel lblAdicionarCliente = new JLabel("Adicionar Cliente:");
        lblAdicionarCliente.setHorizontalAlignment(SwingConstants.CENTER);
        lblAdicionarCliente.setFont(new Font("Arial", Font.BOLD, 22));
        lblAdicionarCliente.setBackground(Color.WHITE);
        add(lblAdicionarCliente, "cell 1 1 6 1,grow");
    }

    public void setInputFields() {

    }

    public void setButtons() {

        btnDados = new JButton("Dados");
        btnDados.setFont(new Font("Arial", Font.BOLD, 20));
        btnDados.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        add(btnDados, "cell 1 3 2 1,grow");
        btnDados.addActionListener(e -> {

            dadosCadastroView = new DadosCadastroView();
            boolean bool = swithchPanel(dadosCadastroView);
            btnDados.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (bool) {
                        btnDados.setBackground(new Color(100, 149, 237));
                        btnEndereco.setBackground(new JButton().getBackground());
                        btnPlano.setBackground(new JButton().getBackground());
                    }
                }
            });

            // TODO SALVAR OS DADOS ANTES DE MUDAR DE TELA

        });

        btnEndereco = new JButton("Endereço");
        btnEndereco.setFont(new Font("Arial", Font.BOLD, 20));
        btnEndereco.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        add(btnEndereco, "cell 3 3 2 1,grow");
        btnEndereco.addActionListener(e -> {

            enderecoCadastroView = new EnderecoCadastroView();
            boolean bool = swithchPanel(enderecoCadastroView);

            btnEndereco.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (bool) {
                        btnEndereco.setBackground(new Color(100, 149, 237));
                        btnDados.setBackground(new JButton().getBackground());
                        btnPlano.setBackground(new JButton().getBackground());
                    }
                }
            });

            // TODO SALVAR OS DADOS ANTES DE MUDAR DE TELA

        });

        btnPlano = new JButton("Plano");
        btnPlano.setFont(new Font("Arial", Font.BOLD, 20));
        btnPlano.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        add(btnPlano, "cell 5 3 2 1,grow");
        btnPlano.addActionListener(e -> {

            planoCadastroView = new PlanoCadastroView();
            boolean bool = swithchPanel(planoCadastroView);

            btnPlano.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (bool) {
                        btnPlano.setBackground(new Color(100, 149, 237));
                        btnDados.setBackground(new JButton().getBackground());
                        btnEndereco.setBackground(new JButton().getBackground());
                    }
                }
            });


            // TODO SALVAR OS DADOS ANTES DE MUDAR DE TELA

        });

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setFont(new Font("Arial", Font.BOLD, 20));
        btnSalvar.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        add(btnSalvar, "cell 5 16 3 2,grow");
        btnSalvar.addActionListener(e -> {

        });
    }

    private boolean swithchPanel(JPanel panel) {
        layeredPane.removeAll();
        panel.setBorder(null);
        panel.setBackground(Color.WHITE);
        panel.repaint();
        panel.revalidate();
        layeredPane.add(panel, "grow");
        layeredPane.repaint();
        layeredPane.revalidate();
        return panel.isVisible();
    }
}
