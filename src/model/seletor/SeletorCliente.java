package model.seletor;

public class SeletorCliente<ClienteVO> implements SuperSeletor<ClienteVO> {

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
