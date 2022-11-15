package com.hossam.emergency.gps_provider;

import android.content.Context;
import android.location.Location;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;

public interface ProviderGps {

    void getCurrentLocation(GoogleMap googleMap);

    void getCurrentAddress(Location location, TextView address);

    void getCurrentDistance(double lat, double lang, final TextView textView);

    void calculateDistance(Location location, TextView textView);

    void drawCircleAroundMarker(GoogleMap googleMap, double lat, double lang, boolean fire);

    void startGpsLocation(Context context);

    void stopGpsLocation();


}
