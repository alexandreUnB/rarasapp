package com.rarasnet.rnp.shared.disease.search.models;

/**
 * Created by Farina on 5/7/2015.
 */
public class Reference {

    private int id;
    private String source;
    private String reference;
    private String mapRelation;
    private String icdRelation;
    private String status;
    private int disorderId;

    public int getDisorderId() {
        return disorderId;
    }

    public void setDisorderId(int disorderId) {
        this.disorderId = disorderId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getMapRelation() {
        return mapRelation;
    }

    public void setMapRelation(String mapRelation) {
        this.mapRelation = mapRelation;
    }

    public String getIcdRelation() {
        return icdRelation;
    }

    public void setIcdRelation(String icdRelation) {
        this.icdRelation = icdRelation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
