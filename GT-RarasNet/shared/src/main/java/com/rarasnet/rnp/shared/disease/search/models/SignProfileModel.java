package com.rarasnet.rnp.shared.disease.search.models;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.rarasnet.rnp.shared.profissionais.controllers.network.responses.ServiceHandler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ronnyery Barbosa on 27/07/2015.
 */
public class SignProfileModel {


    // private String urlPrefix = "http://www.rederaras.unb.br/webservice/rest_signs/";
    private String urlPrefix = "http://www.webservice.rederaras.org/rest_signs/";
    //private String urlPrefix = "http://192.168.85.1/raras/webservice/rest_signs/";
    private String urlSign = "http://192.168.0.118:8080/api/signLoader/";


    Gson gson = new Gson();

    public SignProfileModel() {

    }

    public List<Sign> getSignsLoader(String diseaseID, String pos) {

        String searchURL = urlSign + diseaseID + "," + pos;
        List<Sign> signs = null;
        Log.d("ENTREI", "SIGNS");
        Log.d("ENTREI", "id:"+diseaseID);
        Log.d("ENTREI", "pos:"+pos);
        try {
            ServiceHandler sh = new ServiceHandler();
            // Making a request to url and getting response
            Log.d("ENTREI", "url:"+searchURL);
            String jsonStr = sh.makeServiceCall(searchURL, ServiceHandler.GET);
            Log.d("PORRA", "url:"+searchURL);

            Log.d("A string", "ai" + jsonStr);

            if(jsonStr != null){
                signs = getSigns(jsonStr);
            }

        } catch (Exception e) {

            Log.e("IHHHH", "falhou: " + e);
        }



        return signs;
    }


    /**
     * Method responsible for parsing Json response
     * and get the signs related to this
     * disorder
     * @param jString
     * @return List<Sign>
     */
    private List<Sign> getSigns(String jString) {
        List<Sign> signs = new ArrayList<Sign>();
        Gson gson = new Gson();

        try {
            JSONObject ob = new JSONObject(jString);
            JSONArray jArray = ob.getJSONArray("signs");

            int i = 0;
            while (!jArray.isNull(i)) {
                String stringSign = jArray.getString(i);
                Log.d("AI", stringSign);
                Sign sign = gson.fromJson(stringSign, Sign.class);

                signs.add(sign);
                i++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return signs;
    }

    public Sign getProfile(String signID) {

        String searchURL = urlPrefix + signID + ".json";
        DefaultHttpClient httpClient = new DefaultHttpClient();
        //Sign sign = null;
        // List<Synonym> synonyms = null;
        //List<Sign> signs = null;
        // List<Reference> references = null;
        String jsonResult;
        Sign profile = new Sign();


        try {

            HttpResponse response = httpClient.execute(new HttpGet(searchURL));
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                InputStream instream = entity.getContent();
                jsonResult = toString(instream);
                Log.d("JSON_STRING_RESULT:", jsonResult);
                instream.close();

                profile = getSign(jsonResult);

                // synonyms = getSynonyms(jsonResult);
                //signs = getSigns(jsonResult);
                //references = getReferences(jsonResult);

            }

        } catch (Exception e) {

            Log.e("IHHHH", "falhou: " + e);
        }
        return profile;


    }

    private Sign getSign(String jString) {

        Sign sign = new Sign();

        try {
            Log.d("test:", "json1");

            //JSONObject jObj = new JSONObject(jString);


            ///Log.d("test:", "json1" + jObj);
            // String disorderString = jObj.getString("sing");
            //Log.d("DISORDER_STRING:", jObj.toString());
            sign = gson.fromJson(jString, Sign.class);
            Log.d("test:", "json1");

            Log.d("test:", sign.getId());
            Log.d("test:", "json1");
            Log.d("test:", sign.getName());
            //Log.d("DISORDER_STRINGssss:", String.valueOf(sign.getId()));
        } catch (JsonSyntaxException e) {
            Log.d("LoiraoGAy", e.getMessage());
            e.printStackTrace();
        }
        return sign;
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



