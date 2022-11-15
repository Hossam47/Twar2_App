package com.hossam.emergency.ui.cases;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.maps.model.LatLng;
import com.hossam.emergency.R;
import com.hossam.emergency.adapters.ImageAdapter;
import com.hossam.emergency.firebase.UserState;
import com.hossam.emergency.interfaces.ActivityHelper;
import com.hossam.emergency.services.Twar2App;
import com.hossam.emergency.ui.main.LocationActivity;
import com.hossam.emergency.utils.ToastStyle;
import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter;
import com.sangcomz.fishbun.define.Define;
import com.xw.repo.BubbleSeekBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//import static com.hossam.emergency.ads.GoogleAds.interstitialAdsGoogle;

public class CasesActivity extends AppCompatActivity implements ActivityHelper {

    public static final int REQUEST_CODE_LOCATION = 1047;

    @BindView(R.id.toolbar_case)
    androidx.appcompat.widget.Toolbar toolbar;
    @BindView(R.id.title_case)
    EditText title;
    @BindView(R.id.desc_case)
    EditText desc;
    @BindView(R.id.show_profile_case)
    CheckBox show_profile;
    @BindView(R.id.show_mobile_case)
    CheckBox show_mobile;
    @BindView(R.id.location_case)
    Button location;
    @BindView(R.id.images_case)
    Button images;
    @BindView(R.id.lunch_case)
    Button lunch;

    @BindView(R.id.range_details)
    TextView range_details;

    @BindView(R.id.images_recycler)
    RecyclerView images_recycler;

    @Nullable
    @BindView(R.id.seek_bar)
    BubbleSeekBar mBbubbleSeekBar;

    UiCasesActivity uiCasesActivity;
    ControllerCasesActivity controller;
    LatLng point;
    ArrayList<Uri> path = null;
    ImageAdapter imageAdapter;
    Twar2App twar2App;

    private final UserState userState = UserState.getInstance();
    private InterstitialAd interstitialAd;
    private SmartMaterialSpinner spProvince;
    private SmartMaterialSpinner spEmptyItem;
    private List<String> provinceList;
    private String caseType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cases);
        ButterKnife.bind(this);

        twar2App = (Twar2App) getApplicationContext();
        bindActivity();
        initActivity();
        initSpinner();
    }

    private String getResource(int id) {
        return getResources().getString(id);
    }

    private void initSpinner() {
        spProvince = findViewById(R.id.type_case_spinner);

        provinceList = new ArrayList<>();

        provinceList.add(getResource(R.string.accident_type_case));
        provinceList.add(getResource(R.string.kidnapping_type_case));
        provinceList.add(getResource(R.string.helping_type_case));
        provinceList.add(getResource(R.string.lost_found_type_case));


        spProvince.setItem(provinceList);

        spProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                caseType = provinceList.get(position);
                controller.vaildData(provinceList.get(position), point, path);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                new ToastStyle(CasesActivity.this).negativeToast(getResource(R.string.set_type_case));
            }
        });
    }


    @Override
    public void bindActivity() {
        overridePendingTransition(R.anim.fade, R.anim.fade_out);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.add_case));

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        uiCasesActivity = new UiCasesActivity(title, desc, show_profile, show_mobile, location, images, lunch);
        uiCasesActivity.setmBbubbleSeekBar(mBbubbleSeekBar);
        uiCasesActivity.setImages_recycler(images_recycler);
        uiCasesActivity.setRange_details(range_details);


        images_recycler.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));


    }

    @Override
    public void initActivity() {
        interstitialAd = new InterstitialAd(this);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = LocationActivity.makeIntent(CasesActivity.this);
                startActivityForResult(intent, REQUEST_CODE_LOCATION);

            }
        });

        images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getImages();
            }
        });

        controller = new ControllerCasesActivity(this, uiCasesActivity);

        controller.vaildData(caseType, point, path);
//        interstitialAdsGoogle(interstitialAd);

//        interstitialAd(this);
    }


    private void getImages() {

        FishBun.with(this)
                .setImageAdapter(new GlideAdapter())
                .setIsUseDetailView(false)
                .setMaxCount(5)
                .setMinCount(1)
                .setPickerSpanCount(3)
                .setActionBarColor(Color.parseColor("#607D8B"),
                        Color.parseColor("#B3607D8B"), true)
                .setActionBarTitleColor(Color.parseColor("#ffffff"))
                .setAlbumSpanCount(2, 3)
                .setButtonInAlbumActivity(false)
                .setCamera(true)
                .setReachLimitAutomaticClose(true)
                .setAllViewTitle(getResources().getString(R.string.all_image_library))
                .setActionBarTitle(getResources().getString(R.string.image_library))
                .textOnImagesSelectionLimitReached(getResources().getString(R.string.limite_image_library))
                .textOnNothingSelected(getResources().getString(R.string.select_image_library))
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
                    images_recycler.setAdapter(imageAdapter);
                    controller.vaildData(caseType, point, path);

                    images.setBackgroundResource(R.drawable.o_green);
                    images.setText("Image " + path.size());

                    break;
                }

            case REQUEST_CODE_LOCATION:

                if (resultCode == Activity.RESULT_OK) {

                    double lat = data.getDoubleExtra("lat", 0);
                    double lang = data.getDoubleExtra("lang", 0);

                    Location caseLocation = new Location("");
                    caseLocation.setLatitude(lat);
                    caseLocation.setLongitude(lang);

                    point = new LatLng(lat, lang);

                    location.setBackgroundResource(R.drawable.o_green);
                    controller.vaildData(caseType, point, path);
                    controller.addressCaseLocation(caseLocation, location);

                    break;
                }
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    //
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
