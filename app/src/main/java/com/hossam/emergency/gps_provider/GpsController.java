package com.hossam.emergency.gps_provider;

import static com.hossam.emergency.constants.DimenConstant.speed_zoom_case;
import static com.hossam.emergency.constants.DimenConstant.zoom_case;
import static com.hossam.emergency.firebase.FirebaseContract.getCasesReference;
import static com.hossam.emergency.firebase.FirebaseContract.getMainReference;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Location;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.hossam.emergency.R;
import com.hossam.emergency.services.Twar2App;
import com.hossam.emergency.time.GetTime;
import com.hossam.emergency.ui.cases.CaseModel;
import com.hossam.emergency.ui.main.ControllerMain;
import com.hossam.emergency.ui.sign_up.User;
import com.hossam.emergency.utils.CustomInfoWindowAdapter;


import java.util.ArrayList;
import java.util.List;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.OnReverseGeocodingListener;
import io.nlopez.smartlocation.SmartLocation;

public class GpsController implements ProviderGps {

    Activity activity;
    Context context;
    CurrentUserLocation currentUserLocation = new CurrentUserLocation();
    ControllerMain controllerMain;
    Twar2App twar2App;

    public GpsController(Activity activity) {
        this.activity = activity;
        controllerMain = new ControllerMain(activity);
        twar2App = (Twar2App) activity.getApplication();
    }

    public GpsController(Context context) {
        this.context = context;
    }

    public void getCurrentLocation(final GoogleMap googleMap) {

        SmartLocation.with(activity).location().start(new OnLocationUpdatedListener() {
            @Override
            public void onLocationUpdated(final Location location) {

                getMainReference().addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        User user = dataSnapshot.getValue(User.class);
                        currentUserLocation.setCurrentUserLocation(user, new LatLng(location.getLatitude(),
                                location.getLongitude()), googleMap, activity);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.v("Location_Error", databaseError.getDetails());
                    }
                });
            }

        });

        getMainReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final User user = dataSnapshot.getValue(User.class);

                if (dataSnapshot.hasChild("location")) {

                    getMainReference().child("location").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if (dataSnapshot.exists()) {

                                LocationModel locationModel = dataSnapshot.getValue(LocationModel.class);

                                currentUserLocation.setCurrentUserLocation(user,
                                        new LatLng(locationModel.getLatitude(), locationModel.getLongitude()), googleMap, activity);

                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    public void setCasesMarkers(final GoogleMap googleMap, final boolean exist_user) {

        getCasesReference().orderByChild("deleted").equalTo(false).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<CaseModel> caseModelsList = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    CaseModel model = snapshot.getValue(CaseModel.class);

//                    Log.v("case_delete_markers", model.isDeleted() + "");

//                    if (!model.isDeleted()) {

                    caseModelsList.add(model);

//                    }

                }


                for (int i = 0; i < caseModelsList.size(); i++) {

                    Marker marker = null;

                    if (!caseModelsList.get(i).isSaved()) {

                        marker = googleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(caseModelsList.get(i).getLatitude(), caseModelsList.get(i).getLongitude()))
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.fire_marker_shadow)));

                        drawCircleAroundMarker(googleMap, caseModelsList.get(i).getLatitude(),
                                caseModelsList.get(i).getLongitude(), true);

                    } else if (caseModelsList.get(i).isSaved()) {

                        marker = googleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(caseModelsList.get(i).getLatitude(),
                                        caseModelsList.get(i).getLongitude()))
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.marker_saved)));

                        drawCircleAroundMarker(googleMap, caseModelsList.get(i).getLatitude(),
                                caseModelsList.get(i).getLongitude(), false);

                    }


                    marker.setTag(caseModelsList.get(i));

                    googleMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(googleMap, activity));

                }

                if (exist_user == false) {

                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new
                                    LatLng(caseModelsList.get(0).getLatitude(),
                                    caseModelsList.get(0).getLongitude()),
                            zoom_case), speed_zoom_case, null);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }


    @Override
    public void calculateDistance(final Location location, final TextView textView) {


    }

    @Override
    public void drawCircleAroundMarker(GoogleMap googleMap, double lat, double lng, boolean fire) {

        if (fire) {

            googleMap.addCircle(new CircleOptions()
                    .center(new LatLng(lat, lng))
                    .radius(70)
                    .strokeWidth(140)
                    .strokeColor(activity.getResources().getColor(R.color.orange_trans))
                    .fillColor(activity.getResources().getColor(R.color.red_trans_50)));
        } else {

            googleMap.addCircle(new CircleOptions()
                    .center(new LatLng(lat, lng))
                    .radius(70)
                    .strokeWidth(140)
                    .strokeColor(activity.getResources().getColor(R.color.green_trans))
                    .fillColor(activity.getResources().getColor(R.color.green_solid)));
        }

    }

    @Override
    public void startGpsLocation(Context context) {
        SmartLocation.with(context).location().start(new OnLocationUpdatedListener() {
            @Override
            public void onLocationUpdated(Location location) {

                getMainReference().child("location").setValue(new LocationModel(location.getLatitude(),
                        location.getLongitude(), GetTime.getUTCTimetamp()));

            }
        });
    }

    @Override
    public void stopGpsLocation() {
        SmartLocation.with(activity).activityRecognition().stop();

    }

    @Override
    public void getCurrentAddress(Location location, final TextView title_location) {

        SmartLocation.with(activity).geocoding()
                .reverse(location, new OnReverseGeocodingListener() {
                    @Override
                    public void onAddressResolved(Location location, List<Address> list) {

                        if (list.size() != 0) {

                            title_location.setText(list.get(0).getAddressLine(0));;
                        }
                    }

                });

    }

    @Override
    public void getCurrentDistance(double lat, double lang, final TextView textView) {

        final Location locationCase = new Location("");
        locationCase.setLatitude(lat);
        locationCase.setLongitude(lang);

        getMainReference().child("location").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    LocationModel locationModel = dataSnapshot.getValue(LocationModel.class);

                    Location locationFire = new Location("");
                    locationFire.setLatitude(locationModel.getLatitude());
                    locationFire.setLongitude(locationModel.getLongitude());

                    textView.setText(String.format("%.02f", locationFire.distanceTo(locationCase) / 1000) + " KM");
                } else {

                    textView.setText("?? KM");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
