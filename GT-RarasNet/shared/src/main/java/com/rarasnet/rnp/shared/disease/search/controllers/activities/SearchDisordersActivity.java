package com.rarasnet.rnp.shared.disease.search.controllers.activities;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rarasnet.rnp.shared.R;
import com.rarasnet.rnp.shared.disease.profile.DisorderProfileActivity;
import com.rarasnet.rnp.shared.disease.search.adapters.DisorderAutocompleteBySignAdapter;
import com.rarasnet.rnp.shared.disease.search.adapters.DisordersAutocompleteAdapter;
import com.rarasnet.rnp.shared.disease.search.adapters.DisordersSearchResultsAdapter;
import com.rarasnet.rnp.shared.disease.search.models.DisorderProfile;
import com.rarasnet.rnp.shared.disease.search.models.DisorderProfileModel;
import com.rarasnet.rnp.shared.disease.search.models.DisordersModel;
import com.rarasnet.rnp.shared.disease.search.models.JsonDisorderSign;
import com.rarasnet.rnp.shared.disease.search.models.persistence.BancoDeDados;
import com.rarasnet.rnp.shared.models.Disorder;
import com.rarasnet.rnp.shared.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Farina on 5/4/2015.
 */
public class SearchDisordersActivity extends AppCompatActivity {

//    private DisordersAdapter listAdapter;
  //  private DisorderAdapterSqlite listAdapter1;
   // private DiseaseActivity listdis;
   // private static List<Disease> disease;
private android.app.AlertDialog progress;

    private static final String TYPE_NAME = "name";
    private static final String TYPE_ORPHANUMBER = "orphanumber";
    private static final String TYPE_ICD = "icd";



    //Numeric Pattern
    private static final String orphanumberPattern = "[0-9]+";
    private static final String icdPattern = "[A-Z][0-9][0-9].[0-9]";

    private boolean flag=true;

   // private BancoDeDados db;
   // private List<Disease> diseases = new ArrayList<Disease>();

   //private DiseaseAdapter diseasesAdapter;
    //public static final int REQUEST_EDICAO = 1;
    //public static final int REQUEST_SALVOU = 2;

    private BancoDeDados db = new BancoDeDados(this);
    private List<Disorder> diseases = new ArrayList<Disorder>();

    private Toolbar mToolbar;
    private MenuItem mToolbarSearchItem;
    private AppCompatAutoCompleteTextView ac_searchEditText;
    private AppCompatMultiAutoCompleteTextView ac_searchBySigns;
    private AppCompatAutoCompleteTextView ac_searchByCid;
    private ListView lv_searchResults;
    private DisordersSearchResultsAdapter mSearchResultsAdapter;
    private int et_search_sendButtonResource;
    private ProgressBar pb_searchProgress;
    private ProgressBar pb_searchViewProgress;
    private ProgressBar pb_loadingDisorderData;



