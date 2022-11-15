package com.hossam.emergency.ui.sign_in;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.hossam.emergency.R;
import com.hossam.emergency.animations.AnimationsUtils;
import com.hossam.emergency.ui.intro_main.IntroActivity;
import com.hossam.emergency.ui.main.MainActivity;
import com.hossam.emergency.rest_password_dialog.RestPasswordDialog;
import com.hossam.emergency.services.Twar2App;
import com.hossam.emergency.ui.sign_up.SignUpActivity;
import com.hossam.emergency.ui_component.UIComponenet;
import com.hossam.emergency.utils.StringResouces;

import static com.hossam.emergency.firebase.FirebaseContract.mAuthentication;

public class ControllerSignIn extends UIComponenet implements SignInProvider {

    Activity activity;
    UiSignIn uiSignIn;
    SharedPreferences sharedPref;
    String SHARED_PREF = "LOGIN_FILE";
    StringResouces resouces = StringResouces.getInstance();
    AnimationsUtils animationsUtils = AnimationsUtils.getInstance();
    Twar2App twar2App;

    public ControllerSignIn(final Activity activity, UiSignIn uiSignIn, Twar2App twar2App) {
        this.activity = activity;
        this.uiSignIn = uiSignIn;
        this.twar2App = twar2App;

        sharedPref = activity.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);

        initProgress(resouces.getStringResource(R.string.please_waiting_progress_message, activity), activity);

        getUser(uiSignIn.getEmail(), uiSignIn.getPassword());
        validData();

    }

    @Override
    public void validData() {

        uiSignIn.getSign_in().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                animationsUtils.animationReactionFade(uiSignIn.getSign_in(), activity);

                if (!TextUtils.isEmpty(uiSignIn.getEmail().getText().toString()) && !TextUtils.isEmpty(uiSignIn.getPassword().getText().toString())) {

                    signInAccout(uiSignIn.getEmail().getText().toString(), uiSignIn.getPassword().getText().toString());

                } else if (TextUtils.isEmpty(uiSignIn.getEmail().getText())) {
                    uiSignIn.getEmail().setError(resouces.getStringResource(R.string.username_create, activity));
                } else if (!uiSignIn.getPassword().getText().toString().contains("@")) {
                    uiSignIn.getPassword().setError(resouces.getStringResource(R.string.email_format, activity));
                }
            }
        });


    }

    @Override
    public void convertToSignUp() {

        uiSignIn.getSign_up().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                animationsUtils.animationReactionFade(uiSignIn.getSign_up(), activity);
                activity.startActivity(new Intent(activity, SignUpActivity.class));
            }
        });
    }

    @Override
    public void signInAccout(final String email, final String pass) {

        showProgress();

        final boolean check_intro = twar2App.getSharedPref().getBoolean("SHOW_SLIDES", false);

        mAuthentication().signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            cancelProgress();

                            if (uiSignIn.getRemember().isChecked()) {

                                saveUser(email, pass);

                            }

                            if (!check_intro) {
                                activity.startActivity(new Intent(activity, IntroActivity.class));
                                activity.finish();

                            } else {
                                activity.startActivity(new Intent(activity, MainActivity.class));
                                activity.finish();
                            }

                        } else if (!task.isSuccessful()) {

                            Toast.makeText(activity, resouces.getStringResource(R.string.wrong_data, activity),
                                    Toast.LENGTH_SHORT).show();
                            cancelProgress();
                        }


                        task.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.v("Sign_in_error", e.getMessage());
                            }
                        });


                    }
                });
    }

    @Override
    public void forgetPassword() {

        uiSignIn.getForget().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationsUtils.animationReactionFade(uiSignIn.getForget(), activity);

                RestPasswordDialog restPasswordDialog = new RestPasswordDialog(activity);

                restPasswordDialog.startDialog();
                restPasswordDialog.restPasswordTransaction();
            }
        });
    }

    private void saveUser(String email, String pass) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("Email_Key", email);
        editor.putString("Email_Pass", pass);
        editor.commit();

    }

    private void getUser(EditText email, EditText password) {
        email.setText(sharedPref.getString("Email_Key", ""));
        password.setText(sharedPref.getString("Email_Pass", ""));
    }


}
