package model.seletor;

public interface SuperSeletor<T> {

    /**
     * Cria o filtro de acordo com a quantidade
     * de Parametros preenchidos na classe Objeto
     *
     * @param string
     * @param object
     * @return String: String
     */
    String criarFiltro(String string, T object);

    /**
     * Verifica se os campos digitados Não estão vazios, para criar um Filtro
     *
     * @param object
     * @return true/false
     */
    boolean temFiltro(T object);

}
