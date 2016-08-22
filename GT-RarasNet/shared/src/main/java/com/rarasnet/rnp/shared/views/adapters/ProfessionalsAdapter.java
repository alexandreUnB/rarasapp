package com.rarasnet.rnp.shared.views.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.rarasnet.rnp.shared.models.Professional;

import java.util.List;

/**
 * Created by Ronnyery Barbosa on 12/08/2015.
 *
 *  Adapter para uma lista de Profissionais
 */
public class ProfessionalsAdapter extends ArrayAdapter {


    private Context mContext;
    private int layoutResourceId;
    private List<Professional> data = null;

    public ProfessionalsAdapter(Context mContext, int layoutResourceId, List<Professional> data) {
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

        Professional sign = data.get(position);

        // get the TextView and then set the text (item name) and tag (item ID) values
      //  TextView professionalName = (TextView) convertView.findViewById(R.id.profissional_setname);
       // TextView professionalCidade = (TextView) convertView.findViewById(R.id.profissional_setcidade);
       // TextView professionalEstado = (TextView) convertView.findViewById(R.id.profissional_setestado);


      //  professionalName.setText(sign.getNome());
      //  professionalCidade.setText(sign.getCidade());
      //  professionalEstado.setText(sign.getEstado());


        //resultDiseaseName.setTag(sign.getId());

        /*TextView resultDiseaseOrphanumber = (TextView) convertView.findViewById(R.id.resultOrphanumber);
        resultDiseaseOrphanumber.setText(diseases.getOrphanumber());
        resultDiseaseOrphanumber.setTag(diseases.getOrphanumber());*/
        return convertView;
    }


    public String getItemProfeId(int position) {
        Professional sign = data.get(position);
        return sign.getId();
    }


    public String getItemProfeName(int position) {
        Professional sing = data.get(position);
        return sing.getNome();
    }
}


