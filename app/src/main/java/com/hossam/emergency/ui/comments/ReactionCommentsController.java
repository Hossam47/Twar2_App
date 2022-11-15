package com.hossam.emergency.ui.comments;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.hossam.emergency.R;
import com.hossam.emergency.animations.AnimationsUtils;
import com.hossam.emergency.utils.FillColor;
import com.hossam.emergency.utils.StringResouces;

import static com.hossam.emergency.firebase.FirebaseContract.getCurrentUserID;
import static com.hossam.emergency.firebase.FirebaseContract.getReactionsCommentsReference;
import static com.hossam.emergency.time.GetTime.getUTCTimetamp;

public class ReactionCommentsController implements ProviderReactionComments {

    boolean checker;
    StringResouces stringResouces = StringResouces.getInstance();
    FillColor fillColor = FillColor.getInstance();
    AnimationsUtils animationsUtils = AnimationsUtils.getInstance();
    private DatabaseReference mDatasnap;
    private final Activity context;

    public ReactionCommentsController(Activity context) {
        this.context = context;
    }

    @Override
    public void checkReaction(final String id, final boolean type) {

        getReactionsCommentsReference().child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {


                        ReactionCommentModel reactionCommentModel = snapshot.getValue(ReactionCommentModel.class);

                        if (reactionCommentModel.getUser_id().equals(getCurrentUserID())) {
                            checker = true;
                            mDatasnap = snapshot.getRef();
                            break;
                        } else {
                            checker = false;
                        }
                    }

                    if (checker) {
                        mDatasnap.setValue(null);
                        countUpdatedNumbers(id, false);
                    } else {
                        pushReaction(id, type);
                        countUpdatedNumbers(id, true);

                    }

                } else {
                    pushReaction(id, type);
                    countUpdatedNumbers(id, true);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void pushReaction(String id, boolean type) {

        getReactionsCommentsReference().child(id).child(getCurrentUserID())
                .setValue(new ReactionCommentModel(getCurrentUserID(), id
                        , id, getUTCTimetamp(), type));

    }

    @Override
    public void countReaction(String id, final TextView useful, final TextView shit) {

        getReactionsCommentsReference().child(id).orderByChild("type").equalTo(true).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    useful.setText(String.valueOf(dataSnapshot.getChildrenCount()));

                } else {
                    useful.setText(stringResouces.getStringResource(R.string.zero_reaction_value, context));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        getReactionsCommentsReference().child(id).orderByChild("type").equalTo(false).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    shit.setText(String.valueOf(dataSnapshot.getChildrenCount()));

                } else {
                    shit.setText(stringResouces.getStringResource(R.string.zero_reaction_value, context));

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void initColoredReaction(String id, ImageView icon) {

    }

    @Override
    public void coloredReaction(ImageView icon, boolean type) {

        if (type) {
            fillColor.fillColorOrange(icon, context);
            animationsUtils.animationReaction(icon, context);

        } else {
            fillColor.fillColorGrey(icon, context);
            animationsUtils.animationReaction(icon, context);
        }


    }

    @Override
    public void countUpdatedNumbers(final String id, final boolean update) {

//        reference.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                if (dataSnapshot.exists()) {
//
//                    CaseModel caseModel = dataSnapshot.getValue(CaseModel.class);
//
//                    if (caseModel.getCount() >= 0 && update) {
//
//                        int newNumber = caseModel.getCount();
//
//                        getCasesReference().child(id).child("count").setValue(++newNumber);
//
//                    } else if (caseModel.getCount() > 0 && !update) {
//
//                        int newNumber = caseModel.getCount();
//
//                        getCasesReference().child(id).child("count").setValue(--newNumber);
//                    }
//
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }
}
