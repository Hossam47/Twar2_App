package com.hossam.emergency.services;

import androidx.annotation.NonNull;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;

import static com.hossam.emergency.firebase.FirebaseContract.getCurrentUserID;
import static com.hossam.emergency.firebase.FirebaseContract.getMainReference;

/**
 * Created by hossam on 8/8/17.
 */

/**
 * Called if InstanceID token is updated. This may occur if the security of
 * the previous token had been compromised. Note that this is called when the InstanceID token
 * is initially generated so this is where you would retrieve the token.
 */

public class FCMInstanceIdService extends FirebaseMessagingService {


    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        sendRegistrationToServer(FirebaseInstanceId.getInstance().getToken());

    }

    private void sendRegistrationToServer(String token) {

        if (getCurrentUserID() != null) {

            getMainReference().child("token").setValue(token);

        }
    }

//    @Override
//    public void onTokenRefresh() {
//        super.onTokenRefresh();
//
//
//    }
//
//    /**
//     * Persist token to third-party servers.
//     * <p>
//     * Modify this method to associate the user's FCM InstanceID token with any server-side account
//     * maintained by your application.
//     *
//     * @param token The new token.
//     */

}
