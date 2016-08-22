package com.rarasnet.rnp.shared.models;

import com.rarasnet.rnp.shared.models.Center;
import com.rarasnet.rnp.shared.models.DadosNacionais;
import com.rarasnet.rnp.shared.models.Professional;

import java.util.List;

/**
 * Created by Ronnyery Barbosa on 19/08/2015.
 */
public class CenterProfile {

    private List<Professional> professional;
    private Center center;
    private List<DadosNacionais> disorders;


    public CenterProfile(List<Professional> professional, Center center, List<DadosNacionais> disorders) {
        this.professional = professional;
        this.center = center;
        this.disorders = disorders;
    }

    public CenterProfile(Center center, List<DadosNacionais> disorders) {
        this.center = center;
        this.disorders = disorders;
    }

    public List<Professional> getProfessional() {
        return professional;
    }

    public void setProfessional(List<Professional> professional) {
        this.professional = professional;
    }

    public Center getCenter() {
        return center;
    }

    public void setCenter(Center center) {
        this.center = center;
    }

    public List<DadosNacionais> getDisorders() {
        return disorders;
    }

    public void setDisorders(List<DadosNacionais> disorders) {
        this.disorders = disorders;
    }
}
