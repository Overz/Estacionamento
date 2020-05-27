package model.seletor;

import util.Constantes;

import java.time.LocalDate;

public class Seletor {

    private String valor;
    private LocalDate dt1;
    private LocalDate dt2;
    private boolean temFiltro;

    public boolean temFiltro() {
        return temFiltro = valor != null && !valor.trim().isEmpty() && valor.trim().length() > 0;
    }

    public boolean temData() {
        return temFiltro = dt1 != null && dt2 != null;
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

        if (Constantes.INTERNAL_MESSAGE == 3) {
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
        if (Constantes.INTERNAL_MESSAGE == 4) {
            if (temFiltro) {
                if (!primeiro) {
                    qry += " AND ";
                }
                qry += " hr_entrada >= '" + dt1 + "' and  hr_saida <= '" + dt2 + "' ";
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

    public LocalDate getDt1() {
        return dt1;
    }

    public void setDt1(LocalDate dt1) {
        this.dt1 = dt1;
    }

    public LocalDate getDt2() {
        return dt2;
    }

    public void setDt2(LocalDate dt2) {
        this.dt2 = dt2;
    }
}
