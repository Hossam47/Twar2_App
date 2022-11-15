package com.hossam.emergency.interfaces;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by hossam on 3/9/18.
 */

public interface ProviderGetLocation extends Serializable {

    void getLocationDetalis(LatLng point, Location location);
}
