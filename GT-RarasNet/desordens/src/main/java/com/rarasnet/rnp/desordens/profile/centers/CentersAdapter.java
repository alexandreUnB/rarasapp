package com.rarasnet.rnp.desordens.profile.centers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rarasnet.rnp.desordens.R;

import java.util.ArrayList;


/**
 * Created by Farina on 19/10/2015.
 */
public class CentersAdapter extends RecyclerView.Adapter<CentersAdapter.ViewHolder> {

    private ArrayList<AssociatedCenter> mItems;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(AssociatedCenter item);
    }

    public CentersAdapter(ArrayList<AssociatedCenter> items) {
        mItems = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.default_3line_icon_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        AssociatedCenter item = mItems.get(position);
        //viewHolder.foto.setImageResource(item.getPhotoID());
        viewHolder.textViewPrincipal.setText(item.getNome());
        viewHolder.textViewInfo1.setText(item.getEndereco());
        viewHolder.textViewInfo2.setText("Cnes: " + item.getCnes());

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
        TextView textViewInfo2;

        public ViewHolder(View v) {
            super(v);
            this.textViewPrincipal = (TextView) v.findViewById(R.id.default_3line_icon_item_tv_principal);
            this.textViewInfo1 = (TextView) v.findViewById(R.id.default_3line_icon_item_tv_info1);
            this.textViewInfo2 = (TextView) v.findViewById(R.id.default_3line_icon_item_tv_info2);
            foto = (ImageView) v.findViewById(R.id.default_3line_icon_item_iv_icon);
            rowFrame = (RelativeLayout) v.findViewById(R.id.default_3line_icon_item_rl_frame);
        }
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }


}
