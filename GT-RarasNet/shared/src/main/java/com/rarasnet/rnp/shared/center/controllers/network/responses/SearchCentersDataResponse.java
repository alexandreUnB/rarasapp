package com.rarasnet.rnp.shared.center.controllers.network.responses;

/**
 * Created by Farina on 21/9/2015.
 */
public class SearchCentersDataResponse {

//    private String id;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(String especialidades) {
        this.especialidades = especialidades;
    }

    private String city;
    private String uf;





    private String id;
    private String nome;
    private String cidade;
    private String estado;
    private String especialidades;

    public SearchCentersDataResponse() {
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

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEspecialidade() {
        return especialidades;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidades = especialidade;
    }
}
