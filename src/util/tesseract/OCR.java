package util.tesseract;

import com.itextpdf.text.Image;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * https://nanonets.com/blog/ocr-with-tesseract/
 */
public class OCR extends JFrame {

    private static final String python = "python3 ";
    private static OCR window;
    private String path;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                window = new OCR();
                window.setVisible(true);
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.setResizable(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public OCR() {
        try {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int width = (int) screenSize.getWidth() / 3;
            int height = (int) screenSize.getHeight() / 3;
            this.setTitle("Placa Identificada");
            this.setBounds(width, height, width, height);
            this.setLayout(new MigLayout("", "[grow]", "[grow]"));

            ImageIcon icon = new ImageIcon("/home/cris/tess/wp3.png");
            JLabel label = new JLabel(icon);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            this.add(label, "cell 0 0, grow");
            lerImagem(path);
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
    private void lerImagem(String path) {
        try {
            ActionListener event = e -> {
                try {
                    String scriptPath = new File(".").getCanonicalPath().concat("/py_script/ocr.py");
                    String command = python.concat(scriptPath);
                    String line, pathTest = "/home/cris/tess/wp3.png";
                    Process p = Runtime.getRuntime().exec(command + " " + pathTest);
                    BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    ArrayList<String> listaPlacas = new ArrayList<>();
                    while ((line = in.readLine()) != null) {
                        listaPlacas.add(line);
                        System.out.println(line);
                    }
                } catch (Exception ex) {
                    System.out.println("Erro ao tentar processar a Imagem!");
                    System.out.println(ex.getMessage());
                    ex.printStackTrace();
                }
            };

        } catch (Exception e) {
            System.out.println("Erro no Action Listener de OCR");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
