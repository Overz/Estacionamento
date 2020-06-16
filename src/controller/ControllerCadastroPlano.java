package controller;

import model.banco.BaseDAO;
import model.dao.cliente.PlanoDAO;
import model.vo.cliente.PlanoVO;
import util.constantes.ConstHelpers;
import view.panels.cadastro.subCadastro.PanelzinhoCadastroDados;
import view.panels.cadastro.subCadastro.PanelzinhoCadastroPlano;

import javax.swing.*;
import java.util.ArrayList;

public class ControllerCadastroPlano {

    private final PanelzinhoCadastroPlano planoView;

    public ControllerCadastroPlano(JPanel panel) {
        this.planoView = (PanelzinhoCadastroPlano) panel;
    }

    public PlanoVO getPlanoForm() {
        return (PlanoVO) planoView.getCbPlano().getSelectedItem();
    }

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

    public DefaultComboBoxModel preencherCbx() {
        ConstHelpers.FLAG = 1;
        BaseDAO<PlanoVO> plano = new PlanoDAO();
        ArrayList<PlanoVO> lista = plano.consultarTodos();
        return new DefaultComboBoxModel(lista.toArray());
    }
}
