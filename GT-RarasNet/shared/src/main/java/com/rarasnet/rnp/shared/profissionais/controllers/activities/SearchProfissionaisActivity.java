package com.rarasnet.rnp.shared.profissionais.controllers.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatMultiAutoCompleteTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rarasnet.rnp.shared.R;
import com.rarasnet.rnp.shared.models.ProfessionalProfile;
import com.rarasnet.rnp.shared.models.ProfessionalProfileModel;
import com.rarasnet.rnp.shared.profissionais.controllers.network.responses.LaravelSearchProfissionaisDataResponse;
import com.rarasnet.rnp.shared.profissionais.controllers.network.responses.ProfissionaisAdapter;
import com.rarasnet.rnp.shared.profissionais.controllers.network.responses.SearchProfissionaisDataResponse;
import com.rarasnet.rnp.shared.profissionais.profile.ProfissionalProfile;
import com.rarasnet.rnp.shared.profissionais.views.ProfessionalsAutocompleteAdapter;
import com.rarasnet.rnp.shared.profissionais.views.ProfessionalsSearchResultsAdapter;
import com.rarasnet.rnp.shared.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Farina on 5/4/2015.
 * Adapted by Lucas Vieira on 09/08/2016
 *
 *  Activity responsible for professionals info
 *  search.
 */

public class SearchProfissionaisActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private AppCompatAutoCompleteTextView ac_searchEditText;
    private ListView lv_searchResults;
    private ProfessionalsSearchResultsAdapter mSearchResultsAdapter;
    private int et_search_sendButtonResource;
    private ProgressBar pb_searchProgress;
    private ProgressBar pb_loadingProfissionalsData;
    private android.app.AlertDialog progress;
    private boolean doNameSearch = false;
    private boolean doDisorderSearch = false;
    private static final String TYPE_NAME = "nome";
    private static final String TYPE_ORPHANUMBER = "orphanumber";
    private static final String TYPE_ICD = "icd";
    //Numeric Pattern
    private static final String orphanumberPattern = "[0-9]+";
    private static final String icdPattern = "[A-Z][0-9][0-9].[0-9]";
    private boolean flag = true;
    private RelativeLayout listFrame;
    private ProgressDialog pDialog;
    private ListView mListaProfissionais;
    private RadioGroup radioGroup;
    private String searchOption = "name";
    private int numResultados = 0;
    private RelativeLayout mapLayout;
    private Spinner stateSpinner;
    ArrayAdapter<String> adapter;

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
                    Log.d("handle","teste");
                    Log.d("HANDLED NEW REQUEST", "handle it");

                    handleSearchRequest();
                    Log.d("HANDLED NEW REQUEST", "teste");

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
        setContentView(R.layout.activity_search_professional);
        //ActionBar actionBar = getActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);
        mToolbar = (Toolbar) findViewById(R.id.act_search_professional_tb_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_search_sendButtonResource = R.drawable.ic_search_white_36dp;

        lv_searchResults = (ListView) findViewById(R.id.act_search_professional_lv_searchResult);

        mSearchResultsAdapter = new ProfessionalsSearchResultsAdapter(this, R.layout.default_result_profe_item,
                new ArrayList<LaravelSearchProfissionaisDataResponse>());
        lv_searchResults.setAdapter(mSearchResultsAdapter);


        ac_searchEditText = (AppCompatAutoCompleteTextView) findViewById(R.id.act_search_professional_et_search);
        ac_searchEditText.setOnTouchListener(et_search_touchListener);
        ac_searchEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, et_search_sendButtonResource, 0);
        pb_searchProgress = (ProgressBar) findViewById(R.id.act_search_professional_pb_searchProgress);


        // AUTOCOMPLETE
        ac_searchEditText.setAdapter(new ProfessionalsAutocompleteAdapter(this,
                new ProfessionalsAutocompleteAdapter.AutocompleteListener() {
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
                                ac_searchEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                                        et_search_sendButtonResource, 0);
                                ac_searchEditText.setOnTouchListener(et_search_touchListener);
                            }
                        });


                    }


                }));


        mSearchResultsAdapter.setOnItemClickListener(new ProfessionalsSearchResultsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(LaravelSearchProfissionaisDataResponse professional) {

                //pb_loadingProfissionalsData.setVisibility(View.VISIBLE);
                SearchProfileTask searchTask = new SearchProfileTask();
                download(false);
                searchTask.execute(professional.getId(), "id");


            }

        });


        pb_loadingProfissionalsData = (ProgressBar) findViewById(R.id.act_search_professional_pb_loadingDisorderData);


        // Radio button handlers
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        RadioButton rb = (RadioButton) radioGroup.findViewById(R.id.radio_name_professional);
        rb.toggle();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                if (null != radioButton && checkedId > -1) {

//                    if (checkedId == R.id.radio_disorder_professional) {
//                        ac_searchEditText.setHint("Buscar por nome da disordem");
//                        ac_searchEditText.setVisibility(View.VISIBLE);
//                        ((ProfessionalsAutocompleteAdapter)
//                                ac_searchEditText.getAdapter()).setSearchOption("disorder");
//                        searchOption = "disorder";
//
//                    } else
                    if (checkedId == R.id.radio_name_professional) {
                        ac_searchEditText.setHint("Buscar por nome do profissional");
                        ac_searchEditText.setVisibility(View.VISIBLE);
                        ((ProfessionalsAutocompleteAdapter)
                                ac_searchEditText.getAdapter()).setSearchOption("name");
                        searchOption = "name";


                    } else if (checkedId == R.id.radio_specialty_professional) {
                        ac_searchEditText.setHint("Buscar por especialidade");
                        ac_searchEditText.setVisibility(View.VISIBLE);
                        ((ProfessionalsAutocompleteAdapter)
                                ac_searchEditText.getAdapter()).setSearchOption("specialty");
                        searchOption = "specialty";

                    } else { //local
                        ac_searchEditText.setHint("Buscar por local");
                        ac_searchEditText.setVisibility(View.VISIBLE);
                        ((ProfessionalsAutocompleteAdapter)
                                ac_searchEditText.getAdapter()).setSearchOption("local");
                        searchOption = "local";

                    }
                }
            }
        });


        // map handler

