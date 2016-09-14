package com.rarasnet.rnp.shared.settings;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.rarasnet.rnp.shared.R;
import com.rarasnet.rnp.shared.application.RarasNet;
import com.rarasnet.rnp.shared.network.responses.GenericResponse;
import com.rarasnet.rnp.shared.usuario.controllers.Profile;
import com.rarasnet.rnp.shared.usuario.controllers.network.UserAdapter;
import com.rarasnet.rnp.shared.util.RarasNetPreferenceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucas on 14/09/16.
 */
public class SettingsActivity extends AppCompatActivity {
    private Button baseButton;
    private EditText baseURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.act_user_tb_mainToolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setTitle("Configurações");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        baseURL = (EditText) findViewById(R.id.base_text);
        baseURL.setHint(RarasNet.urlPrefix);
        // butao para mudar base
        baseButton = (Button) findViewById(R.id.base_button);
        baseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //  changes base url
                RarasNet.urlPrefix = baseURL.getText().toString();
            }
        });



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



    private void hideKeyboard() {

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
