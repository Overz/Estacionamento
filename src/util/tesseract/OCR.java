package util.tesseract;

import net.sourceforge.tess4j.ITessAPI;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract1;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

public class OCR {

    public static void main(String[] args) {
        lerImagem();
    }

    private static void lerImagem() {
        try {
//            tesseract  --tessdata-dir  tessdataPath  image.png  output  -l  eng

            String _env = System.getenv("TESSDATA_PREFIX");
            System.out.println(_env);


            File file = new File("/home/cris/Área de Trabalho/tesseract/placa1.png");
            ITesseract tess = new Tesseract1();
            tess.setDatapath("/usr/share/tesseract-ocr/4.00/tessdata/");
            tess.setLanguage("por");
            tess.setOcrEngineMode(ITessAPI.TessOcrEngineMode.OEM_DEFAULT);
            String read = tess.doOCR(file).replaceAll("[^\\w\n.,;!?\'\":»«„”\\(\\) ]", "");
            System.out.println(read);


        } catch (TesseractException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

}
