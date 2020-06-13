package controller;

import model.vo.cliente.PlanoVO;
import view.panels.cadastro.subCadastro.PanelzinhoCadastroPlano;

import javax.swing.*;

public class ControllerCadastroPlano {

    private final PanelzinhoCadastroPlano planoView;

    public ControllerCadastroPlano(JPanel panel) {
        this.planoView = (PanelzinhoCadastroPlano) panel;
    }

    public PlanoVO getPlanoForm() {
        return (PlanoVO) planoView.getCbPlano().getSelectedItem();
    }

    public void limparForm() {
        planoView.getCbFormaPgto().setSelectedIndex(-1);
        planoView.getCbPlano().setSelectedIndex(-1);
    }
}
