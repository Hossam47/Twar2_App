package com.hossam.emergency.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.hossam.emergency.gps_provider.GpsController;

import static com.hossam.emergency.firebase.FirebaseContract.getCurrentUserID;


/**
 * Created by hossam on 3/1/18.
 */

public class ServiceLocation extends Service {

    GpsController gpsController = new GpsController(this);

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        if (getCurrentUserID() != null) {

            gpsController.startGpsLocation(this);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
