package model.vo.cliente;

public class EnderecoVO {

    private int id;
    private Integer numero;
    private String rua;
    private String bairro;
    private String cidade;
    private String uf;

    public EnderecoVO(int id, Integer numero, String rua, String bairro, String cidade, String uf) {
        super();
        this.id = id;
        this.numero = numero;
        this.rua = rua;
        this.bairro = bairro;
        this.cidade = cidade;
    }

    public EnderecoVO(Integer numero, String rua, String bairro, String cidade, String uf) {
        this.numero = numero;
        this.rua = rua;
        this.bairro = bairro;
        this.cidade = cidade;
    }

    public EnderecoVO() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    @Override
    public String toString() {
        return "Número: " + this.numero +
               " - Rua: " + this.rua +
               " - Bairro: " + this.bairro +
               " - Cidade: " + this.cidade +
               " - UF: " + this.uf;
    }


}