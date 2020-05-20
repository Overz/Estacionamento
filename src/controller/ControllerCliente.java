package controller;

import model.banco.BaseDAO;
import model.dao.movientos.MovimentoDAO;
import model.vo.movimentos.MovimentoVO;
import util.Constantes;
import view.panels.ClienteView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class ControllerCliente {
    private final ClienteView clienteView;
    private BaseDAO<MovimentoVO> daoM;
    private ArrayList<MovimentoVO> lista;
    private String msg;

    public ControllerCliente(ClienteView clienteView) {
        this.clienteView = clienteView;
        daoM = new MovimentoDAO();
        lista = new ArrayList<>();
    }

    public void atualizarTabela() {

        limparTabela();

        daoM = new MovimentoDAO();
        lista = daoM.consultarTodos();

        DefaultTableModel model = (DefaultTableModel) clienteView.getTable().getModel();

        Object[] novaLinha = new Object[4];
        for (MovimentoVO movimento : lista) {
            novaLinha[0] = movimento.getPlano().getCliente().getId();
            novaLinha[1] = movimento.getPlano().getCliente().getNome();
            novaLinha[2] = movimento.getPlano().getTipo();
            novaLinha[3] = calcularVencimento();

            model.addRow(novaLinha);

        }
    }

    private void limparTabela() {
        clienteView.getTable().setModel(new DefaultTableModel(new Object[][]{}, Constantes.COLUNAS_CLIENTE));
    }

    private String calcularVencimento() {
        return "TESTE";
    }

    public void removeSelectedRow() {
        int row = clienteView.getTable().getSelectedRow();
        MovimentoVO m = lista.get(row);
        clienteView.getTable().remove(row);

        if (daoM.excluirPorID(m.getId())) {
            msg = "EXCLUSÃO REALIZADA COM SUCESSO!";
        } else {
            msg = "ERRO AO REALIZAR EXCLUSÃO!";
        }

        this.atualizarTabela();

        JOptionPane.showMessageDialog(clienteView, clienteView.getModificacao().labelConfig(clienteView.getLblModificacao(), msg), "EXCLUSÂO",
                JOptionPane.ERROR_MESSAGE);

        System.out.println(m.toString());
    }
}
