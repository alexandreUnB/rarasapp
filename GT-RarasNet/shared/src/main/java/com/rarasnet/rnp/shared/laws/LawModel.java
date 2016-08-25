package com.rarasnet.rnp.shared.laws;

/**
 * Created by lucas on 25/08/16.
 */
public class LawModel {
    private String id;
    private String name_law;
    private String name_pdf;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName_law() {
        return name_law;
    }

    public void setName_law(String name_law) {
        this.name_law = name_law;
    }

    public String getName_pdf() {
        return name_pdf;
    }

    public void setName_pdf(String name_pdf) {
        this.name_pdf = name_pdf;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
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

    private String resume;
    private String created_at;
    private String updated_at;


}
