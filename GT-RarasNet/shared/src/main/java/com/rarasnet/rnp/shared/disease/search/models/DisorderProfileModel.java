package com.rarasnet.rnp.shared.disease.search.models;

import android.util.Log;

import com.google.gson.Gson;
import com.rarasnet.rnp.shared.application.RarasNet;
import com.rarasnet.rnp.shared.disease.profile.associates.AssociatedProfile;
import com.rarasnet.rnp.shared.disease.profile.description.Specialty;
import com.rarasnet.rnp.shared.models.Center;
import com.rarasnet.rnp.shared.models.DadosNacionais;
import com.rarasnet.rnp.shared.models.Disorder;
import com.rarasnet.rnp.shared.models.Indicator;
import com.rarasnet.rnp.shared.models.Professional;
import com.rarasnet.rnp.shared.profissionais.controllers.network.responses.LaravelSearchProfissionaisDataResponse;
import com.rarasnet.rnp.shared.profissionais.controllers.network.responses.ServiceHandler;
import com.rarasnet.rnp.shared.protocol.ProtocolModel;

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
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Farina on 5/7/2015.
 */
public class DisorderProfileModel {
    protected URLConnection conn;
    private String urlPrefix = "http://www.rederaras.org/web/webservice/rest_desordens/";
   //private String urlPrefix = "http://www.webservice.rederaras.org/rest_desordens/";
    //private String urlPrefix = "http://192.168.56.2/webservice/rest_desordens/";
    private String url = RarasNet.urlPrefix + "/api/disorderID/";
    private String urlProf = RarasNet.urlPrefix + "/api/profLoader/";

    Gson gson = new Gson();

    public DisorderProfileModel() {

    }

    public DisorderProfile getProfileNew(String diseaseID) {

        String searchURL = url + diseaseID.replace(" ","%20");
        Log.d("url",searchURL);
        Disorder disorder = null;

        List<Specialty> specialties = null;
        Mortalidade mortalidade = null;
        List<Synonym> synonyms = null;
        List<Sign> signs = null;
        int signsCounter = 0;
        List<Reference> references = null;
        List<Professional> professionals = null;
        List<Center> centers = null;
        List<Indicator> indicators = null;
        DadosNacionais dadosNacionais = null;
        Cid cid = null;
        ProtocolModel protocol = null;
        String jsonResult;
        DisorderProfile profile;
        List<String> cids = null;


//        String searchURL2 = urlPrefix + diseaseID + ".json";
//        Log.d("url",searchURL);
//        DefaultHttpClient httpClient = new DefaultHttpClient();



        try {
//            HttpResponse response = httpClient.execute(new HttpGet(searchURL2));
//            HttpEntity entity = response.getEntity();
//            InputStream instream = entity.getContent();
//            jsonResult = toString(instream);

            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(searchURL, ServiceHandler.GET);

            if(jsonStr != null){
                disorder = getDisorder(jsonStr);
//                specialties = getSpecialties(jsonStr);
                signs = getSigns(jsonStr);
                centers = getCenter(jsonStr);
                professionals = getProfessionais(jsonStr);
                signsCounter = getSignsCounter(jsonStr);
                indicators = getIndicators(jsonStr);
                protocol = getProtocol(jsonStr);
                cids = getCids(jsonStr);

//                mortalidade = getMortalidade(jsonResult);
//                synonyms = getSynonyms(jsonResult);
//                references = getReferences(jsonResult);
//
//                dadosNacionais = getDadosNacionais(jsonResult);
//                cid = getCid(jsonResult);
            }

        } catch (Exception e) {

            Log.e("IHHHH", "falhou: " + e);
        }


        profile = new DisorderProfile(disorder, specialties, references, signs, synonyms, professionals,
                centers, mortalidade, dadosNacionais, indicators, protocol, cids);
        profile.setSignCounter(signsCounter);
        return profile;
    }

