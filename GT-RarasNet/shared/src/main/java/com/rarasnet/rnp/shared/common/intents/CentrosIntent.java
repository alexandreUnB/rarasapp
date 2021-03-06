package com.rarasnet.rnp.shared.common.intents;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Farina on 24/9/2015.
 *
 * Intent para a invocação do subsistema de Centros
 */
public class CentrosIntent extends Intent {

    public static final String ACTION_PROFILE = "com.rarasnet.rnp.centros.action.PROFILE";

    public CentrosIntent(Context context, Class<?> destination) {
        super(context, destination);
    }

    public CentrosIntent(Context context, Class<?> destination, String action) {
        super(context, destination);
        this.setAction(action);
    }

    public CentrosIntent(String action) {
        super(action);
    }
}
