package com.hossam.emergency.ui.chat;

import android.content.Intent;
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
import com.hossam.emergency.ui.messanger.MessangerActivity;
import com.hossam.emergency.utils.ToastStyle;
import com.victor.loading.rotate.RotateLoading;

import butterknife.BindView;
import butterknife.ButterKnife;


//import static com.hossam.emergency.ads.GoogleAds.interstitialAdsGoogle;
//import static com.hossam.emergency.ads.GoogleAds.smallBannerAdsGoogle;

public class ChatActivity extends AppCompatActivity implements ActivityHelper, ViewChat {

    @BindView(R.id.toolbar_chat_activity)
    Toolbar toolbar;

    @BindView(R.id.chat_recycler_view)
    RecyclerView chatRecyclerView;

//    @BindView(R.id.no_cases_chat_container)
//    LinearLayout no_cases_container;

//    @BindView(R.id.small_banner_chat)
//    LinearLayout small_banner_chat;

    @BindView(R.id.ads_small_banner_chat)
    AdView ads_small_banner_chat;

    PresenterChat presenterChat;
    ToastStyle toastStyle;

    @BindView(R.id.rotate_loading_chat)
    RotateLoading rotateLoadingChat;

    @BindView(R.id.no_data_image_chat)
    ImageView noDataImageChat;

    private final UserState userState = UserState.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
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
        getSupportActionBar().setTitle(getResources().getString(R.string.inbox_main));


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager).setReverseLayout(true);

        chatRecyclerView.setLayoutManager(layoutManager);

    }


    @Override
    public void initActivity() {

        toastStyle = new ToastStyle(this);

        presenterChat = new PresenterChat(this, this, rotateLoadingChat, noDataImageChat);
        presenterChat.initChatsRecyclerView(chatRecyclerView);
//        chatBannerAdsGoogle(ads_small_banner_chat);
//        adView = chatFacebookBanner(small_banner_chat, this);
    }

    @Override
    public void onClickMessage(ChatModel chatModel) {
        Intent intent = new Intent(ChatActivity.this, MessangerActivity.class);
        intent.putExtra("chat_model", chatModel);
        startActivity(intent);

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
    protected void onDestroy() {
        super.onDestroy();
        userState.userOffline();
    }

    @Override
    public void onRecyclerViewReady(FirebaseRecyclerAdapter<ChatModel, PresenterChat.ViewHolder> firebaseAdapter) {

    }

}
