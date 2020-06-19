package model.bo;

import util.constantes.ConstHelpers;

public class InicioBO {
    public static boolean validarNumeroTicket(String... values) {
        boolean a = false;
        for (String value : values) {
            try {
                if (value != null) {
                    a = !value.trim().isEmpty()
                        && value.length() > 0
                        && value.trim().matches(ConstHelpers.REGEX_NUMEROS);
                }
            } catch (Exception e) {
                if (e != null) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return a;
    }
}
