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

public class ControllerInicio {
    private final InicioView inicioViewview;
    private final BaseDAO<MovimentoVO> daoM;
    private final BaseDAO<TicketVO> daoT;
    private ArrayList<MovimentoVO> lista;
    private MovimentoVO m;
    private TicketVO t;
    private String msg;

    public ControllerInicio(InicioView inicioView) {
        this.inicioViewview = inicioView;
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
        } else {
            msg = "ERRO AO REALIZAR EXCLUSÃO!";
            JOptionPane.showMessageDialog(inicioViewview, inicioViewview.getModificacao().labelConfig(inicioViewview.getLblModificadoParaExibicao(), msg), "EXCLUSÂO",
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
            inicioViewview.getMf1().setMask("####################");
            inicioViewview.getMf2().setMask("********************");
        } catch (ParseException e) {
            System.out.println("Message:" + e.getMessage());
            System.out.println("Cause:" + e.getCause());
            System.out.println("ErrorOffSet:" + e.getErrorOffset());
            System.out.println("LocalizedMessage:" + e.getLocalizedMessage());
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

    public void validate(String tipoPgto, String ticket) {
        if (InicioBO.validarNumberoTicket(ticket)) {
            if (validarTicket(ticket)) {
                msg = "Ticket Validado!";
            } else {
                msg = "Erro ao Validar o Ticket\n";
                msg += "Erro no Calculo de Validação!";
            }
        }

        this.gambiarra();
        JOptionPane.showMessageDialog(inicioViewview, msg, "Validação", JOptionPane.INFORMATION_MESSAGE);
    }

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
            Constantes.FLAG = 1;

            t = daoT.consultarPorId(id);
            if (t != null) {
                m = daoM.consultarPorId(t.getId());
            } else {
                throw new Exception("Erro ao Preencher o Ticket em Movimento");
            }

            for (MovimentoVO movimentoVO : lista) {
//                id = movimentoVO.getId();
                if (m.getId() == movimentoVO.getId()) {
                    JOptionPane.showMessageDialog(inicioViewview, "Ticket Valido Por 10 Minutos!",
                            "Validação", JOptionPane.INFORMATION_MESSAGE);
                    break;
                }
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public synchronized void timerTicket() {
        ActionListener event = e -> {
            // validar o tipoPgto por 10minutos
            Constantes.FLAG = 1;
            t.setStatus(false);
            daoT.alterar(t);
        };
        Timer timer = new Timer(60000, event);
        timer.setRepeats(false);
    }

    public void gerarTicket() {
        //TODO Gerar um numero aleatorio, salvar no banco, e consultar na tela
        //TODO ticket gerado com valor agregado
        // verificar se ele já existe, e se tem necessidade de existir somente uma vez

    }

    public void gerarComprovantePorLinha() {
        //TODO Usar a Classe GerarRelatorio para gerar um comprovante
    }

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

    private void gambiarra() {
        inicioViewview.getTxtTicket().addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                inicioViewview.getTxtTicket().setText("Nº Ticket");
                inicioViewview.setForeground(Color.BLACK);
            }
        });
    }
}
