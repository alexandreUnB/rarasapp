package com.rarasnet.rnp.desordens.search.models;

/**
 * Created by Farina on 5/7/2015.
 */
public class Synonym {

    private int id;
    private String sinonimo;
    private int desorden_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSinonimo() {
        return sinonimo;
    }

    public void setSinonimo(String sinonimo) {
        this.sinonimo = sinonimo;
    }

    public int getDesorden_id() {
        return desorden_id;
    }

    public void setDesorden_id(int desorden_id) {
        this.desorden_id = desorden_id;
    }
}
