package controller;

import model.banco.BaseDAO;
import model.dao.cliente.ClienteDAO;
import model.vo.cliente.ClienteVO;
import model.vo.veiculo.CarroVO;
import util.Constantes;
import view.panels.cadastro.DadosCadastroView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ControllerCadastro {

    private final DadosCadastroView cadastroView;
    private ArrayList<CarroVO> linhas;
    private final BaseDAO<ClienteVO> daoC;
    private final ClienteVO cliente;
    private final CarroVO carro;

    public ControllerCadastro(DadosCadastroView cadastroView) {
        this.cadastroView = cadastroView;
        linhas = new ArrayList<>();
        daoC = new ClienteDAO();
        cliente = new ClienteVO();
        carro = new CarroVO();
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
    public void getResultadoForm() {
        ActionListener event = e -> {
            Constantes.FLAG = 1;
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

    private void teste() {


    }
}
