package model.bo;

import model.vo.cliente.EnderecoVO;
import util.constantes.ConstHelpers;

public class EnderecoBO {
    public static boolean validarRua(EnderecoVO e) {
        try {
            if (e != null) {
                return e.getRua() != null
                       && !e.getRua().trim().isEmpty()
                       && e.getRua().trim().length() > 0
                       && e.getRua().trim().length() < 255
                       && e.getRua().trim().matches(ConstHelpers.REGEX_PALAVRAS);
            }
        } catch (Exception e1) {
            if (e1 != null) {
                e1.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public static boolean validarBairro(EnderecoVO e) {
        try {
            if (e != null) {
                return e.getBairro() != null
                       && !e.getBairro().trim().isEmpty()
                       && e.getBairro().trim().length() > 0
                       && e.getBairro().trim().length() < 255
                       && e.getBairro().trim().matches(ConstHelpers.REGEX_PALAVRAS);
            }
        } catch (Exception e1) {
            if (e1 != null) {
                e1.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public static boolean validarCidade(EnderecoVO e) {
        try {
            if (e != null) {
                return e.getCidade() != null
                       && !e.getCidade().trim().isEmpty()
                       && e.getCidade().trim().length() > 0
                       && e.getCidade().trim().length() < 255
                       && e.getCidade().trim().matches(ConstHelpers.REGEX_PALAVRAS);
            }
        } catch (Exception e1) {
            if (e1 != null) {
                e1.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public static boolean validarNumero(EnderecoVO e) {
        try {
            if (e != null) {
                String numero = String.valueOf(e.getNumero());
                return numero.trim().length() > 0 && numero.trim().length() <= 11;
            }
        } catch (Exception e1) {
            if (e1 != null) {
                e1.printStackTrace();
                return false;
            }
        }
        return false;
    }
}
