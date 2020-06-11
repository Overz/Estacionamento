package controller;

import model.banco.BaseDAO;
import model.dao.veiculos.CarroDAO;
import model.vo.cliente.ClienteVO;
import model.vo.veiculo.CarroVO;
import util.constantes.ConstHelpers;
import view.panels.cadastro.subCadastro.SubCadastroDadosView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ControllerDadosCadastro {

    private final SubCadastroDadosView cadastroView;
    private final ClienteVO cliente;
    private BaseDAO<CarroVO> daoCarro;
    private ArrayList<CarroVO> linhas;
    private ArrayList<CarroVO> lista;

    public ControllerDadosCadastro(JPanel panel) {
        this.cadastroView = (SubCadastroDadosView) panel;
        linhas = new ArrayList<>();
        lista = new ArrayList<>();
        cliente = new ClienteVO();
        daoCarro = new CarroDAO();
    }

    // TODO Atualizar Tabela caso Haja Carros na tela de Atualização
    public void atualizarTabela() {
        lista = daoCarro.consultar();
    }

    /**
     * Adiciona uma nova linha a tabela
     */
    public void addrow() {
        linhas = new ArrayList<>();
        DefaultTableModel model = (DefaultTableModel) cadastroView.getTable().getModel();
        Object[] data = linhas.toArray();
        model.addRow(data);
    }

    /**
     * Cria um temporizador para setar os valores dos forumalarios
     * - Gambiarra
     */
    public void getResultadoFormTeste() {
        ActionListener event = e -> {
            ConstHelpers.FLAG = 1;
            cliente.setNome(cadastroView.getTxtNome().getText());
            cliente.setCpf(cadastroView.getTxtCPF().getText());
            cliente.setRg(cadastroView.getTxtRG().getText());
            cliente.setEmail(cadastroView.getTxtEmail().getText());
            cliente.setTelefone(cadastroView.getTxtTelefone().getText());

            int selectedRow = cadastroView.getTable().getSelectedRow();
            int selectedColumn = cadastroView.getTable().getSelectedColumn();
            CarroVO carroSelecionado =
                    (CarroVO) cadastroView.getTable().getValueAt(selectedRow, selectedColumn);

//            TableModel model = cadastroView.getTable().getModel();
//            int rows = model.getRowCount();
//            int columns = model.getColumnCount();
//            Object[] values = new Object[columns];
//
//            for (int i = 0; i < columns; i++) {
//                values[i] = model.getValueAt(selectedRow, i);
//            }
        };
        Timer timer = new Timer(300, event);
        timer.setRepeats(true);
        timer.start();
    }

    public ClienteVO getFormCliente() {
        String nome = cadastroView.getTxtNome().getText();
        String cpf = cadastroView.getTxtCPF().getText();
        String rg = cadastroView.getTxtRG().getText();
        String email = cadastroView.getTxtEmail().getText();
        String fone = cadastroView.getTxtTelefone().getText();
        boolean cbx = false;
        return new ClienteVO(nome, cpf, rg, email, fone);
    }

    public CarroVO getFormCarro() {
        for (CarroVO carro : lista) {
            return carro;
        }
        return null;
    }

    public ArrayList<CarroVO> getFormListaCarro() {
        return lista;
    }
}
