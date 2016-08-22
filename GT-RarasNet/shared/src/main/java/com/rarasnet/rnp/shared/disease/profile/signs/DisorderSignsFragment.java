package com.rarasnet.rnp.shared.disease.profile.signs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rarasnet.rnp.shared.R;
import com.rarasnet.rnp.shared.disease.profile.DisorderProfileActivity;
import com.rarasnet.rnp.shared.disease.search.models.Sign;

import java.util.ArrayList;


/**
 * Created by Farina on 23/10/2015.
 */
public class DisorderSignsFragment extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";

    private RelativeLayout mView;
    private RecyclerView mRecyclerView;
   // List<Professionals>
    private SignsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView qnt;

    private int mPage;

    public static DisorderSignsFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        DisorderSignsFragment fragment = new DisorderSignsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }



        RelativeLayout view  = (RelativeLayout) inflater.inflate(R.layout.fragment_disorder_signs,
                container, false);


        mView = view;
        qnt = (TextView) view.findViewById(R.id.frag_disorder_signs_tv_signsQnt);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.frag_disorder_signs_rv_signs);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new SignsAdapter(getDisorderSigns(), getDisorderID() );
        qnt.setText(getDisorderSignsCount());

        mAdapter.setOnItemClickListener(new SignsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Sign item) {

            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        return mView;
    }


    private ArrayList<Sign> getDisorderSigns() {
        return new ArrayList<>(DisorderProfileActivity.mDisorderProfile.getSigns());
    }

    private String getDisorderID() {
        return DisorderProfileActivity.mDisorderProfile.getDisorder().getDesorden_id();
    }

    private String getDisorderSignsCount() {
        return Integer.toString(DisorderProfileActivity.mDisorderProfile.getSignCounter());
    }
}
