package util;

public class Constantes {

    public static final int INICIO_VIEW = 1;
    public static final int CAIXA = 2;
    public static final int CLIENTE = 3;
    public static final int CADASTRO = 4;
    public static final int ATUALIZAR = 5;
    public static final int FLUXO = 6;

    public static final int TIPO_MENSAGEM = 1;
    public static final int TIPO_CONFIRMACAO = 2;
    public static final int TIPO_DIALOGO = 3;
    public static final int TIPO_INTERNO = 4;

    public static final String SELECIONE = "~ SELECIONE ~";
    public static final String DINHEIRO = "1 - DINHEIRO";
    public static final String CARTAO = "2 - CARTÃO";

    public static final String[] COLUNAS_CAIXA = { "Ticket/Cartão", "Descrição", "Hora Entrada", "Hora Validação", "Pagamento", "Valor" };
    public static final String[] COLUNAS_MOVIMENTO = {"Número", "Nome", "Plano", "Placa", "Valor", "Entrada", "Saída"};
}
