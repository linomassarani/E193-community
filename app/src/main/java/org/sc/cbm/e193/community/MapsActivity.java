package org.sc.cbm.e193.community;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import org.sc.cbm.e193.community.dao.LifeguardPostDAO;
import org.sc.cbm.e193.community.pojo.LifeguardTower;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends ActionBarActivity {

    private static final float INITIAL_ZOOM = 7.510545253753662f;
    private static final LatLng INITIAL_LAT_LNG = new LatLng(-27.70261624298937, -48.56094427406788);
    private static final float INITIAL_TILT = 45f;
    private static final float INITIAL_BEARING = 0f;

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private List<LifeguardTower> mLifeguardTowers;
    private ImageView mImageViewGrayBG;
    private ImageView mImageViewSub;
    private ImageView mImageViewHelp;
    private FloatingActionButton mFloatingActionButton;
    private FloatingActionMenu mFloatingActionMenu;
    private List<Marker> mFlagsMarkers;
    private List<Marker> mJelliesMarkers;
    private List<Marker> mWeatherMarkers;
    private List<Marker> mLifeguardsNumMarkers;
    private List<Marker> mCurrentMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_maps);

        mImageViewGrayBG = (ImageView) findViewById(R.id.imageViewGrayBack);
        mImageViewSub = (ImageView) findViewById(R.id.imageViewSub);
        mImageViewHelp = (ImageView) findViewById(R.id.imageViewHelp);

        mImageViewGrayBG.setVisibility(View.GONE);
        mImageViewSub.setVisibility(View.GONE);
        mImageViewHelp.setVisibility(View.GONE);

        mFlagsMarkers = new ArrayList<>();
        mJelliesMarkers = new ArrayList<>();
        mWeatherMarkers = new ArrayList<>();
        mLifeguardsNumMarkers = new ArrayList<>();

        createFloatingButton();
        setHelpListener();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setUpMapIfNeeded();
    }

    private void setHelpListener() {
        mImageViewHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFloatingActionButton.setVisibility(View.INVISIBLE);
                mImageViewGrayBG.setVisibility(View.VISIBLE);

                mImageViewSub.setScaleX(0);
                mImageViewSub.setScaleY(0);
                mImageViewSub.setVisibility(View.VISIBLE);

                PropertyValuesHolder pvhSX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1);
                PropertyValuesHolder pvhSY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(mImageViewSub, pvhSX, pvhSY);
                animation.start();
            }
        });

        mImageViewSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PropertyValuesHolder pvhSX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0);
                PropertyValuesHolder pvhSY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(mImageViewSub, pvhSX, pvhSY);
                animation.start();

                animation.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        mImageViewGrayBG.setVisibility(View.GONE);
                        mImageViewSub.setVisibility(View.GONE);
                        mFloatingActionButton.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
            }
        });
    }

    private void createFloatingButton() {
        // create a buttom to attach the menu
        // in Activity Context
        final ImageView fabIcon = new ImageView(this); // Create an icon
        fabIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_new_light));

        mFloatingActionButton = new FloatingActionButton.Builder(this)
                .setContentView(fabIcon)
                .build();

        //create menu items
        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        // repeat many times:
        ImageView itemIcon0 = new ImageView(this);
        ImageView itemIcon1 = new ImageView(this);
        ImageView itemIcon2 = new ImageView(this);
        ImageView itemIcon3 = new ImageView(this);

        itemIcon0.setImageDrawable(getResources().getDrawable(R.drawable.ic_help_menu));
        itemIcon1.setImageDrawable(getResources().getDrawable(R.drawable.ic_jellyfish));
        itemIcon2.setImageDrawable(getResources().getDrawable(R.drawable.ic_weather_cloudy_menu));
        itemIcon3.setImageDrawable(getResources().getDrawable(R.drawable.ic_blackflag_menu));


        SubActionButton sub0 = itemBuilder.setContentView(itemIcon0).build();
        SubActionButton sub1 = itemBuilder.setContentView(itemIcon1).build();
        SubActionButton sub2 = itemBuilder.setContentView(itemIcon2).build();
        SubActionButton sub3 = itemBuilder.setContentView(itemIcon3).build();

        //create the menu with the items
        mFloatingActionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(sub0, 110, 110)
                .addSubActionView(sub1, 110, 110)
                .addSubActionView(sub2, 110, 110)
                .addSubActionView(sub3, 110, 110)
                .setRadius(200)
                .attachTo(mFloatingActionButton)
                .build();
        //listeners
        mFloatingActionMenu .setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
            @Override
            public void onMenuOpened(FloatingActionMenu floatingActionMenu) {
                // Rotate the icon of rightLowerButton 45 degrees clockwise
                fabIcon.setRotation(0);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 45);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabIcon, pvhR);
                animation.start();
            }

            @Override
            public void onMenuClosed(FloatingActionMenu floatingActionMenu) {
                // Rotate the icon of rightLowerButton 45 degrees counter-clockwise
                fabIcon.setRotation(45);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 0);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabIcon, pvhR);
                animation.start();
            }
        });

        sub0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFloatingActionMenu.close(true);
                mFloatingActionButton.setVisibility(View.INVISIBLE);
                mImageViewGrayBG.setVisibility(View.VISIBLE);

                mImageViewSub.setScaleX(0);
                mImageViewSub.setScaleY(0);

