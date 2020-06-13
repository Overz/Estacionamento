package controller;

import model.vo.cliente.EnderecoVO;
import view.panels.cadastro.subCadastro.PanelzinhoCadastroEndereco;

import javax.swing.*;

public class ControllerCadastroEndereco {

    private final PanelzinhoCadastroEndereco enderecoView;

    public ControllerCadastroEndereco(JPanel panel) {
        enderecoView = (PanelzinhoCadastroEndereco) panel;
    }

    public EnderecoVO getResultadoForm() {
        try {
            String rua = enderecoView.getTxtRua().getText();
            int numero = Integer.parseInt(enderecoView.getTxtNumero().getText());
            String bairro = enderecoView.getTxtBairro().getText();
            String cidade = enderecoView.getTxtCidade().getText();
            return new EnderecoVO(numero, rua, bairro, cidade);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void limparForm(){
        enderecoView.getTxtBairro().setText("");
        enderecoView.getTxtCidade().setText("");
        enderecoView.getTxtNumero().setText("");
        enderecoView.getTxtRua().setText("");
    }
}
