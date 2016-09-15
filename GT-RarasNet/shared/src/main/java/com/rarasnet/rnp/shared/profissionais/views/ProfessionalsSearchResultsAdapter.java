package com.rarasnet.rnp.shared.profissionais.views;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rarasnet.rnp.shared.R;
import com.rarasnet.rnp.shared.profissionais.controllers.network.responses.LaravelSearchProfissionaisDataResponse;
import com.rarasnet.rnp.shared.profissionais.controllers.network.responses.SearchProfissionaisDataResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Farina on 16/11/2015.
 * Adapted by Baby on 09/08/2016
 *
 * This method is responsible to set the list
 * of professionals that were found at the
 * database in a list view
 */
public class ProfessionalsSearchResultsAdapter extends ArrayAdapter<LaravelSearchProfissionaisDataResponse> {

    private List<LaravelSearchProfissionaisDataResponse> mDisorders;
    private int mLayoutResourceId;

    public interface OnItemClickListener {
        void onItemClick(LaravelSearchProfissionaisDataResponse professional);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public ProfessionalsSearchResultsAdapter(Context context, int resource, ArrayList<LaravelSearchProfissionaisDataResponse> professionals)
    {
        super(context, resource, professionals);
        mDisorders = professionals;
        mLayoutResourceId = resource;
    }

    @Override
    public LaravelSearchProfissionaisDataResponse getItem(int position) {
        return mDisorders.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView==null){
            // inflate the layout
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            convertView = inflater.inflate(mLayoutResourceId, parent, false);

            // well set up the ViewHolder
            viewHolder = new ViewHolder();

            viewHolder.loadButton = (ImageButton) convertView.findViewById(R.id.loadButton);
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

        // Object item based on the position
        LaravelSearchProfissionaisDataResponse professional = mDisorders.get(position);

        // Assign values if the object is not null
        if(professional != null) {
            // CODIGO NOVO
            // Gets professional info returned by laravel api and
            // displays it on a new screen
            Log.d("Nome do Cara", professional.getName());
            viewHolder.tv_nome.setText(professional.getName() + professional.getSurname());
            viewHolder.tv_cidade.setText(professional.getCity()+ " - " + professional.getUf());
            viewHolder.tv_profissao.setText(professional.getProfession());
            viewHolder.loadButton.setClickable(false);
            viewHolder.loadButton.setEnabled(false);
            viewHolder.loadButton.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }

    public void setAll(List<LaravelSearchProfissionaisDataResponse> professionals) {
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
        ImageButton loadButton;

    }
}
