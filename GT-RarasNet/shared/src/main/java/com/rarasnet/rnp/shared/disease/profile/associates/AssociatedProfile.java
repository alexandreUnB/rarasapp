package com.rarasnet.rnp.shared.disease.profile.associates;

import com.rarasnet.rnp.shared.disease.profile.description.Specialty;

import java.util.List;

/**
 * Created by Farina on 19/10/2015.
 */
public class AssociatedProfile {

    private int centerId;
    private int photoID;
    private String id;
    private String nome;
    private String cidade;
    private String especialidade;
    private String tipo = "profissional";

    public List<Specialty> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(List<Specialty> specialties) {
        this.specialties = specialties;
    }

    private List<Specialty> specialties;

    public AssociatedProfile() {
    }


    public AssociatedProfile(int centerId, int photoID, String nome, String cidade, String especialidade, String tipo, String id) {
        this.centerId = centerId;
        this.photoID = photoID;
        this.nome = nome;
        this.cidade = cidade;
        this.especialidade = especialidade;
        this.tipo = tipo;
        this.id = id;
    }

    public AssociatedProfile(int photoID, String nome, String cidade, String especialidade, String tipo, String id) {
        this.cidade = cidade;
        this.photoID = photoID;
        this.nome = nome;
        this.especialidade = especialidade;
        this.tipo = tipo;
        this.id = id;
    }

    public int getPhotoID() {
        return photoID;
    }

    public void setPhotoID(int photoID) {
        this.photoID = photoID;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public int getCenterId() {
        return centerId;
    }

    public void setCenterId(int centerId) {
        this.centerId = centerId;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
