package com.rarasnet.rnp.profissionais.controllers.network.requests;

/**
 * Created by Farina on 16/9/2015.
 */
public class SearchProfissionaisRequest {

    private String searchInput;
     private String searchType;

    public SearchProfissionaisRequest() {

    }

    public SearchProfissionaisRequest(String searchInput, String searchType) {
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
