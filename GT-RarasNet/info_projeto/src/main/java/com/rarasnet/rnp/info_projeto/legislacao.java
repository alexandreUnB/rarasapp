package com.rarasnet.rnp.info_projeto;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;

/**
 * Created by Ronnyery Barbosa on 25/02/2016.
 */
public class legislacao extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legislacao);
        //ActionBar actionBar = getActionBar();
        Toolbar mToolbar = (Toolbar) findViewById(R.id.act_lei);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setTitle("Legislação");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //actionBar.setDisplayHomeAsUpEnabled(true);*/

        String htmlText = "<html><body style=\"text-align:justify\"> %s </body></Html>";
        String myData = getString(R.string.lei_1);
        WebView webView = (WebView) findViewById(R.id.webView1);
        webView.setBackgroundColor(Color.TRANSPARENT);
        // webView.loadData(String.format(htmlText, myData), "text/html", "charset = utf-8");
        webView.loadDataWithBaseURL(null, String.format(htmlText, myData), "text/html", "UTF-8", null);



        String htmlText_pessoas = "<html><body style=\"text-align:justify\"> %s </body></Html>";
        String myData_pessoas = getString(R.string.lei2);
        WebView webView2 = (WebView) findViewById(R.id.webView2);
        webView2.setBackgroundColor(Color.TRANSPARENT);
        // webView.loadData(String.format(htmlText, myData), "text/html", "charset = utf-8");
        webView2.loadDataWithBaseURL(null, String.format(htmlText_pessoas, myData_pessoas), "text/html", "UTF-8", null);




        String htmlText_pessoas3 = "<html><body style=\"text-align:justify\"> %s </body></Html>";
        String myData_pessoas3 = getString(R.string.lei3);
        WebView webView3 = (WebView) findViewById(R.id.webView3);
        webView3.setBackgroundColor(Color.TRANSPARENT);
        // webView.loadData(String.format(htmlText3, myData3), "text/html", "charset = utf-8");
        webView3.loadDataWithBaseURL(null, String.format(htmlText_pessoas3, myData_pessoas3), "text/html", "UTF-8", null);
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
}
