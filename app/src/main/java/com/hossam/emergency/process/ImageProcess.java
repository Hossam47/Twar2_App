package com.hossam.emergency.process;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

/**
 * Created by hossam on 11/20/17.
 */

public class ImageProcess {
    private static final ImageProcess ourInstance = new ImageProcess();

    private ImageProcess() {
    }

    public static ImageProcess getInstance() {
        return ourInstance;
    }

    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize, boolean filter) {
        if (realImage.getWidth() > maxImageSize && realImage.getHeight() > maxImageSize) {
            float ratio = Math.min(
                    maxImageSize / realImage.getWidth(),
                    maxImageSize / realImage.getHeight());
            int width = Math.round(ratio * realImage.getWidth());
            int height = Math.round(ratio * realImage.getHeight());

            Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width / 2,
                    height / 2, filter);
            return newBitmap;
        }
        return realImage;
    }

    public void loadImage(ImageView imageView, String url, Context activity) {

        Glide.with(activity).load(url).into(imageView);
    }

    public void loadBitmapImage(final ImageView imageView, String url, Activity activity) {

        Glide.with(activity)
                .asBitmap().
                load(url)
                .apply(new RequestOptions().override(100, 100))
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        // resource is your loaded Bitmap
                        imageView.setImageBitmap(resource);
                        return true;
                    }
                });

    }



}
