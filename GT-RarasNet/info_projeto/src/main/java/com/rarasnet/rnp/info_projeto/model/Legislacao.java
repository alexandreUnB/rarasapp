package com.rarasnet.rnp.info_projeto.model;

/**
 * Created by viniciusthiengo on 4/5/15.
 */
public class Legislacao {
    private String mName;
    private String legislacao;
    private String idProl;


    public Legislacao() {
    }

    public Legislacao(String mName, String legislacao, String idProl) {
        this.mName = mName;
        this.legislacao = legislacao;
        this.idProl = idProl;
    }



    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }



    public String getId() {
        return idProl;
    }

    public void setId(String id) {
        this.idProl = id;
    }

    public String getLegislacao() {
        return legislacao;
    }

    public void setLegislacao(String legislacao) {
        this.legislacao = legislacao;
    }
}
