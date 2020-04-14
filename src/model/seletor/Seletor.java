package model.seletor;

import java.time.LocalDateTime;

public class Seletor {
	
//	Dados Carro
	private String cor, placa;
//	Dados Cliente
	private String nome, cpf, rg, rua, bairro, cidade;
	private long ticket_cartao;
//	Endereco
	
	private LocalDateTime dtInicio, dtFim;
	
	public boolean temFiltro() {
		
		if ((this.nome != null) && (this.nome.length() > 0)) {
			return true;
		}
		if ((this.cpf != null) && (this.cpf.length() > 0)) {
			return true;
		}
		if ((this.rg != null) && (this.rg.length() > 0)) {
			return true;
		}
		if ((this.ticket_cartao > 0)) {
			return true;
		}
		if ((this.rua != null) && (this.rua.length() > 0)) {
			return true;
		}
		if ((this.bairro != null) && (this.bairro.length() > 0)) {
			return true;
		}
		if ((this.cidade != null) && (this.cidade.length() > 0)) {
			return true;
		}
		if (this.dtInicio != null) {
			return true;
		}
		if (this.dtFim != null) {
			return true;
		}
		if (this.placa !=null) {
			return true;
		}
        return this.cor != null;

    }
	
	public String criarFiltroPlanoContrato(String qry) {
		
		qry += " WHERE ";
		boolean primeiro = true;

		if (this.getPlaca() != null && !this.getPlaca().trim().isEmpty()) {
			if (!primeiro) {
				qry += " AND ";
			}
			qry += " PLACA LIKE '%" + this.getPlaca() + "%'";
		}
		if (this.getCor() != null && !this.getCor().trim().isEmpty()) {
			if (!primeiro) {
				qry += " AND ";
			}
			qry += " COR LIKE '%" + this.getCor() + "%'";
		}
		
		return qry;
	}
	
	public String crairFiltroCarro(String qry) {
		
		qry += " WHERE ";
		boolean primeiro = true;

		if (this.getPlaca() != null && !this.getPlaca().trim().isEmpty()) {
			if (!primeiro) {
				qry += " AND ";
			}
			qry += " PLACA LIKE '%" + this.getPlaca() + "%'";
		}
		if (this.getCor() != null && !this.getCor().trim().isEmpty()) {
			if (!primeiro) {
				qry += " AND ";
			}
			qry += " COR LIKE '%" + this.getCor() + "%'";
		}
		
		return qry;
	}
	
	public String criarFiltroCliente(String qry) {
		
		qry += " WHERE ";
		boolean primeiro = true;

		if (this.getNome() != null && !this.getNome().trim().isEmpty()) {
			if (!primeiro) {
				qry += " AND ";
			}
			qry += " NOME LIKE '%" + this.getNome() + "%'";
		}
		if (this.getCpf() != null && !this.getCpf().trim().isEmpty()) {
			if (!primeiro) {
				qry += " AND ";
			}
			qry += " CPF LIKE '%" + this.getCpf() + "%'";
		}
		if (this.getRg() != null && !this.getRg().trim().isEmpty()) {
			if (!primeiro) {
				qry += " AND ";
			}
			qry += " RG LIKE '%" + this.getRg() + "%'";
		}
		if (this.getRua() != null && !this.getRua().trim().isEmpty()) {
			if (!primeiro) {
				qry += " AND ";
			}
			qry += " RUA LIKE '%" + this.getRua() + "%'";
		}
		if (this.getBairro() != null && !this.getBairro().trim().isEmpty()) {
			if (!primeiro) {
				qry += " AND ";
			}
			qry += " BAIRRO LIKE '%" + this.getBairro() + "%'";
		}
		if (this.getCidade() != null && !this.getCidade().trim().isEmpty()) {
			if (!primeiro) {
				qry += " AND ";
			}
			qry += " CIDADE LIKE '%" + this.getCidade() + "%'";
		}
		if (this.getTicket_cartao() > 0) {
			if (!primeiro) {
				qry += " AND ";
			}
			qry += " RG LIKE '%" + this.getRg() + "%'";
		}
		if (this.getDtInicio() != null) {
			if (!primeiro) {
				qry += " AND ";
			}
			qry += " hr_entrada = '" + this.getDtInicio() + "' ";
		}
		if (this.getDtFim() != null) {
			if (!primeiro) {
				qry += " AND ";
			}
			qry += " hr_saida = '" + this.getDtFim() + "' ";
		}
		return qry;
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

	public long getTicket_cartao() {
		return ticket_cartao;
	}

	public void setTicket_cartao(long ticket_cartao) {
		this.ticket_cartao = ticket_cartao;
	}

	public LocalDateTime getDtInicio() {
		return dtInicio;
	}

	public void setDtInicio(LocalDateTime dtInicio) {
		this.dtInicio = dtInicio;
	}

	public LocalDateTime getDtFim() {
		return dtFim;
	}

	public void setDtFim(LocalDateTime dtFim) {
		this.dtFim = dtFim;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}
	
}
