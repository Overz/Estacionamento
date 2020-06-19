package model.vo.movimentos;

import java.io.InputStream;

public class LostTicketVO {

    private int id;
    private String nome;
    private String cpf;
    private String placa;
    private String renavam;
    private String formaPgto;
    private double valorCobrado;
    private byte[] documento;


    public LostTicketVO(String nome, String cpf, String placa, String renavam, String formaPgto, double valorCobrado) {
        super();
        this.nome = nome;
        this.cpf = cpf;
        this.placa = placa;
        this.renavam = renavam;
        this.formaPgto = formaPgto;
        this.valorCobrado = valorCobrado;
    }

    public LostTicketVO() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCPF() {
        return cpf;
    }

    public void setCPF(String cpf) {
        this.cpf = cpf;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getRenavam() {
        return renavam;
    }

    public void setRenavam(String renavam) {
        this.renavam = renavam;
    }

    public String getFormaPgto() {
        return formaPgto;
    }

    public void setFormaPgto(String formaPgto) {
        this.formaPgto = formaPgto;
    }


    @Override
    public String toString() {
        return "Nome:" + nome +
               " - CPF: " + this.cpf +
               " - placa: " + this.placa +
               " - renavam: " + this.renavam +
               " - formaPgto: " + this.formaPgto;
    }

    public double getValorCobrado() {
        return valorCobrado;
    }

    public void setValorCobrado(double valorCobrado) {
        this.valorCobrado = valorCobrado;
    }

    public byte[] getDocumento() {
        return documento;
    }

    public void setDocumento(byte[] documento) {
        this.documento = documento;
    }

}