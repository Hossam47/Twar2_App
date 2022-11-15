package com.hossam.emergency.reactions;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.hossam.emergency.R;
import com.hossam.emergency.animations.AnimationsUtils;
import com.hossam.emergency.ui.cases.CaseModel;
import com.hossam.emergency.ui.comments.CommentModel;
import com.hossam.emergency.utils.FillColor;
import com.hossam.emergency.utils.StringResouces;

import java.util.ArrayList;

import static com.hossam.emergency.firebase.FirebaseContract.getCasesReference;
import static com.hossam.emergency.firebase.FirebaseContract.getCommentsReference;
import static com.hossam.emergency.firebase.FirebaseContract.getCurrentUserID;
import static com.hossam.emergency.firebase.FirebaseContract.getReactionsReference;
import static com.hossam.emergency.firebase.FirebaseContract.getViewsReference;
import static com.hossam.emergency.time.GetTime.getUTCTimetamp;

public class ReactionController implements ProviderReactions {

    boolean checker;
    StringResouces stringResouces = StringResouces.getInstance();
    FillColor fillColor = FillColor.getInstance();
    AnimationsUtils animationsUtils = AnimationsUtils.getInstance();
    Activity activity;
    private DatabaseReference mDatasnap;

    public ReactionController(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void checkReaction(final String id, final ImageView imageView) {

        getReactionsReference().child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {


                        FireModel fireModel = snapshot.getValue(FireModel.class);

                        if (fireModel.getUser_id().equals(getCurrentUserID())) {
                            checker = true;
                            mDatasnap = snapshot.getRef();
                            break;
                        } else {
                            checker = false;
                        }
                    }

                    if (checker) {
                        mDatasnap.setValue(null);
                        coloredReaction(imageView, false);
                        fireNumbers(id, false);
                    } else {
                        pushReaction(id);
                        fireNumbers(id, true);

                    }

                } else {
                    pushReaction(id);
                    fireNumbers(id, true);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void pushReaction(String id) {

        getReactionsReference().child(id).child(getCurrentUserID()).setValue(new FireModel(getCurrentUserID(), id, getUTCTimetamp()));


    }

    @Override
    public void countReaction(final String id, final TextView count) {

        getReactionsReference().child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    count.setText(stringResouces.getStringResource(R.string.fire_reaction_text, activity) + " " +
                            dataSnapshot.getChildrenCount());
                } else {
                    count.setText(stringResouces.getStringResource(R.string.fire_reaction_text, activity) + " " +
                            stringResouces.getStringResource(R.string.zero_reaction_value, activity));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void initColoredReaction(String id, final ImageView icon) {

        getReactionsReference().child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        FireModel fireModel = snapshot.getValue(FireModel.class);

                        if (fireModel.getUser_id().equals(getCurrentUserID())) {

                            checker = true;
                            break;

                        } else {

                            checker = false;

                        }
                    }

                    if (checker) {

                        coloredReaction(icon, checker);

                    } else {

                        coloredReaction(icon, checker);
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void countComments(String id, final TextView count) {

        getCommentsReference().child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final ArrayList commentList = new ArrayList<>();

                if (dataSnapshot.exists()) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        CommentModel model = snapshot.getValue(CommentModel.class);

                        if (!model.isDeleted()) {
                            commentList.add(model);
                        }
                    }

                    count.setText(stringResouces.getStringResource(R.string.comment_reaction_text, activity) + " " + commentList.size());
                } else {
                    count.setText(stringResouces.getStringResource(R.string.comment_reaction_text, activity) + " " +
                            stringResouces.getStringResource(R.string.zero_reaction_value, activity));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void initColoredCommentsReaction(String id, final ImageView icon) {

        getCommentsReference().child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        CommentModel commentModel = snapshot.getValue(CommentModel.class);

                        if (commentModel.getUser_id().equals(getCurrentUserID()) && !commentModel.isDeleted()) {

                            checker = true;
                            break;
                        } else {

                            checker = false;

                        }
                    }

                    if (checker) {

                        coloredCommentReaction(icon, checker);

                    } else {

                        coloredCommentReaction(icon, checker);
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void initColoredViewReaction(String id, final ImageView icon) {

        getViewsReference().child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        ViewModel viewModel = snapshot.getValue(ViewModel.class);

                        if (viewModel.getUser_id().equals(getCurrentUserID())) {

                            checker = true;
                            break;

                        } else {

                            checker = false;

                        }
                    }

                    if (checker) {

                        coloredViewReaction(icon, checker);

                    } else {

                        coloredViewReaction(icon, checker);
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void countViews(String id, final TextView count) {

        getViewsReference().child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    count.setText(stringResouces.getStringResource(R.string.view_reaction_text, activity) + " " + dataSnapshot.getChildrenCount());
                } else {
                    count.setText(stringResouces.getStringResource(R.string.view_reaction_text, activity) + " " + stringResouces.getStringResource(R.string.zero_reaction_value, activity));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void coloredReaction(ImageView icon, boolean type) {

        if (type) {
            fillColor.fillColorOrange(icon, activity);
            animationsUtils.animationReaction(icon, activity);

        } else {
            fillColor.fillColorGrey(icon, activity);
            animationsUtils.animationReaction(icon, activity);
        }


    }

    @Override
    public void coloredViewReaction(ImageView icon, boolean type) {

        if (type) {
            fillColor.fillColorBlue(icon, activity);
            animationsUtils.animationReaction(icon, activity);
        } else {
            fillColor.fillColorGrey(icon, activity);
            animationsUtils.animationReaction(icon, activity);
        }

    }

    @Override
    public void coloredCommentReaction(ImageView icon, boolean type) {

        if (type) {
            fillColor.fillColorGreen(icon, activity);

        } else {
            fillColor.fillColorGrey(icon, activity);

        }

    }

    @Override
    public void fireNumbers(final String caseId, final boolean update) {

        getCasesReference().child(caseId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    CaseModel caseModel = dataSnapshot.getValue(CaseModel.class);

                    if (caseModel.getCount() >= 0 && update) {

                        int newNumber = caseModel.getCount();

                        getCasesReference().child(caseId).child("count").setValue(++newNumber);

                    } else if (caseModel.getCount() > 0 && !update) {

                        int newNumber = caseModel.getCount();

                        getCasesReference().child(caseId).child("count").setValue(--newNumber);
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
