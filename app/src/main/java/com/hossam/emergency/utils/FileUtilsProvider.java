package com.hossam.emergency.utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtilsProvider {

    private static final FileUtilsProvider ourInstance = new FileUtilsProvider();

    private FileUtilsProvider() {
    }

    public static FileUtilsProvider getInstance() {
        return ourInstance;
    }

    public String saveToInternalStorage(Bitmap bitmapImage, Context context) {
        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("twar2_caches", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, "profile.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }


    public Bitmap loadImageFromStorage(String path) {

        Bitmap bitmap = null;
        try {
            File f = new File(path, "profile.jpg");
            bitmap = BitmapFactory.decodeStream(new FileInputStream(f));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return bitmap;
    }


}
