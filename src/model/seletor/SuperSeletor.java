package model.seletor;

public interface SuperSeletor<T> {

    String criarFiltro(String string, T object);

    boolean temFiltro(T object);



}
