package com.rarasnet.rnp.info_projeto;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.rarasnet.rnp.info_projeto.fragment.LegislacaoFragment;
import com.rarasnet.rnp.info_projeto.model.Legislacao;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    private static String TAG = "LOG";
    private Toolbar mToolbar;
    private Toolbar mToolbarBottom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.tb_main);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setTitle("Legislação");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       // mToolbar = (Toolbar) findViewById(R.id.tb_main);
       // mToolbar.setTitle("Main Activity");
       //// mToolbar.setSubtitle("just a subtitle");
       // mToolbar.setLogo(R.drawable.ic_launcher);
       /// setSupportActionBar(mToolbar);
/*
        mToolbarBottom = (Toolbar) findViewById(R.id.inc_tb_bottom);
        mToolbarBottom.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent it = null;

                switch (menuItem.getItemId()) {
                    case R.id.action_facebook:
                        it = new Intent(Intent.ACTION_VIEW);
                        it.setData(Uri.parse("http://www.facebook.com"));
                        break;
                    case R.id.action_youtube:
                        it = new Intent(Intent.ACTION_VIEW);
                        it.setData(Uri.parse("http://www.youtube.com"));
                        break;
                    case R.id.action_google_plus:
                        it = new Intent(Intent.ACTION_VIEW);
                        it.setData(Uri.parse("http://plus.google.com"));
                        break;
                    case R.id.action_linkedin:
                        it = new Intent(Intent.ACTION_VIEW);
                        it.setData(Uri.parse("http://www.linkedin.com"));
                        break;
                    case R.id.action_whatsapp:
                        it = new Intent(Intent.ACTION_VIEW);
                        it.setData(Uri.parse("http://www.whatsapp.com"));
                        break;
                }

                startActivity(it);
                return true;
            }
        });
        mToolbarBottom.inflateMenu(R.menu.menu_bottom);

        mToolbarBottom.findViewById(R.id.iv_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Settings pressed", Toast.LENGTH_SHORT).show();
            }
        });
*/

        // FRAGMENT
            LegislacaoFragment frag = (LegislacaoFragment) getSupportFragmentManager().findFragmentByTag("mainFrag");
            if(frag == null) {
                frag = new LegislacaoFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.rl_fragment_container, frag, "mainFrag");
                ft.commit();
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


