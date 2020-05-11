package model.seletor;

public class SeletorCarro<CarroVO> implements SuperSeletor<CarroVO> {
    @Override
    public String criarFiltro(String string, CarroVO object) {
        return null;
    }

    @Override
    public boolean temFiltro(CarroVO object) {
        return false;
    }
}
