package com.hossam.emergency.notification;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.ads.AdView;
import com.hossam.emergency.R;
import com.hossam.emergency.firebase.UserState;
import com.hossam.emergency.interfaces.ActivityHelper;
import com.victor.loading.rotate.RotateLoading;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NotificationActivity extends AppCompatActivity implements ActivityHelper, ViewNotificationActivity {


    @BindView(R.id.notification_recycler_view)
    RecyclerView notificationRecyclerView;

    @BindView(R.id.toolbar_notification_activity)
    Toolbar toolbar;

    @BindView(R.id.rotate_loading_alerts)
    RotateLoading rotateLoadingAlerts;

    @BindView(R.id.no_notifications_container)
    ImageView no_notifications_container;

    //    @BindView(R.id.small_banner_notification)
//    LinearLayout small_banner_notification;
    @BindView(R.id.ads_small_banner_notifications)
    AdView ads_small_banner_notifications;
    private AdView adView;

    private PresenterNotificationActivity presenterNotificationActivity;
    private UiNotificationActivity uiNotificationActivity;
    private RecyclerView.LayoutManager layoutManager;
    private final UserState userState = UserState.getInstance();

//    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_scrolling_notification_layout);
        ButterKnife.bind(this);

        bindActivity();
        initActivity();
    }

    @Override
    public void bindActivity() {
        overridePendingTransition(R.anim.fade, R.anim.fade_out);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.alerts_main));

        layoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager).setReverseLayout(true);
        ((LinearLayoutManager) layoutManager).setStackFromEnd(true);

        notificationRecyclerView.setLayoutManager(layoutManager);

        uiNotificationActivity = new UiNotificationActivity(no_notifications_container, layoutManager, rotateLoadingAlerts);
    }

    @Override
    public void initActivity() {

        presenterNotificationActivity = new PresenterNotificationActivity(this, this,
                notificationRecyclerView, uiNotificationActivity);

        presenterNotificationActivity.initRecyclerView();
//        adView = alertFacebookBanner(small_banner_notification, this);
//        notificationBannerAdsGoogle(ads_small_banner_notifications);
//        adView = smallBannerAds(small_banner_notification, this);
//        adView.loadAd();


    }

    @Override
    public void initNotificationRecyclerView(FirebaseRecyclerAdapter<NotificationModel, PresenterNotificationActivity.ViewHolder> firebaseAdapter) {
        notificationRecyclerView.setAdapter(firebaseAdapter);
        firebaseAdapter.notifyDataSetChanged();
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


    @Override
    protected void onStart() {
        super.onStart();

//        notificationBannerAdsGoogle(ads_small_banner_notifications);
//        interstitialAdsGoogle(this);
    }

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

        if (adView != null) {

            adView.destroy();
        }
        userState.userOffline();
    }
}
