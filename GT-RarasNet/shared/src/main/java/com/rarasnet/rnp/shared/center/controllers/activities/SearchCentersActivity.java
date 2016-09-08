package com.rarasnet.rnp.shared.center.controllers.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatMultiAutoCompleteTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rarasnet.rnp.shared.R;
import com.rarasnet.rnp.shared.center.controllers.network.responses.SearchCentersDataResponse;
import com.rarasnet.rnp.shared.center.profile.CenterProfile;
import com.rarasnet.rnp.shared.center.search.CentersSearchResultsAdapter;
import com.rarasnet.rnp.shared.center.views.CenterAdpter;
import com.rarasnet.rnp.shared.center.views.CentersAutocompleteAdapter;
import com.rarasnet.rnp.shared.models.CenterProfileModel;
import com.rarasnet.rnp.shared.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Farina on 5/4/2015.
 */
public class SearchCentersActivity extends AppCompatActivity {




    private Toolbar mToolbar;
    private MenuItem mToolbarSearchItem;
    private AppCompatAutoCompleteTextView ac_searchEditText;
    private AppCompatMultiAutoCompleteTextView ac_searchBySigns;
    private ListView lv_searchResults;
    private CentersSearchResultsAdapter mSearchResultsAdapter;
    private int et_search_sendButtonResource;
    private ProgressBar pb_searchProgress;
    private ProgressBar pb_searchViewProgress;
    private ProgressBar pb_loadingProfissionalsData;
    private android.app.AlertDialog progress;

    private RelativeLayout rl_fadeMenu;
    private TextView tv_searchType;
    private RelativeLayout rl_searchType;
    private boolean doSignsSearch = false;


    private static final String TYPE_NAME = "nome";
    private static final String TYPE_ORPHANUMBER = "orphanumber";
    private static final String TYPE_ICD = "icd";

    //Numeric Pattern
    private static final String orphanumberPattern = "[0-9]+";
    private static final String icdPattern = "[A-Z][0-9][0-9].[0-9]";

    private boolean flag = true;
    private RadioGroup radioGroup;
    private boolean doNameSearch = false;
    private boolean doDisorderSearch = false;


   // private ListaCentersAdapter mAdapter;
    private RelativeLayout listFrame;
    private ProgressDialog pDialog;

    private ListView mListaProfissionais;

    private View.OnTouchListener et_search_touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            final int DRAWABLE_LEFT = 0;
            final int DRAWABLE_TOP = 1;
            final int DRAWABLE_RIGHT = 2;
            final int DRAWABLE_BOTTOM = 3;

            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX()
                        >= (ac_searchEditText.getRight()
                        - ac_searchEditText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    Log.d("handle", "teste");
                    handleSearchRequest();
                    Log.d("handle", "teste");
                    return true;
                }
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_center);
        //ActionBar actionBar = getActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);
        mToolbar = (Toolbar) findViewById(R.id.act_search_centers_tb_toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_search_sendButtonResource = R.drawable.ic_search_white_36dp;

        lv_searchResults = (ListView) findViewById(R.id.act_search_centers_lv_searchResult);


        mSearchResultsAdapter = new CentersSearchResultsAdapter(this, R.layout.default_center_item,
                new ArrayList<SearchCentersDataResponse>());
        lv_searchResults.setAdapter(mSearchResultsAdapter);


        ac_searchEditText = (AppCompatAutoCompleteTextView) findViewById(R.id.act_search_centers_et_search);
        ac_searchEditText.setOnTouchListener(et_search_touchListener);
        ac_searchEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, et_search_sendButtonResource, 0);
        pb_searchProgress = (ProgressBar) findViewById(R.id.act_search_centers_pb_searchProgress);

        ac_searchEditText.setAdapter(new CentersAutocompleteAdapter(this, new CentersAutocompleteAdapter.AutocompleteListener() {
            @Override
            public void onStartFiltering() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        pb_searchProgress.setVisibility(View.VISIBLE);
                        ac_searchEditText.setOnTouchListener(null);
                        ac_searchEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        flag = false;

                    }
                });


            }

            @Override
            public void onStopFiltering() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pb_searchProgress.setVisibility(View.INVISIBLE);
                        ac_searchEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, et_search_sendButtonResource, 0);
                        ac_searchEditText.setOnTouchListener(et_search_touchListener);
                    }

                });


            }


        }));

       pb_searchViewProgress  = (ProgressBar) findViewById(R.id.act_search_centers_pb_actionViewSearchProgress);
        pb_searchViewProgress.getIndeterminateDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        mSearchResultsAdapter.setOnItemClickListener(new CentersSearchResultsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(SearchCentersDataResponse professional) {
                Log.d("ID CENTRO", professional.getId());
                //pb_loadingProfissionalsData.setVisibility(View.VISIBLE);
                SearchProfileTask searchTask = new SearchProfileTask();
              download(false);
                searchTask.execute(professional.getId(),"id");


            }

        });



        pb_loadingProfissionalsData = (ProgressBar) findViewById(R.id.act_search_centers_pb_loadingDisorderData);


