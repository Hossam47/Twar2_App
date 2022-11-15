package com.hossam.emergency.utils;

import android.content.Context;
import android.widget.Toast;

import com.hossam.emergency.interfaces.ToastStyleProvider;

import es.dmoral.toasty.Toasty;

public class ToastStyle implements ToastStyleProvider {

    private final Context context;

    public ToastStyle(Context context) {
        this.context = context;
    }

    @Override
    public void positiveToast(String message) {

        Toasty.success(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void negativeToast(String message) {

        Toasty.error(context, message, Toast.LENGTH_SHORT).show();
    }
}
