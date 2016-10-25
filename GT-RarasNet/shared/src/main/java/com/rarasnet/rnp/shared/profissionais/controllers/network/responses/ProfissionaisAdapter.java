package com.rarasnet.rnp.shared.profissionais.controllers.network.responses;

import android.util.Log;

import com.google.gson.Gson;
import com.rarasnet.rnp.shared.application.RarasNet;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ronnyery Barbosa on 04/02/2016.
 * Adapted by Lucas Vieira on 09/08/2016.
 *
 * This Module is responsible for handling the request of professional`s info
 * made by the app and sent to the Laravel API
 *
 */

public class ProfissionaisAdapter {
    /* Laravel api url used to get request response */
    private String nameURL = RarasNet.urlPrefix + "/api/professionalName/";
    private String disorderURL = RarasNet.urlPrefix + "/api/profDisorder/";
    private String specialtyURL = RarasNet.urlPrefix + "/api/professionalSpecialty/";
    private String localURL = RarasNet.urlPrefix + "/api/professionalLocal/";
    private String ufURL = RarasNet.urlPrefix + "/api/professionalUF/";

    /**
    * Method that is called after Professional search icon is pressed.
    * Receives the text typed and and the search option as argument
    * and returns t
     * he professional type with its info filled, in case it was found.
    */
    public List<LaravelSearchProfissionaisDataResponse> searchLaravel(String userInput, String pos,
                                                                      String searchOption) throws Exception
    {
        // Making a request to url and getting response
        String searchURL;

        switch (searchOption)
        {
            case "local":
                searchURL = localURL + userInput.replace(" ", "%20") + "," + pos;
                break;
            case "disorder":
                searchURL = disorderURL + userInput.replace(" ", "%20") + "," + pos;
                break;
            case "specialty":
                searchURL = specialtyURL + userInput.replace(" ", "%20") + "," + pos;
                break;
            case "all":
                searchURL = nameURL +"%25" + "," + pos;
                break;
            case "uf":
                searchURL = ufURL + userInput.replace(" ", "%20") + "," + pos;
                break;
            default:
                searchURL = nameURL + userInput.replace(" ", "%20") + "," + pos;
                break;
        }

        try
        {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(searchURL,
                    ServiceHandler.GET);

            Log.d("Entrei no nome", "ENTREI");
            Log.d("Entrei no nome", searchURL);
            Log.d("Entrei no nome", searchOption);

            // In case we get a response, the json info received is parsed
            // and passed to and equivalent object
            // and passed to and equivalent object
            if(jsonStr != null){
                List<LaravelSearchProfissionaisDataResponse> disordersTeste =
                        getProfessionals(jsonStr);
                return disordersTeste;
            }
        }catch (Exception e)
        {
            throw e;
        }

        return null;
    }

    /**
     * This method parsers a json file got by the larevel server response and
     * parsers it to an equivalent object type.
     * */
    private List<LaravelSearchProfissionaisDataResponse> getDiseasesNew(String jsonString) throws Exception
    {
        List<LaravelSearchProfissionaisDataResponse> disorders =  new ArrayList<LaravelSearchProfissionaisDataResponse>();
        Gson gson = new Gson();

        try {
            JSONArray jArray = new JSONArray(jsonString);
            int i = 0;

            // loops through all the json objects, gets and sets
            // all its info
            while (!jArray.isNull(i))
            {
                String stringDisorder = jArray.getString(i);
                //Log.d("Got this Information: ",stringDisorder);
                LaravelSearchProfissionaisDataResponse dis = gson.fromJson(stringDisorder,
                        LaravelSearchProfissionaisDataResponse.class);
                disorders.add(dis);
                i++;
            }
        } catch (JSONException e) {
            Log.d("[Professionals] Error:", e.getMessage());
            throw new Exception();
        }

        return disorders;
    }


    public List<String> autoComplete(String userInput, String searchOption)
            throws Exception
    {

        try
        {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            // Making a request to url and getting response
            String searchURL;

            switch (searchOption)
            {
                case "local":
                    searchURL = localURL + userInput.replace(" ", "%20") + ",0";
                    break;
                case "disorder":
                    searchURL = disorderURL + userInput.replace(" ", "%20") + ",0";
                    break;
                case "specialty":
                    searchURL = specialtyURL + userInput.replace(" ", "%20") + ",0";
                    break;
                default:
                    searchURL = nameURL + userInput.replace(" ", "%20") + ",0";
                    break;
            }

            Log.d("URL", searchURL);


            String jsonStr = sh.makeServiceCall(searchURL, ServiceHandler.GET);

            // In case we get a response, the json info received is parsed
            // and passed to and equivalent object
            // and passed to and equivalent object
            if(jsonStr != null){

                List<LaravelSearchProfissionaisDataResponse> disordersTeste;

                disordersTeste = getProfessionals(jsonStr);



                List<String> profissionais = new ArrayList<>();

                for (LaravelSearchProfissionaisDataResponse profissional : disordersTeste) {
                    profissionais.add(profissional.getName() + profissional.getSurname());
                }
                return profissionais;
            }
        }catch (Exception e)
        {
            throw e;
        }

        return null;
    }


    /**
     * This method parsers a json file got by the larevel server response and
     * parsers it to an equivalent object type.
     * */
    private List<LaravelSearchProfissionaisDataResponse> getByDisorder(String jsonString)
            throws Exception
    {
        List<LaravelSearchProfissionaisDataResponse> disorders =  new ArrayList<LaravelSearchProfissionaisDataResponse>();
        Gson gson = new Gson();

        try {

            JSONObject ob = new JSONObject(jsonString);
            JSONArray jArray = ob.getJSONArray("professionals");

            int i = 0;
            while (!jArray.isNull(i)) {
                String stringDisorder = jArray.getString(i);
                //Log.d("Got this Information: ",stringDisorder);
                LaravelSearchProfissionaisDataResponse dis = gson.fromJson(stringDisorder,
                        LaravelSearchProfissionaisDataResponse.class);
                disorders.add(dis);
                i++;
            }

        } catch (JSONException e) {
            Log.d("[Professionals] Error:", e.getMessage());
            throw new Exception();
        }

        return disorders;
    }


    /**
     * Method responsible for parsing Json response
     * and get its disorders information
     * @param jsonString
     * @return Disorder
     */
    private List<LaravelSearchProfissionaisDataResponse> getProfessionals(String jsonString) throws Exception {
        List<LaravelSearchProfissionaisDataResponse> professionals =
                new ArrayList<LaravelSearchProfissionaisDataResponse>();
        Gson gson = new Gson();
        try {
            JSONObject ob = new JSONObject(jsonString);
            JSONArray jArray = ob.getJSONArray("professionals");

            int i = 0;
            while (!jArray.isNull(i)) {

                professionals.add(gson.fromJson(jArray.getString(i),
                        LaravelSearchProfissionaisDataResponse.class));
                i++;
            }
        } catch (JSONException e) {
            Log.d("[Prof Search]Error", e.getMessage());
            throw new Exception();
        }

        return professionals;
    }


}
