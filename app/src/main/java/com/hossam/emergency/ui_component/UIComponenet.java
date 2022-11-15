package com.hossam.emergency.ui_component;

import android.app.Activity;
import android.app.ProgressDialog;

import androidx.appcompat.app.AppCompatActivity;

public class UIComponenet extends AppCompatActivity implements ProgressProvider {

    private ProgressDialog progress;

    public void initProgress(String message, Activity context) {

        progress = new ProgressDialog(context);
        progress.setMessage(message);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setCancelable(false);
    }

    @Override
    public void showProgress() {

        progress.show();
    }

    @Override
    public void cancelProgress() {

        progress.cancel();
        progress.dismiss();
    }
}