//        mapLayout = (RelativeLayout) findViewById(R.id.map_layout);
//        mapLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("NMAE", Integer.toString(v.getId()));
//            }
//
//        });

//        stateSpinner = (Spinner) findViewById(R.id.stateSpinner);
//        String businessType[] = { "Automobile", "Food", "Computers", "Education",
//                "Personal", "Travel" };
//
////        // Create an ArrayAdapter using the string array and a default spinner layout
//        adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item,  businessType);
//        stateSpinner.setAdapter(adapter);


//        stateSpinner.setPrompt("Select your favorite Planet!");


//        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view,
//                                       int pos, long id) {
//                String option = parent.getItemAtPosition(pos).toString();
//                Log.d("SELECIONADO", option);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> arg0) {
//                // TODO Auto-generated method stub
//
//            }
//        });
    }

    public void searchLocal(MenuItem item){

        pb_searchProgress.setVisibility(View.VISIBLE);
        ac_searchEditText.setOnTouchListener(null);
        ac_searchEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        searchOption = "uf";
        Log.d("ai", item.getTitle().toString());
        new SearchProfissionaisTask().execute(item.getTitle().toString().toLowerCase(),
                searchOption);
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
        if (searchOption == "nome") {
            message = "Digite mais que dois caracteres";
            minLength = 3;
        } else if (searchType== "orphanumber") {
            message = "Digite mais que um caractere";
            minLength = 2;
        }
        if (userInput.length() < minLength) {
            Toast.makeText(SearchProfissionaisActivity.this, message
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


  //  private class SearchProfissionaisTask extends AsyncTask<String, String, List<SearchProfissionaisDataResponse>> {
  private class SearchProfissionaisTask extends AsyncTask<String, String, List<LaravelSearchProfissionaisDataResponse>> {

        @Override
       // protected List<SearchProfissionaisDataResponse> doInBackground(String... params){
        protected List<LaravelSearchProfissionaisDataResponse> doInBackground(String... params){
            String userInput = params[0];
            String searchType = params[1];
            ProfissionaisAdapter disorders = new ProfissionaisAdapter();
            List<LaravelSearchProfissionaisDataResponse> newResult = null;


            try {
                Log.d("type", searchOption);
                Log.d("userInput", userInput);

                newResult = disorders.searchLaravel(userInput, "0", searchOption);

                // sets number of results
                if(newResult != null && !newResult.isEmpty()){
                    numResultados = Integer.parseInt(newResult.get(0).getCount());
                }

                mSearchResultsAdapter.setQuery(userInput);
                mSearchResultsAdapter.setSearchtype(searchOption);

            } catch (Exception e) {
                Log.d("[SPA]Search error", e.toString());
            }

            return newResult;
        }

        protected void onPostExecute(List<LaravelSearchProfissionaisDataResponse> disorders){

            //MenuItemCompat.collapseActionView(mToolbarSearchItem);
            pb_searchProgress.setVisibility(View.INVISIBLE);
            ac_searchEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, et_search_sendButtonResource, 0);
            ac_searchEditText.setOnTouchListener(et_search_touchListener);

//            pb_searchViewProgress.setVisibility(View.INVISIBLE);


            if(disorders == null)
                Toast.makeText(SearchProfissionaisActivity.this, "Erro de Conexão"
                        , Toast.LENGTH_LONG).show();
            else if(disorders==null)
                Toast.makeText(SearchProfissionaisActivity.this, "Nenhuma Doença foi encontrada"
                        , Toast.LENGTH_LONG).show();
            else{
                SearchProfissionaisActivity.this.renderProfessionalList(disorders);
            }

        }
    }

    class SearchProfileTask extends AsyncTask<String, String, ProfessionalProfile> {


        @Override
        protected ProfessionalProfile doInBackground(String... params) {


            String userInput = params[0];
            String searchType = getSearchType(params[1]);
            ProfessionalProfileModel professionalProfileModel = new ProfessionalProfileModel();
            ProfessionalProfile result = null;

            try {
                Log.d("PROFESSIONAL",userInput);
                Log.d("PROFESSIONAL TYPE",searchType);
//                result = professionalProfileModel.getProfile(userInput, searchType);
                result = professionalProfileModel.getProfileNew(userInput, searchType);


                //List<Disease> result = disorders.getStaticDisease();
            } catch (Exception e) {

            }

            return result;

           // return profile;
        }

        protected void onPostExecute(ProfessionalProfile profissionaisDataResponses){

            pb_loadingProfissionalsData.setVisibility(View.INVISIBLE);
            Intent intent = ProfissionalProfile.getIntent(SearchProfissionaisActivity.this, profissionaisDataResponses);
            download(true);
            startActivity(intent);
        }
    }

    private void renderProfessionalList(List<LaravelSearchProfissionaisDataResponse> professionals) {

        mSearchResultsAdapter.clear();
        mSearchResultsAdapter.addAll(professionals);
        mSearchResultsAdapter.notifyDataSetChanged();
        lv_searchResults.setVisibility(View.VISIBLE);


        float actionBarSize = getResources().getDimension(R.dimen.abc_action_bar_default_height_material);
        ac_searchEditText.setVisibility(View.GONE);
        shrink(mToolbar, Math.round(actionBarSize), numResultados);
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
//    }

    /* @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.menu_search_professional, menu);

            mToolbarSearchItem = menu.findItem(R.id.action_search);
            mToolbarSearchItem.setVisible(false);

            SearchManager searchManager = (SearchManager) this.getSystemService(Context.SEARCH_SERVICE);

            SearchView searchView = null;
            if ( mToolbarSearchItem  != null) {
                searchView = (SearchView)  mToolbarSearchItem.getActionView();
                View.OnClickListener handler = new View.OnClickListener() {
                    public void onClick(View v) {
                        SearchProfissionaisActivity.this.registerForContextMenu(v);
                        openContextMenu(v);
                    }
                };
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        return false;
                    }


                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        hideKeyboard();
                     //   pb_searchViewProgress.setVisibility(View.VISIBLE);

                        return false;
                    }
                });
                searchView.setOnSearchClickListener(handler);
            }
            if (searchView != null) {
               searchView.setSearchableInfo(searchManager.getSearchableInfo(this.getComponentName()));
            }


            return super.onCreateOptionsMenu(menu);
        }*/

    /// Menu handlers
    ///------------------------------------------------------------------------///

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_all, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if((id == android.R.id.home) && (flag ==false)){
            flag=true;
            Intent intent = new Intent(this,SearchProfissionaisActivity.class);
            startActivity(intent);
            finish();
        }else if(id == android.R.id.home){
            finish();
        }else if(id == R.id.menu_show){
            pb_searchProgress.setVisibility(View.VISIBLE);
            ac_searchEditText.setOnTouchListener(null);
            ac_searchEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            searchOption = "all";

            new SearchProfissionaisTask().execute("whatever", "all");
        }
        return true;

    }


    public void download(Boolean j){
        if(j) {
            Log.d("done","done");

            progress.cancel();


        }else{

            // UIUtils.getProgressDialog(SearchDisordersActivity.this);
            progress= UIUtils.getProgressDialog(SearchProfissionaisActivity.this);
            // progress.requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);

            //progress.getWindow().setLayout(UIUtils.dpToPx(200, this), UIUtils.dpToPx(125, this));
            progress.show();


        }}




}