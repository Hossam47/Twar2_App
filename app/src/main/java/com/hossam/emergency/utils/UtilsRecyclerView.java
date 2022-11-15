package com.hossam.emergency.utils;

import android.app.Activity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.recyclerview.widget.RecyclerView;

import com.hossam.emergency.R;
import com.hossam.emergency.interfaces.ProviderUtilsRecyclerView;

/**
 * Created by hossam on 2/16/18.
 */

public class UtilsRecyclerView implements ProviderUtilsRecyclerView {

    private static final UtilsRecyclerView ourInstance = new UtilsRecyclerView();

    private UtilsRecyclerView() {
    }

    public static UtilsRecyclerView getInstance() {
        return ourInstance;
    }

    @Override
    public void fadeAnimationRecyclerView(RecyclerView.ViewHolder viewHolder, Activity activity) {
        final Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(activity, R.anim.fade);
        viewHolder.itemView.setAnimation(animAnticipateOvershoot);
    }

    @Override
    public void bounceAnimationRecyclerView(RecyclerView.ViewHolder viewHolder, Activity activity) {
        final Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(activity, R.anim.bounce_interpolator);
        viewHolder.itemView.setAnimation(animAnticipateOvershoot);
    }

    @Override
    public void interpolatorAnimationRecyclerView(RecyclerView.ViewHolder viewHolder, Activity activity) {
        final Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(activity, R.anim.anticipateovershoot_interpolator);
        viewHolder.itemView.setAnimation(animAnticipateOvershoot);
    }


}
