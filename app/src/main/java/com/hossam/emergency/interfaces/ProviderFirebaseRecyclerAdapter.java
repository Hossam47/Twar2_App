package com.hossam.emergency.interfaces;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.Query;

/**
 * Created by hossam on 2/19/18.
 */

public interface ProviderFirebaseRecyclerAdapter {


    void initRecyclerView(Query query);

    void setRecyclerData(RecyclerView.ViewHolder holder, Object model);


}
