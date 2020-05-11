package model.seletor;

public class SeletorEndereco<EnderecoVO> implements SuperSeletor<EnderecoVO> {

    private EnderecoVO enderecoVO;

    @Override
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

    @Override
    public boolean temFiltro(EnderecoVO object) {
        return object != null;
    }

}
