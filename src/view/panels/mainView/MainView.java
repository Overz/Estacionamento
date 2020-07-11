package view.panels.mainView;

import net.miginfocom.swing.MigLayout;
import util.constantes.ConstCaixa;
import view.panels.CaixaView;
import view.panels.LostTicketView;
import view.panels.MovimentoView;
import view.panels.cadastro.ListaClientesView;
import view.panels.cadastro.MainPanelCadastro;
import view.panels.cadastro.subCadastro.PanelzinhoCadastroDados;
import view.panels.cadastro.subCadastro.PanelzinhoCadastroEndereco;
import view.panels.cadastro.subCadastro.PanelzinhoCadastroPlano;

import javax.swing.*;
import java.awt.*;

import static util.helpers.Util.checkPythonVersion;

public class MainView extends JFrame {

    private static final long serialVersionUID = 6514484047054253588L;

    private static final PanelzinhoCadastroDados DADOS_CADASTRO_VIEW = new PanelzinhoCadastroDados();
    private static final PanelzinhoCadastroEndereco ENDERECO_CADASTRO_VIEW = new PanelzinhoCadastroEndereco();
    private static final PanelzinhoCadastroPlano PLANO_CADASTRO_VIEW = new PanelzinhoCadastroPlano();

    private static final InicioView INICIO_VIEW = new InicioView();
    private static final CaixaView CAIXA_VIEW = new CaixaView();
    private static final ListaClientesView CLIENTE_VIEW = new ListaClientesView();
    private static final MainPanelCadastro CADASTRO_VIEW = new MainPanelCadastro(0);
    private static final MovimentoView MOVIMENTO_VIEW = new MovimentoView();
    private static final LostTicketView LOST_TICKET_VIEW = new LostTicketView();
    private static JLayeredPane layeredPane;

    public MainView() {
        checkPythonVersion();

        this.setTitle("Estacionamento Senac - EasyWay");
        this.setBounds(100, 100, 1163, 739);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(MainView.class.getResource("/img/icons8-p-50.png")));
        this.getContentPane().setBackground(Color.WHITE);
        this.getContentPane().setLayout(new MigLayout("", "[grow]", "[grow]"));

        layeredPane = new JLayeredPane();
        layeredPane.setLayout(new MigLayout("", "[grow]", "[grow]"));
        this.getContentPane().add(layeredPane, "cell 0 0,grow");
        INICIO_VIEW.setBorder(null);
        layeredPane.add(INICIO_VIEW, "grow");

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

    /**
     * limpa os dados das telas de cadastro ao clicar no botão atualizar
     */
    public static void limparDadosDasTelasCadastro() {
        // DADOS
        MainView.getDadosCadastroView().getTxtRG().setText("");
        MainView.getDadosCadastroView().getTxtNome().setText("");
        MainView.getDadosCadastroView().getTxtTelefone().setText("");
        MainView.getDadosCadastroView().getTxtEmail().setText("");
        MainView.getDadosCadastroView().getTxtCPF().setText("");
        MainView.getDadosCadastroView().getTxtPlaca().setText("");
        MainView.getDadosCadastroView().getCbModelo().setSelectedIndex(-1);
        MainView.getDadosCadastroView().getCbCor().setSelectedIndex(-1);
        MainView.getDadosCadastroView().getCbModelo().setSelectedIndex(-1);

        // ENDERECO
        MainView.getEnderecoCadastroView().getTxtRua().setText("");
        MainView.getEnderecoCadastroView().getTxtNumero().setText("");
        MainView.getEnderecoCadastroView().getTxtCidade().setText("");
        MainView.getEnderecoCadastroView().getTxtBairro().setText("");
        MainView.getEnderecoCadastroView().getCbEstado().setSelectedIndex(0);

        // PLANO
        MainView.getPlanoCadastroView().getCbFormaPgto().setSelectedIndex(0);
        MainView.getPlanoCadastroView().getCbPlano().setSelectedIndex(0);
        MainView.getPlanoCadastroView().getTxtCartao().setText("");
        MainView.getPlanoCadastroView().getLblMesValidade().setText("");
    }

