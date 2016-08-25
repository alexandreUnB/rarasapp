package com.rarasnet.rnp.shared.laws;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.rarasnet.rnp.shared.R;
import com.rarasnet.rnp.shared.profissionais.controllers.network.responses.SearchProfissionaisDataResponse;
import com.rarasnet.rnp.shared.profissionais.controllers.tasks.SearchProfissionaisTask;
import com.rarasnet.rnp.shared.protocol.ProtocolProfileModel;

import java.util.ArrayList;
import java.util.List;


import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.rarasnet.rnp.shared.R;
import com.rarasnet.rnp.shared.profissionais.controllers.network.SearchProfessioanlsAutocomplete;
import com.rarasnet.rnp.shared.profissionais.controllers.network.SugegetionProfifissionaisAdapter;
import com.rarasnet.rnp.shared.profissionais.controllers.network.responses.ProfissionaisAdapter;
import com.rarasnet.rnp.shared.profissionais.controllers.network.responses.SearchProfissionaisDataResponse;
import com.rarasnet.rnp.shared.profissionais.controllers.tasks.SearchProfissionaisTask;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Lucas Vieira on 22/08/2016.
 */
public class LawAutocompleteAdapter extends ArrayAdapter<String> {

    public interface AutocompleteListener {
        void onStartFiltering();
        void onStopFiltering();
    }

    private static final String TYPE_NAME = "nome";
    private static final String TYPE_ORPHANUMBER = "orphanumber";
    private static final String TYPE_ICD = "icd";
    //Numeric Pattern
    private static final String orphanumberPattern = "[0-9]+";
    private static final String icdPattern = "[A-Z][0-9][0-9].[0-9]";
    private List<String> suggestions;
    // String searchOption ;

    private AutocompleteListener mAutocompleteListener;

    private int mResourceId = R.layout.default_autocomplete_item;

    //add dropdown no autocomplete
    public LawAutocompleteAdapter(Activity context, AutocompleteListener listener) {
        super(context, R.layout.default_autocomplete_item);
        suggestions = new ArrayList<String>();
        mAutocompleteListener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            convertView = inflater.inflate(R.layout.default_autocomplete_item, parent, false);
            holder = new Holder();
            holder.suggestions = (TextView) convertView.findViewById(R.id.default_autocomplete_item_tv_suggestion);
            convertView.setTag(holder);
        }
        else {
            holder = (Holder) convertView.getTag();
        }
        holder.suggestions.setText(suggestions.get(position));

        return convertView;
    }

    @Override
    public int getCount() {
        return suggestions.size();
    }

    @Override
    public String getItem(int index) {
        return suggestions.get(index);
    }



    @Override
    public Filter getFilter() {
        Filter myFilter = new Filter() {
            @Override
            //Capture Characters on execution
            protected FilterResults performFiltering(CharSequence constraint) {
                mAutocompleteListener.onStartFiltering();
                FilterResults filterResults = new FilterResults();

                if (constraint != null) {
                    LawProfileModel protocols = new LawProfileModel();
                    List<String> newSuggestion = new ArrayList<>();

                    try {
                        newSuggestion = protocols.autoComplete(constraint.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    suggestions.clear();

                    if(newSuggestion != null && !newSuggestion.isEmpty()) {
                        for (String nome : newSuggestion) {
                            suggestions.add(nome);
                        }
                    }

                    filterResults.values = suggestions;
                    filterResults.count = suggestions.size();
                }
                mAutocompleteListener.onStopFiltering();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence contraint,
                                          FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return myFilter;
    }


    public static class Holder {
        TextView suggestions;
    }

    private SearchProfissionaisTask.SearchProfissionaisCallback searchProfissionaisCallback =
            new SearchProfissionaisTask.SearchProfissionaisCallback() {
                @Override
                public void onSearchProfissionaisTaskCallback(SearchProfissionaisDataResponse[] response) {
                    if(response != null) {
                        //  pDialog.dismiss();
                        //renderProtocolsList(new ArrayList<>(Arrays.asList(response)));

                        // mAdapter.update(new ArrayList<>(Arrays.asList(response)));
                        //  bind();
                    }

                }
            };




}