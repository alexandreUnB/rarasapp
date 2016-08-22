package com.rarasnet.rnp.shared.center.controllers.network.requests;

/**
 * Created by Farina on 21/9/2015.
 */
public class SearchCentersRequest {

    private String searchInput;
  private String searchType;

    public SearchCentersRequest() {

    }

    public SearchCentersRequest(String searchInput, String searchType) {
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
