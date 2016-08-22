package com.rarasnet.rnp.shared.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ronnyery Barbosa on 15/02/2016.
 */
public class ProfControl {

    @SerializedName("Profissionai") private Professional searchProfissionaisDataResponse;
    @SerializedName("DadosNacionai") private List<DadosNacionais> dadosNacionaises;
    @SerializedName("Instituicao") private List<Center> centers;

    public ProfControl() {
    }

    public Professional getSearchProfissionaisDataResponse() {
        return searchProfissionaisDataResponse;
    }

    public void setSearchProfissionaisDataResponse(Professional searchProfissionaisDataResponse) {
        this.searchProfissionaisDataResponse = searchProfissionaisDataResponse;
    }

    public List<DadosNacionais> getDadosNacionaises() {
        return dadosNacionaises;
    }

    public void setDadosNacionaises(List<DadosNacionais> dadosNacionaises) {
        this.dadosNacionaises = dadosNacionaises;
    }

    public List<Center> getCenters() {
        return centers;
    }

    public void setCenters(List<Center> centers) {
        this.centers = centers;
    }
}
