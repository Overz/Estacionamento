package model.seletor;

import model.vo.movimentos.MovimentoVO;

public class SeletorInicio implements SuperSeletor<MovimentoVO> {

    private String txtProcurar;
    private String txtTicketCartao;
    private MovimentoVO movimentoVO;

    @Override
    public String criarFiltro(String qry, MovimentoVO object) {
        boolean primeiro = true;

        if (txtProcurar != null && !txtProcurar.isEmpty() && !txtProcurar.isEmpty()) {
            if (!primeiro) {
                qry += " inner join ";
            }
            qry += " PLANO ";
            primeiro = false;
        }

        /*var teste = "select * from movimento\n" +
                "inner join plano\n" +
                "on movimento.idmovimento = plano.idPlano\n" +
                "inner join cliente\n" +
                "on movimento.idmovimento = cliente.idcliente\n" +
                "inner join carro\n" +
                "on cliente.idcliente = carro.idcarro\n" +
                "inner join modelo \n" +
                "on carro.idcarro = modelo.idModelo;";*/

        return null;
    }

    @Override
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
