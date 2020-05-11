package view.mainFrame;

import net.miginfocom.swing.MigLayout;
import util.Constantes;
import view.panels.*;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {

    private static final long serialVersionUID = 6514484047054253588L;
    private static JLayeredPane layeredPane;
    private static final InicioView inicioView = new InicioView();
    private static final CaixaView caixaView = new CaixaView();

    public MainView() {

        this.setTitle("Estacionamento Senac - EasyWay");
        this.setBounds(100, 100, 1163, 739);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(MainView.class.getResource("/img/icons8-p-50.png")));
        this.getContentPane().setBackground(Color.WHITE);
        this.getContentPane().setLayout(new MigLayout("", "[grow]", "[grow]"));

        layeredPane = new JLayeredPane();
        layeredPane.setLayout(new MigLayout("", "[grow]", "[grow]"));
        this.getContentPane().add(layeredPane, "cell 0 0,grow");
        inicioView.setBorder(null);
        layeredPane.add(inicioView, "grow");

        this.initialize();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                MainView window = new MainView();
                window.setExtendedState(MAXIMIZED_BOTH);
                window.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private static void manterDadosImportantes() {
        String a = caixaView.getLblSaldoEmDinheiror().getText();
        String b = caixaView.getLblSaldoEmDinheiror().getText();
        String c = caixaView.getLblTotalCaixa().getText();
        if (a == null || a.equals("") || b == null || b.equals("") || c == null || c.equals("")) {
            caixaView.getLblSaldoEmDinheiror().setText(String.valueOf(Constantes.LBL_VALOR_CAIXA_DINHEIRO));
            caixaView.getLblSaldoEmCarto().setText(String.valueOf(Constantes.LBL_VALOR_CAIXA_CARTAO));
            caixaView.getLblTotalCaixa().setText(String.valueOf(Constantes.LBL_VALOR_CAIXA_TOTAL));
        }
        inicioView.getTxtTicket().repaint();
        inicioView.getTxtTicket().revalidate();
        inicioView.getTxtProcurar().repaint();
        inicioView.getTxtProcurar().revalidate();
    }

    public static void swithPanel(JPanel panel) {

        layeredPane.removeAll();

        manterDadosImportantes();
        panel.setBorder(null);
        panel.setBackground(Color.WHITE);
        panel.repaint();
        panel.revalidate();
        layeredPane.add(panel, "grow");
        layeredPane.repaint();
        layeredPane.revalidate();

    }

    private void initialize() {

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(Color.WHITE);
        setJMenuBar(menuBar);

        JButton btnInicio = new JButton("INICIO");
        btnInicio.setIcon(new ImageIcon(MainView.class.getResource("/img/icons8-fita-de-bookmark-50.png")));
        btnInicio.setFont(new Font("Arial", Font.BOLD, 16));
        btnInicio.setBackground(Color.WHITE);
        btnInicio.setBorder(null);
        menuBar.add(btnInicio);
        btnInicio.addActionListener(e -> swithPanel(inicioView));

        Component strut1 = Box.createHorizontalStrut(20);
        menuBar.add(strut1);

        JButton btnCaixa = new JButton("CAIXA");
        btnCaixa.setIcon(new ImageIcon(MainView.class.getResource("/img/icons8-caixa-registradora-50.png")));
        btnCaixa.setFont(new Font("Arial", Font.BOLD, 16));
        btnCaixa.setBackground(Color.WHITE);
        btnCaixa.setBorder(null);
        menuBar.add(btnCaixa);
        btnCaixa.addActionListener(e -> swithPanel(caixaView));

        Component strut2 = Box.createHorizontalStrut(20);
        menuBar.add(strut2);

        JButton btnClientes = new JButton("CLIENTES");
        btnClientes.setIcon(new ImageIcon(MainView.class.getResource("/img/icons8-gestão-de-cliente-50.png")));
        btnClientes.setFont(new Font("Arial", Font.BOLD, 16));
        btnClientes.setBackground(Color.WHITE);
        btnClientes.setBorder(null);
        menuBar.add(btnClientes);
        btnClientes.addActionListener(e -> {

            ClienteView clienteView = new ClienteView();
            swithPanel(clienteView);

        });

        Component strut3 = Box.createHorizontalStrut(20);
        menuBar.add(strut3);

        JButton btnMovimento = new JButton("MOVIMENTO");
        btnMovimento.setIcon(new ImageIcon(MainView.class.getResource("/img/icons8-lista-50.png")));
        btnMovimento.setFont(new Font("Arial", Font.BOLD, 16));
        btnMovimento.setBackground(Color.WHITE);
        btnMovimento.setBorder(null);
        menuBar.add(btnMovimento);
        btnMovimento.addActionListener(e -> {

            MovimentoView movimentoView = new MovimentoView();
            swithPanel(movimentoView);

        });

        Component strut4 = Box.createHorizontalStrut(20);
        menuBar.add(strut4);

        JButton btnTicketPerdido = new JButton("TICKET PERDIDO");
        btnTicketPerdido.setIcon(new ImageIcon(MainView.class.getResource("/img/icons8-busca-50.png")));
        btnTicketPerdido.setFont(new Font("Arial", Font.BOLD, 16));
        btnTicketPerdido.setBackground(Color.WHITE);
        btnTicketPerdido.setBorder(null);
        menuBar.add(btnTicketPerdido);
        btnTicketPerdido.addActionListener(e -> {

            LostTicketView ticketPerdidoView = new LostTicketView();
            swithPanel(ticketPerdidoView);

        });

        menuBar.add(Box.createHorizontalGlue());

        JButton btnConfig = new JButton("CONFIGURAÇÕES");
        btnConfig.setIcon(new ImageIcon(MainView.class.getResource("/img/atutalizacao-50.png")));
        btnConfig.setFont(new Font("Arial", Font.BOLD, 16));
        btnConfig.setBorder(null);
        btnConfig.setBackground(Color.WHITE);
        btnConfig.setAlignmentX(JButton.RIGHT_ALIGNMENT);
        menuBar.add(btnConfig);
        btnConfig.addActionListener(e -> {


        });

        Component strut5 = Box.createHorizontalStrut(35);
        menuBar.add(strut5);

    }
}