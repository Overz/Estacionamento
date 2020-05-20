package util;

import java.time.format.DateTimeFormatter;

public class Constantes {

    public static final String LBL_TEXT_CAIXA_TOTAL = "Total(R$):";
    public static final String LBL_TEXT_CAIXA_CARTAO = "Saldo em Cartão:";
    public static final String LBL_TEXT_CAIXA_DINHEIRO = "Saldo em Dinheiro(R$):";
    public static final String JOP_SELECIONE = "~ SELECIONE ~";
    public static final String JOP_DINHEIRO = "1 - DINHEIRO";
    public static final String JOP_CARTAO = "2 - CARTÃO";

    public static double LBL_VALOR_CAIXA_TOTAL;
    public static double LBL_VALOR_CAIXA_DINHEIRO;
    public static double LBL_VALOR_CAIXA_CARTAO;

    /**
     * Flag para Sinalizar algumas Ações
     * <p>
     * 0 : 1
     */
    public static int FLAG = 0;

    /**
     * Flag para Auxiliar a Sinalizar Respostas mais Internas
     * <p>
     * 0 : 1
     */
    public static int INTERNAL_MESSAGE = 0;

    public static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy - hh:mm:ss");

    public static final String[] COLUNAS_INICIO = {"Ticket/Cartão", "CARRO", "PLACA", "CLIENTE", "ENTRADA"};
    public static final String[] COLUNAS_CAIXA = {"Ticket/Cartão", "Descrição", "Hora Entrada", "Hora Validação", "Pagamento", "Valor"};
    public static final String[] COLUNAS_CLIENTE = {"#", "NOME", "PLANO", "VENCIMENTO"};
    public static final String[] COLUNAS_MOVIMENTO = {"Número", "Nome", "Plano", "Placa", "Valor", "Entrada", "Saída"};
    public static final String[] COLUNAS_CADASTRO_CLIENTE = {"PLACA", "MARCA", "MODELO", "DESCRIÇÃO", "CÓDIGO CARTÃO"};

    /**
     * Valores referentes aos Campos do Banco de Dados
     */
    public static final String DB_MOVIMENTO_ID = "idmovimento";
    public static final String DB_MOVIMENTO_ENTRADA = "hr_entrada";
    public static final String DB_MOVIMENTO_SAIDA = "hr_saida";

    public static final String DB_PLANO_ID = "idplano";
    public static final String DB_PLANO_TIPO = "tipo";
    public static final String DB_PLANO_DESCRICAO = "tipo";

    public static final String DB_CONTRATO_ID = "idcontrato";
    public static final String DB_CONTRATO_NUM_CARTAO = "n_cartao";
    public static final String DB_CONTRATO_DT_ENTRADA = "dt_entrada";
    public static final String DB_CONTRATO_DT_SAIDA = "dt_saida";
    public static final String DB_CONTRATO_ATIVO = "ativo";
    public static final String DB_CONTRATO_VALOR = "valor";

    public static final String DB_TICKET_ID = "idticket";
    public static final String DB_TICKET_NUM_TICKET = "n_ticket";
    public static final String DB_TICKET_VALOR = "valor";
    public static final String DB_TICKET_DT_VALIDACAO = "hr_validacao";

    public static final String DB_CLIENTE_ID = "idcliente";
    public static final String DB_CLIENTE_NOME = "nome";
    public static final String DB_CLIENTE_CPF = "cpf";
    public static final String DB_CLIENTE_RG = "rg";
    public static final String DB_CLIENTE_EMAIL = "email";
    public static final String DB_CLIENTE_TELEFONE = "telefone";

    public static final String DB_CARRO_ID = "idcarro";
    public static final String DB_CARRO_PLACA = "placa";
    public static final String DB_CARRO_COR = "cor";

    public static final String DB_MODELO_ID = "idmodelo";
    public static final String DB_MODELO_DESCRICAO = "descricao";

    public static final String DB_MARCA_ID = "idmarca";
    public static final String DB_MARCA_NOME = "nome";

    public static final String DB_ENDERECO_ID = "idendereco";
    public static final String DB_ENDERECO_NUMERO = "numero";
    public static final String DB_ENDERECO_RUA = "rua";
    public static final String DB_ENDERECO_BAIRRO = "bairro";
    public static final String DB_ENDERECO_CIDADE = "cidade";
}