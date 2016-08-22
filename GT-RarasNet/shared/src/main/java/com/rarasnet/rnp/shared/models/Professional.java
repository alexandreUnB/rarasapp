package com.rarasnet.rnp.shared.models;

import com.rarasnet.rnp.shared.disease.profile.description.Specialty;

import java.util.List;

/**
 * Created by Ronnyery Barbosa on 12/08/2015.
 */
public class Professional  {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getCouncil_number() {
        return council_number;
    }

    public void setCouncil_number(String council_number) {
        this.council_number = council_number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    //    private String id;
    private String name;
    private String active;
    private String council_number;
    private String city;
    private String uf;
//    private String email;
    private String profession;
    private String telephone;
    private String ddd;

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    private String surname;

    public List<Specialty> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(List<Specialty> specialties) {
        this.specialties = specialties;
    }

    private List<Specialty> specialties;
//    private String facebook;

    // CODIGO LEGADO
    String id;
    String nome;
    String sobrenome;
    String data_nascimento;
    String sexo;
    String cidade;
    String email;
    String estado;
    String cep;
    String profissao;
    String telefone;
    String facebook;
    String twitter;
    String instituico_id;
    String crm;
    String tipo = "profi";

    public Professional() {

    }

    public Professional(String id, String nome, String sobrenome, String data_nascimento, String sexo,
                        String cidade, String email, String estado, String cep, String profissao,
                        String telefone, String facebook, String twitter, String instituico_id, String tipo) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.data_nascimento = data_nascimento;
        this.sexo = sexo;
        this.cidade = cidade;
        this.email = email;
        this.estado = estado;
        this.cep = cep;
        this.profissao = profissao;
        this.telefone = telefone;
        this.facebook = facebook;
        this.twitter = twitter;
        this.instituico_id = instituico_id;
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getData_nascimento() {
        return data_nascimento;
    }

    public void setData_nascimento(String data_nascimento) {
        this.data_nascimento = data_nascimento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getInstituico_id() {
        return instituico_id;
    }


    public void setInstituico_id(String instituico_id) {
        this.instituico_id = instituico_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }
}
