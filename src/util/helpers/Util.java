package util.helpers;

import model.banco.BaseDAO;
import model.dao.cliente.PlanoDAO;
import model.vo.cliente.PlanoVO;
import util.constantes.ConstHelpers;

import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Util {

    private static final String linuxOs = "Linux";
    private static final String windowsOs = "Windows";

    private static long diff; // Diferença ENTRE Dias
    private static long days; // Diferença de Dias
    private static long hours; // Diferença de Horas
    private static long minutes; // Diferença de Minutos
    private static long seconds; // DIferença de Segundos

    /**
     * Formatador de valores;
     *
     * @param value double
     * @return String
     */
    public static String formatarValor(double value) {
        Locale locale = Locale.getDefault(Locale.Category.FORMAT);
        NumberFormat formatter = NumberFormat.getInstance(locale);
        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);
        return formatter.format(value);
    }

    /**
     * Evento de Click, caso pressionado um pré-requisito na tela,
     * realiza ações de Enable(True)
     *
     * @param table   JTable
     * @param button  JButton
     * @param color   String
     * @param tipoCor int
     */
    public synchronized static void habilitarOpcoes(JTable table, JButton button, String color, int tipoCor, JTextField field) {
        try {
            table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == MouseEvent.BUTTON1) {
                        Object o = table.getModel().getValueAt(table.getSelectedRow(), table.getSelectedColumn());
                        if (o != null) {
                            if (tipoCor == 1) {
                                button.setBackground(new Color(100, 149, 237));
                            } else if (tipoCor == 2) {
                                button.setBackground(Color.decode(color));
                            }
                            button.setEnabled(true);
                        }
                    }
                    /*
                     * Remove o focus e as linhas selecionadas
                     * da tabela e trasnfere para o campo de procura
                     * apois 20 mili segundos
                     */
                    if (table.hasFocus()) {
                        ActionListener event = e1 -> {
                            table.getSelectionModel().clearSelection();
                            table.clearSelection();
                            button.setBackground(Color.WHITE);
                            button.setEnabled(false);
                        };
                        Timer timer = new Timer(ConstHelpers.TEMPO_30_SEG, event);
                        timer.start();
                    }

                    int row = table.getSelectedRow();
                    int col = table.getSelectedColumn();
                    if (col == 0) {
                        Object o = table.getValueAt(row, col);
                        field.setText(String.valueOf(o));
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Automação de Cor(Background):
     *
     * <p>Se o <strong>PRIMEIRO</strong> botão/algo estiver visivel e a cor: {@code String/Color != null || !empity}
     * o botão recebe uma cor.</p>
     *
     * <p>Caso tenha uma cor secundaria, para <strong>demais botões</strong>
     * estes receberão, caso contrario, uma cor <strong>Default</strong>(new Jbutton().getBackgroundColor)</p>
     *
     * <p><strong>ESTES BOTÕES RECEBERÃO AS CORES COM A RESPECTIVA ORDEM DE ADIÇÃO</strong></p>
     *
     * @param visible          boolean
     * @param defaultBackgroud boolean
     * @param strMainColor     String
     * @param mainColor        Color
     * @param btns             Array[]
     */
    public static void mudarCorBotao(boolean visible, boolean defaultBackgroud, String strMainColor, String strRestColors,
                                     Color mainColor, Color restColors, JButton... btns) {
        try {
            for (int j = 0; j < btns.length; j++) {
                JButton button = btns[j];
                if (visible) {
                    // Primeiro Botão
                    if (button.equals(btns[0])) {
                        if (!strMainColor.trim().isEmpty() && !strMainColor.trim().isBlank()) {
                            button.setBackground(Color.decode(strMainColor));
                        } else if (mainColor != null) {
                            button.setBackground(mainColor);
                        }
                    }

                    // Demais Botões
                    if (j >= 1) {
                        if (defaultBackgroud) {
                            if (button.equals(btns[j]) || button.equals(btns[btns.length - 1])) {
                                if (button.equals(btns[j])) {
                                    button.setBackground(new JButton().getBackground());
                                }
                            }
                        } else if (!strRestColors.trim().isBlank() && !strRestColors.trim().isBlank()) {
                            if (button.equals(btns[j])) {
                                button.setBackground(Color.decode(strRestColors));
                            }
                        } else if (restColors != null) {
                            if (button.equals(btns[j])) {
                                button.setBackground(restColors);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
            try {
                System.out.println(e.getClass().getMethod("mudarCorBotao", Util.class, Color.class, String.class, Array.class));
            } catch (NoSuchMethodException noSuchMethodException) {
                noSuchMethodException.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    private static String getOs() {
        return System.getProperty("os.name");
    }

    /**
     * Verifica o sistema operacional para executar um comando que verifica o Desktop,
     * e roda na terminal um comando, pegando o retorno e inserindo no JFileChooser como padrão de abertura
     *
     * @return String user directory
     */
    private static String getDesktopPath() {
        try {
            if (getOs().equalsIgnoreCase(linuxOs)) {
                String path;
                Process p = Runtime.getRuntime().exec("xdg-user-dir DESKTOP");
                BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
                if ((path = in.readLine()) != null) {
                    return path;
                }
            } else {
                String userHome = System.getProperty("user.home");
                if (!userHome.contains("Desktop")) {
                    return userHome.concat("/Desktop");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return System.getProperty("user.home");
    }

    /**
     * Abre um JFileChooser com um Caminho Pré-Definido /Home/ ou /Desktop
     *
     * @param panel        Panel
     * @param jFileChooser JFileChooser
     * @return int - SaveDialog(...-1,0,1...)
     */
    public static int abrirJFileChooser(JPanel panel, JFileChooser jFileChooser) {
        try {
            File dir = new File(getDesktopPath());
            jFileChooser.setCurrentDirectory(dir);
            jFileChooser.setDialogTitle("Salvar em...");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jFileChooser.showSaveDialog(panel);
    }

    /**
     * Retorna o Caminho Absoluto do JFileChooser
     *
     * @param file File
     * @return String - Path = (/home/user/path/)
     */
    public static String caminhoFileChooser(File file) {
        return file.getAbsolutePath();
    }

    public static ArrayList<PlanoVO> atualizarListaModelo() {
        ConstHelpers.FLAG = 1;
        BaseDAO<PlanoVO> plano = new PlanoDAO();
        return plano.consultarTodos();
    }

    /**
     * Método para reenderizar a JTable e criar uma mascara com campo formatado em
     * uma coluna especifica
     *
     * @param sportColumn: TableColumn
     */
    public static void maskFormJTable(TableColumn sportColumn) {

        JFormattedTextField placa = new JFormattedTextField();
        MaskFormatter mascara;
        try {
            mascara = new MaskFormatter("HHHHHHH");
            mascara.setPlaceholderCharacter('?');
            mascara.install(placa);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sportColumn.setCellEditor(new DefaultCellEditor(placa));
    }

    // Os Calculos Abaixo foram feitos de DUAS MANEIRAS, TimeUnit, e Representações a 'mão'
    // Ambos estão iguais

//    long days = TimeUnit.MILLISECONDS.toDays(diff);
//    long remainingHoursInMillis = diff - TimeUnit.DAYS.toMillis(days);
//    long hours = TimeUnit.MILLISECONDS.toHours(remainingHoursInMillis);
//    long remainingMinutesInMillis = remainingHoursInMillis - TimeUnit.HOURS.toMillis(hours);
//    long minutes = TimeUnit.MILLISECONDS.toMinutes(remainingMinutesInMillis);
//    long remainingSecondsInMillis = remainingMinutesInMillis - TimeUnit.MINUTES.toMillis(minutes);
//    long seconds = TimeUnit.MILLISECONDS.toSeconds(remainingSecondsInMillis);

    /**
     * Método que calcula diferença de dias, horas, minutos, segundos,
     * podendo utilizar os getters apartir desse método
     *
     * @param diff long
     */
    public static void calcularDiff(long diff) {
        Util.diff = diff;
        days = daysTimeUnit();
        hours = hoursTimeUnit();
        minutes = minutesTimeUnit();
        seconds = secondsTimeUnit();
    }

    /**
     * Diferença de Dias ate Agora
     *
     * @return long
     */
    private static long daysTimeUnit() {
        return TimeUnit.MILLISECONDS.toDays(diff);
    }

    /**
     * Milisegundos Restantes da Diferença de Dias
     *
     * @return long
     */
    private static long remainingHoursInMillis() {
        return Util.diff - TimeUnit.DAYS.toMillis(daysTimeUnit());
    }

    /**
     * Horas Restantes da Diferença de Dias
     *
     * @return long
     */
    private static long hoursTimeUnit() {
        return TimeUnit.MILLISECONDS.toHours(remainingHoursInMillis());
    }

    /**
     * Milsegundos Restantes da Diferença de Horas
     *
     * @return long
     */
    private static long remainingMinutesInMillis() {
        return remainingHoursInMillis() - TimeUnit.HOURS.toMillis(hoursTimeUnit());
    }

    /**
     * Minutos Restantes da Diferença de Horas
     *
     * @return long
     */
    private static long minutesTimeUnit() {
        return TimeUnit.MILLISECONDS.toMillis(remainingMinutesInMillis());
    }

    /**
     * Milisegundos Restantes da Diferença de Minutos
     *
     * @return long
     */
    private static long remainingSecondsInMillis() {
        return remainingMinutesInMillis() - TimeUnit.MINUTES.toMillis(minutesTimeUnit());
    }

    /**
     * Segundos Restantes da Diferença de Minutos
     *
     * @return long
     */
    private static long secondsTimeUnit() {
        return TimeUnit.MILLISECONDS.toSeconds(remainingSecondsInMillis());
    }

    public static long diffSeconds(long diff) {
        return diff / 1000 % 60;
    }

    public static long diffMinutes(long diff) {
        return diff / (60 * 1000) % 60;
    }

    public static long diffHours(long diff) {
        return diff / (60 * 60 * 1000) % 24;
    }

    public static long diffDays(long diff) {
        return diff / (24 * 60 * 60 * 1000);
    }

    // Getters
    public static long getDiff() {
        return diff;
    }

    public static void setDiff(long diff) {
        Util.diff = diff;
    }

    public static long getDays() {
        return days;
    }

    public static long getHours() {
        return hours;
    }

    public static long getMinutes() {
        return minutes;
    }

    public static long getSeconds() {
        return seconds;
    }
}
