package com.rarasnet.rnp.shared.usuario.controllers.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.rarasnet.rnp.shared.R;
import com.rarasnet.rnp.shared.network.responses.GenericResponse;
import com.rarasnet.rnp.shared.usuario.controllers.Profile;
import com.rarasnet.rnp.shared.usuario.controllers.network.UserAdapter;
import com.rarasnet.rnp.shared.util.RarasNetPreferenceUtil;

/**
 *  Activity de Login do Usuário
 *
 */

public class UserActivity extends AppCompatActivity {

    public static final int LOGIN_REQUEST = 1001;
    private String log;
    private String pass;

    private ProgressBar pb_carregando;

    public interface LoginUserCallback {
        public void onLoginTaskCallback(GenericResponse response);
    }

    private LoginUserCallback loginUserCallback = new LoginUserCallback() {
        @Override
        public void onLoginTaskCallback(GenericResponse response) {
//            pb_carregando.setVisibility(View.INVISIBLE);
            if (response == null) {
                Toast.makeText(UserActivity.this, "Erro de conexão com o servidor", Toast.LENGTH_LONG).show();
            } else if(response.getStatus().equals("er")) {
                handleWebServiceResponseError(response.getData());
            } else {
                RarasNetPreferenceUtil.savePreference(RarasNetPreferenceUtil.IS_LOGGED, true);
                RarasNetPreferenceUtil.savePreference(RarasNetPreferenceUtil.LOGIN,log);
                RarasNetPreferenceUtil.savePreference(RarasNetPreferenceUtil.PASSW,pass);
                Log.d("EStou aqui user", "usando");
                setUser(RarasNetPreferenceUtil.getPreference(RarasNetPreferenceUtil.LOGIN, log),
                        RarasNetPreferenceUtil.getPreference(RarasNetPreferenceUtil.PASSW, pass));
                Intent it = new Intent("com.rnp.app.action.main_menu");
                it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(it);
                finish();
            }
        }
    };

    AppCompatEditText nome;
    AppCompatEditText sobrenome;
    AppCompatEditText email;
    AppCompatEditText login;

    AppCompatTextView tvnome;
    AppCompatTextView tvsobrenome;
    AppCompatTextView tvemail;
    AppCompatTextView tvlogin;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.act_user_tb_mainToolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setTitle("Dados do Usuário");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        nome = (AppCompatEditText) findViewById(R.id.user_et_nome);
        sobrenome = (AppCompatEditText) findViewById(R.id.user_et_sobrenome);
        email = (AppCompatEditText) findViewById(R.id.user_et_email);
        login = (AppCompatEditText) findViewById(R.id.user_et_login);

        nome.setVisibility(View.INVISIBLE);
        sobrenome.setVisibility(View.INVISIBLE);
        email.setVisibility(View.INVISIBLE);
        login.setVisibility(View.INVISIBLE);



        tvnome = (AppCompatTextView) findViewById(R.id.user_tv_nome);
        tvsobrenome= (AppCompatTextView) findViewById(R.id.user_tv_sobrenome);
        tvemail = (AppCompatTextView) findViewById(R.id.user_tv_email);
        tvlogin = (AppCompatTextView) findViewById(R.id.user_tv_login);

        pb_carregando = (ProgressBar) findViewById(R.id.act_user_pb_carregando);

        setUser(RarasNetPreferenceUtil.getPreference(RarasNetPreferenceUtil.LOGIN, log),
                RarasNetPreferenceUtil.getPreference(RarasNetPreferenceUtil.PASSW, pass));




