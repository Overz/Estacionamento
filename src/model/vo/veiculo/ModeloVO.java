package model.vo.veiculo;

public class ModeloVO {

    private int id;
    private String descricao;
    private MarcaVO marca;

    public ModeloVO(int id, String descricao, MarcaVO marca) {
        super();
        this.id = id;
        this.descricao = descricao;
        this.marca = marca;
    }

    public ModeloVO() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public MarcaVO getMarca() {
        return marca;
    }

    public void setMarca(MarcaVO marca) {
        this.marca = marca;
    }

    @Override
    public String toString() {
        return "Modelo: " + this.descricao;
    }

}
