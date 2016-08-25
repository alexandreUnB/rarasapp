package com.rarasnet.rnp.shared.laws;

/**
 * Created by lucas on 25/08/16.
 */

import android.util.Log;

import com.google.gson.Gson;
import com.rarasnet.rnp.shared.application.RarasNet;
import com.rarasnet.rnp.shared.profissionais.controllers.network.responses.ServiceHandler;
import com.rarasnet.rnp.shared.protocol.ProtocolModel;
import com.rarasnet.rnp.shared.protocol.ProtocolProfile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


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
public class LawProfileModel {
    private String url = RarasNet.urlPrefix + "/api/lawID/";
    private String urlName = RarasNet.urlPrefix + "/api/lawName/";


    Gson gson = new Gson();
    public LawProfileModel() {

    }

    public List<String> autoComplete(String disorderName){
        String searchURL = urlName + disorderName;
        List<LawModel> protocols = new ArrayList<>();
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
                protocols = getLaws(jsonResult);
            }

            for (LawModel p : protocols) {
                suggestions.add(p.getName_law());
            }

        } catch (Exception e) {

            Log.e("[ProtModel]GET", "error " + e);
        }

        return suggestions;

    }

    public List<LawModel> getLawList(String disorderName){
        String searchURL = urlName + disorderName.replace(" ", "%20");
        List<LawModel> protocols = new ArrayList<>();
        String jsonResult;

        try {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            Log.d("[ProPModel]JSON", searchURL);

            // Making a request to url and getting response
            jsonResult = sh.makeServiceCall(searchURL, ServiceHandler.GET);

            // In case we get a response, the json info received is parsed
            // and passed to and equivalent object
            Log.d("[ProPModel]JSON", jsonResult);
            if(jsonResult != null){
                protocols = getLaws(jsonResult);
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
    private List<LawModel> getLaws(String jString) {

        List<LawModel> laws = new ArrayList<>();
        Gson g = new Gson();

        try {
            JSONObject ob = new JSONObject(jString);
            JSONArray jArray = ob.getJSONArray("laws");

            int i = 0;
            while (!jArray.isNull(i)) {
                String stringSynonym = jArray.getString(i);
                LawModel dados = g.fromJson(stringSynonym, LawModel.class);
                laws.add(dados);
                i++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return laws;
    }


    public LawProfile getProfile(String disorderName){
        String searchURL = urlName + disorderName;
        List<LawModel> laws = new ArrayList<>();
        String jsonResult;
        List<String> suggestions = new ArrayList<>();
        LawProfile profile;

        try {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            // Making a request to url and getting response
            jsonResult = sh.makeServiceCall(searchURL, ServiceHandler.GET);

            // In case we get a response, the json info received is parsed
            // and passed to and equivalent object
            Log.d("[ProPModel]JSON", jsonResult);
            if(jsonResult != null){
                laws = getLaws(jsonResult);
            }


        } catch (Exception e) {

            Log.e("[ProtModel]GET", "error " + e);
        }

        profile = new LawProfile(laws);

        return profile;

    }

}
