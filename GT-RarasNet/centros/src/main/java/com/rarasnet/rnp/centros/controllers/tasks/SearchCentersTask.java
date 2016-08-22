package com.rarasnet.rnp.centros.controllers.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.rarasnet.rnp.centros.controllers.network.requests.SearchCentersRequest;
import com.rarasnet.rnp.centros.controllers.network.responses.SearchCentersDataResponse;
import com.rarasnet.rnp.shared.network.connections.RarasConnection;

/**
 * Created by Farina on 21/9/2015.
 */
public class SearchCentersTask extends AsyncTask<String, String, SearchCentersDataResponse[]> {

    public interface SearchCentersCallback {
        void onSearchCentersTaskCallback(SearchCentersDataResponse[] response);
    }

    SearchCentersCallback searchCentersCallback;

    public SearchCentersTask(SearchCentersCallback callback) {
        this.searchCentersCallback = callback;
    }

    @Override
    protected SearchCentersDataResponse[] doInBackground(String... params) {

        String searchInput = params[0];
        Log.i("SearchCentersTask : ", searchInput);
        String searchType = "nm";
        SearchCentersRequest protocolsRequest = new SearchCentersRequest(searchInput, searchType);
        SearchCentersDataResponse[] result;

        RarasConnection requester = new RarasConnection(RarasConnection.ACTION_SEARCH_CENTERS);
        requester.sendRequest(protocolsRequest, SearchCentersRequest.class);
        result = requester.getResponse(SearchCentersDataResponse[].class);

        return result;
    }

    @Override
    protected void onPostExecute(SearchCentersDataResponse[] response){
        searchCentersCallback.onSearchCentersTaskCallback(response);
    }
}