package com.hossam.emergency.ui.following_cases;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.Query;
import com.hossam.emergency.ui.cases.CaseModel;

public interface ProviderFollowingCases {


    //void getFollowingCasesData(FollowingCasesModel model,ContollerFollowingCases.ViewHolder holder);

    void initRecyclerView(Query query);

    void setRecyclerData(RecyclerView.ViewHolder viewHolder, Object model1);

    void getTitle(ContollerFollowingCases.ViewHolder holder, CaseModel model);

    void getOnContainerClick(ContollerFollowingCases.ViewHolder holder, CaseModel model);

    void getCountComments(ContollerFollowingCases.ViewHolder holder, CaseModel model);

    void getCountView(ContollerFollowingCases.ViewHolder holder, CaseModel model);

    void getCountFires(ContollerFollowingCases.ViewHolder holder, CaseModel model);

    void getPrettyTime(ContollerFollowingCases.ViewHolder holder, CaseModel model);

    void deleteButton(ContollerFollowingCases.ViewHolder holder, CaseModel model);

    void noCases(boolean show);

}
