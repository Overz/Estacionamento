package util.constantes;

import java.util.ArrayList;
import java.util.Arrays;

public class ConstInicio {
    public static final String VAZIO = "~ SELECIONE ~";
    public static final String PGTO_DINHEIRO = "DINHEIRO";
    public static final String PGTO_CARTAO = "CARTÃO";
    public static final String PROCURA = "PROCURAR POR TODOS";
    public static final String PROCURA_CARRO = "PROCURAR POR CARRO";
    public static final String PROCURA_CLIENTE = "PROCURAR POR CLIENTE";
    public static final String PROCURA_TICKET_CARTAO = "PROCURAR POR TICKET/CARTÃO";
    public static final ArrayList<String> LISTA_FORMA_PGTO = new ArrayList<>(Arrays.asList(PGTO_DINHEIRO, PGTO_CARTAO));
}
