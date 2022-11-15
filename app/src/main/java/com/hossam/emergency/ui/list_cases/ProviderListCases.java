package com.hossam.emergency.ui.list_cases;

import com.hossam.emergency.ui.cases.CaseModel;

import java.util.ArrayList;

public interface ProviderListCases {

    void initRecyclerView(int key);

    void initUI();

    void styleBar();

    void checkData(ArrayList<CaseModel> caseModels);

//    void updatedLoadMore(RecyclerView.LayoutManager layoutManager);

    void initSwipeRefresh();
}
