package model.bo;

import util.Constantes;

public class InicioBO {

    public static boolean validarNumeroTicket(String... values) {
        boolean a = false;
        for (String value : values) {
            a = value != null
                && !value.trim().isEmpty()
                && value.length() > 3
                && value.trim().matches(Constantes.REGEX_NUMEROS);
        }
        return a;
    }

    public static boolean validarComprovante() {
        return false;
    }
}
