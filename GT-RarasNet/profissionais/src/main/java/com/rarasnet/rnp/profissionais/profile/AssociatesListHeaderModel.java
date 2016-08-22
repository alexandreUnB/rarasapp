package com.rarasnet.rnp.profissionais.profile;

/**
 * Created by Farina on 22/10/2015.
 */
public class AssociatesListHeaderModel {

    private String label;
    private int iconId;

    public AssociatesListHeaderModel(String label, int iconId) {
        this.label = label;
        this.iconId = iconId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }
}
