package com.rarasnet.rnp.desordens.profile.signs;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rarasnet.rnp.desordens.R;
import com.rarasnet.rnp.desordens.search.models.Sign;

import java.util.ArrayList;


/**
 * Created by Farina on 23/10/2015.
 */
public class SignsAdapter extends RecyclerView.Adapter<SignsAdapter.ViewHolder> {

    private ArrayList<Sign> mItems;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Sign item);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public SignsAdapter(ArrayList<Sign> items) {
        mItems = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.default_3line_icon_descript_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Sign item = mItems.get(position);
        //viewHolder.foto.setImageResource(item.getPhotoID());
        viewHolder.textViewPrincipal.setText(item.getName());
        viewHolder.textViewInfo1.setText(item.getFrequency());
//        viewHolder.textViewInfo2.setText(item.getDescricao());

        viewHolder.rowFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(mItems.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rowFrame;
        ImageView foto;
        TextView textViewPrincipal;
        TextView textViewInfo1;
//        TextView textViewInfo2;

        public ViewHolder(View v) {
            super(v);
            this.textViewPrincipal = (TextView) v.findViewById(R.id.default_3line_icon_descript_item_tv_principal);
            this.textViewInfo1 = (TextView) v.findViewById(R.id.default_3line_icon_descript_item_tv_info1);
//            this.textViewInfo2 = (TextView) v.findViewById(R.id.default_3line_icon_descript_item_tv_info2);
            foto = (ImageView) v.findViewById(R.id.default_3line_icon_descript_item_iv_icon);
            rowFrame = (RelativeLayout) v.findViewById(R.id.default_3line_icon_descript_item_rl_frame);
        }
    }


}
