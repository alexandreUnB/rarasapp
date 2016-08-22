package com.rarasnet.rnp.profissionais.controllers.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.rarasnet.rnp.profissionais.controllers.network.requests.SearchProfissionaisRequest;
import com.rarasnet.rnp.profissionais.controllers.network.responses.SearchProfissionaisDataResponse;
import com.rarasnet.rnp.shared.network.connections.RarasConnection;

/**
 * Created by Farina on 16/9/2015.
 *
 *   AsyncTask responsável pela comunicação com o Webservice na hora de pesquisar profissionais
 *
 */
public class SearchProfissionaisTask extends AsyncTask<String, String, SearchProfissionaisDataResponse[]> {

    public interface SearchProfissionaisCallback {
        void onSearchProfissionaisTaskCallback(SearchProfissionaisDataResponse[] response);
    }

    SearchProfissionaisCallback searchProfissionaisCallback;

    public SearchProfissionaisTask(SearchProfissionaisCallback callback) {
        this.searchProfissionaisCallback = callback;
    }

    @Override
    protected SearchProfissionaisDataResponse[] doInBackground(String... params) {

        String searchInput = params[0];
        Log.d("a5",params[0]);
        String searchType = params[1];
        Log.d("searchtiii", searchType);
        SearchProfissionaisRequest protocolsRequest = new SearchProfissionaisRequest(searchInput, searchType);
        Log.d("a6",protocolsRequest.getSearchType());

        SearchProfissionaisDataResponse[] result;

        RarasConnection requester = new RarasConnection(RarasConnection.ACTION_SEARCH_PROFESSIONALS);
        requester.sendRequest(protocolsRequest, SearchProfissionaisRequest.class);
        result = requester.getResponse(SearchProfissionaisDataResponse[].class);

        return result;
    }

    @Override
    protected void onPostExecute(SearchProfissionaisDataResponse[] response){
        searchProfissionaisCallback.onSearchProfissionaisTaskCallback(response);
    }
}
