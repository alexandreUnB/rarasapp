package com.rarasnet.rnp.shared.disease.search.models;

import android.util.Log;

import com.rarasnet.rnp.shared.models.Center;
import com.rarasnet.rnp.shared.models.DadosNacionais;
import com.rarasnet.rnp.shared.models.Disorder;
import com.rarasnet.rnp.shared.models.Indicator;
import com.rarasnet.rnp.shared.models.Professional;
import com.rarasnet.rnp.shared.disease.profile.description.Specialty;
import com.rarasnet.rnp.shared.protocol.ProtocolModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by Farina on 5/7/2015.
 */
public class DisorderProfile implements Serializable {

    private Disorder Disorder;
    private List<Specialty> Specialties;
    private List<Reference> References;
    private List<Sign> Signs;
    private List<Synonym> Synonyms;
    private List<Professional> Professional;
    private List<Center> Center;

    public ProtocolModel getProtocol() {
        return protocol;
    }

    public void setProtocol(ProtocolModel protocol) {
        this.protocol = protocol;
    }

    private ProtocolModel protocol;
    public Hashtable<String, Hashtable<String, String>> unionIndicators;

    public List<Indicator> getIndicators() {
        return Indicators;
    }

    public void setIndicators(List<Indicator> indicators) {
        Indicators = indicators;
    }

    private List<Indicator> Indicators;
    private Mortalidade Mortalidade;
    private DadosNacionais dadosNacionais;
    private Cid cid;

    public int getSignCounter() {
        return signCounter;
    }

    public void setSignCounter(int signCounter) {
        this.signCounter = signCounter;
    }

    private int signCounter;

    public DisorderProfile(Disorder disorder,  List<Specialty> specialties, List<Reference> references, List<Sign> signs,
                           List<Synonym> synonyms, List<Professional> professional,
                           List<Center> center, Mortalidade mortalidade, DadosNacionais dadosNacionais,
                           List<Indicator> indicators, ProtocolModel protocol, Cid cid) {


        Disorder = disorder;
        Specialties = specialties;
        References = references;
        Signs = signs;
        Synonyms = synonyms;
        Professional = professional;
        Center = center;
        Mortalidade = mortalidade;
        this.dadosNacionais = dadosNacionais;
        this.cid = cid;
        Indicators = indicators;
        this.protocol = protocol;

        // union of indicators with same name
        unionIndicators = new Hashtable<String,  Hashtable<String, String>>();


        for (Indicator i: indicators) {
            Hashtable<String, String> aux = new Hashtable<String, String>();

            // if we already have the indicator we just append to its values
            if(unionIndicators.containsKey(i.getNameIndicatorType() + " " + i.getGetNameIndicatorSource())){
                aux = unionIndicators.get(i.getNameIndicatorType() + " " +
                        i.getGetNameIndicatorSource());
                aux.put(i.getYear(),i.getAmount());
                unionIndicators.put(i.getNameIndicatorType() + " " + i.getGetNameIndicatorSource(),
                        aux);
            }else{
                aux.put(i.getYear(),i.getAmount());
                unionIndicators.put(i.getNameIndicatorType() + " " + i.getGetNameIndicatorSource(),
                        aux);
            }

        }


//        for (String key: unionIndicators.keySet()) {
//            ArrayList<Hashtable<String, String>>indicatorInfoPrint = unionIndicators.get(key);
//            String printar = "";
//            for (Hashtable<String, String> info: indicatorInfoPrint) {
//                for (String year:info.keySet()) {
//                    Log.d("AQUI["+key+"]", year + " " + info.get(year));
//
//                }
//            }
//        }

    }

    public com.rarasnet.rnp.shared.models.Disorder getDisorder() {
        return Disorder;
    }

    public void setDisorder(com.rarasnet.rnp.shared.models.Disorder disorder) {
        Disorder = disorder;
    }

    public List<Specialty> getSpecialties() {
        return Specialties;
    }

    public void setSpecialties(List<Specialty> specialties) {
        Specialties = specialties;
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

    public Mortalidade getMortalidade() {
        return Mortalidade;
    }

    public void setMortalidade(Mortalidade mortalidade) {
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
