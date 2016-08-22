package com.rarasnet.rnp.documentos.controllers.network.requests;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Farina on 19/8/2015.
 */
public class ProtocolsRequest {

    @SerializedName("si") private String searchInput;
    @SerializedName("st") private String searchType;

    public ProtocolsRequest() {

    }

    public ProtocolsRequest(String searchInput, String searchType) {
        this.searchInput = searchInput;
        this.searchType = searchType;
    }

    public String getSearchInput() {
        return searchInput;
    }

    public void setSearchInput(String searchInput) {
        this.searchInput = searchInput;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }
}
