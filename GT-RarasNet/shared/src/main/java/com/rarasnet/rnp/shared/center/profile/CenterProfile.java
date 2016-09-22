package com.rarasnet.rnp.shared.center.profile;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ronnyerybarbosa.library.MaterialDialog;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLngBounds;
import com.rarasnet.rnp.shared.R;
import com.rarasnet.rnp.shared.disease.profile.DisorderProfileActivity;
import com.rarasnet.rnp.shared.disease.search.models.DisorderProfile;
import com.rarasnet.rnp.shared.disease.search.models.DisorderProfileModel;
import com.rarasnet.rnp.shared.models.DadosNacionais;
import com.rarasnet.rnp.shared.models.Professional;
import com.rarasnet.rnp.shared.models.ProfessionalProfileModel;
import com.rarasnet.rnp.shared.profissionais.profile.ProfissionalProfile;
import com.rarasnet.rnp.shared.util.UIUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

//import com.rarasnet.rnp.shared.models.profile.ProfissionalProfile;


/**
 * Created by Ronnyery Barbosa on 05/02/2016.
 */
public class CenterProfile extends AppCompatActivity {


    private GoogleApiClient mGoogleApi;
    private LatLngBounds mBounds;
    private Location mLastLocation;
    private GoogleMap mMap;
    private MapView mMapView;
    private LinearLayout mView;
    MaterialDialog mMaterialDiaolg;

    private AlertDialog progress;

    public static com.rarasnet.rnp.shared.models.CenterProfile mProfissionalProfile;
    private List<DadosNacionais>  dados = mProfissionalProfile.getDisorders();
    private List<Professional> centros = mProfissionalProfile.getProfessional();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


   // ProfissionalProfile disorderProfile = ProfissionalProfile.mDisorderProfile;
    private GoogleApiClient client;
    public static Intent getIntent(Context context, com.rarasnet.rnp.shared.models.CenterProfile profisionalProfile) {
        Intent it = new Intent(context, CenterProfile.class);
        mProfissionalProfile = profisionalProfile;
        return it;
    }

        TextView nome;
        TextView endereco;
        TextView atendimento;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView = (LinearLayout) View.inflate(this, R.layout.activity_center_profile, null);
        setContentView(mView);
      // setText();

        nome = (TextView) findViewById(R.id.act_center_profile_tv_mainCardTitle);
        endereco = (TextView) findViewById(R.id.act_center_profile_tv_mainCardSubTitle);
        atendimento = (TextView) findViewById(R.id.act_center_profile_tv_horaAtendimento);;
        //TextView estado = (TextView) findViewById(R.id.act_center_profile_tv_mainCardTitle);
        ImageView imageView;
        setText();

        Toolbar toolbar = (Toolbar) findViewById(R.id.act_center_profile_tb_mainToolbar);
      // toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Centro de Referência");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        CardView mDoencas = (CardView) findViewById(R.id.act_center_profile_cv_disesasesCard);
            mDoencas.setOnClickListener(new View.OnClickListener() {
                Uri uri;

                @Override
                public void onClick(View v) {

                    if((mProfissionalProfile.getCenter().getLatitude().isEmpty()) ||
                            (mProfissionalProfile.getCenter().getLongitude().isEmpty()) ) {

                        Toast.makeText(CenterProfile.this, "Localização não cadastrada.",
                                Toast.LENGTH_SHORT).show();

                    }else {




//                        Intent it = new Intent(Intent.ACTION_VIEW, uri.parse("geo:"+mProfissionalProfile.getCenter().getLatitude() + "," + mProfissionalProfile.getCenter().getLongetude() + "?q=("+
//                        mProfissionalProfile.getCenter().getName()+")@"+
//                        mProfissionalProfile.getCenter().getLatitude() + "," +
//                                mProfissionalProfile.getCenter().getLongitude()));

                        String latitude = mProfissionalProfile.getCenter().getLatitude().replace(".", "");
                        String longitude = mProfissionalProfile.getCenter().getLongitude().replace(".", "");

                        latitude = latitude.substring(0, latitude.length() + -7) +
                                "." + latitude.substring(latitude.length() + -7);
                        longitude = longitude.substring(0, longitude.length() + -7) +
                                "." + longitude.substring(longitude.length() + -7);

                        Intent it = new Intent(Intent.ACTION_VIEW, uri.parse("geo:"+ latitude
                                + "," +longitude + "?q=("+
                                mProfissionalProfile.getCenter().getName()+")@"+
                                latitude + "," + longitude));
                        startActivity(it);
                    }
                }
            });

