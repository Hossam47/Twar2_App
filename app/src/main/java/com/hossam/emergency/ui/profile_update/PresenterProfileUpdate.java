package com.hossam.emergency.ui.profile_update;

import android.app.Activity;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.hossam.emergency.facebook_account_kit.ControllerFacebookAccountKit;
import com.hossam.emergency.firebase.UserMainInformation;
import com.hossam.emergency.process.CompressionImages;
import com.hossam.emergency.ui.sign_up.User;

import java.util.ArrayList;

import static com.hossam.emergency.firebase.FirebaseContract.getMainReference;
import static com.hossam.emergency.firebase.FirebaseContract.mAuthenticationUser;
import static com.hossam.emergency.utils.CommonUtils.isEmailValid;
import static com.hossam.emergency.utils.CommonUtils.isPasswordVaild;

public class PresenterProfileUpdate implements PresenterProfileUpdateProvider {

    private final ViewProfileUpdateProvider viewProfileUpdateProvider;
    private final UiProfileUpdatedActivity uiProfileUpdatedActivity;
    private int updatedKey;
    String verificationNumberPhone = null;
    ControllerFacebookAccountKit accountKit;
    private ArrayList<Uri> path = null;
    private final Activity activity;
    private final CompressionImages compressionImages;
    private final UserMainInformation userMainInformation = UserMainInformation.getInstance();
    private boolean check_image, check_message, check_call;

    public PresenterProfileUpdate(ViewProfileUpdateProvider viewProfileUpdateProvider, UiProfileUpdatedActivity uiProfileUpdatedActivity, Activity activity) {
        this.viewProfileUpdateProvider = viewProfileUpdateProvider;
        this.uiProfileUpdatedActivity = uiProfileUpdatedActivity;
        this.activity = activity;

        accountKit = new ControllerFacebookAccountKit(activity);
        compressionImages = new CompressionImages(activity);

        initMainUI();
    }

    @Override
    public void onUpdated(User user) {

        updatedKey = isValidData(user);

        if (updatedKey == 7) {

            uploadeData(user);

            viewProfileUpdateProvider.onUpdateSuccess("Your Profile is updated");

        } else {

            viewProfileUpdateProvider.onUpdateFalied("Fail in update");
        }
    }

    @Override
    public int isValidData(User user) {

        if (TextUtils.isEmpty(user.getUsername())) {
            return 0;
        } else if (TextUtils.isEmpty(user.getPhone())) {
            return 1;
        } else if (!isEmailValid(user.getEmail())) {
            return 2;
        } else if (!isPasswordVaild(user.getPassword())) {
            return 3;
        } else return 7;
    }

    @Override
    public void initMainUI() {


        uiProfileUpdatedActivity.getCheck_image().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                check_image = isChecked;
            }
        });

        uiProfileUpdatedActivity.getCheck_message().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                check_message = isChecked;
            }
        });


        uiProfileUpdatedActivity.getCheck_call().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                check_call = isChecked;
            }
        });


    }

    @Override
    public void settingsCheck() {

        getMainReference().child("image_available").setValue(check_image);

        getMainReference().child("message_available").setValue(check_message);

        getMainReference().child("call_available").setValue(check_call);
    }

    @Override
    public void uploadeData(User user) {

        updateUsername(user.getUsername());
        updateEmail(user.getEmail());
        updatePassword(user.getPassword());
        getMainReference().child("phone").setValue(user.getPhone());
        settingsCheck();

        if (path != null) {
            compressionImages.uploadingSingleImages(path);
        }
    }

    @Override
    public void updateUsername(final String name) {

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();

        mAuthenticationUser().updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            getMainReference().child("username").setValue(name);


                        }
                    }
                });
    }

    @Override
    public void updateEmail(final String email) {

        mAuthenticationUser().updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    getMainReference().child("email").setValue(email);

                }
            }
        });
    }

    @Override
    public void updatePassword(final String password) {

        mAuthenticationUser().updatePassword(password)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            getMainReference().child("password").setValue(password);
                        }

                        task.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(activity, "Sign out and log in then try again ", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

    }

    @Override
    public void setCurrentuserData() {

        userMainInformation.getCurrentUserFullInformation(uiProfileUpdatedActivity.getUsername(), uiProfileUpdatedActivity.getEmail()
                , uiProfileUpdatedActivity.getPhone(), uiProfileUpdatedActivity.getPassword(), uiProfileUpdatedActivity.getProfile_image(), activity);

        getMainReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);

                if (dataSnapshot.hasChild("call_available")) {

                    uiProfileUpdatedActivity.getCheck_call().setChecked(user.isCall_available());
                }

                if (dataSnapshot.hasChild("image_available")) {

                    uiProfileUpdatedActivity.getCheck_image().setChecked(user.isImage_available());
                }

                if (dataSnapshot.hasChild("message_available")) {

                    uiProfileUpdatedActivity.getCheck_message().setChecked(user.isMessage_available());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void getImagePath(ArrayList<Uri> path) {

        this.path = path;
    }

    @Override
    public void checkVerificationPhone() {

        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
            @Override
            public void onSuccess(Account account) {

                verificationNumberPhone = String.valueOf(account.getPhoneNumber().getPhoneNumber());
                uiProfileUpdatedActivity.getPhone().setText(account.getPhoneNumber().toString());

            }

            @Override
            public void onError(AccountKitError accountKitError) {

            }
        });
    }

    @Override
    public void onClickedPhoneButton() {

        accountKit.verificationPhone();

    }

}
