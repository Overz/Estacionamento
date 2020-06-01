package controller;

import model.banco.BaseDAO;
import model.bo.MovimentoBO;
import model.dao.movientos.MovimentoDAO;
import model.vo.movimentos.MovimentoVO;
import util.constantes.Colunas;
import util.constantes.ConstHelpers;
import util.helpers.Modificacoes;
import util.helpers.Util;
import view.panels.MovimentoView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.util.ArrayList;

public class ControllerMovimento {
    private final MovimentoView movimentoView;
    private final BaseDAO<MovimentoVO> daoM;
    private ArrayList<MovimentoVO> lista;

    public ControllerMovimento(MovimentoView movimentoView) {
        this.movimentoView = movimentoView;
        lista = new ArrayList<>();
        daoM = new MovimentoDAO();
    }

    public void atualizarTabela() {

        limparTabela();

        DefaultTableModel model = (DefaultTableModel) movimentoView.getTable().getModel();

        Object[] novaLinha = new Object[7];
        for (MovimentoVO movimento : lista) {

            if (movimento.getPlano() != null) {

                // Atualiza o Objeto Plano na tabela
                this.atualizarTabelaPlano(movimento, novaLinha);

                model.addRow(novaLinha);
            }

            if (movimento.getTicket() != null) {

                // Atualiza o Objeto Ticket na tabela
                this.atualizarTabelaTicket(movimento, novaLinha);

                model.addRow(novaLinha);
            }
        }
    }

    /**
     * Mantém a tabela atualizada com Ticket caso exista
     *
     * @param movimento MovimentoVO
     * @param novaLinha Object[]
     */
    private void atualizarTabelaTicket(MovimentoVO movimento, Object[] novaLinha) {
        novaLinha[0] = movimento.getTicket().getNumero();
        novaLinha[1] = "";
        novaLinha[2] = "";
        novaLinha[3] = "";
        if (movimento.getTicket().getValor() > 0.0) {
            novaLinha[4] = "R$: " + Util.formatarValor(movimento.getTicket().getValor());
        } else {
            novaLinha[4] = "Aguardando Validação";
        }
        novaLinha[5] = movimento.getHr_entrada().format(ConstHelpers.DTF);
        novaLinha[6] = movimento.getHr_saida().format(ConstHelpers.DTF);
    }

    /**
     * Mantém a tabela atualizada com Plano caso exista
     *
     * @param movimento MovimentoVO
     * @param novaLinha Object[]
     */
    private void atualizarTabelaPlano(MovimentoVO movimento, Object[] novaLinha) {
        novaLinha[0] = movimento.getPlano().getContrato().getNumeroCartao();
        novaLinha[1] = movimento.getPlano().getCliente().getNome();
        novaLinha[2] = movimento.getPlano().getTipo();
        novaLinha[3] = movimento.getPlano().getCliente().getCarro().getPlaca();
        novaLinha[4] = "R$: " + Util.formatarValor(movimento.getPlano().getContrato().getValor());
        novaLinha[5] = movimento.getHr_entrada().format(ConstHelpers.DTF);
        novaLinha[6] = movimento.getHr_saida().format(ConstHelpers.DTF);
    }

    public void limparTabela() {
        movimentoView.getTable().setModel(new DefaultTableModel(new Object[][]{}, Colunas.COLUNAS_MOVIMENTO));
    }

    public void consultar(String dt1, String dt2) {
        if (validarForm(dt1, dt2)) {
            ConstHelpers.FLAG = 2;
            ConstHelpers.INTERNAL_MESSAGE = 4;
            lista = daoM.consultar(dt1, dt2);
        } else {
            JOptionPane.showMessageDialog(movimentoView, Modificacoes.labelConfig(movimentoView.getLblModificacao(),
                    "Por favor, Digite todas as Datas"), "Erro", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private boolean validarForm(String a, String b) {
        return MovimentoBO.validarData2(b) && MovimentoBO.validarData1(a);
    }

    public void consultarDiaAtual() {
        ConstHelpers.FLAG = 2;
        ConstHelpers.INTERNAL_MESSAGE = 4;
        LocalDate dt = LocalDate.now();
        String hj = String.valueOf(dt);
        lista = daoM.consultar(hj, hj);
        this.atualizarTabela();
    }
}
