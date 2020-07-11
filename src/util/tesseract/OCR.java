package util.tesseract;

import net.miginfocom.swing.MigLayout;
import util.helpers.Modificacoes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;

public class OCR extends JFrame {

    private static final String python = "python3 ";
    private static OCR window;
    private ArrayList<String> listaPlacas;
    private String imagePath;
    private int i = 0;
    private int start = 0;
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
            Runtime.getRuntime().exec("cd " + Paths.get("py_script").toAbsolutePath().toString() + " pip install pytesseract");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void runOcr() {
        try {
            this.addScreenPosition();
            this.setVisible(false);
            if (start == 1) {
                ActionListener event = e -> {
                    this.lerImagem();
                    this.mostrarImagemComLabel();
                };
                Timer timer = new Timer(15000, event);
                timer.start();
                if (start == 0) {
                    timer.stop();
                }
            }
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

    public int getLastIndexPlate() {
        return i;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public ArrayList<String> getListaPlacas() {
        return listaPlacas;
    }
}
