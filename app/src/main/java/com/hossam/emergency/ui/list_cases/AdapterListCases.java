package com.hossam.emergency.ui.list_cases;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdView;
import com.hossam.emergency.R;
import com.hossam.emergency.ui.cases.CaseModel;
import com.hossam.emergency.utils.CoolTextView;
import com.hossam.emergency.utils.UtilsRecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterListCases extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<CaseModel> casesList;
    Activity activity;
    CaseModel caseModel;
    ControllerAdapterListCases controllerAdapterListCases;
    UtilsRecyclerView utilsRecyclerView = UtilsRecyclerView.getInstance();

    int adsPosition = 0;
    private static final int LIST_AD_DELTA = 4;
    private static final int CONTENT = 0;
    private static final int AD = 1;


    public AdapterListCases(ArrayList<CaseModel> casesList, Activity activity) {
        this.casesList = casesList;
        this.activity = activity;

        controllerAdapterListCases = new ControllerAdapterListCases(activity);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        if (viewType == CONTENT) {
            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.itmes_cases_list, parent, false);
            ViewHolder viewHolder = new ViewHolder(layout);

            return viewHolder;
        } else {

            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_ads_feed, parent, false);
            AdsHolder adsHolder = new AdsHolder(layout);

            return adsHolder;
        }



    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position) == CONTENT) {


            ViewHolder caseHolder = (ViewHolder) holder;

            caseModel = casesList.get(getRealPosition(position));

            if (!caseModel.isDeleted()) {

                controllerAdapterListCases.mainInformation(caseModel, caseHolder);
                controllerAdapterListCases.loadCaseImage(caseModel, caseHolder.case_image);
                controllerAdapterListCases.onClickCard(caseHolder, caseModel);
                controllerAdapterListCases.displayProfileImage(caseModel, caseHolder, activity);
                controllerAdapterListCases.checkFollowCase(caseModel, caseHolder);
                controllerAdapterListCases.initFollowCase(caseModel, caseHolder);
                controllerAdapterListCases.initFollowSavedCase(caseModel, caseHolder);

            } else if (caseModel.isDeleted()) {

                caseHolder.main_container.setLayoutParams(new RelativeLayout.LayoutParams(0, 0));
                caseHolder.main_container.setLayoutParams(new RelativeLayout.MarginLayoutParams(0, 0));
                caseHolder.main_container.setVisibility(View.GONE);
            }

        } else {
            AdsHolder adsHolder = (AdsHolder) holder;

            controllerAdapterListCases.initBannerFeedAds(adsHolder, activity);
        }


    }

    @Override
    public int getItemCount() {

        int additionalContent = 0;

        if (casesList.size() > 0 && LIST_AD_DELTA > 0 && casesList.size() > LIST_AD_DELTA) {
            additionalContent = casesList.size() / LIST_AD_DELTA;
        }

        return casesList.size() + additionalContent;

    }

    @Override
    public int getItemViewType(int position) {

        if (position > 0 && position % LIST_AD_DELTA == 0) {
            return AD;
        }
        return CONTENT;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_itmes_list_cases)
        ImageView image;

        @BindView(R.id.image_case_items_list_cases)
        ImageView case_image;

        @BindView(R.id.fire_image)
        ImageView fire_image;

        @BindView(R.id.comments_image)
        ImageView comment_image;

        @BindView(R.id.view_image)
        ImageView view_image;

        @BindView(R.id.name_itmes_list_cases)
        CoolTextView name;

        @BindView(R.id.time_itmes_list_cases)
        CoolTextView time;

        @BindView(R.id.distance_itmes_list_cases)
        CoolTextView distance;

        @BindView(R.id.title_itmes_list_cases)
        com.borjabravo.readmoretextview.ReadMoreTextView title;

        @BindView(R.id.desc_itmes_list_cases)
        com.borjabravo.readmoretextview.ReadMoreTextView desc;

        @BindView(R.id.fire_numbers)
        CoolTextView fire_number;

        @BindView(R.id.comments_numbers)
        CoolTextView comment_number;

        @BindView(R.id.view_number)
        CoolTextView view_number;

        @BindView(R.id.fire_container)
        LinearLayout fire_container;

        @BindView(R.id.comment_container)
        LinearLayout comment_container;

        @BindView(R.id.main_case_container)
        LinearLayout main_container;

        @BindView(R.id.follow_case_container)
        LinearLayout follow_case_container;

        @BindView(R.id.icon_follow_case)
        ImageView icon_follow_case;

        @BindView(R.id.title_follow_case)
        CoolTextView title_follow_case;

        @BindView(R.id.case_type_container)
        CardView case_type_container;

        @BindView(R.id.case_type_name)
        CoolTextView case_type_name;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }

    private int getRealPosition(int position) {
        if (LIST_AD_DELTA == 0) {
            return position;
        } else {
            return position - position / LIST_AD_DELTA;
        }
    }

    public static class AdsHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ads_feed_banner_cases)
        AdView ads_feed_banner_cases;

        public AdsHolder(@NonNull View adsView) {
            super(adsView);
            ButterKnife.bind(this, adsView);
        }
    }
}
