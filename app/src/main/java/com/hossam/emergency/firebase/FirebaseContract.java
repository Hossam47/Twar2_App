package com.hossam.emergency.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by hossam on 11/9/17.
 */

public class FirebaseContract {

    public static DatabaseReference getUserReference() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users");

        return reference;
    }

    public static DatabaseReference getMainReference() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users").child(getCurrentUserID());

        return reference;
    }

    public static DatabaseReference getMainReferenceOfflne(String user_id) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users").child(user_id);

        return reference;
    }


    public static DatabaseReference getCasesReference() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("world_cases");

        return reference;
    }

    public static DatabaseReference getCasesCountryReference() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        return reference;
    }

    public static DatabaseReference getCasesMain() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        return reference;
    }

    public static DatabaseReference getNotificationReference() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("notification");

        return reference;
    }

    public static DatabaseReference getChatsReference() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("chats");

        return reference;
    }

    public static DatabaseReference getMessengerReference() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("messenger");

        return reference;
    }

    public static DatabaseReference getCurrentUserChatsReference() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("chats");

        return reference;
    }

    public static DatabaseReference getFollowingReference() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("following_cases");

        return reference;
    }


    public static DatabaseReference getReactionsReference() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("reactions");

        return reference;
    }

    public static DatabaseReference getViewsReference() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("views");

        return reference;
    }

    public static DatabaseReference getCommentsReference() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("comments");

        return reference;
    }

    public static DatabaseReference getRepliesReference() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("replies");

        return reference;
    }

    public static DatabaseReference getReactionsCommentsReference() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("reactions_comments");

        return reference;
    }

    public static DatabaseReference getReactionsRepliesReference() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("reactions_replies");

        return reference;
    }

    public static DatabaseReference getUpdateInformation() {
        DatabaseReference reference = getMainReference().child("information");

        return reference;
    }

    public static String getCurrentUserID() {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {

            return mAuth.getCurrentUser().getUid();

        } else return null;
    }

    public static FirebaseAuth mAuthentication() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        return mAuth;
    }

    public static FirebaseUser mAuthenticationUser() {
        FirebaseUser user = mAuthentication().getCurrentUser();

        return user;
    }
}
