package com.hossam.emergency.ui.sign_up;

import static com.hossam.emergency.firebase.FirebaseContract.getCurrentUserID;
import static com.hossam.emergency.firebase.FirebaseContract.getMainReference;
import static com.hossam.emergency.firebase.FirebaseContract.mAuthentication;
import static com.hossam.emergency.time.GetTime.getUTCTimetamp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.iid.FirebaseInstanceId;
import com.hbb20.CountryCodePicker;
import com.hossam.emergency.R;
import com.hossam.emergency.facebook_account_kit.ControllerFacebookAccountKit;
import com.hossam.emergency.process.CompressionImages;
import com.hossam.emergency.process.UploadProcess;
import com.hossam.emergency.ui_component.UIComponenet;
import com.hossam.emergency.utils.StringResouces;
import com.hossam.emergency.utils.ToastStyle;

import java.util.ArrayList;

public class ControllerSignUp extends UIComponenet implements SignUpProvider {

    Activity activity;
    UiSignUp uiSignUp;
    StringResouces resouces = StringResouces.getInstance();
    UploadProcess uploadProcess;
    String verificationNumberPhone = null;
    String country_name;

    ToastStyle toastStyle;
    CompressionImages compressionImages;
    ControllerFacebookAccountKit accountKit;

    public ControllerSignUp(Activity activity, UiSignUp uiSignUp) {
        this.activity = activity;
        this.uiSignUp = uiSignUp;
        compressionImages = new CompressionImages(activity);
//        accountKit = new ControllerFacebookAccountKit(activity);
        uploadProcess = new UploadProcess(activity);
        toastStyle = new ToastStyle(activity);
        initProgress(resouces.getStringResource(R.string.please_waiting_progress_message, activity), activity);
        termsReading();
        getCountery();
    }

    @Override
    public void validData(final ArrayList<Uri> path) {

        uiSignUp.getSign_up().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(uiSignUp.getUsername().getText())
                        && !TextUtils.isEmpty(uiSignUp.getEmail().getText())
                        && uiSignUp.getEmail().getText().toString().contains("@")
                        && uiSignUp.getPhone().getText().length() >= 11
                        && !TextUtils.isEmpty(uiSignUp.getPass().getText())
                        && uiSignUp.getPass().getText().length() >= 7
                        && path != null
                        && country_name != null
                        && uiSignUp.getCheckBox_terms().isChecked()
                ) {

                    createAccount(uiSignUp.getUsername().getText().toString(), uiSignUp.getEmail().getText().toString(),
                            uiSignUp.getPhone().getText().toString(), uiSignUp.getPass().getText().toString(), path);

                } else if (TextUtils.isEmpty(uiSignUp.getUsername().getText())) {

                    uiSignUp.getUsername().setError(resouces.getStringResource(R.string.username_create, activity));

                } else if (!uiSignUp.getEmail().getText().toString().contains("@")) {

                    uiSignUp.getEmail().setError(resouces.getStringResource(R.string.email_format, activity));

                } else if (TextUtils.isEmpty(uiSignUp.getEmail().getText())) {

                    uiSignUp.getEmail().setError(resouces.getStringResource(R.string.email_create, activity));

                } else if (TextUtils.isEmpty(uiSignUp.getPass().getText())) {

                    uiSignUp.getPass().setError(resouces.getStringResource(R.string.password_create, activity));

                } else if (uiSignUp.getPass().getText().length() < 7) {

                    uiSignUp.getPass().setError(resouces.getStringResource(R.string.password_length, activity));

                } else if (TextUtils.isEmpty(uiSignUp.getPhone().getText()) || uiSignUp.getPhone().getText().length() < 11) {

                    uiSignUp.getPhone().setError(resouces.getStringResource(R.string.mobile_create, activity));


                } else if (path == null) {

                    Toast.makeText(activity, resouces.getStringResource(R.string.choose_image_create, activity), Toast.LENGTH_SHORT).show();
                } else if (country_name == null) {

                    Toast.makeText(activity, resouces.getStringResource(R.string.choose_country_name, activity), Toast.LENGTH_SHORT).show();

                }
//                else if (!country_name.equals("egypt")) {
//
//                    Toast.makeText(activity, resouces.getStringResource(R.string.choose_country_egypt, activity), Toast.LENGTH_SHORT).show();
//
//                }
                else if (!uiSignUp.getCheckBox_terms().isChecked()) {

                    Toast.makeText(activity, "You must accept Twar2 app terms to use it", Toast.LENGTH_LONG).show();
                }

            }

        });

//        uiSignUp.getPhone().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                accountKit.verificationPhone();
//            }
//        });


    }

    @Override
    public void createAccount(final String username, final String email, final String phone, final String pass, final ArrayList<Uri> path) {

        showProgress();

        mAuthentication().createUserWithEmailAndPassword(uiSignUp.getEmail().getText().toString(), uiSignUp.getPass().getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isComplete()) {

                            getMainReference().setValue(new User(getCurrentUserID(), username, email, phone, pass,
                                    FirebaseInstanceId.getInstance().getToken(), "", country_name, getUTCTimetamp(),
                                    false, false, false, false));


                            if (path != null) {
                                compressionImages.uploadingSingleImages(path);
                            }
                            toastStyle.positiveToast("You are welcome :) ");

                            cancelProgress();

                            ClearData();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(activity, " Unsuccessfully : " + e, Toast.LENGTH_SHORT).show();


            }
        });

    }

    @Override
    public boolean getCountery() {


        country_name = uiSignUp.getCountryCodePicker().getSelectedCountryEnglishName().toLowerCase();

        uiSignUp.getCountryCodePicker().setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {

                country_name = uiSignUp.getCountryCodePicker().getSelectedCountryEnglishName().toLowerCase();

                if (country_name.equals("egypt")) {

//                    new ToastStyle(activity).positiveToast("مصرى !!, حبيبى والله , ولا ال server التانى ها , التانى ");
                }

            }
        });

        return false;
    }

    @Override
    public void ClearData() {

        uiSignUp.getUsername().setText("");
        uiSignUp.getEmail().setText("");
        uiSignUp.getPhone().setText("");
        uiSignUp.getPass().setText("");
        activity.finish();

    }

    @Override
    public void termsReading() {

        uiSignUp.getTermsText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://firebasestorage.googleapis.com/v0/b/emergency-47.appspot.com/o/Twar2_tems.pdf?alt=media&token=277a0e52-dad1-4121-8d19-dbb4d002cf88";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                activity.startActivity(i);
            }
        });

        uiSignUp.getTermsText().setPaintFlags(uiSignUp.getTermsText().getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

    }

    @Override
    public void checkVerificationPhone() {
//        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
//            @Override
//            public void onSuccess(Account account) {
//
//                verificationNumberPhone = String.valueOf(account.getPhoneNumber().getPhoneNumber());
//                uiSignUp.getPhone().setText(account.getPhoneNumber().toString());
//
//
//            }
//
//            @Override
//            public void onError(AccountKitError accountKitError) {
//
//            }
//        });
    }
}
