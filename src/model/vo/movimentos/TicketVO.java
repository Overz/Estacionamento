package model.vo.movimentos;

import model.vo.cliente.ClienteVO;

import java.time.LocalDateTime;

public class TicketVO {

    private int id;
    private long numero;
    private double valor;
    private String tipo;
    private LocalDateTime dataValidacao;
    private ClienteVO cliente;

    public TicketVO(int id, long numero, double valor, String tipo, LocalDateTime dataValidacao, ClienteVO cliente) {
        super();
        this.id = id;
        this.numero = numero;
        this.valor = valor;
        this.tipo = tipo;
        this.dataValidacao = dataValidacao;
        this.cliente = cliente;
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

    @Override
    public String toString() {
        return "ID: " + this.id +
                " Número: " + this.numero +
                " -  valor: " + this.valor +
                " -  dataValidacao: " + this.dataValidacao +
                " -  cliente: " + this.cliente;
    }
}
