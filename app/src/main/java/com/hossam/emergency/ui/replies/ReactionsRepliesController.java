package com.hossam.emergency.ui.replies;

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
import static com.hossam.emergency.firebase.FirebaseContract.getReactionsRepliesReference;
import static com.hossam.emergency.time.GetTime.getUTCTimetamp;

public class ReactionsRepliesController implements ProviderReactionsReplies {

    boolean checker;
    StringResouces stringResouces = StringResouces.getInstance();
    FillColor fillColor = FillColor.getInstance();
    AnimationsUtils animationsUtils = AnimationsUtils.getInstance();
    private DatabaseReference mDatasnap;
    private final Activity context;

    public ReactionsRepliesController(Activity context) {
        this.context = context;
    }


    @Override
    public void checkReaction(final ReplyModel model, final boolean type) {

        getReactionsRepliesReference().child(model.getComment_id()).child(model.getReply_id()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {


                        ReactionReplyModel reactionReplyModel = snapshot.getValue(ReactionReplyModel.class);

                        if (reactionReplyModel.getUser_id().equals(getCurrentUserID())) {
                            checker = true;
                            mDatasnap = snapshot.getRef();
                            break;
                        } else {
                            checker = false;
                        }
                    }

                    if (checker) {
                        mDatasnap.setValue(null);
                        countUpdatedNumbers(model.getReply_id(), false);
                    } else {
                        pushReaction(model, type);
                        countUpdatedNumbers(model.getReply_id(), true);

                    }

                } else {
                    pushReaction(model, type);
                    countUpdatedNumbers(model.getReply_id(), true);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void pushReaction(ReplyModel replyModel, boolean type) {

//        ReactionReplyModel(String user_id, String case_id, String comment_id,
//                String reply_id, long time, boolean type)

        getReactionsRepliesReference().child(replyModel.getComment_id()).child(replyModel.getReply_id()).child(getCurrentUserID())
                .setValue(new ReactionReplyModel(getCurrentUserID(), replyModel.getCase_id()
                        , replyModel.getComment_id(), replyModel.getReply_id(), getUTCTimetamp(), type));


    }

    @Override
    public void countReaction(ReplyModel replyModel, final TextView useful, final TextView shit) {

        getReactionsRepliesReference().child(replyModel.getComment_id()).child(replyModel.getReply_id()).orderByChild("type")
                .equalTo(true).addValueEventListener(new ValueEventListener() {
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

        getReactionsRepliesReference().child(replyModel.getComment_id()).child(replyModel.getReply_id()).orderByChild("type").equalTo(false).addValueEventListener(new ValueEventListener() {
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