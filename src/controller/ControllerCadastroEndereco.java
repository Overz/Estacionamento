package controller;

import model.vo.cliente.EnderecoVO;
import view.panels.cadastro.subCadastro.PanelzinhoCadastroDados;
import view.panels.cadastro.subCadastro.PanelzinhoCadastroEndereco;

import javax.swing.*;

public class ControllerCadastroEndereco {

    private final PanelzinhoCadastroEndereco enderecoView;

    public ControllerCadastroEndereco(JPanel panel) {
        enderecoView = (PanelzinhoCadastroEndereco) panel;
    }

    /**
     * Pega os Resultados do Formulario da tela de Endereço
     *
     * @return EnderecoVO
     */
    public EnderecoVO getResultadoForm() {
        try {
            String rua = enderecoView.getTxtRua().getText();
            String strNumero = enderecoView.getTxtNumero().getText();
            int numero = 0;
            if (strNumero != null && !strNumero.isEmpty()) {
                numero = Integer.parseInt(strNumero);
            }
            String bairro = enderecoView.getTxtBairro().getText().trim();
            String cidade = enderecoView.getTxtCidade().getText().trim();
            String uf = String.valueOf(enderecoView.getCbEstado().getModel().getSelectedItem());
            return new EnderecoVO(numero, rua, bairro, cidade, uf);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Limpa o Formulario
     */
    public void limparForm() {
        try {
            enderecoView.getTxtBairro().setText("");
            enderecoView.getTxtCidade().setText("");
            enderecoView.getTxtNumero().setText("");
            enderecoView.getTxtRua().setText("");
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
}
