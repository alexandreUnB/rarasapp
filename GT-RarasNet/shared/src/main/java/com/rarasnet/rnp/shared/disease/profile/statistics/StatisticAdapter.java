package com.rarasnet.rnp.shared.disease.profile.statistics;

/**
 * Created by lucas on 29/08/16.
 */

import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.rarasnet.rnp.shared.R;

import java.util.ArrayList;
import java.util.List;


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
import com.rarasnet.rnp.shared.models.Indicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Created by Farina on 23/10/2015.
 */
public class StatisticAdapter extends RecyclerView.Adapter<StatisticAdapter.ViewHolder> {

    private ArrayList<Indicator> mItems;
    private String disorderId;
    private OnItemClickListener mOnItemClickListener;
    public int last_loaded = 9;
    private BarChart sChart;

    public interface OnItemClickListener {
        void onItemClick(Sign item);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public StatisticAdapter(ArrayList<Indicator> items, String disorderID, BarChart chart) {
        mItems = items;
        disorderId = disorderID;
        sChart = chart;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.indicator_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        Indicator item = mItems.get(position);
//        int colorOn = 0xFF323E46;
//        int colorOff = 0xFF666666;
//        int colorDisabled = 0xFF333333;
//        StateListDrawable thumbStates = new StateListDrawable();
//        thumbStates.addState(new int[]{android.R.attr.state_checked}, new ColorDrawable(colorOn));
//        thumbStates.addState(new int[]{-android.R.attr.state_enabled}, new ColorDrawable(colorDisabled));
//        thumbStates.addState(new int[]{}, new ColorDrawable(colorOff)); // this one has to come last
//        viewHolder.selector.setThumbDrawable(thumbStates); // only on 16 API and above
            viewHolder.textViewPrincipal.setText(item.getNameIndicatorType() + " " +
            item.getGetNameIndicatorSource());

        viewHolder.selector.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isClicked) {
                if(isClicked){
                    viewHolder.selector.getTrackDrawable().setColorFilter(viewHolder.color,
                            PorterDuff.Mode.SRC_ATOP);
                    setBar(mItems.get(position), viewHolder.color);
                    Log.d("CLIQUEI", "CLIQUEI");

                }else{
                    viewHolder.selector.getTrackDrawable().setColorFilter(Color.GRAY,
                            PorterDuff.Mode.SRC_ATOP);
                    clearStatistic();
                    Log.d("NAOCLIQUEI", "NaoCLIQUEI");


                }
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
        Switch selector;
        TextView textViewInfo1;
        int color;

//        TextView textViewInfo2;

        public ViewHolder(View v) {
            super(v);
            this.textViewPrincipal = (TextView) v.findViewById(R.id.default_3line_icon_descript_item_tv_principal);
            this.selector = (Switch) v.findViewById(R.id.switch_graphics);
//            this.textViewInfo1 = (TextView) v.findViewById(R.id.default_3line_icon_descript_item_tv_info1);
//            this.textViewInfo2 = (TextView) v.findViewById(R.id.default_3line_icon_descript_item_tv_info2);
//            foto = (ImageView) v.findViewById(R.id.default_3line_icon_descript_item_iv_icon);
            Random rnd = new Random();
            this.color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            rowFrame = (RelativeLayout) v.findViewById(R.id.default_3line_icon_descript_item_rl_frame);
        }
    }


    private void setBar(Indicator info, int color) {

        sChart.clear();

        ArrayList<BarEntry> entries = new ArrayList<>();

        entries.add(new BarEntry(Float.parseFloat(info.getAmount()), 0));

        Log.d("INFO", "Received" + info.getAmount());

        BarDataSet dataset = new BarDataSet(entries, info.getNameIndicatorType());

        ArrayList<String> labels = new ArrayList<String>();
        labels.add(info.getYear());
        dataset.setColor(color);
        BarData data = new BarData(labels, dataset);
        sChart.setData(data);


    }

    private void clearStatistic() {
        sChart.clear();
    }

}
