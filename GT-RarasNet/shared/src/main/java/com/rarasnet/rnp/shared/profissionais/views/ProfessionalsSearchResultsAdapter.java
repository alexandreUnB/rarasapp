package com.rarasnet.rnp.shared.profissionais.views;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
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
import com.rarasnet.rnp.shared.profissionais.controllers.network.responses.ProfissionaisAdapter;
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
    private int last_loaded;

    public String getSearchtype() {
        return searchtype;
    }

    public void setSearchtype(String searchtype) {
        this.searchtype = searchtype;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    private String searchtype;
    private String query;

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
        last_loaded = 9;
        searchtype = "name";
        query = "whatever";
    }

    @Override
    public LaravelSearchProfissionaisDataResponse getItem(int position) {
        return mDisorders.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null){
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
            viewHolder.iv_isFavoriteDoenca = (ImageView) convertView.findViewById(R.id.default_search_item_iv_iconRight);

            // store the holder with the view.
            convertView.setTag(viewHolder);

        }else{
            // we've just avoided calling findViewById() on resource everytime
            // just use the viewHolder
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Object item based on the position
        LaravelSearchProfissionaisDataResponse professional = mDisorders.get(position);

        if( position == last_loaded){
            viewHolder.tv_nome.setText("");
            viewHolder.tv_cidade.setText("");
            viewHolder.tv_profissao.setText("");
            viewHolder.loadButton.setClickable(true);
            viewHolder.loadButton.setEnabled(true);
            viewHolder.loadButton.setVisibility(View.VISIBLE);
            viewHolder.iv_isFavoriteDoenca.setVisibility(View.INVISIBLE);

            viewHolder.loadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(position == last_loaded){

                        class SendfeedbackJob extends AsyncTask<String, Void, String> {

                            @Override
                            protected String doInBackground(String[] params) {
                                List<LaravelSearchProfissionaisDataResponse> newResult = null;
                                ProfissionaisAdapter disorders = new ProfissionaisAdapter();

                                try {
                                    newResult = disorders.searchLaravel(query,
                                            Integer.toString(last_loaded) ,searchtype);

                                } catch (Exception e) {
                                    Log.d("[PSRA]Search error", e.toString());
                                }

                                for(LaravelSearchProfissionaisDataResponse prof: newResult){
                                    mDisorders.add(prof);
                                }
                                last_loaded += newResult.size();

                                // no new signs
                                if(newResult.isEmpty()){
                                    // hide the button, because we loaded all signs
                                    last_loaded = -1;
                                }
                                return "some message";
                            }

                            @Override
                            protected void onPostExecute(String message) {
                                notifyDataSetChanged();
                            }
                        }

                        SendfeedbackJob job = new SendfeedbackJob();
                        job.execute();
                    }
                }
            });

        }else{
            // Assign values if the object is not null
            if(professional != null) {
                // Gets professional info returned by laravel api and
                // displays it on a new screen
                viewHolder.tv_nome.setText(professional.getName() + professional.getSurname());
                viewHolder.tv_cidade.setText(professional.getCity()+ " - " + professional.getUf());
                viewHolder.tv_profissao.setText(professional.getProfession());
                viewHolder.loadButton.setClickable(false);
                viewHolder.loadButton.setEnabled(false);
                viewHolder.loadButton.setVisibility(View.INVISIBLE);
                viewHolder.iv_isFavoriteDoenca.setVisibility(View.VISIBLE);

            }
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
