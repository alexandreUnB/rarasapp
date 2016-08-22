package com.rarasnet.rnp.info_projeto;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ronnyerybarbosa.library.MaterialDialog;

/**
 * Created by Farina on 5/4/2015.
 */
public class Contact extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private MenuItem mToolbarSearchItem;
    MaterialDialog mMaterialDiaolg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact);

        Toolbar toolbar = (Toolbar) findViewById(R.id.act_menu_tb_mainToolbar);


        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Fale Conosco");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setShowHideAnimationEnabled(true);
        setSendMailButtonListener();
        setHyperlinks();



       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeAsUpIndicator(false);

        //toolbar.inflateMenu(R.id.home);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.d("Aqui", "aqui");
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }





    private void setSendMailButtonListener() {
        Button sendMail = (Button) findViewById(R.id.sendMail);
        sendMail.setOnClickListener(this);
    }

    private void setHyperlinks() {

        CardView siteLink = (CardView) findViewById(R.id.act_web_Card);
        siteLink.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                init(v);
                show("Rede Raras", "Deseja abrir site do Observatorio Rede Raras", "rederaras");

                return false;
            }
        });
        CardView siteLink1 = (CardView) findViewById(R.id.act_fanpage_Card);
        siteLink1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                init(v);
                show("Fan Page Rede Raras", "Deseja abrir a Fan Page no Facebook", "fanpage");

                return false;
            }
        });

        CardView siteLink2 = (CardView) findViewById(R.id.act_database_Card);
        siteLink2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                init(v);
                show("WebService Rede Raras", "Deseja abrir pagina do WebService", "database");

                return false;
            }
        });
       /* siteLink.setClickable(true);
        siteLink.setMovementMethod(LinkMovementMethod.getInstance());
        String text1 = "<a href='http://rederaras.unb.br/'> Site do Projeto </a>";
        siteLink.setText(Html.fromHtml(text1));

        TextView webSlink = (TextView) findViewById(R.id.link2);
        webSlink.setClickable(true);
        webSlink.setMovementMethod(LinkMovementMethod.getInstance());
        String text2 = "<a href='http://rederaras.unb.br/webservice/'> Webservice </a>";
        webSlink.setText(Html.fromHtml(text2));

        TextView groupFbLink = (TextView) findViewById(R.id.link3);
        groupFbLink.setClickable(true);
        groupFbLink.setMovementMethod(LinkMovementMethod.getInstance());
        String text3 = "<a href='https://www.facebook.com/groups/observatorioderaras/'> " +
                "Grupo Rede Raras do Facebook </a>";
        groupFbLink.setText(Html.fromHtml(text3));*/


    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"contato@rederaras.org"});
        i.putExtra(Intent.EXTRA_CC, new String[]{"ronnyerybarbosa@hotmail.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Rarasnet");
        i.putExtra(Intent.EXTRA_TEXT, "Mensagem");
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(Contact.this, "Não existem provedores de e-mail instalados no celular"
                    , Toast.LENGTH_SHORT).show();
        }
    }


    public void init(View v) {
        mMaterialDiaolg= new MaterialDialog(this);

        // Toast.makeText(getActivity(), "Initializes successfully.",
        //   Toast.LENGTH_SHORT).show();
    }


    public void show(String Title,String Mensagem, final String Complemeto) {
        Log.d("aqui", "testando");
        if (mMaterialDiaolg != null) {

            mMaterialDiaolg.setTitle(Title).setMessage(Mensagem + " ?")
                    .setPositiveButton("SIM", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mMaterialDiaolg.dismiss();
                            Log.d("aqui", "testando");
                            Intent i = new Intent(Intent.ACTION_VIEW);

                            if (Complemeto.equals("rederaras")) {

                                i.setData(Uri.parse("http://rederaras.org/"));
                                startActivity(i);



                            }else if(Complemeto.equals("fanpage")){
                                i.setData(Uri.parse("https://www.facebook.com/groups/observatorioderaras/"));
                                startActivity(i);


                            }else if(Complemeto.equals("database")) {

                                i.setData(Uri.parse("http://rederaras.org/webservice/"));
                                startActivity(i);


                            }else

                            {
                                //consultaOrphanet();
                            }
                            // Toast.makeText(getActivity(), "SIM", Toast.LENGTH_LONG).show();
                        }
                    }).setNegativeButton("NÃO", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMaterialDiaolg.dismiss();
                    // Toast.makeText(getActivity(), "Cancel", Toast.LENGTH_LONG).show();
                }
            }).setCanceledOnTouchOutside(false)

                    .setOnDismissListener(

                            new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    // Toast.makeText(getActivity(), "",
                                    // Toast.LENGTH_SHORT).show();
                                }
                            }).show();

        } else {
            Toast.makeText(this, "You should init firstly!",
                    Toast.LENGTH_SHORT).show();
        }

        Log.d("aqui", "testando2");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            finish();
        }

        return true;
    }


}