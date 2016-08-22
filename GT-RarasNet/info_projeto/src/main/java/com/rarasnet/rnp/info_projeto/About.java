package com.rarasnet.rnp.info_projeto;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;

/**
 * Created by Farina on 5/4/2015.
 */
public class About extends AppCompatActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_rarasnet);
        //ActionBar actionBar = getActionBar();
        Toolbar mToolbar = (Toolbar) findViewById(R.id.act_about);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setTitle("Quem Somos");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //actionBar.setDisplayHomeAsUpEnabled(true);

        String htmlText = "<html><body style=\"text-align:justify\"> %s </body></Html>";
        String myData = getString(R.string.teste);
        WebView webView = (WebView) findViewById(R.id.webView1);
        webView.setBackgroundColor(Color.TRANSPARENT);
       // webView.loadData(String.format(htmlText, myData), "text/html", "charset = utf-8");
        webView.loadDataWithBaseURL(null, String.format(htmlText, myData), "text/html", "UTF-8", null);



        String htmlText_pessoas = "<html><body style=\"text-align:justify\"> %s </body></Html>";
        String myData_pessoas = getString(R.string.membros_projeto);
        WebView webView2 = (WebView) findViewById(R.id.webView2);
        webView2.setBackgroundColor(Color.TRANSPARENT);
        // webView.loadData(String.format(htmlText, myData), "text/html", "charset = utf-8");
        webView2.loadDataWithBaseURL(null, String.format(htmlText_pessoas, myData_pessoas), "text/html", "UTF-8", null);
//        setLinkButtonListener();
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

    /*private void setLinkButtonListener() {
        final ImageButton linkUnb = (ImageButton) findViewById(R.id.link_unb);
        ImageButton linkRnp = (ImageButton) findViewById(R.id.link_rnp);
        ImageButton linkOrphanet = (ImageButton) findViewById(R.id.link_orphanet);
        ImageButton linkTelesaude = (ImageButton) findViewById(R.id.link_telesaude);
        ImageButton linkSgtes = (ImageButton) findViewById(R.id.link_sgtes);
        ImageButton linkNesp = (ImageButton) findViewById(R.id.link_nesp);


        linkUnb.setOnClickListener(this);
        linkRnp.setOnClickListener(this);
        linkOrphanet.setOnClickListener(this);
        linkNesp.setOnClickListener(this);
        linkSgtes.setOnClickListener(this);
        linkTelesaude.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        Uri paginaWeb;
        Intent webPagina;

        int viewId = view.getId();

        if (viewId == R.id.link_unb) {
            paginaWeb = Uri.parse("http://www.unb.br");
            webPagina = new Intent(Intent.ACTION_VIEW, paginaWeb);
            startActivity(webPagina);
        } else if (viewId == R.id.link_rnp) {
            paginaWeb = Uri.parse("http://www.rnp.br");
            webPagina = new Intent(Intent.ACTION_VIEW, paginaWeb);
            startActivity(webPagina);
        } else if (viewId == R.id.link_nesp) {
            paginaWeb = Uri.parse("http://www.nesp.unb.br");
            webPagina = new Intent(Intent.ACTION_VIEW, paginaWeb);
            startActivity(webPagina);
        } else if (viewId == R.id.link_orphanet) {
            paginaWeb = Uri.parse("http://www.orpha.net");
            webPagina = new Intent(Intent.ACTION_VIEW, paginaWeb);
            startActivity(webPagina);
        } else if (viewId == R.id.link_sgtes) {
            paginaWeb = Uri.parse("http://portalsaude.saude.gov.br/");
            webPagina = new Intent(Intent.ACTION_VIEW, paginaWeb);
            startActivity(webPagina);
        } else if (viewId == R.id.link_telesaude) {
            paginaWeb = Uri.parse("http://portalsaude.saude.gov.br/");
            webPagina = new Intent(Intent.ACTION_VIEW, paginaWeb);
            startActivity(webPagina);
        }


    }*/








}
