package model.bo;

import model.vo.cliente.PlanoVO;

public class PlanoBO {
    //TODO Arrumar
    public static boolean validarDadosPlano(PlanoVO p) {
        return p != null
               && !p.getTipo().trim().isEmpty();
    }

}
