package model.seletor;

import model.vo.movimentos.FluxoVO;

import java.time.LocalDateTime;

public class SeletorMovimento implements SuperSeletor<FluxoVO>{

    private LocalDateTime dtInicio, dtFim;
    private FluxoVO fluxoVO;

    @Override
    public String criarFiltro(String qry, FluxoVO object) {
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
    public boolean temFiltro(FluxoVO object) {
        return false;
    }

    public LocalDateTime getDtInicio() {
        return dtInicio;
    }

    public void setDtInicio(LocalDateTime dtInicio) {
        this.dtInicio = dtInicio;
    }

    public void setDtInicio(String dtInicio) {
        this.dtInicio = LocalDateTime.parse(dtInicio);
    }

    public LocalDateTime getDtFim() {
        return dtFim;
    }

    public void setDtFim(LocalDateTime dtFim) {
        this.dtFim = dtFim;
    }

    public void setDtFim(String dtFim) {
        this.dtFim = LocalDateTime.parse(dtFim);
    }

    public FluxoVO getFluxoVO() {
        return fluxoVO;
    }

    public void setFluxoVO(FluxoVO fluxoVO) {
        this.fluxoVO = fluxoVO;
    }
}
