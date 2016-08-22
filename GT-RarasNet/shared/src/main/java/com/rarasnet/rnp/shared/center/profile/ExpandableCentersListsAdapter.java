package com.rarasnet.rnp.shared.center.profile;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rarasnet.rnp.shared.R;
import com.rarasnet.rnp.shared.disease.profile.associates.AssociatesListHeaderModel;
import com.rarasnet.rnp.shared.disease.profile.associates.AssociatesListItemModel;

import java.util.ArrayList;
import java.util.Random;


/**
 * Created by Farina on 22/10/2015.
 */
//TODO Implementar Padrão ViewHolder depois
public class ExpandableCentersListsAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private ArrayList<CenterModel> mAssociatesModel;

    public ExpandableCentersListsAdapter(Context context, ArrayList<CenterModel> associatesModel) {
        this.mContext = context;
        this.mAssociatesModel = associatesModel;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        Log.d("foi 1", mAssociatesModel.get(groupPosition).getAssociatesListItemModel().get(childPosititon).getTitle());
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




        ImageView view = (ImageView) convertView.findViewById(R.id.avatar);
        TextView title = (TextView) convertView.findViewById(R.id.default_3line_fav_item_tv_principal);
        TextView info1 = (TextView) convertView.findViewById(R.id.default_3line_fav_item_tv_info1);
        TextView info2 = (TextView) convertView.findViewById(R.id.default_3line_fav_item_tv_info2);
       // Drawable res;
        //view.setImageResource(getRandomGirlDrawable());
        //view.setImageDrawable();


        title.setText(associatesListItemModel.getTitle());
        if(associatesListItemModel.getTipo() !="profi") {

            if (associatesListItemModel.getInfo1() != null) {
                info1.setText("Orphanumber " + associatesListItemModel.getInfo1());
            } else {
                info1.setText("Orphanumber: não cadastrado");
            }


            if (associatesListItemModel.getInfo2() != null) {
                info2.setText("CID: " + associatesListItemModel.getInfo2());
            } else {
                info2.setText("CID: CID não cadastrado");
            }
        }else{

            if (associatesListItemModel.getInfo1() != null) {
                info1.setText("Cidade: " + associatesListItemModel.getInfo1());
            } else {
                info1.setText("Cidade: não cadastrada");
            }


            if (associatesListItemModel.getInfo2() != null) {
                info2.setText("Profissão: " + associatesListItemModel.getInfo2());
            } else {
                info2.setText("Profissão: não cadastrado");
            }

        }

        //ImageView photo = (ImageView) convertView.findViewById(R.id.default_3line_fav_item_iv_icon);
        //Settar foto depois
        //


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



    public String getItemname(int groupPosition, int position) {
        Log.d("aqui", "getItemname: ");

        AssociatesListItemModel associatesListItemModel = (AssociatesListItemModel)
                getChild(groupPosition, position);
        //AssociatesListItemModel associatesModel = mAssociatesModel.get(groupPosition).getAssociatesListItemModel().get(position);
        return associatesListItemModel.getTitle();
    }

    private int getRandomGirlDrawable() {
        Random rnd = new Random();
        switch (rnd.nextInt(8)) {
            default:
            case 0:
                return R.drawable.ic_no_profile_picture;


        }
    }
}
