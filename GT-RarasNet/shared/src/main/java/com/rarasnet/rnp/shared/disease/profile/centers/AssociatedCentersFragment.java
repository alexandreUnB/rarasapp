package com.rarasnet.rnp.shared.disease.profile.centers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.Toast;

import com.example.ronnyerybarbosa.library.MaterialDialog;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rarasnet.rnp.shared.R;

import com.rarasnet.rnp.shared.disease.profile.DisorderProfileActivity;
import com.rarasnet.rnp.shared.models.Center;
import com.rarasnet.rnp.shared.models.CenterProfile;
import com.rarasnet.rnp.shared.models.CenterProfileModel;
import com.rarasnet.rnp.shared.util.UIUtils;

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

    MaterialDialog mMaterialDiaolg;

    private RecyclerView mRecyclerView;
    private CentersAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private AlertDialog progress;

    TextView centrosQnt;

    private List<Center> center = DisorderProfileActivity.mDisorderProfile.getCenter();
    private CentersAdapter.OnItemClickListener mOnItemClickListener = new CentersAdapter.OnItemClickListener() {

        @Override
        public void onItemClick(AssociatedCenter item) {

              //  init(getView());
             //   Log.d("id",String.valueOf(item.getNome()));

                //show("Carregar Centro", "Deseja carregar " + item.getNome(), "", "center",
                     //   String.valueOf(item.getId()));
            SearchProfileCenterTask searchTask = new SearchProfileCenterTask();
            load(false);
            //Log.d("Profi id antes ", id);
            searchTask.execute(item.getId(), "id");


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
            AssociatedCenter i = new AssociatedCenter(R.drawable.ic_business_white_24dp, c.getName(),
                    c.getCity()+"-"+c.getUf(), c.getCnes(), c.getTipo(), c.getId());
            centers.add(i);

        }


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

        public void init(View v) {
            mMaterialDiaolg= new MaterialDialog(getActivity());

            // Toast.makeText(getActivity(), "Initializes successfully.",
            //   Toast.LENGTH_SHORT).show();
        }
    public void show(String Title,String Mensagem, String Complemeto, final String tipo, final String id) {
        Log.d("aqui", "testando");
        if (mMaterialDiaolg != null) {

            mMaterialDiaolg.setTitle(Title).setMessage(Mensagem + " " + Complemeto + "?")
                    .setPositiveButton("SIM", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mMaterialDiaolg.dismiss();

                                SearchProfileCenterTask searchTask = new SearchProfileCenterTask();
                                Log.d("Profi id antes ", id);
                                searchTask.execute(id, "id");


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

    class SearchProfileCenterTask extends AsyncTask<String, String, CenterProfile> {


        @Override
        protected com.rarasnet.rnp.shared.models.CenterProfile doInBackground(String... params) {


            String userInput = params[0];
            String searchType = params[1];
            CenterProfileModel professionalProfileModel = new CenterProfileModel();
            com.rarasnet.rnp.shared.models.CenterProfile result = null;

            try {
                result = professionalProfileModel.getProfileNew(userInput, searchType);
                //List<Disease> result = disorders.getStaticDisease();
            } catch (Exception e) {

            }
            return result;

            // return profile;
        }

        protected void onPostExecute(com.rarasnet.rnp.shared.models.CenterProfile profissionaisDataResponses){

            //pb_loadingProfissionalsData.setVisibility(View.INVISIBLE);
            Intent intent = com.rarasnet.rnp.shared.center.profile.CenterProfile.getIntent(getActivity(), profissionaisDataResponses);
            load(true);
            startActivity(intent);
        }
    }

    public void load(Boolean j){
        if(j) {
            Log.d("done","done");

            progress.cancel();


        }else{

            // UIUtils.getProgressDialog(SearchDisordersActivity.this);
            progress= UIUtils.getProgressDialog(getActivity());
            // progress.requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);

            //progress.getWindow().setLayout(UIUtils.dpToPx(200, this), UIUtils.dpToPx(125, this));
            progress.show();


        }}


}

