package com.hossam.emergency.retrofit;

import com.hossam.emergency.ui.cases.CaseModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface NotificationCall {

    @Headers("Content-Type: application/json")
    @POST("model")
    Call<CaseModel> sendNotificaion(@Body CaseModel model);
}
