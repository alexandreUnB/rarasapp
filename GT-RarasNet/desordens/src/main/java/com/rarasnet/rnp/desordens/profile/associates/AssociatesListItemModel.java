package com.rarasnet.rnp.desordens.profile.associates;

/**
 * Created by Farina on 22/10/2015.
 */
public class AssociatesListItemModel {

    private String title;
    private String info1;
    private String info2;
    private int photoId;


    public AssociatesListItemModel(String title, String info1, String info2, int photoId) {
        this.title = title;
        this.info1 = info1;
        this.info2 = info2;
        this.photoId = photoId;
    }

    public AssociatesListItemModel(String title, String info1, String info2) {
        this.title = title;
        this.info1 = info1;
        this.info2 = info2;
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

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }
}
