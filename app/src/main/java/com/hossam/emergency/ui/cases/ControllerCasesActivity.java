package com.hossam.emergency.ui.cases;

import android.app.Activity;
import android.location.Location;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.hossam.emergency.R;
import com.hossam.emergency.gps_provider.GpsController;
import com.hossam.emergency.interfaces.InitController;
import com.hossam.emergency.process.CompressionImages;
import com.hossam.emergency.services.Twar2App;
import com.hossam.emergency.ui.sign_up.User;
import com.hossam.emergency.time.GetTime;
import com.hossam.emergency.ui_component.UIComponenet;
import com.hossam.emergency.utils.ToastStyle;
import com.xw.repo.BubbleSeekBar;

import java.util.ArrayList;

import static com.hossam.emergency.firebase.FirebaseContract.getCasesCountryReference;
import static com.hossam.emergency.firebase.FirebaseContract.getCasesReference;
import static com.hossam.emergency.firebase.FirebaseContract.getCurrentUserID;
import static com.hossam.emergency.firebase.FirebaseContract.getMainReference;

public class ControllerCasesActivity extends UIComponenet implements InitController, CasesProvider {

    private final Activity activity;
    private final UiCasesActivity uiCases;
    private final CompressionImages compressionImages;
    private final GpsController gpsController;
    private final ToastStyle toastStyle;
    private int range = 0;
    private String caseType;
    private final Twar2App twar2App;

    public ControllerCasesActivity(Activity activity, UiCasesActivity uiCases) {
        this.activity = activity;
        this.uiCases = uiCases;
        toastStyle = new ToastStyle(activity);
        compressionImages = new CompressionImages(activity);
        gpsController = new GpsController(activity);
        twar2App = (Twar2App) activity.getApplicationContext();

        initController();
    }

    @Override
    public void initController() {
        initIndicatorSeekBar();
        initUi();

    }

    @Override
    public void vaildData(String caseType, final LatLng point, final ArrayList<Uri> path) {

        uiCases.getLunch().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(caseType) &&
                        !TextUtils.isEmpty(uiCases.getTitle().getText().toString()) &&
                        !TextUtils.isEmpty(uiCases.getDesc().getText().toString()) &&
                        range != 0 &&
                        point != null) {

                    pushData(caseType, uiCases.getTitle().getText().toString(), uiCases.getDesc().getText().toString(),
                            uiCases.getShow_profile().isChecked(), uiCases.getShow_mobile().isChecked(), point, path);

                } else if (TextUtils.isEmpty(caseType)) {

                    Toast.makeText(activity, "Please set Type of case ", Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(uiCases.getTitle().getText())) {

                    Toast.makeText(activity, "Please set title", Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(uiCases.getDesc().getText())) {

                    Toast.makeText(activity, "Please describe your state ", Toast.LENGTH_SHORT).show();

                } else if (point == null) {

                    Toast.makeText(activity, "Please set location of state ", Toast.LENGTH_SHORT).show();
                } else if (range == 0) {

                    Toast.makeText(activity, "Please set range ", Toast.LENGTH_SHORT).show();
                } else if (path == null) {

                    Toast.makeText(activity, "Please at least one image ", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public void pushData(String caseType, String title, String desc, boolean show_profile, boolean show_mobile, LatLng point, final ArrayList<Uri> path) {

        final String case_id = getCasesReference().push().getKey();

        final CaseModel caseModel = new CaseModel(caseType,title, desc, case_id, getCurrentUserID(), point.latitude, point.longitude, range, 0,
                GetTime.getUTCTimetamp(), show_profile, show_mobile, false, false);


        getMainReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);

//                if (user.getCountry() != null) {

                getCasesReference().child(case_id).setValue(caseModel);


                    if (path != null) {
                        uploadCaseImages(case_id, path);
                    }

                    activity.finish();

//                } else if (user.getCountry() == null) {
//
//                    new ToastStyle(activity).negativeToast("Please set your country first");
//                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    @Override
    public void initUi() {


    }

    @Override
    public void initIndicatorSeekBar() {

        uiCases.getmBbubbleSeekBar().getConfigBuilder()
                .min(10)
                .max(100)
                .progress(20)
                .sectionCount(9)
                .trackColor(ContextCompat.getColor(activity, R.color.grey_trans))
                .secondTrackColor(ContextCompat.getColor(activity, R.color.red_solid))
                .thumbColor(ContextCompat.getColor(activity, R.color.grey_trans))
                .sectionTextColor(ContextCompat.getColor(activity, R.color.grey_trans))
                .sectionTextSize(14)
                .thumbTextColor(ContextCompat.getColor(activity, R.color.red_solid))
                .thumbTextSize(14)
                .bubbleColor(ContextCompat.getColor(activity, R.color.red_solid_trans))
                .bubbleTextSize(18)
                .bubbleTextColor(ContextCompat.getColor(activity, R.color.white))
                .showSectionMark()
                .sectionTextPosition(BubbleSeekBar.TextPosition.BOTTOM_SIDES)
                .build();

        uiCases.getmBbubbleSeekBar().setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

                uiCases.getRange_details().setText(activity.getResources().getString(R.string.range_case)
                        + " " + progress + " " + activity.getResources().getString(R.string.km_case));
                range = progress;

            }

            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

            }

            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

            }
        });
    }

    @Override
    public void uploadCaseImages(String case_id, ArrayList<Uri> path) {

        compressionImages.uploadingMultiImages(path, case_id);

    }

    @Override
    public void addressCaseLocation(Location location, TextView textView) {

        gpsController.getCurrentAddress(location, textView);
    }


}

