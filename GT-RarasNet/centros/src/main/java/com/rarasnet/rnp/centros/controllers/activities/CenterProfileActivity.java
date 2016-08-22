package com.rarasnet.rnp.centros.controllers.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.rarasnet.rnp.centros.R;
import com.rarasnet.rnp.shared.common.intents.DesordensIntent;
import com.rarasnet.rnp.shared.common.intents.ProfissionaisIntent;
import com.rarasnet.rnp.shared.models.Center;
import com.rarasnet.rnp.shared.models.CenterProfile;
import com.rarasnet.rnp.shared.models.CenterProfileModel;
import com.rarasnet.rnp.shared.models.DadosNacionais;
import com.rarasnet.rnp.shared.models.Professional;
import com.rarasnet.rnp.shared.views.adapters.DadosNacionaisAdapter;
import com.rarasnet.rnp.shared.views.adapters.ProfessionalsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ronnyery Barbosa on 19/08/2015.
 */
public class CenterProfileActivity extends Activity implements View.OnClickListener {


    private static List<Professional> professional = new ArrayList<Professional>();
    private static Center center;
    private static List<DadosNacionais> dadosNacionais = new ArrayList<DadosNacionais>();

    private static DadosNacionaisAdapter dadosNacionaisAdapter;
    private static ProfessionalsAdapter professionalsAdapter;


    String centerID;
    String profeID;
    String diseaseID;

    TextView centerCidade;
    TextView centerEstado;
    TextView centerEmail;
    // TextView centerfName;
    TextView centerTelefone;
    TextView centerEndereco;
    TextView centerEspecialidade;
    TextView centerName;
    TextView centerAtendimentoI;
    TextView centerAtendimentoF;



    TextView signImagens;

    ImageButton tel;

    public static void setProfessional(List<Professional> professional) {
        CenterProfileActivity.professional = professional;
    }

    public static void setCenter(Center center) {
        CenterProfileActivity.center = center;
    }

    public static void setDadosNacionais(List<DadosNacionais> dadosNacionais) {
        CenterProfileActivity.dadosNacionais = dadosNacionais;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.center_info);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

       /* diseaseName = (TextView) findViewById(R.id.diseaseName);
        diseaseOrphanumber = (TextView) findViewById(R.id.diseaseOrphanum);
        diseaseExpertlink = (TextView) findViewById(R.id.diseaseExpertlink);*/
        centerName = (TextView) findViewById(R.id.center_perfil_setname);
        centerCidade = (TextView) findViewById(R.id.center_perfil_setcidade);
        centerEstado = (TextView) findViewById(R.id.center_perfil_setestado);
        centerEmail = (TextView) findViewById(R.id.center_perfil_setemail);
        centerEndereco = (TextView) findViewById(R.id.center_perfil_setendereco);
        centerTelefone = (TextView) findViewById(R.id.center_perfil_settel);
        centerEspecialidade = (TextView) findViewById(R.id.center_perfil_setespecialidade);
        centerAtendimentoI = (TextView) findViewById(R.id.center_perfil_sethorarioI);
        centerAtendimentoF = (TextView) findViewById(R.id.center_perfil_sethorarioF);


        tel = (ImageButton) findViewById(R.id.tel_image);

        tel.setOnClickListener(this);


        // signImagens= (TextView) findViewById(R.id.sing_image);
        //orphan/umber.setText(sing.getName());
        //Log.d("testanso",singaaaa.getName());


        Intent intent = getIntent();
        if (intent != null) {
            Bundle params = intent.getExtras();
            if (params != null) {

                // diseaseName.setText(params.getString("nome"));
                // signname.setText(params.getString("namesign"));

                centerID = params.getString("id");
                Log.d("centet Idss", centerID);

                /*diseaseOrphanumber.setText(params.getString("orphanumber"));
                diseaseExpertlink.setText(params.getString("expertlink"));
                diseaseExpertlink.setClickable(true);
                diseaseExpertlink.setMovementMethod(LinkMovementMethod.getInstance());
                String text = "<a href='" + params.getString("expertlink") + "'> Consultar OrphaNet </a>";
                diseaseExpertlink.setText(Html.fromHtml(text));*/


            }

        }
        SearchProfileTask searchTask = new SearchProfileTask();
        searchTask.execute(centerID);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_center_return, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {


        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;

        }else if(item.getItemId() == R.id.action_search) {
            Intent intent = new Intent(this, SearchCentersdepreActivity.class);
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

        if(v.getId() == R.id.tel_image) {
            Uri uri = Uri.parse("tel:" + centerTelefone);
            Intent ligar = new Intent(Intent.ACTION_DIAL, uri);
            startActivity(ligar);
        }
    }


