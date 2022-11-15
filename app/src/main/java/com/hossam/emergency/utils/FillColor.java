package com.hossam.emergency.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.hossam.emergency.R;

/**
 * Created by hossam on 12/3/17.
 */

public class FillColor {
    private static final FillColor ourInstance = new FillColor();

    private FillColor() {
    }

    public static FillColor getInstance() {
        return ourInstance;
    }

    public void fillColorBlue(ImageView imageView, Activity activity) {

        imageView.setColorFilter(ContextCompat.getColor(activity, R.color.blue_semi_transparent), PorterDuff.Mode.MULTIPLY);

    }

    public void fillColorSolidBlue(ImageView imageView, Activity activity) {

        imageView.setColorFilter(ContextCompat.getColor(activity, R.color.blue), PorterDuff.Mode.MULTIPLY);

    }

    public void fillColorRed(ImageView imageView, Activity activity) {

        imageView.setColorFilter(ContextCompat.getColor(activity, R.color.red_solid), PorterDuff.Mode.MULTIPLY);

    }

    public void fillColorRed50(ImageView imageView, Activity activity) {

        imageView.setColorFilter(ContextCompat.getColor(activity, R.color.red_trans), PorterDuff.Mode.MULTIPLY);

    }

    public void fillColorGreen(ImageView imageView, Activity activity) {

        imageView.setColorFilter(ContextCompat.getColor(activity, R.color.green_solid), PorterDuff.Mode.MULTIPLY);

    }

    public void fillColorOrange(ImageView imageView, Activity activity) {

        imageView.setColorFilter(ContextCompat.getColor(activity, R.color.orange_solid), PorterDuff.Mode.MULTIPLY);

    }

    public void fillColorOrangeTrans(ImageView imageView, Context activity) {

        imageView.setColorFilter(ContextCompat.getColor(activity, R.color.orange_trans), PorterDuff.Mode.MULTIPLY);

    }

    public void fillColorGrey(ImageView imageView, Context activity) {

        imageView.setColorFilter(ContextCompat.getColor(activity, R.color.grey_trans), PorterDuff.Mode.MULTIPLY);

    }

    public void connectedIcon(ImageView icon, Activity activity) {

        icon.setBackground(activity.getResources().getDrawable(R.drawable.baseline_check_white_18));
        icon.setImageResource(R.drawable.baseline_check_white_18);

        fillColorGreen(icon, activity);
    }

    public void unConnectedIcon(ImageView icon, Activity activity) {

        icon.setBackground(activity.getResources().getDrawable(R.drawable.baseline_close_white_18));
        icon.setImageResource(R.drawable.baseline_close_white_18);

        fillColorRed(icon, activity);
    }

    public void unFollowIcon(ImageView icon, Activity activity) {

        icon.setImageResource(R.drawable.baseline_close_white_18);
        fillColorRed(icon, activity);
    }

    public void followIcon(ImageView icon, Activity activity) {

        icon.setImageResource(R.drawable.baseline_add_white_18);
        fillColorBlue(icon, activity);
    }

    public void gpsOnIcon(ImageView icon, Activity activity) {

        icon.setBackground(activity.getResources().getDrawable(R.drawable.baseline_location_on_white_18));
        icon.setImageResource(R.drawable.baseline_location_on_white_18);

        fillColorGreen(icon, activity);
    }

    public void gpsOffIcon(ImageView icon, Activity activity) {

        icon.setBackground(activity.getResources().getDrawable(R.drawable.baseline_location_off_white_18));
        icon.setImageResource(R.drawable.baseline_location_off_white_18);

        fillColorRed(icon, activity);
    }


    public void caseIsLive(ImageView icon, Activity activity) {

        icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.baseline_whatshot_white_18));

        fillColorOrange(icon, activity);
    }


    public void caseNotification(ImageView icon, Activity activity) {

        icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.baseline_whatshot_white_18));

        fillColorOrange(icon, activity);
    }

    public void savedCaseNotification(ImageView icon, Activity activity) {

        icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.baseline_security_white_18));

        fillColorGreen(icon, activity);
    }

    public void messageNotification(ImageView icon, Activity activity) {

        icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.baseline_chat_white_18));

        fillColorBlue(icon, activity);
    }

    public void commentNotification(ImageView icon, Activity activity) {

        icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.baseline_forum_white_18));

        fillColorGreen(icon, activity);
    }

    public void fillPerson(ImageView icon, Activity activity) {

        icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.baseline_person_white_24));

        fillColorGrey(icon, activity);
    }

//

//    public void changToIconYes(ImageView icon, Activity activity) {
//
////        icon.setBackground(activity.getResources().getDrawable(R.drawable.ic_check_white_24dp));
//        icon.setImageResource(R.drawable.ic_check_white_24dp);
//    }
//
//    public void changToIconNo(ImageView icon, Activity activity) {
//
//       // icon.setBackground(ContextCompat.getDrawable(activity,R.drawable.ic_clear_white_24dp));
//        icon.setImageResource(R.drawable.ic_clear_white_24dp);
//    }
//
//    public void fillColorRed(ImageView imageView, Activity activity) {
//
//        imageView.setColorFilter(ContextCompat.getColor(activity, R.color.red_trans), PorterDuff.Mode.MULTIPLY);
//
//    }
//
//    public void fillColorApp(ImageView imageView, Activity activity) {
//
//        imageView.setColorFilter(ContextCompat.getColor(activity, R.color.app_color_trans), PorterDuff.Mode.MULTIPLY);
//
//    }
//
//    public void fillColorAppBold(ImageView imageView, Activity activity) {
//
//        imageView.setColorFilter(ContextCompat.getColor(activity, R.color.app_color_blood), PorterDuff.Mode.MULTIPLY);
//
//    }
//
//    public void fillColorOrange(ImageView imageView, Activity activity) {
//
//        imageView.setColorFilter(ContextCompat.getColor(activity, R.color.orange_trans), PorterDuff.Mode.MULTIPLY);
//
//    }
//
//    public void fillColorGrey(ImageView imageView, Activity activity) {
//
//        imageView.setColorFilter(ContextCompat.getColor(activity, R.color.grey), PorterDuff.Mode.MULTIPLY);
//
//    }
//
//    public void fillColorGreen(ImageView imageView, Activity activity) {
//
//        imageView.setColorFilter(ContextCompat.getColor(activity, R.color.green_trans), PorterDuff.Mode.MULTIPLY);
//
//    }
}
