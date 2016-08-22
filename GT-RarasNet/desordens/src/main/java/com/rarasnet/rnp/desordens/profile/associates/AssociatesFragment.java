package com.rarasnet.rnp.desordens.profile.associates;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rarasnet.rnp.desordens.R;
import com.rarasnet.rnp.desordens.profile.DisorderProfileActivity;
import com.rarasnet.rnp.desordens.search.models.DisorderProfile;
import com.rarasnet.rnp.desordens.search.models.DisorderProfileModel;
import com.rarasnet.rnp.desordens.search.models.Reference;
import com.rarasnet.rnp.shared.models.CenterProfile;
import com.rarasnet.rnp.shared.models.CenterProfileModel;
import com.rarasnet.rnp.shared.models.Professional;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Farina on 22/10/2015.
 */
public class AssociatesFragment extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";
    private RelativeLayout mView;
    private int mPage;
   //x private TextView;
   // ProfessionalsAdapter mAdapter;


    DisorderProfile disorderProfile = DisorderProfileActivity.mDisorderProfile;
    private List<Professional>  prof = disorderProfile.getProfessional();
    private List<Reference> ref = disorderProfile.getReferences();



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
        mView  = (RelativeLayout) inflater.inflate(R.layout.fragment_disorder_associates, container, false);



        final ExpandableListView associatesListGroup =
                (ExpandableListView) mView.findViewById(R.id.frag_disorder_associates_el_associatesLists);
        associatesListGroup.setAdapter(new ExpandableAssociatesListsAdapter(getActivity(), getAssociatesModel()));

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
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
                label.setTextColor(getResources().getColor(R.color.primary_accent));
            }
        });

        associatesListGroup.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                TextView label = (TextView) associatesListGroup.findViewWithTag(groupPosition);
                label.setTextColor(getResources().getColor(R.color.material_grey_palette_800));
            }
        });
        return mView;
    }

    private ArrayList<AssociatesModel> getAssociatesModel() {
        ArrayList<AssociatesModel> associatesModel = new ArrayList<>();

        associatesModel.add(getProfissionais());
        associatesModel.add(getPesquisadores());
        associatesModel.add(getAssocPacientes());
        associatesModel.add(getDisorderReferences());
        return associatesModel;
    }

    private AssociatesModel getProfissionais() {
        //mAdapter = new ProfessionalsAdapter(disorderProfile)
        AssociatesListHeaderModel profissionaisHeader =
                new AssociatesListHeaderModel(String.valueOf(disorderProfile.getProfessional().size() +" "+ "Profissionais de Saúde"), R.drawable.ic_perm_identity_black_24dp);



        ArrayList<AssociatesListItemModel> profissionaisList = new ArrayList<>();
        for(Professional p : prof){
            AssociatesListItemModel i = new AssociatesListItemModel(p.getNome(),p.getCidade(),p.getProfissao());
            profissionaisList.add(i);

        }
       // profissionaisList.add(disorderProfile.getProfessional().);
       /* AssociatesListItemModel prof1 = new AssociatesListItemModel("Nome Profissional", "Brasília - DF", "CRM: 00000");
        profissionaisList.add(prof1);
        AssociatesListItemModel prof2 = new AssociatesListItemModel("Nome Profissional", "Brasília - DF", "CRM: 00000");
        profissionaisList.add(prof2);
        AssociatesListItemModel prof3 = new AssociatesListItemModel("Nome Profissional", "Brasília - DF", "CRM: 00000");
        profissionaisList.add(prof3);
        AssociatesListItemModel prof4 = new AssociatesListItemModel("Nome Profissional", "Brasília - DF", "CRM: 00000");
        profissionaisList.add(prof4);
        AssociatesListItemModel prof5 = new AssociatesListItemModel("Nome Profissional", "Brasília - DF", "CRM: 00000");
        profissionaisList.add(prof5);*/

        AssociatesModel profissionaisModel = new AssociatesModel(profissionaisHeader, profissionaisList);
        return profissionaisModel;
    }

    private AssociatesModel getPesquisadores() {
        AssociatesListHeaderModel pesquisadoresHeader =
                new AssociatesListHeaderModel("Pesquisadores", R.drawable.ic_perm_identity_black_24dp);

        ArrayList<AssociatesListItemModel> pesquisadoresList = new ArrayList<>();
        AssociatesListItemModel pesq1 =
                new AssociatesListItemModel("John Doe", "Brasília - DF", "Universidade de Brasília");
        pesquisadoresList.add(pesq1);

        AssociatesListItemModel pesq2 =
                new AssociatesListItemModel("John Roe", "Brasília - DF", "Universidade de Brasília");
        pesquisadoresList.add(pesq2);

        AssociatesModel pesquisadoresModel = new AssociatesModel(pesquisadoresHeader, pesquisadoresList);
        return pesquisadoresModel;
    }

    private AssociatesModel getAssocPacientes() {
        AssociatesListHeaderModel assocPacientesHeader =
                new AssociatesListHeaderModel("2 Associações de Pacientes", R.drawable.ic_business_black_24dp);

        ArrayList<AssociatesListItemModel> assocPacientesList = new ArrayList<>();
        AssociatesListItemModel assoc1 = new AssociatesListItemModel("APEHI", "Brasília - DF", "CEP: 00000");
        assocPacientesList.add(assoc1);

        AssociatesListItemModel assoc2 = new AssociatesListItemModel("APADON", "Ceará", "CEP: 00000");
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
            AssociatesListItemModel i = new AssociatesListItemModel(r.getSource(),r.getReference(),r.getStatus());
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
                profile = diseaseProfileModel.getProfile(diseaseId);
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

}
