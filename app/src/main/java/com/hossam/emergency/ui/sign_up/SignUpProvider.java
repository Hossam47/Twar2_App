package com.hossam.emergency.ui.sign_up;

import android.net.Uri;

import java.util.ArrayList;

public interface SignUpProvider {

    void validData(ArrayList<Uri> path);

    void createAccount(String username, String email, String phone, String pass, ArrayList<Uri> path);

    boolean getCountery();

    void ClearData();

    void termsReading();

    void checkVerificationPhone();
}
