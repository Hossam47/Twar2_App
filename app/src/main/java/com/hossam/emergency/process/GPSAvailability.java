package com.hossam.emergency.process;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class GPSAvailability {
    private static final GPSAvailability ourInstance = new GPSAvailability();

    private GPSAvailability() {
    }

    public static GPSAvailability getInstance() {
        return ourInstance;
    }

    public boolean checkGPS(Activity activity) {
        LocationManager locationManager = (LocationManager)
                activity.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

    }

    public boolean checkNetwork(Activity activity) {

        ConnectivityManager connectivityManager
                = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }
}