//                float bkpX = mImageViewSub.getX();
//                float bkpY = mImageViewSub.getY();
//                mImageViewSub.setX(mFloatingActionButton.getX());
//                mImageViewSub.setY(mFloatingActionButton.getY());

                mImageViewSub.setVisibility(View.VISIBLE);

                PropertyValuesHolder pvhSX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1);
                PropertyValuesHolder pvhSY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1);
//                PropertyValuesHolder pvTX = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, bkpX);
//                PropertyValuesHolder pvTY = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, bkpY);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(mImageViewSub, pvhSX, pvhSY/**, pvTX, pvTY**/);
                animation.start();
            }
        });

        sub1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setMarkersVisible(mJelliesMarkers);
                mFloatingActionMenu.close(true);
            }
        });

        sub2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setMarkersVisible(mWeatherMarkers);
                mFloatingActionMenu.close(true);
            }
        });

        sub3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setMarkersVisible(mFlagsMarkers);
                mFloatingActionMenu.close(true);
            }
        });
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
            SupportMapFragment mapView = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
            mMap = (mapView).getMap();
            mMap.setPadding(0, 0,
                    getResources().getDimensionPixelSize(R.dimen.action_button_size)
                            + getResources().getDimensionPixelSize(R.dimen.action_button_margin), 0);
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
        mLifeguardTowers = LifeguardPostDAO.getAll();
        loadMarkers();

        setInitialCameraPosition();
    }

    private void loadMarkers() {
        for(LifeguardTower lt : mLifeguardTowers) {
            Marker newFlagMarker = mMap.addMarker(new MarkerOptions()
                    .position(lt.getLatLng())
                    .title("")
                    .icon(BitmapDescriptorFactory.fromResource(getFlagDrawable(lt.getHazardFlag()))));
            mFlagsMarkers.add(newFlagMarker);

            Marker newWeatherMarker = mMap.addMarker(new MarkerOptions()
                    .position(lt.getLatLng())
                    .title("")
                    .icon(BitmapDescriptorFactory.fromResource(getWeatherDrawable(lt.getWeather()))));
            mWeatherMarkers.add(newWeatherMarker);
            newWeatherMarker.setVisible(false);

            Marker newJellyMarker = mMap.addMarker(new MarkerOptions()
                    .position(lt.getLatLng())
                    .title("")
                    .icon(BitmapDescriptorFactory.fromResource(getJellyDrawable(lt.getJellyFish()))));
            mJelliesMarkers.add(newJellyMarker);
            newJellyMarker.setVisible(false);

//            Marker newLifeguardNumMarker = mMap.addMarker(new MarkerOptions()
//                    .position(lt.getLatLng())
//                    .title(String.valueOf(lt.getLifeguardsNum())));
//            mWeatherMarkers.add(newLifeguardNumMarker);
//            newLifeguardNumMarker.setVisible(false);
        }
        mCurrentMarker = mFlagsMarkers;
    }

    private void setMarkersVisible(List<Marker> list) {
        if (list.hashCode() == mCurrentMarker.hashCode())
            return;

        for(Marker m : mCurrentMarker) {
            m.setVisible(false);
        }

        for(Marker m : list) {
            m.setVisible(true);
        }

        mCurrentMarker = list;
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

    private int getFlagDrawable(LifeguardTower.HazardFlag color) {
        switch(color) {
            case GREEN:
                return R.drawable.ic_greenflag;
            case YELLOW:
                return R.drawable.ic_yellowflag;
            case RED:
                return R.drawable.ic_redflag;
            case BLACK:
                return R.drawable.ic_blackflag;
            default:
                return R.drawable.ic_help_menu;
        }
    }

    private int getJellyDrawable(LifeguardTower.JellyFish jellyFish) {
        switch(jellyFish) {
            case LITTLE:
                return R.drawable.ic_jelly_little;
            case NONE:
                return R.drawable.ic_jelly_none;
            case MUCH:
                return R.drawable.ic_jelly_much;
            default:
                return R.drawable.ic_help_menu;
        }
    }

    private int getWeatherDrawable(LifeguardTower.Weather weather) {
        switch(weather) {
            case CLEAN:
                return R.drawable.ic_weather_sunny_color;
            case CLOUDY:
                return R.drawable.ic_weather_cloudy_color;
            case RAINY:
                return R.drawable.ic_weather_rain;
            default:
                return R.drawable.ic_help_menu;
        }
    }
}
