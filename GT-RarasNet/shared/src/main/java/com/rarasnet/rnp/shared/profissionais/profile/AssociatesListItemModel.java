package com.rarasnet.rnp.shared.profissionais.profile;

/**
 * Created by Farina on 22/10/2015.
 */
public class AssociatesListItemModel {

    private String title;
    private String info1;
    private String info2;
    private String tipo;
    private String id;
    private int photoId;


    public AssociatesListItemModel(String title, String info1, String info2, String tipo, int photoId) {
        this.title = title;
        this.info1 = info1;
        this.info2 = info2;
        this.tipo  = tipo;
        this.photoId = photoId;
    }

    public AssociatesListItemModel(String title, String info1, String info2, String id, String tipo) {
        this.title = title;
        this.info1 = info1;
        this.info2 = info2;
        this.id = id;
        this.tipo = tipo;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo1() {
        return info1;
    }

    public void setInfo1(String info1) {
        this.info1 = info1;
    }

    public String getInfo2() {
        return info2;
    }

    public void setInfo2(String info2) {
        this.info2 = info2;
    }

    public String getTipo() {
        return tipo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }
}
