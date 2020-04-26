package model.banco;

import model.seletor.SuperSeletor;

import java.util.ArrayList;

public interface BaseDAO<T> {
	
	/**
	 * Método para consultar tudo o que há no db;
	 * @return ArrayList<?>
	 */
	ArrayList<?> consultarTodos();
	
	/**
	 * Consulta algo especifico através de uma string;
	 * @param seletor: Seletor
	 * @return ArrayList<?>
	 */
    ArrayList<?> consultar(SuperSeletor<T> seletor);

	/**
	 * Consulta algo especifico através de um id;
	 * @param id: int
	 * @return object
	 */
    T consultarPorId(int id);

	/**
	 * Método para cadastrar;
	 * @param object: Object
	 * @return object
	 */
    T cadastrar(T object);
	
	/**
	 * Método para altera/atualizar;
	 * @param object: Object
	 * @return object
	 */
    boolean alterar(T object);

	/**
	 * Método para excluir;
	 * @param id: Int array
	 * @return true/false
	 */
    boolean excluir(int[] id);

}
