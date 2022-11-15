package com.hossam.emergency.ui.cases;

import android.location.Location;
import android.net.Uri;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public interface CasesProvider {


    void vaildData(String caseType,LatLng point, ArrayList<Uri> path);

    void pushData(String caseType,String title, String desc, boolean show_profile, boolean show_mobile, LatLng point, ArrayList<Uri> path);

    void initUi();

    void initIndicatorSeekBar();

    void uploadCaseImages(String case_id, ArrayList<Uri> path);

    void addressCaseLocation(Location location, TextView textView);
}
