package com.rarasnet.rnp.shared.profissionais.profile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ronnyerybarbosa.library.MaterialDialog;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLngBounds;
import com.rarasnet.rnp.shared.R;
import com.rarasnet.rnp.shared.center.profile.CenterProfile;
import com.rarasnet.rnp.shared.disease.profile.DisorderProfileActivity;
import com.rarasnet.rnp.shared.disease.profile.associates.ExpandableAssociatesListsAdapter;
import com.rarasnet.rnp.shared.disease.profile.description.Specialty;
import com.rarasnet.rnp.shared.disease.search.models.DisorderProfile;
import com.rarasnet.rnp.shared.disease.search.models.DisorderProfileModel;
import com.rarasnet.rnp.shared.models.Center;
import com.rarasnet.rnp.shared.models.CenterProfileModel;
import com.rarasnet.rnp.shared.models.DadosNacionais;
import com.rarasnet.rnp.shared.models.ProfessionalProfile;
import com.rarasnet.rnp.shared.util.UIUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



/**
 * Created by Ronnyery Barbosa on 05/02/2016.
 */
public class ProfissionalProfile extends AppCompatActivity {


    private GoogleApiClient mGoogleApi;
    private LatLngBounds mBounds;
    private Location mLastLocation;
    private GoogleMap mMap;
    private MapView mMapView;
    private LinearLayout mView;
    MaterialDialog mMaterialDiaolg;
    ExpandableAssociatesListsAdapter adapter;
    HashMap<String, List<String>> listHashMap;
    private ArrayList<AssociatesModel> mAssociatesModel;
    public static ProfessionalProfile mProfissionalProfile;
    private List<DadosNacionais>  dados = mProfissionalProfile.getDisorders();
    private List<Center> centros = mProfissionalProfile.getCenters();

    private AlertDialog progress;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


