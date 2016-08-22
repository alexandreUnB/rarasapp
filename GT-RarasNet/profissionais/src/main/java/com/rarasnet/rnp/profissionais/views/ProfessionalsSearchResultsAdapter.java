package com.rarasnet.rnp.profissionais.views;

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

import com.rarasnet.rnp.profissionais.R;
import com.rarasnet.rnp.profissionais.controllers.network.responses.SearchProfissionaisDataResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Farina on 16/11/2015.
 */
public class ProfessionalsSearchResultsAdapter extends ArrayAdapter<SearchProfissionaisDataResponse> {

    private List<SearchProfissionaisDataResponse> mDisorders;
    private int mLayoutResourceId;



    public interface OnItemClickListener {
        void onItemClick(SearchProfissionaisDataResponse professional);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public ProfessionalsSearchResultsAdapter(Context context, int resource, ArrayList<SearchProfissionaisDataResponse> professionals) {
        super(context, resource, professionals);
        Log.d("o que temos", "temos2");
        Log.d("WTF", "WTF");

        mDisorders = professionals;
        mLayoutResourceId = resource;
    }

    @Override
    public SearchProfissionaisDataResponse getItem(int position) {
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
            viewHolder.tv_profissao = (TextView) convertView.findViewById(R.id.default_search_item_tv_info2);
            //viewHolder.tv_uf = (TextView) convertView.findViewById(R.id.uf);
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
        SearchProfissionaisDataResponse professional = mDisorders.get(position);

        // assign values if the object is not null
        if(professional != null) {
            // get the TextView from the ViewHolder and then set the text (item name) and tag (item ID) values
            Log.d("o que temos",professional.getNome());
            viewHolder.tv_nome.setText(professional.getNome());
            viewHolder.tv_cidade.setText(professional.getCidade() + " " + professional.getEstado());
           // viewHolder.tv_uf.setText(professional.getEstado());

            viewHolder.tv_profissao.setText(professional.getProfissao());
        }

        return convertView;
    }

    public void setAll(List<SearchProfissionaisDataResponse> professionals) {
        clear();
        mDisorders = professionals;
        notifyDataSetChanged();
    }

    static class ViewHolder{
        RelativeLayout rl_itemFrame;
        TextView tv_nome;
        TextView tv_profissao;
        TextView tv_cidade;
        TextView tv_uf;
        ImageView iv_isFavoriteDoenca;
    }
}
