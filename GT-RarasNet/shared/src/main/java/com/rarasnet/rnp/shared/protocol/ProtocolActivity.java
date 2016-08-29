package com.rarasnet.rnp.shared.protocol;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rarasnet.rnp.shared.R;
import com.rarasnet.rnp.shared.application.RarasNet;
import com.rarasnet.rnp.shared.models.ProfessionalProfile;
import com.rarasnet.rnp.shared.models.ProfessionalProfileModel;
import com.rarasnet.rnp.shared.profissionais.controllers.network.responses.LaravelSearchProfissionaisDataResponse;
import com.rarasnet.rnp.shared.profissionais.controllers.network.responses.ProfissionaisAdapter;
import com.rarasnet.rnp.shared.profissionais.controllers.network.responses.SearchProfissionaisDataResponse;
import com.rarasnet.rnp.shared.profissionais.profile.ProfissionalProfile;
import com.rarasnet.rnp.shared.profissionais.views.ProfessionalsAutocompleteAdapter;
import com.rarasnet.rnp.shared.profissionais.views.ProfessionalsSearchResultsAdapter;
import com.rarasnet.rnp.shared.util.UIUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Farina on 5/4/2015.
 * Adapted by Lucas Vieira on 09/08/2016
 *
 *  Activity responsible for professionals info
 *  search.
 */

public class ProtocolActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private MenuItem mToolbarSearchItem;
    private AppCompatAutoCompleteTextView ac_searchEditText;
    private AppCompatMultiAutoCompleteTextView ac_searchBySigns;
    private ListView lv_searchResults;
    private ProtocolSearchResultsAdapter mSearchResultsAdapter;
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
    private String pdf_url = RarasNet.urlPrefix + "/protocols/";

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
                    handleSearchRequest();
                    return true;
                }
            }
            return false;
        }
    };

    private View.OnClickListener show_all_Listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("Cliquei", "Cliquei");
            // busca todos
            new SearchProfissionaisTask().execute("all", "name");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_protocol);

        // Sets show all button listener
        ImageButton showAll = (ImageButton) findViewById((R.id.showAllButton));
        showAll.setOnClickListener(show_all_Listener);

        mToolbar = (Toolbar) findViewById(R.id.act_search_protocol_tb_toolbar);
        setSupportActionBar(mToolbar);
//        getSupportActionBar().setIcon(R.drawable.ic_show_all_white);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_search_sendButtonResource = R.drawable.ic_search_white_36dp;

        lv_searchResults = (ListView) findViewById(R.id.act_search_protocol_lv_searchResult);

        mSearchResultsAdapter = new ProtocolSearchResultsAdapter(this,
                R.layout.default_result_profe_item,
                new ArrayList<ProtocolModel>());
        lv_searchResults.setAdapter(mSearchResultsAdapter);


        ac_searchEditText = (AppCompatAutoCompleteTextView)
                findViewById(R.id.act_search_protocol_et_search);
        ac_searchEditText.setOnTouchListener(et_search_touchListener);
        ac_searchEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                et_search_sendButtonResource, 0);
        pb_searchProgress = (ProgressBar)
                findViewById(R.id.act_search_protocol_pb_searchProgress);


        // AUTOCOMPLETE
        ac_searchEditText.setAdapter(new ProtocolAutocompleteAdapter(this,
                new ProtocolAutocompleteAdapter.AutocompleteListener() {
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

            mSearchResultsAdapter.setOnItemClickListener(new ProtocolSearchResultsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ProtocolModel professional) {
                Log.d("CLICOU NO", professional.getName_pdf());

                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(pdf_url + professional.getName_pdf()));
                startActivity(browserIntent);
            }

        });

        pb_loadingProfissionalsData = (ProgressBar)
                findViewById(R.id.act_search_protocol_pb_loadingDisorderData);

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
            Toast.makeText(ProtocolActivity.this, message
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
    private class SearchProfissionaisTask extends AsyncTask<String, String, List<ProtocolModel>> {

        @Override
        // protected List<SearchProfissionaisDataResponse> doInBackground(String... params){
        protected List<ProtocolModel> doInBackground(String... params){

            String userInput = params[0];
            String searchType = params[1];
            ProtocolProfileModel protocols = new ProtocolProfileModel();
            List<ProtocolModel> result = null;

            Log.d("profiss",searchType);

            try {
                result = protocols.getProtocolList(userInput);
            } catch (Exception e) {

            }
            Log.d("AQUI1","AQUI1");

            return result;
        }

        protected void onPostExecute(List<ProtocolModel> disorders){

            //MenuItemCompat.collapseActionView(mToolbarSearchItem);
            pb_searchProgress.setVisibility(View.INVISIBLE);
            ac_searchEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, et_search_sendButtonResource, 0);
            ac_searchEditText.setOnTouchListener(et_search_touchListener);

//            pb_searchViewProgress.setVisibility(View.INVISIBLE);


            if(disorders == null)
                Toast.makeText(ProtocolActivity.this, "Erro de Conexão"
                        , Toast.LENGTH_LONG).show();
            else if(disorders==null)
                Toast.makeText(ProtocolActivity.this, "Nenhuma Doença foi encontrada"
                        , Toast.LENGTH_LONG).show();
            else{
                ProtocolActivity.this.renderProfessionalList(disorders);
            }

        }
    }



    private void renderProfessionalList(List<ProtocolModel> professionals) {

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if((id == android.R.id.home) && (flag ==false)){
            flag=true;
            Intent intent = new Intent(this,ProtocolActivity.class);
            startActivity(intent);
            finish();
        }else if(id == android.R.id.home){
            finish();
        }
        return true;

    }



}