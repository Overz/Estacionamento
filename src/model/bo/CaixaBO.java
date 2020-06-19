package model.bo;

public class CaixaBO {
    public static boolean validarValorDigitado(Double value) {
        try {
            if (value != null) {
                return value > 0.0 && !value.isNaN();
            }
        } catch (Exception e) {
            if (e != null) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
}
