package util.tesseract;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

public class OCR {

    private File file;
    private String path;
    private String read;

    public OCR(File file) {
        this.file = file;
    }

    private void lerImagem() {
        try {
            ITesseract tesseract = new Tesseract();
            read = tesseract.doOCR(file);
            System.out.println(read);


        } catch (TesseractException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

}
