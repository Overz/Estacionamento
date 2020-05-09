package model.bo;

public class CaixaBO {

    public static boolean validarAddValor(Double value) {
        return value != null && value > 0.0 && !value.isNaN();
    }

    public static boolean validarRemoverValor(Double... values) {
        boolean a = false;
        for (Double value : values) {
            a = value != null && value > 0.0 && !value.isNaN();
        }
        return a;
    }
}
