package com.hossam.emergency.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import androidx.core.app.ActivityCompat;

import com.hossam.emergency.ui.cases.CaseModel;
import com.hossam.emergency.interfaces.ProviderIntents;

public class IntentUtils implements ProviderIntents {
    private static final IntentUtils ourInstance = new IntentUtils();

    private IntentUtils() {
    }

    public static IntentUtils getInstance() {
        return ourInstance;
    }

    @Override
    public void intentCall(String userNumber, Activity activity) {

        Intent callIntent = new Intent(Intent.ACTION_CALL);

        callIntent.setData(Uri.parse("tel:" + userNumber));

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.CALL_PHONE}, 0);
            return;
        }

        activity.startActivity(callIntent);
    }

    @Override
    public void intentCaseMap(CaseModel model, Activity activity) {

//        google.navigation:q=latitude,longitude
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + model.getLatitude() + "," + model.getLongitude() + "(" + model.getTitle() + ")");
//        Uri gmmIntentUri = Uri.parse(String.valueOf("google.navigation:q="+model.getLatitude()+","+model.getLongitude()+"("+model.getTitle()+")"));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(mapIntent);
        }


    }

    @Override
    public void intentStreetView(double latitude, double longitude, Activity activity) {

        Uri gmmIntentUri = Uri.parse("google.streetview:cbll=" + latitude + "," + longitude);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        activity.startActivity(mapIntent);

    }
}
