package com.rarasnet.rnp.shared.models;

import android.util.Log;

import com.google.gson.Gson;
import com.rarasnet.rnp.shared.disease.profile.description.Specialty;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ronnyery Barbosa on 17/08/2015.
 */
public class ProfessionalProfileModel {

    // private String urlPrefix = "http://www.rederaras.unb.br/webservice/rest_signs/";
    private String urlPrefix = "http://www.rederaras.org/web/webservice/rest_profissionais/";
    //private String urlPrefix = "http://192.168.85.1/raras/webservice/rest_profissionais/";
    private String url = "http://192.168.0.118:8080/api/professionalID/";


    Gson gson = new Gson();
    public ProfessionalProfileModel() {

    }





    // NEW PART OF THE CODE
    /**
     * Method responsible to se Profile according to its professional id
     * @param signID
     * @param singType
     * @return
     */
    public ProfessionalProfile getProfileNew(String signID, String singType)
    {
        String searchURL = url + signID;
        List<DadosNacionais> disease = null;
        List<Center> center = null;
        List<Specialty> specialties = null;
        String jsonResult;
        ProfessionalProfile profile;
        Professional professional = new Professional();

        try {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            // Making a request to url and getting response
            jsonResult = sh.makeServiceCall(searchURL, ServiceHandler.GET);

            // In case we get a response, the json info received is parsed
            // and passed to and equivalent object
            // and passed to and equivalent object
            Log.d("[ProPModel]JSON", jsonResult);
            if(jsonResult != null){
                professional = getProfessionalNew(jsonResult);
                //center = getCenter(jsonResult);
                specialties = getSpecialties(jsonResult);
                disease = getDiseaseNew(jsonResult);
            }
        } catch (Exception e) {

            Log.e("[ProPModel]GET", "error " + e);
        }

        profile = new ProfessionalProfile(professional, disease, center, specialties);

        return profile;


    }

    /**
     * Parses Json response from server
     * to get the professional information
     * @param jString
     * @return
     */
    private Professional getProfessionalNew(String jString) {

        Professional professional = new Professional();
        Gson g = new Gson();

        try {
            JSONObject jObj = new JSONObject(jString);
            String disorderString = jObj.getString("professional");

            professional = gson.fromJson(disorderString, Professional.class);

            Log.d("[ProPModel]JSONParse", professional.getId());

        } catch (JSONException e) {
            Log.d("[ProPModel]JsonProf: ", e.getMessage());
            e.printStackTrace();
        }
        return professional;
    }


    private List<DadosNacionais> getDiseaseNew(String jString) {
        List<DadosNacionais> dadosNacionais = new ArrayList<DadosNacionais>();

        try {
            JSONObject ob = new JSONObject(jString);
            JSONArray jArray = ob.getJSONArray("professionalDisorders");

            Log.d("[ProPModel]DiseaseParse", jArray.toString());

            int i = 0;
            while (!jArray.isNull(i)) {
                String stringSynonym = jArray.getString(i);
                DadosNacionais dados = gson.fromJson(stringSynonym, DadosNacionais.class);
                Log.d("[ProPModel]DiseaseName", dados.getName());
                dadosNacionais.add(dados);
                i++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return dadosNacionais;
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



// CODIGO LEGADO
//    public ProfessionalProfile getProfile(String signID,String singType) {
//
//
//        String searchURL = urlPrefix + signID + ".json";
//        Log.d("URL",searchURL);
//        DefaultHttpClient httpClient = new DefaultHttpClient();
//        //Sign sign = null;
//        // List<Synonym> synonyms = null;
//        //List<Sign> signs = null;
//        List<DadosNacionais> disease = null;
//        List<Center> center = null;
//        String jsonResult;
//        ProfessionalProfile profile;
//        Professional professional = new Professional();
//
//
//        try {
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
//                professional = getProfessional(jsonResult);
//
//                center = getCenter(jsonResult);
//                //disease = getDisease(jsonResult);
//                //  disease = getDisease(jsonResult);
//                //references = getReferences(jsonResult);
//
//            }
//
//        } catch (Exception e) {
//
//            Log.e("IHHHH", "falhou: " + e);
//        }
//
//        profile = new ProfessionalProfile(professional, disease,center);
//        return profile;
//
//
//    }



//
//    private Professional getProfessional(String jString) {
//
//        Professional professional = new Professional();
//        Gson g = new Gson();
//
//        try {
//            JSONObject jObj = new JSONObject(jString);
//            String disorderString = jObj.getString("Profissionai");
//
//            professional = gson.fromJson(disorderString, Professional.class);
//            Log.d("test:", "json1");
//
//            Log.d("test:", professional.getId());
//            Log.d("test:", "json1");
//
//        } catch (JSONException e) {
//            Log.d("LoiraoGAy", e.getMessage());
//            e.printStackTrace();
//        }
//        return professional;
//    }


//
//    private List<DadosNacionais> getDisease(String jString) {
//        List<DadosNacionais> dadosNacionais = new ArrayList<DadosNacionais>();
//
//
//        try {
//
//
//            JSONObject ob = new JSONObject(jString);
//            JSONArray jArray = ob.getJSONArray("DadosNacionai");
//
//            Log.d("dados nacionais:", jArray.toString());
//            int i = 0;
//            while (!jArray.isNull(i)) {
//                String stringSynonym = jArray.getString(i);
//
//
//                DadosNacionais dados = gson.fromJson(stringSynonym, DadosNacionais.class);
//                Log.d("dados nacionais 2:", dados.getDesorden_id());
//                dadosNacionais.add(dados);
//                i++;
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return dadosNacionais;
////    }
//private List<Center> getCenter(String jString) {
//    List<Center> dadosNacionais = new ArrayList<Center>();
//
//    try {
//
//
//        JSONObject ob = new JSONObject(jString);
//        JSONArray jArray = ob.getJSONArray("Instituicao");
//
//        Log.d("intituicao:", jArray.toString());
//        int i = 0;
//        while (!jArray.isNull(i)) {
//            String stringSynonym = jArray.getString(i);
//
//
//            Center dados = gson.fromJson(stringSynonym, Center.class);
//            Log.d("dados nacionais 2:", dados.getNome());
//            dadosNacionais.add(dados);
//            i++;
//        }
//    } catch (JSONException e) {
//        e.printStackTrace();
//    }
//
//    return dadosNacionais;
//}
//
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

