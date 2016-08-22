package com.rarasnet.rnp.documentos.controllers.activities;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.rarasnet.rnp.documentos.R;
import com.rarasnet.rnp.documentos.controllers.network.responses.ProtocolDataResponse;
import com.rarasnet.rnp.documentos.controllers.tasks.SearchProtocolsTask;
import com.rarasnet.rnp.documentos.views.ProtocolsAdapter;
import com.rarasnet.rnp.shared.common.intents.DesordensIntent;
import com.rarasnet.rnp.shared.util.managers.PDFManager;
import com.rarasnet.rnp.shared.util.managers.ProtocolsManager;

import java.util.ArrayList;
import java.util.Arrays;


public class SearchProtocolsActivity extends AppCompatActivity {


    private SearchProtocolsTask.SearchProtocolsCallback searchProtocolsCallback
    = new SearchProtocolsTask.SearchProtocolsCallback() {
        @Override
        public void onSearchProtocolsTaskCallback(ProtocolDataResponse[] response) {
            pDialog.dismiss();
            if (response == null) {
                Toast.makeText(SearchProtocolsActivity.this, "Erro de conexão com o servidor", Toast.LENGTH_LONG).show();
            } else {
                renderProtocolsList(new ArrayList<>(Arrays.asList(response)));
            }
        }
    };

    private ProtocolsAdapter.OnItemListViewElementListener onItemListViewElementListener
    = new ProtocolsAdapter.OnItemListViewElementListener() {
        @Override
        public void onItemListViewElementClick(int viewId, ProtocolDataResponse pData) {
            String protocolName = pData.getProtocolName();
            String protocolId = pData.getId();

            if(viewId == R.id.result_protocols_DiseaseProfile) {
                DesordensIntent i = new DesordensIntent(DesordensIntent.ACTION_PROFILE);
                i.putExtra("id", pData.getDisorderId());
                startActivity(i);
            } else if(viewId == R.id.pdfIcon || viewId == R.id.result_protocols_DiseaseName) {
                if(PDFManager.hasFileInLocal(protocolName)) {
                    openPDF(Uri.parse(pManager.getLocalPDFPath(protocolName)));
                } else {
                    downloadedPdfName = protocolName;
                    downloadedPdfId = protocolId;
                    pManager.downloadPDF(protocolName, protocolId);
                }
            }
        }
    };

    private SearchView searchView;
    private ProtocolsManager pManager;
    private Context context;
    private ProtocolsAdapter mAdapter;
    private EditText searchField;
    private ListView protocolsList;
    private Button searchButton;
    private ProgressDialog pDialog;

    private static final String TYPE_NAME = "name";
    private static final String TYPE_ORPHANUMBER = "orphanumber";
    private static final String TYPE_ICD = "icd";

    //Numeric Pattern
    private static final String orphanumberPattern = "[0-9]+";
    private static final String icdPattern = "[A-Z][0-9][0-9].[0-9]";

    private String downloadedPdfName;
    private String downloadedPdfId;
    private PDFReceiver pdfBroadcastReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getActionBar();

