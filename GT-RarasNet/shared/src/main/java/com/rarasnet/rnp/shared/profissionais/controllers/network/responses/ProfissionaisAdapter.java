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
    private String url = RarasNet.urlPrefix + "/api/professionalName/";

    /**
    * Method that is called after Professional search icon is pressed.
    * Receives the text typed and and the search option as argument
    * and returns t
     * he professional type with its info filled, in case it was found.
    */
    public List<LaravelSearchProfissionaisDataResponse> searchLaravel(String userInput, String searchOption) throws Exception
    {

        try
        {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url + userInput.replace(" ","%20"), ServiceHandler.GET);

            Log.d("Entrei no nome", "ENTREI");

            // In case we get a response, the json info received is parsed
            // and passed to and equivalent object
            // and passed to and equivalent object
            if(jsonStr != null){
                List<LaravelSearchProfissionaisDataResponse> disordersTeste = getDiseasesNew(jsonStr);
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


    public List<String> autoComplete(String userInput, String searchOption) throws Exception
    {

        try
        {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url + userInput.replace(" ","%20"), ServiceHandler.GET);

            // In case we get a response, the json info received is parsed
            // and passed to and equivalent object
            // and passed to and equivalent object
            if(jsonStr != null){
                List<LaravelSearchProfissionaisDataResponse> disordersTeste = getDiseasesNew(jsonStr);
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

//
//    // CODIGO LEGADO -- APAGAR
//private String searchURL = "http://www.webservice.rederaras.org/rest_profissionais.json";
////    private String toString(InputStream is) throws IOException {
//        byte[] bytes = new byte[1024];
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        int lidos;
//        while ((lidos = is.read(bytes)) > 0) {
//            baos.write(bytes, 0, lidos);
//        }
//        return new String(baos.toByteArray());
//    }
//    public List<SearchProfissionaisDataResponse> search(String userInput, String searchOption) throws Exception {
//
//        List<NameValuePair> params = new ArrayList<>();
//        params.add(new BasicNameValuePair("searchInput", userInput));
//        params.add(new BasicNameValuePair("searchType", searchOption));
//        DefaultHttpClient httpClient = new DefaultHttpClient();
//
//
//        try {
//
//            HttpPost httpPost = new HttpPost(searchURL);
//            httpPost.setEntity(new UrlEncodedFormEntity(params));
//
//            HttpResponse httpResponse = httpClient.execute(httpPost);
//
//            HttpEntity entity = httpResponse.getEntity();
//
//            if (entity != null) {
//                InputStream instream = entity.getContent();
//                String json = toString(instream);
//                instream.close();
//
//                Log.d("JSON RESPONSE:", json);
//
//                List<SearchProfissionaisDataResponse> disorders = getDiseases(json);
//                return disorders;
//            }
//        } catch (Exception e) {
//            throw e;
//        }
//        return null;
//    }
//
//    private List<SearchProfissionaisDataResponse> getDiseases(String jsonString) throws Exception {
//        List<SearchProfissionaisDataResponse> disorders =  new ArrayList<SearchProfissionaisDataResponse>();
//        Gson gson = new Gson();
//
//        try {
//            //JSONObject ob = new JSONObject(jsonString);
//            JSONArray jArray = new JSONArray(jsonString);
//
//            int i = 0;
//            Log.d("aqui", String.valueOf(jArray.length()));
//            while (!jArray.isNull(i)) {
//                String stringDisorder = jArray.getString(i);
//                Log.d("aqui",stringDisorder);
//                //aux = objTest.getString("name");
//                //Log.d("gson46.1",aux);
//
//                SearchProfissionaisDataResponse dis = gson.fromJson(stringDisorder, SearchProfissionaisDataResponse.class);
//                Log.d("aqui",dis.getNome());
//                //Log.d("gson46.2",dis.getOrphanumber());
//                //Log.d("gson46.2",dia.getExpertlink());
//                //JSONArray rest = b.getJSONArray("Phone");
//                disorders.add(dis);
//                i++;
//            }
//        } catch (JSONException e) {
//            Log.d("ERRO:", e.getMessage());
//            throw new Exception();
//        }
//        Log.d("disorder", String.valueOf(disorders.size()));
//
//        return disorders;
//    }

}
