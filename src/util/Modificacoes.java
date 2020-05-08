package util;

import model.banco.BaseDAO;
import model.dao.veiculos.MarcaDAO;
import model.dao.veiculos.ModeloDAO;
import model.vo.veiculo.MarcaVO;
import model.vo.veiculo.ModeloVO;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.text.ParseException;
import java.util.ArrayList;

public class Modificacoes {

    /**
     * Modifica e retorna Label contendo uma Mensagem, com fonte, cor e tamanhos
     * padronizados.
     *
     * @param label: JLabel
     * @param text:  String
     * @return label + text
     */
    public JLabel labelConfig(JLabel label, String text) {

        label = new JLabel();
        label.setText(text);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setBackground(Color.WHITE);
        label.setForeground(Color.BLACK);

        if (text.trim().isEmpty() || text.trim().equals("")) {
            label.setText(
                    "<html><body>Erro: >> Modificacoes.class<br>Method: labelConfig.<br>Motivo: Campo Vazio</body></html>");
            return label;
        }

        return label;
    }

    /**
     * Modifica e retorna fonte, cor, e tamanho, padronizados.
     *
     * @param table
     * @return table(Style da Table)
     */
    public JTable tableLookAndFiel(JTable table) {
        table.setBackground(Color.WHITE);
        table.setForeground(Color.BLACK);
        table.setFont(new Font("Arial", Font.BOLD, 16));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        table.getTableHeader().setBackground(Color.WHITE);
        table.setColumnSelectionAllowed(true);
        table.setCellSelectionEnabled(false);
        table.setRowSelectionAllowed(true);
        table.getTableHeader().setReorderingAllowed(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));

        table.setRowHeight(35);
        table.setShowGrid(true);
        table.setGridColor(Color.BLACK);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);

        return table;
    }

    /**
     * Configura a Tabela para editar os campos de acordo com sua instancia
     *
     * @param table: JTable
     * @return table
     */
    public JTable tableConfigurations(JTable table) {
        DefaultTableCellRenderer centerRendererLeft = new DefaultTableCellRenderer();
        DefaultTableCellRenderer centerRendererCenter = new DefaultTableCellRenderer();
        @SuppressWarnings("unused")
        DefaultTableCellRenderer centerRendererRight = new DefaultTableCellRenderer();

        table.getColumnModel().getColumn(0).setCellRenderer(centerRendererCenter);

//		Renderizar os valores dentro da celular
        for (int i = 0; i < table.getModel().getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRendererCenter);
            if (i == 3) {
                table.getColumnModel().getColumn(1).setCellRenderer(centerRendererLeft);
            }
        }


        return table;
    }

    /**
     * Método para criar um ComboBox dentro da JTable
     *
     * @param table       tabela
     * @param sportColumn escolher a coluna
     */
//	 Fiddle with the Sport column's cell editors/renderers.
    public void mostrarComboBoxJTable_ModeloVO(JTable table, TableColumn sportColumn) {
//		 Set up the editor for the sport cells.
        BaseDAO<ModeloVO> bDAO = new ModeloDAO();
        ArrayList<ModeloVO> list = (ArrayList<ModeloVO>) bDAO.consultarTodos();
        JComboBox<ModeloVO> cbModelo = new JComboBox<ModeloVO>();
        cbModelo.setModel(new DefaultComboBoxModel(list.toArray()));

        sportColumn.setCellEditor(new DefaultCellEditor(cbModelo));

//		 Set up tool tips for the sport cells.
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText("CLIQUE PARA O COMBO BOX APARECER");
        sportColumn.setCellRenderer(renderer);
    }

    /**
     * Método para criar um ComboBox dentro da JTable
     *
     * @param table       tabela
     * @param sportColumn escolher a coluna
     */
//	 Fiddle with the Sport column's cell editors/renderers.
    public void mostrarComboBoxJTabel_MarcaVO(JTable table, TableColumn sportColumn) {
//		 Set up the editor for the sport cells.
        BaseDAO<MarcaVO> bDAO = new MarcaDAO();
        ArrayList<MarcaVO> list = (ArrayList<MarcaVO>) bDAO.consultarTodos();
        JComboBox<MarcaVO> cbMarca = new JComboBox<MarcaVO>();
        cbMarca.setModel(new DefaultComboBoxModel(list.toArray()));

        sportColumn.setCellEditor(new DefaultCellEditor(cbMarca));

//		 Set up tool tips for the sport cells.
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText("CLIQUE PARA O COMBO BOX APARECER");
        sportColumn.setCellRenderer(renderer);
    }

    /**
     * Método para reenderizar a JTable e criar uma mascara com campo formatado em
     * uma coluna especifica
     *
     * @param table
     * @param sportColumn: TableColumn
     */
    public void maskFormJTable(JTable table, TableColumn sportColumn) {

        JFormattedTextField placa = new JFormattedTextField();
        MaskFormatter mascara;
        try {
            mascara = new MaskFormatter("HHHHHHHHHH");
            mascara.setPlaceholderCharacter('?');
            mascara.install(placa);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sportColumn.setCellEditor(new DefaultCellEditor(placa));

    }

    /**
     * Adicionar e Remover o PlaceHolder
     *
     * @param field: JTextField
     * @param msg:   String
     */
    public JFormattedTextField adicionarRemoverFocus(JTextField field, String msg) {

        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().trim().toLowerCase().equals(msg.toLowerCase())) {
                    field.setText("");
                }
                field.setForeground(Color.BLACK);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().trim().toLowerCase().equals("")) {
                    field.setText(msg);
                }
                field.setForeground(Color.BLACK);
            }
        });
        return (JFormattedTextField) field;
    }

    /**
     * Criação de uma mascara para o campo de acordo com a instancia, e um place holder(Palavras que somem ao
     * digitar)
     *
     * @param mask: MaskFormatter
     * @param tipo: int
     * @param text: String
     */
    public MaskFormatter maskAndPlaceHolder(MaskFormatter mask, int tipo, String text) {

        try {
            mask = new MaskFormatter();
            if (tipo == 1) {
                mask.setMask("###################################");
            }

            if (tipo == 2) {
                mask.setMask("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
            }

            if (tipo == 3) {
                mask.setMask("***********************************");
            }
            mask.setPlaceholder(text);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mask;
    }

    /**
     * Remove os espaçoes em branco do campo, iniciando a digitação no começo do
     * campo.
     *
     * @param txt: JTextField
     * @return cast(JFormatedTextField)
     */
    public JFormattedTextField caretPosition(JTextField txt) {
        txt.addInputMethodListener(new InputMethodListener() {
            public void caretPositionChanged(InputMethodEvent event) {
                txt.viewToModel2D((Point) event.getText());
                int offset = txt.viewToModel2D((Point) event.getText());
                txt.setCaretPosition(offset);
            }

            public void inputMethodTextChanged(InputMethodEvent event) {

            }
        });
        return (JFormattedTextField) txt;
    }
}
