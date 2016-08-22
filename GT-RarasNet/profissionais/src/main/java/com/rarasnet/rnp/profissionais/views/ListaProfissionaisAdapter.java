package com.rarasnet.rnp.profissionais.views;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rarasnet.rnp.profissionais.R;
import com.rarasnet.rnp.profissionais.controllers.network.responses.SearchProfissionaisDataResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Farina on 17/9/2015.
 *
 * Adapter para a lista de pesquisa de profissionais
 *
 */
public class ListaProfissionaisAdapter extends ArrayAdapter<SearchProfissionaisDataResponse> {

    ArrayList<SearchProfissionaisDataResponse> searchProfissionaisDataResponses = new ArrayList<>();

    public interface OnItemListViewElementListener {
        public void onItemListViewElementClick(int viewId, SearchProfissionaisDataResponse pData);
    }


    private Context mContext;
    private int layoutResourceId;
    private ArrayList<SearchProfissionaisDataResponse > data = new ArrayList<>();
    private ViewHolder viewHolder;
    private OnItemListViewElementListener listViewElementListener;

    public ListaProfissionaisAdapter(Context context, int layoutResourceId,
                                     ArrayList<SearchProfissionaisDataResponse> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.mContext = context;
        this.data = data;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(convertView==null) {
            //infla o layout
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
            //rowData = new RowData((TextView) convertView.findViewById(R.id.result_protocols_DiseaseName));
            viewHolder = new ViewHolder();
            viewHolder.nomeProfissional = (TextView) convertView.findViewById(R.id.list_profissionais_item_tv_nomeProfissional);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.nomeProfissional.setText(data.get(position).getNome());


        return convertView;
    }


    @Override
    public int getCount() {
        return data.size();
    }

    public void update(ArrayList<SearchProfissionaisDataResponse> profissionais) {
        super.clear();
        super.addAll(profissionais);
        notifyDataSetChanged();
    }

    public class ViewHolder {
        TextView nomeProfissional;

        public ViewHolder() {
        }
    }
}
