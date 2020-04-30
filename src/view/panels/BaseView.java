package view.panels;

/**
 * Base para criação dos métodos das Interfaces de Usuario(GUI)
 */
public interface BaseView {

    /**
     * Inicia Todos os Métodos
     */
    void initialize();

    /**
     * Adiciona a tela os componentes de Exibição Estaticos, JLabel e etc
     */
    void setJLabels_JSeparator();

    /**
     * Adiciona a tela os campos de Entrada de Texto
     */
    void setInputFields();

    /**
     * Adiciona a tela os Botões para Utilização
     */
    void setButtons();

    /**
     * Adiciona a tela uma Tabela e suas Configurações
     */
    void setJTable();

}
