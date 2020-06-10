package view.panels.mainCadastro;

import com.sun.tools.javac.Main;
import net.miginfocom.swing.MigLayout;
import util.helpers.Util;
import view.panels.mainCadastro.subCadastro.DadosCadastroView;
import view.panels.mainCadastro.subCadastro.EnderecoCadastroView;
import view.panels.mainCadastro.subCadastro.PlanoCadastroView;
import view.panels.mainView.MainView;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CadastroView extends JPanel {

    private static final long serialVersionUID = -7538521065547926504L;
    private JLayeredPane layeredPane;
    private JButton btnPlano, btnDados, btnEndereco;
    private static JButton btnSalvar;
    private Boolean bool;

    public CadastroView() {
        this.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        this.setBounds(100, 100, 1145, 908);
        this.setLayout(new MigLayout("", "[10px][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][10px]", "[50px][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][10px][35px][35px][10px]"));

        this.initialize();
    }

    public void initialize() {

        layeredPane = new JLayeredPane();
        layeredPane.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        layeredPane.setLayout(new MigLayout("", "[grow]", "[grow]"));
        layeredPane.add(MainView.getDadosCadastroView(), "cell 0 0,grow");
        add(layeredPane, "cell 1 1 14 11,grow");

        this.setButtons();

        this.addListeners();

    }

    public void setButtons() {

        btnDados = new JButton("Dados");
        btnDados.setFont(new Font("Arial", Font.BOLD, 20));
        btnDados.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        add(btnDados, "cell 1 0 2 1,grow");
        btnDados.addActionListener(e -> bool = swithchPanel(MainView.getDadosCadastroView()));

        btnEndereco = new JButton("Endereço");
        btnEndereco.setFont(new Font("Arial", Font.BOLD, 20));
        btnEndereco.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        add(btnEndereco, "cell 3 0 2 1,grow");
        btnEndereco.addActionListener(e -> bool = swithchPanel(MainView.getEnderecoCadastroView()));

        btnPlano = new JButton("Plano");
        btnPlano.setFont(new Font("Arial", Font.BOLD, 20));
        btnPlano.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        add(btnPlano, "cell 5 0 2 1,grow");
        btnPlano.addActionListener(e -> bool = swithchPanel(MainView.getPlanoCadastroView()));

        btnSalvar = new JButton("Salvar");
        btnSalvar.setFont(new Font("Arial", Font.BOLD, 20));
        btnSalvar.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        add(btnSalvar, "cell 5 13 3 2,grow");

    }

    private void addListeners() {
        btnSalvar.addActionListener(e -> {

        });

        btnDados.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (bool) {
                    Color color = new Color(100, 149, 237);
                    Util.mudarCorBotao(true, true, "", "",
                            color, null, btnDados, btnEndereco, btnPlano);
                }
            }
        });

        btnEndereco.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (bool) {
                    Color color = new Color(100, 149, 237);
                    Util.mudarCorBotao(true, true, "", "",
                            color, null, btnEndereco, btnDados, btnPlano);
                }
            }
        });

        btnPlano.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (bool) {
                    Color color = new Color(100, 149, 237);
                    Util.mudarCorBotao(true, true, "",
                            "", color, null, btnPlano, btnDados, btnEndereco);
                }
            }
        });
    }

    /**
     * Troca os paineis e retorna true conforme sua instancia
     *
     * @param panel Panel
     * @return true/false
     */
    private Boolean swithchPanel(JPanel panel) {
        layeredPane.removeAll();
        panel.setBorder(null);
        panel.setBackground(Color.WHITE);
        panel.repaint();
        panel.revalidate();
        layeredPane.add(panel, "grow");
        layeredPane.repaint();
        layeredPane.revalidate();

        if (panel instanceof DadosCadastroView) {
            return true;
        } else if (panel instanceof EnderecoCadastroView) {
            return true;
        } else if (panel instanceof PlanoCadastroView) {
            return true;
        }
        return null;
    }
}