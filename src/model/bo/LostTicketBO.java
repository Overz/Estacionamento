package model.bo;

import model.vo.movimentos.LostTicketVO;
import util.constantes.ConstHelpers;
import util.constantes.ConstInicio;

public class LostTicketBO {
    public static boolean validarNome(LostTicketVO vo) {
        try {
            if (vo != null) {
                return !vo.getNome().isEmpty()
                       && vo.getNome().length() > 0
                       && vo.getNome().length() < 255;
            }
        } catch (Exception e) {
            if (e != null) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public static boolean validarCpf(LostTicketVO vo) {
        try {
            if (vo != null) {
                return !vo.getCPF().isEmpty()
                       && vo.getCPF().length() > 0
                       && vo.getCPF().length() < 14
                       && vo.getCPF().matches(ConstHelpers.REGEX_NUMEROS);
            }
        } catch (Exception e) {
            if (e != null) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public static boolean validarRenavam(LostTicketVO vo) {
        try {
            if (vo != null) {
                return !vo.getRenavam().isEmpty()
                       && vo.getRenavam().length() > 0
                       && vo.getRenavam().length() < 14
                       && vo.getRenavam().matches(ConstHelpers.REGEX_NUMEROS);
            }
        } catch (Exception e) {
            if (e != null) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public static boolean validarPlaca(LostTicketVO vo) {
        try {
            if (vo != null) {
                return !vo.getPlaca().isEmpty()
                       && vo.getPlaca().length() > 0
                       && vo.getPlaca().length() < 8
                       && vo.getPlaca().matches(ConstHelpers.REGEX_NUMEROS_PALAVRAS);
            }
        } catch (Exception e) {
            if (e != null) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public static boolean validarFormaPgto(LostTicketVO vo) {
        try {
            if (vo != null) {
                return !vo.getFormaPgto().isEmpty()
                       && !vo.getFormaPgto().equals(ConstInicio.VAZIO);
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
