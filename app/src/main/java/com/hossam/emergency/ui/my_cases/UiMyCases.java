package com.hossam.emergency.ui.my_cases;

import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.victor.loading.rotate.RotateLoading;

public class UiMyCases {

    Toolbar toolbar;
    RecyclerView myCasesRecycler;
    ImageView no_cases_container;
    RotateLoading rotateLoadingMycases;

    public UiMyCases(Toolbar toolbar, RecyclerView myCasesRecycler, ImageView no_cases_container, RotateLoading rotateLoadingMycases) {
        this.toolbar = toolbar;
        this.myCasesRecycler = myCasesRecycler;
        this.no_cases_container = no_cases_container;
        this.rotateLoadingMycases = rotateLoadingMycases;
    }


    public Toolbar getToolbar() {
        return toolbar;
    }

    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
    }

    public RecyclerView getMyCasesRecycler() {
        return myCasesRecycler;
    }

    public void setMyCasesRecycler(RecyclerView myCasesRecycler) {
        this.myCasesRecycler = myCasesRecycler;
    }

    public ImageView getNo_cases_container() {
        return no_cases_container;
    }

    public void setNo_cases_container(ImageView no_cases_container) {
        this.no_cases_container = no_cases_container;
    }

    public RotateLoading getRotateLoadingMycases() {
        return rotateLoadingMycases;
    }
}
