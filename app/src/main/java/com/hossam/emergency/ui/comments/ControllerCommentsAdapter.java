package com.hossam.emergency.ui.comments;

import android.app.Activity;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.hossam.emergency.R;
import com.hossam.emergency.dialogs.DeleteActionDialoge;
import com.hossam.emergency.firebase.UserMainInformation;
import com.hossam.emergency.ui.replies.ReplyDialog;
import com.hossam.emergency.ui.replies.ReplyModel;
import com.hossam.emergency.ui.sign_up.User;
import com.hossam.emergency.time.GetTime;
import com.hossam.emergency.user_view_dialog.UserViewDilaog;
import com.hossam.emergency.utils.ToastStyle;

import java.util.ArrayList;
import java.util.Date;

import static com.hossam.emergency.firebase.FirebaseContract.getCurrentUserID;
import static com.hossam.emergency.firebase.FirebaseContract.getRepliesReference;
import static com.hossam.emergency.firebase.FirebaseContract.getUserReference;

public class ControllerCommentsAdapter implements ProviderCommentsAdapter {

    private final Activity activity;
    private final UserMainInformation information = UserMainInformation.getInstance();
    private final GetTime getTime = GetTime.getInstance();
    private final ReactionCommentsController reactionCommentsController;
    ToastStyle toastStyle;

    public ControllerCommentsAdapter(Activity activity) {
        this.activity = activity;

        reactionCommentsController = new ReactionCommentsController(activity);
        toastStyle = new ToastStyle(activity);
    }

    @Override
    public void initUser(String user_id, CommentsAdapter.ViewHolder holder) {

        information.getUserInformation(user_id, holder.nameCommentsItems, holder.imageCommentsItems, activity);

    }

    @Override
    public void initComment(CommentModel model, CommentsAdapter.ViewHolder holder) {

        holder.timeCommentsItems.setText(getTime.getStyleTime(activity).format(new Date(model.getTimestamp())));
        holder.commentCommentsItems.setText(model.getComment());

    }

    @Override
    public void onUserImageClicked(final String user_id, CommentsAdapter.ViewHolder holder) {

        holder.imageCommentsItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!getCurrentUserID().equals(user_id)) {
                    final UserViewDilaog userViewDilaog = new UserViewDilaog(activity);
                    userViewDilaog.initView();

                    getUserReference().child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            User user = dataSnapshot.getValue(User.class);

                            userViewDilaog.initMainDialogView(user);
                            userViewDilaog.onClickMessageButton(user);
                            userViewDilaog.startDialog();

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
    }

    @Override
    public void pushReaction(final CommentModel model, CommentsAdapter.ViewHolder holder) {

        holder.usefulContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reactionCommentsController.checkReaction(model.getComment_id(), true);
            }
        });

        holder.hapedContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reactionCommentsController.checkReaction(model.getComment_id(), false);


            }
        });
    }

    @Override
    public void countReactionComment(CommentModel model, CommentsAdapter.ViewHolder holder) {

        reactionCommentsController.countReaction(model.getComment_id(), holder.usefulNumber, holder.hapedNumber);
    }

    @Override
    public void countRepliesOnComment(CommentModel model, final CommentsAdapter.ViewHolder holder) {

        getRepliesReference().child(model.getComment_id()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                final ArrayList replyList = new ArrayList<>();

                if (dataSnapshot.exists()) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        ReplyModel model = snapshot.getValue(ReplyModel.class);

                        if (!model.isDeleted()) {
                            replyList.add(model);
                        }
                    }

                    holder.replyNumber.setText(replyList.size() + "");

                } else {

                    holder.replyNumber.setText(R.string.zero_reaction_value);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClickReply(final CommentModel model, CommentsAdapter.ViewHolder holder) {

        holder.replyContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReplyDialog replyDialog = new ReplyDialog(activity, model);

                replyDialog.initView();
                replyDialog.controllerReplyDialog();
                replyDialog.startDialog();
            }
        });

//        holder.container_comment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ReplyDialog replyDialog = new ReplyDialog(activity, model);
//
//                replyDialog.initView();
//                replyDialog.controllerReplyDialog();
//                replyDialog.startDialog();
//            }
//        });


    }

    @Override
    public void initBannerFeedAds(CommentsAdapter.AdsHolder adsHolder, Activity activity) {


//        commentsBannerAdsGoogle(adsHolder.ads_feed_banner_comments);


//        commentsBannerAdsGoogle(adsHolder.ads_feed_banner_comments);

//
//        AdView adView = smallBannerAds(adsHolder.ads_feed_banner_comments, activity);
//
//       adView.setAdListener(new AdListener() {
//            @Override
//            public void onError(Ad ad, AdError adError) {
//
//                Log.v("error_comments_ad",adError.getErrorMessage());
//            }
//
//            @Override
//            public void onAdLoaded(Ad ad) {
//
//                adView.loadAd();
//            }
//
//            @Override
//            public void onAdClicked(Ad ad) {
//
//            }
//
//            @Override
//            public void onLoggingImpression(Ad ad) {
//
//            }
//        });

    }

    @Override
    public void onLongclickComment(CommentsAdapter.ViewHolder holder, final CommentModel model, final Activity activity) {

        if (model.getUser_id().equals(getCurrentUserID())) {

            holder.container_comment.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    DeleteActionDialoge replyDialog = new DeleteActionDialoge(activity, model);

                    replyDialog.initView();
                    replyDialog.startDialog();
                    replyDialog.deleteComment();

                    return false;
                }
            });
        }
    }
}
