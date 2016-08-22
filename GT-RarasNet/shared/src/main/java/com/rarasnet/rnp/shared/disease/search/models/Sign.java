package com.rarasnet.rnp.shared.disease.search.models;

/**
 * Created by Farina on 5/7/2015.
 */
public class Sign {


    private String id;
    private String name;
    private String frequency;
    private String descricao;
    private String foto;
    private String foto_dir;
    private int length;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Sign() {

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getFoto_dir() {
        return foto_dir;
    }

    public void setFoto_dir(String foto_dir) {
        this.foto_dir = foto_dir;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
