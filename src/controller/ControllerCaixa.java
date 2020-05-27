package controller;

import model.banco.BaseDAO;
import model.bo.CaixaBO;
import model.dao.movientos.MovimentoDAO;
import model.vo.movimentos.MovimentoVO;
import util.Constantes;
import util.Util;
import util.relatorio.GeradorRelatorioCaixa;
import view.panels.CaixaView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class ControllerCaixa {
    private final CaixaView caixaView;
    private final BaseDAO<MovimentoVO> daoM;
    private ArrayList<MovimentoVO> lista;
    private String msg, title, jopValueComboBox, jopValueSaldo;

    public ControllerCaixa(CaixaView caixaView) {
        this.caixaView = caixaView;
        daoM = new MovimentoDAO();
        lista = new ArrayList<>();
        this.timerRefreshData();
    }

    public void atualizarTabela() {
        try {

            this.limparTabela();
            lista = daoM.consultarTodos();

            DefaultTableModel model = (DefaultTableModel) caixaView.getTable().getModel();
            Object[] novaColuna = new Object[6];
            int i = 0;
            for (MovimentoVO movimento : lista) {
                LocalDateTime now = LocalDateTime.now();

                if (movimento.isAtual()) {

                    // TICKET
                    if (movimento.getTicket() != null) {
                        boolean validado = movimento.getTicket().getValidado();
                        if (validado) {
                            LocalDateTime dtTicketEntrada = movimento.getTicket().getDataEntrada();
                            LocalDateTime dtTicketSaida = movimento.getTicket().getDataValidacao();

                            // Mantém a tabela atualizada com Ticket caso exista
                            this.atualizarTabelaTicket(movimento, dtTicketEntrada, dtTicketSaida, novaColuna);

                            System.out.println(movimento.toString());
                            System.out.println(movimento.getTicket().toString());
                            System.out.println();

                            model.addRow(novaColuna);
                        }
                    }

                    // CARTÃO
                    if (movimento.getPlano() != null) {
                        LocalDateTime dtContratoEntrada = movimento.getPlano().getContrato().getDtEntrada();
//                        if (Constantes.INTERNAL_MESSAGE != 9) {
//                            this.somarValoresContratoCliente(movimento, now.toLocalDate(), dtContratoEntrada.toLocalDate());
//                        }

                        // Mantém a tabela atualizada com Plano caso exista
                        this.atualizarTabelaPlano(movimento, dtContratoEntrada, now, novaColuna);

                        System.out.println(movimento.toString());
                        System.out.println(movimento.getPlano().toString());
                        System.out.println();
                        model.addRow(novaColuna);
                    }
                }
                System.out.println("Nova Linha:" + i++);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mantém a tabela atualizada com Ticket caso exista
     *
     * @param movimento       MovimentoVO
     * @param dtTicketEntrada LocalDateTime
     * @param dtTicketSaida   LocalDateTime
     * @param novaColuna      Object[]
     */
    private void atualizarTabelaTicket(MovimentoVO movimento, LocalDateTime dtTicketEntrada, LocalDateTime dtTicketSaida, Object[] novaColuna) {
        // Coluna 1 (Ticket/Cartao)
        novaColuna[0] = movimento.getTicket().getNumero();

        // Coluna 2 (Descricao)
        novaColuna[1] = "TICKET";

        // Coluna 3 (Hr Entrada)
        novaColuna[2] = dtTicketEntrada.format(Constantes.DTF);

        // Coluna 4 (Hr Validacao)
        if (!dtTicketEntrada.equals(dtTicketSaida)) {
            novaColuna[3] = dtTicketSaida.format(Constantes.DTF);
        } else {
            novaColuna[3] = "NÃO VALIDADO";
        }

        // Coluna 5 (Tipo Pagamento)
        novaColuna[4] = movimento.getTicket().getTipo();

        // Coluna 6 (Valores)
        if (movimento.getTicket().getValor() > 0.0) {
            novaColuna[5] = "R$: " + Util.formatarValor(movimento.getTicket().getValor());
        } else {
            novaColuna[5] = "R$:";
        }
    }

    /**
     * Mantém a tabela atualizada com Plano caso exista
     *
     * @param movimento         MovimentoVO
     * @param dtContratoEntrada LocalDateTime
     * @param now               LocalDateTime
     * @param novaLinha         Object[]
     */
    private void atualizarTabelaPlano(MovimentoVO movimento, LocalDateTime dtContratoEntrada, LocalDateTime now, Object[] novaLinha) {

        // Coluna 1 (Ticket/Cartao)
        if (movimento.getPlano().getContrato().getNumeroCartao() > 0) {
            novaLinha[0] = movimento.getPlano().getContrato().getNumeroCartao();
        }

        // Coluna 2 (Descricao)
        novaLinha[1] = movimento.getPlano().getTipo();

        // Coluna 3 (Hr Entrada)
        if (now.toLocalDate().equals(dtContratoEntrada.toLocalDate())) {
            novaLinha[2] = movimento.getHr_entrada().format(Constantes.DTF);
        } else {
            novaLinha[2] = "";
        }

        // Coluna 4 (Hr Validade)
        novaLinha[3] = movimento.getPlano().getContrato().getDtSaida().format(Constantes.DTF);

        // Coluna 5 (Pagamento)
        novaLinha[4] = movimento.getPlano().getContrato().getTipoPgto();

        // Coluna 6 (Valores);
        novaLinha[5] = "R$: " + Util.formatarValor(movimento.getPlano().getContrato().getValor());

        System.out.println(movimento.toString());
        System.out.println(movimento.getPlano().toString());
        System.out.println();
    }

    private void somarValoresContratoCliente(MovimentoVO movimento, LocalDate now, LocalDate dtContratoEntrada) {
        String tipo = movimento.getPlano().getContrato().getTipoPgto();
        for (int i = 0; i < lista.size(); i++) {
            if (movimento.getPlano() != null) {
                if (tipo.equals("DINHEIRO") && now.equals(dtContratoEntrada)) {
                    Constantes.LBL_VALOR_CAIXA_DINHEIRO += movimento.getPlano().getContrato().getValor();
                    Constantes.LBL_VALOR_CAIXA_TOTAL += movimento.getPlano().getContrato().getValor();
                } else if (movimento.getPlano().getContrato().getTipoPgto().equals("CARTÃO") && now.equals(dtContratoEntrada)) {
                    Constantes.LBL_VALOR_CAIXA_CARTAO += movimento.getPlano().getContrato().getValor();
                    Constantes.LBL_VALOR_CAIXA_TOTAL += movimento.getPlano().getContrato().getValor();
                }
            }
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
            jopValueComboBox = (String) JOptionPane.showInputDialog(caixaView, msg, title,
                    JOptionPane.QUESTION_MESSAGE, null, opcoes, "");
        }
        msg = "Digite o Valor:";
        jopValueSaldo = JOptionPane.showInputDialog(caixaView, msg, title, JOptionPane.QUESTION_MESSAGE);

        try {
            Double c = Double.valueOf(jopValueSaldo);

            // Se add com sucesso, mostra uma Mensagem que adc com sucesso, se não, msg com erro
            if (Constantes.FLAG == 1) {
                r = this.addValor(jopValueComboBox, c);
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
            caixaView.getLblSaldoEmDinheiror().setText(Constantes.LBL_TEXT_CAIXA_DINHEIRO + " " + Constantes.LBL_VALOR_CAIXA_DINHEIRO);
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
            caixaView.getLblSaldoEmCarto().setText(Constantes.LBL_TEXT_CAIXA_CARTAO + " " + Constantes.LBL_VALOR_CAIXA_CARTAO);
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
        for (Double a : values) {
            if (CaixaBO.validarValorDigitado(a)) {
                if (Constantes.LBL_VALOR_CAIXA_TOTAL > 0.0) {
                    Constantes.LBL_VALOR_CAIXA_TOTAL -= a;
//                    this.controlarValorLabel();
                    return true;
                }
            }
        }
        return false;
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
            msg = "Erro ao Remover Valores<br>Menores que o Total!";
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
                if (jopValueComboBox.equals(Constantes.PGTO_DINHEIRO)) {
                    c = Double.parseDouble(jopValueSaldo);
                }
                if (jopValueComboBox.equals(Constantes.PGTO_CARTAO)) {
                    d = Double.parseDouble(jopValueSaldo);
                }
                Constantes.LBL_VALOR_CAIXA_TOTAL += c;
                Constantes.LBL_VALOR_CAIXA_TOTAL += d;
            }
            if (Constantes.FLAG == 0) {
                if (jopValueComboBox.equals(Constantes.PGTO_DINHEIRO)) {
                    c = Double.parseDouble(jopValueSaldo);
                }
                if (jopValueComboBox.equals(Constantes.PGTO_CARTAO)) {
                    d = Double.parseDouble(jopValueSaldo);
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
            i++;
            nomes.add(movimentoVO.getPlano().getCliente().getNome());
            valores.add(movimentoVO.getTicket().getValor());
            relatorio.pegarResultados(nomes, valores, i);
        }

        this.verificarDia();
    }

    /**
     * Pega os dados do Dia atual
     */
    private void verificarDia() {

        LocalDate localDate = LocalDate.now();
        LocalTime startTime = LocalTime.of(8, 0, 0);
        LocalTime endTime = LocalTime.of(20, 0, 0);
        LocalDateTime startOfDay = LocalDateTime.of(localDate, startTime);
        LocalDateTime endOfDay = LocalDateTime.of(localDate, endTime);
        LocalDateTime now = LocalDateTime.now();

        LocalTime timeInicio = LocalTime.of(8, 0);
        LocalTime timeMeio = LocalTime.of(12, 0);
        LocalTime timePosMeio = LocalTime.of(13, 0);
        LocalTime timeFinal = LocalTime.of(22, 0);

        Duration d1 = Duration.between(timeInicio, timeMeio);
        Duration d2 = Duration.between(timePosMeio, timeFinal);
        Duration d3 = Duration.between(timeInicio, timeFinal);


        this.encerrarDia(startOfDay, endOfDay, now);
    }

    private void encerrarDia(LocalDateTime startOfDay, LocalDateTime endOfDay, LocalDateTime now) {
        //TODO Calcular o dia referente ao sistema, salvar esses horarios em uma variavel
        //TODO remover os valores da tabela e da lista
        //TODO Impedir que o sistema consulte os valores quando o caixa for fechado

        if (startOfDay.compareTo(now) < 0 && endOfDay.compareTo(now) > 0) {
            title = "ATENÇÂO!";
            msg = "<html><body>Deseja Realmente Fechar o Caixa?<br>- Após fechar o Caixa, os valores Anteriores<br> não Aparecerão</body></html>";
            int i = JOptionPane.showConfirmDialog(caixaView, caixaView.getModificacao().labelConfig(caixaView.getLblModificacao(), msg), title,
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (i == JOptionPane.YES_OPTION) {
                if (this.atualizarStatusMovimento(lista)) {
                    this.atualizarTabela();
                }
            }
        }
    }

    /**
     * Método para Fechar o Caixa atual;
     *
     * @param lista ArrayList
     * @return true/false
     */
    private boolean atualizarStatusMovimento(ArrayList<MovimentoVO> lista) {
        int quantidade = 0;
        BaseDAO<MovimentoVO> daoM = new MovimentoDAO();
        for (MovimentoVO movimento : lista) {
            movimento.setAtual(false);
            if (daoM.alterar(movimento)) {
                quantidade++;
            }
        }
        System.out.println("Quantidade de Movimentos Alterados(Atualizar Status): " + quantidade);
        msg = "Registros Alterados: " + quantidade;
        JOptionPane.showMessageDialog(caixaView, caixaView.getModificacao().labelConfig(caixaView.getLblModificacao(), msg),
                title, JOptionPane.INFORMATION_MESSAGE);
        return true;
    }

    /**
     * Timer que mantém a tabela e os Labels atualizados a cada 1 minuto
     */
    private synchronized void timerRefreshData() {
        ActionListener event = e -> {
            String concatD = Constantes.LBL_TEXT_CAIXA_DINHEIRO + " " +
                             Util.formatarValor(Constantes.LBL_VALOR_CAIXA_DINHEIRO);
            String concatC = Constantes.LBL_TEXT_CAIXA_CARTAO + " " +
                             Util.formatarValor(Constantes.LBL_VALOR_CAIXA_CARTAO);
            String concatT = Constantes.LBL_TEXT_CAIXA_TOTAL + " " +
                             Util.formatarValor(Constantes.LBL_VALOR_CAIXA_TOTAL);

            caixaView.getLblSaldoEmDinheiror().setText(concatD);
            caixaView.getLblSaldoEmCarto().setText(concatC);
            caixaView.getLblTotalCaixa().setText(concatT);

            this.atualizarTabela();
        };
        Timer timer = new Timer(15000, event);
        timer.start();
    }

    public void imprimirComprovante() {
        //TODO Por linha selecionada
    }

}
