package com.rarasnet.rnp.desordens.profile.signs;

/**
 * Created by Farina on 23/10/2015.
 */
public class DisorderSign {

    private int photoId;
    private String nome;
    private String frequencia;
    private String descricao;


    public DisorderSign(String nome, String frequencia, String descricao) {
        this.nome = nome;
        this.frequencia = frequencia;
        this.descricao = descricao;
    }

    public DisorderSign(int photoId, String nome, String frequencia, String descricao) {

        this.photoId = photoId;
        this.nome = nome;
        this.frequencia = frequencia;
        this.descricao = descricao;
    }

    public int getPhotoId() {

        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(String frequencia) {
        this.frequencia = frequencia;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
