package com.rarasnet.rnp.autenticacao.controllers.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.rarasnet.rnp.autenticacao.R;
import com.rarasnet.rnp.autenticacao.controllers.network.tasks.LoginTask;
import com.rarasnet.rnp.shared.network.responses.GenericResponse;
import com.rarasnet.rnp.shared.util.ConnectionDetector;
import com.rarasnet.rnp.shared.util.RarasNetPreferenceUtil;

/**
 *  Activity de Login do Usuário
 *
 */

public class LoginActivity extends FragmentActivity {

    public static final int LOGIN_REQUEST = 1001;
    private String log;
    private String pass;

    private ProgressBar pb_carregando;
    TextView loginButton;
    TextView registerButton;
    ConnectionDetector cd = new ConnectionDetector(this);

    public interface LoginUserCallback {
        public void onLoginTaskCallback(GenericResponse response);
    }

    private LoginUserCallback loginUserCallback = new LoginUserCallback() {
        @Override
        public void onLoginTaskCallback(GenericResponse response) {
            pb_carregando.setVisibility(View.INVISIBLE);

            if (response == null) {
                Toast.makeText(LoginActivity.this, "Erro de conexão com o servidor", Toast.LENGTH_LONG).show();
            } else{
                handleWebServiceResponseError(response.getStatus());

            }

            if (response.getStatus().equals("ok")){
                RarasNetPreferenceUtil.savePreference(RarasNetPreferenceUtil.IS_LOGGED, true);
                RarasNetPreferenceUtil.savePreference(RarasNetPreferenceUtil.LOGIN,log);
                RarasNetPreferenceUtil.savePreference(RarasNetPreferenceUtil.PASSW,pass);
                Log.d("EStou aqui", "aeui");
                Intent it = new Intent("com.rnp.app.action.main_menu");
                it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(it);
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pb_carregando = (ProgressBar) findViewById(R.id.act_login_pb_carregando);
        loginButton = (TextView) findViewById(R.id.act_login_tv_entrar);
        registerButton = (TextView) findViewById(R.id.act_login_tv_cadastrar);
        setLoginButtonListener();
        setRegisterButtonListener();










    }

    private void setLoginButtonListener() {



        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cd.isConnectingToInternet()){

                    AppCompatEditText passwField = (AppCompatEditText) findViewById(R.id.act_login_et_senha);
                    String passw = passwField.getText().toString();

                    AppCompatEditText loginField = (AppCompatEditText) findViewById(R.id.act_login_et_email);
                    String login = loginField.getText().toString();
                    log=login;
                    pass=passw;
                    if(!validateCredentials(login, passw)) {
                        return;
                    }

                    pb_carregando.setVisibility(View.VISIBLE);
                    new LoginTask(loginUserCallback).execute(login, passw);



                }else{

                    Toast.makeText(LoginActivity.this,"Você não está conectado a internet",Toast.LENGTH_LONG).show();


                }


            }
        };
        loginButton.setOnClickListener(listener);
    }

    private void setRegisterButtonListener() {

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cd.isConnectingToInternet()){

                    startActivity(new Intent(LoginActivity.this, RegisterActivity.class));

                }else{

                    Toast.makeText(LoginActivity.this,"Você não está conectado a internett.",Toast.LENGTH_LONG).show();


                }

            }
        };
        registerButton.setOnClickListener(listener);
    }


    private boolean validateCredentials(String login, String passw) {
        if(login.isEmpty() && passw.isEmpty()) {
            Toast.makeText(this, "Para entrar, digite seu login e senha", Toast.LENGTH_SHORT).show();
            return false;
        } else if(login.isEmpty()) {
            Toast.makeText(this, "Campo login vazio", Toast.LENGTH_SHORT).show();
            return false;
        } else if(passw.isEmpty()) {
            Toast.makeText(this, "Campo senha vazio", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private void handleWebServiceResponseError(String errorData) {
        switch (errorData) {

            case "no":
                Toast.makeText(this, "Login ou Senha Incorretos!", Toast.LENGTH_SHORT).show();
                break;
            case "er":
                Toast.makeText(this, "Campo(s) Vazio(s)", Toast.LENGTH_SHORT).show();
                break;


        }
    }

    private void returnResult (int resultStatus) {
        setResult(resultStatus);
        Log.d("UserActivity: ", "Serei Finalizada" );
        finish();
    }







}
