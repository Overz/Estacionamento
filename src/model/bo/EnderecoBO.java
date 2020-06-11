package model.bo;

import model.vo.cliente.EnderecoVO;
import util.constantes.ConstHelpers;

public class EnderecoBO {
    public static boolean validarRua(EnderecoVO e) {
        return e.getRua() != null
               && !e.getRua().trim().isEmpty()
               && e.getRua().trim().length() > 0
               && e.getRua().trim().length() < 255
               && e.getRua().trim().matches(ConstHelpers.REGEX_PALAVRAS);
    }

    public static boolean validarBairro(EnderecoVO e) {
        return e.getBairro() != null
               && !e.getBairro().trim().isEmpty()
               && e.getBairro().trim().length() > 0
               && e.getBairro().trim().length() < 255
               && e.getBairro().trim().matches(ConstHelpers.REGEX_PALAVRAS);
    }

    public static boolean validarCidade(EnderecoVO e) {
        return e.getCidade() != null
               && !e.getCidade().trim().isEmpty()
               && e.getCidade().trim().length() > 0
               && e.getCidade().trim().length() < 255
               && e.getCidade().trim().matches(ConstHelpers.REGEX_PALAVRAS);
    }

    public static boolean validarNumero(EnderecoVO e) {
        return e.getNumero() != null && e.getNumero() > 0;
    }
}
