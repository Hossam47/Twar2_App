package com.hossam.emergency.ui.main;

import static com.hossam.emergency.firebase.FirebaseContract.getMainReference;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.foursquare.api.types.Venue;
import com.foursquare.placepicker.PlacePicker;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.hossam.emergency.R;
import com.hossam.emergency.gps_provider.GpsController;
import com.hossam.emergency.gps_provider.LocationModel;
import com.hossam.emergency.info_dialog.InformationDialog;
import com.hossam.emergency.interfaces.ActivityHelper;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.nlopez.smartlocation.OnReverseGeocodingListener;
import io.nlopez.smartlocation.SmartLocation;

public class LocationActivity extends AppCompatActivity implements ActivityHelper, OnMapReadyCallback {


    @BindView(R.id.btn_get_location)
    LinearLayout getLocation;

    @BindView(R.id.toolbar_location)
    androidx.appcompat.widget.Toolbar toolbar;

    @BindView(R.id.title_location)
    TextView title_location;

    AutocompleteSupportFragment autocompleteFragment;
    GoogleMap mMap;
    MarkerOptions markerOptions = new MarkerOptions();
    Marker marker;
    LatLng point;

    Location location = new Location("");

    GpsController gpsController;


    public static Intent makeIntent(Context context) {

        return new Intent(context, LocationActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        ButterKnife.bind(this);
        bindActivity();
        initActivity();
    }

    @Override
    public void bindActivity() {
        overridePendingTransition(R.anim.fade, R.anim.fade_out);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.case_location));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_get_location);

        mapFragment.getMapAsync(this);

        if (!Places.isInitialized()) {
            Places.initialize(this, getString(R.string.google_api_key), Locale.US);
        }

        autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.LAT_LNG, Place.Field.NAME));
        autocompleteFragment.setTypeFilter(TypeFilter.ADDRESS);



    }

    @Override
    public void initActivity() {

        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();

                if (point != null) {

                    intent.putExtra("lat", point.latitude);
                    intent.putExtra("lang", point.longitude);
                    setResult(Activity.RESULT_OK, intent);

                    finish();

                } else {

                    Toast.makeText(LocationActivity.this, getResources().getString(R.string.set_location_message_location),
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

        gpsController = new GpsController(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(true);
//        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        markerOptions = new MarkerOptions().draggable(true);
//        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.retro_map));

        gpsController.getCurrentLocation(mMap);

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                Location location = new Location("");

                location.setLatitude(latLng.latitude);
                location.setLongitude(latLng.longitude);

                if (marker == null) {

                    marker = mMap.addMarker(markerOptions.position(new LatLng(latLng.latitude, latLng.longitude))
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.fire_marker_shadow)));


                    point = latLng;
                    getLocation.setBackgroundResource(R.drawable.o_green);
                    getCurrentLocationAddress(location);

                } else {

                    marker.setPosition(latLng);
                    getLocation.setBackgroundResource(R.drawable.o_green);
                    getCurrentLocationAddress(location);

                }


            }
        });

        initAutoCompelete(mMap);

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                getUserLocation();

                return false;
            }
        });

    }

    private void pickPlace() {
        Intent intent = new Intent(this, PlacePicker.class);
        startActivityForResult(intent, 9001);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == PlacePicker.PLACE_PICKED_RESULT_CODE) {

            Venue place = data.getParcelableExtra(PlacePicker.EXTRA_PLACE);
            LatLng location = new LatLng(place.getLocation().getLat(), place.getLocation().getLng());

//            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location,
//                    15), 1000, null);
            initCompeleteLocation(location);

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void getUserLocation() {

        getMainReference().child("location").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                LocationModel locationModel = dataSnapshot.getValue(LocationModel.class);

                if (dataSnapshot.exists()) {

                    point = new LatLng(locationModel.getLatitude(), locationModel.getLongitude());
                    getLocation.setBackgroundResource(R.drawable.o_green);

                    location.setLatitude(locationModel.getLatitude());
                    location.setLongitude(locationModel.getLongitude());
                    getCurrentLocationAddress(location);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    private void getCurrentLocationAddress(Location location) {

        SmartLocation.with(this).geocoding()
                .reverse(location, new OnReverseGeocodingListener() {
                    @Override
                    public void onAddressResolved(Location location, List<android.location.Address> list) {

                        if (list.size() != 0) {
                            title_location.setText(list.get(0).getAddressLine(0));
                        }
                    }

                });

    }


    public void initCompeleteLocation(LatLng location) {

        if (marker == null) {

            marker = mMap.addMarker(markerOptions.position(location)
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.fire_marker_shadow)));

            point = location;


        } else {

            marker.setPosition(location);

        }

        getLocation.setBackgroundResource(R.drawable.o_green);

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location,
                16), 1000, null);

        Location location1 = new Location("");
        location1.setLatitude(location.latitude);
        location1.setLongitude(location.longitude);

        getCurrentLocationAddress(location1);


    }

    public void initAutoCompelete(final GoogleMap googleMap) {

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {

                location.setLatitude(place.getLatLng().latitude);
                location.setLongitude(place.getLatLng().longitude);

                if (marker == null) {

                    marker = mMap.addMarker(markerOptions.position(place.getLatLng())
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.fire_marker_shadow)));

                    point = place.getLatLng();


                } else {

                    marker.setPosition(place.getLatLng());

                }

                getLocation.setBackgroundResource(R.drawable.o_green);

                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(),
                        16), 1000, null);

                getCurrentLocationAddress(location);
            }

            @Override
            public void onError(@NonNull Status status) {

            }

        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        } else if (id == R.id.action_current_location) {
            getUserLocation();
            return true;
        } else if (id == R.id.action_search_location) {

            InformationDialog informationDialog = new InformationDialog(this);

            if (informationDialog.getCheckValue()) {
                pickPlace();

            } else if (!informationDialog.getCheckValue()) {

                informationDialog.startDialog();
                informationDialog.getAcceptButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pickPlace();
                        informationDialog.endDialog();
                        informationDialog.initCheckBox();
                    }
                });
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_location, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}

