package com.rarasnet.rnp.shared.center.search;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rarasnet.rnp.shared.R;

import com.rarasnet.rnp.shared.center.controllers.network.responses.SearchCentersDataResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Farina on 16/11/2015.
 */
public class CentersSearchResultsAdapter extends ArrayAdapter<SearchCentersDataResponse> {

    private List<SearchCentersDataResponse> mDisorders;
    private int mLayoutResourceId;



    public interface OnItemClickListener {
        void onItemClick(SearchCentersDataResponse professional);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public CentersSearchResultsAdapter(Context context, int resource, ArrayList<SearchCentersDataResponse> professionals) {
        super(context, resource, professionals);
        Log.d("o que temos", "temos2");
        mDisorders = professionals;
        mLayoutResourceId = resource;
    }

    @Override
    public SearchCentersDataResponse getItem(int position) {
        return mDisorders.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        Log.d("o que temos","temos1");

        if(convertView==null){
            Log.d("o que temos","temos");
            // inflate the layout
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            convertView = inflater.inflate(mLayoutResourceId, parent, false);

            // well set up the ViewHolder
            viewHolder = new ViewHolder();

            viewHolder.tv_nome = (TextView) convertView.findViewById(R.id.default_search_item_tv_principal);
            viewHolder.tv_cidade = (TextView) convertView.findViewById(R.id.default_search_item_tv_info1);
           viewHolder.tv_especialidade = (TextView) convertView.findViewById(R.id.default_search_item_tv_info2);

           // viewHolder.tv_uf = (TextView) convertView.findViewById(R.id.uf);
            viewHolder.rl_itemFrame = (RelativeLayout) convertView.findViewById(R.id.default_search_item_rl_frame);
            viewHolder.rl_itemFrame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(mDisorders.get(position));
                }
            });
            //viewHolder.iv_isFavoriteDoenca = (ImageView) convertView.findViewById(R.id.default_search_item_iv_iconRight);

            // store the holder with the view.
            convertView.setTag(viewHolder);

        }else{
            // we've just avoided calling findViewById() on resource everytime
            // just use the viewHolder
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // object item based on the position
        SearchCentersDataResponse professional = mDisorders.get(position);

        // assign values if the object is not null
        if(professional != null) {
            // get the TextView from the ViewHolder and then set the text (item name) and tag (item ID) values
            Log.d("o que temos",professional.getName());
            viewHolder.tv_nome.setText(professional.getName());
            viewHolder.tv_cidade.setText(professional.getCity() + " - " + professional.getUf());
            viewHolder.tv_especialidade.setText("CNES : Não Especificado ");
           // viewHolder.tv_uf.setText(professional.getEstado());

           // viewHolder.tv_profissao.setText(professional.getProfissao());
        }

        return convertView;
    }

    public void setAll(List<SearchCentersDataResponse> professionals) {
        clear();
        mDisorders = professionals;
        notifyDataSetChanged();
    }

    static class ViewHolder{
        RelativeLayout rl_itemFrame;
        TextView tv_nome;
        TextView tv_especialidade;
        TextView tv_cidade;
        TextView tv_uf;
        ImageView iv_isFavoriteDoenca;
    }
}
