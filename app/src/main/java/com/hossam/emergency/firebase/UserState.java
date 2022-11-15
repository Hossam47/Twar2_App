package com.hossam.emergency.firebase;

import static com.hossam.emergency.firebase.FirebaseContract.getCurrentUserID;
import static com.hossam.emergency.firebase.FirebaseContract.getMainReferenceOfflne;

public class UserState {
    private static final UserState ourInstance = new UserState();

    private UserState() {
    }

    public static UserState getInstance() {
        return ourInstance;
    }

    public void userOnline(String user_id) {

        getMainReferenceOfflne(user_id).child("online").setValue(true);

    }

    public void userOnline() {

        getMainReferenceOfflne(getCurrentUserID()).child("online").setValue(true);

    }

    public void userOffline(String user_id) {

        getMainReferenceOfflne(user_id).child("online").setValue(false);

    }

    public void userOffline() {

        getMainReferenceOfflne(getCurrentUserID()).child("online").setValue(false);

    }
}
