package util.tesseract;

import net.miginfocom.swing.MigLayout;
import util.helpers.Modificacoes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class OCR extends JFrame {

    private static final String python = "python3 ";
    private static OCR window;
    private ArrayList<String> listaPlacas;
    private String imagePath;
    private int i = 0;
    private int y;
    private int x;
    private ImageIcon icon;
    private JLabel label = new JLabel();


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
        try {
            this.addScreenPosition();
            this.setVisible(false);
            ActionListener event = e -> {
                this.lerImagem();
                this.mostrarImagemComLabel();
            };
            Timer timer = new Timer(10000, event);
            timer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Parametros para rodar em Command Line: tesseract  --tessdata-dir  tessdataPath  image.png  output  -l  eng
     * tesseract /example/tesseract /example/tesseract/datapath/ image.png outputType -l language
     * <br><br>
     * Método que utiliza de um Script em Python3, para ler imagens em OCR utilizando Tesseract
     * <br><br>
     * comando: python3 script argumento<br>
     * exemplo: <strong>python3 ocr.py wp5.png</strong>
     */
    public void lerImagem() {
        try {
            String scriptPath = new File(".").getCanonicalPath().concat("/py_script/ocr.py");
            String command = python.concat(scriptPath);
            String line, path = this.imagePath();
            Process p = Runtime.getRuntime().exec(command + " " + path);
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            listaPlacas = new ArrayList<>();
            while ((line = in.readLine()) != null) {
                listaPlacas.add(line);
                System.out.println(line);
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
            // Blue plates
            case 1:
                imagePath = "/home/cris/tess/blueplate/bp1.png";
                break;
            case 2:
                imagePath = "/home/cris/tess/blueplate/bp2.png";
                break;
            case 3:
                imagePath = "/home/cris/tess/blueplate/bp3.png";
                break;
            case 4:
                imagePath = "/home/cris/tess/blueplate/bp4.png";
                break;
            case 5:
                imagePath = "/home/cris/tess/blueplate/bp5.png";
                break;
            case 6:
                imagePath = "/home/cris/tess/blueplate/bp6.png";
                break;

            // Black plates
            case 7:
                imagePath = "/home/cris/tess/blackplate/bp1.png";
                break;
            case 8:
                imagePath = "/home/cris/tess/blackplate/bp2.png";
                break;
            case 9:
                imagePath = "/home/cris/tess/blackplate/bp3.png";
                break;
            case 10:
                imagePath = "/home/cris/tess/blackplate/bp4.png";
                break;
            case 11:
                imagePath = "/home/cris/tess/blackplate/bp5.png";
                break;
            case 12:
                imagePath = "/home/cris/tess/blackplate/bp6.png";
                break;
            case 13:
                imagePath = "/home/cris/tess/blackplate/bp7.png";
                break;
            case 14:
                imagePath = "/home/cris/tess/blackplate/bp8.png";
                break;
            case 15:
                imagePath = "/home/cris/tess/blackplate/bp9.png";
                break;
            case 16:
                imagePath = "/home/cris/tess/blackplate/bp10.png";
                i = 99;
                break;
            default:
                return null;
        }
        System.out.println(imagePath);
        return imagePath;
    }

    /**
     * Mostra um JOptionPane informando uma nova placa, com uma tela exibindo a placa
     */
    public void mostrarImagemComLabel() {
        JOptionPane.showMessageDialog(this, Modificacoes.labelConfig("Uma nova placa foi Identificada!"),
                "Nova Placa", JOptionPane.INFORMATION_MESSAGE, null);
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

    public int getI() {
        return i;
    }

    public ArrayList<String> getListaPlacas() {
        return listaPlacas;
    }
}
