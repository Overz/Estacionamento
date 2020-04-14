package model.vo.movimentos;

import model.vo.cliente.PlanoVO;

import java.time.LocalDateTime;

public class MovimentoVO {

	private int id;
	private LocalDateTime hr_entrada;
	private LocalDateTime hr_saida;
	private PlanoVO plano;
	private TicketVO ticket;
	
	public MovimentoVO(int id, LocalDateTime hr_entrada, LocalDateTime hr_saida, PlanoVO plano, TicketVO ticket) {
		super();
		this.id = id;
		this.hr_entrada = hr_entrada;
		this.hr_saida = hr_saida;
		this.plano = plano;
		this.ticket = ticket;
	}

	public MovimentoVO() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getHr_entrada() {
		return hr_entrada;
	}

	public void setHr_entrada(LocalDateTime hr_entrada) {
		this.hr_entrada = hr_entrada;
	}

	public LocalDateTime getHr_saida() {
		return hr_saida;
	}

	public void setHr_saida(LocalDateTime hr_saida) {
		this.hr_saida = hr_saida;
	}

	public PlanoVO getPlano() {
		return plano;
	}

	public void setPlano(PlanoVO plano) {
		this.plano = plano;
	}

	public TicketVO getTicket() {
		return ticket;
	}

	public void setTicket(TicketVO ticket) {
		this.ticket = ticket;
	}

	@Override
	public String toString() {
		return "ID: " + this.id + 
				" -  hr_entrada: " + this.hr_entrada + 
				" -  hr_saida: " + this.hr_saida + 
				" -  plano: " + this.plano +
				" -  ticket: " + this.ticket;
	}

}