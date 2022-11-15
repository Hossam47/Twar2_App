package com.hossam.emergency.ui.sign_up;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hbb20.CountryCodePicker;

public class UiSignUp {

    private ImageView profile;
    private EditText username, email, phone, pass;
    private Button sign_up;
    private CheckBox checkBox_terms;
    private TextView termsText, country_text;
    private RelativeLayout nextBtnSignUp;
    private CountryCodePicker countryCodePicker;

    public UiSignUp(ImageView profile, EditText username, EditText email, EditText phone, EditText pass, Button sign_up) {
        this.profile = profile;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.pass = pass;
        this.sign_up = sign_up;
    }

    public ImageView getProfile() {
        return profile;
    }

    public void setProfile(ImageView profile) {
        this.profile = profile;
    }

    public EditText getUsername() {
        return username;
    }

    public void setUsername(EditText username) {
        this.username = username;
    }

    public EditText getEmail() {
        return email;
    }

    public void setEmail(EditText email) {
        this.email = email;
    }

    public EditText getPhone() {
        return phone;
    }

    public void setPhone(EditText phone) {
        this.phone = phone;
    }

    public EditText getPass() {
        return pass;
    }

    public void setPass(EditText pass) {
        this.pass = pass;
    }

    public Button getSign_up() {
        return sign_up;
    }

    public void setSign_up(Button sign_up) {
        this.sign_up = sign_up;
    }

    public CheckBox getCheckBox_terms() {
        return checkBox_terms;
    }

    public void setCheckBox_terms(CheckBox checkBox_terms) {
        this.checkBox_terms = checkBox_terms;
    }

    public TextView getTermsText() {
        return termsText;
    }

    public void setTermsText(TextView termsText) {
        this.termsText = termsText;
    }

    public RelativeLayout getNextBtnSignUp() {
        return nextBtnSignUp;
    }

    public void setNextBtnSignUp(RelativeLayout nextBtnSignUp) {
        this.nextBtnSignUp = nextBtnSignUp;
    }

    public TextView getCountry_text() {
        return country_text;
    }

    public void setCountry_text(TextView country_text) {
        this.country_text = country_text;
    }

    public CountryCodePicker getCountryCodePicker() {

        return countryCodePicker;
    }

    public void setCountryCodePicker(CountryCodePicker countryCodePicker) {
        countryCodePicker.setAutoDetectedCountry(true);
        countryCodePicker.getSelectedCountryEnglishName();
        this.countryCodePicker = countryCodePicker;
    }
}
