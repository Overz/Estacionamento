package model.bo;

public class MovimentoBO {
    public static boolean validarData1(String dt1) {
        return dt1 != null && !dt1.trim().isEmpty();
    }

    public static boolean validarData2(String dt2) {
        return dt2 != null && !dt2.trim().isEmpty();
    }
}
