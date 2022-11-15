package com.hossam.emergency.utils;

import android.content.Context;

public class StringResouces {
    private static final StringResouces ourInstance = new StringResouces();

    private StringResouces() {
    }

    public static StringResouces getInstance() {
        return ourInstance;
    }

    public String getStringResource(int id, Context context) {

        return context.getResources().getString(id);
    }
}
