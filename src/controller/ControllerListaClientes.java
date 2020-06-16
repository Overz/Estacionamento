package controller;

import model.banco.BaseDAO;
import model.dao.cliente.ClienteDAO;
import model.dao.cliente.ContratoDAO;
import model.dao.cliente.EnderecoDAO;
import model.dao.cliente.PlanoDAO;
import model.dao.movientos.MovimentoDAO;
import model.vo.cliente.ClienteVO;
import model.vo.cliente.ContratoVO;
import model.vo.cliente.EnderecoVO;
import model.vo.cliente.PlanoVO;
import model.vo.movimentos.MovimentoVO;
import util.constantes.Colunas;
import util.constantes.ConstHelpers;
import util.helpers.Modificacoes;
import view.panels.cadastro.ListaClientesView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;

public class ControllerListaClientes {
    private final ListaClientesView listaClientesView;

    private final BaseDAO<ContratoVO> daoCon;
    //    private final BaseDAO<MovimentoVO> daoM;
    private ArrayList<ContratoVO> list;
    private String msg;

    public ControllerListaClientes(ListaClientesView panel) {
        this.listaClientesView = panel;
        daoCon = new ContratoDAO();
//        daoM = new MovimentoDAO();
        list = new ArrayList<>();
        this.timerRefreshData();
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
            novaLinha[1] = contrato.getCliente().getNome();
            novaLinha[2] = contrato.getPlano().getTipo();
            novaLinha[3] = calcularVencimento();

            model.addRow(novaLinha);
        }

        somarClientes();
        this.ajustarFocusTxtProcurar();
    }

    private void limparTabela() {
        listaClientesView.getTable().setModel(new DefaultTableModel(new Object[][]{}, Colunas.COLUNAS_CLIENTE));
    }

    private String calcularVencimento() {
        return "TESTE";
    }

    private void somarClientes() {
        listaClientesView.getLblTotalDeClientes().setText("Total de Clientes Cadastrados: " + list.size());
    }

    public void removeSelectedRow() {

        int icone = JOptionPane.INFORMATION_MESSAGE;
        int row = listaClientesView.getTable().getSelectedRow();
        if (row >= 0) {

            ContratoVO contrato = list.get(row);

            ConstHelpers.FLAG = 1;
            if (daoCon.excluirPorID(contrato.getId())) {
                ConstHelpers.FLAG = 1;
                msg = "EXCLUSÃO REALIZADA COM SUCESSO!";

                System.out.println("Exclusão de Cliente:" + contrato.toString());
                System.out.println();
            } else {
                msg = "ERRO AO REALIZAR EXCLUSÃO!";
                icone = JOptionPane.ERROR_MESSAGE;
            }
            this.atualizarTabela();
        } else {
            msg = "<html><body>Por favor, Selecione uma Linha Novamente!<br><br>" +
                  "A Tabela foi Atualizada!</body></html>";
        }
        JOptionPane.showMessageDialog(listaClientesView, Modificacoes.labelConfig(msg), "EXCLUSÂO",
                icone);
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
        JOptionPane.showMessageDialog(listaClientesView, Modificacoes.labelConfig(msg),
                "Consulta", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Atualiza o foco do campo TxtProcurar na tela após a consulta
     */
    private void ajustarFocusTxtProcurar() {
        listaClientesView.getTxtProcurar().setText("Procurar...");
        listaClientesView.getTxtProcurar().setForeground(Color.BLACK);
    }

    private synchronized void timerRefreshData() {
        ActionListener event = e -> {
            ConstHelpers.FLAG = 1;
            this.atualizarTabela();
        };
        Timer timer = new Timer(ConstHelpers.TEMPO_30_SEG, event);
        timer.start();
    }
}
