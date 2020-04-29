package view.mainFrame;

import net.miginfocom.swing.MigLayout;
import view.panels.*;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {

	private static final long serialVersionUID = 6514484047054253588L;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainView window = new MainView();
					window.setExtendedState(MAXIMIZED_BOTH);
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private final JLayeredPane layeredPane;
	private JMenuBar menuBar;
	private JButton btnInicio;
	private JButton btnCaixa;
	private JButton btnClientes;
	private JButton btnTicketPerdido;
	private JButton btnMovimento;
	private JButton btnConfig;

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
		InicioView inicioView = new InicioView();
		inicioView.setBorder(null);
		layeredPane.add(inicioView, "grow");
		
		this.initialize();
	}

	private void initialize() {

		menuBar = new JMenuBar();
		menuBar.setBackground(Color.WHITE);
		setJMenuBar(menuBar);
		
		btnInicio = new JButton("INICIO");
		btnInicio.setIcon(new ImageIcon(MainView.class.getResource("/img/icons8-fita-de-bookmark-50.png")));
		btnInicio.setFont(new Font("Arial", Font.BOLD, 16));
		btnInicio.setBackground(Color.WHITE);
		btnInicio.setBorder(null);
		menuBar.add(btnInicio);
		btnInicio.addActionListener(e -> {
			
			InicioView panelInicio = new InicioView();
			swithPanel(panelInicio);
			
		});
		
		Component strut1 = Box.createHorizontalStrut(20);
		menuBar.add(strut1);
		
		btnCaixa = new JButton("CAIXA");
		btnCaixa.setIcon(new ImageIcon(MainView.class.getResource("/img/icons8-caixa-registradora-50.png")));
		btnCaixa.setFont(new Font("Arial", Font.BOLD, 16));
		btnCaixa.setBackground(Color.WHITE);
		btnCaixa.setBorder(null);
		menuBar.add(btnCaixa);
		btnCaixa.addActionListener(e -> {
			
			CaixaView caixaPanel = new CaixaView();
			swithPanel(caixaPanel);
			
		});
		
		Component strut2 = Box.createHorizontalStrut(20);
		menuBar.add(strut2);
		
		btnClientes = new JButton("CLIENTES");
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
		
		btnMovimento = new JButton("MOVIMENTO");
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
		
		btnTicketPerdido = new JButton("TICKET PERDIDO");
		btnTicketPerdido.setIcon(new ImageIcon(MainView.class.getResource("/img/icons8-busca-50.png")));
		btnTicketPerdido.setFont(new Font("Arial", Font.BOLD, 16));
		btnTicketPerdido.setBackground(Color.WHITE);
		btnTicketPerdido.setBorder(null);
		menuBar.add(btnTicketPerdido);
		btnTicketPerdido.addActionListener(e -> {
			
			TicketPerdidoView ticketPerdidoView = new TicketPerdidoView();
			swithPanel(ticketPerdidoView);
			
		});
		
		menuBar.add(Box.createHorizontalGlue());
		
		btnConfig = new JButton("CONFIGURAÇÕES");
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
	
	public void swithPanel(JPanel panel) {
		
		layeredPane.removeAll();
		panel.setBorder(null);
		panel.setBackground(Color.WHITE);
		panel.repaint();
		panel.revalidate();
		layeredPane.add(panel, "grow");
		layeredPane.repaint();
		layeredPane.revalidate();
		
	}

}
