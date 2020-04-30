package model.seletor;

import model.vo.cliente.ClienteVO;

public class SeletorCliente implements SuperSeletor<ClienteVO> {

    @Override
    public String criarFiltro(String string, ClienteVO object) {
        string = " WHERE ";
        return string;
    }

    @Override
    public boolean temFiltro(ClienteVO object) {
        return object != null;
    }
}
