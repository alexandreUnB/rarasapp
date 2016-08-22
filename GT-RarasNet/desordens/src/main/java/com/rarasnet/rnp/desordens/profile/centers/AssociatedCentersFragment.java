package com.rarasnet.rnp.desordens.profile.centers;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rarasnet.rnp.desordens.R;
import com.rarasnet.rnp.desordens.profile.DisorderProfileActivity;
import com.rarasnet.rnp.desordens.search.models.DisorderProfile;
import com.rarasnet.rnp.shared.models.Center;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Farina on 16/10/2015.
 */
public class AssociatedCentersFragment extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";
    private RelativeLayout mView;
    private RelativeLayout mCentersListFrame;

    private FloatingActionButton mExpandMapButton;
    private boolean isMapExpanded = false;

    private RecyclerView mRecyclerView;
    private CentersAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    TextView centrosQnt;

    private List<Center> center =DisorderProfileActivity.mDisorderProfile.getCenter();
    private CentersAdapter.OnItemClickListener mOnItemClickListener = new CentersAdapter.OnItemClickListener() {

        @Override
        public void onItemClick(AssociatedCenter item) {

        }
    };

    private GoogleMap mMap;
    private MapView mMapView;
    private int mPage;

    public static AssociatedCentersFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        AssociatedCentersFragment fragment = new AssociatedCentersFragment();
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

        RelativeLayout view  = (RelativeLayout) inflater.inflate(R.layout.fragment_associated_centers,
                container, false);
        mView = view;
        mCentersListFrame = (RelativeLayout) mView.findViewById(R.id.fragment_associated_centers_rl_frameList);
        //instancia Map
        /*mMapView = (MapView) mView.findViewById(R.id.fragment_associated_centers_mp_centersLocation);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        centrosQnt = (TextView) mView.findViewById(R.id.frag_disorder_associates_tv_associatesQnt);
        centrosQnt.setText(String.valueOf(DisorderProfileActivity.mDisorderProfile.getCenter().size()));
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.fragment_associated_centers_rv_listaCentros);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new CentersAdapter(getAssociatedCenters());
        mAdapter.setOnItemClickListener(mOnItemClickListener);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
       // mMap = mMapView.getMap();

       // if (mMap != null)
         //   setUpMap();

      /*  mExpandMapButton = (FloatingActionButton) mView.findViewById(R.id.fragment_associated_centers_ab_zoom);
        mExpandMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isMapExpanded) {
                    mCentersListFrame.setVisibility(View.INVISIBLE);
                    isMapExpanded = true;
                    mMapView.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
                    RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) mExpandMapButton.getLayoutParams();
                    rlp.addRule(RelativeLayout.CENTER_IN_PARENT, 0);
                    rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                } else {
                    mCentersListFrame.setVisibility(View.VISIBLE);
                    isMapExpanded = false;
                    mMapView.getLayoutParams().height = 0;
                    RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) mExpandMapButton.getLayoutParams();
                    rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0);
                    rlp.addRule(RelativeLayout.CENTER_IN_PARENT);
                }
                mView.requestLayout();
            }
        });*/
        return view;
    }


    private void setUpMap() {
        // For showing a move to my loction button
        mMap.setMyLocationEnabled(true);
        double latitude;
        double longitude;
        if (DisorderProfileActivity.mLastLocation != null) {
            latitude = DisorderProfileActivity.mLastLocation.getLatitude();
            longitude = DisorderProfileActivity.mLastLocation.getLongitude();
            // For dropping a marker at a point on the Map
            mMap.addMarker(new MarkerOptions().position(new LatLng(
                    latitude, longitude)).title("Oi").snippet("Estou aqui"));
            // For zooming automatically to the Dropped PIN Location
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,
                    longitude), 12.0f));
        }

    }

    private ArrayList<AssociatedCenter> getAssociatedCenters() {
        ArrayList<AssociatedCenter> centers = new ArrayList<>();
        for(Center c : center){
            AssociatedCenter i = new AssociatedCenter(R.drawable.ic_business_white_24dp,c.getNome(),c.getCidade(),"00");
            centers.add(i);

        }
       /* centers.add(new AssociatedCenter(R.drawable.ic_business_white_24dp, "Prontonorte", "Asa Norte", "0000"));
        centers.add(new AssociatedCenter(R.drawable.ic_business_white_24dp, "Prontonorte", "Asa Norte", "0000"));
        centers.add(new AssociatedCenter(R.drawable.ic_business_white_24dp, "Prontonorte", "Asa Norte", "0000"));
        centers.add(new AssociatedCenter(R.drawable.ic_business_white_24dp, "Prontonorte", "Asa Norte", "0000"));
        centers.add(new AssociatedCenter(R.drawable.ic_business_white_24dp, "Prontonorte", "Asa Norte", "0000"));
        centers.add(new AssociatedCenter(R.drawable.ic_business_white_24dp, "Prontonorte", "Asa Norte", "0000"));
        centers.add(new AssociatedCenter(R.drawable.ic_business_white_24dp, "Prontonorte", "Asa Norte", "0000"));
        centers.add(new AssociatedCenter(R.drawable.ic_business_white_24dp, "Prontonorte", "Asa Norte", "0000"));
        centers.add(new AssociatedCenter(R.drawable.ic_business_white_24dp, "Prontonorte", "Asa Norte", "0000"));
        centers.add(new AssociatedCenter(R.drawable.ic_business_white_24dp, "Prontonorte", "Asa Norte", "0000"));*/

        return centers;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
      //  mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
       // mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        //mMapView.onLowMemory();
    }
}
