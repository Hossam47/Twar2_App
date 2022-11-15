package com.hossam.emergency.utils;

import android.content.Context;

import androidx.core.content.ContextCompat;

/**
 * Created by hossam on 2/8/18.
 */

public class ColorResource {
    private static final ColorResource ourInstance = new ColorResource();

    private ColorResource() {
    }

    public static ColorResource getInstance() {
        return ourInstance;
    }

    public int getColorResource(int id, Context context) {
        return ContextCompat.getColor(context, id);
    }
}
