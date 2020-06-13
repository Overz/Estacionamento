package model.vo.cliente;

import util.constantes.ConstHelpers;

import java.time.LocalDateTime;

public class ContratoVO {

    private int id;
    private long numeroCartao;
    private LocalDateTime dtEntrada;
    private LocalDateTime dtSaida;
    private boolean ativo;
    private double valor;
    private String tipoPgto;
    private PlanoVO planoVO;
    private ClienteVO clienteVO;

    public ContratoVO(int id, long numeroCartao, LocalDateTime dtEntrada, LocalDateTime dtSaida, boolean ativo, double valor, String tipoPgto, PlanoVO planoVO, ClienteVO clienteVO) {
        this.id = id;
        this.numeroCartao = numeroCartao;
        this.dtEntrada = dtEntrada;
        this.dtSaida = dtSaida;
        this.ativo = ativo;
        this.valor = valor;
        this.tipoPgto = tipoPgto;
        this.planoVO = planoVO;
        this.clienteVO = clienteVO;
    }

    public ContratoVO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(long numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public LocalDateTime getDtEntrada() {
        return dtEntrada;
    }

    public void setDtEntrada(LocalDateTime dtEntrada) {
        this.dtEntrada = dtEntrada;
    }

    public LocalDateTime getDtSaida() {
        return dtSaida;
    }

    public void setDtSaida(LocalDateTime dtSaida) {
        this.dtSaida = dtSaida;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getTipoPgto() {
        return tipoPgto;
    }

    public void setTipoPgto(String tipoPgto) {
        this.tipoPgto = tipoPgto;
    }

    @Override
    public String toString() {
        if (dtSaida == null) {
            return " - ID: " + this.id +
                   " - Nº: " + this.numeroCartao +
                   " - Hora de Entrada: " + this.dtEntrada.format(ConstHelpers.DTF) +
                   " - Ativo?: " + this.ativo +
                   " - R$: " + this.valor;
        } else {
            return " - ID: " + this.id +
                   " - Nº: " + this.numeroCartao +
                   " - Hora de Entrada: " + this.dtEntrada.format(ConstHelpers.DTF) +
                   " - Hora de Saída: " + this.dtSaida.format(ConstHelpers.DTF) +
                   " - Ativo?: " + this.ativo +
                   " - R$: " + this.valor;
        }
    }

    public PlanoVO getPlano() {
        return planoVO;
    }

    public void setPlano(PlanoVO planoVO) {
        this.planoVO = planoVO;
    }

    public ClienteVO getCliente() {
        return clienteVO;
    }

    public void setCliente(ClienteVO clienteVO) {
        this.clienteVO = clienteVO;
    }
}
