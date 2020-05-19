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
import java.text.ParseException;
import java.util.ArrayList;
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

    public void timerRefreshData() {
        ActionListener event = actionEvent -> atualizarTabela();
        Timer timer = new Timer(60000, event);
        timer.start();
    }

    public void validate(String tipoPgto, String ticket) {
        if (calcular(tipoPgto)) {
            if (InicioBO.validarNumberoTicket(ticket)) {
                if (validarTicket(ticket)) {
                    msg = "Ticket Valido Por: " + minutes + " minuto's!";
                } else {
                    msg = "Erro ao Validar o Ticket\n";
                }
            }
            this.gambiarra();
            int i = JOptionPane.showConfirmDialog(inicioView, inicioView.getModificacao().labelConfig(inicioView.getLblModificadoParaExibicao(), msg),
                    title, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        } else {
            msg += "Ação Cancelada\nO Ticket não foi validado!";
            JOptionPane.showMessageDialog(inicioView, inicioView.getModificacao().labelConfig(inicioView.getLblModificadoParaExibicao(), msg),
                    title, JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private boolean calcular(String tipoPgto) {
        //TODO Realizar o Calculo do valor do ticket baseado em horas


        title = "Validação";
        double total = m.getTicket().getValor();
        msg = "Total: " + total + "\nDeseja Validar este Ticket?";
        int i = JOptionPane.showConfirmDialog(inicioView, inicioView.getModificacao().labelConfig(inicioView.getLblModificadoParaExibicao(), msg),
                title, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        return i == JOptionPane.YES_OPTION;
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
                        this.timerTicket();
                        if (a) {
                            return true;
                        }
                        break;
                    }
                }
            } else {
                msg += "Erro no Calculo de Validação!\n";
                msg += "Ticket já Validado!\n";
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
