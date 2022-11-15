package com.hossam.emergency.interfaces;

import android.app.Activity;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by hossam on 2/16/18.
 */

public interface ProviderUtilsRecyclerView {

    void fadeAnimationRecyclerView(RecyclerView.ViewHolder viewHolder, Activity activity);

    void bounceAnimationRecyclerView(RecyclerView.ViewHolder viewHolder, Activity activity);

    void interpolatorAnimationRecyclerView(RecyclerView.ViewHolder viewHolder, Activity activity);
}
