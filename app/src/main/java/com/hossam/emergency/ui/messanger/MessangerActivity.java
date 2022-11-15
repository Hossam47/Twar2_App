package com.hossam.emergency.ui.messanger;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hossam.emergency.R;
import com.hossam.emergency.adapters.ImageAdapter;
import com.hossam.emergency.firebase.UserState;
import com.hossam.emergency.interfaces.ActivityHelper;
import com.hossam.emergency.ui.chat.ChatModel;
import com.hossam.emergency.utils.FillColor;
import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter;
import com.sangcomz.fishbun.define.Define;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessangerActivity extends AppCompatActivity implements ActivityHelper, ViewMessenger {

    @BindView(R.id.recycler_messenger)
    RecyclerView recyclerMessenger;

    @BindView(R.id.image_icon)
    ImageView imageIcon;

    @BindView(R.id.message_edit_text)
    AutoCompleteTextView messageEditText;

    @BindView(R.id.icon_sender_messenger)
    ImageView iconSenderMessenger;

    @BindView(R.id.message_container)
    RelativeLayout messageContainer;

    @BindView(R.id.toolbar_messenger)
    Toolbar toolbarMessenger;

    private ChatModel chatModel;
    private PresenterMessenger presenterMessenger;
    private UiMessengerActivity uiMessengerActivity;
    private final UserState userState = UserState.getInstance();
    private ArrayList<Uri> path = null;
    private ImageAdapter imageAdapter;
    private final FillColor fillColor = FillColor.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messanger);
        ButterKnife.bind(this);

        bindActivity();
        initActivity();

    }


    @Override
    public void bindActivity() {
        overridePendingTransition(R.anim.fade, R.anim.fade_out);
        setSupportActionBar(toolbarMessenger);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        recyclerMessenger.setLayoutManager(layoutManager);
        uiMessengerActivity = new UiMessengerActivity(imageIcon, iconSenderMessenger, messageEditText, messageContainer);


    }

    @Override
    public void initActivity() {

        presenterMessenger = new PresenterMessenger(this, this, uiMessengerActivity);
        presenterMessenger.initMessageComponent();
        presenterMessenger.onClickSenderMessages();

    }


    private void getImages() {

        FishBun.with(this)
                .setImageAdapter(new GlideAdapter())
                .setIsUseDetailView(false)
                .setMaxCount(1)
                .setMinCount(1)
                .setPickerSpanCount(3)
                .setActionBarColor(Color.parseColor("#6e787878"), Color.parseColor("#6e787878"), true)
                .setActionBarTitleColor(Color.parseColor("#ffffff"))
                .setAlbumSpanCount(2, 3)
                .setButtonInAlbumActivity(false)
                .setCamera(true)
                .setReachLimitAutomaticClose(true)
                .setAllViewTitle("All")
                .setActionBarTitle("Image Library")
                .textOnImagesSelectionLimitReached("Limit Reached!")
                .textOnNothingSelected("Nothing Selected")
                .startAlbum();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case Define.ALBUM_REQUEST_CODE:

                if (resultCode == RESULT_OK) {

                    path = data.getParcelableArrayListExtra(Define.INTENT_PATH);
                    imageAdapter = new ImageAdapter(this, path);
                    fillColor.fillColorGreen(uiMessengerActivity.getImageIcon(), this);

                    presenterMessenger.setPath(path);

                    break;
                }


        }


    }

    @Override
    protected void onStart() {
        super.onStart();

        Bundle bundle = getIntent().getExtras();

        chatModel = (ChatModel) bundle.getSerializable("chat_model");
        String other_id = bundle.getString("other_id");

        if (chatModel != null) {

            chatModel = (ChatModel) bundle.getSerializable("chat_model");
            presenterMessenger.converterData(chatModel);
            presenterMessenger.usernameChat(chatModel.getOther_user(), toolbarMessenger);

        } else if (other_id != null) {

            presenterMessenger.checkUserChatExist(other_id);
            presenterMessenger.usernameChat(other_id, toolbarMessenger);
            presenterMessenger.setOther_id(other_id);

        }

        userState.userOnline();
    }

    @Override
    public void initMessengerRecyclerView(MessagerAdapter adapter, int size) {

        recyclerMessenger.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        recyclerMessenger.scrollToPosition(0);


    }

    @Override
    public void onClickedViewImages() {
        getImages();
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

}
