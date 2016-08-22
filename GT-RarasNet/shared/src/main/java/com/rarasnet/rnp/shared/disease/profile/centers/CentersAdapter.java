package com.rarasnet.rnp.shared.disease.profile.centers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rarasnet.rnp.shared.R;

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
                .inflate(R.layout.default_center_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        AssociatedCenter item = mItems.get(position);

      //  viewHolder.foto.setImageResource(R.drawable.ic_hospital_building_black_24dp);
        viewHolder.textViewPrincipal.setText(item.getNome());
        viewHolder.textViewInfo1.setText(item.getEndereco());
        if(item.getCnes().isEmpty())
            viewHolder.textViewInfo2.setText("Cnes: Indisponível");
        else
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
            this.textViewPrincipal = (TextView) v.findViewById(R.id.default_search_item_tv_principal);
            this.textViewInfo1 = (TextView) v.findViewById(R.id.default_search_item_tv_info1);
            this.textViewInfo2 = (TextView) v.findViewById(R.id.default_search_item_tv_info2);
            foto = (ImageView) v.findViewById(R.id.avatar);
            rowFrame = (RelativeLayout) v.findViewById(R.id.default_search_item_rl_frame);
        }
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }


}
