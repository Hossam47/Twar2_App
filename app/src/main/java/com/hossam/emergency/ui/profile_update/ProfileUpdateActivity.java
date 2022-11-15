package com.hossam.emergency.ui.profile_update;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdLayout;
import com.hossam.emergency.R;
import com.hossam.emergency.firebase.UserState;
import com.hossam.emergency.interfaces.ActivityHelper;
import com.hossam.emergency.services.Twar2App;
import com.hossam.emergency.ui.sign_up.User;
import com.hossam.emergency.utils.ToastStyle;
import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter;
import com.sangcomz.fishbun.define.Define;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.hossam.emergency.ads.FacebookAds.interstitialAd;
import static com.hossam.emergency.constants.ConstantTags.REQUEST_CODE;
import static com.hossam.emergency.utils.CommonUtils.getTextFromComponent;

public class ProfileUpdateActivity extends AppCompatActivity implements ActivityHelper, ViewProfileUpdateProvider {

    @BindView(R.id.toolbar_update_layout)
    Toolbar toolbar;

    @BindView(R.id.image_update_layout)
    ImageView profile;

    @BindView(R.id.username_update_layout)
    EditText username;

    @BindView(R.id.email_update_layout)
    EditText email;

    @BindView(R.id.phone__update_layout)
    EditText phone;

    @BindView(R.id.password_update_layout)
    EditText password;

    @BindView(R.id.btn_update_layout)
    Button update_btn;

    @BindView(R.id.profile_check_update_layout)
    CheckBox check_image;

    @BindView(R.id.message_check_update_layout)
    CheckBox check_message;

    @BindView(R.id.call_check_update_layout)
    CheckBox check_call;

    private ToastStyle toastStyle;
    private UiProfileUpdatedActivity uiProfileUpdatedActivity;
    private PresenterProfileUpdateProvider presenterProfile;
    private ArrayList<Uri> path = null;
    private final UserState userState = UserState.getInstance();


    private final String TAG = ProfileUpdateActivity.class.getSimpleName();
    private NativeAd nativeAd;
    private NativeAdLayout nativeAdLayout;
    private LinearLayout adView;

    private AdView adViews;

    private InterstitialAd interstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);
        ButterKnife.bind(this);

        AudienceNetworkAds.initialize(this);

        bindActivity();
        initActivity();
    }

    @Override
    public void bindActivity() {
        overridePendingTransition(R.anim.fade, R.anim.fade);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.profile_navigation));

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        uiProfileUpdatedActivity = new UiProfileUpdatedActivity(profile, username, email, phone, password, update_btn);
        uiProfileUpdatedActivity.setCheck_image(check_image);
        uiProfileUpdatedActivity.setCheck_message(check_message);
        uiProfileUpdatedActivity.setCheck_call(check_call);
    }

    @Override
    public void initActivity() {

        toastStyle = ((Twar2App) getApplication()).getToastStyle();

        presenterProfile = new PresenterProfileUpdate(this, uiProfileUpdatedActivity, this);

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                presenterProfile.onUpdated(new User(getTextFromComponent(username), getTextFromComponent(email),
                        getTextFromComponent(phone), getTextFromComponent(password))
                );
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImages();
            }
        });

        uiProfileUpdatedActivity.getPhone().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                presenterProfile.onClickedPhoneButton();
            }
        });

        presenterProfile.setCurrentuserData();

        interstitialAd(this);
    }

    public void onActivityResult(int requestCode, int resultCode,
                                 Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case Define.ALBUM_REQUEST_CODE:

                if (resultCode == RESULT_OK) {

                    path = data.getParcelableArrayListExtra(Define.INTENT_PATH);
                    presenterProfile.getImagePath(path);

                    try {
                        uiProfileUpdatedActivity.getProfile_image().setImageBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(), path.get(0)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                break;

            case REQUEST_CODE:

                AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);

                if (loginResult.getError() != null) {

                    toastStyle.negativeToast("Something wrong");

                } else if (loginResult.wasCancelled()) {

                    toastStyle.negativeToast("Cancel");

                } else {

                    if (loginResult.getAccessToken() != null) {

                        presenterProfile.checkVerificationPhone();

                    }
                }

        }
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
    public void onUpdateSuccess(String message) {

        toastStyle.positiveToast(message);
    }

    @Override
    public void onUpdateFalied(String message) {

        toastStyle.negativeToast(message);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

        userState.userOffline();

        if (adViews != null) {
            adViews.destroy();
        }
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
