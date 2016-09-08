package com.rarasnet.rnp.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.rarasnet.rnp.autenticacao.controllers.activities.LoginActivity;
import com.rarasnet.rnp.shared.util.RarasNetPreferenceUtil;

/**
 * Verdadeiro ponto de inicialização do sistema, independentemente da forma que a aplicação tenha sido
 * inicializada.
 *
 * É a Activity responsável pela comunicação com o <a>subsistema de autenticação</a>, e o gerenciamento do
 * resultado desta.
 *
 * Controla o acesso do usuário à aplicação.
 *
 * @see com.rarasnet.rnp.autenticacao.controllers.activities
 * @see com.rarasnet.rnp.app.MainMenuActivity
 *
 */

public class InitialActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if(RarasNetPreferenceUtil.getPreference(RarasNetPreferenceUtil.IS_LOGGED, false)) {
//            Log.d("login2",RarasNetPreferenceUtil.getPreference(RarasNetPreferenceUtil.LOGIN,"LOGIN"));
//            Log.d("pass",RarasNetPreferenceUtil.getPreference(RarasNetPreferenceUtil.PASSW,"PASSW"));
//            startUserActivity();
//        } else{
//
//            Log.d("login23",RarasNetPreferenceUtil.getPreference(RarasNetPreferenceUtil.LOGIN,"LOGIN"));
//           // Log.d("login",RarasNetPreferenceUtil.getPreference(RarasNetPreferenceUtil.PASSW,"PASSW"));
//            startLoginActivity();
//        }
//
//        finish();
        Intent it = new Intent("com.rnp.app.action.main_menu");
        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(it);
        finish();
    }

    private void startUserActivity() {
        Intent i = new Intent(this, MainMenuActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(i, MainMenuActivity.MAIN_REQUEST);
    }

    private void startLoginActivity() {
        Intent i = new Intent(this, LoginActivity.class);
        Log.d("login",RarasNetPreferenceUtil.getPreference(RarasNetPreferenceUtil.LOGIN,"LOGIN"));
        startActivityForResult(i, LoginActivity.LOGIN_REQUEST);

    }


    /**
     * Ponto de retorno do subsistema de autenticação e do menu principal da aplicação.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     *
     * @see com.rarasnet.rnp.autenticacao.controllers.activities
     * @see com.rarasnet.rnp.app.MainMenuActivity
     *
     */


}
