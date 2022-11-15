package com.hossam.emergency.markers;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.hossam.emergency.R;
import com.hossam.emergency.utils.FileUtilsProvider;
import com.hossam.emergency.utils.FillColor;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class MarkerDrawer implements MarkerDrawerProvider {

    private static final MarkerDrawer ourInstance = new MarkerDrawer();

    FillColor fillColor = FillColor.getInstance();
    private final FileUtilsProvider fileUtilsProvider = FileUtilsProvider.getInstance();

    private MarkerDrawer() {
    }

    public static MarkerDrawer getInstance() {
        return ourInstance;
    }

    @Override
    public void utilMarker() {

    }

    @Override
    public void initMarker() {

    }

    public Bitmap getMarkerBitmapFromView(final String url, final Activity context) {

        View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.user_marker, null);

        final ImageView mMarkerImageView = view.findViewById(R.id.image_user_marker);

        if (view == null) {
            view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(R.layout.user_marker, null);
        }


        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                mMarkerImageView.setImageBitmap(bitmap);

            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                fillColor.fillPerson(mMarkerImageView, context);

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                // getStatues(state, id);
                fillColor.fillPerson(mMarkerImageView, context);
            }
        };

        if (!url.isEmpty()) {

            Picasso.get().load(url).into(target);

        }
//
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();

        Bitmap returnedBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = view.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        view.draw(canvas);

        return returnedBitmap;
    }

}
