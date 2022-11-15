package com.hossam.emergency.ui_component;

import android.app.Activity;
import android.app.ProgressDialog;

import androidx.fragment.app.Fragment;

public class UIFragmentComponent extends Fragment implements ProgressProvider {

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

        progress.dismiss();
    }
}