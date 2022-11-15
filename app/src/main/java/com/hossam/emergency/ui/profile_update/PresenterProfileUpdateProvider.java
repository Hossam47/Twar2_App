package com.hossam.emergency.ui.profile_update;

import android.net.Uri;

import com.hossam.emergency.ui.sign_up.User;

import java.util.ArrayList;

public interface PresenterProfileUpdateProvider {

    void onUpdated(User user);

    int isValidData(User user);

    void initMainUI();

    void settingsCheck();

    void uploadeData(User user);

    void updateUsername(String name);

    void updateEmail(String email);

    void updatePassword(String password);

    void setCurrentuserData();

    void getImagePath(ArrayList<Uri> path);

    void checkVerificationPhone();

    void onClickedPhoneButton();
}