        if(actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.search_protocols);
        context = getApplicationContext();
        pManager = new ProtocolsManager(context);
        protocolsList = (ListView) findViewById(R.id.list_protocols);
    }

    @Override
    protected void onStart() {
        super.onStart();
        pdfBroadcastReceiver = new PDFReceiver();
        registerReceiver(pdfBroadcastReceiver, new IntentFilter(
                DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(pdfBroadcastReceiver);
    }

    private void askToOpenPDFThroughGoogleDrive(final String pdfUrl ) {
        new AlertDialog.Builder(this)
                .setTitle("Aviso")
                .setMessage("Nennhum leitor de Pdfs foi encontrado neste aparelho. Deseja " +
                        "abrir o arquivo no Google Drive?")
                .setNegativeButton("Não", null)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        openPDFThroughGDrive(pdfUrl);
                    }
                })
                .show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search_protocols, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        View.OnClickListener handler = new View.OnClickListener() {
            public void onClick(View v) {
                SearchProtocolsActivity.this.registerForContextMenu(v);
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
                if(inputIsValid(query, getSearchType(query))) {
                    pDialog = new ProgressDialog(SearchProtocolsActivity.this);
                    pDialog.setMessage("Pesquisando Protocolos");
                    pDialog.setIndeterminate(false);
                    pDialog.setCancelable(false);
                    pDialog.show();
                    new SearchProtocolsTask(searchProtocolsCallback).execute(query, getSearchType(query));
                    searchView.setFocusable(false);
                    getWindow().setSoftInputMode(
                            WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                }
                return false;
            }
        });
        searchView.setOnSearchClickListener(handler);
        // Configure the search info and add any event listeners
        // ...

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openPDFThroughGDrive(final String pdfUrl) {
        Intent i = new Intent( Intent.ACTION_VIEW );
        i.setDataAndType(Uri.parse(pManager.getGoogleDriveReaderPrefix() + pdfUrl),
                pManager.getHtmlMimeType());
        startActivity(i);
    }

    private void openPDF(Uri localUri ) {
        Intent i = new Intent(Intent.ACTION_VIEW );
        i.setDataAndType(localUri, pManager.getPdfMimeType());
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    private class PDFReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if(action.equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                Toast.makeText(getApplicationContext(), "Download Realizado com sucesso!",
                        Toast.LENGTH_SHORT).show();
                if (pManager.hasPDFOpener(context)) {
                    String dp = pManager.getLocalPDFPath(downloadedPdfName);
                    openPDF(Uri.parse(dp));
                } else {
                    askToOpenPDFThroughGoogleDrive(pManager.buildPDFLink(downloadedPdfName, downloadedPdfId));
                }

            }
        }
    }

    private class OnItemClickListenerListViewItem implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ProtocolDataResponse protocolData = mAdapter.getItem(position);
            String protocolName = protocolData.getProtocolName();

            /*Intent i = new Intent(SearchProtocolsActivity.this , DiseaseProfile.class);
            i.putExtra("diseaseID", protocolData.getDisorderId());
            startActivity(i);*/
            if (view.getId() == R.id.result_protocols_DiseaseProfile) {
                DesordensIntent i = new DesordensIntent(DesordensIntent.ACTION_PROFILE);
                i.putExtra("id", protocolData.getDisorderId());
                startActivity(i);
            } else {
                if(PDFManager.hasFileInLocal(protocolName)) {
                    openPDF(Uri.parse(pManager.getLocalPDFPath(protocolName)));
                } else {
                    downloadedPdfName = protocolData.getProtocolName();
                    downloadedPdfId = protocolData.getId();
                    pManager.downloadPDF(protocolData.getProtocolName(), protocolData.getId());
                }
            }
        }
    }


    private void renderProtocolsList(ArrayList<ProtocolDataResponse> protocols) {
            if(mAdapter != null) {
                mAdapter.update(protocols);
                //mAdapter.clear();
                //mAdapter.addAll(protocols);
                //mAdapter.notifyDataSetChanged();
            } else {
                mAdapter = new ProtocolsAdapter(this,R.layout.list_protocols_row_item, protocols,
                        onItemListViewElementListener);
                protocolsList.setAdapter(mAdapter);
                //protocolsList.setOnItemClickListener(new OnItemClickListenerListViewItem());
                protocolsList.setVisibility(View.VISIBLE);
            }
    }


    private String getSearchType(String query) {
        if (query.matches(orphanumberPattern)) {
            //Toast.makeText(Diseases.this, "TIPO ORPHA" ,Toast.LENGTH_SHORT).show();
            return TYPE_ORPHANUMBER;
        } else if (query.matches(icdPattern)) {
            //Toast.makeText(Diseases.this, "TIPO ICD" ,Toast.LENGTH_SHORT).show();
            return TYPE_ICD;
        } else {
            //Toast.makeText(Diseases.this, "TIPO NAME" ,Toast.LENGTH_SHORT).show();
            return TYPE_NAME;
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
            Toast.makeText(SearchProtocolsActivity.this, message
                    , Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }
}
