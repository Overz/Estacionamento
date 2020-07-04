package controller;

import model.vo.cliente.ContratoVO;
import model.vo.cliente.PlanoVO;
import util.constantes.ConstHelpers;
import util.helpers.Util;
import view.panels.cadastro.subCadastro.PanelzinhoCadastroDados;
import view.panels.cadastro.subCadastro.PanelzinhoCadastroPlano;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

public class ControllerCadastroPlano {

    private final PanelzinhoCadastroPlano planoView;

    public ControllerCadastroPlano(PanelzinhoCadastroPlano panel) {
        this.planoView = panel;
        this.atualizarHoraTela();
    }

    public PlanoVO getPlanoForm() {
        return (PlanoVO) planoView.getCbPlano().getSelectedItem();
    }

    /**
     * Retorna os dados do Contrato + Plano
     *
     * @return new ContratoVO
     */
    public ContratoVO getContratoForm() {
        try {
            String strCartao = planoView.getTxtCartao().getText();
            long cartao = 0;
            if (!strCartao.isEmpty()) {
                cartao = Long.parseLong(strCartao);
            }
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime validade = calcularValidade();
            boolean bloqueado = planoView.getChckbxBloquear().isSelected();
            boolean ativo = false;
            if (!bloqueado) {
                ativo = true;
            }
            PlanoVO p = getPlanoForm();
            double valor = 0.0;
            if (p.getId() == 1) {
                valor = 200.0;
            } else if (p.getId() == 2) {
                valor = 25.0;
            }
            return new ContratoVO(cartao, now, validade, ativo, valor, p.getTipo(), p);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Limpa o formulario da tela
     */
    public void limparForm() {
        try {
            planoView.getCbFormaPgto().setSelectedIndex(-1);
            planoView.getCbPlano().setSelectedIndex(0);
        } catch (Exception e) {
            try {
                System.out.println(e.getClass().getSimpleName());
                System.out.println(e.getClass().getMethod("limparForm",
                        PanelzinhoCadastroDados.class, ControllerCadastroDados.class));
                System.out.println(e.getMessage());
            } catch (NoSuchMethodException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Timer para o horario da tela, se manter atualizado
     */
    private void atualizarHoraTela() {
        ActionListener event = e -> {
            planoView.getLblHora().setText(LocalDateTime.now().format(ConstHelpers.DTF));
        };
        Timer timer = new Timer(500, event);
        timer.start();
    }

    /**
     * Adiciona 1 Mes de Validade ao Contrato do usuario
     */
    public LocalDateTime calcularValidade() {
        LocalDateTime now = LocalDateTime.now();
        Month month = now.getMonth().plus(1);
        LocalDate nextMonth = LocalDate.of(now.getYear(), month, now.getDayOfMonth());
        return LocalDateTime.of(nextMonth, now.toLocalTime());
    }

    public DefaultComboBoxModel preencherCbx() {
        return new DefaultComboBoxModel(Util.atualizarListaModelo().toArray());
    }

    private MouseAdapter addMousAdapter() {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                LocalDateTime validade = LocalDateTime.now();
                validade = validade.plusWeeks(5);
                planoView.getLblMesValidade().setText(validade.format(ConstHelpers.DTF));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                LocalDateTime validade = LocalDateTime.now();
                validade = validade.plusWeeks(5);
                planoView.getLblMesValidade().setText(validade.format(ConstHelpers.DTF));
            }
        };
    }

    public void addValidade() {
        planoView.getCbPlano().addMouseListener(addMousAdapter());
        planoView.getCbFormaPgto().addMouseListener(addMousAdapter());
        planoView.getTxtCartao().addMouseListener(addMousAdapter());
    }
}
