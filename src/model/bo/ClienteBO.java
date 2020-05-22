package model.bo;

import model.vo.cliente.ClienteVO;
import util.Constantes;

public class ClienteBO {

    public static boolean validarNomeCliente(ClienteVO c) {
        return c.getNome() != null
               && !c.getNome().trim().isEmpty()
               && c.getNome().trim().length() > 0
               && c.getNome().trim().length() < 255
               && c.getNome().trim().matches(Constantes.REGEX_PALAVRAS);
    }

    public static boolean validarCPFcliente(ClienteVO c) {
        return c.getCpf() != null
               && !c.getCpf().trim().isEmpty()
               && c.getCpf().trim().length() == 11
               && c.getCpf().trim().matches(Constantes.REGEX_NUMEROS);
    }

    public static boolean validarRG(ClienteVO c) {
        return c.getRg() != null
                && !c.getRg().trim().isEmpty()
                && c.getRg().trim().length() > 0
                && c.getRg().trim().length() <= 11
                && c.getRg().trim().matches(Constantes.REGEX_NUMEROS);
    }

    public static boolean validarEmail(ClienteVO c) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return c.getEmail() != null
               && c.getEmail().trim().length() > 0
                && c.getEmail().trim().length() < 255
                && c.getEmail().matches(regex);
    }

    public static boolean validarTelefone(ClienteVO c) {
        return c.getTelefone() != null
                && !c.getTelefone().trim().isEmpty()
                && c.getTelefone().trim().length() > 0
                && c.getTelefone().trim().length() <= 16
                && c.getTelefone().trim().matches(Constantes.REGEX_NUMEROS);
    }
}
