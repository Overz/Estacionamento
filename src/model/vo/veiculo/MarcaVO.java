package model.vo.veiculo;

public class MarcaVO {

	private int id;
	private String marca;
	
	public MarcaVO(int id, String marca) {
		super();
		this.id = id;
		this.marca = marca;
	}
	
	public MarcaVO() {
		super();
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getMarca() {
		return marca;
	}
	
	public void setMarca(String marca) {
		this.marca = marca;
	}

	@Override
	public String toString() {
		return "Marca: " + this.marca;
	}
}
