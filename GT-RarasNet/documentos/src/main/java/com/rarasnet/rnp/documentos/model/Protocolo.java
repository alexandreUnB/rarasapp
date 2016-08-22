package com.rarasnet.rnp.documentos.model;

/**
 * Created by viniciusthiengo on 4/5/15.
 */
public class Protocolo {
    private String mName;
    private String orphanumber;
    private String cid;
    private String protocolo;
    private String idProl;


    public Protocolo(){}

    public Protocolo(String mName, String orphanumber, String cid, String protocolo, String id) {
        this.mName = mName;
        this.orphanumber = orphanumber;
        this.cid = cid;
        this.protocolo = protocolo;
        this.idProl = id;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getOrphanumber() {
        return orphanumber;
    }

    public void setOrphanumber(String orphanumber) {
        this.orphanumber = orphanumber;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }

    public String getId() {
        return idProl;
    }

    public void setId(String id) {
        this.idProl = id;
    }
}
