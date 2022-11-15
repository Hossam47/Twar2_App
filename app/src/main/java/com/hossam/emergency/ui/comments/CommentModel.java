package com.hossam.emergency.ui.comments;

public class CommentModel {

    private String comment_id, comment, user_id, case_id;
    private long timestamp;
    private boolean deleted;

    public CommentModel() {
    }

    public CommentModel(String comment_id, String comment, String user_id, String case_id, long timestamp, boolean deleted) {
        this.comment_id = comment_id;
        this.comment = comment;
        this.user_id = user_id;
        this.case_id = case_id;
        this.timestamp = timestamp;
        this.deleted = deleted;
    }

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCase_id() {
        return case_id;
    }

    public void setCase_id(String case_id) {
        this.case_id = case_id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
