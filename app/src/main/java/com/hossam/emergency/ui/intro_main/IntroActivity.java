package com.hossam.emergency.ui.intro_main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.FloatRange;
import androidx.annotation.Nullable;

import com.hossam.emergency.ui.main.MainActivity;

import io.github.dreierf.materialintroscreen.MaterialIntroActivity;
import io.github.dreierf.materialintroscreen.animations.IViewTranslation;

public class IntroActivity extends MaterialIntroActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableLastSlideAlphaExitTransition(true);

        getBackButtonTranslationWrapper()
                .setEnterTranslation(new IViewTranslation() {
                    @Override
                    public void translate(View view, @FloatRange(from = 0, to = 1.0) float percentage) {
                        view.setAlpha(percentage);
                    }
                });


        addSlide(new Opening_slide());

        addSlide(new WelcomeSlide());

        //    addSlide(new GuideSlide());

        addSlide(new InitializeSlide());

        addSlide(new FinishSlide());

//        addSlide(new SlideFragmentBuilder()
//                        .backgroundColor(R.color.red_trans_150)
//                        .buttonsColor(R.color.grey_trans_50)
//                        .image(R.drawable.ic_launcher_background)
//                        .title("Organize your time with us")
//                        .description("Would you try?")
//                        .build(),
//                new MessageButtonBehaviour(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        showMessage("We provide solutions to make you love your work");
//                    }
//                }, "Work with love"));
//
//
//
//        addSlide(new SlideFragmentBuilder()
//                .backgroundColor(R.color.colorPrimaryDark)
//                .buttonsColor(R.color.colorAccent)
//                .title("Want more?")
//                .description("Go on")
//                .build());
//
//        addSlide(new SlideFragmentBuilder()
//                        .backgroundColor(R.color.colorPrimaryDark)
//                        .buttonsColor(R.color.colorAccent)
//                        .neededPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
//                                Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CALL_PHONE})
//                        .image(R.drawable.ic_finish)
////                        .title("محتاجين من حضرتك شوية permissions ,عشان ندخلك على التطبيق , هتنشف دماغك ومش هتدينى ال انا عاوزه , طلاق تلاته ما هدخلك التطبيق لو جيبتلى Google CEO ")
//                        .description("يا انا ياانت فى التطبيق ده ")
//                        .build(),
//
//                new MessageButtonBehaviour(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        showMessage("Try us!");
//                    }
//                }, "عدى يا باشا , افتح الطريق يابنى ! "));
//
//        addSlide(new SlideFragmentBuilder()
//                .backgroundColor(R.color.colorPrimary)
//                .buttonsColor(R.color.colorAccent)
//                .title("That's it")
//                .description("Would you join us?")
//                .build());
    }

    @Override
    public void onFinish() {
        super.onFinish();

        startActivity(new Intent(this, MainActivity.class));

    }
}