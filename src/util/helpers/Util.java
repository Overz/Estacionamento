package util.helpers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Util {

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
     * Evento de Click, caso pressionado um pré-requisito na tela
     * realiza ações de Enable(True)
     *
     * @param table   JTable
     * @param button  JButton
     * @param color   String
     * @param tipoCor int
     */
    public synchronized static void habilitarOpcoes(JTable table, JButton button, String color, int tipoCor) {
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
                    Timer timer = new Timer(30000, event);
                    timer.start();
                }
            }
        });
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
                                     Color mainColor, Color restColors, JButton... btns) throws NoSuchMethodException {
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
            System.out.println(e.getClass().getMethod("mudarCorBotao", Util.class, Color.class, String.class, Array.class));
            e.printStackTrace();
        }
    }

    /**
     * Abre um JFileChooser com um Caminho Pré-Definido /Home/ ou /Desktop
     *
     * @param panel        Panel
     * @param jFileChooser JFileChooser
     * @return int - SaveDialog(...-1,0,1...)
     */
    public static int abrirJFileChooser(JPanel panel, JFileChooser jFileChooser) {
        String userName = System.getProperty("user.home");
        File dir = new File(userName + "/Desktop");

        jFileChooser.setCurrentDirectory(dir);
        jFileChooser.setDialogTitle("Salvar em...");
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
