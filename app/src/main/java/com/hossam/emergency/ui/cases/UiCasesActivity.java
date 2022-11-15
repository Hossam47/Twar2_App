package com.hossam.emergency.ui.cases;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.xw.repo.BubbleSeekBar;

public class UiCasesActivity {

    private EditText title, desc;
    private CheckBox show_profile, show_mobile;
    private Button location, images, lunch;
    private BubbleSeekBar mBbubbleSeekBar;
    private RecyclerView images_recycler;
    private TextView range_details;

    public UiCasesActivity(EditText title, EditText desc, CheckBox show_profile, CheckBox show_mobile, Button location, Button images, Button lunch) {
        this.title = title;
        this.desc = desc;
        this.show_profile = show_profile;
        this.show_mobile = show_mobile;
        this.location = location;
        this.images = images;
        this.lunch = lunch;
    }

    public EditText getTitle() {
        return title;
    }

    public void setTitle(EditText title) {
        this.title = title;
    }

    public EditText getDesc() {
        return desc;
    }

    public void setDesc(EditText desc) {
        this.desc = desc;
    }

    public CheckBox getShow_mobile() {
        return show_mobile;
    }

    public void setShow_mobile(CheckBox show_mobile) {
        this.show_mobile = show_mobile;
    }

    public Button getLocation() {
        return location;
    }

    public void setLocation(Button location) {
        this.location = location;
    }

    public Button getImages() {
        return images;
    }

    public void setImages(Button images) {
        this.images = images;
    }

    public Button getLunch() {
        return lunch;
    }

    public void setLunch(Button lunch) {
        this.lunch = lunch;
    }

    public CheckBox getShow_profile() {
        return show_profile;
    }

    public void setShow_profile(CheckBox show_profile) {
        this.show_profile = show_profile;
    }

    public BubbleSeekBar getmBbubbleSeekBar() {
        return mBbubbleSeekBar;
    }

    public void setmBbubbleSeekBar(BubbleSeekBar mBbubbleSeekBar) {
        this.mBbubbleSeekBar = mBbubbleSeekBar;
    }

    public RecyclerView getImages_recycler() {
        return images_recycler;
    }

    public void setImages_recycler(RecyclerView images_recycler) {
        this.images_recycler = images_recycler;
    }

    public TextView getRange_details() {
        return range_details;
    }

    public void setRange_details(TextView range_details) {
        this.range_details = range_details;
    }
}
