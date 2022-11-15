package com.hossam.emergency.utils.utils_activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hossam.emergency.R;
import com.hossam.emergency.animations.AnimationsUtils;
import com.hossam.emergency.interfaces.ActivityHelper;
import com.hossam.emergency.models.ImageModel;
import com.hossam.emergency.process.ImageProcess;
import com.hossam.emergency.utils.OnSwipeTouchListener;
import com.hossam.emergency.utils.ToastStyle;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DisplayImageActivity extends AppCompatActivity implements ActivityHelper {


    @BindView(R.id.images_swipe_recyclerView)
    RecyclerView images_swipe_recyclerView;

    @BindView(R.id.image_display)
    ImageView imageDisplay;

    @BindView(R.id.main_layout_display_activity)
    RelativeLayout mainLayoutDisplayActivity;

    DisplayImageController displayImageController;
    ToastStyle toastStyle;
    AnimationsUtils animationsUtils = AnimationsUtils.getInstance();
    ImageProcess imageProcess = ImageProcess.getInstance();
    View mDecorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image);
        ButterKnife.bind(this);
        bindActivity();
        initActivity();
    }


    @Override
    public void bindActivity() {
        mDecorView = getWindow().getDecorView();
        overridePendingTransition(R.anim.hold, R.anim.fade_in);
        toastStyle = new ToastStyle(this);
        hideSystemUI();

        images_swipe_recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    public void initActivity() {

        imageDisplay.setOnTouchListener(new OnSwipeTouchListener(getBaseContext()) {
            public void onSwipeTop() {

                finish();
                overridePendingTransition(R.anim.hold, R.anim.fade_out);
            }

            public void onSwipeRight() {
            }

            public void onSwipeLeft() {
            }

            public void onSwipeBottom() {

                finish();
                overridePendingTransition(R.anim.hold, R.anim.fade_out);
            }

        });

        displayImageController = new DisplayImageController(this, images_swipe_recyclerView);
    }

    @Override
    protected void onStart() {
        super.onStart();


        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {

            if (bundle.get("image_url") != null) {

                images_swipe_recyclerView.setVisibility(View.GONE);

                String image_url = (String) bundle.get("image_url");
                imageProcess.loadImage(imageDisplay, image_url, this);

            } else {

                imageDisplay.setVisibility(View.GONE);

                ArrayList<ImageModel> imageModels;
                imageModels = (ArrayList<ImageModel>) getIntent().getSerializableExtra("image_list");

                displayImageController.initRecyclerView(imageModels, 0);

            }
        }
    }

    //
//
    private void hideSystemUI() {
        mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    private void showSystemUI() {
        mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
}