/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_second_activity){
            startActivity(new Intent(this, SecondActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

*/

    public List<Legislacao> getSetlegiscao(){
        //String[] models = new String[]{"Gallardo", "Vyron", "Corvette", "Pagani Zonda", "Porsche 911 Carrera", "BMW 720i", "DB77", "Mustang", "Camaro", "CT6"};
        //String[] brands = new String[]{"Lamborghini", " bugatti", "Chevrolet", "Pagani", "Porsche", "BMW", "Aston Martin", "Ford", "Chevrolet", "Cadillac"};
       // int[] photos = new int[]{R.drawable.gallardo, R.drawable.vyron, R.drawable.corvette, R.drawable.paganni_zonda, R.drawable.porsche_911, R.drawable.bmw_720, R.drawable.db77, R.drawable.mustang, R.drawable.camaro, R.drawable.ct6};
        List<Legislacao> listAux = new ArrayList<>();

      

       
        listAux.add(new Legislacao("PORTARIA GM N. 981, DE 21 DE MAIO DE 2014","portaria981.pdf","3"));
        listAux.add(new Legislacao("PORTARIA N. 81, DE 20 DE JANEIRO DE 2009 ","portaria81.pdf","1"));
        listAux.add(new Legislacao("PORTARIA N. 199, DE 30 DE JANEIRO DE 2014","portaria199.pdf","2"));

        /*listAux.add(new Legislacao("Artrite Reativa - doença de Reiter ","Orphanumber: 90053","Cid : não cadastrado","artrite_reativa.pdf","7"));
        listAux.add(new Legislacao("Angioedema Hereditário ","Orphanumber: 91378","Cid : não cadastrado","angioedema.pdf","5"));
        listAux.add(new Legislacao("Aplasia Pura Adquirida Crônica da Série Vermelha ","Orphanumber: 98421","Cid : não cadastrado","aplasia_pura_adquirida_cronica_da_serie_vermelha.pdf","6"));
        listAux.add(new Legislacao("Artitre Reativa - doença de Reiter ","Orphanumber: 29207","Cid : não cadastrado","artrite_reativa.pdf","7"));
        listAux.add(new Legislacao(" Artrite Reumatoide ","Orphanumber: 284130","Cid : não cadastrado","artrite_reumatoide.pdf","8"));
        listAux.add(new Legislacao("Deficiência da Biotinidase ","Orphanumber: 79241","Cid : não cadastrado","deficiencia_da_biotinidase.pdf","9"));
        listAux.add(new Legislacao("Deficiência de Hormônio do Crescimento - Hipopituitarismo ","Orphanumber: 631","Cid : não cadastrado","deficiencia_de_hormonio_do_crescimento.pdf","10"));

        listAux.add(new Legislacao("Dermatomiosite ","Orphanumber: 221","Cid : não cadastrado","dermatomiosite.pdf","11"));

        listAux.add(new Legislacao("Diabete Insípido ","Orphanumber: 178029","Cid : não cadastrado","diabete_insipido.pdf","13"));
        listAux.add(new Legislacao("Distonias Focais ","Orphanumber: 221083","Cid : não cadastrado","distonias_focais.pdf","14"));
        listAux.add(new Legislacao("Doença de Crohn ","Orphanumber: 206","Cid : não cadastrado","doença_de_crohn.pdf","16"));
        listAux.add(new Legislacao("Doença de Gaucher ","Orphanumber: 355","Cid : não cadastrado","doença_de_gaucher.pdf","17"));
        listAux.add(new Legislacao("Doença de Paget - Osteíte deformante ","Orphanumber: 280110","Cid : não cadastrado","doença_de_paget.pdf","18"));
        listAux.add(new Legislacao("Doença de Wilson ","Orphanumber: 905","Cid : não cadastrado",
                "doença_de_wilson.pdf","19"));
        listAux.add(new Legislacao("Esclerose Lateral Amiotrófica ","Orphanumber: 803","Cid : não cadastrado","esclerose_lateral_amiotrofica.pdf","20"));
        listAux.add(new Legislacao("Esclerose Múltipla ","Orphanumber: 802","Cid : não cadastrado","esclerose_multipla.pdf","38"));
        listAux.add(new Legislacao("Espasmo Hemifacial ","Orphanumber: 1866","Cid : não cadastrado","espasmo_hemifacial.pdf","15"));
        listAux.add(new Legislacao("Espondilite ancilosante ","Orphanumber: 825","Cid : não cadastrado","espondilite_ancilosante.pdf","21"));
        listAux.add(new Legislacao("Fenilcetonúria ","Orphanumber: 716","Cid : não cadastrado","fenilcetonuria.pdf","22"));
        listAux.add(new Legislacao("Fibrose Cística - Insuficiência Pancréatica ","Orphanumber: 586","Cid : não cadastrado","fibrose_cistica_insuficiencia_pancreatica.pdf","23"));
        listAux.add(new Legislacao("Fibrose Cística - Manisfetações Pulmonares","Orphanumber: 586","Cid : não cadastrado","fibrose_cistica_manifestacoes_pulmonares.pdf","39"));
        listAux.add(new Legislacao("Hepatite Autoimune ","Orphanumber: 2137","Cid : não cadastrado","hepatite_autoimune.pdf","24"));
        listAux.add(new Legislacao("Hiperplasia Adrenal Congênita ","Orphanumber: 418","Cid : não cadastrado","hiperplasia_adrenal_congenita.pdf","25"));
        listAux.add(new Legislacao("Hipoparatireoidismo ","Orphanumber: 181405","Cid : não cadastrado","hipoparatireoidismo.pdf","26"));
        listAux.add(new Legislacao("Hipotireoidismo congênito ","Orphanumber: 442","Cid : não cadastrado","hipotireoidismo_congenito.pdf","27"));
        listAux.add(new Legislacao("Ictioses Hereditárias ","Orphanumber: 101977","Cid : não cadastrado","ictioses_hereditarias.pdf","28"));
        listAux.add(new Legislacao("Imunodeficiências Primárias com Deficiência de Anticorpos ","Orphanumber: 101977","Cid : não cadastrado","imunodeficiencias_primarias_com_deficiencia_de_anticorpos.pdf","29"));
        listAux.add(new Legislacao("Insuficiência Adrenal Primária(Doença de Addison) ","Orphanumber: 85138","Cid : não cadastrado",
                "insuficiencia_adrenal_primaria.pdf","30"));
        listAux.add(new Legislacao("Lúpus Eritematoso Sistêmico ","Orphanumber: 536","Cid : não cadastrado","lupus_eritematoso_sistemico.pdf","31"));
        listAux.add(new Legislacao("Miastenia Gavis","Orphanumber: 589","Cid : não cadastrado","miastenia_gavis.pdf","32"));
        listAux.add(new Legislacao("Mielodisplasia","Orphanumber: 314399","Cid : não cadastrado","mielodisplasia.pdf","3"));
        listAux.add(new Legislacao("Neutropenias Constitucionais","Orphanumber: 90053","Cid : não cadastrado","neutropenias_constitucionais.pdf","4"));
        listAux.add(new Legislacao("Osteogênese Imperfeita","Orphanumber: 589","Cid : não cadastrado","osteogenese_imperfeita.pdf","33"));

        listAux.add(new Legislacao("Polimiosite","Orphanumber: 732","Cid : não cadastrado","polimiosite.pdf","12"));
        listAux.add(new Legislacao("Síndrome de Guillain-Barré","Orphanumber: 2103","Cid : não cadastrado","sindrome_de_guillain-barre.pdf","35"));
        listAux.add(new Legislacao("Síndrome Nefrótica Primária em Crianças e Adolesce...","Orphanumber: 2103","Cid : não cadastrado","sindrome_nefrotica_primaria_em_crianças_e_adolescentes.pdf","37"));
        listAux.add(new Legislacao("Sínndrome de Turner","Orphanumber: 881","Cid : não cadastrado","sindrome_de_turner.pdf","36"));
        listAux.add(new Legislacao("Trombocitopênica Idiopática","Orphanumber: 3002","Cid : não cadastrado","trombocitopenica_idiopatica.pdf","34"));
        */return(listAux);
    }


}
