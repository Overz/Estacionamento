package model.seletor;

import model.vo.cliente.EnderecoVO;

public class SeletorEndereco implements SuperSeletor<EnderecoVO> {

    private EnderecoVO enderecoVO;

    @Override
    public String criarFiltro(String string, EnderecoVO object) {
        string += " WHERE ";
        boolean primeiro = true;

        if (object != null) {
            if (!primeiro) {
                string += " AND ";
            }
            string += "IDENDERECO = " + object.getId();
            primeiro = false;
        }
        return string;
    }

    @Override
    public boolean temFiltro(EnderecoVO object) {
        return object != null;
    }

}
