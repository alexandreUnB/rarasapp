package com.rarasnet.rnp.shared.disease.profile.signs;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rarasnet.rnp.shared.R;

import com.rarasnet.rnp.shared.disease.search.models.DisorderProfileModel;
import com.rarasnet.rnp.shared.disease.search.models.Sign;
import com.rarasnet.rnp.shared.disease.search.models.SignProfileModel;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Farina on 23/10/2015.
 */
public class SignsAdapter extends RecyclerView.Adapter<SignsAdapter.ViewHolder> {

    private ArrayList<Sign> mItems;
    private String disorderId;
    private OnItemClickListener mOnItemClickListener;
    public int last_loaded = 9;

    public interface OnItemClickListener {
        void onItemClick(Sign item);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public SignsAdapter(ArrayList<Sign> items, String disorderID) {
        mItems = items;
        disorderId = disorderID;
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
        if(position == last_loaded){
            viewHolder.textViewPrincipal.setText("");
            viewHolder.textViewInfo1.setText("");

            // load buttom
            viewHolder.loadButton.setClickable(true);
            viewHolder.loadButton.setEnabled(true);
            viewHolder.loadButton.setVisibility(View.VISIBLE);
            viewHolder.loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position == last_loaded){

                     class SendfeedbackJob extends AsyncTask<String, Void, String> {

                        @Override
                        protected String doInBackground(String[] params) {
                            ImageView foto;
                            List<Sign> newSigns;
                            SignProfileModel requester = new SignProfileModel();

                            newSigns = requester.getSignsLoader(disorderId, Integer.toString(last_loaded));
                            for(Sign newSing: newSigns){
                                mItems.add(newSing);
                            }
                            last_loaded += newSigns.size();

                            // no new signs
                            if(newSigns.isEmpty()){
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
//                mOnItemClickListener.onItemClick(mItems.get(position));
            }
        });

        }else{
            viewHolder.loadButton.setClickable(false);
            viewHolder.loadButton.setEnabled(false);
            viewHolder.loadButton.setVisibility(View.INVISIBLE);
            viewHolder.textViewPrincipal.setText(item.getName());
            viewHolder.textViewInfo1.setText(item.getFrequency());
        }


    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rowFrame;
        ImageButton loadButton;
        TextView textViewPrincipal;
        TextView textViewInfo1;
//        TextView textViewInfo2;

        public ViewHolder(View v) {
            super(v);
            this.textViewPrincipal = (TextView) v.findViewById(R.id.default_3line_icon_descript_item_tv_principal);
            this.textViewInfo1 = (TextView) v.findViewById(R.id.default_3line_icon_descript_item_tv_info1);
//            this.textViewInfo2 = (TextView) v.findViewById(R.id.default_3line_icon_descript_item_tv_info2);
            loadButton = (ImageButton) v.findViewById(R.id.loadButton);
            rowFrame = (RelativeLayout) v.findViewById(R.id.default_3line_icon_descript_item_rl_frame);
        }
    }


}
