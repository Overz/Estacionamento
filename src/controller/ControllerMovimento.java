package controller;

import model.banco.BaseDAO;
import model.bo.MovimentoBO;
import model.dao.movientos.MovimentoDAO;
import model.vo.movimentos.MovimentoVO;
import util.Constantes;
import util.Util;
import view.panels.MovimentoView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.util.ArrayList;

public class ControllerMovimento {
    private MovimentoView movimentoView;
    private BaseDAO<MovimentoVO> daoM;
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

            if (movimento.getPlano() == null) {
                Util.ajustarTabelaNull(movimento);
            }

            if (movimento.getPlano().getContrato().getNumeroCartao() > 0) {
                novaLinha[0] = movimento.getPlano().getContrato().getNumeroCartao();
            } else {
                novaLinha[0] = movimento.getTicket().getNumero();
            }

            if (movimento.getPlano().getCliente().getNome() != null) {
                novaLinha[1] = movimento.getPlano().getCliente().getNome();
            } else {
                novaLinha[1] = "";
            }

            if (movimento.getPlano() != null) {
                novaLinha[2] = movimento.getPlano().getTipo();
            } else {
                novaLinha[2] = "";
            }

            if (movimento.getPlano().getCliente().getCarro().getPlaca() != null) {
                novaLinha[3] = movimento.getPlano().getCliente().getCarro().getPlaca();
            } else {
                novaLinha[3] = "";
            }

            if (movimento.getPlano().getContrato().getValor() > 0.0 && movimento.getPlano().getContrato().isAtivo()) {
                novaLinha[4] = movimento.getPlano().getContrato().getValor();
            } else if (movimento.getTicket().getValor() > 0.0) {
                novaLinha[4] = movimento.getTicket().getValor();
            } else {
                novaLinha[4] = "Aguardando/Já Validado";
            }

            novaLinha[5] = movimento.getHr_entrada().format(Constantes.dtf);

            if (movimento.getHr_saida().equals(movimento.getHr_entrada())) {
                novaLinha[6] = "";
            } else {
                novaLinha[6] = movimento.getHr_saida().format(Constantes.dtf);
            }

            model.addRow(novaLinha);
        }
    }

    public void limparTabela() {
        movimentoView.getTable().setModel(new DefaultTableModel(new Object[][]{}, Constantes.COLUNAS_MOVIMENTO));
    }

    public void consultar(String dt1, String dt2) {
        if (validarForm(dt1, dt2)) {
            Constantes.FLAG = 2;
            Constantes.INTERNAL_MESSAGE = 4;
            lista = daoM.consultar(dt1, dt2);
        } else {
            JOptionPane.showMessageDialog(movimentoView, movimentoView.getModificacao().labelConfig(movimentoView.getLblModificacao(),
                    "Por favor, Digite todas as Datas"), "Erro", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private boolean validarForm(String a, String b) {
        return MovimentoBO.validarData2(b) && MovimentoBO.validarData1(a);
    }

    public void consultarDiaAtual() {
        Constantes.FLAG = 2;
        Constantes.INTERNAL_MESSAGE = 4;
        LocalDate dt = LocalDate.now();
        String hj = String.valueOf(dt);
        lista = daoM.consultar(hj, hj);
        this.atualizarTabela();
    }
}
