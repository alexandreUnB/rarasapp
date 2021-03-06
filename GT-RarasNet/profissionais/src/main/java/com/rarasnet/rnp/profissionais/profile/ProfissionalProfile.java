package com.rarasnet.rnp.profissionais.profile;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.rarasnet.rnp.centros.profile.CenterProfile;
import com.rarasnet.rnp.desordens.profile.DisorderProfileActivity;
import com.rarasnet.rnp.desordens.profile.associates.ExpandableAssociatesListsAdapter;
import com.rarasnet.rnp.desordens.search.models.DisorderProfile;
import com.rarasnet.rnp.desordens.search.models.DisorderProfileModel;
import com.rarasnet.rnp.profissionais.R;
import com.rarasnet.rnp.profissionais.controllers.activities.SearchProfissionaisActivity;
import com.rarasnet.rnp.shared.models.Center;
import com.rarasnet.rnp.shared.models.CenterProfileModel;
import com.rarasnet.rnp.shared.models.DadosNacionais;
import com.rarasnet.rnp.shared.models.ProfessionalProfile;

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
        mView = (LinearLayout) View.inflate(this, R.layout.activity_profe_profile, null);
        setContentView(mView);

        Toolbar toolbar = (Toolbar) findViewById(R.id.act_center_profile_tb_mainToolbar);
      // toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profissionais");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView nome = (TextView) findViewById(R.id.nome);
        TextView especialidade = (TextView) findViewById(R.id.especialidade);
        TextView crm = (TextView) findViewById(R.id.crm);
        TextView endereco = (TextView) findViewById(R.id.endereco);
        TextView mail = (TextView) findViewById(R.id.email);

        nome.setText("Dr." + mProfissionalProfile.getProfessional().getNome() + " " + mProfissionalProfile.getProfessional().getSobrenome());
        especialidade.setText(mProfissionalProfile.getProfessional().getProfissao());
        crm.setText("Não cadastrado");
        endereco.setText(mProfissionalProfile.getProfessional().getCidade() + " - " + mProfissionalProfile.getProfessional().getEstado());
        mail.setText(mProfissionalProfile.getProfessional().getEmail());

               /* CardView mDoencas = (CardView) findViewById(R.id.act_center_profile_cv_disesasesCard);
            mDoencas.setOnClickListener(new View.OnClickListener() {
                Uri uri;

                @Override
                public void onClick(View v) {
                    Intent it = new Intent(Intent.ACTION_VIEW, uri.parse("geo:-20.194594,-40.249089?z=25"));
                    startActivity(it);
                }
            });

            CardView mLigar = (CardView) findViewById(R.id.act_center_profile_cv_diseasesCard);
            mLigar.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {
                    init(v);
                    show("Fazer Ligação", "Deseja ligar para o centro de referencia", "", "email");

                }
            });

            CardView mEmail = (CardView) findViewById(R.id.act_center_profile_cv_profsCard);
            mEmail.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {
                    sendEmail();

                }
            });*/

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


        associatesListGroup.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {



                Log.d("Estou seachdisease",getAssociatesModel().get(groupPosition).getAssociatesListItemModel().get(childPosition).getId());
                if("doença".equals(getAssociatesModel().get(groupPosition).getAssociatesListItemModel().get(childPosition).getTipo())){
                    init(v);
                    show("Carregar doença","Deseja carregar doença ","","doença",getAssociatesModel().get(groupPosition).getAssociatesListItemModel().get(childPosition).getId());
                }else if("center".equals(getAssociatesModel().get(groupPosition).getAssociatesListItemModel().get(childPosition).getTipo())){
                    init(v);
                    show("Carregar Centro","Deseja carregar " + getAssociatesModel().get(groupPosition).getAssociatesListItemModel().get(childPosition).getInfo1(),"","center",
                            "1");

                }else {
                    init(v);
                    show("Carregar doença","Deseja carregar doença","","doença","");
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

        associatesModel.add(getDoenca());
        associatesModel.add(getCentros());
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
            AssociatesListItemModel i = new AssociatesListItemModel(p.getDoenca(),p.getOrphanumber(),p.getDesorden_id(),p.getDesorden_id(),p.getTipo());
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
            AssociatesListItemModel i = new AssociatesListItemModel(p.getNome(),p.getCidade(),p.getEstado(),p.getId(),p.getTipo());
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

            Log.i("Finished sending email...", "");
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
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent it = new Intent(ProfissionalProfile.this, SearchProfissionaisActivity.class);
                startActivity(it);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
                                //SearchCentersdepreActivity searchCentersActivity = null;

                               // searchCentersActivity.loadCenter(id);
                                //Intent intent = SearchCentersdepreActivity.get;
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
                profile = diseaseProfileModel.getProfile(diseaseId);
            } catch (Exception e) {

            }

            return profile;
        }

        protected void onPostExecute(DisorderProfile profile) {

          //  pb_loadingDisorderData.setVisibility(View.INVISIBLE);
            Intent it = DisorderProfileActivity.getIntent(ProfissionalProfile.this, profile);
            startActivity(it);
        }
    }

}
