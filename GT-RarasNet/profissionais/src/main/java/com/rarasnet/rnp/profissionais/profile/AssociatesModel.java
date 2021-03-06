package com.rarasnet.rnp.profissionais.profile;

import com.rarasnet.rnp.desordens.profile.associates.AssociatesListHeaderModel;
import com.rarasnet.rnp.desordens.profile.associates.AssociatesListItemModel;

import java.util.ArrayList;

/**
 * Created by Farina on 22/10/2015.
 */
public class AssociatesModel {

    private AssociatesListHeaderModel associatesListHeaderModel;
    private ArrayList<AssociatesListItemModel> associatesListItemModel;

    public AssociatesModel(AssociatesListHeaderModel associatesListHeaderModel,
                           ArrayList<AssociatesListItemModel> associatesListItemModel) {
        this.associatesListHeaderModel = associatesListHeaderModel;
        this.associatesListItemModel = associatesListItemModel;
    }

    public AssociatesListHeaderModel getAssociatesListHeaderModel() {
        return associatesListHeaderModel;
    }

    public void setAssociatesListHeaderModel(AssociatesListHeaderModel associatesListHeaderModel) {
        this.associatesListHeaderModel = associatesListHeaderModel;
    }

    public ArrayList<AssociatesListItemModel> getAssociatesListItemModel() {
        return associatesListItemModel;
    }

    public void setAssociatesListItemModel(ArrayList<AssociatesListItemModel> associatesListItemModel) {
        this.associatesListItemModel = associatesListItemModel;
    }
}
