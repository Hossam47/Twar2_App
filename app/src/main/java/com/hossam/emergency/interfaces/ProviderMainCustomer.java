package com.hossam.emergency.interfaces;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by hossam on 2/21/18.
 */

public interface ProviderMainCustomer {

    void initLocation(GoogleMap mMap);

    void setLocationParkingMarker(GoogleMap mMap);

    void setLocationMainMarker(GoogleMap mMap);

    void setLocationUserMarker(GoogleMap mMap);
}
