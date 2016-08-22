package com.rarasnet.rnp.shared.models;

/**
 * Created by Ronnyery Barbosa on 18/08/2015.
 */
public class DadosNacionais {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDrugs() {
        return drugs;
    }

    public void setDrugs(String drugs) {
        this.drugs = drugs;
    }

    public String getProcedures() {
        return procedures;
    }

    public void setProcedures(String procedures) {
        this.procedures = procedures;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    //private String id;
    private String name;
    //private String orphanumber;
    private String description;
    private String drugs;
    private String procedures;
    //private String tipo = "doença";


    // CODIGO LEGADO
    private String id;
    private String desorden_id;
    private String doenca;
    private String orphanumber;
    private String protocolo;
    private String protocolo_dir;
    private String tipo = "doença";


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesorden_id() {
        return desorden_id;
    }

    public void setDesorden_id(String desorden_id) {
        this.desorden_id = desorden_id;
    }

    public String getDoenca() {
        return doenca;
    }

    public void setDoenca(String doenca) {
        this.doenca = doenca;
    }

    public String getOrphanumber() {
        return orphanumber;
    }

    public void setOrphanumber(String orphanumber) {
        this.orphanumber = orphanumber;
    }

    public String getProtocolo() {
        return protocolo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }

    public String getProtocolo_dir() {
        return protocolo_dir;
    }

    public void setProtocolo_dir(String protocolo_dir) {
        this.protocolo_dir = protocolo_dir;
    }
}
