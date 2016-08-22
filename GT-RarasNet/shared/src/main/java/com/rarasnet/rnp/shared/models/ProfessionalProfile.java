package com.rarasnet.rnp.shared.models;

import com.google.gson.annotations.SerializedName;
import com.rarasnet.rnp.shared.disease.profile.description.Specialty;

import java.util.List;

/**
 * Created by Ronnyery Barbosa on 18/08/2015.
 */
public class ProfessionalProfile {

    private Professional professional;

    @SerializedName("DadosNacionai") private List<DadosNacionais> disorders;

    @SerializedName("Instituicao") private List<Center> centers;

    public List<Specialty> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(List<Specialty> specialties) {
        this.specialties = specialties;
    }

    private List<Specialty> specialties;

    public ProfessionalProfile(Professional professional, List<DadosNacionais> disorders,
                               List<Center> centers, List<Specialty> specialties) {
        this.professional = professional;
        this.disorders = disorders;
        this.centers = centers;
        this.specialties = specialties;
    }

    public Professional getProfessional() {
        return professional;
    }

    public void setProfessional(Professional professional) {
        this.professional = professional;
    }



    public List<DadosNacionais> getDisorders() {
        return disorders;
    }

    public void setDisorders(List<DadosNacionais> disorders) {
        this.disorders = disorders;
    }

    public List<Center> getCenters() {
        return centers;
    }

    public void setCenters(List<Center> centers) {
        this.centers = centers;
    }
}
