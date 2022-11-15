package com.hossam.emergency.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.hossam.emergency.R;
import com.hossam.emergency.interfaces.ActivityHelper;
import com.hossam.emergency.ui.intro_main.IntroActivity;
import com.hossam.emergency.services.Twar2App;
import com.hossam.emergency.ui.sign_in.SignInActivity;

import static com.hossam.emergency.firebase.FirebaseContract.getCurrentUserID;

public class TransmissionActivity extends AppCompatActivity implements ActivityHelper {

    Twar2App twar2App;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transmission);

        twar2App = (Twar2App) getApplicationContext();
        bindActivity();
        initActivity();

    }

    @Override
    public void bindActivity() {
        overridePendingTransition(R.anim.fade, R.anim.fade_out);
    }

    @Override
    public void initActivity() {

        splachScreen();
    }


    public void splachScreen() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (getCurrentUserID() != null) {

                    boolean check = twar2App.getSharedPref().getBoolean("SHOW_SLIDES", false);

                    if (!check) {

                        startActivity(new Intent(TransmissionActivity.this, IntroActivity.class));
                        finish();

                    } else {

                        startActivity(new Intent(TransmissionActivity.this, MainActivity.class));
                        finish();
                    }

                } else {

                    startActivity(new Intent(TransmissionActivity.this, SignInActivity.class));
                    finish();

                }
            }
        }, 3000);
    }

}
