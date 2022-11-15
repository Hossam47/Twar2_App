package com.hossam.emergency.ui.messanger;


public class MessageModel {

    private String user_id, image, message, message_id, chat_id;
    private double lat, lang;
    private long time;
    private boolean seen;

    public MessageModel() {
    }


    public MessageModel(boolean seen, String user_id, String image, String message, String message_id, String chat_id, double lat, double lang, long time) {
        this.seen = seen;
        this.user_id = user_id;
        this.image = image;
        this.message = message;
        this.message_id = message_id;
        this.chat_id = chat_id;
        this.lat = lat;
        this.lang = lang;
        this.time = time;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getChat_id() {
        return chat_id;
    }

    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLang() {
        return lang;
    }

    public void setLang(double lang) {
        this.lang = lang;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }


}
