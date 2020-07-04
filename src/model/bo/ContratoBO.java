package model.bo;

import model.vo.cliente.ContratoVO;

public class ContratoBO {
    public static boolean validarNumeroCartao(ContratoVO c) {
        try {
            if (c != null) {
                String cartao = String.valueOf(c.getNumeroCartao());
                return cartao.trim().length() > 0
                       && cartao.trim().length() <= 18;
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
