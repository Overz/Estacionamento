package controller;

import model.banco.BaseDAO;
import model.dao.veiculos.CarroDAO;
import model.dao.veiculos.MarcaDAO;
import model.dao.veiculos.ModeloDAO;
import model.vo.cliente.ClienteVO;
import model.vo.veiculo.CarroVO;
import model.vo.veiculo.MarcaVO;
import model.vo.veiculo.ModeloVO;
import util.constantes.Colunas;
import util.constantes.ConstHelpers;
import util.constantes.ConstInicio;
import util.helpers.Modificacoes;
import view.panels.cadastro.subCadastro.PanelzinhoCadastroDados;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.util.ArrayList;

public class ControllerCadastroDados {

    private final PanelzinhoCadastroDados cadastroView;
    private final BaseDAO<CarroVO> daoCarro;
    private ArrayList<CarroVO> linhas;
    private ArrayList<CarroVO> lista;
    private int id;
    private int listaCheia;

    public ControllerCadastroDados(JPanel panel) {
        this.cadastroView = (PanelzinhoCadastroDados) panel;
        linhas = new ArrayList<>();
        lista = new ArrayList<>();
        daoCarro = new CarroDAO();
    }

    public void atualizarTabela() {
        limparTabela();

        if (ConstHelpers.FLAG == 1) {
            lista = daoCarro.consultar(String.valueOf(id));
        }

        DefaultTableModel model = (DefaultTableModel) cadastroView.getTable().getModel();

        Object[] novaLinha = new Object[4];
        for (CarroVO carro : lista) {

            novaLinha[0] = carro.getPlaca();
            novaLinha[1] = carro.getModelo().getMarca();
            novaLinha[2] = carro.getModelo();
            novaLinha[3] = carro.getCor();

            model.addRow(novaLinha);
        }
    }

    private void limparTabela() {
        cadastroView.getTable().setModel(new DefaultTableModel(new Object[][]{}, Colunas.COLUNAS_CLIENTE));
    }

    /**
     * Adiciona uma nova linha a tabela
     */
    public void addrow() {
        DefaultTableModel model = (DefaultTableModel) cadastroView.getTable().getModel();
        Object[] data = new Object[1];
        model.addRow(data);
    }

    /**
     * Pega o formulario dos dados do Cliente na tela
     *
     * @return ClienteVO
     */
    public ClienteVO getFormCliente() {
        try {
            String nome = cadastroView.getTxtNome().getText();
            String cpf = cadastroView.getTxtCPF().getText();
            String rg = cadastroView.getTxtRG().getText();
            String email = cadastroView.getTxtEmail().getText();
            String fone = cadastroView.getTxtTelefone().getText();
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
     * @param table JTable
     * @return CarroVO
     */
    public CarroVO getFormCarro(JTable table) { // TODO ALTERAR PARA PEGAR OS CAMPOS
        int row = table.getRowCount();
        DefaultTableModel model = (DefaultTableModel) cadastroView.getTable().getModel();
        String primeiraPlaca = null;
        String primeiraCor = null;
        ModeloVO primeiroModelo = null;
        for (int i = 0; i < row; i++) {
            String placa = (String) model.getValueAt(i, 0);
            MarcaVO marca = (MarcaVO) model.getValueAt(i, 1);
            ModeloVO modelo = (ModeloVO) model.getValueAt(i, 2);
            String cor = (String) model.getValueAt(i, 3);
            if (marca != null) {
                modelo.setMarca(marca);
            }
            if (placa != null && !placa.trim().isEmpty()) {
                placa = placa.replace('?', ' ').trim();
            }
            if (primeiraPlaca == null || primeiraCor == null || primeiroModelo == null) {
                primeiraPlaca = placa;
                primeiraCor = cor;
                primeiroModelo = modelo;
            }
            if (i > 0) {
                this.salvarOutrosCarros(new CarroVO(placa, cor, modelo));
            }
        }
        return new CarroVO(primeiraPlaca, primeiraCor, primeiroModelo);
    }

    public void salvarOutrosCarros(CarroVO carroVO) {
        lista.add(carroVO);
    }

    /**
     * Método para retornar aa lista de Carros completa
     *
     * @return ArrayList
     */
    public ArrayList<CarroVO> retornarListaCarros() {
        return lista;
    }

    /**
     * Remove a linha selecionada na tabela de carros do cliente
     */
    public void removeRow() {
        try {
            int row = cadastroView.getTable().getSelectedRow();
            if (row >= 0) {
                DefaultTableModel model = (DefaultTableModel) cadastroView.getTable().getModel();
                model.removeRow(row);

                if (ConstHelpers.FLAG == -1) {
                    CarroVO carro = linhas.get(row);

                    if (daoCarro.excluirPorID(carro.getId())) {
                        JOptionPane.showMessageDialog(cadastroView, Modificacoes.labelConfig("EXCLUSÂO REALIZADA"),
                                "Exclusão", JOptionPane.WARNING_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(cadastroView,
                        Modificacoes.labelConfig("Por favor, Selecione uma Linha"),
                        "Exclusão", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void limparTabelaCarros() {
        JTable table = cadastroView.getTable();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int row = table.getRowCount();
        for (int i = 0; i < row; i++) {
            model.removeRow(i);
        }
    }

    /**
     * Método para criar um ComboBox dentro da JTable
     */
    private void addComboBoxTable_MarcaVO() {
        JTable table = cadastroView.getTable();
        TableColumn tableColumn = table.getColumnModel().getColumn(1);
        tableColumn.setCellEditor(new DefaultCellEditor(new JComboBox<>(preencherCbx_Marca())));

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText("CLIQUE PARA O COMBO BOX APARECER");
        tableColumn.setCellRenderer(renderer);
    }

    /**
     * Método para criar um ComboBox dentro da Tabela
     */
    private void addComboBoxTable_ModeloVO() {
        JTable table = cadastroView.getTable();
        TableColumn tableColumn = table.getColumnModel().getColumn(2);
        JComboBox cbx = new JComboBox(preencherCbx_Modelo());
        tableColumn.setCellEditor(new DefaultCellEditor(cbx));

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText("CLIQUE PARA O COMBO BOX APARECER");
        tableColumn.setCellRenderer(renderer);
    }

    private void addComboBoxTable_Cores() {
        JTable table = cadastroView.getTable();
        TableColumn tableColumn = table.getColumnModel().getColumn(3);
        JComboBox cbx = new JComboBox(preencherCbx_Cores());
        cbx.setSelectedIndex(0);
        tableColumn.setCellEditor(new DefaultCellEditor(cbx));

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText("CLIQUE PARA O COMBO BOX APARECER");
        tableColumn.setCellRenderer(renderer);
    }

    public void addComboBoxJTable() {
        addComboBoxTable_MarcaVO();
        addComboBoxTable_ModeloVO();
        addComboBoxTable_Cores();
    }

    private DefaultComboBoxModel<String> preencherCbx_Cores() {
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
    private DefaultComboBoxModel<MarcaVO> preencherCbx_Marca() {
        BaseDAO<MarcaVO> bDAO = new MarcaDAO();
        ArrayList<MarcaVO> list = bDAO.consultarTodos();
        return new DefaultComboBoxModel(list.toArray());
    }

    /**
     * Preenche o ComboBox Modelo da JTable
     *
     * @return new DefaultComboBoxModel
     */
    private DefaultComboBoxModel<ModeloVO> preencherCbx_Modelo() {
        BaseDAO<ModeloVO> bDAO = new ModeloDAO();
        ArrayList<ModeloVO> list = bDAO.consultarTodos();
        return new DefaultComboBoxModel(list.toArray());
    }

}
