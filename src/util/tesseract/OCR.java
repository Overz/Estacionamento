package util.tesseract;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static util.helpers.Util.abrirJFileChooser;

/**
 * https://nanonets.com/blog/ocr-with-tesseract/
 */
public class OCR {

    private static final String python = "python3 ";
    private JFrame frame;

    public static void main(String[] args) {
        new Runnable() {
            @Override
            public void run() {
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                int width = (int) screenSize.getWidth() / 3;
                int height = (int) screenSize.getHeight() / 3;
                int y = (int) (height * 0.1);
                int x = (int) (width * 0.2);


                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setTitle("Placa Identificada");
                frame.setBounds(x, y, width, height);
//                Thread t = new Thread((Runnable) frame);
//                t.start();
            }
        };

//        lerImagem(null);
    }

    public OCR(JFrame frame) {
        this.frame = frame;
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

            String scriptPath = new File(".").getCanonicalPath().concat("/py_script/ocr.py");
            String command = python.concat(scriptPath);
            String line, pathTest = "/home/cris/tess/wp3.png";
            Process p = Runtime.getRuntime().exec(command + " " + path);
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            ArrayList<String> listaPlacas = new ArrayList<>();
            while ((line = in.readLine()) != null) {
                listaPlacas.add(line);
                System.out.println(line);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void realizerSimulado(JPanel panel) {

        frame.setVisible(true);

        int res = abrirJFileChooser(panel, new JFileChooser());
    }

}
