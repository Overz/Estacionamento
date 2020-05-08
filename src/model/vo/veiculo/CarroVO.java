package model.vo.veiculo;

public class CarroVO {

    private int id;
    private String placa;
    private String cor;
    //	Objects
    private ModeloVO modelo;

    public CarroVO(int id, String placa, String cor, ModeloVO modelo) {
        super();
        this.id = id;
        this.placa = placa;
        this.cor = cor;
        this.modelo = modelo;
    }

    public CarroVO() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public ModeloVO getModelo() {
        return modelo;
    }

    public void setModelo(ModeloVO modelo) {
        this.modelo = modelo;
    }

    @Override
    public String toString() {
        return "Placa: " + this.placa +
                " -  Cor: " + this.cor +
                " -  Modelo: " + this.modelo.toString();
    }
}
