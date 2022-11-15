package com.hossam.emergency.animations;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public interface ProviderAnimationUtils {

    void animationReaction(ImageView imageView, Activity activity);

    void animationReaction(LinearLayout linearLayout, Activity activity);

    void animationReactionInterpolator(View view, Activity activity);

    void animationReaction(View view, Activity activity);

    void animationReactionFade(View view, Activity activity);


    void animationSwipe(ImageView imageView, Activity activity);
}
