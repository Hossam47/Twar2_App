package com.hossam.emergency.retrofit;

import com.hossam.emergency.ui.cases.CaseModel;

import retrofit2.Retrofit;

public interface ProviderRetrofit {

    void connectURL(String url);

    Retrofit getConnection();

    void sendCaseNotification(CaseModel model);
}
