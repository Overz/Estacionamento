package model.vo.movimentos;

import util.constantes.ConstHelpers;

import java.time.LocalDateTime;

import static util.constantes.ConstHelpers.TIPO_TOSTRING;

public class TicketVO {

    private int id;
    private String placa;
    private long numero;
    private double valor;
    private String tipo;
    private LocalDateTime dataEntrada;
    private LocalDateTime dataValidacao;
    private Boolean status;
    private Boolean validado;

    public TicketVO(int id, long numero, double valor, String tipo, LocalDateTime dataEntrada, LocalDateTime dataValidacao, Boolean status, Boolean validado) {
        super();
        this.id = id;
        this.numero = numero;
        this.valor = valor;
        this.tipo = tipo;
        this.dataEntrada = dataEntrada;
        this.dataValidacao = dataValidacao;
        this.status = status;
        this.validado = validado;
    }

    public TicketVO(long numero, LocalDateTime dataEntrada, Boolean status, Boolean validado) {
        this.numero = numero;
        this.dataEntrada = dataEntrada;
        this.status = status;
        this.validado = validado;
    }

    public TicketVO(long numero, String placa, LocalDateTime dataEntrada, Boolean status, Boolean validado) {
        this.numero = numero;
        this.dataEntrada = dataEntrada;
        this.status = status;
        this.validado = validado;
        this.placa = placa;
    }

    public TicketVO() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getNumero() {
        return numero;
    }

    public void setNumero(long numero) {
        this.numero = numero;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getDataValidacao() {
        return dataValidacao;
    }

    public void setDataValidacao(LocalDateTime dataValidacao) {
        this.dataValidacao = dataValidacao;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public LocalDateTime getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDateTime dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public Boolean getValidado() {
        return validado;
    }

    public void setValidado(Boolean validado) {
        this.validado = validado;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    private String builderToString() {
        if (TIPO_TOSTRING == 1) {
            return "<br>Nº: " + numero + "<br>ENTRADA: " + dataEntrada.format(ConstHelpers.DTF);
        } else {
            return " - TICKET: " +
                   " - ID: " + id +
                   " - Nº: " + numero +
                   " - VALOR: " + valor +
                   " - TIPO: " + tipo +
                   " - DT_ENTRADA: " + dataEntrada +
                   " - DT_SAIDA: " + dataValidacao +
                   " - STATUS: " + status +
                   " - VALIDADO: " + validado;
        }
    }

    @Override
    public String toString() {
        return builderToString();
    }
}