package com.rarasnet.rnp.documentos.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.rarasnet.rnp.documentos.R;
import com.rarasnet.rnp.documentos.controllers.activities.SearchProtocolsActivity;
import com.rarasnet.rnp.documentos.controllers.network.responses.ProtocolDataResponse;
import com.rarasnet.rnp.shared.util.managers.PDFManager;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Farina on 18/7/2015.
 */
public class ProtocolsAdapter extends ArrayAdapter<ProtocolDataResponse> {

    public interface OnItemListViewElementListener {
        public void onItemListViewElementClick(int viewId, ProtocolDataResponse pData);
    }

    private class RowData {
        public TextView associatedDisease;

        public RowData(TextView associatedDisease) {
            this.associatedDisease = associatedDisease;
        }
    }

    private Context mContext;
    private int layoutResourceId;
    private List<ProtocolDataResponse> data = null;
    private RowData rowData;
    private OnItemListViewElementListener listViewElementListener;

    public ProtocolsAdapter(Context mContext, int layoutResourceId, List<ProtocolDataResponse> data,
                            OnItemListViewElementListener listViewElementListener) {
        super(mContext, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;
        RelativeLayout layout = (RelativeLayout) ((Activity) mContext).findViewById(R.id.frame_list_diseases);
        layout.setVisibility(View.VISIBLE);
        this.listViewElementListener = listViewElementListener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(convertView==null) {
            //infla o layout
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
            //rowData = new RowData((TextView) convertView.findViewById(R.id.result_protocols_DiseaseName));
        }

        //TODO Arrumar essa desgraça depois
        ProtocolDataResponse protocolData = data.get(position);
        TextView tst = (TextView) convertView.findViewById(R.id.result_protocols_DiseaseName);
        tst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listViewElementListener.onItemListViewElementClick(v.getId(), data.get(position));
            }
        });
        tst.setText(protocolData.getProtocolName());

        TextView verPerfil = (TextView) convertView.findViewById(R.id.result_protocols_DiseaseProfile);
        verPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listViewElementListener.onItemListViewElementClick(v.getId(), data.get(position));
            }
        });

        ImageView pdfSymbol = (ImageView) convertView.findViewById(R.id.pdfIcon);
        pdfSymbol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listViewElementListener.onItemListViewElementClick(v.getId(), data.get(position));
            }
        });
        //Log.d("Protocols adapter:", protocolData.getDisorderName());
        //rowData.associatedDisease.setText(protocolData.getDisorderName());
        //rowData.associatedDisease.setTag(protocolData.getId());
        //Log.d("Protocols adapter TextView: ", rowData.associatedDisease.getText().toString());
        return convertView;
    }



    @Override
    public long getItemId(int position) {
        ProtocolDataResponse protocolData = data.get(position);
        return Long.parseLong(protocolData.getId());
    }

    @Override
    public int getCount() {
        return data.size();
    }

    public void update(ArrayList<ProtocolDataResponse> protocols) {
        this.data = protocols;
        notifyDataSetChanged();
    }
}