// Radio button handlers
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        RadioButton rb = (RadioButton) radioGroup.findViewById(R.id.radio_name_center);
        rb.toggle();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                if (null != radioButton && checkedId > -1) {

                    if (checkedId == R.id.radio_disorder_center) {
                        ac_searchEditText.setHint("Buscar por nome da Disordem");
                        ac_searchEditText.setVisibility(View.VISIBLE);
                        doNameSearch = false;
                        doDisorderSearch = true;
                        ((CentersAutocompleteAdapter)
                                ac_searchEditText.getAdapter()).setSearchOption("disorder");

                    }else if(checkedId == R.id.radio_name_center) {
                        ac_searchEditText.setHint("Buscar por nome do centro");
                        ac_searchEditText.setVisibility(View.VISIBLE);
                        doNameSearch = true;
                        doDisorderSearch = false;
                        ((CentersAutocompleteAdapter)
                                ac_searchEditText.getAdapter()).setSearchOption("name");
                    }else if(checkedId == R.id.radio_specialty_center) {
                        ac_searchEditText.setHint("Buscar por especialidade");
                        ac_searchEditText.setVisibility(View.VISIBLE);
                        doNameSearch = true;
                        doDisorderSearch = false;
                        ((CentersAutocompleteAdapter)
                                ac_searchEditText.getAdapter()).setSearchOption("specialty");
                    }else if(checkedId == R.id.radio_uf_center) {
                        ac_searchEditText.setHint("Buscar por local");
                        ac_searchEditText.setVisibility(View.VISIBLE);
                        doNameSearch = true;
                        doDisorderSearch = false;
                        ((CentersAutocompleteAdapter)
                                ac_searchEditText.getAdapter()).setSearchOption("local");
                    }


                }}
        });



    }



    // radio button clicked event treatment
    public void onRadioButtonClicked(View v){
        // pass, but necessary here
    }


    private void handleSearchRequest() {
        hideKeyboard();
        final String query = ac_searchEditText.getText().toString();

        if (inputIsValid(query, getSearchType(query))) {
            pb_searchProgress.setVisibility(View.VISIBLE);
            ac_searchEditText.setOnTouchListener(null);
            ac_searchEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

            new SearchProfissionaisTask().execute(query, getSearchType(query));
        }

    }

    private void hideKeyboard() {

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    private boolean inputIsValid(String userInput, String searchType) {
        int minLength = 0;
        String message = null;
        if (searchType == "nome") {
            message = "Digite mais que dois caracteres";
            minLength = 3;
        } else if (searchType== "orphanumber") {
            message = "Digite mais que um caractere";
            minLength = 2;
        }
        if (userInput.length() < minLength) {
            Toast.makeText(SearchCentersActivity.this, message
                    , Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    private String getSearchType(String query) {
        if (query.matches(orphanumberPattern)) {
            //Toast.makeText(SearchDisordersActivity.this, "TIPO ORPHA" ,Toast.LENGTH_SHORT).show();
            return TYPE_ORPHANUMBER;
        } else if (query.matches(icdPattern)) {
            //Toast.makeText(SearchDisordersActivity.this, "TIPO ICD" ,Toast.LENGTH_SHORT).show();
            return TYPE_ICD;
        } else {
            //Toast.makeText(SearchDisordersActivity.this, "TIPO NAME" ,Toast.LENGTH_SHORT).show();
            return TYPE_NAME;
        }
    }


    private class SearchProfissionaisTask extends AsyncTask<String, String, List<SearchCentersDataResponse>> {

        @Override
        protected List<SearchCentersDataResponse> doInBackground(String... params){
            String userInput = params[0];
            String searchType = params[1];
            CenterAdpter disorders = new CenterAdpter();
            CenterAdpter centerFinder = new CenterAdpter();
            List<SearchCentersDataResponse> result = null;


            try {
                if(searchType == "nome"){
                    result = centerFinder.nameSearch(userInput);
                }else{
                    result = disorders.search(userInput, searchType, "code");
                }
                //List<Disease> result = disorders.getStaticDisease();
            } catch (Exception e) {

            }
            return result;
        }

        protected void onPostExecute(List<SearchCentersDataResponse> disorders){
//            MenuItemCompat.collapseActionView(mToolbarSearchItem);
            pb_searchProgress.setVisibility(View.INVISIBLE);
            ac_searchEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, et_search_sendButtonResource, 0);
            ac_searchEditText.setOnTouchListener(et_search_touchListener);

          //  pb_searchViewProgress.setVisibility(View.INVISIBLE);

            if(disorders == null)
                Toast.makeText(SearchCentersActivity.this, "Erro de Conexão"
                        , Toast.LENGTH_LONG).show();
            else if(disorders==null)
                Toast.makeText(SearchCentersActivity.this, "Nenhuma Doença foi encontrada"
                        , Toast.LENGTH_LONG).show();
            else
                SearchCentersActivity.this.renderProfessionalList(disorders);
        }}

    class SearchProfileTask extends AsyncTask<String, String, com.rarasnet.rnp.shared.models.CenterProfile> {


        @Override
        protected com.rarasnet.rnp.shared.models.CenterProfile doInBackground(String... params) {


            String userInput = params[0];
            String searchType = getSearchType(params[1]);
            CenterProfileModel professionalProfileModel = new CenterProfileModel();
            com.rarasnet.rnp.shared.models.CenterProfile result = null;

            try {
                result = professionalProfileModel.getProfileNew(userInput, searchType);
                //List<Disease> result = disorders.getStaticDisease();
            } catch (Exception e) {

            }
            return result;

            // return profile;
        }

        protected void onPostExecute(com.rarasnet.rnp.shared.models.CenterProfile profissionaisDataResponses){

            pb_loadingProfissionalsData.setVisibility(View.INVISIBLE);
            Intent intent = CenterProfile.getIntent(SearchCentersActivity.this,
                    profissionaisDataResponses);
            download(true);
            startActivity(intent);
        }
    }

    private void renderProfessionalList(List<SearchCentersDataResponse> professionals) {

        mSearchResultsAdapter.clear();
        mSearchResultsAdapter.addAll(professionals);

        mSearchResultsAdapter.notifyDataSetChanged();

        lv_searchResults.setVisibility(View.VISIBLE);


        float actionBarSize = getResources().getDimension(R.dimen.abc_action_bar_default_height_material);
        ac_searchEditText.setVisibility(View.GONE);
        shrink(mToolbar, Math.round(actionBarSize), professionals.size());
    }

    public void shrink(final View v, final int newSize, final int numResultados) {

        final int initialHeight = v.getMeasuredHeight();


        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                int tmpSize = initialHeight - (int)(initialHeight * interpolatedTime);
                if(tmpSize > newSize) {
                    v.getLayoutParams().height = tmpSize;
                    v.requestLayout();
                } else {
                    v.getLayoutParams().height = newSize;
                    v.requestLayout();
//                    mToolbarSearchItem.setVisible(true);
                    getSupportActionBar().setTitle(numResultados + " resultados");
                    getSupportActionBar().setDisplayShowTitleEnabled(true);
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) ((initialHeight) / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }







    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if((id == android.R.id.home) && (flag ==false)){
            flag=true;
            Intent intent = new Intent(this,SearchCentersActivity.class);
            startActivity(intent);
            finish();
        }else

        if(id == android.R.id.home){
            finish();
        }

        return true;
    }


   /* private class SearchProfesionalTask extends AsyncTask<String, String, List<Professional>> {

        @Override
        protected List<Professional> doInBackground(String... params){
            String userInput = params[0];
            String searchType = params[1];
            ProfessionalProfileModel disorders = new ProfessionalProfileModel();
            List<ProfessionalProfile> result = null;

            try {
                result = disorders.sea(userInput, searchType);
                //List<Disease> result = disorders.getStaticDisease();
            } catch (Exception e) {

            }
            return result;
        }

        protected void onPostExecute(List<Disorder> disorders){
            MenuItemCompat.collapseActionView(mToolbarSearchItem);
            pb_searchProgress.setVisibility(View.INVISIBLE);
            ac_searchEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, et_search_sendButtonResource, 0);
            ac_searchEditText.setOnTouchListener(et_search_touchListener);

            pb_searchViewProgress.setVisibility(View.INVISIBLE);

            if(disorders == null)
                Toast.makeText(SearchDisordersActivity.this, "Erro de Conexão"
                        , Toast.LENGTH_LONG).show();
            else if(disorders.isEmpty())
                Toast.makeText(SearchDisordersActivity.this, "Nenhuma Doença foi encontrada"
                        , Toast.LENGTH_LONG).show();
            else
                SearchDisordersActivity.this.renderDisordersList(disorders);
        }
    }*/

    public void loadCenter(String id){



        pb_loadingProfissionalsData.setVisibility(View.VISIBLE);
        SearchProfileTask searchTask = new SearchProfileTask();
        Log.d("load center ", id);
        searchTask.execute(id,"id");


    }

    public void download(Boolean j){
        if(j) {
            Log.d("done","done");

            progress.cancel();


        }else{

            // UIUtils.getProgressDialog(SearchDisordersActivity.this);
            progress= UIUtils.getProgressDialog(SearchCentersActivity.this);
            // progress.requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);

            //progress.getWindow().setLayout(UIUtils.dpToPx(200, this), UIUtils.dpToPx(125, this));
            progress.show();


        }}



}