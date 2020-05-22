package model.seletor;

import util.Constantes;

public class Seletor {

    private String valor;
    private boolean temFiltro;

    public boolean temFiltro() {
        return temFiltro = valor != null && !valor.trim().isEmpty() && valor.trim().length() > 0;
    }

    /**
     * where t.n_ticket like '%1%'
     * or con.n_cartao like '%1%'
     * or car.placa like '%1%'
     * or modl.descricao like '%1%'
     * or cli.nome like '%1%'
     */
    public String criarFiltro(String qry) {
        boolean primeiro = true;
        qry += " where ";

        if (Constantes.INTERNAL_MESSAGE == 3){
            if (temFiltro) {
                if (!primeiro) {
                    qry += " OR ";
                }
                qry += " t.n_ticket like '%" + valor + "%' ";
                primeiro = false;
            }
        }
        if (Constantes.INTERNAL_MESSAGE == 3) {
            if (temFiltro) {
                if (!primeiro) {
                    qry += " OR ";
                }
                qry += " con.n_cartao like '%" + valor + "%' ";
                primeiro = false;
            }
        }
        if (Constantes.INTERNAL_MESSAGE == 1) {
            if (temFiltro) {
                if (!primeiro) {
                    qry += " OR ";
                }
                qry += " car.placa like '%" + valor + "%' ";
                primeiro = false;
            }
        }
        if (Constantes.INTERNAL_MESSAGE == 1) {
            if (temFiltro) {
                if (!primeiro) {
                    qry += " OR ";
                }
                qry += " modl.descricao like '%" + valor + "%' ";
                primeiro = false;
            }
        }
        if (Constantes.INTERNAL_MESSAGE == 2) {
            if (temFiltro) {
                if (!primeiro) {
                    qry += " OR ";
                }
                qry += " cli.nome like '%" + valor + "%' ";
                primeiro = false;
            }
        }

        return qry;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
