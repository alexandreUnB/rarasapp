package com.rarasnet.rnp.shared.center.profile;

import java.util.ArrayList;

/**
 * Created by Farina on 22/10/2015.
 */
public class CenterModel {

    private com.rarasnet.rnp.shared.disease.profile.associates.AssociatesListHeaderModel associatesListHeaderModel;
    private ArrayList<com.rarasnet.rnp.shared.disease.profile.associates.AssociatesListItemModel> associatesListItemModel;

    public CenterModel(com.rarasnet.rnp.shared.disease.profile.associates.AssociatesListHeaderModel associatesListHeaderModel,
                       ArrayList<com.rarasnet.rnp.shared.disease.profile.associates.AssociatesListItemModel> associatesListItemModel) {
        this.associatesListHeaderModel = associatesListHeaderModel;
        this.associatesListItemModel = associatesListItemModel;
    }

    public com.rarasnet.rnp.shared.disease.profile.associates.AssociatesListHeaderModel getAssociatesListHeaderModel() {
        return associatesListHeaderModel;
    }

    public void setAssociatesListHeaderModel(com.rarasnet.rnp.shared.disease.profile.associates.AssociatesListHeaderModel associatesListHeaderModel) {
        this.associatesListHeaderModel = associatesListHeaderModel;
    }

    public ArrayList<com.rarasnet.rnp.shared.disease.profile.associates.AssociatesListItemModel> getAssociatesListItemModel() {
        return associatesListItemModel;
    }

    public void setAssociatesListItemModel(ArrayList<com.rarasnet.rnp.shared.disease.profile.associates.AssociatesListItemModel> associatesListItemModel) {
        this.associatesListItemModel = associatesListItemModel;
    }
}
