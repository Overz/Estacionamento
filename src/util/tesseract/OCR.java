package util.tesseract;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class OCR {

    public static void main(String[] args) {
        try {
            JFileChooser jFileChooser = new JFileChooser();
            String userName = System.getProperty("user.home");
            File dir = new File(userName + "/Desktop");

            jFileChooser.setCurrentDirectory(dir);
            jFileChooser.setDialogTitle("Salvar em...");
            String path = jFileChooser.getSelectedFile().getAbsolutePath();
            OCR.lerImagem(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Parametros para rodar em Command Line: tesseract  --tessdata-dir  tessdataPath  image.png  output  -l  eng
     * <p>
     * Método que utiliza de um Script em Python3, para ler imagens em OCR utilizando Tesseract
     */
    private static void lerImagem(String path) {
        try {

            String line, placa = "wp3.png";
            Process p = Runtime.getRuntime().exec("python3 " + path);
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

}
