package controller;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.banco.BaseDAO;
import model.dao.cliente.ClienteDAO;
import model.dao.cliente.ContratoDAO;
import model.dao.movientos.MovimentoDAO;
import model.vo.cliente.ContratoVO;
import util.constantes.Colunas;
import util.constantes.ConstHelpers;
import util.helpers.Modificacoes;
import view.panels.cadastro.ListaClientesView;
import view.panels.mainView.MainView;

public class ControllerListaClientes {
	private final ListaClientesView listaClientesView;

	private final BaseDAO<ContratoVO> daoCon;
	private ArrayList<ContratoVO> list;
	private String msg;

	public ControllerListaClientes(ListaClientesView panel) {
		this.listaClientesView = panel;
		daoCon = new ContratoDAO();
		list = new ArrayList<>();
	}

	public void atualizarTabela() {

		limparTabela();

		if (ConstHelpers.FLAG == 1) {
			list = daoCon.consultarTodos();
		}

		DefaultTableModel model = (DefaultTableModel) listaClientesView.getTable().getModel();

		Object[] novaLinha = new Object[4];
		for (ContratoVO contrato : list) {

			novaLinha[0] = contrato.getCliente().getId();
			novaLinha[1] = contrato.getCliente().getNome().toUpperCase();
			novaLinha[2] = contrato.getPlano().getTipo().toUpperCase();
			novaLinha[3] = contrato.getDtSaida().format(ConstHelpers.DTF);

			model.addRow(novaLinha);
		}

		contarClientes();
		this.ajustarFocusTxtProcurar();
	}

	private void limparTabela() {
		listaClientesView.getTable().setModel(new DefaultTableModel(new Object[][] {}, Colunas.COLUNAS_CLIENTE));
	}

	private void contarClientes() {
		listaClientesView.getLblTotalDeClientes().setText("Total de Clientes Cadastrados: " + list.size());
	}

	public void removeSelectedRow() {

		int icone = JOptionPane.INFORMATION_MESSAGE;
		int row = listaClientesView.getTable().getSelectedRow();
		DefaultTableModel model = (DefaultTableModel) listaClientesView.getTable().getModel();
		if (row >= 0) {

			ContratoVO contrato = list.get(row);

			// ConstHelpers.FLAG = 1;
			// boolean a = daoCon.excluirPorID(contrato.getId());
			boolean a = new MovimentoDAO().excluirPorContratoId(contrato.getId());
			ConstHelpers.FLAG = 3;
			boolean b = daoCon.excluirPorID(contrato.getId());
			boolean c = new ClienteDAO().excluirPorID(contrato.getCliente().getId());
			if (!c) {
				System.out.println("Segunda Tentativa de Excluir Cliente");
				ConstHelpers.FLAG = 2;
				a = daoCon.excluirPorID(contrato.getId());
			}
			if (c) {
				ConstHelpers.FLAG = 1;
				msg = "EXCLUSÃO REALIZADA COM SUCESSO!";
				list.remove(row);
				model.removeRow(row);
				System.out.println("Exclusão de Cliente:" + contrato.toString());
				System.out.println();
			} else {
				msg = "ERRO AO REALIZAR EXCLUSÃO!";
				icone = JOptionPane.ERROR_MESSAGE;
			}
			this.atualizarTabela();
		} else {
			msg = "<html><body>Por favor, Selecione uma Linha Novamente!<br><br>"
					+ "A Tabela foi Atualizada!</body></html>";
		}
		JOptionPane.showMessageDialog(listaClientesView, Modificacoes.labelConfig(msg), "EXCLUSÂO", icone);
	}

	/**
	 * Consulta os valores digitados na tela e atualiza tabela
	 */
	public void consultar() {
		String text = listaClientesView.getTxtProcurar().getText();
		if (text.equals("Procurar...") || text.isEmpty()) {
			ConstHelpers.FLAG = 1;
		} else {
			ConstHelpers.FLAG = -1;
			list = daoCon.consultar(text.toUpperCase());
		}
		if (list != null) {
			atualizarTabela();
			msg = "Consulta Realizada!";
		} else {
			msg = "Erro ao Consultar!";
		}
		JOptionPane.showMessageDialog(listaClientesView, Modificacoes.labelConfig(msg), "Consulta",
				JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Atualiza o foco do campo TxtProcurar na tela após a consulta
	 */
	private void ajustarFocusTxtProcurar() {
		listaClientesView.getTxtProcurar().setText("Procurar...");
		listaClientesView.getTxtProcurar().setForeground(Color.BLACK);
	}

	public void atualizarObjeto() {
		try {
			JTable table = listaClientesView.getTable();
			int row = table.getSelectedRow();
			ContratoVO c = list.get(row);
			ControllerMainCadastro ctrl = new ControllerMainCadastro(MainView.getDadosCadastroView());
			ctrl.preencherObjetos(c);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
