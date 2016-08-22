package com.rarasnet.rnp.shared.usuario.controllers.network;

import android.util.Log;

import com.google.gson.Gson;
import com.rarasnet.rnp.shared.usuario.controllers.Profile;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ronnyery Barbosa on 04/02/2016.
 */
public class UserAdapter {

    //private String searchURL = "http://192.168.56.2/webservice/rest_profissionais.json";

    private String searchURL = "http://www.webservice.rederaras.org//rest_user/user.json";
    //private String searchURL = "http://192.168.85.1/raras/webservice/rest_desordens.json";

    public Profile search(String userInput, String searchOption) throws Exception {

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("lg", userInput));
        params.add(new BasicNameValuePair("pw", searchOption));
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

                Log.d("JSON RESPONSE User:", json);

                Profile user = getUser(json);
                return user;
            }
        } catch (Exception e) {
            throw e;
        }
        return null;
    }

    private Profile getUser(String jsonString) throws Exception {
        Profile user =  new Profile();
        Gson gson = new Gson();

        try {
            JSONObject ob = new JSONObject(jsonString);
            //JSONArray jArray = ob.getJSONArray("User");

        Log.d("aqui", ob.optString("User"));

            int i = 0;
            //Log.d("aqui", String.valueOf(jArray.length()));
            //while (!jArray.isNull(i)) {
            String stringDisorder = ob.optString("User");
                //Log.d("aqui",stringDisorder);
                //aux = objTest.getString("name");
                //Log.d("gson46.1",aux);

                user = gson.fromJson(stringDisorder, Profile.class);
                Log.d("aqui",user.getNome());
                //Log.d("gson46.2",dis.getOrphanumber());
                //Log.d("gson46.2",dia.getExpertlink());
                //JSONArray rest = b.getJSONArray("Phone");
                i++;
            //}
        } catch (JSONException e) {
            Log.d("ERRO:", e.getMessage());
            throw new Exception();
        }
        //Log.d("disorder", String.valueOf(disorders.size()));

        return user;
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
