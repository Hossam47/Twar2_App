package com.hossam.emergency.firebase;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.hossam.emergency.ui.sign_up.User;
import com.hossam.emergency.utils.FileUtilsProvider;

import static com.hossam.emergency.firebase.FirebaseContract.getMainReference;
import static com.hossam.emergency.firebase.FirebaseContract.getUserReference;

public class UserMainInformation {

    private static final UserMainInformation ourInstance = new UserMainInformation();

    private UserMainInformation() {
    }

    public static UserMainInformation getInstance() {
        return ourInstance;
    }

    private final FileUtilsProvider fileUtilsProvider = FileUtilsProvider.getInstance();
    private SharedPreferences sharedPref;
    private final String SHARED_PREF = "IMAGE_FILE";

    public void getUserInformation(final String user_id, final TextView name, final ImageView profile, final Activity activity) {

        getUserReference().child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    User user = dataSnapshot.getValue(User.class);
                    name.setText(user.getUsername());

                    Glide.with(activity).load(user.getImage()).into(profile);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getCurrentUserInformation(final TextView name, final ImageView profile, final Activity activity) {

        getMainReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (name != null && profile != null) {

                    if (dataSnapshot.exists()) {

                        User user = dataSnapshot.getValue(User.class);
                        name.setText(user.getUsername());
                        Glide.with(activity.getApplicationContext()).load(user.getImage()).into(profile);
                    }
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void getCurrentUserFullInformation(final TextView name, final TextView email, final TextView phone,
                                              final TextView password, final ImageView profile, final Activity activity) {

        getMainReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (name != null && profile != null) {

                    if (dataSnapshot.exists()) {
                        User user = dataSnapshot.getValue(User.class);

                        name.setText(user.getUsername());
                        email.setText(user.getEmail());
                        phone.setText(user.getPhone());
                        password.setText(user.getPassword());

                        Glide.with(activity.getApplicationContext()).load(user.getImage()).into(profile);

                    }
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getCurrentUserInformation(final ImageView profile, final Context activity) {

        getMainReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (profile != null) {

                    if (dataSnapshot.exists()) {

                        User user = dataSnapshot.getValue(User.class);
                        Glide.with(activity.getApplicationContext()).load(user.getImage()).into(profile);
                    }
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void getUserInformation(User user, final TextView name, final ImageView profile, final Activity activity) {

        name.setText(user.getUsername());
        Glide.with(activity).load(user.getImage()).into(profile);

    }


    public void getCommentaryUserInformation(String user_id, final TextView name, final ImageView profile, final Activity activity) {


        getUserReference().child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    User user = dataSnapshot.getValue(User.class);
                    name.setText(user.getUsername() + " comment on your case");
                    Glide.with(activity).load(user.getImage()).into(profile);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void getUserInformation(String id, final Toolbar chatToolbar) {


        getUserReference().child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    User user = dataSnapshot.getValue(User.class);

                    chatToolbar.setTitle(user.getUsername());

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void getUserImageInformation(String id, final ImageView profile, final Context activity) {

        getUserReference().child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    User user = dataSnapshot.getValue(User.class);

                    Glide.with(activity).load(user.getImage()).into(profile);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public void getUserImageAndNameInformation(String id, final ImageView profile, final TextView name, final Activity activity) {

        getUserReference().child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    User user = dataSnapshot.getValue(User.class);

                    name.setText(user.getUsername());
                    Glide.with(activity).load(user.getImage()).into(profile);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


//    public void mainCaches(final Activity activity) {
//
//        getMainReference().addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//
//                if (dataSnapshot.exists()) {
//
//                    User user = dataSnapshot.getValue(User.class);
//                    cachesProfileImage(user.getImage(), activity);
//                }
//            }
//
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//    }

//    public void cachesProfileImage(String url, final Activity activity) {
//
//        sharedPref = activity.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
//
//        Target target = new Target() {
//            @Override
//            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//
//                offlineProfileImage(fileUtilsProvider.saveToInternalStorage(bitmap, activity));
//
//            }
//
//            @Override
//            public void onBitmapFailed(Drawable errorDrawable) {
//
//            }
//
//            @Override
//            public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//            }
//        };
//
//        Picasso.with(activity).load(url).into(target);
//
//
//    }
//
//    private void offlineProfileImage(String path) {
//        SharedPreferences.Editor editor = sharedPref.edit();
//        editor.putString("path_profile_image", path);
//        editor.commit();
//
//    }

}
