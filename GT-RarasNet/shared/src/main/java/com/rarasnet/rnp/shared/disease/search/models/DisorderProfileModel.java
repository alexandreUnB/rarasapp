package com.rarasnet.rnp.shared.disease.search.models;

import android.util.Log;

import com.google.gson.Gson;
import com.rarasnet.rnp.shared.application.RarasNet;
import com.rarasnet.rnp.shared.disease.profile.associates.AssociatedProfile;
import com.rarasnet.rnp.shared.disease.profile.description.Specialty;
import com.rarasnet.rnp.shared.models.Center;
import com.rarasnet.rnp.shared.models.DadosNacionais;
import com.rarasnet.rnp.shared.models.Disorder;
import com.rarasnet.rnp.shared.models.Professional;
import com.rarasnet.rnp.shared.profissionais.controllers.network.responses.LaravelSearchProfissionaisDataResponse;
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
        DadosNacionais dadosNacionais = null;
        Cid cid = null;

        String jsonResult;
        DisorderProfile profile;

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
                specialties = getSpecialties(jsonStr);
                signs = getSigns(jsonStr);
                centers = getCenter(jsonStr);
                professionals = getProfessionais(jsonStr);
                signsCounter = getSignsCounter(jsonStr);

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
                centers, mortalidade, dadosNacionais, cid);
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

    // CODIGO LEGADO
//    public DisorderProfile getProfile(String diseaseID) {
//
//        String searchURL = urlPrefix + diseaseID + ".json";
//        Log.d("url",searchURL);
//        DefaultHttpClient httpClient = new DefaultHttpClient();
//        Disorder disorder = null;
//
//        Mortalidade mortalidade = null;
//        List<Synonym> synonyms = null;
//        List<Sign> signs = null;
//        List<Reference> references = null;
//        List<Professional> professionals = null;
//        List<Center> centers = null;
//        DadosNacionais dadosNacionais = null;
//        Cid cid = null;
//
//        String jsonResult;
//        DisorderProfile profile;
//
//
//        try {
//
//
//            HttpResponse response = httpClient.execute(new HttpGet(searchURL));
//            HttpEntity entity = response.getEntity();
//
//            if (entity != null) {
//                InputStream instream = entity.getContent();
//                jsonResult = toString(instream);
//                Log.d("JSON_STRING_RESULT:", jsonResult);
//                instream.close();
//
//                disorder = getDisorder(jsonResult);
//                mortalidade = getMortalidade(jsonResult);
//                synonyms = getSynonyms(jsonResult);
//                signs = getSigns(jsonResult);
//                references = getReferences(jsonResult);
//                professionals = getProfessionais(jsonResult);
//                centers = getCenter(jsonResult);
//                dadosNacionais = getDadosNacionais(jsonResult);
//                cid = getCid(jsonResult);
//            }
//
//        } catch (Exception e) {
//
//            Log.e("IHHHH", "falhou: " + e);
//        }
//
//
//        profile = new DisorderProfile(disorder, references, signs, synonyms, professionals,
//                centers, mortalidade, dadosNacionais, cid);
//        return profile;
//    }
//
//
//
//    private List<Synonym> getSynonyms(String jString) {
//        List<Synonym> synonyms = new ArrayList<Synonym>();
//
//        try {
//
//
//            JSONObject ob = new JSONObject(jString).getJSONObject("desorden");
//            JSONArray jArray = ob.getJSONArray("Sinonimo");
//
//            int i = 0;
//            while (!jArray.isNull(i)) {
//                String stringSynonym = jArray.getString(i);
//                //Log.d("SYNONYM_STRING:", stringSynonym);
//
//                Synonym syn = gson.fromJson(stringSynonym, Synonym.class);
//                synonyms.add(syn);
//                i++;
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return synonyms;
//    }
//
//
//
//    private List<Reference> getReferences(String jString) {
//        List<Reference> references = new ArrayList<Reference>();
//
//        try {
//
//            JSONObject ob = new JSONObject(jString).getJSONObject("desorden");
//            JSONArray jArray = ob.getJSONArray("Referencia");
//
//            int i = 0;
//            while (!jArray.isNull(i)) {
//                String stringReference = jArray.getString(i);
//                //Log.d("REFERENCE_STRING:", stringReference);
//                //aux = objTest.getString("name");
//                //Log.d("gson46.1",aux);
//
//                Reference reference = gson.fromJson(stringReference, Reference.class);
//                //Log.d("gson46.1",aux);
//                references.add(reference);
//                i++;
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return references;
//    }
//
//
//
//
//
//
//    private Mortalidade getMortalidade(String jString) {
//
//        Gson gson = new Gson();
//        Mortalidade mortalidade = null;
//
//
//
//            try {
//
//                JSONObject ob = new JSONObject(jString).getJSONObject("desorden");
//                JSONArray jArray = ob.getJSONArray("Mortalidade");
//                int i=0;
//                while (!jArray.isNull(i)) {
//                    String stringSign = jArray.getString(i);
//                    //Log.d("SIGN_STRING:", stringSign);
//                    //aux = objTest.getString("name");
//                    Log.d("json", stringSign);
//
//                    mortalidade = gson.fromJson(stringSign, Mortalidade.class);
//                    Log.d("Teste", mortalidade.getAno2004());
//                    //mortalidade.add(mortalit);
//                    i++;
//                }
//
//        } catch (JSONException e) {
//
//            e.printStackTrace();
//        }
//        return mortalidade;
//    }
//
//    private DadosNacionais getDadosNacionais(String jsonResult) {
//
//        DadosNacionais dadosNacionais = null;
//
//        try {
//            JSONObject jObj = new JSONObject(jsonResult);
//            String dadosNacionaisString = jObj.getJSONObject("desorden").getString("DadosNacionai");
//            Log.d("PMODEL: ", dadosNacionaisString);
//            dadosNacionais = gson.fromJson(dadosNacionaisString, DadosNacionais.class);
//        } catch (JSONException e) {
//            Log.d("PMODEL: ", "Erro na conversão dos dados nacionais");
//            e.printStackTrace();
//        }
//
//        return dadosNacionais;
//    }
//
//    private Cid getCid(String jsonResult) {
//
//        Cid cid = null;
//
//        try {
//            JSONObject jObj = new JSONObject(jsonResult);
//            String cidString = jObj.getJSONObject("desorden").getString("cids");
//            Log.d("CIDMODEL: ", cidString);
//            cid = gson.fromJson(cidString, Cid.class);
//            //Log.d("CIDMODEL: ", cidString);
//            //Log.d("CIDMODELid: ", cid.getId());
//        } catch (JSONException e) {
//            Log.d("cidODEL: ", "Erro na conversão dos dados nacionais");
//            e.printStackTrace();
//        }
//
//        return cid;
//    }
//
//
//    private String toString(InputStream is) throws IOException {
//        byte[] bytes = new byte[1024];
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        int lidos;
//        while ((lidos = is.read(bytes)) > 0) {
//            baos.write(bytes, 0, lidos);
//        }
//        return new String(baos.toByteArray());
//    }
}
