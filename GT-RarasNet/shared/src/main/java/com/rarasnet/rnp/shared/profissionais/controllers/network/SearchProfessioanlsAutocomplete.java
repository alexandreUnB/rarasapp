package com.rarasnet.rnp.shared.profissionais.controllers.network;

import android.util.Log;

import com.google.gson.Gson;
import com.rarasnet.rnp.shared.models.Professional;

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
 * Created by Farina on 19/11/2015.
 */
public class SearchProfessioanlsAutocomplete {

    private String searchURL = "http://www.webservice.rederaras.org/rest_profissionais.json";



    // CODIGO LEGADO
    public ArrayList<String> getSuggestions(String query, String queryType) throws Exception {

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("searchInput", query));
        params.add(new BasicNameValuePair("searchOption", queryType));
        DefaultHttpClient httpClient = new DefaultHttpClient();


        try {

            HttpPost httpPost = new HttpPost(searchURL);

            httpPost.setEntity(new UrlEncodedFormEntity(params));
            Log.d("input input json", httpPost.toString());

            HttpResponse httpResponse = httpClient.execute(httpPost);

            HttpEntity entity = httpResponse.getEntity();

            if (entity != null) {
                InputStream instream = entity.getContent();
                String json = toString(instream);
                instream.close();

                Log.d("JSON RESPONSE:", json);

                List<Professional> professionals = getProfissionals(json);

                ArrayList<String> suggestions = new ArrayList<>();

                for(Professional professional : professionals) {
                    suggestions.add(professional.getNome());
                }


                return suggestions;
            }
        } catch (Exception e) {
            throw e;
        }
        return null;
    }

    private List<Professional> getProfissionals(String jsonString) throws Exception {
        List<Professional> disorders = new ArrayList<Professional>();
        Gson gson = new Gson();



        try {
            JSONObject ob = new JSONObject(jsonString);
            JSONArray jArray = ob.getJSONArray("profissionai");

            int i = 0;
            while (!jArray.isNull(i)) {
                String stringDisorder = jArray.getString(i);



                Log.d("utf",stringDisorder);

                Professional dis = gson.fromJson(stringDisorder, Professional.class);

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
