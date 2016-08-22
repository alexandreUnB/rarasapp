package com.rarasnet.rnp.desordens.search.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import com.rarasnet.rnp.desordens.R;
import com.rarasnet.rnp.shared.models.Disorder;

/**
 * Created by Ronnyery Barbosa on 18/06/2015.
 */
public abstract class DisorderAdapterSqlite extends ArrayAdapter<Disorder> {
    private Context mContext;
    private int layoutResourceId;
    private List<Disorder> data = null;
    private ImageButton button;

    public DisorderAdapterSqlite(Context mContext, int layoutResourceId, List<Disorder> data) {
        super(mContext, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;

    }

    public void novosDados(List<Disorder> diseases) {
        this.data = diseases;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Disorder getItem(int position) {
        // TODO Auto-generated method stub
        return data.get(position);
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            //infla o layout
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
        }

        //final Disorder diseases = data.get(position);

        // get the TextView and then set the text (item name) and tag (item ID) values
        TextView resultDiseaseName = (TextView) convertView.findViewById(R.id.txtNome);
        resultDiseaseName.setText(data.get(position).getName());

        resultDiseaseName.setTag(data.get(position).getId());

        ((ImageButton) (convertView.findViewById(R.id.btnEditar))).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //Log.d("Quando edita", data.get(position).getDisorderName());
                edita(data.get(position));
            }
        });

        ((ImageButton) (convertView.findViewById(R.id.btnExcluir))).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                deleta(data.get(position));
            }
        });


        // button = (ImageButton) convertView.findViewById(R.id.list_image);

        /*TextView resultDiseaseOrphanumber = (TextView) convertView.findViewById(R.id.resultOrphanumber);
        resultDiseaseOrphanumber.setText(diseases.getOrphanumber());
        resultDiseaseOrphanumber.setTag(diseases.getOrphanumber());*/
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public abstract void edita(Disorder disease);

    public abstract void deleta(Disorder disease);
}


