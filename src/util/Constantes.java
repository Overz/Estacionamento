package util;

import java.time.format.DateTimeFormatter;

public class Constantes {

    public static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss - a");
    public static final String REGEX_PALAVRAS = "[a-z-A-Z]+";
    public static final String REGEX_NUMEROS = "[0-9]+";
    public static final String REGEX_NUMEROS_PALAVRAS = "[A-Za-z0-9]+";

    public static final String VAZIO = "~ SELECIONE ~";
    public static final String PROCURA = "PROCURAR POR TODOS";
    public static final String PGTO_DINHEIRO = "1 - DINHEIRO";
    public static final String PGTO_CARTAO = "2 - CARTÃO";
    public static final String PROCURA_CARRO = "PROCURAR POR CARRO";
    public static final String PROCURA_CLIENTE = "PROCURAR POR CLIENTE";
    public static final String PROCURA_TICKET_CARTAO = "PROCURAR POR TICKET/CARTÃO";

    public static final String LBL_TEXT_CAIXA_TOTAL = "Total(R$):";
    public static final String LBL_TEXT_CAIXA_CARTAO = "Saldo em Cartão:";
    public static final String LBL_TEXT_CAIXA_DINHEIRO = "Saldo em Dinheiro(R$):";
    public static double LBL_VALOR_CAIXA_TOTAL;
    public static double LBL_VALOR_CAIXA_DINHEIRO;
    public static double LBL_VALOR_CAIXA_CARTAO;

    public static int FLAG = 0;
    public static int INTERNAL_MESSAGE = 0;

    public static final String[] COLUNAS_INICIO = {"Ticket/Cartão", "CARRO", "PLACA", "CLIENTE", "ENTRADA"};
    public static final String[] COLUNAS_CAIXA = {"Ticket/Cartão", "Descrição", "Hora Entrada", "Hora Validação", "Pagamento", "Valor"};
    public static final String[] COLUNAS_CLIENTE = {"#", "NOME", "PLANO", "VENCIMENTO"};
    public static final String[] COLUNAS_MOVIMENTO = {"Número", "Nome", "Plano", "Placa", "Valor", "Entrada", "Saída"};
    public static final String[] COLUNAS_CADASTRO_CLIENTE = {"PLACA", "MARCA", "MODELO", "DESCRIÇÃO", "CÓDIGO CARTÃO"};

}