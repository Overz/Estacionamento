package model.seletor;

public class SeletorContrato<ContratoVO> implements SuperSeletor<ContratoVO> {
    @Override
    public String criarFiltro(String string, ContratoVO object) {
        return null;
    }

    @Override
    public boolean temFiltro(ContratoVO object) {
        return false;
    }
}
