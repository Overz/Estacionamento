package model.seletor;

import util.constantes.ConstHelpers;

import java.time.LocalDate;

public class SeletorInicio {

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
     * where
     * <p>
     * Message == 3 {
     * t.n_ticket like '%X_value%'SELECT *
     * FROM contrato;
     * or con.n_cartao like '%X_value%'
     * }
     * <p>
     * Message == 1 {
     * or car.placa like '%X_value%'
     * or modl.descricao like '%X_value%'
     * }
     * <p>
     * Message == 2 {
     * or cli.nome like '%X_value%'
     * }
     * <p>
     * Message == 4 {
     * and hr_entrada >= 'X_value' and hr_saida <= 'X_value'
     * }
     */
    public String criarFiltro(String qry) {
        boolean primeiro = true;
        qry += " where ";

        if (ConstHelpers.SUB_FLAG == 3) {
            if (temFiltro) {
                if (!primeiro) {
                    qry += " or ";
                }
                qry += " t.n_ticket like '%" + valor + "%' ";
                primeiro = false;
            }
        }
        if (ConstHelpers.SUB_FLAG == 3) {
            if (temFiltro) {
                if (!primeiro) {
                    qry += " or ";
                }
                qry += " con.n_cartao like '%" + valor + "%' ";
                primeiro = false;
            }
        }
        if (ConstHelpers.SUB_FLAG == 1) {
            if (temFiltro) {
                if (!primeiro) {
                    qry += " or ";
                }
                qry += " car.placa like '%" + valor + "%' ";
                primeiro = false;
            }
        }
        if (ConstHelpers.SUB_FLAG == 1) {
            if (temFiltro) {
                if (!primeiro) {
                    qry += " or ";
                }
                qry += " t.placa like '%" + valor + "%' ";
                primeiro = false;
            }
        }
        if (ConstHelpers.SUB_FLAG == 1) {
            if (temFiltro) {
                if (!primeiro) {
                    qry += " or ";
                }
                qry += " modl.descricao like '%" + valor + "%' ";
                primeiro = false;
            }
        }
        if (ConstHelpers.SUB_FLAG == 2) {
            if (temFiltro) {
                if (!primeiro) {
                    qry += " or ";
                }
                qry += " cli.nome like '%" + valor + "%' ";
                primeiro = false;
            }
        }
        if (ConstHelpers.SUB_FLAG == 4) {
            if (temFiltro) {
                if (!primeiro) {
                    qry += " and ";
                }
                qry += " hr_entrada >= '" + dt1 + " 00:00:01' and " +
                       " hr_saida <= '" + dt2 + " 23:59:59' " +
                       " or hr_saida is null ";
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
