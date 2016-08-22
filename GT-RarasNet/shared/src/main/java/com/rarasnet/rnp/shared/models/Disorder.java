package com.rarasnet.rnp.shared.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Farina on 4/5/2015.
 */
public class Disorder {


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDrugs() {
        return drugs;
    }


    public void setReferences(String references) {
        this.references = references;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    //    private String id;
//    private String name;
//    private String orphanumber;
    public void setDrugs(String drugs) {
        this.drugs = drugs;
    }

    public String getProcedures() {
        return procedures;
    }

    public void setProcedures(String procedures) {
        this.procedures = procedures;
    }

    public String getReferences() {
        return references;
    }
    private String description;
    private String drugs;
    private String procedures;
    private String references;
//    private String tipo = "doença";

    // CODIGO LEGADO
    @SerializedName("desorden_id") private String id;
    @SerializedName("id") private String desorden_id;
    private String name;
    private String orphanumber;
    private String expertlink;
    private String disorderType;
    private String descricao;
    private String bibliografia;
    private String tipo = "doença";
    //String tipo = "doença";

    public Disorder() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrphanumber() {
        return orphanumber;
    }

    public void setOrphanumber(String orphanumber) {
        this.orphanumber = orphanumber;
    }

    public String getExpertlink() {
        return expertlink;
    }

    public void setExpertlink(String expertlink) {
        this.expertlink = expertlink;
    }

    public String getDisorderType() {
        return disorderType;
    }

    public void setDisorderType(String disorderType) {
        this.disorderType = disorderType;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getBibliografia() {
        return bibliografia;
    }

    public void setBibliografia(String bibliografia) {
        this.bibliografia = bibliografia;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDesorden_id() {
        return desorden_id;
    }

    public void setDesorden_id(String desorden_id) {
        this.desorden_id = desorden_id;
    }
}
