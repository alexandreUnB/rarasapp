package com.rarasnet.rnp.centros.views;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rarasnet.rnp.centros.R;
import com.rarasnet.rnp.centros.controllers.network.responses.SearchCentersDataResponse;

import java.util.ArrayList;

/**
 * Created by Farina on 21/9/2015.
 */
public class ListaCentersAdapter extends ArrayAdapter<SearchCentersDataResponse> {


    private Context mContext;
    private int layoutResourceId;
    private ArrayList<SearchCentersDataResponse > data = new ArrayList<>();
    private ViewHolder viewHolder;

    public ListaCentersAdapter(Context context, int layoutResourceId,
                                     ArrayList<SearchCentersDataResponse> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.mContext = context;
        this.data = data;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(convertView==null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.nomeCentro = (TextView) convertView.findViewById(R.id.list_centers_item_tv_nomeProfissional);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.nomeCentro.setText(data.get(position).getNome());
        return convertView;
    }


    @Override
    public int getCount() {
        return data.size();
    }

    public void update(ArrayList<SearchCentersDataResponse> centers) {
        super.clear();
        super.addAll(centers);
        notifyDataSetChanged();
    }

    public class ViewHolder {
        TextView nomeCentro;

        public ViewHolder() {
        }
    }
}
