package com.rarasnet.rnp.desordens.search.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import com.rarasnet.rnp.desordens.R;
import com.rarasnet.rnp.desordens.search.models.Sign;

/**
 * Created by Farina on 5/8/2015.
 */
public class SignsAdapter extends ArrayAdapter {

    private Context mContext;
    private int layoutResourceId;
    private List<Sign> data = null;

    public SignsAdapter(Context mContext, int layoutResourceId, List<Sign> data) {
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

        Sign sign = data.get(position);

        // get the TextView and then set the text (item name) and tag (item ID) values
        TextView resultDiseaseName = (TextView) convertView.findViewById(R.id.diseaseSigns);
        resultDiseaseName.setText(sign.getName());
        resultDiseaseName.setTag(sign.getId());

        /*TextView resultDiseaseOrphanumber = (TextView) convertView.findViewById(R.id.resultOrphanumber);
        resultDiseaseOrphanumber.setText(diseases.getOrphanumber());
        resultDiseaseOrphanumber.setTag(diseases.getOrphanumber());*/
        return convertView;
    }


    public String getItemSingId(int position) {
        Sign sign = data.get(position);
        return sign.getId();
    }


    public String getItemname(int position) {
        Sign sing = data.get(position);
        return sing.getName();
    }
}
