package com.hossam.emergency.ui.main;

import com.google.android.gms.maps.GoogleMap;

public interface ProviderMain {

    void initServices();

    void initMainData();

    void onClickFireContainer();

    void onClickInboxContainer();

    void onClickNotificationContainer();

    void getCountFire();

    void getCountInbox();

    void getCountNotification();

    void initSeeMore();

    void setCasesMarkers(GoogleMap googleMap);

    void initNavUser();

    void profileImageCacheUpdated();

    void refreshToken();
}
