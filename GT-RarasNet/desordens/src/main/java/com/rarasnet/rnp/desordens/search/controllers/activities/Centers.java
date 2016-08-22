package com.rarasnet.rnp.desordens.search.controllers.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.rarasnet.rnp.desordens.R;
import com.rarasnet.rnp.shared.common.intents.CentrosIntent;
import com.rarasnet.rnp.shared.views.adapters.CentersAdapter;
import com.rarasnet.rnp.shared.views.adapters.DadosNacionaisAdapter;
import com.rarasnet.rnp.shared.models.Center;
import com.rarasnet.rnp.shared.models.DadosNacionais;

import java.util.List;

/**
 * Created by Ronnyery Barbosa on 20/08/2015.
 */
public class Centers extends Activity {

    private static List<Center> centers;
    private static List<DadosNacionais> dados;
    private CentersAdapter listcenters;
    private DadosNacionaisAdapter listdados;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.disease_center);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        Diseasesdadas();


        ListView centerlList = (ListView) findViewById(R.id.profe_perfil_listdisease);
        listcenters = new CentersAdapter(this, R.layout.item_center,
                centers);
        centerlList.setAdapter(listcenters);

        centerlList.setOnItemClickListener(new OnItemClickListenerListViewItem());

        /*ListView testeList = (ListView) findViewById(R.id.profe_perfil_listdisease);
        listdados = new DadosAdapter(this, R.layout.item_dados,
                dados);
        testeList.setAdapter(listcenters);

        testeList.setOnItemClickListener(new OnItemClickListenerListViewItem());*/


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

    public static void setCenters(List<Center> centers) {
        Centers.centers = centers;
    }

    public static void setDados(List<DadosNacionais> dados) {
        Centers.dados = dados;
    }

    public void Diseasesdadas() {
        TextView diseasesName = (TextView) findViewById(R.id.center_dis_Name);
        TextView orphanumber = (TextView) findViewById(R.id.center_dis_Orphanum);
        TextView expertlink = (TextView) findViewById(R.id.center_dis_Expertlink);
        Intent intent = getIntent();
        if (intent != null) {
            Bundle params = intent.getExtras();
            if (params != null) {

                diseasesName.setText(params.getString("nome"));
                orphanumber.setText(params.getString("orphanumber"));
                expertlink.setText(params.getString("expertlink"));
                expertlink.setClickable(true);
                expertlink.setMovementMethod(LinkMovementMethod.getInstance());
                String text = "<a href='" + params.getString("expertlink") + "'> Consultar OrphaNet </a>";
                expertlink.setText(Html.fromHtml(text));


            }

        }

    }

    private class OnItemClickListenerListViewItem implements AdapterView.OnItemClickListener {


        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d("testesss", "dsdsd");
            String namesign = listcenters.getItemCenterName(position);

            String singID = listcenters.getItemCenterId(position);


            //Toast.makeText(SearchDisordersActivity.this, ID, Toast.LENGTH_LONG).show();
            //Log.d("center_info sing class", String.valueOf(listsing.getItemId(position)));


            //Diseasesdadas();
            Intent o = getIntent();

            CentrosIntent i = new CentrosIntent(CentrosIntent.ACTION_PROFILE);
            Bundle params = o.getExtras();

            params.putString("nome", params.getString("nome"));
            params.putString("orphanumber", params.getString("orphanumber"));
            params.putString("expertlink", params.getString("expertlink"));
            params.putString("namesign", namesign);

            Log.d("Centro Id", listcenters.getItemCenterId(position));
            params.putString("id", listcenters.getItemCenterId(position));

            i.putExtras(params);
            startActivity(i);
        }
    }
}
