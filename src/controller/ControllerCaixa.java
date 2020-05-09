package controller;

import model.banco.BaseDAO;
import model.bo.CaixaBO;
import model.dao.movientos.MovimentoDAO;
import model.vo.movimentos.MovimentoVO;
import util.Constantes;
import view.panels.CaixaView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class ControllerCaixa {
    private final CaixaView caixaView;
    private final double total;
    private BaseDAO<MovimentoVO> daoM;
    private ArrayList<MovimentoVO> lista;

    public ControllerCaixa(CaixaView caixaView) {
        this.caixaView = caixaView;
        daoM = new MovimentoDAO();
        lista = new ArrayList<>();
        total = 0.0;
    }

    public void atualizarTabela() {
        daoM = new MovimentoDAO();
        lista = daoM.consultarTodos();

        this.limparTabela();

        DefaultTableModel model = (DefaultTableModel) caixaView.getTable().getModel();

        Object[] novaLinha = new Object[6];
        for (MovimentoVO movimento : lista) {
            novaLinha[0] = movimento.getTicket().getNumero();
            novaLinha[1] = movimento.getTicket().getCliente().getNome();
            novaLinha[2] = movimento.getHr_entrada().format(Constantes.dtf);
            novaLinha[3] = movimento.getHr_saida().format(Constantes.dtf);
            novaLinha[4] = movimento.getTicket().getTipo();
            novaLinha[5] = movimento.getTicket().getValor();

            model.addRow(novaLinha);
        }
    }

    public void limparTabela() {
        caixaView.getTable().setModel(new DefaultTableModel(new Object[][]{}, Constantes.COLUNAS_CAIXA));
    }

    //TODO Realizar ação para abrir jopotin pane, digitar valor, salvar, e usar o Label para atualizar esse valor
    private boolean addValor(String tipo, Double... values) {
        boolean bool = false;
        if (tipo.equals(Constantes.DINHEIRO)) {
            for (Double a : values) {
                Constantes.VALOR_DINHEIRO += a;
                if (CaixaBO.validarAddValor(a)) {
                    caixaView.getLblSaldoEmDinheiror().setText(String.valueOf(Constantes.VALOR_DINHEIRO));
                    bool = true;
                } else {
                    bool = false;
                }
            }
        }
        if (tipo.equals(Constantes.CARTAO)) {
            for (Double a : values) {
                if (CaixaBO.validarAddValor(a)) {
                    Constantes.VALOR_CARTAO += a;
                    caixaView.getLblSaldoEmCarto().setText(String.valueOf(Constantes.VALOR_CARTAO));
                    bool = true;
                } else {
                    bool = false;
                }
            }
        }
        return bool;
    }

    public void showInputDialog() {
        String msg1 = "Escolha o Tipo de Entrada:";
        String msg2 = "Digite o Valor:";
        String title = "Adicionar Valor";
        String success = "Valor Adicionado!";
        String error = "Erro ao Adicionar o Valor, Por favor, Digite Corretamente!";

        Object[] opcoes = {Constantes.DINHEIRO, Constantes.CARTAO};
        String a = (String) JOptionPane.showInputDialog(caixaView, msg1, title,
                JOptionPane.QUESTION_MESSAGE, null, opcoes, "");
        String b = JOptionPane.showInputDialog(caixaView, msg2, title, JOptionPane.QUESTION_MESSAGE);

        try {
            Double c = Double.valueOf(b);
            if (this.addValor(a, c)) {
                JOptionPane.showMessageDialog(caixaView,
                        caixaView.getModificacao().labelConfig(caixaView.getLblModificacao(), success),
                        title, JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(caixaView,
                        caixaView.getModificacao().labelConfig(caixaView.getLblModificacao(), error),
                        title, JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    private void totalizarCaixa() {
        String cartao = caixaView.getLblSaldoEmCarto().getText();
        String dinheiro = caixaView.getLblSaldoEmDinheiror().getText();
        try {
            Double total = Double.valueOf(cartao) + Double.valueOf(dinheiro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removerValor() {
        //TODO Realizar ação para abrir jopotin pane, digitar valor, salvar, e usar o Label para atualizar esse valor
    }

    public void fecharCaixa() {
        //TODO Realizar ação para fechar o caixa de acordo com o DIA, os dados utilizados na tabela
        //TODO e realizar a limpeza dos dados do caixa na tabela.
    }

    public void imprimirComprovante() {
        //TODO Por linha selecionada
    }

    public void imprimirRelatorio() {
        //TODO Imprimir do ultimo caixa, ou se o caixa atual já fechou
    }

}
