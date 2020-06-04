package util.pdf;

import java.time.format.DateTimeFormatter;

public class PdfHelpers {
    protected static final DateTimeFormatter DAY_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    protected static final DateTimeFormatter HOUR_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");
    protected static final String EXTENSAO = ".pdf";
    protected static String CAMINHO;
}
