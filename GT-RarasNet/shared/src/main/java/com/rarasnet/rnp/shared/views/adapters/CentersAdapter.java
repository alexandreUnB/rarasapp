package com.rarasnet.rnp.shared.views.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import com.rarasnet.rnp.shared.R;
import com.rarasnet.rnp.shared.models.Center;

/**
 * Created by Ronnyery Barbosa on 14/08/2015.
 *
 *  Adapter para uma lista de Centros
 */
public class CentersAdapter extends ArrayAdapter {
    private Context mContext;
    private int layoutResourceId;
    private List<Center> data = null;

    public CentersAdapter(Context mContext, int layoutResourceId, List<Center> data) {
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

        Center sign = data.get(position);

        // get the TextView and then set the text (item name) and tag (item ID) values
        TextView professionalName = (TextView) convertView.findViewById(R.id.center_setname);
        TextView professionalCidade = (TextView) convertView.findViewById(R.id.center_setcidade);
        TextView professionalEstado = (TextView) convertView.findViewById(R.id.center_setestado);


        professionalName.setText(sign.getNome());
        professionalCidade.setText(sign.getCidade());
        professionalEstado.setText(sign.getEstado());


        //resultDiseaseName.setTag(sign.getId());

        /*TextView resultDiseaseOrphanumber = (TextView) convertView.findViewById(R.id.resultOrphanumber);
        resultDiseaseOrphanumber.setText(diseases.getOrphanumber());
        resultDiseaseOrphanumber.setTag(diseases.getOrphanumber());*/
        return convertView;
    }


    public String getItemCenterId(int position) {
        Center sign = data.get(position);
        return sign.getId();
    }


    public String getItemCenterName(int position) {
        Center sing = data.get(position);
        return sing.getNome();
    }
}






