package com.hossam.emergency.ui.intro_main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.Nullable;

import com.hossam.emergency.R;
import com.hossam.emergency.interfaces.ActivityHelper;
import com.hossam.emergency.services.Twar2App;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.dreierf.materialintroscreen.SlideFragment;

public class FinishSlide extends SlideFragment implements ActivityHelper {

    @BindView(R.id.checkbox_finish_slide)
    CheckBox checkboxFinishSlide;

    Unbinder unbinder;
    private Twar2App twar2App;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.finish_slide_layout, container, false);
        unbinder = ButterKnife.bind(this, view);

        bindActivity();
        initActivity();

        return view;
    }

    @Override
    public void bindActivity() {
    }

    @Override
    public void initActivity() {

        twar2App = (Twar2App) getActivity().getApplicationContext();
        twar2App.getSharedPref();
    }

    @Override
    public int backgroundColor() {
        return R.color.white_s;
    }

    @Override
    public int buttonsColor() {
        return R.color.intro_color1;
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

        twar2App.saveCachesIntro(checkboxFinishSlide.isChecked());
        unbinder.unbind();
    }
}
