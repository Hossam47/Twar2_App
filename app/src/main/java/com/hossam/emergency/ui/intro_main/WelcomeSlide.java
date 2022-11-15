package com.hossam.emergency.ui.intro_main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.hossam.emergency.R;
import com.hossam.emergency.interfaces.ActivityHelper;
import com.hossam.emergency.utils.CoolTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.dreierf.materialintroscreen.SlideFragment;

public class WelcomeSlide extends SlideFragment implements ActivityHelper {

    @BindView(R.id.coolTextView2)
    CoolTextView coolTextView2;
    @BindView(R.id.coolTextView)
    CoolTextView coolTextView;
    @BindView(R.id.imageView2)
    ImageView imageView2;
    @BindView(R.id.source_btn)
    CoolTextView sourceBtn;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.welcome_slide_layout, container, false);

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
        return R.color.yellow5;
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

        sourceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "https://devpost.com/software/twar2-app-bewh02";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }
}
