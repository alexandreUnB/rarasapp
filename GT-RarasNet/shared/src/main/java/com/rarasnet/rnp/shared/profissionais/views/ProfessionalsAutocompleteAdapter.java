package com.rarasnet.rnp.shared.profissionais.views;

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

//import com.rarasnet.rnp.profissionais.controllers.network.SearchProfessioanlsAutocomplete;

/**
 * Created by Ronnyery Barbosa on 06/11/2015.
 */
public class ProfessionalsAutocompleteAdapter extends ArrayAdapter<String> {

    public interface AutocompleteListener {
        void onStartFiltering();
        void onStopFiltering();
    }

    //private ListaProfissionaisAdapter mAdapter;
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
    public ProfessionalsAutocompleteAdapter(Activity context, AutocompleteListener listener) {
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

//                SearchProfessioanlsAutocomplete searchProfessioanlsAutocomplete = new SearchProfessioanlsAutocomplete();
//
//                SugegetionProfifissionaisAdapter teste = new SugegetionProfifissionaisAdapter();
                //faz pesquisa em tempo de execução após 3 caracteres
                if (constraint != null) {
                    // cosulta o webservice e retorna uma lista de objetos
                    ProfissionaisAdapter professionals = new ProfissionaisAdapter();;
                    List<String> newSuggestion = new ArrayList<>();

//                    SearchProfissionaisDataResponse[] new_suggestions = null;
//                    JSONObject.quote(constraint.toString());
//                    searchOption = getSearchType("nome");
                    //searchOption = nome;
                    //new SearchProfissionaisTask(searchProfissionaisCallback).execute(constraint.toString(),searchOption);

                    try {
                        newSuggestion = professionals.autoComplete(constraint.toString(), "nome");
//                        Log.d("task","task");
//                       new_suggestions = teste.searchProf(constraint.toString(), "nome");

                        //new_suggestions = searchProfessioanlsAutocomplete.getSuggestions(constraint.toString(), "nome");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    suggestions.clear();
//                    if(new_suggestions != null) {
//                        for (SearchProfissionaisDataResponse suggestion : new_suggestions) {
//                            suggestions.add(suggestion.getNome());
//                        }
//                    }
                    if(!newSuggestion.isEmpty()) {
                        for (String nome : newSuggestion) {
                            suggestions.add(nome);
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