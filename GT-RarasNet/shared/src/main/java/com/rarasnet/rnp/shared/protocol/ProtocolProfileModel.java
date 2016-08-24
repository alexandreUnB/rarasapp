package com.rarasnet.rnp.shared.protocol;

import android.util.Log;

import com.google.gson.Gson;
import com.rarasnet.rnp.shared.application.RarasNet;
import com.rarasnet.rnp.shared.profissionais.controllers.network.responses.ServiceHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucas on 22/08/16.
 */
public class ProtocolProfileModel {
    private String url = RarasNet.urlPrefix + "/api/protocolID/";
    private String urlName = RarasNet.urlPrefix + "/api/protocolName/";


    Gson gson = new Gson();
    public ProtocolProfileModel() {

    }

    public List<String> autoComplete(String disorderName){
        String searchURL = urlName + disorderName;
        List<ProtocolModel> protocols = new ArrayList<>();
        String jsonResult;
        List<String> suggestions = new ArrayList<>();

        try {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            // Making a request to url and getting response
            jsonResult = sh.makeServiceCall(searchURL, ServiceHandler.GET);

            // In case we get a response, the json info received is parsed
            // and passed to and equivalent object
            Log.d("[ProPModel]JSON", jsonResult);
            if(jsonResult != null){
                protocols = getProtocols(jsonResult);
            }

            for (ProtocolModel p : protocols) {
                suggestions.add(p.getDocument());
            }

        } catch (Exception e) {

            Log.e("[ProtModel]GET", "error " + e);
        }

        return suggestions;

    }

    public List<ProtocolModel> getProtocolList(String disorderName){
        String searchURL = urlName + disorderName;
        List<ProtocolModel> protocols = new ArrayList<>();
        String jsonResult;

        try {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            // Making a request to url and getting response
            jsonResult = sh.makeServiceCall(searchURL, ServiceHandler.GET);

            // In case we get a response, the json info received is parsed
            // and passed to and equivalent object
            Log.d("[ProPModel]JSON", jsonResult);
            if(jsonResult != null){
                protocols = getProtocols(jsonResult);
            }



        } catch (Exception e) {

            Log.e("[ProtModel]GET", "error " + e);
        }

        return protocols;

    }

    /**
     * Parses Json response from server
     * to get the professional information
     * @param jString
     * @return
     */
    private List<ProtocolModel> getProtocols(String jString) {

        List<ProtocolModel> protocols = new ArrayList<>();
        Gson g = new Gson();

        try {
            JSONObject ob = new JSONObject(jString);
            JSONArray jArray = ob.getJSONArray("protocols");

            int i = 0;
            while (!jArray.isNull(i)) {
                String stringSynonym = jArray.getString(i);
                ProtocolModel dados = g.fromJson(stringSynonym, ProtocolModel.class);
                protocols.add(dados);
                i++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return protocols;
    }


    public ProtocolProfile getProfile(String disorderName){
        String searchURL = urlName + disorderName;
        List<ProtocolModel> protocols = new ArrayList<>();
        String jsonResult;
        List<String> suggestions = new ArrayList<>();
        ProtocolProfile profile;

        try {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            // Making a request to url and getting response
            jsonResult = sh.makeServiceCall(searchURL, ServiceHandler.GET);

            // In case we get a response, the json info received is parsed
            // and passed to and equivalent object
            Log.d("[ProPModel]JSON", jsonResult);
            if(jsonResult != null){
                protocols = getProtocols(jsonResult);
            }


        } catch (Exception e) {

            Log.e("[ProtModel]GET", "error " + e);
        }

        profile = new ProtocolProfile(protocols);

        return profile;

    }

}
