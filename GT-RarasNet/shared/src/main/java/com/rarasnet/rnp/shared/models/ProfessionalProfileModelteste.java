package com.rarasnet.rnp.shared.models;

import android.util.Log;

import com.google.gson.Gson;
import com.rarasnet.rnp.shared.disease.profile.description.Specialty;

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
 * Created by Ronnyery Barbosa on 17/08/2015.
 */
public class ProfessionalProfileModelteste {


        protected URLConnection conn;
        private String urlPrefix = "http://www.rederaras.org/web/webservice/rest_profissionais/";
        //private String urlPrefix = "http://www.webservice.rederaras.org/rest_desordens/";
        //private String urlPrefix = "http://192.168.56.2/webservice/rest_desordens/";


        Gson gson = new Gson();

        public ProfessionalProfileModelteste() {

        }

        public ProfessionalProfile getProfile(String diseaseID) {

            String searchURL = urlPrefix + diseaseID + ".json";
            DefaultHttpClient httpClient = new DefaultHttpClient();
            Professional professional = null;



            List<Disorder> disorders = null;
            List<Center> centers = null;
            List<Specialty> specialties = null;
            List<DadosNacionais> dadosNacionais = null;


            String jsonResult;
            ProfessionalProfile profile;


            try {


                HttpResponse response = httpClient.execute(new HttpGet(searchURL));
                HttpEntity entity = response.getEntity();

                if (entity != null) {
                    InputStream instream = entity.getContent();
                    jsonResult = toString(instream);
                    Log.d("JSON_STRING_RESULT:", jsonResult);
                    instream.close();

                    professional = getProfissionais(jsonResult);

                    //disorders = getDisorder(jsonResult);
                    centers = getCenter(jsonResult);
                    //dadosNacionais = getDadosNacionais(jsonResult);

                }

            } catch (Exception e) {

                Log.e("IHHHH", "falhou: " + e);
            }


            profile = new ProfessionalProfile(professional, dadosNacionais, centers, specialties);
            return profile;
        }

        private Professional getProfissionais(String jString) {

            Professional professional = null;

            try {
                JSONObject jObj = new JSONObject(jString);
                String profeString =  jObj.getString("Profissionai");
                Log.d("PROFE STRIG:", profeString);
                professional = gson.fromJson(profeString, Professional.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return professional;
        }





        private List<Center> getCenter(String jString) {
            List<Center> centers = new ArrayList<Center>();
            Gson gson = new Gson();

            try {

                JSONObject ob = new JSONObject(jString);
                JSONArray jArray = ob.getJSONArray("Instituicao");

                int i = 0;
                while (!jArray.isNull(i)) {
                    String stringSign = jArray.getString(i);
                    Log.d("SIGN_STRING:", stringSign);
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




        private List<DadosNacionais> getDadosNacionais(String jsonResult) {

            List<DadosNacionais> dadosNacionais = new ArrayList<DadosNacionais>();

            try {



                    JSONObject ob = new JSONObject(jsonResult);
                    JSONArray jArray = ob.getJSONArray("DadosNacionai");

                    int i = 0;
                    while (!jArray.isNull(i)) {
                        String stringSign = jArray.getString(i);
                        //Log.d("SIGN_STRING:", stringSign);
                        //aux = objTest.getString("name");
                        //Log.d("gson46.1",aux);

                        DadosNacionais dadosNacionais1 = gson.fromJson(jsonResult, DadosNacionais.class);
                        dadosNacionais.add(dadosNacionais1);
                        i++;
                    }

                //Log.d("PMODEL: ", dadosNacionaisString);
                //dadosNacionais = gson.fromJson(dadosNacionaisString, DadosNacionais.class);
            } catch (JSONException e) {
                Log.d("PMODEL: ", "Erro na conversão dos dados nacionais");
                e.printStackTrace();
            }

            return dadosNacionais;
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
