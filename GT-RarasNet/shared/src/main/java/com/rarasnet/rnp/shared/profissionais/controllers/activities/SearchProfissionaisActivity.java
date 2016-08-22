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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
    private MenuItem mToolbarSearchItem;
    private AppCompatAutoCompleteTextView ac_searchEditText;
    private AppCompatMultiAutoCompleteTextView ac_searchBySigns;
    private ListView lv_searchResults;
    private ProfessionalsSearchResultsAdapter mSearchResultsAdapter;
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
    //private ListaProfissionaisAdapter mAdapter;
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
        ac_searchEditText.setAdapter(new ProfessionalsAutocompleteAdapter(this, new ProfessionalsAutocompleteAdapter.AutocompleteListener() {
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

      //pb_searchViewProgress  = (ProgressBar) findViewById(R.id.act_search_professional_pb_actionViewSearchProgress);
        //pb_searchViewProgress.getIndeterminateDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        mSearchResultsAdapter.setOnItemClickListener(new ProfessionalsSearchResultsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(LaravelSearchProfissionaisDataResponse professional) {

                //pb_loadingProfissionalsData.setVisibility(View.VISIBLE);
                SearchProfileTask searchTask = new SearchProfileTask();
                download(false);
                searchTask.execute(professional.getId(),"id");


            }

        });



        pb_loadingProfissionalsData = (ProgressBar) findViewById(R.id.act_search_professional_pb_loadingDisorderData);






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
            //List<SearchProfissionaisDataResponse> result = null;
            List<LaravelSearchProfissionaisDataResponse> newResult = null;

                Log.d("profiss",searchType);

            try {
                //result = disorders.search(userInput, searchType);
                newResult = disorders.searchLaravel(userInput, searchType);

                //List<Disease> result = disorders.getStaticDisease();
            } catch (Exception e) {

            }
            Log.d("AQUI1","AQUI1");

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
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

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