package com.rarasnet.rnp.shared.models;

import android.util.Log;

import com.google.gson.Gson;
import com.rarasnet.rnp.shared.application.RarasNet;
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
 * Created by Ronnyery Barbosa on 18/08/2015.
 */
public class CenterProfileModel {

    // private String urlPrefix = "http://www.rederaras.unb.br/webservice/rest_signs/";
    private String urlPrefix = "http://www.rederaras.org/web/webservice/rest_instituicoes/";
    //private String urlPrefix = "http://192.168.85.1/raras/webservice/rest_instituicoes/";
    private String url = RarasNet.urlPrefix  + "/api/centerID/";

    Gson gson = new Gson();

    public CenterProfileModel() {

    }

    /**
     * Requests center information from server.s
     * @param signID
     * @param s
     * @return
     */
    public CenterProfile getProfileNew(String signID, String s) {
        String jsonResult;
        String searchURL = url + signID;
        List<DadosNacionais> disease = null;
        Center center = new Center();
        CenterProfile profile;

        try {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            // Making a request to url and getting response
            jsonResult = sh.makeServiceCall(searchURL, ServiceHandler.GET);

            // In case we get a response, the json info received is parsed
            // and passed to and equivalent object
            // and passed to and equivalent object
            Log.d("[CenterPModel]JSON", jsonResult);

            if(jsonResult != null){
                center = getCenter(jsonResult);
                disease = getDisease(jsonResult);
            }

        } catch (Exception e) {

            Log.e("IHHHH", "falhou: " + e);
        }

        profile = new CenterProfile(center, disease);
        return profile;
    }

    /**
     * Parsers response from server and gets selected center
     * information.
     * @param jString
     * @return
     */
    private Center getCenter(String jString)
    {
        Center center = new Center();

        try {
            Log.d("AI", jString);
            JSONObject jObj = new JSONObject(jString);
            String disorderString = jObj.getString("treatmentCenter");
            Log.d("dados centro:", disorderString);

            center = gson.fromJson(disorderString, Center.class);
        } catch (JSONException e) {
            Log.d("[CenterProfM]Error:", e.getMessage());
            e.printStackTrace();
        }
        return center;
    }

    /**
     * Parsers Json responses and gets diseases related to the selected
     * center.
     * @param jString
     * @return
     */
    private List<DadosNacionais> getDisease(String jString) {
        List<DadosNacionais> dadosNacionais = new ArrayList<DadosNacionais>();

        try {
            JSONObject ob = new JSONObject(jString);
            JSONArray jArray = ob.getJSONArray("centerDis");

            int i = 0;
            while (!jArray.isNull(i)) {
                String stringSynonym = jArray.getString(i);
                DadosNacionais dados = gson.fromJson(stringSynonym, DadosNacionais.class);
                dadosNacionais.add(dados);
                i++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return dadosNacionais;
    }

    private List<Professional> getProfessional(String jString) {
        List<Professional> professionals = new ArrayList<Professional>();

        try {


            JSONObject ob = new JSONObject(jString);
            JSONArray jArray = ob.getJSONArray("Profissionai");

            Log.d("dados prof str:", jArray.toString());
            int i = 0;
            while (!jArray.isNull(i)) {
                String stringSynonym = jArray.getString(i);


                Professional dados = gson.fromJson(stringSynonym, Professional.class);
                Log.d("prof obj:", dados.getNome());
                professionals.add(dados);
                i++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return professionals;
    }

    // CODIGO LEGADO
    public CenterProfile getProfile(String signID, String s) {

        String searchURL = urlPrefix + signID + ".json";
        DefaultHttpClient httpClient = new DefaultHttpClient();
        //Sign sign = null;
        // List<Synonym> synonyms = null;
        List<Professional> professional = null;
        List<DadosNacionais> disease = null;
        //Center center = null;
        String jsonResult;
        CenterProfile profile;
        Center center = new Center();


        try {

            HttpResponse response = httpClient.execute(new HttpGet(searchURL));
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                InputStream instream = entity.getContent();
                jsonResult = toString(instream);
                Log.d("JSON_STRING_RESULT:", jsonResult);
                instream.close();

                center = getCenter(jsonResult);

                professional = getProfessional(jsonResult);
                disease = getDisease(jsonResult);
                //  disease = getDisease(jsonResult);
                //references = getReferences(jsonResult);

            }

        } catch (Exception e) {

            Log.e("IHHHH", "falhou: " + e);
        }

        profile = new CenterProfile(professional, center, disease);
        return profile;


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
