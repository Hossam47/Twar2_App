package com.hossam.emergency.interfaces;

import android.app.Activity;

import com.hossam.emergency.ui.cases.CaseModel;

public interface ProviderIntents {

    void intentCall(String userNumber, Activity activity);

//    void intentMap(double lat, double lang, Activity activity);

    void intentCaseMap(CaseModel model, Activity activity);

    void intentStreetView(double lat, double lang, Activity activity);
}
