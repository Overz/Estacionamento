package controller;

import model.banco.BaseDAO;
import model.bo.InicioBO;
import model.dao.movientos.MovimentoDAO;
import model.vo.movimentos.MovimentoVO;
import util.Constantes;
import view.panels.InicioView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ControllerInicio {
    private final InicioView inicioViewview;
    private final BaseDAO<MovimentoVO> daoM = new MovimentoDAO();
    private ArrayList<MovimentoVO> lista;
    private String msg;

    public ControllerInicio(InicioView inicioView) {
        this.inicioViewview = inicioView;
    }

    /**
     * Atualiza a JTable com Todos os Valores
     */
    public void atualizarTabela() {
        // Limpa a tabela
        this.limparTabela();

        lista = daoM.consultarTodos();
        DefaultTableModel model = (DefaultTableModel) inicioViewview.getTable().getModel();
        // Percorre os empregados para adicionar linha a linha na tabela (JTable)
        Object[] novaLinha = new Object[5];
        for (MovimentoVO movimento : lista) {
            novaLinha[0] = String.valueOf(movimento.getTicket().getNumero());
            novaLinha[1] = movimento.getTicket().getCliente().getCarro().getModelo().getDescricao();
            novaLinha[2] = movimento.getTicket().getCliente().getCarro().getPlaca();
            novaLinha[3] = movimento.getTicket().getCliente().getNome();
            novaLinha[4] = movimento.getHr_entrada().format(Constantes.dtf);

//			 Adiciona a nova linha na tabela
            model.addRow(novaLinha);
        }
    }

    /**
     * Limpa a tela para revalidar os valores
     */
    private void limparTabela() {
        inicioViewview.getTable().setModel(new DefaultTableModel(new Object[][]{}, Constantes.COLUNAS_INICIO));
    }

    /**
     * Remover as linhas selecionadas da tablea;
     */
    public void removeSelectedRow() {

        int row = inicioViewview.getTable().getSelectedRow();
        MovimentoVO m = lista.get(row);
        inicioViewview.getTable().remove(row);

        if (daoM.excluirPorID(m.getId())) {
            msg = "EXCLUSÃO REALIZADA COM SUCESSO!";
            JOptionPane.showMessageDialog(inicioViewview, inicioViewview.getModificacao().labelConfig(inicioViewview.getLblModificadoParaExibicao(), msg), "EXCLUSÂO",
                    JOptionPane.INFORMATION_MESSAGE);
            System.out.println(m.toString());
        } else {
            msg = "ERRO AO REALIZAR EXCLUSÃO!";
            JOptionPane.showMessageDialog(inicioViewview, inicioViewview.getModificacao().labelConfig(inicioViewview.getLblModificadoParaExibicao(), msg), "EXCLUSÂO",
                    JOptionPane.ERROR_MESSAGE);
            System.out.println(m.toString());
        }
    }

    /**
     * Adiciona um Timer em Alguns Campos
     */
    public void timerRefreshData() {
        ActionListener event = actionEvent -> atualizarTabela();
        Timer timer = new Timer(10000, event);
        timer.start();
    }

    public String validate(String... values) {
        if (InicioBO.validarNumberoTicket(values)) {
            return "";
        } else {
            return "Por favor, Digite os Campos Corretamente!";
        }
    }

    public void validarTicket(String ticket) {
        // TODO Notificar o usuario na tela a validação do ticket
        // TODO validar o ticket por 10minutos
        // TODO utilizar JOptionPane para notificar a validação na View
    }
}
