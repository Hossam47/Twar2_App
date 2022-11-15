package com.hossam.emergency.notification;

import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.victor.loading.rotate.RotateLoading;

public class UiNotificationActivity {

    private ImageView no_notifications_container;
    private RecyclerView.LayoutManager layoutManager;
    private RotateLoading rotateLoadingAlerts;

    public UiNotificationActivity(ImageView no_notifications_container, RecyclerView.LayoutManager layoutManager, RotateLoading rotateLoadingAlerts) {
        this.no_notifications_container = no_notifications_container;
        this.layoutManager = layoutManager;
        this.rotateLoadingAlerts = rotateLoadingAlerts;
    }

    public UiNotificationActivity(ImageView no_notifications_container, RecyclerView.LayoutManager layoutManager) {
        this.no_notifications_container = no_notifications_container;
        this.layoutManager = layoutManager;
    }

    public ImageView getNo_notifications_container() {
        return no_notifications_container;
    }

    public void setNo_notifications_container(ImageView no_notifications_container) {
        this.no_notifications_container = no_notifications_container;
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return layoutManager;
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    public RotateLoading getRotateLoadingAlerts() {
        return rotateLoadingAlerts;
    }
}
