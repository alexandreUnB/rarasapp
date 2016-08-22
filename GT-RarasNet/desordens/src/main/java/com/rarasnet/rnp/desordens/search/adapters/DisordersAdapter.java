package com.rarasnet.rnp.desordens.search.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rarasnet.rnp.desordens.R;
import com.rarasnet.rnp.shared.models.Disorder;

import java.util.List;

/**
 * Created by Farina on 4/5/2015.
 */
public class DisordersAdapter extends ArrayAdapter<Disorder> {

    private Context mContext;
    private int layoutResourceId;
    private List<Disorder> data = null;

    public DisordersAdapter(Context mContext, int layoutResourceId, List<Disorder> data) {
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

        Disorder diseases = data.get(position);

        // get the TextView and then set the text (item name) and tag (item ID) values
        TextView resultDiseaseName = (TextView) convertView.findViewById(R.id.resultDiseaseName);
        resultDiseaseName.setText(diseases.getName());
        resultDiseaseName.setTag(diseases.getId());

        // button = (ImageButton) convertView.findViewById(R.id.list_image);

        /*TextView resultDiseaseOrphanumber = (TextView) convertView.findViewById(R.id.resultOrphanumber);
        resultDiseaseOrphanumber.setText(diseases.getOrphanumber());
        resultDiseaseOrphanumber.setTag(diseases.getOrphanumber());*/
        return convertView;
    }


}
