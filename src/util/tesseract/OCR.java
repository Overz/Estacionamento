package util.tesseract;

import controller.ControllerInicio;
import model.banco.BaseDAO;
import model.dao.cliente.ContratoDAO;
import model.dao.movientos.MovimentoDAO;
import model.dao.movientos.TicketDAO;
import model.vo.cliente.ContratoVO;
import model.vo.movimentos.MovimentoVO;
import model.vo.movimentos.TicketVO;
import net.miginfocom.swing.MigLayout;
import util.constantes.ConstHelpers;
import util.helpers.Modificacoes;
import view.panels.mainView.MainView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class OCR extends JFrame {

    private static final String python = "python3 ";
    private static OCR window;
    private ControllerInicio controllerInicio;
    private BaseDAO<TicketVO> daoT;
    private BaseDAO<MovimentoVO> daoM;
    private BaseDAO<ContratoVO> daoC;
    private TicketVO t;
    private MovimentoVO m;
    private ArrayList<String> listaPlacas;
    private String imagePath;
    private int i = 0;
    private int start = 0;
    private int y;
    private int x;
    private ImageIcon icon;
    private JLabel label = new JLabel();
    private Timer timer;

    public static void main(String[] args) {
        try {
            EventQueue.invokeLater(() -> {
                window = new OCR();
                window.setVisible(true);
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.setResizable(false);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public OCR() {
    }

    public OCR(ControllerInicio controllerInicio, BaseDAO<MovimentoVO> daoM, BaseDAO<TicketVO> daoT, BaseDAO<ContratoVO> daoC, MovimentoVO m, TicketVO t) {
        this.controllerInicio = controllerInicio;
        this.daoM = daoM;
        this.daoT = daoT;
        this.daoC = daoC;
        this.m = m;
        this.t = t;
    }

    /**
     * Método principal para executar a Simulação do OCR
     */
    public void runOcr() {
        try {
            this.addScreenPosition();
            this.setVisible(false);
            if (start == 1) {
                ActionListener event = e -> {
                    this.lerImagem();
                    this.mostrarImagemComLabel();
                    this.cadastrar();
                };
                timer = new Timer(15000, event);
                timer.start();
                if (start == 0) {
                    timer.stop();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TODO DAR CONTINUIDADE - EXTREMA IMPORTANCIA
    // TODO Testar para ver se cadastra Ticket/Cliente
    private void cadastrar() {
        controllerInicio = new ControllerInicio(MainView.getInicioView());
        daoC = new ContratoDAO();
        daoM = new MovimentoDAO();
        daoT = new TicketDAO();

        long leftLimit = 9999L;
        long rightLimit = 999999999L;
        if (timer.isRunning()) {

            if (this.i == -1) {
                start = 0; // Método para dar Stop no timer
                timer.stop();
                timer.setRepeats(false);
            }

            if (listaPlacas != null) {

                for (String placa : listaPlacas) {
                    ConstHelpers.FLAG = 2;
                    ContratoVO c = daoC.consultar(placa); // TODO LEFT/INNER JOIN NO DAO

                    if (c != null) {
                        m = new MovimentoVO(c.getId(), LocalDateTime.now(), null, true, c);
                        m = daoM.cadastrar(m);
                        if (m != null) {
                            controllerInicio.atualizarTabela();
                            ConstHelpers.FLAG = 1; // Se cadastrar, deverá exibir um ToString personalizado
                            JOptionPane.showMessageDialog(MainView.getInicioView(),
                                    Modificacoes.labelConfig("<html><body>Placa Vinculada: " + placa
                                                             + "<br>" + c.toString() + "</body></html>"));
                        }
                    } else {
                        t = new TicketVO(controllerInicio.randomTicketGenerator(leftLimit, rightLimit), LocalDateTime.now(), true, false);
                        t = daoT.cadastrar(t);
                        if (t != null) {
                            m = new MovimentoVO(t.getId(), LocalDateTime.now(), true, t);
                            m = daoM.cadastrar(m);
                            if (m != null) {
                                controllerInicio.atualizarTabela();
                                ConstHelpers.FLAG = 1;
                                JOptionPane.showMessageDialog(MainView.getInicioView(),
                                        Modificacoes.labelConfig("<html><body>Placa Vinculada: " + placa
                                                                 + "<br>" + t.toString()));
                            }
                        }
                    }
                }
            }
        }
        ConstHelpers.TIPO_TOSTRING = 0;
    }

    /**
     * Parametros para rodar em Command Line: tesseract  --tessdata-dir  tessdataPath  image.png  output  -l  eng<br>
     * tesseract /example/tesseract /example/tesseract/datapath/ image.png outputType -l language
     * <br><br>
     * Método que utiliza de um Script em Python3, para ler imagens em OCR utilizando Tesseract
     * <br><br>
     * comando: python3 script argumento<br>
     * exemplo: <strong>python3 ocr.py wp5.png</strong>
     */
    public void lerImagem() {
        try {
            String scriptPath = Paths.get("py_script", "ocr.py").toAbsolutePath().toString();
            String command = python.concat(scriptPath);
            String line, path = this.imagePath();
            Process p = Runtime.getRuntime().exec(command + " " + path);
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            listaPlacas = new ArrayList<>();
            while ((line = in.readLine()) != null) {
                listaPlacas.add(line);
            }
        } catch (Exception ex) {
            System.out.println("Erro ao tentar processar a Imagem!");
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Retorna o caminho das imagens
     *
     * @return String
     */
    private String imagePath() {
        i++;
        switch (i) {
            case 1:
                imagePath = imagePath("bp1.png");
                break;
            case 2:
                imagePath = imagePath("bp2.png");
                break;
            case 3:
                imagePath = imagePath("bp3.png");
                break;
            case 4:
                imagePath = imagePath("bp4.png");
                break;
            case 5:
                imagePath = imagePath("bp5.png");
                break;
            case 6:
                imagePath = imagePath("bp6.png");
                break;
            case 7:
                imagePath = imagePath("bp7.png");
                break;
            case 8:
                imagePath = imagePath("bp8.png");
                break;
            case 9:
                imagePath = imagePath("bp10.png");
                break;
            case 10:
                imagePath = imagePath("bp11.png");
                break;
            case 11:
                imagePath = imagePath("bp12.png");
                break;
            case 12:
                imagePath = imagePath("bp13.png");
                break;
            case 13:
                imagePath = imagePath("bp14.png");
                break;
            case 14:
                imagePath = imagePath("bp15.png");
                break;
            case 15:
                imagePath = imagePath("bp16.png");
                break;
            case 16:
                imagePath = imagePath("bp17.png");
                i = -1;
                break;
            default:
                return null;
        }
        return imagePath;
    }

    private String imagePath(String img) {
        return Paths.get("files", img).toAbsolutePath().toString();
    }

    /**
     * Mostra um JOptionPane informando uma nova placa, com uma tela exibindo a placa
     */
    public void mostrarImagemComLabel() {
        int res;
        do { // Enquanto o Usuário não clicar em OK, o timer acrescentara um Delay de 0.5s
            timer.setDelay(500);
            res = JOptionPane.showConfirmDialog(this, Modificacoes.labelConfig("Uma nova placa foi Identificada!"),
                    "Nova Placa", JOptionPane.OK_CANCEL_OPTION);

            if (res == JOptionPane.CANCEL_OPTION || res == JOptionPane.CLOSED_OPTION) {
                timer.stop();
                JOptionPane.showMessageDialog(this, Modificacoes.labelConfig("A Simulação foi Finalizada!"));
            }
        } while (res != JOptionPane.OK_OPTION);

        if (imagePath != null && !imagePath.isEmpty()) {

            icon = new ImageIcon(imagePath);
            label.setIcon(icon);

            int h = this.getImageHeight(icon);
            int w = this.getImageWidth(icon);
            this.setBounds(x, y, w, h);
            this.setTitle("Placa Identificada");
            this.setVisible(true);
            this.setLayout(new MigLayout("", "[grow]", "[grow]"));
            this.add(label, "cell 0 0, grow");
        }
    }

    /**
     * Calcula a posição da tela
     */
    private void addScreenPosition() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        y = (int) (screenSize.height * 0.3);
        x = (int) (screenSize.width * 0.3);
    }

    /**
     * Retorna o Width do Icone a ser exibido
     *
     * @param icon ImageIcon
     * @return int
     */
    private int getImageWidth(ImageIcon icon) {
        return icon.getIconWidth();
    }

    /**
     * Calcula o Height do Icon a ser exibito
     *
     * @param icon ImageIcon
     * @return int
     */
    private int getImageHeight(ImageIcon icon) {
        return icon.getIconHeight();
    }

    public void setStart(int start) {
        this.start = start;
    }

}
