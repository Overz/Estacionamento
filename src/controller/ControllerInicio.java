package controller;

import model.banco.BaseDAO;
import model.bo.InicioBO;
import model.dao.movientos.MovimentoDAO;
import model.dao.movientos.TicketDAO;
import model.vo.movimentos.MovimentoVO;
import model.vo.movimentos.TicketVO;
import util.Constantes;
import view.panels.InicioView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ControllerInicio {
    private final InicioView inicioView;
    private final BaseDAO<MovimentoVO> daoM;
    private final BaseDAO<TicketVO> daoT;
    private ArrayList<MovimentoVO> lista;
    private MovimentoVO m;
    private TicketVO t;
    private String msg = "";
    private String title = "";
    private long minutes;

    public ControllerInicio(InicioView inicioView) {
        this.inicioView = inicioView;
        daoM = new MovimentoDAO();
        daoT = new TicketDAO();
        m = new MovimentoVO();
        t = new TicketVO();
        lista = new ArrayList<>();
    }

    /**
     * Atualiza a JTable com Todos os Valores
     */
    public void atualizarTabela() {
        // Limpa a tabela
        this.limparTabela();

        lista = daoM.consultarTodos();
        DefaultTableModel model = (DefaultTableModel) inicioView.getTable().getModel();
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
        int total = lista.size();
        this.addTotalVeiculos(total);
    }

    private void addTotalVeiculos(int total) {
        inicioView.getLblTotalDeVeiculos().setText("Total de Veiculos:" + total);
    }

    /**
     * Limpa a tela para revalidar os valores
     */
    private void limparTabela() {
        inicioView.getTable().setModel(new DefaultTableModel(new Object[][]{}, Constantes.COLUNAS_INICIO));
    }

    /**
     * Remover as linhas selecionadas da tablea;
     */
    public void removeSelectedRow() {

        int row = inicioView.getTable().getSelectedRow();
        MovimentoVO m = lista.get(row);
        inicioView.getTable().remove(row);

        title = "Exclusão";
        if (daoM.excluirPorID(m.getId())) {
            msg = "EXCLUSÃO REALIZADA COM SUCESSO!";
            JOptionPane.showMessageDialog(inicioView, inicioView.getModificacao().labelConfig(inicioView.getLblModificadoParaExibicao(), msg), title,
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            msg = "ERRO AO REALIZAR EXCLUSÃO!";
            JOptionPane.showMessageDialog(inicioView, inicioView.getModificacao().labelConfig(inicioView.getLblModificadoParaExibicao(), msg), title,
                    JOptionPane.ERROR_MESSAGE);
        }
        System.out.println(m.toString());
    }

    /**
     * Criação de uma mascara para o campo, e um place holder(Palavras que somem ao
     * digitar)
     */
    public void maskAndPlaceHolder() {
        try {
            inicioView.getMf1().setMask("####################");
            inicioView.getMf2().setMask("********************");
        } catch (ParseException e) {
            System.out.println("Message:" + e.getMessage());
            System.out.println("Cause:" + e.getCause());
            System.out.println("ErrorOffSet:" + e.getErrorOffset());
            System.out.println("LocalizedMessage:" + e.getLocalizedMessage());
        }
    }

    /**
     * Timer que mantém a tabela atualizada a cada 1 minuto
     */
    public void timerRefreshData() {
        ActionListener event = actionEvent -> atualizarTabela();
        Timer timer = new Timer(60000, event);
        timer.start();
    }

    /**
     * Valida o ticket por algum tempo, e atualiza o Status no banco
     *
     * @param tipoPgto String
     * @param ticket   String
     */
    public void validate(String tipoPgto, String ticket) {
        if (InicioBO.validarNumberoTicket(ticket)) {
            if (calcular(tipoPgto, ticket)) {
                if (validarTicket(ticket)) {
                    msg = "Ticket Valido Por: " + minutes + " minuto's!";
                } else {
                    msg = "Erro ao Validar o Ticket\n";
                }
                JOptionPane.showMessageDialog(inicioView, inicioView.getModificacao().labelConfig(inicioView.getLblModificadoParaExibicao(), msg),
                        title, JOptionPane.INFORMATION_MESSAGE);
            } else if (Constantes.INTERNAL_MESSAGE == 0) {
                title = "Erro";
                msg = "<html><body>Ação Cancelada!<br>O Ticket não foi validado!<br>Motivo: Já Validado!</body></html>";
                JOptionPane.showMessageDialog(inicioView, inicioView.getModificacao().labelConfig(inicioView.getLblModificadoParaExibicao(), msg),
                        title, JOptionPane.WARNING_MESSAGE);
            }
        } else {
            msg = "Por Favor, Digite o Ticket Corretamente!";
            JOptionPane.showMessageDialog(inicioView, inicioView.getModificacao().labelConfig(inicioView.getLblModificadoParaExibicao(), msg), title, JOptionPane.WARNING_MESSAGE);
        }
        this.gambiarra();
    }

    /**
     * Método para Calcula o valor TOTAL(Desde o Inicio, até o Tempo Atual(Agora))
     * do Valor do Ticket. Utilizando de Valores Iniciais, e Valores de Diferença (Fim - Inicio).
     * <p>
     * Se o TICKET estiver com o Status Flase(Não Validado), o Método permite a Possibilidade
     * de Perguntar se deseja Validar o Ticket quando o evento acontece.
     * <p>
     * Formata e Acumula o valor em uma variavel Constante para a tela CaixaView.
     *
     * @param tipoPgto String
     * @param ticket   String
     * @return true/false
     */
    private boolean calcular(String tipoPgto, String ticket) {
        for (MovimentoVO movimentoVO : lista) {
            String numero = String.valueOf(movimentoVO.getTicket().getNumero());
            if (ticket.equals(numero)) {
                int id = movimentoVO.getTicket().getId();
                t = daoT.consultarPorId(id);
                break;
            }
        }
        if (t != null && t.getStatus().equals(false)) {
            m = daoM.consultarPorId(t.getId());

            Date date1 = new Date();
            Date date2 = new Date();

            try {
                LocalDateTime ldtEntrada = m.getHr_entrada();
                LocalDateTime now = LocalDateTime.now();
                date1 = Date.from(ldtEntrada.atZone(ZoneId.systemDefault()).toInstant());
                date2 = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Diferença entre Milisegundos de Agora com a Entrada
            long diff = date2.getTime() - date1.getTime(); // Variavel base para o calculo das demais abaixo

            // Os Calculos Abaixo foram feitos de DUAS MANEIRAS, TimeUnit, e Representações a 'mão'
            // Ambos estão iguais
            long days =                                                 TimeUnit.MILLISECONDS.toDays(diff); // Diferença de Dias ate Agora
            long remainingHoursInMillis = diff -                        TimeUnit.DAYS.toMillis(days); // Milisegundos Restantes da Diferença de Dias
            long hours =                                                TimeUnit.MILLISECONDS.toHours(remainingHoursInMillis); // Horas Restantes da Diferença de Dias
            long remainingMinutesInMillis = remainingHoursInMillis -    TimeUnit.HOURS.toMillis(hours); // Milsegundos Restantes da Diferença de Horas
            long minutes =                                              TimeUnit.MILLISECONDS.toMinutes(remainingMinutesInMillis); // Minutos Restantes da Diferença de Horas
            long remainingSecondsInMillis = remainingMinutesInMillis -  TimeUnit.MINUTES.toMillis(minutes); // Milisegundos Restantes da Diferença de Minutos
            long seconds =                                              TimeUnit.MILLISECONDS.toSeconds(remainingSecondsInMillis); // Segundos Restantes da Diferença de Minutos

            // Diferença entre Segundos Totais e Atuais (Atual - Total)
            // Calculos feitos a mão
            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            System.out.println("Dados Formatados:");
            System.out.println("Days: " + days + ", hours: " + hours + ", minutes: " + minutes + ", seconds: " + seconds);
            System.out.println("Dados de Feitos: ");
            System.out.println("Days: " + diffDays + ", hours: " + diffHours + ", minutes: " + diffMinutes + ", seconds: " + diffSeconds);

            // Calculo Final
            double valorDia = 10.0; // Valor da Hora
            double valorMinuto = valorDia / 60.0; // Valor do Minuto
            double minPorDia = 24 * 60; // Minutos totais de 24h(1 Dia)
            double minRestantes = (diffHours * 60.0) + diffMinutes; // Minutos Restantes do Dia atual (Hora + Minuto)
            double total = ((diffDays * minPorDia) + minRestantes); // Diferença de Dias entre o Inicio ate Agora * Minutos Totais de um Dia + Minutos Restantes do Dia Atual
            double valor = total * valorMinuto; // Total dos valores Iniciais ate Agora

            if (tipoPgto.equals(Constantes.JOP_CARTAO)) {
                Constantes.LBL_VALOR_CAIXA_DINHEIRO += valor;
            } else if (tipoPgto.equals(Constantes.JOP_DINHEIRO)) {
                Constantes.LBL_VALOR_CAIXA_CARTAO += valor;
            }

            Locale locale = Locale.getDefault(Locale.Category.FORMAT);
            NumberFormat formatter = NumberFormat.getInstance(locale);
            formatter.setMinimumFractionDigits(2);
            formatter.setMaximumFractionDigits(2);
            String format = formatter.format(valor);
            System.out.println(format);

            title = "Validação";
            msg = "<html><body>Total(R$): " + format + "<br>Deseja Validar este Ticket?</body></html>";
            int i = JOptionPane.showConfirmDialog(inicioView, inicioView.getModificacao().labelConfig(inicioView.getLblModificadoParaExibicao(), msg),
                    title, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
            return i == JOptionPane.YES_OPTION;
        } else {
            Constantes.INTERNAL_MESSAGE = 0;
            return false;
        }
    }

    /**
     * Valida o Ticket com Status True no banco, com Timer para X tempo,
     * Após esse tempo o status atualiza para false;
     *
     * @param ticket String
     * @return true/false
     */
    public boolean validarTicket(String ticket) {
        int id = 0;
        for (MovimentoVO movimentoVO : lista) {
            String numero = String.valueOf(movimentoVO.getTicket().getNumero());
            if (ticket.equals(numero)) {
                id = movimentoVO.getTicket().getId();
                break;
            }
        }

        try {
            t = daoT.consultarPorId(id);
            if (t != null && t.getStatus().equals(false)) {
                m = daoM.consultarPorId(t.getId());

                for (MovimentoVO movimentoVO : lista) {
                    if (m.getId() == movimentoVO.getId()) {
                        Constantes.FLAG = 1;
                        t.setStatus(true);
                        boolean a = daoT.alterar(t);
                        if (a) {
                            this.timerTicket();
                            return true;
                        }
                        break;
                    }
                }
            } else {
                msg += "<html><body>Erro no Calculo de Validação!</body></html>";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Cria o Timer para atualizar o Status do ticket
     */
    public synchronized void timerTicket() {
        ActionListener event = e -> {
            // validar o tipoPgto por X minutos
            Constantes.FLAG = 1;
            t.setStatus(false);
            daoT.alterar(t);
        };
        Timer timer = new Timer(60000, event);
        timer.setRepeats(false);
        timer.start();
        minutes = TimeUnit.MILLISECONDS.toMinutes(timer.getDelay());
    }

    /**
     * Controla os campos da Cancela na tela, atualizando a cor ao clicar
     * e com um timer para retornar ao status original
     * simulando a abertura da cancela
     *
     * @param button JButton
     * @param label  JLabel
     */
    public void controlarCancela(JButton button, JLabel label) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                label.setText("Abrindo Cancela");
                label.setBackground(Color.decode("#35D073"));
                button.setBackground(Color.decode("#35D073"));
                ActionListener event = actionEvent -> {
                    label.setText("Cancela Fechada");
                    label.setBackground(Color.decode("#F85C50"));
                    button.setBackground(Color.WHITE);
                };
                Timer timer = new Timer(10000, event);
                timer.start();
            }
        });
    }

    /**
     * Atualiza o foco do campo TxtTicket na tela após a validação
     */
    private void gambiarra() {
        inicioView.getTxtTicket().addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                inicioView.getTxtTicket().setText("Nº Ticket");
                inicioView.setForeground(Color.BLACK);
            }
        });
    }

    public void gerarTicket() {
        //TODO Gerar um numero aleatorio, salvar no banco, e consultar na tela
        //TODO ticket gerado com valor agregado
        // verificar se ele já existe, e se tem necessidade de existir somente uma vez

    }

    public void gerarComprovantePorLinha() {
        //TODO Usar a Classe GerarRelatorio para gerar um comprovante
    }
}
