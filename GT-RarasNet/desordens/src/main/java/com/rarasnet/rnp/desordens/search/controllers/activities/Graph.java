package com.rarasnet.rnp.desordens.search.controllers.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rarasnet.rnp.desordens.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.rarasnet.rnp.desordens.search.models.Mortalidade;


/**
 * Created by Ronnyery Barbosa on 11/08/2015.
 */
public class Graph extends Activity {

    TextView disease;
    private static Mortalidade mortalidade = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        disease = (TextView) findViewById(R.id.graph_title);
        //GraphView graph = (GraphView) findViewById(R.id.gg);
        GraphView graphe = new GraphView(this);


        Intent intent = getIntent();

        if (intent != null) {
            Bundle params = intent.getExtras();
            if (params != null) {

                mortalidade = (Mortalidade) params.getSerializable("mortalidade");
                Log.d("mor",mortalidade.getAno2004());

                disease.setText(getString(R.string.doenca) + " " + params.getString("nome"));


            }

        }


        BarGraphSeries<DataPoint> series1 = new BarGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(2002, Double.parseDouble(mortalidade.getAno2002())),
                new DataPoint(2003, Double.parseDouble(mortalidade.getAno2003())),
                new DataPoint(2004, Double.parseDouble(mortalidade.getAno2004())),
                new DataPoint(2005, Double.parseDouble(mortalidade.getAno2005())),
                new DataPoint(2006, Double.parseDouble(mortalidade.getAno2006())),
                new DataPoint(2007, Double.parseDouble(mortalidade.getAno2007())),
                new DataPoint(2008, Double.parseDouble(mortalidade.getAno2008())),
                new DataPoint(2009, Double.parseDouble(mortalidade.getAno2009())),
                new DataPoint(2010, Double.parseDouble(mortalidade.getAno2010())),
                new DataPoint(2011, Double.parseDouble(mortalidade.getAno2011())),
                new DataPoint(2012, Double.parseDouble(mortalidade.getAno2012())),


        });


        graphe.addSeries(series1);

// styling


        series1.setSpacing(60);

// draw values on top
        series1.setDrawValuesOnTop(true);
        series1.setColor(Color.RED);
        series1.setValuesOnTopColor(Color.BLUE);
//series.setValuesOnTopSize(50);

        //graphView.setViewPort(2, 40);
        //  graphView.setManualYAxisBounds(0, 10);
        // graphView.setScrollable(true);


        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graphe);
        staticLabelsFormatter.setHorizontalLabels(new String[]{"2002", " 2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012"});
        //staticLabelsFormatter.setVerticalLabels(new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8"});
        graphe.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);


        //series2.setTitle("bar");
        graphe.getLegendRenderer().setVisible(true);
        graphe.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        LinearLayout ll = (LinearLayout) findViewById(R.id.graph);
        ll.addView(graphe);


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
