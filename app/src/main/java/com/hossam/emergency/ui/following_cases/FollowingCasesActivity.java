package com.hossam.emergency.ui.following_cases;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hossam.emergency.R;
import com.hossam.emergency.firebase.UserState;
import com.hossam.emergency.interfaces.ActivityHelper;
import com.victor.loading.rotate.RotateLoading;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FollowingCasesActivity extends AppCompatActivity implements ActivityHelper {

    @BindView(R.id.following_cases_recycler)
    RecyclerView followingCasesRecycler;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.no_cases_follow_container)
    ImageView no_cases_container;

    ContollerFollowingCases contollerFollowingCases;

    @BindView(R.id.rotate_loading_following)
    RotateLoading rotateLoadingFollowing;

    private final UserState userState = UserState.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following_cases);
        ButterKnife.bind(this);

        bindActivity();
        initActivity();
    }

    @Override
    public void bindActivity() {
        overridePendingTransition(R.anim.fade, R.anim.fade);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.following_cases_navigation));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        followingCasesRecycler.setLayoutManager(layoutManager);
        followingCasesRecycler.setNestedScrollingEnabled(false);
    }

    @Override
    public void initActivity() {

        contollerFollowingCases = new ContollerFollowingCases(this, followingCasesRecycler,
                no_cases_container, rotateLoadingFollowing);
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

}
