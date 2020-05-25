package util;

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
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Util {

    public static void ajustarTabelaNull(MovimentoVO movimento) {
        if (movimento.getPlano() == null) {

            MarcaVO marcaVO = new MarcaVO(0, "");
            ModeloVO modeloVO = new ModeloVO(0, "", marcaVO);
            EnderecoVO enderecoVO = new EnderecoVO(0, 0, "", "", "");
            CarroVO carroVO = new CarroVO(0, "", "", modeloVO);
            ClienteVO clienteVO = new ClienteVO(0, "", "", "", "", "", enderecoVO, carroVO);
            ContratoVO contratoVO = new ContratoVO(0, 0, LocalDateTime.now(), LocalDateTime.now(), false, 0.0);
            PlanoVO planoVO = new PlanoVO(0, "", "", clienteVO, contratoVO);
            movimento.setPlano(planoVO);
            if (movimento.getHr_entrada() == null) {
                if (movimento.getTicket().getDataEntrada() != null) {
                    movimento.setHr_entrada(movimento.getTicket().getDataValidacao());
                }
            }
        }

        if (movimento.getTicket() == null) {
            TicketVO ticket = new TicketVO(0, 0, 0.0, "",
                    LocalDateTime.now(), LocalDateTime.now(), false, false);
            movimento.setTicket(ticket);
        }

    }

    public static String formatarValor(double value) {
        Locale locale = Locale.getDefault(Locale.Category.FORMAT);
        NumberFormat formatter = NumberFormat.getInstance(locale);
        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);
        return formatter.format(value);
    }


    // Os Calculos Abaixo foram feitos de DUAS MANEIRAS, TimeUnit, e Representações a 'mão'
    // Ambos estão iguais

    /**
     * Diferença de Dias ate Agora
     *
     * @param diff long
     * @return long
     */
    public static long diffDaysTimeUnit(long diff) {
        return TimeUnit.MILLISECONDS.toDays(diff);
    }

    /**
     * Milisegundos Restantes da Diferença de Dias
     *
     * @param days long
     * @param diff long
     * @return long
     */
    public static long remainingHoursInMillis(long days, long diff) {
        return diff - TimeUnit.DAYS.toMillis(diff);
    }

    /**
     * Horas Restantes da Diferença de Dias
     *
     * @param remainingHoursInMillis long
     * @return long
     */
    public static long diffHouersTimeUnir(long remainingHoursInMillis) {
        return TimeUnit.MILLISECONDS.toHours(remainingHoursInMillis);
    }

    /**
     * Milsegundos Restantes da Diferença de Horas
     *
     * @param remainingHoursInMillis long
     * @param hours                  long
     * @return long
     */
    public static long remainingMinutesInMillis(long remainingHoursInMillis, long hours) {
        return remainingHoursInMillis - TimeUnit.HOURS.toMillis(hours);
    }

    /**
     * Minutos Restantes da Diferença de Horas
     *
     * @param remainingMinutesInMillis long
     * @return long
     */
    public static long diffMinutesTimeUnit(long remainingMinutesInMillis) {
        return TimeUnit.MILLISECONDS.toMillis(remainingMinutesInMillis);
    }

    /**
     * Milisegundos Restantes da Diferença de Minutos
     *
     * @param remainingMinutesInMillis long
     * @param minutes                  long
     * @return long
     */
    public static long remainingSecondsInMillis(long remainingMinutesInMillis, long minutes) {
        return remainingMinutesInMillis - TimeUnit.MINUTES.toMillis(minutes);
    }

    /**
     * Segundos Restantes da Diferença de Minutos
     *
     * @param remainingSecondsInMillis long
     * @return long
     */
    public static long diffSecondsTimeUnir(long remainingSecondsInMillis) {
        return TimeUnit.MILLISECONDS.toSeconds(remainingSecondsInMillis);
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

}
