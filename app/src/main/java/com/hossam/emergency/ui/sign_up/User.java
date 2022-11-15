package com.hossam.emergency.ui.sign_up;

import java.io.Serializable;

public class User implements Serializable {

    private String id, username, email, phone, password, token, image, country;
    private long time;
    private boolean online, image_available, message_available, call_available;


    public User(String id, String username, String email, String phone, String password, String token,
                String image, String country, long time, boolean online, boolean image_available,
                boolean message_available, boolean call_available) {

        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.token = token;
        this.image = image;
        this.country = country;
        this.time = time;
        this.online = online;
        this.image_available = image_available;
        this.message_available = message_available;
        this.call_available = call_available;
    }

    public User(String id, String username, String email, String phone, String password, String token,
                String image, long time, boolean online, boolean image_available,
                boolean message_available, boolean call_available) {

        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.token = token;
        this.image = image;
        this.time = time;
        this.online = online;
        this.image_available = image_available;
        this.message_available = message_available;
        this.call_available = call_available;

    }


    //    public User(String id, String username, String email, String phone, String password, String token, String image, long time, boolean online) {
//        this.id = id;
//        this.username = username;
//        this.email = email;
//        this.phone = phone;
//        this.password = password;
//        this.token = token;
//        this.image = image;
//        this.time = time;
//        this.online = online;
//    }

    public User(String id, String username, String email, String phone, String password, String token, String image, long time) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.token = token;
        this.image = image;
        this.time = time;
    }

    public User(String username, String email, String phone, String password) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public User(String username, String email, String phone) {
        this.username = username;
        this.email = email;
        this.phone = phone;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public boolean isImage_available() {
        return image_available;
    }

    public void setImage_available(boolean image_available) {
        this.image_available = image_available;
    }

    public boolean isMessage_available() {
        return message_available;
    }

    public void setMessage_available(boolean message_available) {
        this.message_available = message_available;
    }

    public boolean isCall_available() {
        return call_available;
    }

    public void setCall_available(boolean call_available) {
        this.call_available = call_available;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
