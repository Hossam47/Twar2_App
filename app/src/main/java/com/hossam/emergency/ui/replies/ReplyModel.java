package com.hossam.emergency.ui.replies;

public class ReplyModel {

    private String comment_id, reply_id, reply, user_id, case_id;
    private long timestamp;
    private boolean deleted;


    public ReplyModel() {
    }

    public ReplyModel(String comment_id, String reply_id, String reply, String user_id, String case_id, long timestamp, boolean deleted) {
        this.comment_id = comment_id;
        this.reply_id = reply_id;
        this.reply = reply;
        this.user_id = user_id;
        this.case_id = case_id;
        this.timestamp = timestamp;
        this.deleted = deleted;
    }

    public String getComment_id() {
        return comment_id;
    }

    public String getReply_id() {
        return reply_id;
    }

    public String getReply() {
        return reply;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getCase_id() {
        return case_id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public boolean isDeleted() {
        return deleted;
    }
}