   // ProfissionalProfile disorderProfile = ProfissionalProfile.mDisorderProfile;
    private GoogleApiClient client;
    public static Intent getIntent(Context context, ProfessionalProfile profisionalProfile) {
        Intent it = new Intent(context, ProfissionalProfile.class);
        mProfissionalProfile = profisionalProfile;
        return it;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mView = (LinearLayout) View.inflate(this, R.layout.activity_profe_profile, null);
        mView = (LinearLayout) View.inflate(this, R.layout.professional_profile, null);

        setContentView(mView);

        Toolbar toolbar = (Toolbar) findViewById(R.id.act_contact_tb_mainToolbar);
      // toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profissionais");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView nome = (TextView) findViewById(R.id.name);
        TextView conselho = (TextView) findViewById(R.id.conselho);
        TextView situacao = (TextView) findViewById(R.id.situacao);
        TextView specialties = (TextView) findViewById(R.id.specialty);
        TextView city = (TextView) findViewById(R.id.city);
        TextView ocupation = (TextView) findViewById(R.id.ocupation);

        // Card views
        CardView faceCard = (CardView) findViewById(R.id.act_face_card);
        CardView mailCard = (CardView) findViewById(R.id.act_mail_card);
        CardView phoneCard = (CardView) findViewById(R.id.act_telephone_card);
        CardView twitterCard = (CardView) findViewById(R.id.act_twitter_card);



        /// Sets text views info
        nome.setText( mProfissionalProfile.getProfessional().getName() +
                mProfissionalProfile.getProfessional().getSurname());

        // Writes Concil number of the professional
        if(mProfissionalProfile.getProfessional().getCouncil_number() == null ||
                mProfissionalProfile.getProfessional().getProfession() != null &&
                        mProfissionalProfile.getProfessional().getCouncil_number().isEmpty()) {
            conselho.setText("Não cadastrado");
        }else {
            conselho.setText(mProfissionalProfile.getProfessional().getCouncil_number());
        }

        // Writes professional status
        if(mProfissionalProfile.getProfessional().getActive().equals("1")) {
            situacao.setText("Sim");
        }else {
            situacao.setText("Não");
        }

        // Writes profession
        if(mProfissionalProfile.getProfessional().getProfession() == null ||
                mProfissionalProfile.getProfessional().getProfession() != null &&
                mProfissionalProfile.getProfessional().getProfession().isEmpty()) {
            ocupation.setText("Sem informação");
        }else {
            ocupation.setText(mProfissionalProfile.getProfessional().getProfession());
        }

        // Writes city and uf
        if(mProfissionalProfile.getProfessional().getCity() == null ||
                mProfissionalProfile.getProfessional().getProfession() != null &&
                        mProfissionalProfile.getProfessional().getCity().isEmpty()) {
            city.setText("Sem informação");
        }else {
            city.setText(mProfissionalProfile.getProfessional().getCity() +
            " - " + mProfissionalProfile.getProfessional().getUf());
        }

        // Writes professional specialties list
        specialties.setText("");
        for (Specialty specialty: mProfissionalProfile.getSpecialties()) {
            specialties.append(specialty.getName());
        }

        /// Card views listeners
        faceCard.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                if(mProfissionalProfile.getProfessional().getFacebook() == null ||
                        mProfissionalProfile.getProfessional().getFacebook() != null &&
                                mProfissionalProfile.getProfessional().getFacebook().isEmpty()) {

                    //Toast.makeText(ProfessionalProfile.this, "Facebook não disponível.",
                     //       Toast.LENGTH_SHORT).show();

                    Snackbar.make(findViewById(R.id.myCoordinatorLayout),"Facebook não disponível.",
                            Snackbar.LENGTH_SHORT)
                            .show();
                }else {

                    // facebook profile intent
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse(mProfissionalProfile.getProfessional().getFacebook())));
                    } catch(Exception e) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/" +
                                mProfissionalProfile.getProfessional().getFacebook()));
                        startActivity(intent);
                    }                }

            }
        });

        mailCard.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                    Snackbar.make(findViewById(R.id.myCoordinatorLayout),"Email não disponível.",
                            Snackbar.LENGTH_SHORT)
                            .show();

            }
        });

        twitterCard.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Snackbar.make(findViewById(R.id.myCoordinatorLayout),"Twitter não disponível.",
                        Snackbar.LENGTH_SHORT)
                        .show();

            }
        });

        phoneCard.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Snackbar.make(findViewById(R.id.myCoordinatorLayout),"Telefone não disponível.",
                        Snackbar.LENGTH_SHORT)
                        .show();

            }
        });

        /// Diseases expandable view
        final ExpandableListView associatesListGroup =
                (ExpandableListView) mView.findViewById(R.id.frag_center_associates_el_associatesLists);
        associatesListGroup.setAdapter(new ExpandableProfiListsAdapter(this, getAssociatesModel()));

        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
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

        /**
         * Responsivle to handle clickings at disease
         */
        associatesListGroup.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {



                Log.d("Estou seachdisease",getAssociatesModel().get(groupPosition).getAssociatesListItemModel().get(childPosition).getId());
                if("doença".equals(getAssociatesModel().get(groupPosition).getAssociatesListItemModel().get(childPosition).getTipo())){
                    SearchProfileDiseaseTask searchTask = new SearchProfileDiseaseTask();
                    load(false);
                   // Log.d("Profi id antes ", id);
                    searchTask.execute(getAssociatesModel().get(groupPosition).getAssociatesListItemModel().get(childPosition).getId(), "id");
                }else if("center".equals(getAssociatesModel().get(groupPosition).getAssociatesListItemModel().get(childPosition).getTipo())){
                    SearchProfileTask searchTask = new SearchProfileTask();
                    load(false);
                    //Log.d("Profi id antes ", id);
                    searchTask.execute(getAssociatesModel().get(groupPosition).getAssociatesListItemModel().get(childPosition).getId(), "id");
                   // temModel().get(childPosition).getId());

                }else {
                    init(v);
                    show("Carregar Erro","Erro","","doença","");
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






    private ArrayList<ProfissionaisModel> getAssociatesModel() {
        ArrayList<ProfissionaisModel> associatesModel = new ArrayList<>();

//        associatesModel.add(getDoenca());
//        associatesModel.add(getCentros());

        // associatesModel.add(getPesquisadores());
        //associatesModel.add(getAssocPacientes());
        //associatesModel.add(getDisorderReferences());
        return associatesModel;
    }

    private ProfissionaisModel getDoenca() {
        AssociatesListHeaderModel doencaHeader =
                new AssociatesListHeaderModel(mProfissionalProfile.getDisorders().size()+" Doenças que possui Especialização", R.drawable.ic_description_black_36dp);

        ArrayList<AssociatesListItemModel> profissionaisList = new ArrayList<>();
        for(DadosNacionais p : dados){
            AssociatesListItemModel i = new AssociatesListItemModel(p.getName(),p.getOrphanumber(),"",p.getId(),p.getTipo());
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

        ProfissionaisModel doencaModel = new ProfissionaisModel(doencaHeader, profissionaisList);
        return doencaModel;
    }

    private ProfissionaisModel getCentros() {
        AssociatesListHeaderModel profissionaisHeader =
                new AssociatesListHeaderModel(mProfissionalProfile.getCenters().size()+" Centros de Referência Associado", R.drawable.ic_hospital_building_black_36dp);

        ArrayList<AssociatesListItemModel> profissionaisList = new ArrayList<>();
        for(Center p : centros){
            AssociatesListItemModel i = new AssociatesListItemModel(p.getNome(),p.getEndereco(),p.getCidade()+" "+ p.getEstado(),p.getId(),p.getTipo());
            profissionaisList.add(i);

        }


        ProfissionaisModel profissionaisModel = new ProfissionaisModel(profissionaisHeader, profissionaisList);
        return profissionaisModel;
    }

    private ProfissionaisModel getPesquisadores() {
        AssociatesListHeaderModel pesquisadoresHeader =
                new AssociatesListHeaderModel("2 Pesquisadores Associados", R.drawable.ic_business_black_24dp);

        ArrayList<AssociatesListItemModel> pesquisadoresList = new ArrayList<>();
        AssociatesListItemModel pesq1 =
                new AssociatesListItemModel("John Doe", "Brasília - DF", "Universidade de Brasília","","");
        pesquisadoresList.add(pesq1);

        AssociatesListItemModel pesq2 =
                new AssociatesListItemModel("John Roe", "Brasília - DF", "Universidade de Brasília","","");
        pesquisadoresList.add(pesq2);

        ProfissionaisModel pesquisadoresModel = new ProfissionaisModel(pesquisadoresHeader, pesquisadoresList);
        return pesquisadoresModel;
    }

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




    private void fazerLigacao() { Uri uri = Uri.parse("tel:0123456789012");
        Intent itNavegar = new Intent(Intent.ACTION_DIAL,uri);
        startActivity(itNavegar);
    }

    protected void sendEmail() {
        Log.i("Send email", "");

        String[] TO = {"someone@gmail.com"};
        String[] CC = {"xyz@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));

            Log.i("Finished sending mail", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ProfissionalProfile.this,
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
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
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
                            if (tipo.equals("email")) {

                                fazerLigacao();
                            }else if(tipo.equals("doença")){
                                SearchProfileDiseaseTask searchTask = new SearchProfileDiseaseTask();
                                Log.d("Profi id antes ", id);
                                searchTask.execute(id, "id");
                            }else if(tipo.equals("center")) {
                                //SearchCentersActivity searchCentersActivity = null;

                               // searchCentersActivity.loadCenter(id);
                                //Intent intent = SearchCentersActivity.get;
                                SearchProfileTask searchTask = new SearchProfileTask();
                                Log.d("Profi id antes ", id);
                                searchTask.execute(id,"id");


                                // Intent Intent = new Intent(ProfissionalProfile.this, ProfissionalProfile.class);
                                // startActivity(Intent);}
                            }

                            else

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

    class SearchProfileTask extends AsyncTask<String, String, com.rarasnet.rnp.shared.models.CenterProfile> {


        @Override
        protected com.rarasnet.rnp.shared.models.CenterProfile doInBackground(String... params) {


            String userInput = params[0];
            String searchType = params[1];
            CenterProfileModel professionalProfileModel = new CenterProfileModel();
            com.rarasnet.rnp.shared.models.CenterProfile result = null;

            try {
                result = professionalProfileModel.getProfile(userInput, searchType);
                //List<Disease> result = disorders.getStaticDisease();
            } catch (Exception e) {

            }
            return result;

            // return profile;
        }

        protected void onPostExecute(com.rarasnet.rnp.shared.models.CenterProfile profissionaisDataResponses){

            //pb_loadingProfissionalsData.setVisibility(View.INVISIBLE);
            Intent intent = CenterProfile.getIntent(ProfissionalProfile.this, profissionaisDataResponses);
            load(true);
            startActivity(intent);
        }
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
            Intent it = DisorderProfileActivity.getIntent(ProfissionalProfile.this, profile);
            load(true);
            startActivity(it);
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
