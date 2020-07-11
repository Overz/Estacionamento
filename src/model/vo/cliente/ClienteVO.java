package model.vo.cliente;

import model.vo.veiculo.CarroVO;

public class ClienteVO {

    private int id;
    private String nome;
    private String cpf;
    private String rg;
    private String email;
    private String telefone;
    //	Objects
    private EnderecoVO endereco;
    private CarroVO carro;

    public ClienteVO(int id, String nome, String cpf, String rg, String email, String telefone, EnderecoVO endereco,
                     CarroVO carro) {
        super();
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.rg = rg;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.carro = carro;
    }

    public ClienteVO(String nome, String cpf, String rg, String email, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.rg = rg;
        this.email = email;
        this.telefone = telefone;
    }

    public ClienteVO() {
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public EnderecoVO getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoVO endereco) {
        this.endereco = endereco;
    }

    public CarroVO getCarro() {
        return carro;
    }

    public void setCarro(CarroVO carro) {
        this.carro = carro;
    }

    @Override
    public String toString() {
        return " - CLIENTE: " +
               " - ID: " + this.id +
               " - Nome: " + this.nome +
               " - CPF: " + this.cpf +
               " - RG: " + this.rg +
               " - Email: " + this.email +
               " - Telefone: " + this.telefone;

    }

}
