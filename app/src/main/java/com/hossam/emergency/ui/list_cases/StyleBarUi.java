package com.hossam.emergency.ui.list_cases;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.cardview.widget.CardView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.hossam.emergency.R;
import com.hossam.emergency.animations.AnimationsUtils;
import com.hossam.emergency.utils.CoolTextView;
import com.hossam.emergency.utils.FillColor;

public class StyleBarUi {

    Activity activity;
    FillColor fillColor = FillColor.getInstance();
    AnimationsUtils animationsUtils = AnimationsUtils.getInstance();

    private final ImageView timelineFilterImage;
    private final ImageView fireFilterImage;
    private final ImageView nearFilterImage;
    private final ImageView savedFilterImage;
    private final CoolTextView timelineFilterText;
    private final CoolTextView fireFilterText;
    private final CoolTextView nearFilterText;
    private final CoolTextView savedFilterText;
    private final LinearLayout timelineFilterContainer;
    private final LinearLayout fireFilterContainer;
    private final LinearLayout nearFilterContainer;
    private final LinearLayout savedFilterContainer;
    private RelativeLayout emptyView;
    private ProgressBar progressListCases;
    private CardView mainStyleBar;
    private SwipeRefreshLayout swipeContainer;
    private ImageView noDataImage;

    public StyleBarUi(Activity activity, ImageView timelineFilterImage, ImageView fireFilterImage, ImageView nearFilterImage,
                      ImageView savedFilterImage, CoolTextView timelineFilterText, CoolTextView fireFilterText,
                      CoolTextView nearFilterText, CoolTextView savedFilterText, LinearLayout timelineFilterContainer,
                      LinearLayout fireFilterContainer, LinearLayout nearFilterContainer, LinearLayout savedFilterContainer) {
        this.activity = activity;
        this.timelineFilterImage = timelineFilterImage;
        this.fireFilterImage = fireFilterImage;
        this.nearFilterImage = nearFilterImage;
        this.savedFilterImage = savedFilterImage;
        this.timelineFilterText = timelineFilterText;
        this.fireFilterText = fireFilterText;
        this.nearFilterText = nearFilterText;
        this.savedFilterText = savedFilterText;
        this.timelineFilterContainer = timelineFilterContainer;
        this.fireFilterContainer = fireFilterContainer;
        this.nearFilterContainer = nearFilterContainer;
        this.savedFilterContainer = savedFilterContainer;

    }

