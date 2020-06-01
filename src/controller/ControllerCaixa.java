package controller;

import model.banco.BaseDAO;
import model.bo.CaixaBO;
import model.dao.movientos.MovimentoDAO;
import model.vo.movimentos.MovimentoVO;
import util.constantes.Colunas;
import util.constantes.ConstCaixa;
import util.constantes.ConstHelpers;
import util.constantes.ConstInicio;
import util.helpers.Modificacoes;
import util.helpers.Util;
import util.pdf.PdfComprovante;
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
    private boolean primeiro = true;

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

            int i = 0;
            Object[] novaColuna = new Object[6];
            LocalDateTime now = LocalDateTime.now();
            DefaultTableModel model = (DefaultTableModel) caixaView.getTable().getModel();
            for (MovimentoVO movimento : lista) {

                if (movimento.isAtual()) {

                    // TICKET
                    if (movimento.getTicket() != null) {
                        boolean validado = movimento.getTicket().getValidado();
                        if (validado) {
                            LocalDateTime dtTicketEntrada = movimento.getTicket().getDataEntrada();
                            LocalDateTime dtTicketSaida = movimento.getTicket().getDataValidacao();

                            // Mantém a tabela atualizada com Ticket caso exista
                            this.atualizarTabelaTicket(movimento, dtTicketEntrada, dtTicketSaida, novaColuna);

                            System.out.println("Linha:" + i);
                            System.out.println("Ticket:");
                            System.out.println(movimento.toString());
                            System.out.println(movimento.getTicket().toString());
                            System.out.println();

                            model.addRow(novaColuna);
                        }
                    }

                    // CARTÃO
                    if (movimento.getPlano() != null) {
                        LocalDateTime dtContratoEntrada = movimento.getPlano().getContrato().getDtEntrada();

                        // Mantém a tabela atualizada com Plano caso exista
                        this.atualizarTabelaPlano(movimento, dtContratoEntrada, now, novaColuna);

                        if (primeiro) {
                            primeiro = false;
                            this.somarValoresContratoCliente(now, dtContratoEntrada);
                        }

                        System.out.println("Linha:" + i);
                        System.out.println("Plano:");
                        System.out.println(movimento.toString());
                        System.out.println(movimento.getPlano().toString());
                        System.out.println();
                        model.addRow(novaColuna);
                    }
                    i++;
                }
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
        novaColuna[2] = dtTicketEntrada.format(ConstHelpers.DTF);

        // Coluna 4 (Hr Validacao)
        if (!dtTicketEntrada.equals(dtTicketSaida)) {
            novaColuna[3] = dtTicketSaida.format(ConstHelpers.DTF);
        } else {
            novaColuna[3] = "NÃO VALIDADO";
        }

        // Coluna 5 (Tipo Pagamento)
        String tipo = movimento.getTicket().getTipo();
        if (tipo != null) {
            novaColuna[4] = movimento.getTicket().getTipo();
        } else {
            novaColuna[4] = "NÃO VALIDADO";
        }

        // Coluna 6 (Valores)
        if (movimento.getTicket().getValor() > 0.0) {
            novaColuna[5] = "R$: " + Util.formatarValor(movimento.getTicket().getValor());
        } else {
            novaColuna[5] = "R$: NÃO VALIDADO";
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
            novaLinha[2] = movimento.getHr_entrada().format(ConstHelpers.DTF);
        } else {
            novaLinha[2] = "";
        }

        // Coluna 4 (Hr Validade)
        novaLinha[3] = movimento.getPlano().getContrato().getDtSaida().format(ConstHelpers.DTF);

        // Coluna 5 (Pagamento)
        novaLinha[4] = movimento.getPlano().getContrato().getTipoPgto();

        // Coluna 6 (Valores);
        novaLinha[5] = "R$: " + Util.formatarValor(movimento.getPlano().getContrato().getValor());
    }

    /**
     * Método para percorrer a lista uma vez e somar os valores dos planos já existentes,
     * para atribuir aos Labels da tela de Caixa.
     * <p>
     * Método inútil em uma aplicação real, pois o cadastro gerado de um cliente, automaticamente já gera um valor, e um movimento,
     * eventualmente, não tem a necessidade de percorrer esta lista para somar, pois o valor já vai ser atribuido a Constantes.X_Valor
     *
     * @param now               LocalDateTime
     * @param dtContratoEntrada LocalDateTime
     */
    public void somarValoresContratoCliente(LocalDateTime now, LocalDateTime dtContratoEntrada) {
        try {
            for (MovimentoVO m : lista) {
                if (m.getPlano() != null) {
                    String plano = m.getPlano().getContrato().getTipoPgto();
                    if (plano.equals("DINHEIRO") && now.toLocalDate().equals(dtContratoEntrada.toLocalDate())) {
                        ConstCaixa.LBL_VALOR_CAIXA_DINHEIRO += m.getPlano().getContrato().getValor();
                        ConstCaixa.LBL_VALOR_CAIXA_TOTAL += m.getPlano().getContrato().getValor();
                    } else if (plano.equals("CARTÃO")) {
                        ConstCaixa.LBL_VALOR_CAIXA_CARTAO += m.getPlano().getContrato().getValor();
                        ConstCaixa.LBL_VALOR_CAIXA_TOTAL += m.getPlano().getContrato().getValor();
                    }
                }

//                if (m.getTicket() != null) {
//                    String ticket = m.getTicket().getTipo();
//                    double valor = m.getTicket().getValor();
//
//                    if (ticket.equals("DINHEIRO")) {
//                        Constantes.LBL_VALOR_CAIXA_DINHEIRO += valor;
//                        Constantes.LBL_VALOR_CAIXA_TOTAL += valor;
//                    } else if (ticket.equals("CARTÃO")) {
//                        Constantes.LBL_VALOR_CAIXA_CARTAO += valor;
//                        Constantes.LBL_VALOR_CAIXA_TOTAL += valor;
//                    }
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void limparTabela() {
        caixaView.getTable().setModel(new DefaultTableModel(new Object[][]{}, Colunas.COLUNAS_CAIXA));
    }

    /**
     * Mostra uma Mensagem de Sucesso ou de Erro caso ação realizada
     * Adição ou Remoção
     */
    public void showInputDialog() {
        boolean r;
        title = "Adicionar Valor";
        msg = "Escolha o Tipo de Entrada:";

        if (ConstHelpers.FLAG == 1) {
            Object[] opcoes = {ConstInicio.PGTO_DINHEIRO, ConstInicio.PGTO_CARTAO};
            jopValueComboBox = (String) JOptionPane.showInputDialog(caixaView, msg, title,
                    JOptionPane.QUESTION_MESSAGE, null, opcoes, "");
        }
        msg = "Digite o Valor:";
        jopValueSaldo = JOptionPane.showInputDialog(caixaView, msg, title, JOptionPane.QUESTION_MESSAGE);

        try {
            Double c = Double.valueOf(jopValueSaldo);

            // Se add com sucesso, mostra uma Mensagem que adc com sucesso, se não, msg com erro
            if (ConstHelpers.FLAG == 1) {
                r = this.addValor(jopValueComboBox, c);
                this.msgAdicionar(r);
            }

            // Se remover com sucesso, mostra uma Mensagem que removeu com sucesso, se não, msg com Erro
            if (ConstHelpers.FLAG == 0) {
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
        if (tipo.equals(ConstInicio.PGTO_DINHEIRO)) {
            for (Double a : values) {
                if (CaixaBO.validarValorDigitado(a)) {
                    ConstCaixa.LBL_VALOR_CAIXA_DINHEIRO += a;
                    bool = true;
                } else {
                    bool = false;
                }
            }
            caixaView.getLblSaldoEmDinheiror().setText(ConstCaixa.LBL_TEXT_CAIXA_DINHEIRO + " " + ConstCaixa.LBL_VALOR_CAIXA_DINHEIRO);
        }
        if (tipo.equals(ConstInicio.PGTO_CARTAO)) {
            for (Double a : values) {
                if (CaixaBO.validarValorDigitado(a)) {
                    ConstCaixa.LBL_VALOR_CAIXA_CARTAO += a;
                    bool = true;
                } else {
                    bool = false;
                }
            }
            caixaView.getLblSaldoEmCarto().setText(ConstCaixa.LBL_TEXT_CAIXA_CARTAO + " " + ConstCaixa.LBL_VALOR_CAIXA_CARTAO);
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
                if (ConstCaixa.LBL_VALOR_CAIXA_TOTAL > 0.0) {
                    ConstCaixa.LBL_VALOR_CAIXA_TOTAL -= a;
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
                Modificacoes.labelConfig(caixaView.getLblModificacao(), this.msg),
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
                Modificacoes.labelConfig(caixaView.getLblModificacao(), this.msg),
                this.title, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Calcula os valores e totaliza o Caixa em um Label;
     */
    private void totalizarCaixa() {
        double c = 0.0, d = 0.0;
        try {
            if (ConstHelpers.FLAG == 1) {
                if (jopValueComboBox.equals(ConstInicio.PGTO_DINHEIRO)) {
                    c = Double.parseDouble(jopValueSaldo);
                }
                if (jopValueComboBox.equals(ConstInicio.PGTO_CARTAO)) {
                    d = Double.parseDouble(jopValueSaldo);
                }
                ConstCaixa.LBL_VALOR_CAIXA_TOTAL += c;
                ConstCaixa.LBL_VALOR_CAIXA_TOTAL += d;
            }
            if (ConstHelpers.FLAG == 0) {
                if (jopValueComboBox.equals(ConstInicio.PGTO_DINHEIRO)) {
                    c = Double.parseDouble(jopValueSaldo);
                }
                if (jopValueComboBox.equals(ConstInicio.PGTO_CARTAO)) {
                    d = Double.parseDouble(jopValueSaldo);
                }
                if (ConstCaixa.LBL_VALOR_CAIXA_TOTAL == 0.0) {
                    JOptionPane.showMessageDialog(caixaView, "Total do Caixa já consta em R$ 0.0 !\n Essa ação não ira ser realizada.", "Erro",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    ConstCaixa.LBL_VALOR_CAIXA_TOTAL -= c;
                    ConstCaixa.LBL_VALOR_CAIXA_TOTAL -= d;
                }
            }
            caixaView.getLblTotalCaixa().setText(ConstCaixa.LBL_TEXT_CAIXA_TOTAL + "" + ConstCaixa.LBL_VALOR_CAIXA_TOTAL);
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
        PdfComprovante relatorio = new PdfComprovante();

        for (MovimentoVO movimentoVO : lista) {
            i++;
            nomes.add(movimentoVO.getPlano().getCliente().getNome());
            valores.add(movimentoVO.getTicket().getValor());
//            relatorio.pegarResultados(nomes, valores, i);
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
            int i = JOptionPane.showConfirmDialog(caixaView, Modificacoes.labelConfig(caixaView.getLblModificacao(), msg), title,
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
        JOptionPane.showMessageDialog(caixaView, Modificacoes.labelConfig(caixaView.getLblModificacao(), msg),
                title, JOptionPane.INFORMATION_MESSAGE);
        return true;
    }

    /**
     * Timer que mantém a tabela e os Labels atualizados a cada 1 minuto
     */
    private void timerRefreshData() {
        ActionListener event = e -> {
            String concatD = ConstCaixa.LBL_TEXT_CAIXA_DINHEIRO + " " +
                             Util.formatarValor(ConstCaixa.LBL_VALOR_CAIXA_DINHEIRO);
            String concatC = ConstCaixa.LBL_TEXT_CAIXA_CARTAO + " " +
                             Util.formatarValor(ConstCaixa.LBL_VALOR_CAIXA_CARTAO);
            String concatT = ConstCaixa.LBL_TEXT_CAIXA_TOTAL + " " +
                             Util.formatarValor(ConstCaixa.LBL_VALOR_CAIXA_TOTAL);

            caixaView.getLblSaldoEmDinheiror().setText(concatD);
            caixaView.getLblSaldoEmCarto().setText(concatC);
            caixaView.getLblTotalCaixa().setText(concatT);

            this.atualizarTabela();
        };
        Timer timer = new Timer(30000, event);
        timer.start();
    }

    /**
     * Cria um JFileChooser e cria um PDF
     */
    public void gerarComprovantePorLinha() {

        title = "Criação do PDF";

        int row = caixaView.getTable().getSelectedRow();
        MovimentoVO movimentoVO = lista.get(row);

        if (movimentoVO.getPlano() == null) {
            if (movimentoVO.getTicket() != null) {
                if (movimentoVO.getTicket().getValidado()) {

                    JFileChooser jfc = new JFileChooser();
                    int i = Util.abrirJFileChooser(caixaView, jfc);

                    if (i == JFileChooser.APPROVE_OPTION) {
                        String caminhoEscolhido = Util.caminhoFileChooser(jfc.getSelectedFile());
                        PdfComprovante pdf = new PdfComprovante(caminhoEscolhido, movimentoVO);
                        msg = pdf.gerarPdf();
                        if (msg == null || msg.isEmpty() || msg.isBlank()) {
                            msg = "PDF Gerado!";
                        }
                    } else {
                        msg = "<html><body>Operação Cancelada</body></html>";
                    }
                } else {
                    msg = "<html><body>Por favor, Selecione um Ticket já validado!</body></html>";
                }
            } else {
                msg = "<html><body>Erro ao criar um Comprovante" +
                      "<br> com o Ticket Selecionado!</body></html>";
            }
        } else {
            msg = "Por favor, Selecione Somente TICKET!";
        }

        JOptionPane.showMessageDialog(caixaView, Modificacoes.labelConfig(caixaView.getLblModificacao(), msg),
                title, JOptionPane.INFORMATION_MESSAGE);
    }

}
