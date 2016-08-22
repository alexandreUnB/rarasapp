package com.rarasnet.rnp.shared.disease.search.models.persistence;

import android.util.Log;

import com.google.gson.Gson;
import com.rarasnet.rnp.shared.disease.search.models.Sign;

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
 * Created by Ronnyery Barbosa on 15/11/2015.
 */
public class JsonSign {

    private String searchURL = "http://www.webservice.rederaras.org/rest_signs.json";
    //private String searchURL = "http://192.168.25.4/webservice/rest_signs.json";
    //private String searchURL = "http://192.168.1.103/webservice/rest_signs.json";

    public List<Sign> search(String userInput, String searchOption) throws Exception {

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("searchInput", userInput));
        params.add(new BasicNameValuePair("searchOption", searchOption));
        DefaultHttpClient httpClient = new DefaultHttpClient();


        try {
            Log.d("string", userInput);
            HttpPost httpPost = new HttpPost(searchURL);
            httpPost.setEntity(new UrlEncodedFormEntity(params));

            HttpResponse httpResponse = httpClient.execute(httpPost);

            HttpEntity entity = httpResponse.getEntity();

            if (entity != null) {
                InputStream instream = entity.getContent();
                String json = toString(instream);
                instream.close();

                Log.d("JSON RESPONSE:", json);

                List<Sign> disorders = getDiseases(json);
                return disorders;
            }
        } catch (Exception e) {
            throw e;
        }
        return null;
    }

    private List<Sign> getDiseases(String jsonString) throws Exception {
        List<Sign> disorders = new ArrayList<Sign>();
        Gson gson = new Gson();

        try {
            JSONObject ob = new JSONObject(jsonString);
            Log.d("JSON RESPONSE2:", ob.toString());
            JSONArray jArray = ob.getJSONArray("sign");
            Log.d("JSON RESPONSE:1", jArray.toString());

            int i = 0;
            while (!jArray.isNull(i)) {
                String stringDisorder = jArray.getString(i);

                //aux = objTest.getString("name");
                //Log.d("gson46.1",aux);

                Sign dis = gson.fromJson(stringDisorder, Sign.class);

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