package model.vo.cliente;

import model.vo.cliente.ClienteVO;

public class PlanoVO {

	private int id;
	private String tipo;
	private String descircao;
	private ClienteVO cliente;
	private ContratoVO contrato;

	public PlanoVO(int id, String tipo, String descircao, ClienteVO cliente, ContratoVO contrato) {
		this.id = id;
		this.tipo = tipo;
		this.descircao = descircao;
		this.cliente = cliente;
		this.contrato = contrato;
	}

	public PlanoVO() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDescircao() {
		return descircao;
	}

	public void setDescircao(String descircao) {
		this.descircao = descircao;
	}

	public ClienteVO getCliente() {
		return cliente;
	}

	public void setCliente(ClienteVO cliente) {
		this.cliente = cliente;
	}

	public ContratoVO getContrato() {
		return contrato;
	}

	public void setContrato(ContratoVO contrato) {
		this.contrato = contrato;
	}

	@Override
	public String toString() {
		return "Tipo: " + this.tipo +
				" - descircao: " + this.descircao;
	}
}
