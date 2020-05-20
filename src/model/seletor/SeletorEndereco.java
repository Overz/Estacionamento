package model.seletor;

public class SeletorEndereco<EnderecoVO> {

    private EnderecoVO enderecoVO;

    public String criarFiltro(String string, EnderecoVO object) {
        string += " WHERE ";
        boolean primeiro = true;

        if (object != null) {
            if (!primeiro) {
                string += " AND ";
            }
//            string += "IDENDERECO = " + this.enderecoVO.get;
            primeiro = false;
        }
        return string;
    }

    public boolean temFiltro(EnderecoVO object) {
        return object != null;
    }

}
