package model.seletor;

import model.vo.cliente.ContratoVO;

public class SeletorContrato implements SuperSeletor<ContratoVO> {
    @Override
    public String criarFiltro(String string, ContratoVO object) {
        return null;
    }

    @Override
    public boolean temFiltro(ContratoVO object) {
        return false;
    }
}
