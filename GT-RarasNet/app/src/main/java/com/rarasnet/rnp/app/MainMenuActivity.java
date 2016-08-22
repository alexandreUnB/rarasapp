package com.rarasnet.rnp.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.rarasnet.rnp.documentos.MainActivity;
import com.rarasnet.rnp.info_projeto.About;
import com.rarasnet.rnp.info_projeto.Contact;
import com.rarasnet.rnp.shared.usuario.controllers.activities.UserActivity;
import com.rarasnet.rnp.shared.util.RarasNetPreferenceUtil;


/**
 * Created by Farina on 4/22/2015.
 *
 * Menu Principal da Aplicação.
 * Direciona o usuário para o subsistema requisitado
 */
public class MainMenuActivity extends AppCompatActivity {

    public static final int MAIN_REQUEST = 1002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.act_menu_tb_mainToolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(getString(R.string.act_menu_tb_title));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_reorder_white_24dp);
        Log.d("pass main", RarasNetPreferenceUtil.getPreference(RarasNetPreferenceUtil.PASSW, "PASSW"));




        CardView mDoencas = (CardView) findViewById(R.id.act_menu_cv_doencas);
        mDoencas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainMenuActivity.this, com.rarasnet.rnp.shared.disease.search.controllers.activities.SearchDisordersActivity.class);
                startActivity(it);
            }
        });

        CardView mProfissionais = (CardView) findViewById(R.id.act_menu_cv_profissionais);
        mProfissionais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainMenuActivity.this, com.rarasnet.rnp.shared.profissionais.controllers.activities.SearchProfissionaisActivity.class);
                Toast.makeText(MainMenuActivity.this, "Busca por nome do Profissional.",
                        Toast.LENGTH_LONG).show();
                startActivity(it);

            }
        });

        CardView mCentros = (CardView) findViewById(R.id.act_menu_cv_centros);
        mCentros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainMenuActivity.this, com.rarasnet.rnp.shared.center.controllers.activities.SearchCentersActivity.class);
                Toast.makeText(MainMenuActivity.this, "Busca por nome do Centro de Referência.",
                        Toast.LENGTH_LONG).show();
                startActivity(it);

            }
        });

        CardView mProtocolos = (CardView) findViewById(R.id.act_menu_cv_protocolos);
        mProtocolos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainMenuActivity.this, MainActivity.class);
                startActivity(it);
            }
        });

        CardView mLegislacao = (CardView) findViewById(R.id.act_menu_cv_legislacao);
        mLegislacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainMenuActivity.this, com.rarasnet.rnp.info_projeto.MainActivity.class);
                startActivity(it);
            }
        });

        CardView mMeusDados = (CardView) findViewById(R.id.act_menu_cv_meusDados);
        mMeusDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(MainMenuActivity.this, UserActivity.class);
                startActivity(it);
                //Toast.makeText(getApplicationContext(), "Em desenvolvimento" , Toast.LENGTH_SHORT).show();
            }
        });

        CardView mContato = (CardView) findViewById(R.id.act_menu_cv_contato);
        mContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainMenuActivity.this, Contact.class);
                startActivity(it);
            }
        });

        CardView mSobreNos = (CardView) findViewById(R.id.act_menu_cv_sobreNos);
        mSobreNos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainMenuActivity.this, About.class);
                startActivity(it);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_logout);
        searchItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                RarasNetPreferenceUtil.savePreference(RarasNetPreferenceUtil.IS_LOGGED, false);
                Intent it = new Intent(MainMenuActivity.this, InitialActivity.class);
                it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(it);
                finish();
                return true;
            }
        });
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }

    private void returnResult (int resultStatus) {
        setResult(resultStatus);
        finish();
    }

    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        returnResult(RESULT_OK);
    }





}
