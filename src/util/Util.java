package util;

import model.banco.BaseDAO;
import model.dao.movientos.MovimentoDAO;
import model.vo.cliente.ClienteVO;
import model.vo.cliente.ContratoVO;
import model.vo.cliente.EnderecoVO;
import model.vo.cliente.PlanoVO;
import model.vo.movimentos.MovimentoVO;
import model.vo.movimentos.TicketVO;
import model.vo.veiculo.CarroVO;
import model.vo.veiculo.MarcaVO;
import model.vo.veiculo.ModeloVO;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    /**
     * Verificar o dia atual, comparar com o movimento se o dia for diferente do atual,
     * coloca o 'atual' de movimento false
     *
     * @param lista ArrayList<MovimentoVO>();
     */
    public static boolean atualizarObjetoMovimentoAtual(ArrayList<MovimentoVO> lista, Integer vaiFazer) {
        Integer jaFez = 0;
        int quantidade = 0;

        if (!vaiFazer.equals(jaFez)) {
            BaseDAO<MovimentoVO> daoM = new MovimentoDAO();

            for (MovimentoVO movimento : lista) {
                LocalDate dtMovimento = movimento.getHr_entrada().toLocalDate();
                LocalDate now = LocalDate.now();

                if (now.compareTo(dtMovimento) > 0) {
                    movimento.setAtual(false);

                    if (daoM.alterar(movimento)) {
                        quantidade++;
                    }
                }
            }
            System.out.println("Quantidade de Movimentos Atuais Alterados(Atualizar Objeto): " + quantidade);
            return true;
        }
        return false;
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
