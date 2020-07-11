package util.constantes;

import model.vo.cliente.ClienteVO;

import java.time.format.DateTimeFormatter;

public class ConstHelpers {
    public static final String MASK_CPF = "###.###.###-##";
    public static final String MASK_RENAVAM_11 = "###########";
    public static final String MASK_RG = "##########";
    public static final String MASK_NUMBER = "#####";
    public static final String MASK_TICKET_CARD_15 = "###########";
    public static final String MASK_FONE_11 = "(##)#########";
    public static final String MASK_PLATE_7 = "AAAAAAA";
    public static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss - a");
    public static final String REGEX_PALAVRAS = "[a-z-A-Z ]+";
    public static final String REGEX_NUMEROS = "[0-9]+";
    public static final String REGEX_NUMEROS_PALAVRAS = "[A-Za-z0-9]+";
    public static final String REGEX_TELEFONE = "[\\(\\)\\0-9]+";
    public static final String REGEX_CPF = "[\\.?\\-?\\0-9]+";
    public static final int TEMPO_1_MIN = 60 * 5000;
    public static final int TEMPO_30_SEG = 30 * 1000;
    public static ClienteVO clienteForID;
    public static int FLAG = 0;
    public static int SUB_FLAG = 0;
    public static int TEMPO_X = 0;
    public static int TEMPO_TICKET = 0;
    public static int MIN = 0;
    public static int HORA = 0;
    public static double LOST_TICKET = 56.70;
    public static int TIPO_TOSTRING;
}