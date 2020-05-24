package model.vo.movimentos;

import model.vo.cliente.ClienteVO;

import java.time.LocalDateTime;

public class TicketVO {

    private int id;
    private long numero;
    private double valor;
    private String tipo;
    private LocalDateTime dataEntrada;
    private LocalDateTime dataValidacao;
    private Boolean status;
    private Boolean validado;
    private ClienteVO cliente;

    public TicketVO(int id, long numero, double valor, String tipo, LocalDateTime dataEntrada, LocalDateTime dataValidacao, Boolean status, Boolean validado, ClienteVO cliente) {
        super();
        this.id = id;
        this.numero = numero;
        this.valor = valor;
        this.tipo = tipo;
        this.dataEntrada = dataEntrada;
        this.dataValidacao = dataValidacao;
        this.status = status;
        this.validado = validado;
        this.cliente = cliente;
    }

    public TicketVO(long numero, LocalDateTime dataEntrada, Boolean status, Boolean validado) {
        this.numero = numero;
        this.dataEntrada = dataEntrada;
        this.status = status;
        this.validado = validado;
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

    public ClienteVO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteVO cliente) {
        this.cliente = cliente;
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

    @Override
    public String toString() {
        return "ID: " + this.id +
               " Número: " + this.numero +
               " -  valor: " + this.valor +
               " -  dataValidacao: " + this.dataValidacao +
               " -  cliente: " + this.cliente;
    }
}
