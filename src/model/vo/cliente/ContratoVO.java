package model.vo.cliente;

import java.time.LocalDateTime;

public class ContratoVO {
	
	private int id;
	private long numeroCartao;
	private LocalDateTime dtEntrada;
	private LocalDateTime dtSaida;
	private boolean ativo;
	private double valor;

	public ContratoVO(int id, long numeroCartao, LocalDateTime dtEntrada, LocalDateTime dtSaida, boolean ativo, double valor) {
		this.id = id;
		this.numeroCartao = numeroCartao;
		this.dtEntrada = dtEntrada;
		this.dtSaida = dtSaida;
		this.ativo = ativo;
		this.valor = valor;
	}

	public ContratoVO() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getNumeroCartao() {
		return numeroCartao;
	}

	public void setNumeroCartao(long numeroCartao) {
		this.numeroCartao = numeroCartao;
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

	@Override
	public String toString() {
		return "Nº Cartão: " + this.numeroCartao +
				" - Hora de Entrada: " + this.dtEntrada +
				" - Hora de Saída: " + this.dtSaida + 
				" - Ativo?: " + this.ativo + 
				" - R$: " + this.valor;
	}

}