    public void setStyleBar(int key) {

        switch (key) {

            case 0:

                timelineFilterContainer.setBackground(activity.getResources().getDrawable(R.drawable.square_red_trans));
                fireFilterContainer.setBackground(null);
                nearFilterContainer.setBackground(null);
                savedFilterContainer.setBackground(null);

                fillColor.fillColorRed(timelineFilterImage, activity);
                fillColor.fillColorGrey(fireFilterImage, activity);
                fillColor.fillColorGrey(nearFilterImage, activity);
                fillColor.fillColorGrey(savedFilterImage, activity);

//                timelineFilterText.setTextColor(activity.getResources().getColor(R.color.grey));
//                fireFilterText.setTextColor(activity.getResources().getColor(R.color.grey_trans));
//                nearFilterText.setTextColor(activity.getResources().getColor(R.color.grey_trans));
                animationsUtils.animationReaction(timelineFilterImage, activity);

                break;

            case 1:

                timelineFilterContainer.setBackground(null);
                fireFilterContainer.setBackground(activity.getResources().getDrawable(R.drawable.square_orange_trans));
                nearFilterContainer.setBackground(null);
                savedFilterContainer.setBackground(null);

                fillColor.fillColorGrey(timelineFilterImage, activity);
                fillColor.fillColorOrange(fireFilterImage, activity);
                fillColor.fillColorGrey(nearFilterImage, activity);
                fillColor.fillColorGrey(savedFilterImage, activity);

//                timelineFilterText.setTextColor(activity.getResources().getColor(R.color.grey_trans));
//                fireFilterText.setTextColor(activity.getResources().getColor(R.color.grey));
//                nearFilterText.setTextColor(activity.getResources().getColor(R.color.grey_trans));
                animationsUtils.animationReaction(fireFilterImage, activity);

                break;

            case 2:

                timelineFilterContainer.setBackground(null);
                fireFilterContainer.setBackground(null);
                nearFilterContainer.setBackground(activity.getResources().getDrawable(R.drawable.square_blue_trans));
                savedFilterContainer.setBackground(null);

                fillColor.fillColorGrey(timelineFilterImage, activity);
                fillColor.fillColorGrey(fireFilterImage, activity);
                fillColor.fillColorBlue(nearFilterImage, activity);
                fillColor.fillColorGrey(savedFilterImage, activity);
//
//                timelineFilterText.setTextColor(activity.getResources().getColor(R.color.grey_trans));
//                fireFilterText.setTextColor(activity.getResources().getColor(R.color.grey_trans));
//                nearFilterText.setTextColor(activity.getResources().getColor(R.color.grey));
                animationsUtils.animationReaction(nearFilterImage, activity);

                break;


            case 3:

                timelineFilterContainer.setBackground(null);
                fireFilterContainer.setBackground(null);
                nearFilterContainer.setBackground(null);
                savedFilterContainer.setBackground(activity.getResources().getDrawable(R.drawable.square_green_trans));

                fillColor.fillColorGrey(timelineFilterImage, activity);
                fillColor.fillColorGrey(fireFilterImage, activity);
                fillColor.fillColorGrey(nearFilterImage, activity);
                fillColor.fillColorGreen(savedFilterImage, activity);
//
//                timelineFilterText.setTextColor(activity.getResources().getColor(R.color.grey_trans));
//                fireFilterText.setTextColor(activity.getResources().getColor(R.color.grey_trans));
//                nearFilterText.setTextColor(activity.getResources().getColor(R.color.grey));
                animationsUtils.animationReaction(savedFilterImage, activity);

                break;

        }


    }


    public ImageView getTimelineFilterImage() {
        return timelineFilterImage;
    }

    public ImageView getFireFilterImage() {
        return fireFilterImage;
    }

    public ImageView getNearFilterImage() {
        return nearFilterImage;
    }

    public CoolTextView getTimelineFilterText() {
        return timelineFilterText;
    }

    public CoolTextView getFireFilterText() {
        return fireFilterText;
    }

    public CoolTextView getNearFilterText() {
        return nearFilterText;
    }

    public LinearLayout getTimelineFilterContainer() {
        return timelineFilterContainer;
    }

    public LinearLayout getFireFilterContainer() {
        return fireFilterContainer;
    }

    public LinearLayout getNearFilterContainer() {
        return nearFilterContainer;
    }

    public RelativeLayout getEmptyView() {
        return emptyView;
    }

    public void setEmptyView(RelativeLayout emptyView) {
        this.emptyView = emptyView;
    }


    public Activity getActivity() {
        return activity;
    }

    public FillColor getFillColor() {
        return fillColor;
    }

    public AnimationsUtils getAnimationsUtils() {
        return animationsUtils;
    }

    public ImageView getSavedFilterImage() {
        return savedFilterImage;
    }

    public CoolTextView getSavedFilterText() {
        return savedFilterText;
    }

    public LinearLayout getSavedFilterContainer() {
        return savedFilterContainer;
    }

    public CardView getMainStyleBar() {
        return mainStyleBar;
    }

    public void setMainStyleBar(CardView mainStyleBar) {
        this.mainStyleBar = mainStyleBar;
    }

    public SwipeRefreshLayout getSwipeContainer() {
        return swipeContainer;
    }

    public void setSwipeContainer(SwipeRefreshLayout swipeContainer) {
        this.swipeContainer = swipeContainer;
    }

    public ProgressBar getProgressListCases() {
        return progressListCases;
    }

    public void setProgressListCases(ProgressBar progressListCases) {
        this.progressListCases = progressListCases;
    }

    public ImageView getNoDataImage() {
        return noDataImage;
    }

    public void setNoDataImage(ImageView noDataImage) {
        this.noDataImage = noDataImage;
    }
}
