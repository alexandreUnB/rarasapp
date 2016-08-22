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
import com.rarasnet.rnp.desordens.search.models.Reference;

/**
 * Created by Farina on 5/8/2015.
 */
public class ReferencesAdapter extends ArrayAdapter {

    private Context mContext;
    private int layoutResourceId;
    private List<Reference> data = null;

    public ReferencesAdapter(Context mContext, int layoutResourceId, List<Reference> data) {
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

        Reference reference = data.get(position);

        // get the TextView and then set the text (item name) and tag (item ID) values
        TextView referenceText = (TextView) convertView.findViewById(R.id.diseaseReferences);
        referenceText.setText(reference.getReference());
        referenceText.setTag(reference.getId());


        TextView source = (TextView) convertView.findViewById(R.id.referenceSource);
        source.setText(reference.getSource());
        source.setTag(reference.getId());
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        Reference reference = data.get(position);
        return reference.getId();
    }
}
