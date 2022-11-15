package com.hossam.emergency.notification;

import com.firebase.ui.database.FirebaseRecyclerAdapter;

public interface ViewNotificationActivity {

    void initNotificationRecyclerView(FirebaseRecyclerAdapter<NotificationModel, PresenterNotificationActivity.ViewHolder> firebaseAdapter);
}
