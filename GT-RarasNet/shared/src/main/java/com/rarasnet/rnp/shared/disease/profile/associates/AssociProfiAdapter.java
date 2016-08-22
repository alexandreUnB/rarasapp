package com.rarasnet.rnp.shared.disease.profile.associates;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * Created by Farina on 19/10/2015.
 */
public class AssociProfiAdapter extends RecyclerView.Adapter<AssociProfiAdapter.ViewHolder> {

    private ArrayList<AssociatedProfile> mItems;
    private OnItemClickListener mOnItemClickListener;
    public int last_loaded = 9;
    private String disorderId;

    public interface OnItemClickListener {
        void onItemClick(AssociatedProfile item);
    }

    public AssociProfiAdapter(ArrayList<AssociatedProfile> items, String disorderID) {
        mItems = items;
        disorderId = disorderID;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.default_result_profe_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        AssociatedProfile item = mItems.get(position);

        if(position == last_loaded){
            viewHolder.botao.setImageResource(R.mipmap.ic_add_load);
            viewHolder.textViewPrincipal.setText("");
            viewHolder.textViewInfo1.setText("");
            viewHolder.textViewInfo2.setText("");
            viewHolder.foto.setImageResource(0);
            viewHolder.icon.setImageResource(0);
            viewHolder.rowFrame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        class SendfeedbackJob extends AsyncTask<String, Void, String> {
                            @Override
                            protected String doInBackground(String[] params) {
                                List<AssociatedProfile> newAssociates;
                                DisorderProfileModel requester = new DisorderProfileModel();


                                newAssociates = requester.getProfsLoader(disorderId,
                                        Integer.toString(last_loaded));
                                for(AssociatedProfile newAssociate: newAssociates){
                                    mItems.add(newAssociate);
                                }
                                last_loaded += newAssociates.size();

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
            });

        }else {
            //  viewHolder.foto.setImageResource(R.drawable.ic_hospital_building_black_24dp);
            viewHolder.botao.setImageResource(0);
            viewHolder.textViewPrincipal.setText(item.getNome());
            viewHolder.textViewInfo1.setText(item.getCidade());
//        viewHolder.textViewInfo2.setText("Especialidade: " + item.getEspecialidade());
            viewHolder.textViewInfo2.setText(item.getEspecialidade());//uf


            viewHolder.rowFrame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        mOnItemClickListener.onItemClick(mItems.get(position));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rowFrame;
        ImageView foto;
        ImageView botao;
        ImageView icon;
        TextView textViewPrincipal;
        TextView textViewInfo1;
        TextView textViewInfo2;

        public ViewHolder(View v) {
            super(v);
            this.textViewPrincipal = (TextView) v.findViewById(R.id.default_search_item_tv_principal);
            this.textViewInfo1 = (TextView) v.findViewById(R.id.default_search_item_tv_info1);
            this.textViewInfo2 = (TextView) v.findViewById(R.id.default_search_item_tv_info2);
            foto = (ImageView) v.findViewById(R.id.avatar);
            botao = (ImageView) v.findViewById(R.id.default_search_item_iv_icon_add);
            icon = (ImageView) v.findViewById((R.id.default_search_item_iv_iconRight));
            rowFrame = (RelativeLayout) v.findViewById(R.id.default_search_item_rl_frame);
        }
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }


}
