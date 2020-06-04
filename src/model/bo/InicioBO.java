package model.bo;

import util.constantes.ConstHelpers;

public class InicioBO {
    public static boolean validarNumeroTicket(String... values) {
        boolean a = false;
        for (String value : values) {
            a = value != null
                && !value.trim().isEmpty()
                && value.length() > 3
                && value.trim().matches(ConstHelpers.REGEX_NUMEROS);
        }
        return a;
    }
}