    /**
     * Method responsible for parsing Json response
     * and get its disorders information
     * @param jString
     * @return Disorder
     */
    private Disorder getDisorder(String jString) {

        Disorder disorder = null;

        try {
            JSONObject jObj = new JSONObject(jString);
            String disorderString =  jObj.getString("disorder");

            disorder = gson.fromJson(disorderString, Disorder.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return disorder;
    }


    /**
     * Method responsible for parsing Json response
     * and get the specialties that 'treats' this
     * disorder
     * @param jString
     * @return List<Specialty>
     */
    private List<Specialty> getSpecialties(String jString) {
        List<Specialty> specialties = new ArrayList<Specialty>();

        try {
            JSONObject ob = new JSONObject(jString);
            JSONArray jArray = ob.getJSONArray("specialties");

            int i = 0;
            while (!jArray.isNull(i)) {
                String stringSpecialty = jArray.getString(i);
                Specialty spc = gson.fromJson(stringSpecialty, Specialty.class);
                specialties.add(spc);
                i++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return specialties;
    }

    /**
     * Method responsible for parsing Json response
     * and get the signs related to this
     * disorder
     * @param jString
     * @return List<Sign>
     */
    private List<Sign> getSigns(String jString) {
        List<Sign> signs = new ArrayList<Sign>();
        Gson gson = new Gson();

        try {
            JSONObject ob = new JSONObject(jString);
            JSONArray jArray = ob.getJSONArray("signs");

            int i = 0;
            while (!jArray.isNull(i)) {
                String stringSign = jArray.getString(i);
                Log.d("AI", stringSign);
                Sign sign = gson.fromJson(stringSign, Sign.class);

                signs.add(sign);
                i++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return signs;
    }

    /**
     * Method responsible for parsing Json response
     * and get the number of signs related to this
     * disorder
     * @param jString
     * @return List<Sign>
     */
    private int getSignsCounter(String jString) {
        int signsCounter = 0;
        Gson gson = new Gson();

        try {
            JSONObject ob = new JSONObject(jString);
            signsCounter =ob.getInt("signsLength");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return signsCounter;
    }

    /**
     * Method responsible for parsing Json response
     * and get the centers that treats the selected
     * disorder
     * @param jString
     * @return List<Center>
     */
    private List<Center> getCenter(String jString) {
        List<Center> centers = new ArrayList<Center>();
        Gson gson = new Gson();

        try {
            JSONObject ob = new JSONObject(jString);
            JSONArray jArray = ob.getJSONArray("centers");

            int i = 0;
            while (!jArray.isNull(i)) {
                String stringSign = jArray.getString(i);
                Log.d("centro string:", stringSign);

                Center sign = gson.fromJson(stringSign, Center.class);
                centers.add(sign);
                i++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return centers;
    }

    /**
     * Parsers servers json request and gets its
     * associated professionals information.
     * @param jString
     * @return
     */
    private List<Professional> getProfessionais(String jString) {
        List<Professional> professionals = new ArrayList<Professional>();
        Gson gson = new Gson();

        try {

            JSONObject ob = new JSONObject(jString);
            JSONArray jArray = ob.getJSONArray("professionalsFilter");

            int i = 0;
            while (!jArray.isNull(i)) {
                String stringSign = jArray.getString(i);
                Professional sign = gson.fromJson(stringSign, Professional.class);
                professionals.add(sign);
                i++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return professionals;
    }

    public List<AssociatedProfile> getProfsLoader(String diseaseID, String pos) {

        String searchURL = urlProf + diseaseID + "," + pos;
        List<AssociatedProfile> associated = null;
        List<Professional> professionals = null;

        try {
            ServiceHandler sh = new ServiceHandler();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(searchURL, ServiceHandler.GET);

            if(jsonStr != null){
                associated = new ArrayList<>();
                professionals = getProfessionais(jsonStr);
                for(Professional p: professionals){
                    AssociatedProfile aux = new AssociatedProfile(0, 0, p.getName(),
                            p.getCity(), p.getUf(), "", p.getId());
                    associated.add(aux);
                }
            }

        } catch (Exception e) {

            Log.e("IHHHH", "falhou: " + e);
        }



        return associated;
    }


    private List<Indicator> getIndicators(String jString) {
        List<Indicator> indicators = new ArrayList<Indicator>();
        Gson gson = new Gson();

        try {
            JSONObject ob = new JSONObject(jString);
            JSONArray jArray = ob.getJSONArray("indicators");

            int i = 0;
            while (!jArray.isNull(i)) {
                String stringSign = jArray.getString(i);
                Log.d("indicator:", stringSign);

                Indicator sign = gson.fromJson(stringSign, Indicator.class);
                indicators.add(sign);
                i++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return indicators;
    }


    /**
     * Method responsible for parsing Json response
     * and get its protocols information
     * @param jString
     * @return Disorder
     */
    private ProtocolModel getProtocol(String jString) {

        ProtocolModel protocol = null;

        try {
            JSONObject jObj = new JSONObject(jString);
            String string =  jObj.getString("protocol");

            protocol = gson.fromJson(string, ProtocolModel.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return protocol;
    }



    private List<String> getCids(String jString) {
        List<String> cids = new ArrayList<String>();

        try {
            JSONObject ob = new JSONObject(jString);
            JSONArray jArray = ob.getJSONArray("icds");

            int i = 0;
            while (!jArray.isNull(i)) {
                String stringCid= jArray.getString(i);
                cids.add(stringCid);
                i++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return cids;
    }

}
