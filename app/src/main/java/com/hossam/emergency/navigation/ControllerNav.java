package com.hossam.emergency.navigation;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.accountkit.AccountKit;
import com.google.android.material.navigation.NavigationView;
import com.hossam.emergency.R;
import com.hossam.emergency.firebase.UserMainInformation;
import com.hossam.emergency.ui.following_cases.FollowingCasesActivity;
import com.hossam.emergency.ui.my_cases.MyCasesActivity;
import com.hossam.emergency.ui.profile_update.ProfileUpdateActivity;
import com.hossam.emergency.services.FCMService;
import com.hossam.emergency.services.ServiceLocation;
import com.hossam.emergency.ui.sign_in.SignInActivity;

import static com.hossam.emergency.firebase.FirebaseContract.mAuthentication;


public class ControllerNav implements ProviderNav {

    private final Activity activity;
    private ImageView profileImage;
    private TextView profileName;
    private final UserMainInformation userMainInformation = UserMainInformation.getInstance();


    public ControllerNav(Activity activity) {
        this.activity = activity;
        initNavUi();
        setNavData();
    }

    @Override
    public void initNavUi() {

        NavigationView navigationView = activity.findViewById(R.id.nav_view);
        View view = navigationView.getHeaderView(0);

        profileImage = view.findViewById(R.id.user_image_nav);
        profileName = view.findViewById(R.id.user_name_nav);

    }

    @Override
    public void userProfile() {
        activity.startActivity(new Intent(activity, ProfileUpdateActivity.class));

    }

    @Override
    public void setNavData() {

        userMainInformation.getCurrentUserInformation(profileName, profileImage, activity);
    }

    @Override
    public void myCases() {

        activity.startActivity(new Intent(activity, MyCasesActivity.class));

    }

    @Override
    public void followingCases() {
        activity.startActivity(new Intent(activity, FollowingCasesActivity.class));
    }

    @Override
    public void share() {

        String packageName = activity.getPackageName();
        Intent playStoreIntent = new Intent();
        playStoreIntent.setAction(Intent.ACTION_SEND);
        playStoreIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=" + packageName);
        playStoreIntent.setType("text/plain");
        activity.startActivity(Intent.createChooser(playStoreIntent, "choose one"));
    }


    @Override
    public void about() {
        // activity.startActivity(new Intent(activity, DeveloperActivity.class));
    }

    @Override
    public void settings() {

        // activity.startActivity(new Intent(activity, SettingsActivity.class));
    }

    @Override
    public void signOut() {

        AccountKit.logOut();
        activity.stopService(new Intent(activity, ServiceLocation.class));
        activity.stopService(new Intent(activity, FCMService.class));
        activity.stopService(new Intent(activity, ServiceLocation.class));
        activity.startActivity(new Intent(activity, SignInActivity.class));
        activity.finish();
        mAuthentication().signOut();


    }

    @Override
    public void rateUs() {
        final String packageName = activity.getPackageName();
        activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));

    }
}
