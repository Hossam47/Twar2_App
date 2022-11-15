package com.hossam.emergency.ui.list_cases;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.facebook.ads.AudienceNetworkAds;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.hossam.emergency.R;
import com.hossam.emergency.firebase.UserState;
import com.hossam.emergency.interfaces.ActivityHelper;
import com.hossam.emergency.utils.CoolTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListCasesActivity extends AppCompatActivity implements ActivityHelper {

    @BindView(R.id.toolbar_list_case)
    Toolbar toolbar;

    @BindView(R.id.list_cases_recycler)
    RecyclerView recyclerView;

//    @BindView(R.id.list_scroll)
//    ScrollView scroll;

    @BindView(R.id.style_bar_container)
    CardView style_bar;

    @BindView(R.id.timeline_filter_image)
    ImageView timelineFilterImage;

//    @BindView(R.id.timeline_filter_text)
//    CoolTextView timelineFilterText;

    @BindView(R.id.timeline_filter_container)
    LinearLayout timelineFilterContainer;

    @BindView(R.id.fire_filter_image)
    ImageView fireFilterImage;

    @BindView(R.id.fire_filter_text)
    CoolTextView fireFilterText;

    @BindView(R.id.fire_filter_container)
    LinearLayout fireFilterContainer;

    @BindView(R.id.near_filter_image)
    ImageView nearFilterImage;

    @BindView(R.id.near_filter_text)
    CoolTextView nearFilterText;

    @BindView(R.id.near_filter_container)
    LinearLayout nearFilterContainer;

    @BindView(R.id.saved_filter_image)
    ImageView savedFilterImage;

    @BindView(R.id.saved_filter_text)
    CoolTextView savedFilterText;

    @BindView(R.id.saved_filter_container)
    LinearLayout savedFilterContainer;

    @BindView(R.id.timeline_filter_text)
    CoolTextView timelineFilterText;

//    @BindView(R.id.list_scroll)
//    ScrollView list_scroll;

    //    @BindView(R.id.empty_view)
//    RelativeLayout emptyView;


    @BindView(R.id.app_bar)
    AppBarLayout appBar;

    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;

    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;

    @BindView(R.id.progress_list_cases)
    ProgressBar progressListCases;

//    @BindView(R.id.empty_view)
//    RelativeLayout emptyView;

    @BindView(R.id.no_data_image)
    ImageView noDataImage;

    StyleBarUi styleBarUi;
    ControllerListCases controllerListCases;


    private RecyclerView.LayoutManager layoutManager;
    private final UserState userState = UserState.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_cases);
        ButterKnife.bind(this);
        AudienceNetworkAds.initialize(this);
        bindActivity();
        initActivity();


    }

    @Override
    public void bindActivity() {
        overridePendingTransition(R.anim.fade, R.anim.fade_out);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.cases_main));
//        getSupportActionBar().setIcon(this.getResources().getDrawable(R.drawable.baseline_whatshot_white_24));

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setNestedScrollingEnabled(false);

        styleBarUi = new StyleBarUi(this, timelineFilterImage, fireFilterImage, nearFilterImage,
                savedFilterImage, timelineFilterText, fireFilterText,
                nearFilterText, savedFilterText, timelineFilterContainer,
                fireFilterContainer, nearFilterContainer, savedFilterContainer);

        styleBarUi.setMainStyleBar(style_bar);
        styleBarUi.setEmptyView(null);
        styleBarUi.setSwipeContainer(swipeContainer);
        styleBarUi.setProgressListCases(progressListCases);
        styleBarUi.setNoDataImage(noDataImage);


    }

    @Override
    public void initActivity() {

        controllerListCases = new ControllerListCases(this, styleBarUi, recyclerView, layoutManager);

    }

    //    @Override
//    protected void onStart() {
//        super.onStart();
//
//        userState.userOnline();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        userState.userOnline();
//
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//
//        userState.userOffline();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//
//        userState.userOffline();
//    }
//
    @Override
    protected void onDestroy() {
        super.onDestroy();

        userState.userOffline();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