     pb_carregando.setVisibility(View.VISIBLE);
        pb_carregando.setVisibility(View.VISIBLE);
        pb_carregando.getIndeterminateDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);

        ////new UserTask(loginUserCallback).execute(RarasNetPreferenceUtil.getPreference(RarasNetPreferenceUtil.LOGIN,log),
                //RarasNetPreferenceUtil.getPreference(RarasNetPreferenceUtil.PASSW,pass));




       // setLoginButtonListener();
       // setRegisterButtonListener();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setUser(String login, String senha) {
        //TextView loginButton = (TextView) findViewById(R.id.act_login_tv_entrar);
        //View.OnClickListener listener = new View.OnClickListener() {
        // @Override
        //// public void onClick(View v) {

        //AppCompatEditText passwField = (AppCompatEditText) findViewById(R.id.act_login_et_senha);
        //String passw = passwField.getText().toString();

        //AppCompatEditText loginField = (AppCompatEditText) findViewById(R.id.act_login_et_email);
        //String login = loginField.getText().toString();
        ////log=login;
        //pass=passw;
        //if(!validateCredentials(login, passw)) {
        // return;
        //}
        pb_carregando.setVisibility(View.VISIBLE);
        pb_carregando.setVisibility(View.VISIBLE);
        pb_carregando.getIndeterminateDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        LoadUserTask searchTask = new LoadUserTask();
        //Log.d("Profi id antes ", professional.getId());

        searchTask.execute(login, senha);
       pb_carregando.setVisibility(View.VISIBLE);
        //new UserTask(loginUserCallback).execute(login, senha);


        //loginButton.setOnClickListener(listener);
    }

    /*private void setLoginButtonListener() {
        TextView loginButton = (TextView) findViewById(R.id.act_login_tv_entrar);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                new UserTask(loginUserCallback).execute(login, passw);
            }
        };
        loginButton.setOnClickListener(listener);
    }*/

   /* private void setRegisterButtonListener() {
        TextView registerButton = (TextView) findViewById(R.id.act_login_tv_cadastrar);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserActivity.this, RegisterActivity.class));
            }
        };
        registerButton.setOnClickListener(listener);
    }*/


    private boolean validateCredentials(String login, String passw) {
        if(login.isEmpty() && passw.isEmpty()) {
            Toast.makeText(this, "Para entrar, digite seu login e senha", Toast.LENGTH_SHORT).show();
            return false;
        } else if(login.isEmpty()) {
            Toast.makeText(this, "Para entrar, também digite seu login", Toast.LENGTH_SHORT).show();
            return false;
        } else if(passw.isEmpty()) {
            Toast.makeText(this, "Para entrar, também digite sua senha", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private void handleWebServiceResponseError(String errorData) {
        switch (errorData) {

            case "li":
                Toast.makeText(this, "Login não cadastrado!", Toast.LENGTH_SHORT).show();
                break;
            case "si":
                Toast.makeText(this, "Senha inválida", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this, "Erro no servidor", Toast.LENGTH_SHORT).show();

        }
    }

    private void returnResult (int resultStatus) {
        setResult(resultStatus);
        Log.d("UserActivity: ", "Serei Finalizada" );
        finish();
    }

    private class LoadUserTask extends AsyncTask<String, String, Profile> {

        @Override
        protected Profile doInBackground(String... params){
            String  login = params[0];
            String passw = params[1];
            UserAdapter user = new UserAdapter();
            Profile result = null;
           // Log.d("profiss",searchType);

            try {
                result = user.search(login, passw);
                //List<Disease> result = disorders.getStaticDisease();
            } catch (Exception e) {

            }
            return result;
        }

        protected void onPostExecute(Profile user){


            tvnome.setText(user.getNome());
            tvsobrenome.setText(user.getSobrenome());
            tvemail.setText(user.getEmail());
            tvlogin.setText(user.getLogin());
           // email.setText(user.getEmail());


            hideKeyboard();

           // MenuItemCompat.collapseActionView(mToolbarSearchItem);
            //pb_searchProgress.setVisibility(View.INVISIBLE);
           // ac_searchEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, et_search_sendButtonResource, 0);
           // ac_searchEditText.setOnTouchListener(et_search_touchListener);

           //

           // if(disorders == null)
              //  Toast.makeText(SearchProfissionaisActivity.this, "Erro de Conexão"
                 ////       , Toast.LENGTH_LONG).show();
           ////else if(disorders==null)
               // Toast.makeText(SearchProfissionaisActivity.this, "Nenhuma Doença foi encontrada"
               //         , Toast.LENGTH_LONG).show();
            //else
               // UserActivity.this.renderProfessionalList(user);
        }}


   /* private void renderProfessionalList(Profile user) {
        Log.d("TEste", "aqui1");
        mSearchResultsAdapter.clear();
        Log.d("TEste", "aqui2");
        mSearchResultsAdapter.addAll(professionals);
        Log.d("TEste", "aqui3");
        mSearchResultsAdapter.notifyDataSetChanged();
        Log.d("TEste", "aqui4");
        lv_searchResults.setVisibility(View.VISIBLE);
        Log.d("TEste", "aqui5");

        float actionBarSize = getResources().getDimension(R.dimen.abc_action_bar_default_height_material);
        ac_searchEditText.setVisibility(View.GONE);
        shrink(mToolbar, Math.round(actionBarSize), professionals.size());
    }*/

    private void hideKeyboard() {

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }



}
