package util.pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;

import java.time.format.DateTimeFormatter;

public class PdfHelpers {
    protected static final DateTimeFormatter DAY_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    protected static final DateTimeFormatter HOUR_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");
    protected static final Font MAIN_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD, BaseColor.BLACK);
    protected static final Font FONT4ALL = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD, BaseColor.BLACK);
    public static final String EXTENSAO = ".pdf";
    protected static String CAMINHO;
}