            CardView mLigar = (CardView) findViewById(R.id.act_center_profile_cv_diseasesCard);
            mLigar.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {
                    if(mProfissionalProfile.getCenter().getPhone_number().isEmpty()){
                        Toast.makeText(CenterProfile.this, "Telefone não Cadastrado.",
                                Toast.LENGTH_SHORT).show();
                    }else {

                        init(v);
                        show("Fazer Ligação", "Deseja ligar para " +
                                mProfissionalProfile.getCenter().getName(), "", "ligar","");
                    }
                }
            });

            CardView mEmail = (CardView) findViewById(R.id.act_center_profile_cv_profsCard);
            mEmail.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {

                    if(mProfissionalProfile.getCenter().getEmail().isEmpty()) {

                        Toast.makeText(CenterProfile.this, "Email não Cadastrado.",
                                Toast.LENGTH_SHORT).show();

                    }else {

                        sendEmail(mProfissionalProfile.getCenter().getEmail());
                    }

                }
            });


       CardView mSite = (CardView) findViewById(R.id.act_center_profile_cv_webCard);
        mSite.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {


                if(mProfissionalProfile.getCenter().getSite().isEmpty()){
                    Toast.makeText(CenterProfile.this, "Site não Cadastrado.",
                            Toast.LENGTH_SHORT).show();
                }else {

                    init(v);
                    show("Site", "Deseja ir a pagina do(a) " + mProfissionalProfile.getCenter().getName(),
                            "", "site",mProfissionalProfile.getCenter().getSite());
                }
            }
        });

        final ExpandableListView associatesListGroup =
                (ExpandableListView) mView.findViewById(R.id.frag_center_associates_el_associatesLists);
        associatesListGroup.setAdapter(new ExpandableCentersListsAdapter(this, getAssociatesModel()));

        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            associatesListGroup.setIndicatorBounds(width-66, width);
        } else {
            associatesListGroup.setIndicatorBoundsRelative(width-66, width);
        }

        associatesListGroup.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                TextView label = (TextView) associatesListGroup.findViewWithTag(groupPosition);
                //label.setTextColor(ContextCompat.getColor(ProfissionalProfile.this, R.color.primary_accent));
            }
        });

        associatesListGroup.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                TextView label = (TextView) associatesListGroup.findViewWithTag(groupPosition);
                //label.setTextColor(ContextCompat.getColor(ProfissionalProfile.this, R.color.material_grey_palette_800));
            }
        });


        associatesListGroup.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                Log.d("entrei",getAssociatesModel().get(groupPosition).getAssociatesListItemModel().get(childPosition).getTipo());

                String f;
                Log.d("entrei",getAssociatesModel().get(groupPosition).getAssociatesListItemModel().get(childPosition).getId());
                if("doença".equals(getAssociatesModel().get(groupPosition).getAssociatesListItemModel().get(childPosition).getTipo())){
                    SearchProfileDiseaseTask searchTask = new SearchProfileDiseaseTask();
                    load(false);
                    //Log.d("disease id antes ", id);
                    searchTask.execute(getAssociatesModel().get(groupPosition).getAssociatesListItemModel().get(childPosition).getId(), "id");
                }else if("profi".equals(getAssociatesModel().get(groupPosition).getAssociatesListItemModel().get(childPosition).getTipo())){
                    SearchProfileTask searchTask = new SearchProfileTask();
                    load(false);
                    //Log.d("Profi id antes ", id);
                    searchTask.execute(getAssociatesModel().get(groupPosition).getAssociatesListItemModel().get(childPosition).getId(), "id");
                }else {
                    init(v);
                    //show("Carregar doença","Deseja carregar doença","","doença");
                }
                Log.d("teste",getAssociatesModel().get(groupPosition).getAssociatesListItemModel().get(childPosition).getTitle());

                // f = adapter.getItemname(groupPosition,childPosition);
                   /* Toast.makeText(
                            ProfissionalProfile.this,
                            "You clicked : " + associatesListsAdapter.getChild(groupPosition,childPosition),
                            Toast.LENGTH_SHORT).show();*/
                return false;
            }
        });







    }




    private ArrayList<CenterModel> getAssociatesModel() {
        ArrayList<CenterModel> associatesModel = new ArrayList<>();

        associatesModel.add(getDoenca());
//        associatesModel.add(getCentros());
        // associatesModel.add(getPesquisadores());
        //associatesModel.add(getAssocPacientes());
        //associatesModel.add(getDisorderReferences());
        return associatesModel;
    }

    private CenterModel getDoenca() {
        com.rarasnet.rnp.shared.disease.profile.associates.AssociatesListHeaderModel doencaHeader =
                new com.rarasnet.rnp.shared.disease.profile.associates.AssociatesListHeaderModel(mProfissionalProfile.getDisorders().size()+" Doenças Atendidas por esse Centro", R.drawable.ic_description_black_36dp);

        ArrayList<com.rarasnet.rnp.shared.disease.profile.associates.AssociatesListItemModel> profissionaisList = new ArrayList<>();
        for(DadosNacionais p : dados){
            com.rarasnet.rnp.shared.disease.profile.associates.AssociatesListItemModel i =
                    new com.rarasnet.rnp.shared.disease.profile.associates.AssociatesListItemModel(p.getName(),
                            p.getOrphanumber(), p.getId(), p.getId(), p.getTipo());
            profissionaisList.add(i);

        }


       /* ArrayList<AssociatesListItemModel> profissionaisList = new ArrayList<>();
        AssociatesListItemModel prof1 = new AssociatesListItemModel("Nome Doenca", "Orphananber: 999999", "CID: 00000");
        profissionaisList.add(prof1);
        AssociatesListItemModel prof2 = new AssociatesListItemModel("Nome Doenca", "Orphananber: 999999", "CID: 00000");
        profissionaisList.add(prof2);
        AssociatesListItemModel prof3 = new AssociatesListItemModel("Nome Doenca", "Orphananber: 999999", "CID: 00000");
        profissionaisList.add(prof3);
        AssociatesListItemModel prof4 = new AssociatesListItemModel("Nome Doenca", "Orphananber: 999999", "CID: 00000");
        profissionaisList.add(prof4);
        AssociatesListItemModel prof5 = new AssociatesListItemModel("Nome Doenca", "Orphananber: 999999", "CID: 00000");
        profissionaisList.add(prof5);*/

        CenterModel doencaModel = new CenterModel(doencaHeader, profissionaisList);
        return doencaModel;
    }

    private CenterModel getCentros() {
        com.rarasnet.rnp.shared.disease.profile.associates.AssociatesListHeaderModel profissionaisHeader =
                new com.rarasnet.rnp.shared.disease.profile.associates.AssociatesListHeaderModel(mProfissionalProfile.getProfessional().size()+" Profissionais Associados", R.drawable.ic_account_multiple_black_36dp);

        ArrayList<com.rarasnet.rnp.shared.disease.profile.associates.AssociatesListItemModel> profissionaisList = new ArrayList<>();
        for(Professional p : centros){
            com.rarasnet.rnp.shared.disease.profile.associates.AssociatesListItemModel i = new com.rarasnet.rnp.shared.disease.profile.associates.AssociatesListItemModel(p.getNome(),p.getCidade()+"-"+p.getEstado(),p.getProfissao(),p.getId(),p.getTipo());
            profissionaisList.add(i);

        }


        CenterModel profissionaisModel = new CenterModel(profissionaisHeader, profissionaisList);
        return profissionaisModel;
    }

   /* private CenterModel getPesquisadores() {
        AssociatesListHeaderModel pesquisadoresHeader =
                new AssociatesListHeaderModel("2 Pesquisadores Associados", R.drawable.ic_business_black_24dp);

        ArrayList<AssociatesListItemModel> pesquisadoresList = new ArrayList<>();
        AssociatesListItemModel pesq1 =
                new AssociatesListItemModel("John Doe", "Brasília - DF", "Universidade de Brasília","","");
        pesquisadoresList.add(pesq1);

        AssociatesListItemModel pesq2 =
                new AssociatesListItemModel("John Roe", "Brasília - DF", "Universidade de Brasília","","");
        pesquisadoresList.add(pesq2);

        CenterModel pesquisadoresModel = new CenterModel(pesquisadoresHeader, pesquisadoresList);
        return pesquisadoresModel;
    }*/

    /*private ProfissionaisModel getAssocPacientes() {
        AssociatesListHeaderModel assocPacientesHeader =
                new AssociatesListHeaderModel("2 Associações de Pacientes", R.drawable.ic_business_black_24dp);

        ArrayList<AssociatesListItemModel> assocPacientesList = new ArrayList<>();
        AssociatesListItemModel assoc1 = new AssociatesListItemModel("APEHI", "Brasília - DF", "CEP: 00000");
        assocPacientesList.add(assoc1);

        AssociatesListItemModel assoc2 = new AssociatesListItemModel("APADON", "Ceará", "CEP: 00000");
        assocPacientesList.add(assoc2);

        ProfissionaisModel assocPacientesModel = new ProfissionaisModel(assocPacientesHeader, assocPacientesList);
        return assocPacientesModel;
    }*/




    private void fazerLigacao() { Uri uri = Uri.parse("tel:"+mProfissionalProfile.getCenter().getDdd() +
            mProfissionalProfile.getCenter().getPhone_number());
        Intent itNavegar = new Intent(Intent.ACTION_DIAL,uri);
        startActivity(itNavegar);
    }

    protected void sendEmail(String email) {
        Log.i("Enviar Email", "");

        String[] TO = {email};
        String[] CC = {"monsores@unb.br","contato@rederaras.org"};
        String[] ACC = {"contato@rederaras.org"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Rarasnet");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            startActivity(Intent.createChooser(emailIntent, "Envindo Email..."));

            Log.i("Finished sending mail.", "");
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(CenterProfile.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_center_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home) {
            finish();
        }     return super.onOptionsItemSelected(item);


    }

    public void init(View v) {
        mMaterialDiaolg= new MaterialDialog(this);

        // Toast.makeText(getActivity(), "Initializes successfully.",
        //   Toast.LENGTH_SHORT).show();
    }


    public void show(String Title,String Mensagem, String Complemeto, final String tipo, final String id) {
        Log.d("aqui", "testando");
        if (mMaterialDiaolg != null) {

            mMaterialDiaolg.setTitle(Title).setMessage(Mensagem + " " + Complemeto + "?")
                    .setPositiveButton("SIM", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mMaterialDiaolg.dismiss();
                            if (tipo.equals("ligar")) {

                                fazerLigacao();
                            }else if(tipo.equals("doença")){
                                SearchProfileDiseaseTask searchTask = new SearchProfileDiseaseTask();
                                Log.d("disease id antes ", id);
                                searchTask.execute(id, "id");
                            }else if(tipo.equals("profi")) {
                                SearchProfileTask searchTask = new SearchProfileTask();
                                Log.d("Profi id antes ", id);
                                searchTask.execute(id, "id");

                            }else if(tipo.equals("site")){
                                Intent i = new Intent();
                                i.setData(Uri.parse(id));
                                startActivity(i);

                            }else

                            {
                                //consultaOrphanet();
                            }
                            // Toast.makeText(getActivity(), "SIM", Toast.LENGTH_LONG).show();
                        }
                    }).setNegativeButton("NÃO", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMaterialDiaolg.dismiss();
                    // Toast.makeText(getActivity(), "Cancel", Toast.LENGTH_LONG).show();
                }
            }).setCanceledOnTouchOutside(false)

                    .setOnDismissListener(

                            new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    // Toast.makeText(getActivity(), "",
                                    // Toast.LENGTH_SHORT).show();
                                }
                            }).show();

        } else {
            Toast.makeText(this, "You should init firstly!",
                    Toast.LENGTH_SHORT).show();
        }

        Log.d("aqui", "testando2");
    }

    public void setText(){
        nome.setText(mProfissionalProfile.getCenter().getName());
        endereco.setText(mProfissionalProfile.getCenter().getAddress() + " - " +
                mProfissionalProfile.getCenter().getCity() + " - " +
                mProfissionalProfile.getCenter().getUf());

        // verifies if the center is open 24 hours
        if(mProfissionalProfile.getCenter().getOpen24() == "1")
            atendimento.setText("Aberto 24 horas.");
        else
            atendimento.setText("Ligue para descobrir horario de atendimento.");


    }

    class SearchProfileDiseaseTask extends AsyncTask<String, String, DisorderProfile> {


        @Override
        protected DisorderProfile doInBackground(String... params) {

            Log.d("Estou seachdisease","aqui");
            String diseaseId = params[0];
            Log.d("Estou seachdisease",diseaseId);
            DisorderProfileModel diseaseProfileModel = new DisorderProfileModel();
            DisorderProfile profile = null;

            try {
                profile = diseaseProfileModel.getProfileNew(diseaseId);
            } catch (Exception e) {

            }

            return profile;
        }

        protected void onPostExecute(DisorderProfile profile) {

            //  pb_loadingDisorderData.setVisibility(View.INVISIBLE);
            Intent it = DisorderProfileActivity.getIntent(CenterProfile.this, profile);
            load(true);

            startActivity(it);
        }
    }

    class SearchProfileTask extends AsyncTask<String, String, com.rarasnet.rnp.shared.models.ProfessionalProfile> {


        @Override
        protected com.rarasnet.rnp.shared.models.ProfessionalProfile doInBackground(String... params) {


            String userInput = params[0];
            String searchType = params[1];
            ProfessionalProfileModel professionalProfileModel = new ProfessionalProfileModel();
            com.rarasnet.rnp.shared.models.ProfessionalProfile result = null;

            try {
                result = professionalProfileModel.getProfileNew(userInput, searchType);
                //List<Disease> result = disorders.getStaticDisease();
            } catch (Exception e) {

            }
            return result;

            // return profile;
        }

        protected void onPostExecute(com.rarasnet.rnp.shared.models.ProfessionalProfile profissionaisDataResponses){

            //pb_loadingProfissionalsData.setVisibility(View.INVISIBLE);
            Intent intent = ProfissionalProfile.getIntent(CenterProfile.this, profissionaisDataResponses);
            load(true);
           startActivity(intent);
        }
    }

    public void load(Boolean j){
        if(j) {
            Log.d("done","done");

            progress.cancel();


        }else{

            // UIUtils.getProgressDialog(SearchDisordersActivity.this);
            progress= UIUtils.getProgressDialog(this);
            // progress.requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);

            //progress.getWindow().setLayout(UIUtils.dpToPx(200, this), UIUtils.dpToPx(125, this));
            progress.show();


        }}

}
