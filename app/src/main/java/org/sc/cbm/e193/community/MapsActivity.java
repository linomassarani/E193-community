package org.sc.cbm.e193.community;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.sc.cbm.e193.community.dao.HazardFlagDAO;
import org.sc.cbm.e193.community.pojo.HazardFlag;

import java.util.List;

public class MapsActivity extends ActionBarActivity {

    private static final float INITIAL_ZOOM = 7.510545253753662f;
    private static final LatLng INITIAL_LAT_LNG = new LatLng(-27.70261624298937, -48.56094427406788);
    private static final float INITIAL_TILT = 45f;
    private static final float INITIAL_BEARING = 0f;

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private LinearLayout mMapView;
    private List<HazardFlag> mHazardFlags;
    private ImageView mImageViewGrayBG;
    private ImageView mImageViewSub;
    private ImageView mImageViewHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_maps);

        mImageViewGrayBG = (ImageView) findViewById(R.id.imageViewGrayBack);
        mImageViewSub = (ImageView) findViewById(R.id.imageViewSub);
        mImageViewHelp = (ImageView) findViewById(R.id.imageViewHelp);

        mImageViewGrayBG.setVisibility(View.GONE);
        mImageViewSub.setVisibility(View.GONE);

        mImageViewHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mImageViewGrayBG.setVisibility(View.VISIBLE);
                mImageViewSub.setVisibility(View.VISIBLE);
            }
        });

        mImageViewSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mImageViewGrayBG.setVisibility(View.GONE);
                mImageViewSub.setVisibility(View.GONE);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mHazardFlags = HazardFlagDAO.getAllFlags();
        for(HazardFlag hf : mHazardFlags) {
            mMap.addMarker(new MarkerOptions()
                    .position(hf.getLatLng())
                    .title("")
                    .icon(BitmapDescriptorFactory.fromResource(getFlag(hf.getColor())))
            );
        }

        setInitialCameraPosition();
//        mMap.getUiSettings().setZoomControlsEnabled(true);

    }

    private void setInitialCameraPosition() {
        LatLng initialLatLng= INITIAL_LAT_LNG;
        float initialZoom = INITIAL_ZOOM;
        float initialBearing = INITIAL_BEARING;
        float initialTilt = INITIAL_TILT;

        SharedPreferences sp = getPreferences(MODE_PRIVATE);

        if(sp.contains(getString(R.string.initial_lat)) &&
                sp.contains(getResources().getString(R.string.initial_lng))) {
            String strInitialLat = sp.getString(getResources().getString(R.string.initial_lat), "");
            String strInitialLng = sp.getString(getResources().getString(R.string.initial_lng), "");

            initialLatLng = new LatLng(Double.parseDouble(strInitialLat), Double.parseDouble(strInitialLng));
        }

        if(sp.contains(getString(R.string.initial_zoom))) {
            String strInitialZoom = sp.getString(getResources().getString(R.string.initial_zoom), "");

            initialZoom = Float.parseFloat(strInitialZoom);
        }

        if(sp.contains(getString(R.string.initial_tilt))) {
            String strInitialTilt = sp.getString(getResources().getString(R.string.initial_tilt), "");

            initialTilt = Float.parseFloat(strInitialTilt);
        }

        if(sp.contains(getString(R.string.initial_bearing))) {
            String strInitialBearing = sp.getString(getResources().getString(R.string.initial_bearing), "");

            initialBearing = Float.parseFloat(strInitialBearing);
        }

        CameraPosition newPosition = new CameraPosition(initialLatLng, initialZoom, initialTilt, initialBearing);
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(newPosition));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //saving current map state to future use
        SharedPreferences.Editor ed = getPreferences(MODE_PRIVATE).edit();
        CameraPosition cp = mMap.getCameraPosition();

        ed.putString(getString(R.string.initial_lat), String.valueOf(cp.target.latitude));
        ed.putString(getString(R.string.initial_lng), String.valueOf(cp.target.longitude));
        ed.putString(getString(R.string.initial_tilt), String.valueOf(cp.tilt));
        ed.putString(getString(R.string.initial_bearing), String.valueOf(cp.bearing));
        ed.putString(getString(R.string.initial_zoom), String.valueOf(cp.zoom));
        ed.commit();
    }

    private int getFlag(HazardFlag.Color color) {
        switch(color) {
            case GREEN:
                return R.drawable.ic_greenflag;
            case YELLOW:
                return R.drawable.ic_yellowflag;
            case RED:
                return R.drawable.ic_redflag;
            default:
                return R.drawable.ic_blackflag;
        }
    }
}
