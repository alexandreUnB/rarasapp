package com.rarasnet.rnp.info_projeto.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rarasnet.rnp.info_projeto.R;
import com.rarasnet.rnp.documentos.interfaces.RecyclerViewOnClickListenerHack;
import com.rarasnet.rnp.info_projeto.model.Legislacao;

import java.util.List;


/**
 * Created by viniciusthiengo on 4/5/15.
 */
public class LegislacaoAdapter extends RecyclerView.Adapter<LegislacaoAdapter.MyViewHolder> {
    private List<Legislacao> mList;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;


    public LegislacaoAdapter(Context c, List<Legislacao> l){
        mList = l;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Log.i("LOG", "onCreateViewHolder()");
        View v = mLayoutInflater.inflate(R.layout.item_legislacao, viewGroup, false);
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
        Log.i("LOG", "onBindViewHolder()");
       // myViewHolder.cid.setImageResource( mList.get(position).getPhoto() );
        myViewHolder.tvModel.setText(mList.get(position).getmName() );
       // myViewHolder.tvOrphanumber.setText( mList.get(position).getOrphanumber() );
        //myViewHolder.tvcid.setText(mList.get(position).getCid() );
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r){
        mRecyclerViewOnClickListenerHack = r;
    }


    public void addListItem(Legislacao c, int position){
        mList.add(c);
        notifyItemInserted(position);
    }


    public void removeListItem(int position){
        mList.remove(position);
        notifyItemRemoved(position);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView ivCar;
        public TextView tvModel;
        public TextView tvOrphanumber;
        public TextView tvcid;

        public MyViewHolder(View itemView) {
            super(itemView);

           // tvcid = (TextView) itemView.findViewById(R.id.cid);
            tvModel = (TextView) itemView.findViewById(R.id.tv_model);
           // tvOrphanumber = (TextView) itemView.findViewById(R.id.orphanunber);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mRecyclerViewOnClickListenerHack != null){
                mRecyclerViewOnClickListenerHack.onClickListener(v, getPosition());
            }
        }
    }
}
