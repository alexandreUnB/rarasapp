package com.rarasnet.rnp.shared.profissionais.controllers.network.responses;

/**
 * Created by lucas on 08/08/16.
 */
public class LaravelSearchProfissionaisDataResponse
{

    private String id;
    private String name;
    private String active;
    private String council_number;
    private String city;
    private String uf;
    private String email;
    private String profession;
    private String telephone;
    private String ddd;
    private String facebook;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    private String count;

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    private String surname;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public void setCouncil_number(String council_number) {
        this.council_number = council_number;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getActive() {
        return active;
    }

    public String getCouncil_number() {
        return council_number;
    }

    public String getCity() {
        return city;
    }

    public String getUf() {
        return uf;
    }

    public String getEmail() {
        return email;
    }

    public String getProfession() {
        return profession;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getDdd() {
        return ddd;
    }

    public String getFacebook() {
        return facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    private String twitter;
    private String created_at;
    private String updated_at;

}
