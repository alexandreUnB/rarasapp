package com.rarasnet.rnp.desordens.search.models;

import android.util.Log;

import com.google.gson.Gson;
import com.rarasnet.rnp.shared.models.Center;
import com.rarasnet.rnp.shared.models.DadosNacionais;
import com.rarasnet.rnp.shared.models.Disorder;
import com.rarasnet.rnp.shared.models.Professional;

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


    Gson gson = new Gson();

    public DisorderProfileModel() {

    }

    public DisorderProfile getProfile(String diseaseID) {

        String searchURL = urlPrefix + diseaseID + ".json";
        DefaultHttpClient httpClient = new DefaultHttpClient();
        Disorder disorder = null;

        Mortalidade mortalidade = null;
        List<Synonym> synonyms = null;
        List<Sign> signs = null;
        List<Reference> references = null;
        List<Professional> professionals = null;
        List<Center> centers = null;
        DadosNacionais dadosNacionais = null;
        Cid cid = null;

        String jsonResult;
        DisorderProfile profile;


        try {


            HttpResponse response = httpClient.execute(new HttpGet(searchURL));
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                InputStream instream = entity.getContent();
                jsonResult = toString(instream);
                Log.d("JSON_STRING_RESULT:", jsonResult);
                instream.close();

                disorder = getDisorder(jsonResult);
                mortalidade = getMortalidade(jsonResult);
                synonyms = getSynonyms(jsonResult);
                signs = getSigns(jsonResult);
                references = getReferences(jsonResult);
                professionals = getProfessionais(jsonResult);
                centers = getCenter(jsonResult);
                dadosNacionais = getDadosNacionais(jsonResult);
                cid = getCid(jsonResult);
            }

        } catch (Exception e) {

            Log.e("IHHHH", "falhou: " + e);
        }


        profile = new DisorderProfile(disorder, references, signs, synonyms, professionals,
                centers, mortalidade, dadosNacionais, cid);
        return profile;
    }

    private Disorder getDisorder(String jString) {

        Disorder disorder = null;

        try {
            JSONObject jObj = new JSONObject(jString);
            String disorderString =  jObj.getJSONObject("desorden").getString("Desorden");
            Log.d("DISORDER_STRING1:", disorderString);
            disorder = gson.fromJson(disorderString, Disorder.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return disorder;
    }

    private List<Synonym> getSynonyms(String jString) {
        List<Synonym> synonyms = new ArrayList<Synonym>();

        try {


            JSONObject ob = new JSONObject(jString).getJSONObject("desorden");
            JSONArray jArray = ob.getJSONArray("Sinonimo");

            int i = 0;
            while (!jArray.isNull(i)) {
                String stringSynonym = jArray.getString(i);
                //Log.d("SYNONYM_STRING:", stringSynonym);

                Synonym syn = gson.fromJson(stringSynonym, Synonym.class);
                synonyms.add(syn);
                i++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return synonyms;
    }

    private List<Sign> getSigns(String jString) {
        List<Sign> signs = new ArrayList<Sign>();
        Gson gson = new Gson();


        try {

            JSONObject ob = new JSONObject(jString).getJSONObject("desorden");
            JSONArray jArray = ob.getJSONArray("Sinais");

            int i = 0;
            while (!jArray.isNull(i)) {
                String stringSign = jArray.getString(i);
                //Log.d("SIGN_STRING:", stringSign);
                //aux = objTest.getString("name");
                //Log.d("gson46.1",aux);

                Sign sign = gson.fromJson(stringSign, Sign.class);
                signs.add(sign);
                i++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return signs;
    }

    private List<Reference> getReferences(String jString) {
        List<Reference> references = new ArrayList<Reference>();

        try {

            JSONObject ob = new JSONObject(jString).getJSONObject("desorden");
            JSONArray jArray = ob.getJSONArray("Referencia");

            int i = 0;
            while (!jArray.isNull(i)) {
                String stringReference = jArray.getString(i);
                //Log.d("REFERENCE_STRING:", stringReference);
                //aux = objTest.getString("name");
                //Log.d("gson46.1",aux);

                Reference reference = gson.fromJson(stringReference, Reference.class);
                //Log.d("gson46.1",aux);
                references.add(reference);
                i++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return references;
    }

    private List<Professional> getProfessionais(String jString) {
        List<Professional> professionals = new ArrayList<Professional>();
        Gson gson = new Gson();

        try {

            JSONObject ob = new JSONObject(jString).getJSONObject("desorden");
            JSONArray jArray = ob.getJSONArray("Profissionai");

            int i = 0;
            while (!jArray.isNull(i)) {
                String stringSign = jArray.getString(i);
                //Log.d("SIGN_STRING:", stringSign);
                //aux = objTest.getString("name");
                //Log.d("gson46.1",aux);

                Professional sign = gson.fromJson(stringSign, Professional.class);
                Log.d("gson46.1", sign.getNome());
                professionals.add(sign);
                i++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return professionals;
    }

    private List<Center> getCenter(String jString) {
        List<Center> centers = new ArrayList<Center>();
        Gson gson = new Gson();

        try {

            JSONObject ob = new JSONObject(jString).getJSONObject("desorden");
            JSONArray jArray = ob.getJSONArray("Instituico");

            int i = 0;
            while (!jArray.isNull(i)) {
                String stringSign = jArray.getString(i);
                //Log.d("SIGN_STRING:", stringSign);
                //aux = objTest.getString("name");
                //Log.d("gson46.1",aux);

                Center sign = gson.fromJson(stringSign, Center.class);
                Log.d("gson46.1", sign.getNome());
                centers.add(sign);
                i++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return centers;
    }


    private Mortalidade getMortalidade(String jString) {

        Gson gson = new Gson();
        Mortalidade mortalidade = null;



            try {

                JSONObject ob = new JSONObject(jString).getJSONObject("desorden");
                JSONArray jArray = ob.getJSONArray("Mortalidade");
                int i=0;
                while (!jArray.isNull(i)) {
                    String stringSign = jArray.getString(i);
                    //Log.d("SIGN_STRING:", stringSign);
                    //aux = objTest.getString("name");
                    Log.d("json", stringSign);

                    mortalidade = gson.fromJson(stringSign, Mortalidade.class);
                    Log.d("Teste", mortalidade.getAno2004());
                    //mortalidade.add(mortalit);
                    i++;
                }

        } catch (JSONException e) {

            e.printStackTrace();
        }
        return mortalidade;
    }

    private DadosNacionais getDadosNacionais(String jsonResult) {

        DadosNacionais dadosNacionais = null;

        try {
            JSONObject jObj = new JSONObject(jsonResult);
            String dadosNacionaisString = jObj.getJSONObject("desorden").getString("DadosNacionai");
            Log.d("PMODEL: ", dadosNacionaisString);
            dadosNacionais = gson.fromJson(dadosNacionaisString, DadosNacionais.class);
        } catch (JSONException e) {
            Log.d("PMODEL: ", "Erro na conversão dos dados nacionais");
            e.printStackTrace();
        }

        return dadosNacionais;
    }

    private Cid getCid(String jsonResult) {

        Cid cid = null;

        try {
            JSONObject jObj = new JSONObject(jsonResult);
            String cidString = jObj.getJSONObject("desorden").getString("cids");
            Log.d("CIDMODEL: ", cidString);
            cid = gson.fromJson(cidString, Cid.class);
            //Log.d("CIDMODEL: ", cidString);
            //Log.d("CIDMODELid: ", cid.getId());
        } catch (JSONException e) {
            Log.d("cidODEL: ", "Erro na conversão dos dados nacionais");
            e.printStackTrace();
        }

        return cid;
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
