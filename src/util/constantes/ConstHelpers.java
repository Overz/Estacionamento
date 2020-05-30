package util.constantes;

import java.time.format.DateTimeFormatter;

public class ConstHelpers {
    public static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss - a");
    public static final String REGEX_PALAVRAS = "[a-z-A-Z]+";
    public static final String REGEX_NUMEROS = "[0-9]+";
    public static final String REGEX_NUMEROS_PALAVRAS = "[A-Za-z0-9]+";
    public static int FLAG = 0;
    public static int INTERNAL_MESSAGE = 0;
}