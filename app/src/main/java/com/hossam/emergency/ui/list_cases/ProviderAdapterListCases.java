package com.hossam.emergency.ui.list_cases;

import android.app.Activity;
import android.widget.ImageView;

import com.hossam.emergency.ui.cases.CaseModel;

public interface ProviderAdapterListCases {

    void mainInformation(CaseModel caseModel, AdapterListCases.ViewHolder holder);

    void loadCaseImage(CaseModel model, ImageView imageView);

    void displayProfileImage(CaseModel model, AdapterListCases.ViewHolder holder, Activity activity);

    void onClickCard(AdapterListCases.ViewHolder holder, CaseModel caseModel);

    void initReaction(CaseModel caseModel, AdapterListCases.ViewHolder holder);

    void initCaseType(CaseModel caseModel, AdapterListCases.ViewHolder holder);

    void utilsReaction(CaseModel caseModel, AdapterListCases.ViewHolder holder);

    void initComments(CaseModel caseModel, AdapterListCases.ViewHolder holder);

    void initViewReaction(CaseModel caseModel, AdapterListCases.ViewHolder holder);

    void setLocationDistance(CaseModel caseModel, AdapterListCases.ViewHolder holder);

    void checkFollowCase(CaseModel caseModel, AdapterListCases.ViewHolder holder);

    void initFollowCase(CaseModel caseModel, AdapterListCases.ViewHolder holder);

    void initFollowSavedCase(CaseModel caseModel, AdapterListCases.ViewHolder holder);

    void initBannerFeedAds(AdapterListCases.AdsHolder adsHolder, Activity activity);
}
