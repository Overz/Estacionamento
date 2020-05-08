package model.seletor;

import java.time.LocalDateTime;

public class SeletorMovimento<MovimentoVO> implements SuperSeletor<MovimentoVO> {

    private LocalDateTime dtInicio, dtFim;
    private MovimentoVO movimentoVO;

    @Override
    public String criarFiltro(String qry, MovimentoVO object) {
        qry += " WHERE ";
        boolean primeiro = true;

        if (dtInicio != null) {
            if (!primeiro) {
                qry += " AND ";
            }
            qry += " HR_ENTRADA >= " + dtInicio;
            primeiro = false;
        }
        if (dtFim != null) {
            if (!primeiro) {
                qry += " AND ";
            }
            qry += " HR_SAIDA <= " + dtFim;
        }

        return qry;
    }

    @Override
    public boolean temFiltro(MovimentoVO object) {
        return false;
    }

    public LocalDateTime getDtInicio() {
        return dtInicio;
    }

    public void setDtInicio(LocalDateTime dtInicio) {
        this.dtInicio = dtInicio;
    }

    public void setDtInicio(String dtInicio) {
        try {
            this.dtInicio = LocalDateTime.parse(dtInicio);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            System.out.println(e.getLocalizedMessage());
        }
    }

    public LocalDateTime getDtFim() {
        return dtFim;
    }

    public void setDtFim(LocalDateTime dtFim) {
        this.dtFim = dtFim;
    }

    public void setDtFim(String dtFim) {
        try {
            this.dtFim = LocalDateTime.parse(dtFim);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            System.out.println(e.getLocalizedMessage());
        }
    }

    public MovimentoVO getMovimentoVO() {
        return movimentoVO;
    }

    public void setMovimentoVO(MovimentoVO movimentoVO) {
        this.movimentoVO = movimentoVO;
    }
}
