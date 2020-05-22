package model.bo;

import util.Constantes;

public class CarroBO {
    public static boolean validarCarro(String valor) {
        return valor != null
               && !valor.trim().isEmpty()
               && valor.trim().length() > 0
               && valor.trim().length() < 15
               && valor.trim().matches(Constantes.REGEX_NUMEROS_PALAVRAS);
    }
}
