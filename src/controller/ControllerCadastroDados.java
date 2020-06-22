package controller;

import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import javax.swing.JTable;

import model.banco.BaseDAO;
import model.dao.veiculos.MarcaDAO;
import model.dao.veiculos.ModeloDAO;
import model.vo.cliente.ClienteVO;
import model.vo.veiculo.CarroVO;
import model.vo.veiculo.MarcaVO;
import model.vo.veiculo.ModeloVO;
import util.constantes.ConstInicio;
import view.panels.cadastro.subCadastro.PanelzinhoCadastroDados;

public class ControllerCadastroDados {

	private final PanelzinhoCadastroDados cadastroView;

	public ControllerCadastroDados(JPanel panel) {
		this.cadastroView = (PanelzinhoCadastroDados) panel;
	}

	/**
	 * Pega o formulario dos dados do Cliente na tela
	 *
	 * @return ClienteVO
	 */
	public ClienteVO getFormCliente() {
		try {
			String nome = cadastroView.getTxtNome().getText();
			String cpf = cadastroView.getTxtCPF().getText();
			String rg = cadastroView.getTxtRG().getText();
			String email = cadastroView.getTxtEmail().getText();
			String fone = cadastroView.getTxtTelefone().getText();
			return new ClienteVO(nome, cpf, rg, email, fone);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Pega o formulario de cadastro de Carro
	 * 
	 * @return CarroVO
	 */
	public CarroVO getFormCarro(JTable table) {
		String placa = cadastroView.getTxtPlaca().getText();
		String cor = cadastroView.getCbCor().getSelectedItem().toString();
		MarcaVO marca = (MarcaVO) cadastroView.getCbMarca().getSelectedItem();
		ModeloVO modelo = (ModeloVO) cadastroView.getCbModelo().getSelectedItem();
		modelo.setMarca(marca);
		return new CarroVO(placa, cor, modelo);
	}

//		ADICIONA COMBO BOX A TABELA
//    private void addComboBoxTable_MarcaVO() {
//        JTable table = cadastroView.getTable();
//        TableColumn tableColumn = table.getColumnModel().getColumn(1);
//        tableColumn.setCellEditor(new DefaultCellEditor(new JComboBox<>(preencherCbx_Marca())));
//
//        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
//        renderer.setToolTipText("CLIQUE PARA O COMBO BOX APARECER");
//        tableColumn.setCellRenderer(renderer);
//    }

	public DefaultComboBoxModel<String> preencherCbx_Cores() {
		ArrayList<String> cores = new ArrayList<>();
		cores.add(ConstInicio.VAZIO);
		cores.add("AMARELO");
		cores.add("AZUL");
		cores.add("BRANCO");
		cores.add("BEGE");
		cores.add("CINZA");
		cores.add("CROMADO");
		cores.add("LARANJA");
		cores.add("MARROM");
		cores.add("PRATA");
		cores.add("PRETO");
		cores.add("ROSA");
		cores.add("ROXO");
		cores.add("VERMELHO");
		cores.add("VINHO");
		return new DefaultComboBoxModel(cores.toArray());
	}

	public DefaultComboBoxModel<MarcaVO> preencherCbx_Marca() {
		BaseDAO<MarcaVO> bDAO = new MarcaDAO();
		ArrayList<MarcaVO> list = bDAO.consultarTodos();
		return new DefaultComboBoxModel(list.toArray());
	}

	public DefaultComboBoxModel<ModeloVO> preencherCbx_Modelo() {
		BaseDAO<ModeloVO> bDAO = new ModeloDAO();
		ArrayList<ModeloVO> list = bDAO.consultarTodos();
		return new DefaultComboBoxModel(list.toArray());
	}

}
