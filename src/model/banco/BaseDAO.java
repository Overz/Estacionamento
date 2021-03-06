package model.banco;

import java.util.ArrayList;

public interface BaseDAO<T> {

    /**
     * Método para consultar tudo o que há no db;
     *
     * @return ArrayList<?>
     */
    ArrayList<T> consultarTodos();

    /**
     * @param values Array String
     * @param <T>    Valor Indefinido
     * @return Retorno Indefinido
     */
    <T> T consultar(String... values);

    /**
     * Consulta algo especifico através de um id;
     *
     * @param id: int
     * @return object
     */
    T consultarPorId(int id);

    /**
     * Método para cadastrar;
     *
     * @param newObject: Object
     * @return newObject
     */
    T cadastrar(T newObject, String... values);

    /**
     * Método para altera/atualizar;
     *
     * @param object: Object
     * @return object
     */
    boolean alterar(T object);

    /**
     * Método para excluir;
     *
     * @param id: Int array
     * @return true/false
     */
    boolean excluirPorID(int id);

}
