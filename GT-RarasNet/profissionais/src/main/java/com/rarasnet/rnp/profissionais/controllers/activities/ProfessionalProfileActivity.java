package com.rarasnet.rnp.profissionais.controllers.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.rarasnet.rnp.profissionais.R;
import com.rarasnet.rnp.shared.common.intents.CentrosIntent;
import com.rarasnet.rnp.shared.common.intents.DesordensIntent;
import com.rarasnet.rnp.shared.models.Center;
import com.rarasnet.rnp.shared.models.DadosNacionais;
import com.rarasnet.rnp.shared.models.Professional;
import com.rarasnet.rnp.shared.models.ProfessionalProfile;
import com.rarasnet.rnp.shared.models.ProfessionalProfileModel;
import com.rarasnet.rnp.shared.views.adapters.CentersAdapter;
import com.rarasnet.rnp.shared.views.adapters.DadosNacionaisAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Ronnyery Barbosa on 17/08/2015.
 *
 * Activity para a visualização de informações sobre um determinado profissional de saúde
 */
public class ProfessionalProfileActivity extends Activity implements View.OnClickListener {


    public static Intent getIntent(Context context, ProfessionalProfile profisionalProfile) {
        Intent it = new Intent(context, ProfessionalProfileActivity.class);
        mProfissionalProfile = profisionalProfile;
        return it;
    }

    public static ProfessionalProfile mProfissionalProfile;
    private static Professional professional;
    private static List<Center> center = new ArrayList<Center>();
    private static List<DadosNacionais> dadosNacionaises = new ArrayList<DadosNacionais>();

    private static DadosNacionaisAdapter dadosNacionaisAdapter;
    private static CentersAdapter centersAdapter;

    String profeID;
    String diseaseID;
    String centerID;

    TextView profeCidade;
    TextView profeEstado;
    TextView profeEmail;
    TextView profeName;
    TextView profeSobrenome;

    TextView centerCidade;
    TextView centerEstado;
    TextView centerName;


    TextView signImagens;

    ImageButton mail;

    public static void setProfessional(Professional professional) {
        ProfessionalProfileActivity.professional = professional;
    }

    public static void setCenter(List<Center> center) {
        ProfessionalProfileActivity.center = center;
    }

    public static void setDadosNacionaises(List<DadosNacionais> dadosNacionaises) {
        ProfessionalProfileActivity.dadosNacionaises = dadosNacionaises;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profissional_info);
       // ActionBar actionBar = getActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);

        profeName = (TextView) findViewById(R.id.profe_perfil_setname);
        profeCidade = (TextView) findViewById(R.id.profe_perfil_setcidade);
        profeEstado = (TextView) findViewById(R.id.profe_perfil_setestado);
        profeEmail = (TextView) findViewById(R.id.profe_perfil_setemail);
        profeSobrenome = (TextView) findViewById(R.id.profe_perfil_setsobrenome);

        /*centerEstado = (TextView) findViewById(R.id.center_perfil_setestado);
        centerName = (TextView) findViewById(R.id.center_perfil_setname);
        centerCidade = (TextView) findViewById(R.id.center_perfil_setcidade);*/

        mail = (ImageButton) findViewById(R.id.profe_email);

        mail.setOnClickListener(this);


        // signImagens= (TextView) findViewById(R.id.sing_image);
        //orphan/umber.setText(sing.getName());
        //Log.d("testanso",singaaaa.getName());


        Intent intent = getIntent();
        if (intent != null) {
            Bundle params = intent.getExtras();
            if (params != null) {

                // diseaseName.setText(params.getString("nome"));
                // signname.setText(params.getString("namesign"));

                profeID = params.getString("id");
                Log.d("profe test class info", profeID);

                /*diseaseOrphanumber.setText(params.getString("orphanumber"));
                diseaseExpertlink.setText(params.getString("expertlink"));
                diseaseExpertlink.setClickable(true);
                diseaseExpertlink.setMovementMethod(LinkMovementMethod.getInstance());
                String text = "<a href='" + params.getString("expertlink") + "'> Consultar OrphaNet </a>";
                diseaseExpertlink.setText(Html.fromHtml(text));*/


            }

        }
        SearchProfileTask searchTask = new SearchProfileTask();
        searchTask.execute(profeID);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_profissional_return, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {


        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;

        }else if(item.getItemId() == R.id.action_search) {
            Intent intent = new Intent(this, SearchProfissionaisActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("finish",true);
            startActivity(intent);
            this.finish();
            return true; }
        else {
            return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.profe_email) {
            Intent intent = new Intent(this, SendMail.class);
            Bundle params = new Bundle();
            params.putString("email", professional.getEmail());
            intent.putExtras(params);
            startActivity(intent);
        }

    }


