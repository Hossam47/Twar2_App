package com.hossam.emergency.ui.settings;

import android.app.Activity;

public class PresenterSettings implements MVPSettings.Presenter {

    private final Activity activity;
    private final MVPSettings.View view;

    public PresenterSettings(Activity activity, MVPSettings.View view) {
        this.activity = activity;
        this.view = view;
    }


}
