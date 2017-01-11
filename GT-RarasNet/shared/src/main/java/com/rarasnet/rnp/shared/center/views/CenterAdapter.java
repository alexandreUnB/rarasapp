package com.rarasnet.rnp.shared.center.views;

import android.util.Log;

import com.google.gson.Gson;
import com.rarasnet.rnp.shared.application.RarasNet;
import com.rarasnet.rnp.shared.center.controllers.network.responses.SearchCentersDataResponse;
import com.rarasnet.rnp.shared.models.Center;
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
 * Created by Ronnyery Barbosa on 05/02/2016.
 */
public class CenterAdapter {

//private String searchURL = "http://192.168.56.2/webservice/rest_profissionais.json";

    private String searchURL = "http://www.webservice.rederaras.org/rest_instituicoes.json";
    //private String searchURL = "http://192.168.85.1/raras/webservice/rest_desordens.json";
    private String nameURL = RarasNet.urlPrefix + "/api/centerName/";
    private String disorderURL = RarasNet.urlPrefix + "/api/centerDisorder/";
    private String specialtyURL = RarasNet.urlPrefix + "/api/centerSpecialty/";
    private String localURL = RarasNet.urlPrefix + "/api/centerLocal/";


    /**
     * search centers on server by name
     * @param userInput
     * @return
     * @throws Exception
     */
    public List<SearchCentersDataResponse> nameSearch(String userInput) throws Exception {
        String searchURL = nameURL + userInput.replace(" ", "%20")+ ",0";
        List<SearchCentersDataResponse> centers = null;

        try {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(searchURL, ServiceHandler.GET);

            if(jsonStr != null) {
                centers = getCenters(jsonStr);
            }

            return  centers;
        } catch (Exception e) {
            throw e;
        }

    }

    /**
     * autocomplete search method
     * @param userInput, option
     *
     * @return
     * @throws Exception
     */
    public List<SearchCentersDataResponse> autoComplete(String userInput, String option) throws Exception {
        String searchURL;
        List<SearchCentersDataResponse> centers = null;

        switch (option)
        {
            case "local":
                searchURL = localURL + userInput.replace(" ", "%20") + ",0";
            break;
            case "disorder":
                searchURL = disorderURL + userInput.replace(" ", "%20") + ",0";
            break;
            case "specialty":
                searchURL = specialtyURL + userInput.replace(" ", "%20") + ",0";
            break;
            default:
                searchURL = nameURL + userInput.replace(" ", "%20") + ",0";
            break;
        }

        try {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(searchURL, ServiceHandler.GET);

            if(jsonStr != null) {
                centers = getCenters(jsonStr);
            }

            return  centers;
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
    private List<SearchCentersDataResponse> getCenters(String jsonString) throws Exception {
        List<SearchCentersDataResponse> centers = new ArrayList<SearchCentersDataResponse>();
        Gson gson = new Gson();

        try {
            JSONObject ob = new JSONObject(jsonString);
            JSONArray jArray = ob.getJSONArray("centers");

            int i = 0;
            while (!jArray.isNull(i)) {
               centers.add(gson.fromJson(jArray.getString(i),
                       SearchCentersDataResponse.class));
                i++;
            }
        } catch (JSONException e) {
            Log.d("[Center Search]Error", e.getMessage());
            throw new Exception();
        }

        return centers;
    }

    public List<SearchCentersDataResponse> search(String userInput, String searchOption, String code) throws Exception {

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("searchInput", userInput));

        params.add(new BasicNameValuePair("searchType", searchOption));
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

                List<SearchCentersDataResponse> disorders = getDiseases(json);
                return disorders;
            }
        } catch (Exception e) {
            throw e;
        }
        return null;
    }

    private List<SearchCentersDataResponse> getDiseases(String jsonString) throws Exception {
        List<SearchCentersDataResponse> disorders =  new ArrayList<SearchCentersDataResponse>();
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

                SearchCentersDataResponse dis = gson.fromJson(stringDisorder, SearchCentersDataResponse.class);
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
}
