package model.bo;

import java.time.LocalDate;

public class MovimentoBO {
	public static boolean validarData1(String dt1) {
		return dt1 != null && !dt1.trim().isEmpty() && !LocalDate.parse(dt1).isAfter(LocalDate.now());
	}

	public static boolean validarData2(String dt2) {
		return dt2 != null && !dt2.trim().isEmpty() && !LocalDate.parse(dt2).isAfter(LocalDate.now());
	}
}
