package com.hossam.emergency.ui.detalis_cases;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.hossam.emergency.R;
import com.hossam.emergency.ui.cases.CaseModel;
import com.hossam.emergency.firebase.UserState;
import com.hossam.emergency.interfaces.ActivityHelper;
import com.hossam.emergency.utils.CoolTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.hossam.emergency.firebase.FirebaseContract.getCommentsReference;

//import static com.hossam.emergency.ads.GoogleAds.interstitialAdsGoogle;

public class DetailsCases extends AppCompatActivity implements ActivityHelper {

    @BindView(R.id.toolbar_details_cases)
    Toolbar toolbar;

    @BindView(R.id.images_cases_details_recyclerView)
    RecyclerView images_recyclerView;

    @BindView(R.id.comments_cases_details_recyclerView)
    RecyclerView comments_recyclerView;

    @BindView(R.id.image_details_part_one)
    ImageView imageDetails;

    @BindView(R.id.name_details_part_one)
    CoolTextView nameDetails;

    @BindView(R.id.distance_details_part_one)
    CoolTextView distanceDetails;

    @BindView(R.id.time_details_part_one)
    CoolTextView timeDetails;

    @BindView(R.id.title_details_part_one)
    CoolTextView titleDetails;

    @BindView(R.id.comments_numbers)
    CoolTextView number_comments;

    @BindView(R.id.phone_details_part_one)
    CoolTextView buttonPhone;

//    @BindView(R.id.message_details_part_one)
//    Button buttonMessage;

    @BindView(R.id.street_details_part_one)
    CoolTextView buttonStreet;

    @BindView(R.id.map_details_part_one)
    CoolTextView buttonMap;
//
//    @BindView(R.id.image_icon)
//    ImageView imageIcon;

    @BindView(R.id.comment_edit_text_details_cases)
    AutoCompleteTextView comment;

    @BindView(R.id.sender_icon_text_details_cases)
    ImageView senderIcon;

    @BindView(R.id.description_details_cases)
    CoolTextView descriptionDetailsCases;

    @BindView(R.id.call_btn_container)
    LinearLayout callBtnContainer;

    @BindView(R.id.street_btn_container)
    LinearLayout streetBtnContainer;

    @BindView(R.id.map_btn_container)
    LinearLayout mapBtnContainer;

    @BindView(R.id.saved_message_container)
    RelativeLayout saved_message_container;

    @BindView(R.id.message_container_details)
    RelativeLayout messageContainer;

    @BindView(R.id.icon_follow_details_part_one)
    ImageView iconFollowDetailsPartOne;

    @BindView(R.id.title_follow_details_part_one)
    CoolTextView titleFollowDetailsPartOne;

    @BindView(R.id.follow_case_details_part_one)
    LinearLayout followCaseDetailsPartOne;

    @BindView(R.id.container_main_feature)
    LinearLayout containerMainFeature;

    @BindView(R.id.image_direct_message_details_part_one)
    ImageView imageDirectMessageDetailsPartOne;

    @BindView(R.id.direct_message_details_part_one)
    CoolTextView directMessageDetailsPartOne;

    @BindView(R.id.direct_message_btn_container)
    LinearLayout directMessageBtnContainer;

//    @BindView(R.id.small_banner_details)
//    LinearLayout small_banner_details;

//    @BindView(R.id.ads_small_banner_details)
//    AdView ads_small_banner_details;

    ControllerDetailsCases controllerDetailsCases;
    UIDetalisCases uiDetalisCases;
    CaseModel caseModel;

    private final UserState userState = UserState.getInstance();

    InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_cases);
        ButterKnife.bind(this);

        bindActivity();

    }

    @Override
    public void bindActivity() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        overridePendingTransition(R.anim.fade, R.anim.fade_out);

        images_recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));

        comments_recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));

        comments_recyclerView.setNestedScrollingEnabled(false);

        uiDetalisCases = new UIDetalisCases(images_recyclerView, comments_recyclerView, descriptionDetailsCases,
                imageDetails, null, nameDetails, distanceDetails,
                timeDetails, titleDetails, senderIcon, buttonPhone, null,
                buttonStreet, buttonMap, comment);

        uiDetalisCases.setNumber_comments(number_comments);
        uiDetalisCases.setCall_container(callBtnContainer);
        uiDetalisCases.setStreet_container(streetBtnContainer);
        uiDetalisCases.setMap_container(mapBtnContainer);
        uiDetalisCases.setSaved_message_container(saved_message_container);
        uiDetalisCases.setMessageContainer(messageContainer);
        uiDetalisCases.setFollowCaseDetailsPartOne(followCaseDetailsPartOne);
        uiDetalisCases.setIconFollowDetailsPartOne(iconFollowDetailsPartOne);
        uiDetalisCases.setTitleFollowDetailsPartOne(titleFollowDetailsPartOne);
        uiDetalisCases.setContainerMainFeature(containerMainFeature);
        uiDetalisCases.setDirectMessageBtnContainer(directMessageBtnContainer);
        uiDetalisCases.setImageDirectMessageDetailsPartOne(imageDirectMessageDetailsPartOne);
        uiDetalisCases.setDirectMessageDetailsPartOne(directMessageDetailsPartOne);

    }

    @Override
    public void initActivity() {

        controllerDetailsCases = new ControllerDetailsCases(this, uiDetalisCases, caseModel);

        getCommentsReference().child(caseModel.getCase_id()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String numbers = String.valueOf(dataSnapshot.getChildrenCount());
                number_comments.setText(numbers + " Comment");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        interstitialAdsGoogle(this);
    }


    @Override
    protected void onStart() {
        super.onStart();


        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {

            userState.userOnline();

            caseModel = (CaseModel) bundle.getSerializable("case_model");
            getSupportActionBar().setTitle(caseModel.getTitle());
            initActivity();
            controllerDetailsCases.pushView();

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
