package com.rarasnet.rnp.desordens.search.controllers.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import com.rarasnet.rnp.desordens.R;
import com.rarasnet.rnp.desordens.search.adapters.ReferencesAdapter;
import com.rarasnet.rnp.desordens.search.adapters.SynonymsAdapter;
import com.rarasnet.rnp.shared.models.Disorder;
import com.rarasnet.rnp.desordens.search.models.Reference;

/**
 * Created by Ronnyery Barbosa on 10/06/2015.
 */
public class References extends Activity {

    private static List<Reference> references;
    private SynonymsAdapter synonymslist;
    private static Disorder disorder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reference_list);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        Diseasesdadas();

        ListView referencesList = (ListView) findViewById(R.id.reference_list);
        ReferencesAdapter referencesAdapter = new ReferencesAdapter(this, R.layout.list_references_row_item,
                references);
        referencesList.setAdapter(referencesAdapter);


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

    public static void setReferences(List<Reference> references) {

        References.references = references;
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
