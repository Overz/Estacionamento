package model.bo;

import model.vo.cliente.PlanoVO;

public class PlanoBO {
    public static boolean validarDadosPlano(PlanoVO p) {
        return p != null
               && !p.getTipo().trim().isEmpty();
    }

    public static boolean validarNumeroCartaoContrato(PlanoVO p) {
        Long numero = p.getContrato().getNumeroCartao();
        return numero > 5 && numero <= 30;
    }
}
