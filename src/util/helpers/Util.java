package util.helpers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Util {

    private static long diff;
    private static long days;
    private static long hours;
    private static long minutes;
    private static long seconds;

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

    public static int abrirJFileChooser(JPanel panel, JFileChooser jFileChooser) {
        String userName = System.getProperty("user.home");
        File dir = new File(userName + "/Desktop");

        jFileChooser.setCurrentDirectory(dir);
        jFileChooser.setDialogTitle("Salvar em...");
        return jFileChooser.showSaveDialog(panel);
    }

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
    // Setters
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
