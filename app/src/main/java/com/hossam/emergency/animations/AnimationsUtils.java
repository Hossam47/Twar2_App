package com.hossam.emergency.animations;

import android.app.Activity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hossam.emergency.R;

public class AnimationsUtils implements ProviderAnimationUtils {
    private static final AnimationsUtils ourInstance = new AnimationsUtils();

    private AnimationsUtils() {
    }

    public static AnimationsUtils getInstance() {
        return ourInstance;
    }

    @Override
    public void animationReaction(ImageView imageView, Activity activity) {
        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.anticipateovershoot_interpolator);
        animation.setDuration(300);
        imageView.startAnimation(animation);
    }

    @Override
    public void animationReaction(LinearLayout linearLayout, Activity activity) {
        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.fade);
        animation.setDuration(500);
        linearLayout.startAnimation(animation);
    }

    @Override
    public void animationReaction(View view, Activity activity) {

        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.fade);
        animation.setDuration(1000);
        view.startAnimation(animation);
    }

    @Override
    public void animationReactionFade(View view, Activity activity) {

        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.fade);
        animation.setDuration(1000);
        view.startAnimation(animation);
    }

    @Override
    public void animationReactionInterpolator(View view, Activity activity) {

        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.anticipateovershoot_interpolator);
        animation.setDuration(1000);
        view.startAnimation(animation);
    }

    @Override
    public void animationSwipe(ImageView imageView, Activity activity) {
        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.fade_out);
        animation.setDuration(2000);
        imageView.startAnimation(animation);
    }


}
