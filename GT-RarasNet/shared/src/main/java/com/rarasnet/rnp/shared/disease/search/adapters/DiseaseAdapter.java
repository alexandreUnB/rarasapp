package com.rarasnet.rnp.shared.disease.search.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.rarasnet.rnp.shared.R;
import com.rarasnet.rnp.shared.models.Disorder;

import java.util.List;

public abstract class DiseaseAdapter extends BaseAdapter {

    private List<Disorder> diseases = null;
    private LayoutInflater inflater;
    private Context mContext;
    private int layoutResourceId;

    public DiseaseAdapter(Context context, List<Disorder> diseases) {

        this.inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        this.diseases = diseases;
    }

    public void novosDados(List<Disorder> diseases) {
        this.diseases = diseases;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return diseases.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return diseases.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View v = inflater.inflate(R.layout.item_disease, null);
        ((TextView) (v.findViewById(R.id.txtNome))).setText(diseases.get(position).getName());

        ((ImageButton) (v.findViewById(R.id.btnEditar))).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                edita(diseases.get(position));
            }
        });

        ((ImageButton) (v.findViewById(R.id.btnExcluir))).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                deleta(diseases.get(position));
            }
        });

        return v;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public abstract void edita(Disorder disease);

    public abstract void deleta(Disorder disease);
}
