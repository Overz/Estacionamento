package controller;

import model.banco.BaseDAO;
import model.bo.CaixaBO;
import model.dao.movientos.MovimentoDAO;
import model.vo.movimentos.MovimentoVO;
import util.Constantes;
import util.relatorio.GeradorRelatorioCaixa;
import view.panels.CaixaView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class ControllerCaixa {
    private final CaixaView caixaView;
    private BaseDAO<MovimentoVO> daoM;
    private ArrayList<MovimentoVO> lista;
    private String msg, title, a, b;

    public ControllerCaixa(CaixaView caixaView) {
        this.caixaView = caixaView;
        daoM = new MovimentoDAO();
        lista = new ArrayList<>();
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

    /**
     * Mostra uma Mensagem de Sucesso ou de Erro caso ação realizada
     * Adição ou Remoção
     */
    public void showInputDialog() {
        boolean r;
        title = "Adicionar Valor";
        msg = "Escolha o Tipo de Entrada:";

        if (Constantes.FLAG == 1) {
            Object[] opcoes = {Constantes.PGTO_DINHEIRO, Constantes.PGTO_CARTAO};
            a = (String) JOptionPane.showInputDialog(caixaView, msg, title,
                    JOptionPane.QUESTION_MESSAGE, null, opcoes, "");

            msg = "Digite o Valor:";
            b = JOptionPane.showInputDialog(caixaView, msg, title, JOptionPane.QUESTION_MESSAGE);
        } else {
            msg = "Digite o Valor:";
            b = JOptionPane.showInputDialog(caixaView, msg, title, JOptionPane.QUESTION_MESSAGE);
        }


        try {
            Double c = Double.valueOf(b);

            // Se add com sucesso, mostra uma Mensagem que adc com sucesso, se não, msg com erro
            if (Constantes.FLAG == 1) {
                r = this.addValor(a, c);
                this.msgAdicionar(r);
            }

            // Se remover com sucesso, mostra uma Mensagem que removeu com sucesso, se não, msg com Erro
            if (Constantes.FLAG == 0) {
                r = this.removerValor(c);
                this.msgRemover(r);
            }

            this.totalizarCaixa();

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Adiciona um valor digitado, em um Label Constante na tela CaixaView
     *
     * @param tipo   String
     * @param values Array Double
     * @return true/false
     */
    private boolean addValor(String tipo, Double... values) {
        boolean bool = false;
        if (tipo.equals(Constantes.PGTO_DINHEIRO)) {
            for (Double a : values) {
                if (CaixaBO.validarValorDigitado(a)) {
                    Constantes.LBL_VALOR_CAIXA_DINHEIRO += a;
                    bool = true;
                } else {
                    bool = false;
                }
            }
            caixaView.getLblSaldoEmDinheiror().setText(Constantes.LBL_TEXT_CAIXA_DINHEIRO + "" + Constantes.LBL_VALOR_CAIXA_DINHEIRO);
        }
        if (tipo.equals(Constantes.PGTO_CARTAO)) {
            for (Double a : values) {
                if (CaixaBO.validarValorDigitado(a)) {
                    Constantes.LBL_VALOR_CAIXA_CARTAO += a;
                    bool = true;
                } else {
                    bool = false;
                }
            }
            caixaView.getLblSaldoEmCarto().setText(Constantes.LBL_TEXT_CAIXA_CARTAO + "" + Constantes.LBL_VALOR_CAIXA_CARTAO);
        }
        return bool;
    }

    /**
     * Remove um valor digitado, em um label Constante na tela CaixaView
     *
     * @param values Array Double
     * @return true/false
     */
    private boolean removerValor(Double... values) {
        //TODO Realizar ação para abrir jopotin pane, digitar valor, salvar, e usar o Label para atualizar esse valor
        boolean bool = false;
        for (Double a : values) {
            if (CaixaBO.validarValorDigitado(a)) {
                Constantes.LBL_VALOR_CAIXA_TOTAL -= a;
                caixaView.getLblTotalCaixa().setText(String.valueOf(Constantes.LBL_VALOR_CAIXA_TOTAL));
                bool = true;
            }
        }
        return bool;
    }

    /**
     * Mensagem para add valor
     *
     * @param r boolean
     */
    private void msgAdicionar(boolean r) {
        this.title = "Adicionar Valor";
        if (r) {
            this.msg = "Valor Adicionado!";
        } else {
            this.msg = "Erro ao Adicionar o Valor, Por favor, Digite Corretamente!";
        }
        JOptionPane.showMessageDialog(caixaView,
                caixaView.getModificacao().labelConfig(caixaView.getLblModificacao(), this.msg),
                this.title, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Mensagem para remover valor
     *
     * @param r boolean
     */
    private void msgRemover(boolean r) {
        title = "Remover Valor";
        if (r) {
            msg = "Remoção Realizada!";
        } else {
            msg = "Erro ao Remover um Valor menor que o total!";
        }
        JOptionPane.showMessageDialog(caixaView,
                caixaView.getModificacao().labelConfig(caixaView.getLblModificacao(), this.msg),
                this.title, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Calcula os valores e totaliza o Caixa em um Label;
     */
    private void totalizarCaixa() {
        double c = 0.0, d = 0.0;
        try {
            if (Constantes.FLAG == 1) {
                if (a.equals(Constantes.PGTO_DINHEIRO)) {
                    c = Double.parseDouble(b);
                }
                if (a.equals(Constantes.PGTO_CARTAO)) {
                    d = Double.parseDouble(b);
                }
                Constantes.LBL_VALOR_CAIXA_TOTAL += c;
                Constantes.LBL_VALOR_CAIXA_TOTAL += d;
            }
            if (Constantes.FLAG == 0) {
                if (a.equals(Constantes.PGTO_DINHEIRO)) {
                    c = Double.parseDouble(b);
                }
                if (a.equals(Constantes.PGTO_CARTAO)) {
                    d = Double.parseDouble(b);
                }
                if (Constantes.LBL_VALOR_CAIXA_TOTAL == 0.0) {
                    JOptionPane.showMessageDialog(caixaView, "Total do Caixa já consta em R$ 0.0 !\n Essa ação não ira ser realizada.", "Erro",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    Constantes.LBL_VALOR_CAIXA_TOTAL -= c;
                    Constantes.LBL_VALOR_CAIXA_TOTAL -= d;
                }
            }
            caixaView.getLblTotalCaixa().setText(Constantes.LBL_TEXT_CAIXA_TOTAL + "" + Constantes.LBL_VALOR_CAIXA_TOTAL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fecharCaixa() {
        //TODO Realizar ação para fechar o caixa de acordo com o DIA, os dados utilizados na tabela
        //TODO e realizar i limpeza dos dados do caixa na tabela.
        int i = 0;
        ArrayList<String> nomes = new ArrayList<>();
        ArrayList<Double> valores = new ArrayList<>();
        GeradorRelatorioCaixa relatorio = new GeradorRelatorioCaixa();

        for (MovimentoVO movimentoVO : lista) {
            nomes.add(movimentoVO.getPlano().getCliente().getNome());
            valores.add(movimentoVO.getTicket().getValor());
            i++;
            relatorio.pegarResultados(nomes, valores, i);
        }
    }

    public void imprimirComprovante() {
        //TODO Por linha selecionada
    }

    public void imprimirRelatorio() {
        //TODO Imprimir do ultimo caixa, ou se o caixa atual já fechou
    }

    public void verificarDia() {

        LocalDate data = LocalDate.now();
        LocalTime time = LocalTime.now();
        LocalDateTime dateTime = LocalDateTime.now();
        int flagTempo = 0;


        LocalTime timeInicio = LocalTime.of(8, 0);
        LocalTime timeMeio = LocalTime.of(12, 0);
        LocalTime timePosMeio = LocalTime.of(13, 0);
        LocalTime timeFinal = LocalTime.of(22, 0);

        Duration d1 = Duration.between(timeInicio, timeMeio);
        Duration d2 = Duration.between(timePosMeio, timeFinal);
        Duration d3 = Duration.between(timeInicio, timeFinal);


        if (time.getHour() >= timeInicio.getHour()) {
            flagTempo = 1;
        }
        if (time.getHour() <= timeMeio.getHour()) {
            flagTempo = 2;
        }
        if (time.getHour() >= timePosMeio.getHour()) {
            flagTempo = 3;
        }
        if (time.getHour() <= timeFinal.getHour()) {
            flagTempo = 4;
        }

        encerrarDia(flagTempo);
    }

    private void encerrarDia(int flag) {
        this.verificarDia();
        //TODO Calcular o dia referente ao sistema, salvar esses horarios em uma variavel
        //TODO remover os valores da tabela e da lista
        //TODO Impedir que o sistema consulte os valores quando o caixa for fechado


    }
}
