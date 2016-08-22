package com.rarasnet.rnp.shared.disease.search.models;

import android.util.Log;

import com.google.gson.Gson;
import com.rarasnet.rnp.shared.models.Disorder;

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
 * Created by Ronnyery Barbosa on 29/11/2015.
 */
public class JsonDisorderSign {

    private String searchURL = "http://www.webservice.rederaras.org/rest_signs.json";
    //private String searchURL = "http://192.168.25.4/webservice/rest_signs.json";
    //private String searchURL = "http://192.168.1.103/webservice/rest_signs.json";

    public List<Disorder> search(String userInput, String searchOption) throws Exception {
        Log.d("bb","teste");
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("searchInput", userInput));
        params.add(new BasicNameValuePair("searchOption", searchOption));
        DefaultHttpClient httpClient = new DefaultHttpClient();


        try {
            Log.d("bbb","teste1");

            HttpPost httpPost = new HttpPost(searchURL);
            Log.d("bbb",searchURL);
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            Log.d("bb", "teste31");
            HttpResponse httpResponse = httpClient.execute(httpPost);
            Log.d("b","teste32");
            HttpEntity entity = httpResponse.getEntity();
            Log.d("sds","teste3");
            if (entity != null) {
                Log.d("sds","teste4");
                InputStream instream = entity.getContent();
                String json = toString(instream);
                instream.close();

                Log.d("JSON RESPONSE:", json);

                List<Disorder> disorders = getDiseases(json);
                return disorders;
            }
        } catch (Exception e) {
             e.getStackTrace();
        }
        return null;
    }

    private List<Disorder> getDiseases(String jsonString) throws Exception {
        Log.d("sds","teste5");
        List<Disorder> disorders = new ArrayList<Disorder>();
        Gson gson = new Gson();

        try {
            JSONObject ob = new JSONObject(jsonString);
            JSONArray jArray = ob.getJSONArray("sign");

            int i = 0;
            while (!jArray.isNull(i)) {
                String stringDisorder = jArray.getString(i);



                Disorder dis = gson.fromJson(stringDisorder, Disorder.class);
                Log.d("Doença", dis.getName());

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
