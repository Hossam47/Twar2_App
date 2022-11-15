package com.hossam.emergency.ui.chat;

import java.io.Serializable;

public class ChatModel implements Serializable {

    private String current_user, other_user, chat_id;
    private long time;
    private boolean deleted;

    public ChatModel(String current_user, String other_user, String chat_id, long time, boolean deleted) {
        this.current_user = current_user;
        this.other_user = other_user;
        this.chat_id = chat_id;
        this.time = time;
        this.deleted = deleted;
    }

    public ChatModel() {
    }

    public String getCurrent_user() {
        return current_user;
    }

    public String getOther_user() {
        return other_user;
    }

    public String getChat_id() {
        return chat_id;
    }

    public long getTime() {
        return time;
    }

    public boolean isDeleted() {
        return deleted;
    }
}
