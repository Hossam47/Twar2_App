package com.hossam.emergency.firebase;

import com.hossam.emergency.notification.NotificationModel;

import static com.hossam.emergency.firebase.FirebaseContract.getNotificationReference;

public class FirebaseNotification {

    private static final FirebaseNotification ourInstance = new FirebaseNotification();

    private FirebaseNotification() {
    }

    public static FirebaseNotification getInstance() {
        return ourInstance;
    }

    public void pushCommentNotification(NotificationModel model) {

        getNotificationReference().child(model.getReceiver_id()).child(model.getNotification_id()).setValue(model);

    }
}
