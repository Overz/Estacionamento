package model.seletor;

import model.vo.movimentos.MovimentoVO;

public class SeletorInicio {

    private String txtProcurar;
    private String txtTicketCartao;
    private String ticket;
    private String carro;
    private String placa;
    private String cliente;

    public String criarFiltro(String qry, MovimentoVO object) {
        boolean primeiro = true;
        qry += " WHERE ";

        if (txtTicketCartao != null && !txtTicketCartao.trim().isEmpty()) {
            if (!primeiro) {
                qry += " AND ";
            }
            qry += " n_ticket = " + object.getTicket().getNumero();
            primeiro = false;
        }
        if (txtProcurar != null && !txtProcurar.trim().isEmpty()) {
            if (!primeiro) {
                qry += " AND ";
            }
            qry += "???????????????";
        }
        return null;
    }

    public boolean temFiltro(MovimentoVO object) {
        return false;
    }

    public String getTxtProcurar() {
        return txtProcurar;
    }

    public void setTxtProcurar(String txtProcurar) {
        this.txtProcurar = txtProcurar;
    }

    public String getTxtTicketCartao() {
        return txtTicketCartao;
    }

    public void setTxtTicketCartao(String txtTicketCartao) {
        this.txtTicketCartao = txtTicketCartao;
    }
}
