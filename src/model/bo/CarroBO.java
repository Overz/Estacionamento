package model.bo;

import model.vo.veiculo.CarroVO;
import util.constantes.ConstHelpers;

public class CarroBO {
    public static boolean validarPlaca(CarroVO carro) {
        return carro != null
               && !carro.getPlaca().trim().isEmpty()
               && carro.getPlaca().trim().length() > 0
               && carro.getPlaca().trim().length() <= 7
               && carro.getPlaca().trim().matches(ConstHelpers.REGEX_NUMEROS_PALAVRAS);
    }

    public static boolean validarPlaca(String carro) {
        return carro != null
               && !carro.trim().isEmpty()
               && carro.trim().length() > 0
               && carro.trim().length() <= 7
               && carro.trim().matches(ConstHelpers.REGEX_NUMEROS_PALAVRAS);
    }

    public static boolean validarCor(CarroVO carro) {
        return carro != null
               && !carro.getCor().trim().isEmpty()
               && carro.getCor().trim().length() > 0
               && carro.getCor().trim().length() <= 45
               && carro.getCor().trim().matches(ConstHelpers.REGEX_PALAVRAS);
    }

    public static boolean validarDescricao(CarroVO carro) {
        return carro != null
               && !carro.getModelo().getDescricao().trim().isEmpty()
               && carro.getModelo().getDescricao().trim().length() > 0
               && carro.getModelo().getDescricao().trim().length() <= 100
               && carro.getModelo().getDescricao().trim().matches(ConstHelpers.REGEX_PALAVRAS);
    }

    public static boolean validarMarca(CarroVO car) {
        return car.getModelo().getMarca() != null || !car.getModelo().getMarca().getMarca().trim().isEmpty();
    }

    public static boolean validarModelo(CarroVO car) {
        return car.getModelo() != null || !car.getModelo().getDescricao().trim().isEmpty();
    }
}