    private RelativeLayout rl_fadeMenu;
    private TextView tv_searchType;
    private RelativeLayout rl_searchType;
    private boolean doSignsSearch = false;

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
                    Log.d("ESSE handle","teste");
                    handleSearchRequest();
                    return true;
                }
            }
            return false;
        }
    };

    private View.OnTouchListener ac_searchBySigns_touchListener = new View.OnTouchListener() {
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

    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_disorders);
        mToolbar = (Toolbar) findViewById(R.id.act_search_disorders_tb_toolbar);
        this.setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        et_search_sendButtonResource = R.drawable.ic_search_white_36dp;




        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        RadioButton rb = (RadioButton) radioGroup.findViewById(R.id.radio_nome);
        rb.toggle();
        //radioGroup.clearCheck();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                if (null != radioButton && checkedId > -1) {

                    if (checkedId == R.id.radio_nome) {
                        doSignsSearch = false;
                        ac_searchEditText.setHint("Busca por Nome da Disordem");
                        ac_searchEditText.setVisibility(View.VISIBLE);
                        ac_searchByCid.setVisibility(View.INVISIBLE);
                        ac_searchBySigns.setVisibility(View.INVISIBLE);
//                    }else if(checkedId == R.id.radio_sinais) {
//                    //tv_searchType.setText("Busca por Nome, CID e Orphanumber");
//                    doSignsSearch = true;
//
//                    ac_searchEditText.setVisibility(View.INVISIBLE);
//                        ac_searchByCid.setVisibility(View.INVISIBLE);
//                    ac_searchBySigns.setVisibility(View.VISIBLE);
//                        Toast.makeText(SearchDisordersActivity.this,
//                                "Os sinais deve ser digitado em Inglês.",
//                                Toast.LENGTH_LONG).show();
                }else if(checkedId == R.id.radio_cid) {
                        //arrumar depois
                    //tv_searchType.setText("Busca por CID");
                    doSignsSearch = false;
                        ac_searchEditText.setHint("Busca por CID");
                    ac_searchEditText.setVisibility(View.VISIBLE);
                    ac_searchBySigns.setVisibility(View.INVISIBLE);
                        ac_searchByCid.setVisibility(View.INVISIBLE);

                    //Toast.makeText(SearchDisordersActivity.this, radioButton.getText(), Toast.LENGTH_SHORT).show();
                }
            }}
        });






        lv_searchResults = (ListView) findViewById(R.id.act_search_disorders_lv_searchResult);
        mSearchResultsAdapter = new DisordersSearchResultsAdapter(this, R.layout.default_search_item,
                new ArrayList<Disorder>());
        lv_searchResults.setAdapter(mSearchResultsAdapter);

        ac_searchEditText = (AppCompatAutoCompleteTextView) findViewById(R.id.act_search_disorders_et_search);
        ac_searchByCid = (AppCompatAutoCompleteTextView) findViewById(R.id.act_search_disorders_ac_searchByCid);
        ac_searchEditText.setOnTouchListener(et_search_touchListener);
        ac_searchEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, et_search_sendButtonResource, 0);
        pb_searchProgress = (ProgressBar) findViewById(R.id.act_search_disorders_pb_searchProgress);

        ac_searchEditText.setAdapter(new DisordersAutocompleteAdapter(this, new DisordersAutocompleteAdapter.AutocompleteListener() {
            @Override
            public void onStartFiltering() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pb_searchProgress.setVisibility(View.VISIBLE);
                        ac_searchEditText.setOnTouchListener(null);
                        ac_searchEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        flag=false;
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

       // pb_searchViewProgress = (ProgressBar) findViewById(R.id.act_search_disorders_pb_actionViewSearchProgress);
        //pb_searchViewProgress.getIndeterminateDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        mSearchResultsAdapter.setOnItemClickListener(new DisordersSearchResultsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Disorder disorder) {


              //  pb_loadingDisorderData.setVisibility(View.VISIBLE);
                //pb_loadingDisorderData.getIndeterminateDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
               download(false);
                //UIUtils.getProgressDialog(SearchDisordersActivity.this);
                SearchProfileTask searchTask = new SearchProfileTask();
               //UIUtils.getProgressDialog(SearchDisordersActivity.this).dismiss();


                searchTask.execute(disorder.getDesorden_id());

            }
        });

        pb_loadingDisorderData = (ProgressBar)
                findViewById(R.id.act_search_disorders_pb_loadingDisorderData);


      //tv_searchType = (TextView) findViewById(R.id.act_search_disorders_tv_searchType);
        //rl_searchType = (RelativeLayout) findViewById(R.id.act_search_disorders_rl_searchType);
        //rl_fadeMenu = (RelativeLayout) findViewById(R.id.act_search_disorders_rl_fadeMenu);
        ac_searchBySigns = (AppCompatMultiAutoCompleteTextView) findViewById(R.id.act_search_disorders_ac_searchBySigns);

        /*rl_searchType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(doSignsSearch) {
                    tv_searchType.setText("Busca por Sinais");
                    doSignsSearch = false;
                    ac_searchEditText.setVisibility(View.VISIBLE);
                    ac_searchBySigns.setVisibility(View.INVISIBLE);
                } else {
                    tv_searchType.setText("Busca por Nome, CID e Orphanumber");
                    doSignsSearch = true;
                    ac_searchEditText.setVisibility(View.INVISIBLE);
                    ac_searchBySigns.setVisibility(View.VISIBLE);
                }
            }
        });*/


       ac_searchBySigns.setAdapter(new DisorderAutocompleteBySignAdapter(this,
               new DisorderAutocompleteBySignAdapter.AutocompleteListener() {
            @Override
            public void onStartFiltering() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pb_searchProgress.setVisibility(View.VISIBLE);
                        ac_searchBySigns.setOnTouchListener(null);
                        ac_searchBySigns.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
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
                        ac_searchBySigns.setCompoundDrawablesWithIntrinsicBounds(0, 0, et_search_sendButtonResource, 0);
                        ac_searchBySigns.setOnTouchListener(et_search_touchListener);
                    }
                });
            }
        }));
        ac_searchBySigns.setTokenizer(new AppCompatMultiAutoCompleteTextView.CommaTokenizer());
        ac_searchBySigns.setOnTouchListener(ac_searchBySigns_touchListener);



    }

    ///------------------------------------------------------------------------///
    /// Menu handlers
    ///------------------------------------------------------------------------///

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_all, menu);
        return true;
    }



    public void onRadioButtonClicked(View v){
        // Your code on click
    }


    private void handleSearchRequest() {
        hideKeyboard();

        if(!doSignsSearch) {
            final String query = ac_searchEditText.getText().toString();
            if (inputIsValid(query, getSearchType(query))) {
                pb_searchProgress.setVisibility(View.VISIBLE);
                ac_searchEditText.setOnTouchListener(null);
                ac_searchEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                new SearchDiseasesTask().execute(query, getSearchType(query));
            }
        } else {
            final String query = ac_searchBySigns.getText().toString();
            pb_searchProgress.setVisibility(View.VISIBLE);
            ac_searchBySigns.setOnTouchListener(null);
            ac_searchBySigns.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            new SearchDiseasesTaskBySign().execute(query);
        }
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
                    //mToolbarSearchItem.setVisible(true);
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
//        return super.onCreateOptionsMenu(menu);}

    /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_search_disorders, menu);

        mToolbarSearchItem = menu.findItem(R.id.action_search);
        mToolbarSearchItem.setVisible(false);

        SearchManager searchManager = (SearchManager) this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if ( mToolbarSearchItem  != null) {
            searchView = (SearchView)  mToolbarSearchItem.getActionView();
            View.OnClickListener handler = new View.OnClickListener() {
                public void onClick(View v) {
                    SearchDisordersActivity.this.registerForContextMenu(v);
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
                    pb_searchViewProgress.setVisibility(View.VISIBLE);
                    if(inputIsValid(query, getSearchType(query))) {
                        new SearchDiseasesTask().execute(query, getSearchType(query));
                    }
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if((id == android.R.id.home) && (flag ==false)){
            flag=true;
            Intent intent = new Intent(this,SearchDisordersActivity.class);
            startActivity(intent);
            finish();
        }else if(id == R.id.menu_show){
            // show all button selected
        }

        if(id == android.R.id.home){
            finish();
        }

        return true;
    }


    private void renderDisordersList(List<Disorder> disorders) {
        mSearchResultsAdapter.clear();
        mSearchResultsAdapter.addAll(disorders);
        mSearchResultsAdapter.notifyDataSetChanged();
        lv_searchResults.setVisibility(View.VISIBLE);
        float actionBarSize = getResources().getDimension(R.dimen.abc_action_bar_default_height_material);
        ac_searchEditText.setVisibility(View.GONE);
        shrink(mToolbar, Math.round(actionBarSize), disorders.size());
    }


    private void hideKeyboard() {

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    /*
    *Método para associar o Botão de Pesquisa da Activity ao seu Listener
    */

    private boolean inputIsValid(String userInput, String searchType) {
        int minLength = 0;
        String message = null;
        if (searchType == "name") {
            message = "Digite mais que dois caracteres";
            minLength = 3;
        } else if (searchType== "orphanumber") {
            message = "Digite mais que um caractere";
            minLength = 2;
        }
        if (userInput.length() < minLength) {
            Toast.makeText(SearchDisordersActivity.this, message
                    , Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

 /*
* Inner Class responsável pela criação da Thread que fará a consulta ao BD,
* seus resultados
*/

    private class SearchDiseasesTask extends AsyncTask<String, String, List<Disorder>> {

        @Override
        protected List<Disorder> doInBackground(String... params){
            String userInput = params[0];
            String searchType = params[1];
            DisordersModel disorders = new DisordersModel();
            List<Disorder> result = null;
            Log.d("Search Disorder", searchType);

            try {

                if(searchType == TYPE_NAME){
                    result = disorders.nameSearch(userInput);
                }else if(searchType == TYPE_ICD){
                    result = disorders.cidSearch(userInput);

                }else{
                    result = disorders.search(userInput, searchType,"code");
                }
                //List<Disease> result = disorders.getStaticDisease();
            } catch (Exception e) {

            }
            return result;
        }

        protected void onPostExecute(List<Disorder> disorders){
           // MenuItemCompat.collapseActionView(mToolbarSearchItem);
            pb_searchProgress.setVisibility(View.INVISIBLE);
            ac_searchEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, et_search_sendButtonResource, 0);
            ac_searchEditText.setOnTouchListener(et_search_touchListener);

//            pb_searchViewProgress.setVisibility(View.INVISIBLE);

            if(disorders == null)
                Toast.makeText(SearchDisordersActivity.this, "Erro de Conexão"
                        , Toast.LENGTH_LONG).show();
            else if(disorders.isEmpty())
                Toast.makeText(SearchDisordersActivity.this, "Nenhuma Disordem foi encontrada"
                        , Toast.LENGTH_LONG).show();
            else
                SearchDisordersActivity.this.renderDisordersList(disorders);
        }
    }

    class SearchProfileTask extends AsyncTask<String, String, DisorderProfile> {


        @Override
        protected DisorderProfile doInBackground(String... params) {
           // UIUtils.getProgressDialog(SearchDisordersActivity.this);
            Log.d("Estou aqui","aqui");
            String diseaseId = params[0];
            DisorderProfileModel diseaseProfileModel = new DisorderProfileModel();
            DisorderProfile profile = null;

            try {
                profile = diseaseProfileModel.getProfileNew(diseaseId);
            } catch (Exception e) {

            }

            return profile;
        }

        protected void onPostExecute(DisorderProfile profile) {

            pb_loadingDisorderData.setVisibility(View.INVISIBLE);


            Intent it = DisorderProfileActivity.getIntent(SearchDisordersActivity.this, profile);


            download(true);

            startActivity(it);
        }
    }

    private class SearchDiseasesTaskBySign extends AsyncTask<String, String, List<Disorder>> {

        @Override
        protected List<Disorder> doInBackground(String... params){
            String userInput = params[0];
            JsonDisorderSign disorders = new JsonDisorderSign();
            List<Disorder> result = null;

            try {
                result = disorders.search(userInput, "dis");
                //List<Disease> result = disorders.getStaticDisease();
            } catch (Exception e) {

            }
            return result;
        }

        protected void onPostExecute(List<Disorder> disorders){
            //MenuItemCompat.collapseActionView(mToolbarSearchItem);
            pb_searchProgress.setVisibility(View.INVISIBLE);
            ac_searchBySigns.setCompoundDrawablesWithIntrinsicBounds(0, 0, et_search_sendButtonResource, 0);
            ac_searchBySigns.setOnTouchListener(ac_searchBySigns_touchListener);
//            pb_searchViewProgress.setVisibility(View.INVISIBLE);


            if(disorders == null)
                Toast.makeText(SearchDisordersActivity.this, "Erro de Conexão"
                        , Toast.LENGTH_LONG).show();
            else if(disorders.isEmpty())
                Toast.makeText(SearchDisordersActivity.this, "Nenhuma Disordem foi encontrada"
                        , Toast.LENGTH_LONG).show();
            else
                SearchDisordersActivity.this.renderDisordersList(disorders);
        }
    }




    public void download(Boolean j){
        if(j) {
            Log.d("done","done");

            progress.cancel();


        }else{

           // UIUtils.getProgressDialog(SearchDisordersActivity.this);
            progress= UIUtils.getProgressDialog(SearchDisordersActivity.this);
           // progress.requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);

            //progress.getWindow().setLayout(UIUtils.dpToPx(200, this), UIUtils.dpToPx(125, this));
            progress.show();
    /*
    progress.setMessage("Downloading Music");
    progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    progress.setIndeterminate(true);
    progress.setProgress(0);
    progress.show();







    final int totalProgressTime = 10;
    final Thread t = new Thread() {
        @Override
        public void run() {
            int jumpTime = 0;

            while(jumpTime < totalProgressTime) {
                try {
                    sleep(200);
                    jumpTime += 5;
                    progress.setProgress(jumpTime);
                }
                catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();


                }
            }
        }
    };

    t.start();*/
}}

}