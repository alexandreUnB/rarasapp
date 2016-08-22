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
import com.rarasnet.rnp.desordens.search.models.Synonym;

/**
 * Created by Farina on 5/8/2015.
 */
public class SynonymsAdapter extends ArrayAdapter {

    private Context mContext;
    private int layoutResourceId;
    private List<Synonym> data = null;

    public SynonymsAdapter(Context mContext, int layoutResourceId, List<Synonym> data) {
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

        Synonym synonym = data.get(position);

        // get the TextView and then set the text (item name) and tag (item ID) values
        TextView synonymName = (TextView) convertView.findViewById(R.id.diseaseSynonyms);
        synonymName.setText(synonym.getSinonimo());
        synonymName.setTag(synonym.getId());

        /*TextView resultDiseaseOrphanumber = (TextView) convertView.findViewById(R.id.resultOrphanumber);
        resultDiseaseOrphanumber.setText(diseases.getOrphanumber());
        resultDiseaseOrphanumber.setTag(diseases.getOrphanumber());*/
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        Synonym synonym = data.get(position);
        return synonym.getId();
    }

}
