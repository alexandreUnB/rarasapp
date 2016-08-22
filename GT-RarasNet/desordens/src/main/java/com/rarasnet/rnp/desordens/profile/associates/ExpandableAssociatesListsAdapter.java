package com.rarasnet.rnp.desordens.profile.associates;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rarasnet.rnp.desordens.R;

import java.util.ArrayList;


/**
 * Created by Farina on 22/10/2015.
 */
//TODO Implementar Padrão ViewHolder depois
public class ExpandableAssociatesListsAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private ArrayList<AssociatesModel> mAssociatesModel;

    public ExpandableAssociatesListsAdapter(Context context, ArrayList<AssociatesModel> associatesModel) {
        this.mContext = context;
        this.mAssociatesModel = associatesModel;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return mAssociatesModel.get(groupPosition).getAssociatesListItemModel().get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.default_3line_fav_item, null);
        }

        AssociatesListItemModel associatesListItemModel = (AssociatesListItemModel)
                getChild(groupPosition, childPosition);

        TextView title = (TextView) convertView.findViewById(R.id.default_3line_fav_item_tv_principal);
        title.setText(associatesListItemModel.getTitle());

        TextView info1 = (TextView) convertView.findViewById(R.id.default_3line_fav_item_tv_info1);
        info1.setText(associatesListItemModel.getInfo1());

        TextView info2 = (TextView) convertView.findViewById(R.id.default_3line_fav_item_tv_info2);
        info2.setText(associatesListItemModel.getInfo2());

        //ImageView photo = (ImageView) convertView.findViewById(R.id.default_3line_fav_item_iv_icon);
        //Settar foto depois

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mAssociatesModel.get(groupPosition).getAssociatesListItemModel().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.mAssociatesModel.get(groupPosition).getAssociatesListHeaderModel();
    }

    @Override
    public int getGroupCount() {
        return this.mAssociatesModel.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.expandable_list_header, null);
        }

        AssociatesListHeaderModel associatesListHeaderModel =
                (AssociatesListHeaderModel) getGroup(groupPosition);

        TextView headerLabel = (TextView) convertView
                .findViewById(R.id.expandable_list_header_tv_headerTitle);
        headerLabel.setText(associatesListHeaderModel.getLabel());
        headerLabel.setTag(groupPosition);

        ImageView headerIcon = (ImageView) convertView
                .findViewById(R.id.expandable_list_header_iv_headerIcon);
        headerIcon.setImageResource(associatesListHeaderModel.getIconId());

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }



}