    class SearchProfileTask extends AsyncTask<String, String, ProfessionalProfile> {

        private ProgressDialog pDialog;

        //Implementar validação de negócio aqui?
        //Bloquear listener até o término da execução aqui?
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Log.d("center_info", "==12");
            pDialog = new ProgressDialog(ProfessionalProfileActivity.this);
            pDialog.setMessage("Carregando Informações...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected ProfessionalProfile doInBackground(String... params) {
            //Log.d("center_info", "==14");
            String signID = params[0];
            ProfessionalProfileModel professionalProfileModel = new ProfessionalProfileModel();
            ProfessionalProfile profile = null;

            try {
                profile = professionalProfileModel.getProfileNew(signID,"id");
            } catch (Exception e) {

            }
//            Log.d("testess", profile.getName());
            return profile;
        }

        protected void onPostExecute(ProfessionalProfile profile) {
            pDialog.dismiss();
            //
            setProfessional(profile.getProfessional());
            setCenter(profile.getCenters());
            setDadosNacionaises(profile.getDisorders());

            Log.d("testess", profile.getProfessional().getNome());

            rendeSignItem();

            Log.d("3", "");


            //Renderiza ListView de Sinônimos
            // renderCurrentItem(0);
        }


    }

    public void rendeSignItem() {

        profeName.setText(professional.getNome());
        profeCidade.setText(professional.getCidade());
        profeEstado.setText(professional.getEstado());
        profeEmail.setText(professional.getEmail());
        profeSobrenome.setText(professional.getSobrenome());

      if(center.isEmpty()) {

        }else {
          ListView centerList = (ListView) findViewById(R.id.profe_perfil_listcenter);
          centersAdapter = new CentersAdapter(this, R.layout.item_center,
                  center);
          centerList.setAdapter(centersAdapter);
          centerList.setOnItemClickListener(new OnViewItemCenter());
      }


        if (dadosNacionaises.isEmpty()) {
            // TextView notFound = (TextView) findViewById(R.id.sing_discricao);

            // notFound.setText(getString(R.string.sign_detalhe));


        } else {


          /*  ListView dadosList = (ListView) findViewById(R.id.profe_perfil_listdisease);
            dadosNacionaisAdapter = new DadosNacionaisAdapter(this, R.layout.item_dados,
                    dadosNacionaises);
            dadosList.setAdapter(dadosNacionaisAdapter);
            dadosList.setOnItemClickListener(new OnItemClickListenerListViewItem());*/


        }
       /* if (sing.getFoto().isEmpty()){
            TextView notFound = (TextView) findViewById(R.id.sing_imagens);

            notFound.setText(getString(R.string.sign_imagens));




            RelativeLayout r = (RelativeLayout) findViewById(R.id.sign_frame_image);
            Resources res = getResources();
            Drawable drawable = res.getDrawable(R.drawable.shapetransparencenotfound);
            r.setBackgroundDrawable(drawable);

        }else {
            TextView textView = (TextView)findViewById(R.id.sing_imagens);
            textView.setText(sing.getFoto());

            RelativeLayout r = (RelativeLayout) findViewById(R.id.sign_frame_image);
        }*/

    }

    private class OnItemClickListenerListViewItem implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Context context = view.getContext();

            diseaseID = String.valueOf(dadosNacionaisAdapter.getItemDadosId(position));


            /*//Toast.makeText(Diseases.this, ID, Toast.LENGTH_LONG).show();
            Log.d("teste id", diseaseID);
            Intent i = new Intent(ProfessionalsInfo.this , DiseaseProfile.class);

            i.putExtra("diseaseID", diseaseID);
            startActivity(i);*/


            DesordensIntent intent = new DesordensIntent(DesordensIntent.ACTION_PROFILE);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("id", diseaseID);
            intent.putExtra("EXIT", true);
            startActivity(intent);

        }
    }

    private class OnViewItemCenter implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Context context = view.getContext();

            centerID = String.valueOf(centersAdapter.getItemCenterId(position));


            /*//Toast.makeText(Diseases.this, ID, Toast.LENGTH_LONG).show();
            Log.d("teste id", diseaseID);
        */


            CentrosIntent intent = new CentrosIntent(CentrosIntent.ACTION_PROFILE);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("id", centerID);
            //intent.putExtra("EXIT", true);
            startActivity(intent);

        }
    }

}
