package com.rarasnet.rnp.shared.disease.profile.associates;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ronnyerybarbosa.library.MaterialDialog;
import com.rarasnet.rnp.shared.R;
import com.rarasnet.rnp.shared.disease.profile.DisorderProfileActivity;
import com.rarasnet.rnp.shared.disease.profile.description.Specialty;
import com.rarasnet.rnp.shared.disease.search.models.DisorderProfile;
import com.rarasnet.rnp.shared.disease.search.models.DisorderProfileModel;
import com.rarasnet.rnp.shared.disease.search.models.Reference;
import com.rarasnet.rnp.shared.models.CenterProfile;
import com.rarasnet.rnp.shared.models.CenterProfileModel;
import com.rarasnet.rnp.shared.models.Professional;
import com.rarasnet.rnp.shared.models.ProfessionalProfileModel;
import com.rarasnet.rnp.shared.profissionais.profile.ProfissionalProfile;
import com.rarasnet.rnp.shared.util.UIUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Farina on 22/10/2015.
 */
public class AssociatesFragment extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";
    private RelativeLayout mView;
    private RelativeLayout mProfiListFrame;
    MaterialDialog mMaterialDiaolg;
    private int mPage;
   //x private TextView;
    AssociProfiAdapter mAdapter;
    AlertDialog progress;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;


    DisorderProfile disorderProfile = DisorderProfileActivity.mDisorderProfile;
    private List<Professional>  prof = disorderProfile.getProfessional();
    private List<Reference> ref = disorderProfile.getReferences();
    private List<Specialty> specialties = disorderProfile.getSpecialties();

    TextView profqnt;

    private AssociProfiAdapter.OnItemClickListener mOnItemClickListener = new AssociProfiAdapter.OnItemClickListener() {

        @Override
        public void onItemClick(AssociatedProfile item) {

          //  init(getView());
          //  Log.d("id",String.valueOf(item.getNome()));

            //show("Carregar Centro", "Deseja carregar " + item.getNome(), "", "center",
                //    String.valueOf(item.getId()));
            download(false);
            SearchProfileTask searchTask = new SearchProfileTask();
            //Log.d("Profi id antes ", id);
            searchTask.execute(item.getId(), "id");


        }
    };






    public static AssociatesFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        AssociatesFragment fragment = new AssociatesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(container ==null){
            return null;
        }
        RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_associated_profissional, container, false);
        mView= relativeLayout;
        mProfiListFrame=(RelativeLayout) mView.findViewById(R.id.fragment_associated_profissional_rl_frameList);

        profqnt = (TextView) mView.findViewById(R.id.frag_disorder_associates_tv_associatesQnt);
        profqnt.setText(String.valueOf(DisorderProfileActivity.mDisorderProfile.getProfessional().size()));

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.fragment_associated_profissional_rv_listaCentros);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new AssociProfiAdapter(getAssociatesProfi(),
                disorderProfile.getDisorder().getDesorden_id());
        mAdapter.setOnItemClickListener(mOnItemClickListener);
        mRecyclerView.setAdapter(mAdapter);

        /*final ExpandableListView associatesListGroup =
                (ExpandableListView) mView.findViewById(R.id.frag_disorder_associates_el_associatesLists);
        associatesListGroup.setAdapter(new ExpandableAssociatesListsAdapter(getActivity(), getAssociatesModel()));*/
        //mAdapter = new AssociProfiAdapter(getAssociatesProfi());
       // DisplayMetrics displayMetrics = new DisplayMetrics();
       // getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        //int width = displayMetrics.widthPixels;
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


        return  relativeLayout;
    }








    private ArrayList<AssociatesModel> getAssociatesModel() {
        ArrayList<AssociatesModel> associatesModel = new ArrayList<>();

       // associatesModel.add(getProfissionais());
       // associatesModel.add(getPesquisadores());
      //  associatesModel.add(getAssocPacientes());
        associatesModel.add(getDisorderReferences());
        return associatesModel;
    }

   // private AssociatesModel getProfissionais() {
        //mAdapter = new ProfessionalsAdapter(disorderProfile)
        //AssociatesListHeaderModel profissionaisHeader =
          //      new AssociatesListHeaderModel(String.valueOf(disorderProfile.getProfessional().size() +" "+ "Profissionais de Saúde"), R.drawable.ic_perm_identity_black_24dp);


    private ArrayList<AssociatedProfile> getAssociatesProfi(){
        ArrayList<AssociatedProfile> profissionaisList = new ArrayList<>();
        int counter = 0;

        for(Professional p : prof){
            AssociatedProfile i = new AssociatedProfile(R.drawable.ic_no_profile_picture,
                    p.getName() + p.getSurname(),
                    p.getCity(), p.getUf(), p.getTipo(), p.getId());
            profissionaisList.add(i);
            counter++;

        }

             return profissionaisList;
    }

    private ArrayList<AssociatedProfile> getAssociatedCenters() {
        ArrayList<AssociatedProfile> centers = new ArrayList<>();
        for (Professional c : prof) {
            AssociatedProfile i = new AssociatedProfile(R.drawable.ic_business_white_24dp, c.getNome(), c.getCidade() + "-" + c.getEstado(), c.getProfissao(), c.getId(), c.getTipo());
            centers.add(i);

        }
        return centers;
    }

        private AssociatesModel getPesquisadores() {
        AssociatesListHeaderModel pesquisadoresHeader =
                new AssociatesListHeaderModel("Pesquisadores", R.drawable.ic_perm_identity_black_24dp);

        ArrayList<AssociatesListItemModel> pesquisadoresList = new ArrayList<>();
        AssociatesListItemModel pesq1 =
                new AssociatesListItemModel("John Doe", "Brasília - DF", "Universidade de Brasília","","");
        pesquisadoresList.add(pesq1);

        AssociatesListItemModel pesq2 =
                new AssociatesListItemModel("John Roe", "Brasília - DF", "Universidade de Brasília","","");
        pesquisadoresList.add(pesq2);

        AssociatesModel pesquisadoresModel = new AssociatesModel(pesquisadoresHeader, pesquisadoresList);
        return pesquisadoresModel;
    }

    private AssociatesModel getAssocPacientes() {
        AssociatesListHeaderModel assocPacientesHeader =
                new AssociatesListHeaderModel("2 Associações de Pacientes", R.drawable.ic_business_black_24dp);

        ArrayList<AssociatesListItemModel> assocPacientesList = new ArrayList<>();
        AssociatesListItemModel assoc1 = new AssociatesListItemModel("APEHI", "Brasília - DF", "CEP: 00000","","");
        assocPacientesList.add(assoc1);

        AssociatesListItemModel assoc2 = new AssociatesListItemModel("APADON", "Ceará", "CEP: 00000","","");
        assocPacientesList.add(assoc2);

        AssociatesModel assocPacientesModel = new AssociatesModel(assocPacientesHeader, assocPacientesList);
        return assocPacientesModel;
    }

    private AssociatesModel getDisorderReferences() {
        AssociatesListHeaderModel disorderReferencesHeader =
                new AssociatesListHeaderModel(String.valueOf(ref.size() +" " + "Referências"), R.drawable.ic_share_black_24dp);

        //ArrayList<AssociatesListItemModel> profissionaisList = new ArrayList<>();


        ArrayList<AssociatesListItemModel> refsList = new ArrayList<>();
        for(Reference r : ref){
            AssociatesListItemModel i = new AssociatesListItemModel(r.getSource(),r.getReference(),r.getStatus(),"","");
            refsList.add(i);

        }
        /*AssociatesListItemModel ref1 = new AssociatesListItemModel("ICD-10", "E22.0", "Validado");
        refsList.add(ref1);

        AssociatesListItemModel ref2 = new AssociatesListItemModel("MedRA", "100000599", "Validado");
        refsList.add(ref2);

        AssociatesListItemModel ref3 = new AssociatesListItemModel("OMIM", "102200", "Validado");
        refsList.add(ref3);

        AssociatesListItemModel ref4 = new AssociatesListItemModel("OMIM", "300943", "Validado");
        refsList.add(ref4);

        AssociatesListItemModel ref5 = new AssociatesListItemModel("MeSH", "D000172", "Validado");
        refsList.add(ref5);

        AssociatesListItemModel ref6 = new AssociatesListItemModel("UMLS", "C0001206", "Validado");
        refsList.add(ref6);*/


        return  new AssociatesModel(disorderReferencesHeader, refsList);
    }

      private ArrayList<Professional> getDisorderprofissionais() {
        return new ArrayList<>(DisorderProfileActivity.mDisorderProfile.getProfessional());
    }

    class SearchProfileCenterTask extends AsyncTask<String, String, CenterProfile> {


        @Override
        protected CenterProfile doInBackground(String... params) {


            String userInput = params[0];
            String searchType = params[1];
            CenterProfileModel professionalProfileModel = new CenterProfileModel();
            CenterProfile result = null;

            try {
                result = professionalProfileModel.getProfile(userInput, searchType);
                //List<Disease> result = disorders.getStaticDisease();
            } catch (Exception e) {

            }
            return result;

            // return profile;
        }

        protected void onPostExecute(CenterProfile profissionaisDataResponses){

            //pb_loadingProfissionalsData.setVisibility(View.INVISIBLE);
            //Intent intent = .getIntent(ProfissionalProfile.this, profissionaisDataResponses);
            //startActivity(intent);
        }
    }


    class SearchProfileDiseaseTask extends AsyncTask<String, String, DisorderProfile> {


        @Override
        protected DisorderProfile doInBackground(String... params) {

            Log.d("Estou seachdisease", "aqui");
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
            //Intent it = DisorderProfileActivity.getIntent(ProfissionalProfile.this, profile);
            //startActivity(it);
        }
    }


    public void init(View v) {
        mMaterialDiaolg= new MaterialDialog(getActivity());

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

                              //  fazerLigacao();
                            }else if(tipo.equals("doença")){
                                SearchProfileDiseaseTask searchTask = new SearchProfileDiseaseTask();
                                Log.d("disease id antes ", id);
                                searchTask.execute(id, "id");
                            }else if(tipo.equals("profi")) {
                                SearchProfileTask searchTask = new SearchProfileTask();
                                Log.d("Profi id antes ", id);
                                searchTask.execute(id, "id");

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
            Toast.makeText(getActivity(), "You should init firstly!",
                    Toast.LENGTH_SHORT).show();
        }

        Log.d("aqui", "testando2");
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
            Intent intent = ProfissionalProfile.getIntent(getActivity(), profissionaisDataResponses);
            download(true);
            startActivity(intent);
        }
    }


    public void download(Boolean j){
        if(j) {
            Log.d("done","done");

            progress.cancel();


        }else{

            // UIUtils.getProgressDialog(SearchDisordersActivity.this);
            progress= UIUtils.getProgressDialog(getActivity());
            // progress.requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);

            //progress.getWindow().setLayout(UIUtils.dpToPx(200, this), UIUtils.dpToPx(125, this));
            progress.show();


        }}



}
