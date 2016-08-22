package com.rarasnet.rnp.shared.center.views;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.rarasnet.rnp.shared.R;
import com.rarasnet.rnp.shared.center.controllers.network.SearchCentersAutocomplete;
import com.rarasnet.rnp.shared.center.controllers.network.SugegetionCentersAdapter;
import com.rarasnet.rnp.shared.center.controllers.network.responses.SearchCentersDataResponse;
import com.rarasnet.rnp.shared.center.controllers.tasks.SearchCentersTask;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

//import com.rarasnet.rnp.profissionais.controllers.network.SearchProfessioanlsAutocomplete;

/**
 * Created by Ronnyery Barbosa on 06/11/2015.
 */
public class CentersAutocompleteAdapter extends ArrayAdapter<String> {

    public interface AutocompleteListener {
        void onStartFiltering();
        void onStopFiltering();
    }

   // private ListaCentersAdapter mAdapter;
    private static final String TYPE_NAME = "nome";
    private static final String TYPE_ORPHANUMBER = "orphanumber";
    private static final String TYPE_ICD = "icd";
    private String searchOption;
    //Numeric Pattern
    private static final String orphanumberPattern = "[0-9]+";
    private static final String icdPattern = "[A-Z][0-9][0-9].[0-9]";

    protected static final String TAG = "DisordersAutocompleteAdapter";
    private List<String> suggestions;
   // String searchOption ;

    private AutocompleteListener mAutocompleteListener;

    private int mResourceId = R.layout.default_autocomplete_item;

    //add dropdown no autocomplete
    public CentersAutocompleteAdapter(Activity context, AutocompleteListener listener) {
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
            //captura caracter em tempo de execução
            protected FilterResults performFiltering(CharSequence constraint) {
                mAutocompleteListener.onStartFiltering();
                FilterResults filterResults = new FilterResults();

                SearchCentersAutocomplete searchCentersAutocomplete = new SearchCentersAutocomplete();

                SugegetionCentersAdapter sugegetionCentersAdapter = new SugegetionCentersAdapter();
                //faz pesquisa em tempo de execução após 3 caracteres
                if (constraint != null) {
                    // cosulta o webservice e retorna uma lista de objetos
                    JSONObject.quote(constraint.toString());
                    SearchCentersDataResponse[] new_suggestions = null;
                    searchOption = getSearchType("nome");
                    //searchOption = nome;
                    //new SearchProfissionaisTask(searchProfissionaisCallback).execute(constraint.toString(),searchOption);
                    try {
                        // Convert from Unicode to UTF-8
                        String string = "\u00f3";
                        byte[] utf8 = constraint.toString().getBytes("UTF-8");
                        Log.d("veento",string);

                        // Convert from UTF-8 to Unicode
                        string = new String(utf8, "UTF-8");
                        Log.d("veento1",string);
                    } catch (UnsupportedEncodingException e) {
                    }

                    try {
                        Log.d("task","task");
                       new_suggestions = sugegetionCentersAdapter.searchCenter(constraint.toString(), "nome");

                       //new_suggestions = searchProfessioanlsAutocomplete.getSuggestions(constraint.toString(), "nome");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    suggestions.clear();
                    if(new_suggestions != null) {
                        for (SearchCentersDataResponse suggestion : new_suggestions) {
                            suggestions.add(suggestion.getNome());
                        }
                    }

                    // Atribui valores aos FilterResults
                    // object
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



    private String getSearchType(String query) {

        if (query.matches(orphanumberPattern)) {
            //Toast.makeText(Diseases.this, "TIPO ORPHA" ,Toast.LENGTH_SHORT).show();
            return TYPE_ORPHANUMBER;
        } else if (query.matches(icdPattern)) {
            //Toast.makeText(Diseases.this, "TIPO ICD" ,Toast.LENGTH_SHORT).show();
            return TYPE_ICD;
        } else {
            //Toast.makeText(Diseases.this, "TIPO NAME" ,Toast.LENGTH_SHORT).show();
            return TYPE_NAME;
        }
    }

    public static class Holder {
        TextView suggestions;
    }

    private SearchCentersTask.SearchCentersCallback searchCentersCallback =
            new SearchCentersTask.SearchCentersCallback() {
                @Override
                public void onSearchCentersTaskCallback(SearchCentersDataResponse[] response) {
                    if (response != null) {
                        //  pDialog.dismiss();
                        //renderProtocolsList(new ArrayList<>(Arrays.asList(response)));

                        //mAdapter.update(new ArrayList<>(Arrays.asList(response)));
                    }
                }
            };






}