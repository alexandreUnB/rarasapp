package com.rarasnet.rnp.desordens.search.models;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Ronnyery Barbosa on 27/07/2015.
 */
public class SignProfileModel {


    // private String urlPrefix = "http://www.rederaras.unb.br/webservice/rest_signs/";
    private String urlPrefix = "http://www.webservice.rederaras.org/rest_signs/";
    //private String urlPrefix = "http://192.168.85.1/raras/webservice/rest_signs/";


    Gson gson = new Gson();

    public SignProfileModel() {

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


