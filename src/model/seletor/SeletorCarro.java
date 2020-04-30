package model.seletor;

import model.vo.veiculo.CarroVO;

public class SeletorCarro implements SuperSeletor<CarroVO> {
    @Override
    public String criarFiltro(String string, CarroVO object) {
        return null;
    }

    @Override
    public boolean temFiltro(CarroVO object) {
        return false;
    }
}
