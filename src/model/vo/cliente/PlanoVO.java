package model.vo.cliente;

import util.helpers.Util;

public class PlanoVO {

    private int id;
    private String tipo;
    private String descircao;

    public PlanoVO(int id, String tipo, String descircao) {
        this.id = id;
        this.tipo = tipo;
        this.descircao = descircao;
    }

    public PlanoVO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescircao() {
        return descircao;
    }

    public void setDescircao(String descircao) {
        this.descircao = descircao;
    }

    public String toStringDiff() {
        return " - PLANO: " +
               " - ID: " + id +
               " - TIPO_PLANO: " + tipo +
               " - DESCRICAO: " + descircao;
    }

    @Override
    public String toString() {
        return "TIPO: " + tipo + " - " + descircao;
    }
}
