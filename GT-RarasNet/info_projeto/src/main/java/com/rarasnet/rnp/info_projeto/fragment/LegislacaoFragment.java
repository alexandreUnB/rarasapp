package com.rarasnet.rnp.info_projeto.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ronnyerybarbosa.library.MaterialDialog;
import com.rarasnet.rnp.documentos.interfaces.RecyclerViewOnClickListenerHack;
import com.rarasnet.rnp.info_projeto.R;
import com.rarasnet.rnp.info_projeto.adapters.LegislacaoAdapter;
import com.rarasnet.rnp.info_projeto.model.Legislacao;

import java.util.List;


public class LegislacaoFragment extends Fragment implements RecyclerViewOnClickListenerHack {

    private RecyclerView mRecyclerView;
    private List<Legislacao> mList;

    MaterialDialog mMaterialDiaolg;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }


        });


      LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);


        mList = ((com.rarasnet.rnp.info_projeto.MainActivity) getActivity()).getSetlegiscao();
        LegislacaoAdapter adapter = new LegislacaoAdapter(getActivity(), mList);
        adapter.setRecyclerViewOnClickListenerHack(this);
        mRecyclerView.setAdapter(adapter);


        return view;
    }

    @Override
    public void onClickListener(View view, int position) {


        init(view);
        show("Visualizar Legislacao", "Deseja  visualizar protocolo(pdf) de " + mList.get(position).getmName(),"", mList.get(position).getLegislacao(), mList.get(position).getId());


     //   adapter.removeListItem(position);
    }



    public void init(View v) {
        mMaterialDiaolg= new MaterialDialog(getActivity());

        // Toast.makeText(getActivity(), "Initializes successfully.",
        //   Toast.LENGTH_SHORT).show();
    }
    public void show(String Title,String Mensagem, String Complemeto,  final String protocolo, final String id) {
        Log.d("aqui", "testando");
        if (mMaterialDiaolg != null) {

            mMaterialDiaolg.setTitle(Title).setMessage(Mensagem + " " + Complemeto + "?")
                    .setPositiveButton("SIM", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                          //  downloadPdf(protocolo, id);
                            Toast.makeText(getActivity(),"Carregando PDF... ", Toast.LENGTH_SHORT).show();


                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://rederaras.org/webservice/app/webroot/files/dados_nacionai/legislacao/" + id + "/" + protocolo)));



                            // Toast.makeText(getActivity(), "SIM", Toast.LENGTH_LONG).show();
                        }
                    }).setNegativeButton("NÃO", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMaterialDiaolg.dismiss();
                    // Toast.makeText(getActivity(), "Cancel", Toast.LENGTH_LONG).show();
                }
            }).setCanceledOnTouchOutside(false)

                    .setOnDismissListener(

                            new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    // Toast.makeText(getActivity(), "",
                                    // Toast.LENGTH_SHORT).show();
                                }
                            }).show();

        } else {
            Toast.makeText(getActivity(), "You should init firstly!",
                    Toast.LENGTH_SHORT).show();
        }

        Log.d("aqui", "testando2");
    }


}
