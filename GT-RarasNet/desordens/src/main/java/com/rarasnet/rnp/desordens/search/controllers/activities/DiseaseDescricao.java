package com.rarasnet.rnp.desordens.search.controllers.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

import com.rarasnet.rnp.desordens.R;
import com.rarasnet.rnp.shared.models.Disorder;

/**
 * Created by Ronnyery Barbosa on 30/06/2015.
 */
public class DiseaseDescricao extends Activity {
    private Disorder disorder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.disease_resume);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView nome = (TextView) findViewById(R.id.resumeName);
        TextView orphanumber = (TextView) findViewById(R.id.resumeOrphanum);
        TextView expertlink = (TextView) findViewById(R.id.resumeExpertlink);
        TextView discricao = (TextView) findViewById(R.id.disease_description);
        TextView bibliografia = (TextView) findViewById(R.id.disease_bibliografia);

        Intent intent = getIntent();

        disorder = (Disorder) intent.getSerializableExtra("disease");

        nome.setText(disorder.getName());
        orphanumber.setText(disorder.getOrphanumber());
        expertlink.setText(disorder.getExpertlink());

        expertlink.setMovementMethod(LinkMovementMethod.getInstance());
        String text = "<a href='" + disorder.getExpertlink() + "'>" + getString(R.string.consultarorpha) + "</a>";
        expertlink.setText(Html.fromHtml(text));

        discricao.setText(disorder.getDescricao());
        bibliografia.setText(disorder.getBibliografia());


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


}
