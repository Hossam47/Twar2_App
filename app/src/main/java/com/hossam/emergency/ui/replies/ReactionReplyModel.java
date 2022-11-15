package com.hossam.emergency.ui.replies;

import com.hossam.emergency.ui.comments.ReactionCommentModel;

public class ReactionReplyModel extends ReactionCommentModel {

    private String reply_id;

    public ReactionReplyModel(String user_id, String case_id, String comment_id,
                              String reply_id, long time, boolean type) {

        super(user_id, case_id, comment_id, time, type);
        this.reply_id = reply_id;
    }

    public ReactionReplyModel() {
    }

    @Override
    public String getUser_id() {
        return super.getUser_id();
    }

    @Override
    public String getCase_id() {
        return super.getCase_id();
    }

    @Override
    public String getComment_id() {
        return super.getComment_id();
    }

    public String getReply_id() {
        return reply_id;
    }

    @Override
    public long getTime() {
        return super.getTime();
    }

    @Override
    public boolean isType() {
        return super.isType();
    }


}
