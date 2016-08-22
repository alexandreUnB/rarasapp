package com.rarasnet.rnp.shared.network.connections;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Farina on 6/1/2015.
 *
 * Classe que representa a conexão com o Webservice do RarasNet.
 */
public class RarasConnection {
    private final String TAG = this.getClass().getSimpleName();
    //protected static final String WEBSERVICE_ADDRESS = "http://192.168.56.2/webservice/rest_desordens/";
    protected static final String WEBSERVICE_ADDRESS = "http://www.webservice.rederaras.org";
    protected Gson gson;
    protected URLConnection conn;

    public static final String ACTION_LOGIN = "/rest_user/login.json";
    public static final String ACTION_REGISTER = "/rest_user/register.json";
    public static final String ACTION_USER = "/rest_user/user.json";
    public static final String ACTION_SEARCH_PROTOCOLS = "/rest_dados_nacionais/search.json";

    public static final String ACTION_SEARCH_PROFESSIONALS = "/rest_profissionais.json";
    public static final String ACTION_PROFILE_PROFESSIONALS = "/rest_profissionais/profile.json";

    public static final String ACTION_SEARCH_CENTERS = "/rest_instituicoes.json";

     public RarasConnection(String webServiceAction) {
        gson = new Gson();
         Log.d("a","a");

        try {
            URL url = new URL(WEBSERVICE_ADDRESS + webServiceAction);
            Log.d("a",url.toString());
            conn = url.openConnection();

            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json;charset=UTF-8");
        } catch (java.net.MalformedURLException e) {
            Log.d(TAG+"1", e.toString());
        } catch (IOException e) {
            Log.d(TAG+"2", e.toString());
        }
    }

    protected String toString(InputStream is) throws IOException {
        byte[] bytes = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int lidos;
        while ((lidos = is.read(bytes)) > 0) {
            baos.write(bytes, 0, lidos);
        }
        return new String(baos.toByteArray());
    }

    public <T> void sendRequest(T request, Class<T> requestType) {
        try {

            String jsonString = gson.toJson(request, requestType);
            Log.d("sendreques", jsonString);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            //Log.d("sendreques", wr.getEncoding());
            wr.write(jsonString);
            Log.d("passo", "10");
            wr.flush();
        } catch (Exception e) {
            Log.d(TAG+"4", e.getMessage());
        }
    }

    public <E> E getResponse(Class<E> responseType) {
        E response = null;
        String jsonString = null;
        try {

            Log.d("passo","11");
            InputStream in = new BufferedInputStream(conn.getInputStream());
            Log.d("passo","11.1");
            jsonString = toString(in);
            Log.d("passo23",jsonString);
            Log.d(TAG, jsonString);
            in.close();

            response = gson.fromJson(jsonString, responseType);
            Log.d(TAG+"4", response.toString());
        }  catch (JsonSyntaxException e) {
            Log.d(TAG+"5", e.getMessage());
            return null;
        } catch (NullPointerException e) {
            Log.d(TAG, e.toString());
            return null;
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
        return response;
    }

}
