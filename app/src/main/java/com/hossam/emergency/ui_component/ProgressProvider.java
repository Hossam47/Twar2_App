package com.hossam.emergency.ui_component;

import android.app.Activity;

public interface ProgressProvider {


    void initProgress(String message, Activity context);

    void showProgress();

    void cancelProgress();

}
