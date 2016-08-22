package com.rarasnet.rnp.profissionais.controllers.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.rarasnet.rnp.profissionais.R;


/**
 * Created by Ronnyery Barbosa on 17/08/2015.
 *
 *  Activity responsável pelo envio de e-mails para os profissionais cadastrados
 *
 */
public class SendMail extends Activity implements View.OnClickListener {


    private TextView toEmail;
    private EditText emailSubject = null;
    private EditText emailBody = null;

    private Button enviar;
    private Button limpar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_mail);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        toEmail = (TextView) findViewById(R.id.toEmail);
        emailSubject = (EditText) findViewById(R.id.subject);
        emailBody = (EditText) findViewById(R.id.emailBody);

        enviar = (Button) findViewById(R.id.enviar);
        limpar = (Button) findViewById(R.id.limpar);


        enviar.setOnClickListener(this);
        limpar.setOnClickListener(this);

        Intent intent = getIntent();

        if (intent != null) {
            Bundle params = intent.getExtras();
            if (params != null) {

                // diseaseName.setText(params.getString("nome"));
                // signname.setText(params.getString("namesign"));
                Log.d("profe test class info", params.getString("email"));
                toEmail.setText(params.getString("email"));
                // toEmail.setHint(params.getString("email"));


                /*diseaseOrphanumber.setText(params.getString("orphanumber"));
                diseaseExpertlink.setText(params.getString("expertlink"));
                diseaseExpertlink.setClickable(true);
                diseaseExpertlink.setMovementMethod(LinkMovementMethod.getInstance());
                String text = "<a href='" + params.getString("expertlink") + "'> Consultar OrphaNet </a>";
                diseaseExpertlink.setText(Html.fromHtml(text));*/


            }

        }


    }


    @Override
    public void onClick(View view) {
        //Intent intent;
        int viewId = view.getId();

        if (viewId == R.id.enviar) {
            Log.d("class", "estrpu");

            String to = toEmail.getText().toString();
            String subject = emailSubject.getText().toString();
            String message = emailBody.getText().toString();

            Intent email = new Intent(Intent.ACTION_SEND);
            email.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
            email.putExtra(Intent.EXTRA_SUBJECT, subject);
            email.putExtra(Intent.EXTRA_TEXT, message);

            // need this to prompts email client only
            email.setType("message/rfc822");

            startActivity(Intent.createChooser(email, "Choose an Email client"));
        } else if(viewId == R.id.limpar) {
            toEmail.setText("");
            emailBody.setText("");
            emailSubject.setText("");

            // startActivity( disorderInfoFragment.setProfessionalList());
        }

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
