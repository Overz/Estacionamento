package model.bo;

import model.vo.veiculo.CarroVO;
import util.constantes.ConstHelpers;
import util.constantes.ConstInicio;

public class CarroBO {
    public static boolean validarPlaca(CarroVO carro) {
        try {
            if (carro != null) {
                return !carro.getPlaca().trim().isEmpty()
                       && !carro.getPlaca().equals(ConstInicio.VAZIO);
//               && !carro.getPlaca().trim().isEmpty()
//               && carro.getPlaca().trim().length() > 0
//               && carro.getPlaca().trim().length() <= 10
//               && carro.getPlaca().replace('?', ' ').trim().matches(ConstHelpers.REGEX_NUMEROS_PALAVRAS);
            }
        } catch (Exception e) {
            if (e != null) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public static boolean validarPlaca(String carro) {
        try {
            if (carro != null) {
                return !carro.trim().isEmpty()
                       && carro.trim().length() > 0
                       && carro.trim().length() <= 7
                       && carro.trim().matches(ConstHelpers.REGEX_NUMEROS_PALAVRAS);
            }
        } catch (Exception e) {
            if (e != null) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public static boolean validarCor(CarroVO carro) {
        try {
            if (carro != null) {
                return !carro.getCor().trim().isEmpty();
            }
        } catch (Exception e) {
            if (e != null) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public static boolean validarDescricao(CarroVO carro) {
        return carro != null
               && !carro.getModelo().getDescricao().trim().isEmpty()
               && carro.getModelo().getDescricao().trim().length() > 0
               && carro.getModelo().getDescricao().trim().length() <= 100
               && carro.getModelo().getDescricao().trim().matches(ConstHelpers.REGEX_PALAVRAS);
    }

    public static boolean validarMarca(CarroVO car) {
        try {
            if (car != null) {
                return car.getModelo().getMarca() != null || !car.getModelo().getMarca().getMarca().trim().isEmpty();
            }
        } catch (Exception e) {
            if (e != null) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public static boolean validarModelo(CarroVO carro) {
        try {
            if (carro != null) {
                return carro.getModelo() != null || !carro.getModelo().getDescricao().trim().isEmpty();
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

