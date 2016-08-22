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
import android.widget.Toast;

import com.rarasnet.rnp.desordens.R;
import com.rarasnet.rnp.shared.common.intents.ProfissionaisIntent;
import com.rarasnet.rnp.shared.models.Professional;
import com.rarasnet.rnp.shared.views.adapters.ProfessionalsAdapter;

import java.util.List;

/**
 * Created by Ronnyery Barbosa on 12/08/2015.
 */
public class Professionals extends Activity {


    private static List<Professional> professionals;
    private ProfessionalsAdapter listprofissionais;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.disease_professional);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        Diseasesdadas();

//
       // ListView professionalList = (ListView) findViewById(R.id.professional_list);
       //// listprofissionais = new ProfessionalsAdapter(this, R.layout.item_professional,
                //professionals);
      /////  professionalList.setAdapter(listprofissionais);
//
       /// professionalList.setOnItemClickListener(new OnItemClickListenerListViewItem());


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

    public static void setProfessionals(List<Professional> professionals) {
        Professionals.professionals = professionals;
    }


    public void Diseasesdadas() {
        TextView diseasesName = (TextView) findViewById(R.id.profe_dis_Name);
        TextView orphanumber = (TextView) findViewById(R.id.profe_dis_Orphanum);
        TextView expertlink = (TextView) findViewById(R.id.profe_dis_Expertlink);
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
            String namesign = listprofissionais.getItemProfeName(position);

            String singID = listprofissionais.getItemProfeId(position);


            //Toast.makeText(SearchDisordersActivity.this, ID, Toast.LENGTH_LONG).show();
            //Log.d("center_info sing class", String.valueOf(listsing.getItemId(position)));


            //Diseasesdadas();
            //params.putString("nome", params.getString("nome"));
            //params.putString("orphanumber", params.getString("orphanumber"));
            //params.putString("expertlink", params.getString("expertlink"));
            //params.putString("namesign", namesign);

            Intent o = getIntent();
            Bundle params = o.getExtras();

            try {
                ProfissionaisIntent i = new ProfissionaisIntent(ProfissionaisIntent.ACTION_PROFILE);
                params.putString("id", listprofissionais.getItemProfeId(position));

                i.putExtras(params);
                startActivity(i);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),"Erro na aplicação", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
