package com.hossam.emergency.notification;

import java.io.Serializable;

public class NotificationModel implements Serializable {

    private String pusher_id, receiver_id, case_id, comment_id, chat_id, message_id, notification_id, text;
    private long time;
    private int type;
    private boolean seen;


    public NotificationModel() {
    }

    public NotificationModel(String pusher_id, String receiver_id, String case_id, String comment_id,
                             String chat_id, String message_id, String notification_id, String text, long time, int type, boolean seen) {
        this.pusher_id = pusher_id;
        this.receiver_id = receiver_id;
        this.case_id = case_id;
        this.comment_id = comment_id;
        this.chat_id = chat_id;
        this.message_id = message_id;
        this.notification_id = notification_id;
        this.text = text;
        this.time = time;
        this.type = type;
        this.seen = seen;
    }

    public String getPusher_id() {
        return pusher_id;
    }

    public void setPusher_id(String pusher_id) {
        this.pusher_id = pusher_id;
    }

    public String getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(String receiver_id) {
        this.receiver_id = receiver_id;
    }

    public String getCase_id() {
        return case_id;
    }

    public void setCase_id(String case_id) {
        this.case_id = case_id;
    }

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public String getChat_id() {
        return chat_id;
    }

    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
    }

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getNotification_id() {
        return notification_id;
    }

    public void setNotification_id(String notification_id) {
        this.notification_id = notification_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }
}