    private static void manterDadosImportantes() {
        String a = CAIXA_VIEW.getLblSaldoEmDinheiror().getText();
        String b = CAIXA_VIEW.getLblSaldoEmDinheiror().getText();
        String c = CAIXA_VIEW.getLblTotalCaixa().getText();
        if (a == null || a.equals("") || b == null || b.equals("") || c == null || c.equals("")) {
            CAIXA_VIEW.getLblSaldoEmDinheiror().setText(String.valueOf(ConstCaixa.LBL_VALOR_CAIXA_DINHEIRO));
            CAIXA_VIEW.getLblSaldoEmCarto().setText(String.valueOf(ConstCaixa.LBL_VALOR_CAIXA_CARTAO));
            CAIXA_VIEW.getLblTotalCaixa().setText(String.valueOf(ConstCaixa.LBL_VALOR_CAIXA_TOTAL));
        }
        INICIO_VIEW.getTxtTicket().repaint();
        INICIO_VIEW.getTxtTicket().revalidate();
        INICIO_VIEW.getTxtProcurar().repaint();
        INICIO_VIEW.getTxtProcurar().revalidate();
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

    public static PanelzinhoCadastroDados getDadosCadastroView() {
        return DADOS_CADASTRO_VIEW;
    }

    public static PanelzinhoCadastroEndereco getEnderecoCadastroView() {
        return ENDERECO_CADASTRO_VIEW;
    }

    public static PanelzinhoCadastroPlano getPlanoCadastroView() {
        return PLANO_CADASTRO_VIEW;
    }

    public static MainPanelCadastro getCadastroView() {
        return CADASTRO_VIEW;
    }

    public static InicioView getInicioView() {
        return INICIO_VIEW;
    }

    public static CaixaView getCaixaView() {
        return CAIXA_VIEW;
    }

    public static ListaClientesView getClienteView() {
        return CLIENTE_VIEW;
    }

    public static MovimentoView getMovimentoView() {
        return MOVIMENTO_VIEW;
    }

    public static LostTicketView getLostTicketView() {
        return LOST_TICKET_VIEW;
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
        btnInicio.addActionListener(e -> swithPanel(INICIO_VIEW));

        Component strut1 = Box.createHorizontalStrut(20);
        menuBar.add(strut1);

        JButton btnCaixa = new JButton("CAIXA");
        btnCaixa.setIcon(new ImageIcon(MainView.class.getResource("/img/icons8-caixa-registradora-50.png")));
        btnCaixa.setFont(new Font("Arial", Font.BOLD, 16));
        btnCaixa.setBackground(Color.WHITE);
        btnCaixa.setBorder(null);
        menuBar.add(btnCaixa);
        btnCaixa.addActionListener(e -> swithPanel(CAIXA_VIEW));

        Component strut2 = Box.createHorizontalStrut(20);
        menuBar.add(strut2);

        JButton btnClientes = new JButton("CLIENTES");
        btnClientes.setIcon(new ImageIcon(MainView.class.getResource("/img/icons8-gestão-de-cliente-50.png")));
        btnClientes.setFont(new Font("Arial", Font.BOLD, 16));
        btnClientes.setBackground(Color.WHITE);
        btnClientes.setBorder(null);
        menuBar.add(btnClientes);
        btnClientes.addActionListener(e -> swithPanel(CLIENTE_VIEW));

        Component strut3 = Box.createHorizontalStrut(20);
        menuBar.add(strut3);

        JButton btnMovimento = new JButton("MOVIMENTO");
        btnMovimento.setIcon(new ImageIcon(MainView.class.getResource("/img/icons8-lista-50.png")));
        btnMovimento.setFont(new Font("Arial", Font.BOLD, 16));
        btnMovimento.setBackground(Color.WHITE);
        btnMovimento.setBorder(null);
        menuBar.add(btnMovimento);
        btnMovimento.addActionListener(e -> swithPanel(MOVIMENTO_VIEW));

        Component strut4 = Box.createHorizontalStrut(20);
        menuBar.add(strut4);

        JButton btnTicketPerdido = new JButton("TICKET PERDIDO");
        btnTicketPerdido.setIcon(new ImageIcon(MainView.class.getResource("/img/icons8-busca-50.png")));
        btnTicketPerdido.setFont(new Font("Arial", Font.BOLD, 16));
        btnTicketPerdido.setBackground(Color.WHITE);
        btnTicketPerdido.setBorder(null);
        menuBar.add(btnTicketPerdido);
        btnTicketPerdido.addActionListener(e -> swithPanel(LOST_TICKET_VIEW));

        menuBar.add(Box.createHorizontalGlue());

        JButton btnConfig = new JButton("CONFIGURAÇÕES");
        btnConfig.setIcon(new ImageIcon(MainView.class.getResource("/img/atualizacao-50.png")));
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