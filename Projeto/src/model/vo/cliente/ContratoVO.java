package model.vo.cliente;

import java.time.LocalDateTime;

import model.vo.movimentos.PlanoVO;

public class ContratoVO {
	
	private int id;
	private long n_cartao;
	private LocalDateTime dtEntrada;
	private LocalDateTime dtSaida;
	private boolean ativo;
	private double valor;
//	Objects
	private ClienteVO cliente;
	private PlanoVO plano;
	
	public ContratoVO(int id, long n_cartao, LocalDateTime dtEntrada, LocalDateTime dtSaida, boolean ativo,
			double valor, ClienteVO cliente, PlanoVO plano) {
		super();
		this.id = id;
		this.n_cartao = n_cartao;
		this.dtEntrada = dtEntrada;
		this.dtSaida = dtSaida;
		this.ativo = ativo;
		this.valor = valor;
		this.cliente = cliente;
		this.plano = plano;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getN_cartao() {
		return n_cartao;
	}

	public void setN_cartao(long n_cartao) {
		this.n_cartao = n_cartao;
	}

	public LocalDateTime getDtEntrada() {
		return dtEntrada;
	}

	public void setDtEntrada(LocalDateTime dtEntrada) {
		this.dtEntrada = dtEntrada;
	}

	public LocalDateTime getDtSaida() {
		return dtSaida;
	}

	public void setDtSaida(LocalDateTime dtSaida) {
		this.dtSaida = dtSaida;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public ClienteVO getCliente() {
		return cliente;
	}

	public void setCliente(ClienteVO cliente) {
		this.cliente = cliente;
	}

	public PlanoVO getPlano() {
		return plano;
	}

	public void setPlano(PlanoVO plano) {
		this.plano = plano;
	}
}
