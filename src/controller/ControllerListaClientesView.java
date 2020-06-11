package controller;

import model.banco.BaseDAO;
import model.dao.cliente.PlanoDAO;
import model.vo.cliente.ClienteVO;
import model.vo.cliente.EnderecoVO;
import model.vo.cliente.PlanoVO;
import util.constantes.Colunas;
import util.helpers.Modificacoes;
import view.panels.cadastro.ListaClientesView;
import view.panels.cadastro.subCadastro.SubCadastroDadosView;
import view.panels.cadastro.subCadastro.SubCadastroEnderecoView;
import view.panels.cadastro.subCadastro.SubCadastroPlanoView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class ControllerListaClientesView {
    private ListaClientesView mainClienteCadastroListagemView;
    private SubCadastroDadosView dadosView;
    private SubCadastroEnderecoView enderecoView;
    private SubCadastroPlanoView planoView;

    private SubCadastroDadosView view;

    private final BaseDAO<PlanoVO> daoP;
    private ArrayList<PlanoVO> list;
    private String msg;

    public ControllerListaClientesView(JPanel panel) {
        if (panel instanceof ListaClientesView){
            this.mainClienteCadastroListagemView = (ListaClientesView) panel;
        }
        if (panel instanceof SubCadastroDadosView){
            this.dadosView = (SubCadastroDadosView) panel;
        }
        if (panel instanceof SubCadastroEnderecoView){
            enderecoView = (SubCadastroEnderecoView) panel;
        }
        
        daoP = new PlanoDAO();
        list = new ArrayList<>();
    }

    public void atualizarTabela() {

        limparTabela();

        list = daoP.consultarTodos();

        DefaultTableModel model = (DefaultTableModel) mainClienteCadastroListagemView.getTable().getModel();

        Object[] novaLinha = new Object[4];
        for (PlanoVO plano : list) {

            novaLinha[0] = plano.getCliente().getId();
            novaLinha[1] = plano.getCliente().getNome();
            novaLinha[2] = plano.getTipo();
            novaLinha[3] = calcularVencimento();

            model.addRow(novaLinha);
        }
    }

    private void limparTabela() {
        mainClienteCadastroListagemView.getTable().setModel(new DefaultTableModel(new Object[][]{}, Colunas.COLUNAS_CLIENTE));
    }

    private String calcularVencimento() {
        return "TESTE";
    }

    public void removeSelectedRow() {

        int row = mainClienteCadastroListagemView.getTable().getSelectedRow();
        PlanoVO p = list.get(row);
        DefaultTableModel model = (DefaultTableModel) mainClienteCadastroListagemView.getTable().getModel();
        model.removeRow(row);

        if (daoP.excluirPorID(p.getId())) {
            msg = "EXCLUSÃO REALIZADA COM SUCESSO!";
        } else {
            msg = "ERRO AO REALIZAR EXCLUSÃO!";
        }

        this.atualizarTabela();

        JOptionPane.showMessageDialog(mainClienteCadastroListagemView, Modificacoes.labelConfig(msg), "EXCLUSÂO",
                JOptionPane.ERROR_MESSAGE);

        System.out.println(p.toString());
    }

    public void cadastrarDados(ClienteVO c) {
        //TODO Cadastrar os dados da tela SubDadosCadastro
    }

    public void cadastrarEndereco(EnderecoVO e) {
        //TODO Cadastrar os dados da tela SubEnderecoCadastro
    }

    public void cadastrarPlano(PlanoVO p) {
        //TODO Cadastrar os dados da tela SubPlanoCadastro
    }

    public ClienteVO getFormDadosTelaCliente() {
        return view.returnForm();
    }

    public ClienteVO getFormEnderecoCliente() {
        return null;
    }

    public EnderecoVO getFormPlanoCliente() {
        return null;
    }
}
