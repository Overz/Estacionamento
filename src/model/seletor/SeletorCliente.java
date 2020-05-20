package model.seletor;

public class SeletorCliente<ClienteVO> {

    public String criarFiltro(String string, ClienteVO object) {
        string = " WHERE ";
        return string;
    }

    public boolean temFiltro(ClienteVO object) {
        return object != null;
    }
}
