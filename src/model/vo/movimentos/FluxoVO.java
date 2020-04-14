package model.vo.movimentos;

public class FluxoVO {
	
	private int id;
//	Objects
	private MovimentoVO movimento;
	
	public FluxoVO(int id, MovimentoVO movimento) {
		super();
		this.id = id;
		this.movimento = movimento;
	}
	
	public FluxoVO() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public MovimentoVO getMovimento() {
		return movimento;
	}

	public void setMovimento(MovimentoVO movimento) {
		this.movimento = movimento;
	}

	@Override
	public String toString() {
		return "ID: " + id + 
				" - Movimento: " + this.movimento.toString();
	}
	
}
