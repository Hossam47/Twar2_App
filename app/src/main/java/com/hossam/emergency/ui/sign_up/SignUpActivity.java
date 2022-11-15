package com.hossam.emergency.ui.sign_up;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.facebook.accountkit.AccountKitLoginResult;
import com.hbb20.CountryCodePicker;
import com.hossam.emergency.R;
import com.hossam.emergency.interfaces.ActivityHelper;
import com.hossam.emergency.process.UploadProcess;
import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter;
import com.sangcomz.fishbun.define.Define;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static com.hossam.emergency.constants.ConstantTags.REQUEST_CODE;

public class SignUpActivity extends AppCompatActivity implements ActivityHelper, EasyPermissions.PermissionCallbacks {

    @BindView(R.id.toolbar_sign_up)
    Toolbar toolbar;

    @BindView(R.id.image_sign_up)
    ImageView profile;

    @BindView(R.id.username_sign_up)
    EditText username;

    @BindView(R.id.email_sign_up)
    EditText email;

    @BindView(R.id.phone_sign_up)
    EditText phone;

    @BindView(R.id.password_sign_up)
    EditText password;

    @BindView(R.id.btn_sign_up)
    Button sign_up;

    @BindView(R.id.check_terms)
    CheckBox check_terms;

    @BindView(R.id.read_terms)
    TextView read_terms;

//    @BindView(R.id.country_text)
//    TextView country_text;

    @BindView(R.id.next_btn_sign_up)
    RelativeLayout nextBtnSignUp;

    UiSignUp uiSignUp;
    ControllerSignUp controller;
    UploadProcess uploadProcess;

    private ArrayList<Uri> path = null;
    private Bitmap img;
    private CountryCodePicker countryCodePicker;

    private static final String[] LOCATION_AND_STORAGE =
            {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            };

    private static final int RC_LOCATION_STORAGE = 124;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
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
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(this.getResources().getString(R.string.registration_activity));

        countryCodePicker = findViewById(R.id.ccp);

        uiSignUp = new UiSignUp(profile, username, email, phone, password, sign_up);
        uiSignUp.setCheckBox_terms(check_terms);
        uiSignUp.setTermsText(read_terms);
        uiSignUp.setNextBtnSignUp(nextBtnSignUp);
        uiSignUp.setCountryCodePicker(countryCodePicker);


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImages();
            }
        });
    }

    @Override
    public void initActivity() {
        uploadProcess = new UploadProcess(this);
        controller = new ControllerSignUp(this, uiSignUp);
        controller.validData(null);

        locationAndContactsTask();
    }


    private void getImages() {

        FishBun.with(this)
                .setImageAdapter(new GlideAdapter())
                .setIsUseDetailView(false)
                .setMaxCount(1)
                .setMinCount(1)
                .setPickerSpanCount(6).
                setActionBarColor(Color.parseColor("#607D8B"), Color.parseColor("#B3607D8B"), true)
                .setActionBarTitleColor(Color.parseColor("#ffffff"))
                .setAlbumSpanCount(2, 4)
                .setButtonInAlbumActivity(false)
                .setCamera(true)
                .setReachLimitAutomaticClose(true)
                .setAllViewTitle("All")
                .setActionBarTitle("Image Library")
                .textOnImagesSelectionLimitReached("Limit Reached!")
                .textOnNothingSelected("Nothing Selected")
                .startAlbum();

    }

    public void onActivityResult(int requestCode, int resultCode,
                                 Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case Define.ALBUM_REQUEST_CODE:

                if (resultCode == RESULT_OK) {
                    // path = imageData.getStringArrayListExtra(Define.INTENT_PATH);
                    // you can get an image path(ArrayList<String>) on <0.6.2

                    path = data.getParcelableArrayListExtra(Define.INTENT_PATH);
                    try {
                        img = UploadProcess.scaleDown(MediaStore.Images.Media.getBitmap(this.getContentResolver(), path.get(0)),
                                1000, true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    profile.setImageBitmap(img);

                    controller.validData(path);

                }
                break;


            case REQUEST_CODE:

                AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);

                if (loginResult.getError() != null) {

                    Toast.makeText(this, loginResult.getError().toString(), Toast.LENGTH_LONG).show();
                } else if (loginResult.wasCancelled()) {

                    Toast.makeText(this, "Cancel", Toast.LENGTH_LONG).show();

                }
//                else {
//
//                    if (loginResult.getAccessToken() != null) {
//
//                        controller.checkVerificationPhone();
//
//                    }
//                }

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


    @Override
    protected void onResume() {
        super.onResume();

        controller.checkVerificationPhone();
    }

    private boolean hasLocationAndContactsPermissions() {
        return EasyPermissions.hasPermissions(this, LOCATION_AND_STORAGE);
    }


    @AfterPermissionGranted(RC_LOCATION_STORAGE)
    public void locationAndContactsTask() {
        if (!hasLocationAndContactsPermissions()) {
            // Ask for both permissions
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.rationale_location_contacts),
                    RC_LOCATION_STORAGE,
                    LOCATION_AND_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }
}
