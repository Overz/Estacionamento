package model.bo;

import model.vo.cliente.ClienteVO;
import util.constantes.ConstHelpers;

public class ClienteBO {

    public static boolean validarNomeCliente(ClienteVO c) {
        try {
            if (c != null) {
                return !c.getNome().trim().isEmpty()
                       && c.getNome().trim().length() > 0
                       && c.getNome().trim().length() < 255
                       && c.getNome().trim().matches(ConstHelpers.REGEX_PALAVRAS);
            }
        } catch (Exception e) {
            if (e != null) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public static boolean validarCPFcliente(ClienteVO c) {
        try {
            if (c != null) {
                return !c.getCpf().trim().isEmpty()
                       && c.getCpf().trim().length() == 11
                       && c.getCpf().trim().matches(ConstHelpers.REGEX_NUMEROS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean validarRG(ClienteVO c) {
        try {
            if (c != null) {
                return !c.getRg().trim().isEmpty()
                       && c.getRg().trim().length() > 0
                       && c.getRg().trim().length() <= 11
                       && c.getRg().trim().matches(ConstHelpers.REGEX_NUMEROS)
                       || (c.getRg() != null ? c.getRg().trim().isBlank() : true)
                       || (c.getRg() != null ? c.getRg().trim().isEmpty() : true);
            }
        } catch (Exception e) {
            if (e != null) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public static boolean validarEmail(ClienteVO c) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        try {
            if (c != null) {
                return c.getEmail().trim().length() > 0
                       && c.getEmail().trim().length() < 255
                       && c.getEmail().matches(regex)
                       || (c.getEmail() != null ? c.getEmail().trim().isBlank() : true)
                       || (c.getEmail() != null ? c.getEmail().trim().isEmpty() : true);
            }
        } catch (Exception e) {
            if (e != null) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public static boolean validarTelefone(ClienteVO c) {
        try {
            if (c != null) {
                return !c.getTelefone().trim().isEmpty()
                       && c.getTelefone().trim().length() > 0
                       && c.getTelefone().trim().length() <= 16
                       && c.getTelefone().trim().matches(ConstHelpers.REGEX_NUMEROS);
            }
        } catch (Exception e) {
            if (e != null) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
}
