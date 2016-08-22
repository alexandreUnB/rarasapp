package com.rarasnet.rnp.shared.views.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rarasnet.rnp.shared.R;
import com.rarasnet.rnp.shared.models.DadosNacionais;

import java.util.List;

/**
 * Created by Ronnyery Barbosa on 18/08/2015.
 *
 *  Adapter para uma lista de Dados Nacionais
 */
public class DadosNacionaisAdapter extends ArrayAdapter {

    private Context mContext;
    private int layoutResourceId;
    private List<DadosNacionais> data = null;

    public DadosNacionaisAdapter(Context mContext, int layoutResourceId, List<DadosNacionais> data) {
        super(mContext, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            //infla o layout
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
        }

        DadosNacionais sign = data.get(position);

        // get the TextView and then set the text (item name) and tag (item ID) values
        TextView professionalName = (TextView) convertView.findViewById(R.id.resultDiseaseName);
        //TextView professionalCidade = (TextView) convertView.findViewById(R.id.profissional_setcidade);
        //TextView professionalEstado = (TextView) convertView.findViewById(R.id.profissional_setestado);


        professionalName.setText(sign.getDoenca());
        //professionalCidade.setText(sign.getCidade());
        //professionalEstado.setText(sign.getEstado());


        //resultDiseaseName.setTag(sign.getId());

        /*TextView resultDiseaseOrphanumber = (TextView) convertView.findViewById(R.id.resultOrphanumber);
        resultDiseaseOrphanumber.setText(diseases.getOrphanumber());
        resultDiseaseOrphanumber.setTag(diseases.getOrphanumber());*/
        return convertView;
    }


    public String getItemDadosId(int position) {
        DadosNacionais sign = data.get(position);
        return sign.getDesorden_id();
    }


}


