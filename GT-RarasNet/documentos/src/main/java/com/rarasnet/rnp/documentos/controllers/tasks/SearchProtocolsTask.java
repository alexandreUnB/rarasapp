package com.rarasnet.rnp.documentos.controllers.tasks;

import android.os.AsyncTask;

import com.rarasnet.rnp.documentos.controllers.activities.SearchProtocolsActivity;
import com.rarasnet.rnp.documentos.controllers.network.requests.ProtocolsRequest;
import com.rarasnet.rnp.documentos.controllers.network.responses.ProtocolDataResponse;
import com.rarasnet.rnp.shared.network.connections.RarasConnection;


/**
 * Created by Farina on 19/8/2015.
 */
public class SearchProtocolsTask extends AsyncTask<String, String, ProtocolDataResponse[]> {

    public interface SearchProtocolsCallback {
        public void onSearchProtocolsTaskCallback(ProtocolDataResponse[] response);
    }

    SearchProtocolsCallback searchProtocolsCallback;

    public SearchProtocolsTask(SearchProtocolsCallback searchProtocolsCallback) {
        this.searchProtocolsCallback = searchProtocolsCallback;
    }

    @Override
    protected ProtocolDataResponse[] doInBackground(String... params){
        String searchInput = params[0];
        String searchType = params[1];
        ProtocolsRequest protocolsRequest = new ProtocolsRequest(searchInput, searchType);
        ProtocolDataResponse[] result;

        RarasConnection requester = new RarasConnection(RarasConnection.ACTION_SEARCH_PROTOCOLS);
        requester.sendRequest(protocolsRequest, ProtocolsRequest.class);
        result = requester.getResponse(ProtocolDataResponse[].class);

        return result;
    }

    @Override
    protected void onPostExecute(ProtocolDataResponse[] protocolData){
        searchProtocolsCallback.onSearchProtocolsTaskCallback(protocolData);
    }
}
