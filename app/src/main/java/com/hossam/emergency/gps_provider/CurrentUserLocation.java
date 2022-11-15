package com.hossam.emergency.gps_provider;

import static com.hossam.emergency.constants.DimenConstant.speed_zoom_user;
import static com.hossam.emergency.constants.DimenConstant.zoom_user;

import android.app.Activity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hossam.emergency.R;
import com.hossam.emergency.markers.MarkerDrawer;
import com.hossam.emergency.ui.sign_up.User;


public class CurrentUserLocation {

    private final MarkerDrawer markerDrawer = MarkerDrawer.getInstance();

    private final MarkerOptions options = new MarkerOptions();

    private Marker marker;

    private final CircleOptions circleOptions = new CircleOptions();

    public CurrentUserLocation() {
    }


    public Marker setCurrentUserLocation(User user, LatLng latLng, GoogleMap googleMap, Activity activity) {

        options.title(user.getUsername())
                .icon(BitmapDescriptorFactory
                        .fromBitmap(markerDrawer.getMarkerBitmapFromView(user.getImage(), activity))).position(latLng);

        if (marker == null) {

            marker = googleMap.addMarker(options);

        } else if (marker != null) {

            marker.remove();
            marker = googleMap.addMarker(options);

        }


        circleOptions.center(latLng).radius(100)
                .fillColor(activity.getResources().getColor(R.color.red_solid))
                .strokeColor(activity.getResources().getColor(R.color.red_trans))
                .strokeWidth(25);

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,
                zoom_user), speed_zoom_user, null);

        return marker;
    }

}
