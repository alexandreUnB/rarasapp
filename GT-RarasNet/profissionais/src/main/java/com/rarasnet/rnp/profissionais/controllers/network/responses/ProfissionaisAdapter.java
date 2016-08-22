package com.rarasnet.rnp.profissionais.controllers.network.responses;

import android.util.Log;

import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ronnyery Barbosa on 04/02/2016.
 */
public class ProfissionaisAdapter {

    //private String searchURL = "http://192.168.56.2/webservice/rest_profissionais.json";

    private String searchURL = "http://www.webservice.rederaras.org/rest_profissionais.json";
    //private String searchURL = "http://192.168.85.1/raras/webservice/rest_desordens.json";

    public List<SearchProfissionaisDataResponse> search(String userInput, String searchOption) throws Exception {

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("searchInput", userInput));
        params.add(new BasicNameValuePair("searchType", searchOption));
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

                List<SearchProfissionaisDataResponse> disorders = getDiseases(json);
                return disorders;
            }
        } catch (Exception e) {
            throw e;
        }
        return null;
    }

    private List<SearchProfissionaisDataResponse> getDiseases(String jsonString) throws Exception {
        List<SearchProfissionaisDataResponse> disorders =  new ArrayList<SearchProfissionaisDataResponse>();
        Gson gson = new Gson();

        try {
            //JSONObject ob = new JSONObject(jsonString);
            JSONArray jArray = new JSONArray(jsonString);

            int i = 0;
            Log.d("aqui", String.valueOf(jArray.length()));
            while (!jArray.isNull(i)) {
                String stringDisorder = jArray.getString(i);
                Log.d("aqui",stringDisorder);
                //aux = objTest.getString("name");
                //Log.d("gson46.1",aux);

                SearchProfissionaisDataResponse dis = gson.fromJson(stringDisorder, SearchProfissionaisDataResponse.class);
                Log.d("aqui",dis.getNome());
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
        Log.d("disorder", String.valueOf(disorders.size()));

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

        /*String teste = "çasá"
                ;        String latin1Result = new String(userInput.getBytes("ISO-8859-1"), "ISO-8859-1");
        Log.d("input input teste  json", latin1Result);
        //a userInput = "Doen\u00e7a de Alexander";

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("searchInput", userInput));
        params.add(new BasicNameValuePair("searchOption", searchOption));

        Log.d("input option json", searchOption);
        Log.d("input input json",userInput);
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

                List<Professional> disorders = getDiseases(json);
                return disorders;
            }
        } catch (Exception e) {
            throw e;
        }
        return null;
    }

    private List<Professional> getDiseases(String jsonString) throws Exception {
        List<Professional> disorders = new ArrayList<Professional>();
        Gson gson = new Gson();



        try {
            JSONObject ob = new JSONObject(jsonString);
            JSONArray jArray = ob.getJSONArray("profissionai");

            int i = 0;
            while (!jArray.isNull(i)) {
                String stringProfessional = jArray.getString(i);



                Log.d("utf",stringProfessional);

                Professional dis = gson.fromJson(stringProfessional, Professional.class);

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
    }*/
}
