package com.rarasnet.rnp.desordens.search.controllers.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatMultiAutoCompleteTextView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
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

import com.rarasnet.rnp.desordens.R;
import com.rarasnet.rnp.desordens.profile.DisorderProfileActivity;
import com.rarasnet.rnp.desordens.search.adapters.DisorderAutocompleteBySignAdapter;
import com.rarasnet.rnp.desordens.search.adapters.DisordersAutocompleteAdapter;
import com.rarasnet.rnp.desordens.search.adapters.DisordersSearchResultsAdapter;
import com.rarasnet.rnp.desordens.search.models.DisorderProfile;
import com.rarasnet.rnp.desordens.search.models.DisorderProfileModel;
import com.rarasnet.rnp.desordens.search.models.DisordersModel;
import com.rarasnet.rnp.desordens.search.models.JsonDisorderSign;
import com.rarasnet.rnp.desordens.search.models.persistence.BancoDeDados;
import com.rarasnet.rnp.shared.models.Disorder;

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

    private static final String TYPE_NAME = "name";
    private static final String TYPE_ORPHANUMBER = "orphanumber";
    private static final String TYPE_ICD = "icd";

    //Numeric Pattern
    private static final String orphanumberPattern = "[0-9]+";
    private static final String icdPattern = "[A-Z][0-9][0-9].[0-9]";

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
                    Log.d("handle","teste");
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_disorders);
        mToolbar = (Toolbar) findViewById(R.id.act_search_disorders_tb_toolbar);
        this.setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        et_search_sendButtonResource = R.drawable.ic_send_white_24dp;


        lv_searchResults = (ListView) findViewById(R.id.act_search_disorders_lv_searchResult);
        Log.d("SEtadpter","adpte");
        mSearchResultsAdapter = new DisordersSearchResultsAdapter(this, R.layout.default_search_item,
                new ArrayList<Disorder>());
        lv_searchResults.setAdapter(mSearchResultsAdapter);

        ac_searchEditText = (AppCompatAutoCompleteTextView) findViewById(R.id.act_search_disorders_et_search);
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

        pb_searchViewProgress = (ProgressBar) findViewById(R.id.act_search_disorders_pb_actionViewSearchProgress);

        mSearchResultsAdapter.setOnItemClickListener(new DisordersSearchResultsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Disorder disorder) {
                Log.d("Teste dis","aqui");
                pb_loadingDisorderData.setVisibility(View.VISIBLE);
                SearchProfileTask searchTask = new SearchProfileTask();
                searchTask.execute(disorder.getId());
            }
        });

        pb_loadingDisorderData = (ProgressBar)
                findViewById(R.id.act_search_disorders_pb_loadingDisorderData);


       // tv_searchType = (TextView) findViewById(R.id.act_search_disorders_tv_searchType);
        //rl_searchType = (RelativeLayout) findViewById(R.id.act_search_disorders_rl_searchType);
        rl_fadeMenu = (RelativeLayout) findViewById(R.id.act_search_disorders_rl_fadeMenu);
        ac_searchBySigns = (AppCompatMultiAutoCompleteTextView) findViewById(R.id.act_search_disorders_ac_searchBySigns);

        rl_searchType.setOnClickListener(new View.OnClickListener() {
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
        });

        ac_searchBySigns.setAdapter(new DisorderAutocompleteBySignAdapter(this, new DisorderAutocompleteBySignAdapter.AutocompleteListener() {
            @Override
            public void onStartFiltering() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pb_searchProgress.setVisibility(View.VISIBLE);
                        ac_searchBySigns.setOnTouchListener(null);
                        ac_searchBySigns.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
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
                    mToolbarSearchItem.setVisible(true);
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
        MenuInflater menuInflater = getMenuInflater();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void renderDisordersList(List<Disorder> disorders) {
//        Log.d("TEste", disorders.get(1).getName());
        mSearchResultsAdapter.clear();
        mSearchResultsAdapter.addAll(disorders);
        mSearchResultsAdapter.notifyDataSetChanged();
        lv_searchResults.setVisibility(View.VISIBLE);
        Log.d("TEste", "aqui2");
        float actionBarSize = getResources().getDimension(R.dimen.abc_action_bar_default_height_material);
        ac_searchEditText.setVisibility(View.GONE);
        Log.d("TEste", "aqui3");
        shrink(mToolbar, Math.round(actionBarSize), disorders.size());
        Log.d("TEste", "aqui4");
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
*Inner Class responsável pela criação da Thread que fará a consulta ao BD,
* seus resultados
*/

    private class SearchDiseasesTask extends AsyncTask<String, String, List<Disorder>> {

        @Override
        protected List<Disorder> doInBackground(String... params){
            String userInput = params[0];
            String searchType = params[1];
            DisordersModel disorders = new DisordersModel();
            List<Disorder> result = null;

            try {
                result = disorders.search(userInput, searchType);
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
    }

    class SearchProfileTask extends AsyncTask<String, String, DisorderProfile> {


        @Override
        protected DisorderProfile doInBackground(String... params) {

            Log.d("Estou aqui","aqui");
            String diseaseId = params[0];
            DisorderProfileModel diseaseProfileModel = new DisorderProfileModel();
            DisorderProfile profile = null;

            try {
                profile = diseaseProfileModel.getProfile(diseaseId);
            } catch (Exception e) {

            }

            return profile;
        }

        protected void onPostExecute(DisorderProfile profile) {

            pb_loadingDisorderData.setVisibility(View.INVISIBLE);
            Intent it = DisorderProfileActivity.getIntent(SearchDisordersActivity.this, profile);
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
    }


    /*public void lerDados(){

        db.abrir();
        diseases.clear();

        Cursor cursor = db.retornaTodosDiseases();
        if (cursor.moveToFirst()){
            do {

                Disorder a= new Disorder();


                //Log.d("testando banco1",cursor.getString(cursor.getColumnIndex(BancoDeDados.getKEY_ID())));
                //Log.d("testando banco",cursor.getString(cursor.getColumnIndex(BancoDeDados.getKEY_ORPHANUMBER())));
                a.setDiseaseID (cursor.getInt(cursor.getColumnIndex(BancoDeDados.getKEY_ID())));
                a.setDiseaseName(cursor.getString(cursor.getColumnIndex(BancoDeDados.getKEY_NOME())));
                a.setDiseaseOrphanumber(cursor.getString(cursor.getColumnIndex(BancoDeDados.getKEY_ORPHANUMBER())));
                //a.setDiseaseOrphanumber(cursor.getString(cursor.getColumnIndex(BancoDeDados.getKEY_EXPERTLINK())));
                //a.expertlink = cursor.getString(cursor.getColumnIndex(BancoDeDados.KEY_EXPERTLINK));
                a.setDescricao(cursor.getString(cursor.getColumnIndex(BancoDeDados.getKEY_DESCRICAO())));

                diseases.add(a);
            } while (cursor.moveToNext());
        }

       if (diseases.size() >= 0){
            if (mSearchResultsAdapter == null){

                listAdapter1 = new DisorderAdapterSqlite(this, R.layout.item_disease,
                        diseases) {
                    @Override
                    public void edita(Disorder disease) {
                        //Log.d("nome no esdita",disease.getDisorderName());
                       // TextView t = (TextView) findViewById(R.id.quest);
                        //t.setText("teste");


                        Intent intent = new Intent(SearchDisordersActivity.this, DisorderProfileActivity.class);


                            intent.putExtra("id", String.valueOf(disease.getDisorderID()));


                       // intent.putExtra("txt","Deseja carregar as informações da doença" + disease.getDisorderName() + "?");
                        intent.putExtra("visible","1");
                        startActivity(intent);
                    }

                    @Override
                    public void deleta(Disorder disease) {
                        Intent intent = new Intent(getApplicationContext(), SaveDiseaseActivity.class);


                        intent.putExtra("disease", disease);
                        intent.putExtra("txt","Deseja remover "+ disease.getDisorderName() + " da sua lista de doenças?");
                        //intent.putExtra("visible",2);

                        startActivity(intent);

                      // Intent i = new Intent(SearchDisordersActivity.this , DisorderProfileActivity.class);
                        //Log.d("deleta" , String.valueOf(disease.getDisorderID()));
                        //i.putExtra("diseaseID", String.valueOf(disease.getDisorderID()));
                       //startActivity(i);

                        //Intent intent = new Intent(getApplicationContext(), DisorderInfoFragment.class);
                        //DisorderInfoFragment.setDisorder(disease);
                        //DisorderInfoFragment.setSigns(null);
                       // DisorderInfoFragment.setReferences(null);
                       //][ DisorderInfoFragment.setSynonyms(null);
                        //intent.putExtra("disease", disease);
                        //startActivity(intent);
                        //Log.d("deleta" , String.valueOf(disease.getDisorderID()));
                       // db.abrir();
                        //db.apagaDisease(disease.getDisorderID());
                        //db.fechar();
                        //lerDados();

                    }
                };
                ListView listViewItems = (ListView) findViewById(R.id.listteste);
                //listViewItems.setTextAlignment();
                listViewItems.setAdapter(listAdapter1);
                listViewItems.setOnItemClickListener(new OnItemClickListenerListViewItem());
                listViewItems.setVisibility(View.VISIBLE);

                RelativeLayout frameListView = (RelativeLayout) findViewById(R.id.frame_list_diseases1);
                frameListView.setVisibility(View.VISIBLE);

                ImageView logoBG = (ImageView) findViewById(R.id.logo_bg);
                logoBG.setVisibility(View.INVISIBLE);


            } else {
                listAdapter1.novosDados(diseases);
            }
        }
        db.fechar();
    }*/

    @Override
    public void onBackPressed() {
        if(MenuItemCompat.isActionViewExpanded(mToolbarSearchItem))
            MenuItemCompat.collapseActionView(mToolbarSearchItem);
        else
            super.onBackPressed();
    }

}