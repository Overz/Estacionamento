package model.vo;

import model.vo.cliente.ClienteVO;
import model.vo.cliente.ContratoVO;
import model.vo.cliente.EnderecoVO;
import model.vo.cliente.PlanoVO;
import model.vo.movimentos.FluxoVO;
import model.vo.movimentos.MovimentoVO;
import model.vo.movimentos.TicketVO;
import model.vo.veiculo.MarcaVO;
import model.vo.veiculo.ModeloVO;

public class GetAll {
	
	private FluxoVO fluxo;
	private MovimentoVO movimento;
	private TicketVO ticket;
	private PlanoVO plano;
	private ContratoVO contrato;
	private ClienteVO cliente;
	private MovimentoVO carro;
	private ModeloVO modelo;
	private MarcaVO marca;
	private EnderecoVO endereco;
	
	public GetAll(FluxoVO fluxo, MovimentoVO movimento, TicketVO ticket, PlanoVO plano, ContratoVO contrato,
			ClienteVO cliente, MovimentoVO carro, ModeloVO modelo, MarcaVO marca, EnderecoVO endereco) {
		super();
		this.fluxo = fluxo;
		this.movimento = movimento;
		this.ticket = ticket;
		this.plano = plano;
		this.contrato = contrato;
		this.cliente = cliente;
		this.carro = carro;
		this.modelo = modelo;
		this.marca = marca;
		this.endereco = endereco;
	}

	public GetAll() {
		super();
	}

	public FluxoVO getFluxo() {
		return fluxo;
	}

	public void setFluxo(FluxoVO fluxo) {
		this.fluxo = fluxo;
	}

	public MovimentoVO getMovimento() {
		return movimento;
	}

	public void setMovimento(MovimentoVO movimento) {
		this.movimento = movimento;
	}

	public TicketVO getTicket() {
		return ticket;
	}

	public void setTicket(TicketVO ticket) {
		this.ticket = ticket;
	}

	public PlanoVO getPlano() {
		return plano;
	}

	public void setPlano(PlanoVO plano) {
		this.plano = plano;
	}

	public ContratoVO getContrato() {
		return contrato;
	}

	public void setContrato(ContratoVO contrato) {
		this.contrato = contrato;
	}

	public ClienteVO getCliente() {
		return cliente;
	}

	public void setCliente(ClienteVO cliente) {
		this.cliente = cliente;
	}

	public MovimentoVO getCarro() {
		return carro;
	}

	public void setCarro(MovimentoVO carro) {
		this.carro = carro;
	}

	public ModeloVO getModelo() {
		return modelo;
	}

	public void setModelo(ModeloVO modelo) {
		this.modelo = modelo;
	}

	public MarcaVO getMarca() {
		return marca;
	}

	public void setMarca(MarcaVO marca) {
		this.marca = marca;
	}

	public EnderecoVO getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoVO endereco) {
		this.endereco = endereco;
	}

	
}