    class SearchProfileTask extends AsyncTask<String, String, CenterProfile> {

        private ProgressDialog pDialog;

        //Implementar validação de negócio aqui?
        //Bloquear listener até o término da execução aqui?
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Log.d("center_info", "==12");
            pDialog = new ProgressDialog(CenterProfileActivity.this);
            pDialog.setMessage("Carregando Informações...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected CenterProfile doInBackground(String... params) {
            //Log.d("center_info", "==14");
            String signID = params[0];
            CenterProfileModel centerProfileModel = new CenterProfileModel();
            CenterProfile profile = null;

            try {
                profile = centerProfileModel.getProfile(signID,"");
            } catch (Exception e) {

            }
//            Log.d("testess", profile.getName());
            return profile;
        }

        protected void onPostExecute(CenterProfile profile) {
            pDialog.dismiss();
            //
            setProfessional(profile.getProfessional());

            setCenter(profile.getCenter());
            setDadosNacionais(profile.getDisorders());



            rendeSignItem();

            Log.d("3", "");


            //Renderiza ListView de Sinônimos
            // renderCurrentItem(0);
        }


    }

    public void rendeSignItem() {

        centerName.setText(center.getNome());
        centerCidade.setText(center.getCidade());
        centerEstado.setText(center.getEstado());
        //centerEmail.setText(center.);
        centerEspecialidade.setText(center.getEspecialidades());


        centerTelefone.setText(center.getTelefone());
        centerEndereco.setText(center.getEndereco());
        centerAtendimentoI.setText(center.getHorario_atendimento_inicial());
        centerAtendimentoF.setText(center.getHorario_atendimento_final());
        // centerEstado.setText(center.getEstado());

        if (dadosNacionais == null || dadosNacionais.isEmpty()) {
			Log.d("testess44444", "sdsdsdsdsfdfds");

            // TextView notFound = (TextView) findViewById(R.id.sing_discricao);

            // notFound.setText(getString(R.string.sign_detalhe));


        } else {


           /* ListView dadosList = (ListView) findViewById(R.id.center_perfil_listprofissionais);
            dadosNacionaisAdapter = new DadosNacionaisAdapter(this, R.layout.item_dados,
                    dadosNacionais);
            dadosList.setAdapter(dadosNacionaisAdapter);
            dadosList.setOnItemClickListener(new OnItemDadosViewItem());*/


        }

        if (professional.isEmpty()) {
            // TextView notFound = (TextView) findViewById(R.id.sing_discricao);

            // notFound.setText(getString(R.string.sign_detalhe));


        } else {


           /* ListView dadosList = (ListView) findViewById(R.id.profe_perfil_listdisease);
            professionalsAdapter = new ProfessionalsAdapter(this, R.layout.item_professional,
                    professional);
            dadosList.setAdapter(professionalsAdapter);
            dadosList.setOnItemClickListener(new OnItemProfissionaisViewItem());*/


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

    private class OnItemDadosViewItem implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Context context = view.getContext();

            diseaseID = String.valueOf(dadosNacionaisAdapter.getItemDadosId(position));
            Log.d("teste disiase center", diseaseID);


            /*//Toast.makeText(Diseases.this, ID, Toast.LENGTH_LONG).show();
            Log.d("teste id", diseaseID);
            Intent i = new Intent(ProfessionalsInfo.this , DiseaseProfile.class);

            i.putExtra("diseaseID", diseaseID);
            startActivity(i);*/


            DesordensIntent intent = new DesordensIntent(DesordensIntent.ACTION_PROFILE);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("id", diseaseID);

            startActivity(intent);

        }
    }


    private class OnItemProfissionaisViewItem implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Context context = view.getContext();

            profeID = String.valueOf(professionalsAdapter.getItemProfeId(position));


            /*//Toast.makeText(Diseases.this, ID, Toast.LENGTH_LONG).show();
            Log.d("teste id", diseaseID);
            Intent i = new Intent(ProfessionalsInfo.this , DiseaseProfile.class);

            i.putExtra("diseaseID", diseaseID);
            startActivity(i);*/


            ProfissionaisIntent intent = new ProfissionaisIntent(ProfissionaisIntent.ACTION_PROFILE);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("id", profeID);

            startActivity(intent);

        }
    }


}


