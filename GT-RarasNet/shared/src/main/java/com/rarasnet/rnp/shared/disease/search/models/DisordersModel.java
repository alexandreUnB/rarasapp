package com.rarasnet.rnp.shared.disease.search.models;

import android.util.Log;

import com.google.gson.Gson;
import com.rarasnet.rnp.shared.application.RarasNet;
import com.rarasnet.rnp.shared.models.Disorder;
import com.rarasnet.rnp.shared.profissionais.controllers.network.responses.ServiceHandler;

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
 * Created by Farina on 4/17/2015.
 */
public class DisordersModel {
    private String searchURL = "http://www.webservice.rederaras.org/rest_desordens.json";
    //private String searchURL = "http://192.168.85.1/raras/webservice/rest_desordens.json";
    private String nameURL = RarasNet.urlPrefix  + "/api/disorderName/";
    private String cidURL = RarasNet.urlPrefix  + "/api/cidID/";


    public List<Disorder> nameSearch(String userInput) throws Exception {

        String searchURL = nameURL + userInput.replace(" ", "%20");
        List<Disorder> disorders = null;

        try {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(searchURL, ServiceHandler.GET);

            if(jsonStr != null) {
                disorders = getDisorders(jsonStr);
            }

            return  disorders;
        } catch (Exception e) {
            throw e;
        }
    }

    public List<Disorder> cidSearch(String userInput) throws Exception {

        String searchURL = cidURL + userInput.replace(" ", "%20");
        List<Disorder> disorders = null;
        Log.d("Search Disorderasddas", "By CID");

        try {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(searchURL, ServiceHandler.GET);
            Log.d("Search Disorderassa", "By CID");

            if(jsonStr != null) {
                disorders = getDisorders(jsonStr);
            }

            return  disorders;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Method responsible for parsing Json response
     * and get its disorders information
     * @param jsonString
     * @return Disorder
     */
    private List<Disorder> getDisorders(String jsonString) throws Exception {
        List<Disorder> disorders = new ArrayList<Disorder>();
        Gson gson = new Gson();

        try {
            JSONObject ob = new JSONObject(jsonString);
            JSONArray jArray = ob.getJSONArray("disorders");

            int i = 0;
            while (!jArray.isNull(i)) {
                String stringDisorder = jArray.getString(i);
                Disorder dis = gson.fromJson(stringDisorder, Disorder.class);
                disorders.add(dis);
                i++;
            }
        } catch (JSONException e) {
            Log.d("ERRO:", e.getMessage());
            throw new Exception();
        }

        return disorders;
    }

    public List<Disorder> search(String userInput, String searchOption, String code) throws Exception {

        List<NameValuePair> params = new ArrayList<>();


        params.add(new BasicNameValuePair("searchInput", userInput));
        params.add(new BasicNameValuePair("searchOption", searchOption));
        params.add(new BasicNameValuePair("searchCode", code));
        DefaultHttpClient httpClient = new DefaultHttpClient();


        try {

            HttpPost httpPost = new HttpPost(searchURL);
            httpPost.setEntity(new UrlEncodedFormEntity(params));

            HttpResponse httpResponse = httpClient.execute(httpPost);

            HttpEntity entity = httpResponse.getEntity();

            if (entity != null) {
                InputStream instream = entity.getContent();
                String json = toString(instream);
                instream.close();

                Log.d("JSON RESPONSE:", json);

                List<Disorder> disorders = getDiseases(json);
                return disorders;
            }
        } catch (Exception e) {
            throw e;
        }
        return null;
    }

    private List<Disorder> getDiseases(String jsonString) throws Exception {
        List<Disorder> disorders = new ArrayList<Disorder>();
        Gson gson = new Gson();

        try {
            JSONObject ob = new JSONObject(jsonString);
            JSONArray jArray = ob.getJSONArray("desorden");

            int i = 0;
            while (!jArray.isNull(i)) {
                String stringDisorder = jArray.getString(i);

                //aux = objTest.getString("name");
                //Log.d("gson46.1",aux);

                Disorder dis = gson.fromJson(stringDisorder, Disorder.class);

                //Log.d("gson46.2",dis.getOrphanumber());
                //Log.d("gson46.2",dia.getExpertlink());
                //JSONArray rest = b.getJSONArray("Phone");
                disorders.add(dis);
                i++;
            }
        } catch (JSONException e) {
            Log.d("ERRO:", e.getMessage());
            throw new Exception();
        }

        return disorders;
    }

    private String toString(InputStream is) throws IOException {
        byte[] bytes = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int lidos;
        while ((lidos = is.read(bytes)) > 0) {
            baos.write(bytes, 0, lidos);
        }
        return new String(baos.toByteArray());
    }

}

