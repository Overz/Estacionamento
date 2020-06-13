package model.vo.movimentos;

import model.vo.cliente.ContratoVO;
import util.constantes.ConstHelpers;

import java.time.LocalDateTime;

public class MovimentoVO {

    private int id;
    private LocalDateTime hr_entrada;
    private LocalDateTime hr_saida;
    private boolean atual;
    private TicketVO ticket;
    private ContratoVO contrato;

    public MovimentoVO(int id, LocalDateTime hr_entrada, LocalDateTime hr_saida, boolean atual, TicketVO ticket, ContratoVO contrato) {
        super();
        this.id = id;
        this.hr_entrada = hr_entrada;
        this.hr_saida = hr_saida;
        this.atual = atual;
        this.ticket = ticket;
        this.contrato = contrato;
    }

    public MovimentoVO(int id, LocalDateTime hr_entrada, boolean atual, TicketVO ticket) {
        this.id = id;
        this.hr_entrada = hr_entrada;
        this.atual = atual;
        this.ticket = ticket;
    }

    public MovimentoVO() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getHr_entrada() {
        return hr_entrada;
    }

    public void setHr_entrada(LocalDateTime hr_entrada) {
        this.hr_entrada = hr_entrada;
    }

    public LocalDateTime getHr_saida() {
        return hr_saida;
    }

    public void setHr_saida(LocalDateTime hr_saida) {
        this.hr_saida = hr_saida;
    }

    public TicketVO getTicket() {
        return ticket;
    }

    public void setTicket(TicketVO ticket) {
        this.ticket = ticket;
    }

    public ContratoVO getContrato() {
        return contrato;
    }

    public void setContrato(ContratoVO contrato) {
        this.contrato = contrato;
    }

    public boolean isAtual() {
        return atual;
    }

    public void setAtual(boolean atual) {
        this.atual = atual;
    }

    @Override
    public String toString() {
        if (hr_saida == null) {
            return " - MOVIMENTO: " +
                   " - ID: " + id +
                   " - DT_ENTRADA: " + hr_entrada.format(ConstHelpers.DTF) +
                   " - MOVI_ATUAL: " + atual;
        } else {
            return " - MOVIMENTO: " +
                   " - ID: " + id +
                   " - DT_ENTRADA: " + hr_entrada.format(ConstHelpers.DTF) +
                   " - DT_SAIDA: " + hr_saida.format(ConstHelpers.DTF) +
                   " - MOVI_ATUAL: " + atual;
        }
    }
}