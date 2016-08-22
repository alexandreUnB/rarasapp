package com.rarasnet.rnp.profissionais.profile;

import java.util.ArrayList;

/**
 * Created by Farina on 22/10/2015.
 */
public class ProfissionaisModel {

    private AssociatesListHeaderModel associatesListHeaderModel;
    private ArrayList<AssociatesListItemModel> associatesListItemModel;

    public ProfissionaisModel(AssociatesListHeaderModel associatesListHeaderModel,
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
