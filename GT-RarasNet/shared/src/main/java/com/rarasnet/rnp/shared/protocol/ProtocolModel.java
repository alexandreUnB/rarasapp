package com.rarasnet.rnp.shared.protocol;

/**
 * Created by lucas on 22/08/16.
 *
 * @brief class that contais protocols column values as
 * the DB
 */

public class ProtocolModel {
    private String id;
    private String document;

    public String getDisorder_name() {
        return disorder_name;
    }

    public void setDisorder_name(String disorder_name) {
        this.disorder_name = disorder_name;
    }

    private String disorder_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getName_pdf() {
        return name_pdf;
    }

    public void setName_pdf(String name_pdf) {
        this.name_pdf = name_pdf;
    }

    public String getDisorder_id() {
        return disorder_id;
    }

    public void setDisorder_id(String disorder_id) {
        this.disorder_id = disorder_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    private String name_pdf;
    private String disorder_id;
    private String created_at;
    private String updated_at;
}
