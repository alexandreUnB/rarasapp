package com.rarasnet.rnp.shared.disease.profile;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.rarasnet.rnp.shared.R;
import com.rarasnet.rnp.shared.disease.search.adapters.DisorderProfilePagerAdapter;
import com.rarasnet.rnp.shared.disease.search.models.DisorderProfile;

/**
 * Created by Farina on 5/6/2015.
 */
public class DisorderProfileActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {

    private static final String EXTRA_DISORDER_NAME = "disorder_name";
    public static final String EXTRA_DISORDER_PROFILE = "disorder_profile";

    public static Intent getIntent(Context context, DisorderProfile disorderProfile) {
        Intent it = new Intent(context, DisorderProfileActivity.class);
        mDisorderProfile = disorderProfile;
        return it;
    }

    public static DisorderProfile mDisorderProfile;
    private GoogleApiClient mGoogleApi;
    private LatLngBounds mBounds;
    public static Location mLastLocation;
    public static FragmentManager mFragmentManager;
    private Toolbar mToolbar;
    private int [] tabIcons = {
            R.drawable.ic_description_white_24dp,
            R.drawable.ic_event_note_white_24dp,
//            R.drawable.ic_business_white_24dp,
//            R.drawable.ic_view_list_white_24dp,
            R.drawable.ic_poll_white_24dp
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("[DisPAct]AFTER CLICK", "AFTER CLICK");
        setContentView(R.layout.activity_disorder_profile);
        mToolbar = (Toolbar) findViewById(R.id.act_disorder_profile_tb_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Informações da Doença");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.act_disorder_profile_vp_pager);
        viewPager.setAdapter(new DisorderProfilePagerAdapter(getSupportFragmentManager(),
                this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.act_disorder_profile_tl_slidingTabs);
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setIcon(tabIcons[i]);
        }

        mFragmentManager = getSupportFragmentManager();
        mGoogleApi = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();


        TextView tv_toolbarTitle = (TextView) findViewById(R.id.act_disorder_profile_tv_toolbarTitle);
        tv_toolbarTitle.setText(mDisorderProfile.getDisorder().getName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_disorder_profile, menu);
        //MenuItem searchItem = menu.findItem(R.id.action_favoritar);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home) {
            finish();
        }

        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApi);
        if (mLastLocation != null) {
            mBounds = calculateLatLngBounds();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private LatLngBounds calculateLatLngBounds() {
        LatLngBounds bounds = LatLngBounds.builder().include(new LatLng(mLastLocation.getLatitude(),
                mLastLocation.getLongitude())).build();
        return bounds;
    }



}
