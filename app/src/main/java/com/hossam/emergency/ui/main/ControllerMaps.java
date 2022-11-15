package com.hossam.emergency.ui.main;

//import com.google.android.libraries.places.api.model.Place;
//import com.google.android.libraries.places.api.model.TypeFilter;
//import com.google.android.libraries.places.compat.Place;
//import com.google.android.libraries.places.compat.ui.PlaceAutocompleteFragment;
//import com.google.android.libraries.places.compat.ui.PlaceSelectionListener;
//import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
//import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

//import com.google.android.gms.location.places.Place;
//import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
//import com.google.android.gms.location.places.ui.PlaceSelectionListener;
//import com.google.android.libraries.places.api.model.Place;
//import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
//import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;
import java.util.Locale;

public class ControllerMaps {
    private static final ControllerMaps ourInstance = new ControllerMaps();
    private static final String TAG = "ControllerMaps";

    private ControllerMaps() {
    }

    public static ControllerMaps getInstance() {
        return ourInstance;
    }

    public void initAutoCompelete(final GoogleMap googleMap, AutocompleteSupportFragment autocompleteFragment) {

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.LAT_LNG, Place.Field.ADDRESS));
        autocompleteFragment.setTypeFilter(TypeFilter.ADDRESS);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(),
                        15), 1000, null);
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });

    }
}
