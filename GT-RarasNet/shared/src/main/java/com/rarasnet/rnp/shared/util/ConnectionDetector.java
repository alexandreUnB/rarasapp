package com.rarasnet.rnp.shared.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Eu mesmo on 22/07/2016.
 */
public class ConnectionDetector {

    private Context context;

    public ConnectionDetector(Context context){
        this.context = context;
    }

    /**
     * Retorna se o app está conectado na internet
     * @return
     */
    public boolean isConnectingToInternet(){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}