package model.bo;

public class CaixaBO {
    public static boolean validarValorDigitado(Double value) {
        return value != null && value > 0.0 && !value.isNaN() ;
    }
}
