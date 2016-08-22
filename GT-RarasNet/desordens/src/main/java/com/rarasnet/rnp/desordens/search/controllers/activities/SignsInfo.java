package com.rarasnet.rnp.desordens.search.controllers.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rarasnet.rnp.desordens.R;
import com.rarasnet.rnp.desordens.search.models.Sign;
import com.rarasnet.rnp.desordens.search.models.SignProfileModel;

/**
 * Created by Ronnyery Barbosa on 29/05/2015.
 */
public class SignsInfo extends Activity {

    private static Sign sing;

    String signID;
    TextView diseaseName;
    TextView diseaseOrphanumber;
    TextView diseaseExpertlink;
    TextView signName;
    TextView signDetalhe;
    TextView signImagens;


    public static void setSing(Sign sing) {
        SignsInfo.sing = sing;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signs_info);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        Intent tt;
        diseaseName = (TextView) findViewById(R.id.diseaseName);
        diseaseOrphanumber = (TextView) findViewById(R.id.diseaseOrphanum);
        diseaseExpertlink = (TextView) findViewById(R.id.diseaseExpertlink);
        signName = (TextView) findViewById(R.id.name_sign);
        //signDetalhe = (TextView) findViewById(R.id.sing_detalhe);
        // signImagens= (TextView) findViewById(R.id.sing_image);
        //orphan/umber.setText(sing.getName());
        //Log.d("testanso",singaaaa.getName());


        Intent intent = getIntent();
        if (intent != null) {
            Bundle params = intent.getExtras();
            if (params != null) {

                diseaseName.setText(params.getString("nome"));
                // signname.setText(params.getString("namesign"));

                signID = params.getString("signsID");
                Log.d("sing test class info", signID);

                diseaseOrphanumber.setText(params.getString("orphanumber"));
                diseaseExpertlink.setText(params.getString("expertlink"));
                diseaseExpertlink.setClickable(true);
                diseaseExpertlink.setMovementMethod(LinkMovementMethod.getInstance());
                String text = "<a href='" + params.getString("expertlink") + "'> Consultar OrphaNet </a>";
                diseaseExpertlink.setText(Html.fromHtml(text));


            }

        }
        SearchProfileTask searchTask = new SearchProfileTask();
        searchTask.execute(signID);

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


    class SearchProfileTask extends AsyncTask<String, String, Sign> {

        private ProgressDialog pDialog;

        //Implementar validação de negócio aqui?
        //Bloquear listener até o término da execução aqui?
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Log.d("center_info", "==12");
            pDialog = new ProgressDialog(SignsInfo.this);
            pDialog.setMessage("Carregando Informações...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected Sign doInBackground(String... params) {
            //Log.d("center_info", "==14");
            String signID = params[0];
            SignProfileModel singProfileModel = new SignProfileModel();
            Sign profile = null;

            try {
                profile = singProfileModel.getProfile(signID);
            } catch (Exception e) {

            }
//            Log.d("testess", profile.getName());
            return profile;
        }

        protected void onPostExecute(Sign profile) {
            pDialog.dismiss();
            //
            setSing(profile);

            Log.d("testess", sing.getName());

            rendeSignItem();

            Log.d("3", "");


            //Renderiza ListView de Sinônimos
            // renderCurrentItem(0);
        }


    }

    public void rendeSignItem() {
        signName.setText(sing.getName());

        if (sing.getDescricao().isEmpty()) {
            TextView notFound = (TextView) findViewById(R.id.sing_discricao);

            notFound.setText(getString(R.string.sign_detalhe));




        } else {


            TextView textView = (TextView) findViewById(R.id.sing_discricao);
            textView.setText(sing.getDescricao());

            RelativeLayout r = (RelativeLayout) findViewById(R.id.sign_frame_detalhe);


        }
        if (sing.getFoto().isEmpty()) {
            TextView notFound = (TextView) findViewById(R.id.sing_imagens);

            notFound.setText(getString(R.string.sign_imagens));




        } else {
            TextView textView = (TextView) findViewById(R.id.sing_imagens);
            textView.setText(sing.getFoto());

            RelativeLayout r = (RelativeLayout) findViewById(R.id.sign_frame_image);
        }

    }


}
