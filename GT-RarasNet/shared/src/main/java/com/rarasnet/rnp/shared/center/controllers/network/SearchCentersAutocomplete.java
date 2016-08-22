package com.rarasnet.rnp.shared.center.controllers.network;

import android.util.Log;

import com.google.gson.Gson;
import com.rarasnet.rnp.shared.models.Center;

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
public class SearchCentersAutocomplete {

    private String searchURL = "http://www.webservice.rederaras.org/rest_instituicoes.json";

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

                List<Center> centers = getCenters(json);

                ArrayList<String> suggestions = new ArrayList<>();

                for(Center center : centers) {
                    suggestions.add(center.getNome());
                }


                return suggestions;
            }
        } catch (Exception e) {
            throw e;
        }
        return null;
    }

    private List<Center> getCenters(String jsonString) throws Exception {
        List<Center> centers = new ArrayList<Center>();
        Gson gson = new Gson();



        try {
            JSONObject ob = new JSONObject(jsonString);
            JSONArray jArray = ob.getJSONArray("Instituico");

            int i = 0;
            while (!jArray.isNull(i)) {
                String stringCenters = jArray.getString(i);



                Log.d("utf",stringCenters);

                Center dis = gson.fromJson(stringCenters, Center.class);

                centers.add(dis);
                i++;
            }
        } catch (JSONException e) {
            Log.d("ERRO:", e.getMessage());
            throw new Exception();
        }
        return centers;
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
