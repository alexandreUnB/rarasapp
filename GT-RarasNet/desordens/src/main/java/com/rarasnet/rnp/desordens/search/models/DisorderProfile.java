package com.rarasnet.rnp.desordens.search.models;

import com.rarasnet.rnp.shared.models.Center;
import com.rarasnet.rnp.shared.models.DadosNacionais;
import com.rarasnet.rnp.shared.models.Disorder;
import com.rarasnet.rnp.shared.models.Professional;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Farina on 5/7/2015.
 */
public class DisorderProfile implements Serializable {

    private Disorder Disorder;
    private List<Reference> References;
    private List<Sign> Signs;
    private List<Synonym> Synonyms;
    private List<Professional> Professional;
    private List<Center> Center;
    private Mortalidade Mortalidade;
    private DadosNacionais dadosNacionais;
    private Cid cid;

    public DisorderProfile(Disorder disorder, List<Reference> references, List<Sign> signs,
                           List<Synonym> synonyms, List<Professional> professional,
                           List<Center> center, Mortalidade mortalidade, DadosNacionais dadosNacionais, Cid cid) {
        Disorder = disorder;
        References = references;
        Signs = signs;
        Synonyms = synonyms;
        Professional = professional;
        Center = center;
        Mortalidade = mortalidade;
        this.dadosNacionais = dadosNacionais;
        this.cid = cid;
    }

    public com.rarasnet.rnp.shared.models.Disorder getDisorder() {
        return Disorder;
    }

    public void setDisorder(com.rarasnet.rnp.shared.models.Disorder disorder) {
        Disorder = disorder;
    }

    public List<Reference> getReferences() {
        return References;
    }

    public void setReferences(List<Reference> references) {
        References = references;
    }

    public List<Sign> getSigns() {
        return Signs;
    }

    public void setSigns(List<Sign> signs) {
        Signs = signs;
    }

    public List<Synonym> getSynonyms() {
        return Synonyms;
    }

    public void setSynonyms(List<Synonym> synonyms) {
        Synonyms = synonyms;
    }

    public List<com.rarasnet.rnp.shared.models.Professional> getProfessional() {
        return Professional;
    }

    public void setProfessional(List<com.rarasnet.rnp.shared.models.Professional> professional) {
        Professional = professional;
    }

    public List<com.rarasnet.rnp.shared.models.Center> getCenter() {
        return Center;
    }

    public void setCenter(List<com.rarasnet.rnp.shared.models.Center> center) {
        Center = center;
    }

    public com.rarasnet.rnp.desordens.search.models.Mortalidade getMortalidade() {
        return Mortalidade;
    }

    public void setMortalidade(com.rarasnet.rnp.desordens.search.models.Mortalidade mortalidade) {
        Mortalidade = mortalidade;
    }

    public DadosNacionais getDadosNacionais() {
        return dadosNacionais;
    }

    public void setDadosNacionais(DadosNacionais dadosNacionais) {
        this.dadosNacionais = dadosNacionais;
    }

    public Cid getCid() {
        return cid;
    }

    public void setCid(Cid cid) {
        this.cid = cid;
    }
}
