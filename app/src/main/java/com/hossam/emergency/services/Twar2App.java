package com.hossam.emergency.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;
import com.hossam.emergency.utils.ToastStyle;

import static com.hossam.emergency.firebase.FirebaseContract.getCurrentUserID;

//import com.google.firebase.crash.FirebaseCrash;

public class Twar2App extends MultiDexApplication {

    ToastStyle toastStyle;
    private SharedPreferences sharedPref;
    private String SHARED_PREF = "USER_FILE";

    public static final String CHANNEL_ID = "twar2_service_channel";

    @Override
    public void onCreate() {
        super.onCreate();

        sharedPref = getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);

        FirebaseDatabase.getInstance().setLogLevel(Logger.Level.DEBUG);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        if (getCurrentUserID() != null) {

            startService(new Intent(getApplicationContext(), FCMService.class));
            startService(new Intent(getApplicationContext(), FCMInstanceIdService.class));
            startService(new Intent(getApplicationContext(), ServiceLocation.class));
//            FirebaseCrash.setCrashCollectionEnabled(true);

            //  initImageLoader(getApplicationContext());

        }


        MultiDex.install(this);

        toastStyle = new ToastStyle(getApplicationContext());

        createNotificaionChannel();

    }

    private void createNotificaionChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "twar2",
                    NotificationManager.IMPORTANCE_HIGH

            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }


    public ToastStyle getToastStyle() {
        return toastStyle;
    }

    public SharedPreferences getSharedPref() {
        return sharedPref;
    }

    public void saveCachesIntro(boolean check) {
        SharedPreferences.Editor editor = getSharedPref().edit();
        editor.putBoolean("SHOW_SLIDES", check);
        editor.commit();
    }

    public void saveCachesCountry(String country) {
        SharedPreferences.Editor editor = getSharedPref().edit();
        editor.putString("USER_COUNTRY", country);
        editor.commit();
    }

    public String getUserCountry() {

        String country_name = getSharedPref().getString("USER_COUNTRY", "");
        return country_name.concat("_cases");

    }

}
