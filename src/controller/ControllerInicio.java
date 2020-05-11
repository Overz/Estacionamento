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
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;

public class ControllerInicio {
    private final InicioView inicioViewview;
    private final BaseDAO<MovimentoVO> daoM;
    private final BaseDAO<TicketVO> daoT;
    private ArrayList<MovimentoVO> lista;
    private final TicketVO t;
    private String msg;

    public ControllerInicio(InicioView inicioView) {
        this.inicioViewview = inicioView;
        daoM = new MovimentoDAO();
        daoT = new TicketDAO();
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
            inicioViewview.getMf1().setPlaceholder("Nº Ticket");
            inicioViewview.getMf2().setMask("********************");
            inicioViewview.getMf2().setPlaceholder("Pesquisar... (F6)");
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

    public void validate(String... values) {
        if (InicioBO.validarNumberoTicket(values)) {
            if (validarTicket(values)) {
                msg = "Ticket Validado!";
            } else {
                msg += "Erro no Calculo de Validação!\n";
            }
        } else {
            msg += "Erro ao Validar o Ticket";
        }
        JOptionPane.showMessageDialog(inicioViewview, msg, "Validação", JOptionPane.INFORMATION_MESSAGE);
    }

    public boolean validarTicket(String... values) {
        String ticket = null;
        String idString = null;
        int id = 0;
        for (MovimentoVO movimento : lista) {
            ticket = values[values.length - 1];
            if (ticket.equals(movimento.getTicket().getNumero())) {
                id = movimento.getTicket().getId();
            }
        }

        try {
            idString = String.valueOf(id);

            Constantes.FLAG = 1;
            ArrayList<TicketVO> listaTicket = new ArrayList<>();
            listaTicket = daoT.consultarObjeto(ticket, idString);
            lista.sort((Comparator<? super MovimentoVO>) listaTicket);
            if (lista.contains(listaTicket)) {
                // TODO OQUE FAZER?
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // TODO Consultar no banco o ticket da tela, e validar
        // TODO utilizar Label para notificar a validação na View

        ActionListener event = e -> {
            // TODO validar o ticket por 10minutos
            Constantes.FLAG = 1;
            t.setStatus(false);
            daoT.alterar(t);
        };

        Timer timer = new Timer(600000, event);
        timer.start();
        return false;
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
