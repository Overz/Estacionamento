package model.bo;

import util.constantes.ConstHelpers;

public class CarroBO {
    public static boolean validarCarro(String valor) {
        return valor != null
               && !valor.trim().isEmpty()
               && valor.trim().length() > 0
               && valor.trim().length() < 15
               && valor.trim().matches(ConstHelpers.REGEX_NUMEROS_PALAVRAS);
    }
}
