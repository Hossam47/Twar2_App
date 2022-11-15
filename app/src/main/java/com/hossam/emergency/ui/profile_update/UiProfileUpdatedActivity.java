package com.hossam.emergency.ui.profile_update;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

public class UiProfileUpdatedActivity {

    private final ImageView profile_image;
    private final EditText username;
    private final EditText email;
    private final EditText phone;
    private final EditText password;
    private final Button update_btn;
    private CheckBox check_image, check_message, check_call;


    public UiProfileUpdatedActivity(ImageView profile_image, EditText username, EditText email, EditText phone, EditText password, Button update_btn) {
        this.profile_image = profile_image;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.update_btn = update_btn;
    }

    public EditText getUsername() {
        return username;
    }

    public EditText getEmail() {
        return email;
    }

    public EditText getPhone() {
        return phone;
    }

    public Button getUpdate_btn() {
        return update_btn;
    }

    public ImageView getProfile_image() {
        return profile_image;
    }

    public EditText getPassword() {
        return password;
    }

    public CheckBox getCheck_image() {
        return check_image;
    }

    public void setCheck_image(CheckBox check_image) {
        this.check_image = check_image;
    }

    public CheckBox getCheck_message() {
        return check_message;
    }

    public void setCheck_message(CheckBox check_message) {
        this.check_message = check_message;
    }

    public CheckBox getCheck_call() {
        return check_call;
    }

    public void setCheck_call(CheckBox check_call) {
        this.check_call = check_call;
    }
}
