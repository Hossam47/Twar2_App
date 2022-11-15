package com.hossam.emergency.ui.intro_main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.hossam.emergency.R;
import com.hossam.emergency.interfaces.ActivityHelper;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.dreierf.materialintroscreen.SlideFragment;

public class Opening_slide extends SlideFragment implements ActivityHelper {

    Unbinder unbinder;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.opening_slide_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        bindActivity();
        initActivity();

        return view;
    }

    @Override
    public int backgroundColor() {
        return R.color.white_s;
    }

    @Override
    public int buttonsColor() {
        return R.color.primary_trans;
    }


    @Override
    public boolean canMoveFurther() {
        return true;
    }

    @Override
    public String cantMoveFurtherErrorMessage() {
        return getString(R.string.app_name);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void bindActivity() {
    }

    @Override
    public void initActivity() {
    }


}