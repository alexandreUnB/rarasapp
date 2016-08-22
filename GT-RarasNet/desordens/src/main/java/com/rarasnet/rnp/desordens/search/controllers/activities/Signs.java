package com.rarasnet.rnp.desordens.search.controllers.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import com.rarasnet.rnp.desordens.R;
import com.rarasnet.rnp.desordens.search.adapters.SignsAdapter;
import com.rarasnet.rnp.desordens.search.models.Sign;

/**
 * Created by Ronnyery Barbosa on 28/05/2015.
 */
public class Signs extends Activity {
    private static List<Sign> signs;
    private SignsAdapter listsing;

    String singID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_signs);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Diseasesdadas();

        ListView signsList = (ListView) findViewById(R.id.list_signs7);
        listsing = new SignsAdapter(this, R.layout.list_signs_row_item,
                signs);
        signsList.setAdapter(listsing);
        signsList.setOnItemClickListener(new OnItemClickListenerListViewItem());


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

    public static void setSigns2(List<Sign> signs) {

        Signs.signs = signs;
    }

    private class OnItemClickListenerListViewItem implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String namesign = listsing.getItemname(position);

            String singID = listsing.getItemSingId(position);


            //Toast.makeText(SearchDisordersActivity.this, ID, Toast.LENGTH_LONG).show();
            //Log.d("center_info sing class", String.valueOf(listsing.getItemId(position)));


            //Diseasesdadas();
            Intent o = getIntent();

            Intent i = new Intent(Signs.this, SignsInfo.class);
            Bundle params = o.getExtras();

            params.putString("nome", params.getString("nome"));
            params.putString("orphanumber", params.getString("orphanumber"));
            params.putString("expertlink", params.getString("expertlink"));
            params.putString("namesign", namesign);
            params.putString("signsID", listsing.getItemSingId(position));

            i.putExtras(params);
            startActivity(i);
        }
    }

    public void Diseasesdadas() {
        TextView diseasesName = (TextView) findViewById(R.id.diseaseName);
        TextView orphanumber = (TextView) findViewById(R.id.diseaseOrphanum);
        TextView expertlink = (TextView) findViewById(R.id.diseaseExpertlink);
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


}
