package com.hossam.emergency.process;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

/**
 * Created by hossam on 11/15/17.
 */

public class ProcessMap {
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
    private static final ProcessMap ourInstance = new ProcessMap();
    // Declaring a Location Manager
    protected LocationManager locationManager;
    // flag for GPS status
    boolean isGPSEnabled = false;
    // flag for network status
    boolean isNetworkEnabled = false;
    // flag for GPS status
    LocationListener listener;
    Location location; // location
    LatLng finalPoint;

    private ProcessMap() {
    }

    public static ProcessMap getInstance() {
        return ourInstance;
    }

    public void mapCamera(GoogleMap googleMap, LatLng latLng) {

        if (latLng != null) {
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
    }

    public LatLng getMarkerPosition(final GoogleMap googleMap, final MarkerOptions markerOptions, final LatLng latLngas) {

        final Marker marker = googleMap.addMarker(markerOptions.position(latLngas).title("New Parking"));

        marker.setDraggable(true);

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                marker.setPosition(latLng);

                mapCamera(googleMap, latLng);

                finalPoint = latLng;

            }
        });

        return finalPoint;
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng point = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            point = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return point;
    }


    public Location getCurrentLocaiton(Activity activity) {

        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);

        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            return null;
        }
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

                //showSettingsAlert();

            }
        };
        if (isGPSEnabled) {

            if (locationManager != null) {

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, listener);

                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                return location;
            }
        } else if (!isNetworkEnabled) {

            if (locationManager != null) {

                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, listener);

                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                return location;
            }

        }

        if (location == null) {

            Criteria criteria = new Criteria();

            String bestProvider = locationManager.getBestProvider(criteria, false);
            Location location = locationManager.getLastKnownLocation(bestProvider);

            return location;

        }

        return location;
    }

}
