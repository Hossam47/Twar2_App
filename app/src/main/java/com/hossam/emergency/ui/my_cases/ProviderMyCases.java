package com.hossam.emergency.ui.my_cases;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.Query;
import com.hossam.emergency.ui.cases.CaseModel;

public interface ProviderMyCases {

    void initRecyclerView(Query query);

    void setRecyclerData(RecyclerView.ViewHolder viewHolder, Object model1);

    void getTitle(ControllerMyCasesActivity.ViewHolder holder, CaseModel model);

    void getOnContainerClick(ControllerMyCasesActivity.ViewHolder holder, CaseModel model);

    void getCountComments(ControllerMyCasesActivity.ViewHolder holder, CaseModel model);

    void getCountView(ControllerMyCasesActivity.ViewHolder holder, CaseModel model);

    void getCountFires(ControllerMyCasesActivity.ViewHolder holder, CaseModel model);

    void getPrettyTime(ControllerMyCasesActivity.ViewHolder holder, CaseModel model);

    void deleteMycase(ControllerMyCasesActivity.ViewHolder holder, CaseModel model);

    void editMycase(ControllerMyCasesActivity.ViewHolder holder, CaseModel model);

    void savedMycase(ControllerMyCasesActivity.ViewHolder holder, CaseModel model);

    void manageLayout(ControllerMyCasesActivity.ViewHolder holder, CaseModel model);

    void noCases(boolean show);

}
