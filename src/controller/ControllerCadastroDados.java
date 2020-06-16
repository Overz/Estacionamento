package controller;

import model.banco.BaseDAO;
import model.dao.veiculos.CarroDAO;
import model.dao.veiculos.MarcaDAO;
import model.dao.veiculos.ModeloDAO;
import model.vo.cliente.ClienteVO;
import model.vo.veiculo.CarroVO;
import model.vo.veiculo.MarcaVO;
import model.vo.veiculo.ModeloVO;
import util.constantes.ConstHelpers;
import util.helpers.Modificacoes;
import view.panels.cadastro.subCadastro.PanelzinhoCadastroDados;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ControllerCadastroDados {

    private final PanelzinhoCadastroDados cadastroView;
    private final ClienteVO cliente;
    private final BaseDAO<CarroVO> daoCarro;
    private ArrayList<CarroVO> linhas;
    private ArrayList<CarroVO> lista;

    public ControllerCadastroDados(JPanel panel) {
        this.cadastroView = (PanelzinhoCadastroDados) panel;
        linhas = new ArrayList<>();
        lista = new ArrayList<>();
        cliente = new ClienteVO();
        daoCarro = new CarroDAO();
    }

    // TODO.html Atualizar Tabela caso Haja Carros na tela de Atualização
    public void atualizarTabela() {
        lista = daoCarro.consultar();
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
     * Cria um temporizador para setar os valores dos forumalarios
     * - Gambiarra
     */
    public void getResultadoFormTeste() {
        ActionListener event = e -> {
            ConstHelpers.FLAG = 1;
            cliente.setNome(cadastroView.getTxtNome().getText());
            cliente.setCpf(cadastroView.getTxtCPF().getText());
            cliente.setRg(cadastroView.getTxtRG().getText());
            cliente.setEmail(cadastroView.getTxtEmail().getText());
            cliente.setTelefone(cadastroView.getTxtTelefone().getText());

            int selectedRow = cadastroView.getTable().getSelectedRow();
            int selectedColumn = cadastroView.getTable().getSelectedColumn();
            CarroVO carroSelecionado =
                    (CarroVO) cadastroView.getTable().getValueAt(selectedRow, selectedColumn);

//            TableModel model = cadastroView.getTable().getModel();
//            int rows = model.getRowCount();
//            int columns = model.getColumnCount();
//            Object[] values = new Object[columns];
//
//            for (int i = 0; i < columns; i++) {
//                values[i] = model.getValueAt(selectedRow, i);
//            }
        };
        Timer timer = new Timer(300, event);
        timer.setRepeats(true);
        timer.start();
    }

    /**
     * Pega o formulario dos dados do Cliente na tela
     * @return ClienteVO
     */
    public ClienteVO getFormCliente() {
        try {
            String nome = cadastroView.getTxtNome().getText();
            String cpf = cadastroView.getTxtCPF().getText();
            String rg = cadastroView.getTxtRG().getText();
            String email = cadastroView.getTxtEmail().getText();
            String fone = cadastroView.getTxtTelefone().getText();
            boolean cbx = false;
            return new ClienteVO(nome, cpf, rg, email, fone);
        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Pega a lista de Carros da tabela
     * @return CarroVO
     */
    public CarroVO getFormCarro() {
        for (CarroVO carro : lista) {
            return carro;
        }
        return null;
    }

    /**
     * Pega as linhas da tabela
     * @return ArrayList<CarroVO>
     */
    public ArrayList<CarroVO> getFormListaCarro() {
        return lista;
    }

    /**
     * Limpa o formulario
     */
    public void limparForm() {
        try {
            cadastroView.getTxtNome().setText("");
            cadastroView.getTxtCPF().setText("");
            cadastroView.getTxtRG().setText("");
            cadastroView.getTxtEmail().setText("");
            cadastroView.getTxtTelefone().setText("");
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

    /**
     * Método para criar um ComboBox dentro da JTable
     */
    public void addComboBoxTable_MarcaVO() {
        JTable table = cadastroView.getTable();
        TableColumn tableColumn = table.getColumnModel().getColumn(1);
        tableColumn.setCellEditor(new DefaultCellEditor(new JComboBox<>(preencherCbxMarca())));

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText("CLIQUE PARA O COMBO BOX APARECER");
        tableColumn.setCellRenderer(renderer);
    }

    /**
     * Método para criar um ComboBox dentro da Tabela
     */
    public void addComboBoxTable_ModeloVO() {
        JTable table = cadastroView.getTable();
        TableColumn tableColumn = table.getColumnModel().getColumn(2);
        tableColumn.setCellEditor(new DefaultCellEditor(new JComboBox<>(preencherCbxModelo())));

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText("CLIQUE PARA O COMBO BOX APARECER");
        tableColumn.setCellRenderer(renderer);
    }

    /**
     * Preenche o ComboBox Marca da JTable
     *
     * @return new DefaultComboBoxModel
     */
    private DefaultComboBoxModel<MarcaVO> preencherCbxMarca() {
        BaseDAO<MarcaVO> bDAO = new MarcaDAO();
        ArrayList<MarcaVO> list = bDAO.consultarTodos();
        return new DefaultComboBoxModel(list.toArray());
    }

    /**
     * Preenche o ComboBox Modelo da JTable
     *
     * @return new DefaultComboBoxModel
     */
    private DefaultComboBoxModel<ModeloVO> preencherCbxModelo() {
        BaseDAO<ModeloVO> bDAO = new ModeloDAO();
        ArrayList<ModeloVO> list = bDAO.consultarTodos();
        return new DefaultComboBoxModel(list.toArray());
    }

    /**
     * Atualiza o Proximo ComboBox da tabela pelo tipo escolhido no ComboBox anterior
     */
    private ArrayList<?> atualizarComboBoxPorTipo(JComboBox cbx) {
        return null; // TODO realizar consulta por tipo do combobox
    }
}
