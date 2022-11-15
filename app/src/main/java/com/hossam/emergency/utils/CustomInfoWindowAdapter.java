package com.hossam.emergency.utils;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.hossam.emergency.R;
import com.hossam.emergency.ui.cases.CaseModel;
import com.hossam.emergency.ui.detalis_cases.DetailsCases;
import com.hossam.emergency.firebase.UserMainInformation;
import com.hossam.emergency.gps_provider.GpsController;
import com.hossam.emergency.time.GetTime;

import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    View view;
    //    final View userView;
    UserMainInformation userMainInformation = UserMainInformation.getInstance();
    Activity activity;
    GoogleMap googleMap;
    CircleImageView imageInfoWindow, userImage;
    CoolTextView nameInfoWindow, userName;
    CoolTextView timeInfoWindow;
    CoolTextView locationInfoWindow, userLocation;
    CoolTextView caseInfoWindow;
    RelativeLayout containerInfoWindow;
    GetTime getTime = GetTime.getInstance();
    GpsController gpsController;

    public CustomInfoWindowAdapter(GoogleMap googleMap, Activity activity) {
        this.activity = activity;
        this.googleMap = googleMap;
        gpsController = new GpsController(activity);

        bindInfoWindow();
    }


    public void bindInfoWindow() {

        view = LayoutInflater.from(activity).inflate(R.layout.custom_info_window, null);

        imageInfoWindow = view.findViewById(R.id.image_info_window);
        nameInfoWindow = view.findViewById(R.id.name_info_window);
        timeInfoWindow = view.findViewById(R.id.time_info_window);
        locationInfoWindow = view.findViewById(R.id.location_info_window);
        caseInfoWindow = view.findViewById(R.id.case_info_window);
        containerInfoWindow = view.findViewById(R.id.container_info_window);

//        userImage = userView.findViewById(R.id.image_user_info_window);
//        userName = userView.findViewById(R.id.name_user_info_window);
    }


    public void initInfoWindow(Marker marker) {

        CaseModel caseModel = (CaseModel) marker.getTag();

        if (caseModel != null) {

            userMainInformation.getUserInformation(caseModel.getUser_id(), nameInfoWindow, imageInfoWindow, activity);
            timeInfoWindow.setText(getTime.getStyleTime(activity).format(new Date(caseModel.getTime())));
            gpsController.getCurrentDistance(caseModel.getLatitude(), caseModel.getLongitude(), locationInfoWindow);
            caseInfoWindow.setText(caseModel.getTitle());

            googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {

                    if (marker.getTag() != null) {

                        Intent intent = new Intent(activity, DetailsCases.class);
                        intent.putExtra("case_model", (CaseModel) marker.getTag());
                        activity.startActivity(intent);
                    }
                }
            });
        }
    }

    @Override
    public View getInfoWindow(Marker marker) {

        CaseModel caseModel = (CaseModel) marker.getTag();
        initInfoWindow(marker);

        if (caseModel != null) {
            return view;
        } else {
            return null;

        }

    }

    @Override
    public View getInfoContents(Marker marker) {
        CaseModel caseModel = (CaseModel) marker.getTag();
        initInfoWindow(marker);

        if (caseModel != null) {
            return view;
        } else {

            marker.setTitle("You");
            return null;

        }
    }


}
