package com.rarasnet.rnp.shared.protocol;

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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucas on 22/08/16.
 */
public class ProtocolSearchResultsAdapter extends ArrayAdapter<ProtocolModel> {
    private List<ProtocolModel> mDisorders;
    private int mLayoutResourceId;

    public interface OnItemClickListener {
        void onItemClick(ProtocolModel professional);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public ProtocolSearchResultsAdapter(Context context, int resource,
                                       ArrayList<ProtocolModel> protocols)
    {
        super(context, resource, protocols);
        mDisorders = protocols;
        mLayoutResourceId = resource;
    }

    @Override
    public ProtocolModel getItem(int position) {
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

            viewHolder.imagem = (ImageView) convertView.findViewById(R.id.avatar);
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
        ProtocolModel professional = mDisorders.get(position);

        // Assign values if the object is not null
        if(professional != null) {
            // CODIGO NOVO
            // Gets professional info returned by laravel api and
            // displays it on a new screen
            viewHolder.tv_nome.setText(professional.getDocument());
            viewHolder.tv_cidade.setText("");
            viewHolder.tv_profissao.setText("");
            viewHolder.imagem.setImageResource(R.mipmap.pdf_icon);

        }

        return convertView;
    }

    public void setAll(List<ProtocolModel> professionals) {
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
        ImageView imagem;
        ImageView iv_isFavoriteDoenca;
    }
}
