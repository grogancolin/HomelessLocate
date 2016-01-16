package com.homelesslocate.homelesslocate;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class LocateActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Location mLoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locate);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    GoogleMap.OnMyLocationButtonClickListener myLocationButtonClickListener = new GoogleMap.OnMyLocationButtonClickListener(){

        @Override
        public boolean onMyLocationButtonClick() {
            mLoc = mMap.getMyLocation();
            if(mLoc != null){
                mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(mLoc.getLatitude(), mLoc.getLongitude())));
                mMap.moveCamera(CameraUpdateFactory.zoomTo(16));
            }
            return false;
        }
    };
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        // get the users location
        mLoc = mMap.getMyLocation();
        if(mLoc != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(mLoc.getLatitude(), mLoc.getLongitude())));
            mMap.moveCamera(CameraUpdateFactory.zoomTo(16));
        }
    }

    /**
     * Moves the user to the insert info page
     * @param view
     */
    public void onClick(View view){
        Intent i = new Intent(this, InformationUpload.class);
        LatLng point = mMap.getCameraPosition().target;
        double[] locToSend = { point.latitude, point.longitude };
        i.putExtra("location", locToSend);
        startActivity(i);
    }
}
