package model.seletor;

import model.vo.cliente.ClienteVO;

public class SeletorCliente {

    public String criarFiltro(String string, ClienteVO object) {
        string = " WHERE ";
        return string;
    }

    public boolean temFiltro(ClienteVO object) {
        return object != null;
    }
}
