package com.rarasnet.rnp.desordens.search.adapters;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rarasnet.rnp.desordens.R;
import com.rarasnet.rnp.shared.models.Disorder;

import java.util.ArrayList;

/**
 * Created by Farina on 16/11/2015.
 */
public class DisordersSearchResultsAdapter extends ArrayAdapter<Disorder> {

    private ArrayList<Disorder> mDisorders;
    private int mLayoutResourceId;

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
    }

    @Override
    public Disorder getItem(int position) {
        return mDisorders.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;


        if(convertView==null){
            Log.d("o que temos","temos");
            // inflate the layout
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            convertView = inflater.inflate(mLayoutResourceId, parent, false);

            // well set up the ViewHolder
            viewHolder = new ViewHolder();

            viewHolder.tv_domeDoenca = (TextView) convertView.findViewById(R.id.default_search_item_tv_principal);
            viewHolder.tv_orphanumberDoenca = (TextView) convertView.findViewById(R.id.default_search_item_tv_info1);
            viewHolder.tv_cidDoenca = (TextView) convertView.findViewById(R.id.default_search_item_tv_info2);
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
        Disorder disorder = mDisorders.get(position);

        // assign values if the object is not null
        if(disorder != null) {
            // get the TextView from the ViewHolder and then set the text (item name) and tag (item ID) values
            //Log.d("o que temos",);
            viewHolder.tv_domeDoenca.setText(disorder.getName());
            viewHolder.tv_orphanumberDoenca.setText("Orphanumber: " + disorder.getOrphanumber());
            String link = String.format("<a href=\"%s\">%s/>", disorder.getExpertlink(), disorder.getExpertlink());
            viewHolder.tv_cidDoenca.setText(Html.fromHtml(link));
            viewHolder.tv_cidDoenca.setMovementMethod(LinkMovementMethod.getInstance());
            viewHolder.tv_cidDoenca.setClickable(true);
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
    }
}
