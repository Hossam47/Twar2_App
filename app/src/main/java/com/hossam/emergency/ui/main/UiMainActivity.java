package com.hossam.emergency.ui.main;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class UiMainActivity {

    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private TextView gps_state, network_state, titleFire, countFire, titleInbox, countInbox, titleNotification, countNotification;
    private ImageView gps_image, network_image, iconFire, iconNotification;
    private LinearLayout see_more, fireContainer, inboxContainer, notificationContainer;


    public UiMainActivity(FloatingActionButton floatingActionButton, RecyclerView recyclerView) {
        this.floatingActionButton = floatingActionButton;
        this.recyclerView = recyclerView;
    }

    public FloatingActionButton getFloatingActionButton() {
        return floatingActionButton;
    }

    public void setFloatingActionButton(FloatingActionButton floatingActionButton) {
        this.floatingActionButton = floatingActionButton;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public TextView getGps_state() {
        return gps_state;
    }

    public void setGps_state(TextView gps_state) {
        this.gps_state = gps_state;
    }

    public TextView getNetwork_state() {
        return network_state;
    }

    public void setNetwork_state(TextView network_state) {
        this.network_state = network_state;
    }

    public ImageView getGps_image() {
        return gps_image;
    }

    public void setGps_image(ImageView gps_image) {
        this.gps_image = gps_image;
    }

    public ImageView getNetwork_image() {
        return network_image;
    }

    public void setNetwork_image(ImageView network_image) {
        this.network_image = network_image;
    }

    public LinearLayout getSee_more() {
        return see_more;
    }

    public void setSee_more(LinearLayout see_more) {
        this.see_more = see_more;
    }


    public TextView getTitleFire() {
        return titleFire;
    }

    public void setTitleFire(TextView titleFire) {
        this.titleFire = titleFire;
    }

    public TextView getCountFire() {
        return countFire;
    }

    public void setCountFire(TextView countFire) {
        this.countFire = countFire;
    }

    public TextView getTitleInbox() {
        return titleInbox;
    }

    public void setTitleInbox(TextView titleInbox) {
        this.titleInbox = titleInbox;
    }

    public TextView getCountInbox() {
        return countInbox;
    }

    public void setCountInbox(TextView countInbox) {
        this.countInbox = countInbox;
    }

    public TextView getTitleNotification() {
        return titleNotification;
    }

    public void setTitleNotification(TextView titleNotification) {
        this.titleNotification = titleNotification;
    }

    public TextView getCountNotification() {
        return countNotification;
    }

    public void setCountNotification(TextView countNotification) {
        this.countNotification = countNotification;
    }

    public ImageView getIconFire() {
        return iconFire;
    }

    public void setIconFire(ImageView iconFire) {
        this.iconFire = iconFire;
    }

    public ImageView getIconNotification() {
        return iconNotification;
    }

    public void setIconNotification(ImageView iconNotification) {
        this.iconNotification = iconNotification;
    }

    public LinearLayout getFireContainer() {
        return fireContainer;
    }

    public void setFireContainer(LinearLayout fireContainer) {
        this.fireContainer = fireContainer;
    }

    public LinearLayout getInboxContainer() {
        return inboxContainer;
    }

    public void setInboxContainer(LinearLayout inboxContainer) {
        this.inboxContainer = inboxContainer;
    }

    public LinearLayout getNotificationContainer() {
        return notificationContainer;
    }

    public void setNotificationContainer(LinearLayout notificationContainer) {
        this.notificationContainer = notificationContainer;
    }
}
