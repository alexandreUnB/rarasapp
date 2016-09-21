package com.rarasnet.rnp.shared.disease.search.adapters;

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
import com.rarasnet.rnp.shared.disease.search.models.DisordersModel;
import com.rarasnet.rnp.shared.models.Disorder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Farina on 16/11/2015.
 */
public class DisordersSearchResultsAdapter extends ArrayAdapter<Disorder> {

    private ArrayList<Disorder> mDisorders;
    private int mLayoutResourceId;

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    private String searchType;
    private String query;
    private int last_loaded;

    public interface OnItemClickListener {
        void onItemClick(Disorder disorder);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public DisordersSearchResultsAdapter(Context context, int resource, ArrayList<Disorder> disorders) {
        super(context, resource, disorders);
        mDisorders = disorders;
        mLayoutResourceId = resource;
        searchType = "name";
        query = "whatever";
        last_loaded = 9;
    }

    @Override
    public Disorder getItem(int position) {
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
            viewHolder.tv_domeDoenca = (TextView) convertView.findViewById(R.id.default_search_item_tv_principal);
            viewHolder.tv_orphanumberDoenca = (TextView) convertView.findViewById(R.id.default_search_item_tv_info1);
//            viewHolder.tv_cidDoenca = (TextView) convertView.findViewById(R.id.default_search_item_tv_info2);
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

        // object item based on the position
        Disorder disorder = mDisorders.get(position);

        // assign values if the object is not null
        if(disorder != null) {

            if( position == last_loaded) {
                viewHolder.tv_domeDoenca.setText("");
                viewHolder.tv_orphanumberDoenca.setText("");
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
                                    List<Disorder> result = null;
                                    DisordersModel disorders = new DisordersModel();

                                    try {
                                            if(searchType == "name"){
                                                result = disorders.nameSearch(query, Integer.toString(last_loaded));
                                            }else if(searchType == "cid"){
                                                result = disorders.cidSearch(query, Integer.toString(last_loaded));
                                            }else{
                                                result = disorders.nameSearch("%25", Integer.toString(last_loaded));
                                            }


                                    } catch (Exception e) {
                                        Log.d("[DSRA]Search error", e.toString());
                                    }

                                    Log.d("Search", searchType + " " + query);

                                    for(Disorder disorder: result){
                                        mDisorders.add(disorder);
                                    }
                                    last_loaded += result.size();

                                    // no new signs
                                    if(result.isEmpty()){
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

            }else {
                // get the TextView from the ViewHolder and then set the text (item name) and tag (item ID) values
                viewHolder.tv_domeDoenca.setText(disorder.getName());
                viewHolder.tv_orphanumberDoenca.setText("Orphanumber: " + disorder.getOrphanumber());
                viewHolder.loadButton.setClickable(false);
                viewHolder.loadButton.setEnabled(false);
                viewHolder.loadButton.setVisibility(View.INVISIBLE);
                viewHolder.iv_isFavoriteDoenca.setVisibility(View.VISIBLE);
            }
        }

        return convertView;
    }

    public void setAll(ArrayList<Disorder> disorders) {
        clear();
        mDisorders = disorders;
        notifyDataSetChanged();
    }

    static class ViewHolder{
        RelativeLayout rl_itemFrame;
        TextView tv_domeDoenca;
        TextView tv_cidDoenca;
        TextView tv_orphanumberDoenca;
        ImageView iv_isFavoriteDoenca;
        ImageButton loadButton;
    }
}
