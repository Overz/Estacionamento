package controller;

import model.vo.cliente.PlanoVO;
import view.panels.cadastro.subCadastro.SubCadastroPlanoView;

import javax.swing.*;

public class ControllerPlanoCadastro {

    private final SubCadastroPlanoView planoView;

    public ControllerPlanoCadastro(JPanel panel) {
        this.planoView = (SubCadastroPlanoView) panel;
    }

    public PlanoVO getPlanoForm() {
        return (PlanoVO) planoView.getCbPlano().getSelectedItem();
    }

    public void limparForm() {
        planoView.getCbFormaPgto().setSelectedIndex(-1);
        planoView.getCbPlano().setSelectedIndex(-1);
    }
}
