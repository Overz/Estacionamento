package util.constantes;

import java.time.format.DateTimeFormatter;

public class ConstHelpers {
    public static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss - a");
    public static final String REGEX_PALAVRAS = "[a-z-A-Z ]+";
    public static final String REGEX_NUMEROS = "[0-9]+";
    public static final String REGEX_NUMEROS_PALAVRAS = "[A-Za-z0-9]+";
    public static int FLAG = 0;
    public static int SUB_FLAG = 0;
    public static final int TEMPO_1_MIN = 60 * 1000;
    public static final int TEMPO_30_SEG = 30 * 1000;
    public static int TEMPO_X = 0;
    public static int TEMPO_TICKET = 0;
    public static int MIN = 0;
    public static int HORA = 0;
    public static double LOST_TICKET = 56.7;
}