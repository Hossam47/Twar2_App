package com.hossam.emergency.utils;

public class DisplayImage {
    private static final DisplayImage ourInstance = new DisplayImage();

    private DisplayImage() {
    }

    public static DisplayImage getInstance() {
        return ourInstance;
    }



}
