package model.bo;

import model.vo.cliente.EnderecoVO;
import util.Constantes;

public class EnderecoBO {

    public static boolean validarRua(EnderecoVO e) {
        return !e.getRua().trim().isEmpty() && e.getRua().trim().length() > 5 && e.getRua().trim().matches(Constantes.REGEX_PALAVRAS);
    }

    public static boolean validarNumero(EnderecoVO e) {
        return e.getNumero() != null;
    }

    public static boolean validarBairro(EnderecoVO e) {
        return !e.getBairro().trim().isEmpty() && e.getBairro().trim().length() > 5 && e.getBairro().trim().matches(Constantes.REGEX_PALAVRAS);
    }

    public static boolean validarCidade(EnderecoVO e) {
        return !e.getCidade().trim().isEmpty() && e.getCidade().trim().length() > 5 && e.getCidade().trim().matches(Constantes.REGEX_PALAVRAS);
    }

}
