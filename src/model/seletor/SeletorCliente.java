package model.seletor;

public class SeletorCliente {

    private String valor;
    private boolean temFiltro;

    public boolean temFiltro() {
        return temFiltro = valor != null && !valor.trim().isEmpty() && valor.trim().length() > 0;
    }

    public String criarFiltro(String qry) {
        boolean primeiro = true;
        qry += " where ";

        if (temFiltro) {
            if (!primeiro) {
                qry += " or ";
            }
            qry += " con.n_cartao = '" + valor + "' ";
            primeiro = false;
        }
        if (temFiltro) {
            if (!primeiro) {
                qry += " or ";
            }
            qry += " car.placa = '" + valor + "' ";
            primeiro = false;
        }
        if (temFiltro) {
            if (!primeiro) {
                qry += " or ";
            }
            qry += " car.cor like '%" + valor + "%' ";
            primeiro = false;
        }
        if (temFiltro) {
            if (!primeiro) {
                qry += " or ";
            }
            qry += " mdl.descricao = '" + valor + "' ";
            primeiro = false;
        }
        if (temFiltro) {
            if (!primeiro) {
                qry += " or ";
            }
            qry += " mar.nome = '" + valor + "' ";
            primeiro = false;
        }
        if (temFiltro) {
            if (!primeiro) {
                qry += " or ";
            }
            qry += " cli.nome like '%" + valor + "%' ";
            primeiro = false;
        }
        if (temFiltro) {
            if (!primeiro) {
                qry += " or ";
            }
            qry += " cli.cpf = '" + valor + "' ";
            primeiro = false;
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
