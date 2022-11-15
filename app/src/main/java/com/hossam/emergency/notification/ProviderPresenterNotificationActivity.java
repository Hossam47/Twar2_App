package com.hossam.emergency.notification;

import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

public interface ProviderPresenterNotificationActivity {

    void initRecyclerView();

    void initMainData(PresenterNotificationActivity.ViewHolder holder, NotificationModel model, int position);

    void initNotificationImage(PresenterNotificationActivity.ViewHolder holder, NotificationModel model);

    void initNotificationTitle(PresenterNotificationActivity.ViewHolder holder, NotificationModel model);

    void initNotificationTime(PresenterNotificationActivity.ViewHolder holder, NotificationModel model);

    void initNotificationDesc(PresenterNotificationActivity.ViewHolder holder, NotificationModel model);

    void converterToCase(PresenterNotificationActivity.ViewHolder holder, NotificationModel model);

    void converterToComment(PresenterNotificationActivity.ViewHolder holder, NotificationModel model);

    void converterToMessage(PresenterNotificationActivity.ViewHolder holder, NotificationModel model);

    void initUserImage(PresenterNotificationActivity.ViewHolder holder, NotificationModel model);

    void initSeenNotificaton(PresenterNotificationActivity.ViewHolder holder, NotificationModel model);

    void seenNotificaton(NotificationModel model);

    void noNotifications(ImageView container);

    void updatedLoadMore(RecyclerView.LayoutManager layoutManager);
}
