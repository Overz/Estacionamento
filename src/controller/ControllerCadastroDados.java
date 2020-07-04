package controller;

import model.banco.BaseDAO;
import model.dao.veiculos.MarcaDAO;
import model.dao.veiculos.ModeloDAO;
import model.vo.cliente.ClienteVO;
import model.vo.veiculo.CarroVO;
import model.vo.veiculo.MarcaVO;
import model.vo.veiculo.ModeloVO;
import util.constantes.ConstInicio;
import view.panels.cadastro.subCadastro.PanelzinhoCadastroDados;

import javax.swing.*;
import java.util.ArrayList;

public class ControllerCadastroDados {

    private final PanelzinhoCadastroDados cadastroView;

    public ControllerCadastroDados(JPanel panel) {
        this.cadastroView = (PanelzinhoCadastroDados) panel;
    }

    /**
     * Pega o formulario dos dados do Cliente na tela
     *
     * @return ClienteVO
     */
    public ClienteVO getFormCliente() {
        try {
            String nome = cadastroView.getTxtNome().getText();
            String cpf = cadastroView.getTxtCPF().getText().trim().replaceAll("\\.", "").replaceAll("-", "");
            String rg = cadastroView.getTxtRG().getText().trim();
            String email = cadastroView.getTxtEmail().getText().trim();
            String fone = cadastroView.getTxtTelefone().getText().trim().replace("(", "").replace(")", "");
            return new ClienteVO(nome, cpf, rg, email, fone);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Pega a lista de Carros da tabela
     *
     * @return CarroVO
     */
    public CarroVO getFormCarro() {
        try {
            String placa = cadastroView.getTxtPlaca().getText().trim();
            String cor = (String) cadastroView.getCbCor().getSelectedItem();
            ModeloVO modelo = (ModeloVO) cadastroView.getCbModelo().getSelectedItem();
            MarcaVO marca = (MarcaVO) cadastroView.getCbMarca().getSelectedItem();
            modelo.setMarca(marca);
            return new CarroVO(placa, cor, modelo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public DefaultComboBoxModel<String> preencherCbx_Cores() {
        ArrayList<String> cores = new ArrayList<>();
        cores.add(ConstInicio.VAZIO);
        cores.add("AMARELO");
        cores.add("AZUL");
        cores.add("BRANCO");
        cores.add("BEGE");
        cores.add("CINZA");
        cores.add("CROMADO");
        cores.add("LARANJA");
        cores.add("MARROM");
        cores.add("PRATA");
        cores.add("PRETO");
        cores.add("ROSA");
        cores.add("ROXO");
        cores.add("VERMELHO");
        cores.add("VINHO");
        return new DefaultComboBoxModel(cores.toArray());
    }

    /**
     * Preenche o ComboBox Marca da JTable
     *
     * @return new DefaultComboBoxModel
     */
    public DefaultComboBoxModel<MarcaVO> preencherCbx_Marca() {
        BaseDAO<MarcaVO> bDAO = new MarcaDAO();
        ArrayList<MarcaVO> list = bDAO.consultarTodos();
        return new DefaultComboBoxModel(list.toArray());
    }

    /**
     * Preenche o ComboBox Modelo da JTable
     *
     * @return new DefaultComboBoxModel
     */
    public DefaultComboBoxModel<ModeloVO> preencherCbx_Modelo() {
        BaseDAO<ModeloVO> bDAO = new ModeloDAO();
        ArrayList<ModeloVO> list = bDAO.consultarTodos();
        return new DefaultComboBoxModel(list.toArray());
    }

}
