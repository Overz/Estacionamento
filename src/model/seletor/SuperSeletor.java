package model.seletor;

public interface SuperSeletor<T> {

    /**
     * Cria o filtro de acordo com a quantidade
     * de Parametros preenchidos na classe Objeto
     *
     * @param values: String
     * @param object: T
     * @return String: String
     */
    String criarFiltro(String values, T object);

    /**
     * Verifica se os campos digitados Não estão vazios, para criar um Filtro
     *
     * @param object: T
     * @return true/false
     */
    boolean temFiltro(T object);

}
