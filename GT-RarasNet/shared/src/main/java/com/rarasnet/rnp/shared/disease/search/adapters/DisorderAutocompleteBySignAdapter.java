package com.rarasnet.rnp.shared.disease.search.adapters;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import com.rarasnet.rnp.shared.R;
import com.rarasnet.rnp.shared.disease.search.models.Sign;
import com.rarasnet.rnp.shared.disease.search.models.persistence.JsonSign;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ronnyery Barbosa on 23/11/2015.
 */
public class DisorderAutocompleteBySignAdapter extends  ArrayAdapter<String>{

    public interface AutocompleteListener {
        void onStartFiltering();
        void onStopFiltering();
    }

    private AutocompleteListener mAutocompleteListener;

    public void setAutocompleteListener(AutocompleteListener mAutocompleteListener) {
        this.mAutocompleteListener = mAutocompleteListener;
    }

    private String searchOption = "";
        private List<String> suggestions;

        public DisorderAutocompleteBySignAdapter(Context context, AutocompleteListener autocompleteListener) {
            super(context, R.layout.default_autocomplete_item,
                    R.id.default_autocomplete_item_tv_suggestion);
            suggestions = new ArrayList<String>();
            mAutocompleteListener = autocompleteListener;
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
                    FilterResults filterResults = new FilterResults();
                    JsonSign jp = new JsonSign();
                   
                        //faz pesquisa em tempo de execução após 3 caracteres
                        if (constraint != null) {
                            // cosulta o webservice e retorna uma lista de objetos
                            List<Sign> new_suggestions = null;
                            try {
                                mAutocompleteListener.onStartFiltering();
                                new_suggestions = jp.search(constraint.toString(), searchOption);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            suggestions.clear();

                            for (int i = 0; i < new_suggestions.size(); i++) {
                                suggestions.add(new_suggestions.get(i).getName());
                                // Log.d("ArrayAdapterItem", new_suggestions.get(i).getId());
                            }
                            // Atribui valores aos FilterResults
                            // object
                            filterResults.values = suggestions;
                            filterResults.count = suggestions.size();
                            mAutocompleteListener.onStopFiltering();
                        }

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






    }


