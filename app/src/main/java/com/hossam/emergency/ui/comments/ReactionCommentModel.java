package com.hossam.emergency.ui.comments;

public class ReactionCommentModel {

    private String user_id, case_id, comment_id;
    private long time;
    private boolean type;

    public ReactionCommentModel() {
    }

    public ReactionCommentModel(String user_id, String case_id, String comment_id, long time, boolean type) {
        this.user_id = user_id;
        this.case_id = case_id;
        this.comment_id = comment_id;
        this.time = time;
        this.type = type;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getCase_id() {
        return case_id;
    }

    public String getComment_id() {
        return comment_id;
    }

    public long getTime() {
        return time;
    }

    public boolean isType() {
        return type;
    }
}
