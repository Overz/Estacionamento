package model.banco;

import model.seletor.SuperSeletor;

import java.util.ArrayList;

public interface BaseDAO<T> {

    /**
     * Método para consultar tudo o que há no db;
     *
     * @return ArrayList<?>
     */
    ArrayList<T> consultarTodos();

    /**
     * Consulta algo especifico através de uma string;
     *
     * @param seletor: Seletor
     * @return ArrayList<?>
     */
    ArrayList<T> consultar(SuperSeletor<T> seletor);

    <T> T consultarObjeto(String... values);

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
    T cadastrar(T newObject);

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
