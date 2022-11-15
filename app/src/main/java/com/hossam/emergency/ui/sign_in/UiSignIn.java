package com.hossam.emergency.ui.sign_in;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class UiSignIn {

    private EditText email, password;
    private TextView forget, terms, privacy;
    private CheckBox remember;
    private Button sign_in, sign_up, facebook, google;


    public UiSignIn(EditText email, EditText password, TextView forget, TextView terms, TextView privacy, CheckBox remember, Button sign_in, Button sign_up, Button facebook, Button google) {
        this.email = email;
        this.password = password;
        this.forget = forget;
        this.terms = terms;
        this.privacy = privacy;
        this.remember = remember;
        this.sign_in = sign_in;
        this.sign_up = sign_up;
        this.facebook = facebook;
        this.google = google;
    }

    public EditText getEmail() {
        return email;
    }

    public void setEmail(EditText email) {
        this.email = email;
    }

    public EditText getPassword() {
        return password;
    }

    public void setPassword(EditText password) {
        this.password = password;
    }

    public TextView getForget() {
        return forget;
    }

    public void setForget(TextView forget) {
        this.forget = forget;
    }

    public TextView getTerms() {
        return terms;
    }

    public void setTerms(TextView terms) {
        this.terms = terms;
    }

    public TextView getPrivacy() {
        return privacy;
    }

    public void setPrivacy(TextView privacy) {
        this.privacy = privacy;
    }

    public CheckBox getRemember() {
        return remember;
    }

    public void setRemember(CheckBox remember) {
        this.remember = remember;
    }

    public Button getSign_in() {
        return sign_in;
    }

    public void setSign_in(Button sign_in) {
        this.sign_in = sign_in;
    }

    public Button getSign_up() {
        return sign_up;
    }

    public void setSign_up(Button sign_up) {
        this.sign_up = sign_up;
    }

    public Button getFacebook() {
        return facebook;
    }

    public void setFacebook(Button facebook) {
        this.facebook = facebook;
    }

    public Button getGoogle() {
        return google;
    }

    public void setGoogle(Button google) {
        this.google = google;
    }
}

