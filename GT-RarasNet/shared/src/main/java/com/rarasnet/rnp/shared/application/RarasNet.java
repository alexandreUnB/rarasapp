package com.rarasnet.rnp.shared.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by Farina on 17/8/2015.
 *
 * Aplicação RarasNet
 *
 */
public class RarasNet extends Application {
//    public static String urlPrefix = "http://192.168.0.118:8080";
    private static Context context;
    public static String urlPrefix = "http://rarasbase.rederaras.org";


    @Override
    public void onCreate() {
        super.onCreate();
        RarasNet.context = getApplicationContext();
    }

    public static Context getAppContext() {
        /*if(context == null) {
            RarasNet.context = getApplicationContext();
        } else {
            return context;
        }*/
        return RarasNet.context;
    }
}