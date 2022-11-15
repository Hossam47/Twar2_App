package com.hossam.emergency.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hossam.emergency.ui.cases.CaseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitConnections implements ProviderRetrofit {

    private Retrofit retrofit;

    public RetrofitConnections() {

    }

    @Override
    public void sendCaseNotification(CaseModel model) {

        NotificationCall notificationCall = getConnection().create(NotificationCall.class);

        Call<CaseModel> call = notificationCall.sendNotificaion(model);

        call.enqueue(new Callback<CaseModel>() {
            @Override
            public void onResponse(Call<CaseModel> call, Response<CaseModel> response) {

            }

            @Override
            public void onFailure(Call<CaseModel> call, Throwable t) {

            }
        });


    }


    @Override
    public void connectURL(String url) {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson));

        this.retrofit = builder.build();
    }

    @Override
    public Retrofit getConnection() {

        return retrofit;
    }

}
