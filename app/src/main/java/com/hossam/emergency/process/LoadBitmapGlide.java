package com.hossam.emergency.process;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.bumptech.glide.Glide;

import java.util.concurrent.ExecutionException;

public class LoadBitmapGlide extends AsyncTask<String, String, Bitmap> {

    Activity activity;

    public LoadBitmapGlide(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {

        Bitmap bitmap = null;

        try {
            bitmap = Glide.
                    with(activity).asBitmap().
                    load(strings[0]).
                    into(100, 100). // Width and height
                    get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return bitmap;
    }
}
