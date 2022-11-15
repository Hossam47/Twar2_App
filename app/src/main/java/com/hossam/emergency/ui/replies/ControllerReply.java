package com.hossam.emergency.ui.replies;

import android.app.Activity;
import android.view.View;

import com.hossam.emergency.dialogs.DeleteActionDialoge;
import com.hossam.emergency.firebase.UserMainInformation;
import com.hossam.emergency.time.GetTime;

import java.util.Date;

import static com.hossam.emergency.firebase.FirebaseContract.getCurrentUserID;

public class ControllerReply implements ProviderControllerReply {

    private final Activity activity;
    private final GetTime getTime = GetTime.getInstance();
    private final UserMainInformation userMainInformation = UserMainInformation.getInstance();
    private final ReactionsRepliesController reactionsRepliesController;

    public ControllerReply(Activity activity) {
        this.activity = activity;
        reactionsRepliesController = new ReactionsRepliesController(activity);
    }


    @Override
    public void setReplyData(AdapterReplies.ViewHolder holder, ReplyModel replyModel) {

        holder.replyReplyItems.setText(replyModel.getReply());
        holder.timeReplyItems.setText(getTime.getStyleTime(activity).format(new Date(replyModel.getTimestamp())));

        reactionsRepliesController.countReaction(replyModel, holder.usefulNumber, holder.hapedNumber);

    }

    @Override
    public void getImageReplyUser(AdapterReplies.ViewHolder holder, ReplyModel replyModel) {
        userMainInformation.getUserInformation(replyModel.getUser_id(), holder.nameReplyItems, holder.imageReplyItems, activity);
    }

    @Override
    public void intializeReactions(AdapterReplies.ViewHolder holder, final ReplyModel replyModel) {


        holder.usefulContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reactionsRepliesController.checkReaction(replyModel, true);
            }
        });

        holder.hapedContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reactionsRepliesController.checkReaction(replyModel, false);
            }
        });
    }

    @Override
    public void onLongDeleteReply(AdapterReplies.ViewHolder holder, final ReplyModel replyModel) {

        if (replyModel.getUser_id().equals(getCurrentUserID())) {

            holder.containerReply.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    DeleteActionDialoge replyDialog = new DeleteActionDialoge(activity, replyModel);

                    replyDialog.initView();
                    replyDialog.startDialog();
                    replyDialog.deleteReply();

                    return false;
                }
            });
        }
    }


}
