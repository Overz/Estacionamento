package util.helpers;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;

public class Modificacoes {

    /**
     * Modifica e retorna Label contendo uma Mensagem, com fonte, cor e tamanhos
     * padronizados.
     *
     * @param text: String
     * @return label + text
     */
    public static JLabel labelConfig(String text) {

        JLabel label = new JLabel();
        label.setText(text);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setBackground(Color.WHITE);
        label.setForeground(Color.BLACK);

        if (text == null || text.trim().isEmpty() || text.trim().equals("")) {
            label.setText(
                    "<html><body>Erro: >> Modificacoes.class<br>Method: labelConfig.<br>Motivo: Campo Vazio</body></html>");
            return label;
        }

        return label;
    }

    /**
     * Remove os espaçoes em branco do campo, iniciando a digitação no começo do campo
     *
     * @param txt {@link JFormattedTextField}
     */
    public static void caretPosition(@NotNull JFormattedTextField txt) {
        txt.addInputMethodListener(new InputMethodListener() {
            public void caretPositionChanged(InputMethodEvent event) {
                txt.viewToModel2D((Point) event.getText());
                int offset = txt.viewToModel2D((Point) event.getText());
                txt.setCaretPosition(offset);
            }

            public void inputMethodTextChanged(InputMethodEvent event) {

            }
        });

    }

    public static MaskFormatter addMask(MaskFormatter mask, String type, String placeHolder) {
        try {
            mask = new MaskFormatter(type);
            mask.setPlaceholder(placeHolder);
            return mask;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Arruma a mascara para que o place holder seja "focado" novamente.
     *
     * @param mask  {@link MaskFormatter}
     * @param field {@link JFormattedTextField}
     */
    public static void reinstalMask(MaskFormatter mask, JFormattedTextField field) {
        mask.uninstall();
        mask.install(field);
    }

    /**
     * Ajusta o ganho de Focus, verificando se o texto do campo NÃO bate com: NUMEROS, PALAVRAS, ou AMBOS,
     * se não bater, remove o texto, se sim, foca o CaretPosition para o tamanho do texto
     * <br>
     * 0: numeros<br>
     * 1: palavras<br>
     * 2: numeros & palavras<br>
     * 3: telefone com mascara<br>
     * 4: cpf com mascara<br>
     *
     * @param field {@link JTextField}
     * @param tipo  int
     * @return new {@link FocusAdapter}
     */
    public static FocusListener addMyFocusListener(JFormattedTextField field, String regex) {
        return new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (!field.getText().trim().matches(regex)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                } else {
                    String txt = field.getText().trim();
                    field.setCaretPosition(txt.length());
                }
            }
        };
    }

    /**
     * Modifica e retorna fonte, cor, e tamanho, padronizados.
     *
     * @param table: JTable
     * @return table(Style da Table)
     */
    public JTable tableLookAndFiel(JTable table) {
        table.setBackground(Color.WHITE);
        table.setForeground(Color.BLACK);
        table.setFont(new Font("Arial", Font.BOLD, 16));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        table.getTableHeader().setBackground(Color.WHITE);
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
}
