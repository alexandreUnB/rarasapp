package com.rarasnet.rnp.shared.disease.profile.centers;

/**
 * Created by Farina on 19/10/2015.
 */
public class AssociatedCenter {

    private int centerId;
    private int photoID;
    private String id;
    private String nome;
    private String endereco;
    private String cnes;
    private String tipo = "center";

    public AssociatedCenter() {
    }


    public AssociatedCenter(int centerId, int photoID, String nome, String endereco, String cnes,String tipo, String id) {
        this.centerId = centerId;
        this.photoID = photoID;
        this.nome = nome;
        this.endereco = endereco;
        this.cnes = cnes;
        this.tipo = tipo;
        this.id = id;
    }

    public AssociatedCenter(int photoID, String nome, String endereco, String cnes,String tipo, String id) {
        this.cnes = cnes;
        this.photoID = photoID;
        this.nome = nome;
        this.endereco = endereco;
        this.tipo = tipo;
        this.id = id;
    }

    public int getPhotoID() {
        return photoID;
    }

    public void setPhotoID(int photoID) {
        this.photoID = photoID;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCnes() {
        return cnes;
    }

    public void setCnes(String cnes) {
        this.cnes = cnes;
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
