package view.panels;

import net.miginfocom.swing.MigLayout;
import view.mainFrame.MainView;
import view.panels.cadastro.DadosCadastroView;
import view.panels.cadastro.EnderecoCadastroView;
import view.panels.cadastro.PlanoCadastroView;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class CadastroView extends JPanel implements BaseView {

    private static final long serialVersionUID = -7538521065547926504L;
    private DadosCadastroView dadosCadastroView;

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

        this.setJTable();

        JLayeredPane layeredPane = new JLayeredPane();
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

    @Override
    public void setInputFields() {

    }

    public void setButtons() {

        JButton btnDados = new JButton("Dados");
        btnDados.setFont(new Font("Arial", Font.BOLD, 20));
        btnDados.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        add(btnDados, "cell 1 3 2 1,grow");
        btnDados.addActionListener(e -> {

            dadosCadastroView = new DadosCadastroView();
            swithPanel(dadosCadastroView);
            if (dadosCadastroView.isShowing()) {
                if (btnDados.isSelected()) {
                    btnDados.setBackground(Color.WHITE);
                } else {
                    btnDados.setBackground(new JButton().getBackground());
                }
            }

            // TODO SALVAR OS DADOS ANTES DE MUDAR DE TELA

        });

        JButton btnEndereco = new JButton("Endereço");
        btnEndereco.setFont(new Font("Arial", Font.BOLD, 20));
        btnEndereco.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        add(btnEndereco, "cell 3 3 2 1,grow");
        btnEndereco.addActionListener(e -> {

            EnderecoCadastroView enderecoCadastroView = new EnderecoCadastroView();
            swithPanel(enderecoCadastroView);
            if (enderecoCadastroView.isShowing()) {
                if (btnDados.isSelected()) {
                    btnDados.setBackground(Color.WHITE);
                } else {
                    btnDados.setBackground(new JButton().getBackground());
                }
            }

            // TODO SALVAR OS DADOS ANTES DE MUDAR DE TELA

        });

        JButton btnPlano = new JButton("Plano");
        btnPlano.setFont(new Font("Arial", Font.BOLD, 20));
        btnPlano.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        add(btnPlano, "cell 5 3 2 1,grow");
        btnPlano.addActionListener(e -> {

            PlanoCadastroView planoCadastroView = new PlanoCadastroView();
            swithPanel(planoCadastroView);
            if (planoCadastroView.isShowing()) {
                if (btnDados.isSelected()) {
                    btnDados.setBackground(Color.WHITE);
                } else {
                    btnDados.setBackground(new JButton().getBackground());
                }
            }

            // TODO SALVAR OS DADOS ANTES DE MUDAR DE TELA

        });

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setFont(new Font("Arial", Font.BOLD, 20));
        btnSalvar.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        add(btnSalvar, "cell 5 16 3 2,grow");
        btnSalvar.addActionListener(e -> {

        });
    }

    @Override
    public void setJTable() {

    }

    public void swithPanel(JPanel panel) {
        MainView.swithPanel(panel);
    }

}
