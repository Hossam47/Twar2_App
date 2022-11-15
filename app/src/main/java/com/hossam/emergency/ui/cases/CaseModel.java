package com.hossam.emergency.ui.cases;

import java.io.Serializable;

public class CaseModel implements Serializable {

    private String case_type, title, description, case_id, user_id;
    private double latitude, longitude;
    private int range, count;
    private long time;
    private boolean show_profile, show_mobile, saved, deleted;

    public CaseModel(String case_type, String title, String description, String case_id, String user_id, double latitude,
                     double longitude, int range, int count, long time, boolean show_profile, boolean
                             show_mobile, boolean saved, boolean deleted) {

        this.case_type = case_type;
        this.title = title;
        this.description = description;
        this.case_id = case_id;
        this.user_id = user_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.range = range;
        this.count = count;
        this.time = time;
        this.show_profile = show_profile;
        this.show_mobile = show_mobile;
        this.saved = saved;
        this.deleted = deleted;
    }

    public CaseModel(String title, String description, String case_id, String user_id, double latitude,
                     double longitude, int range, int count, long time, boolean show_profile, boolean
                             show_mobile, boolean saved, boolean deleted) {
        this.title = title;
        this.description = description;
        this.case_id = case_id;
        this.user_id = user_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.range = range;
        this.count = count;
        this.time = time;
        this.show_profile = show_profile;
        this.show_mobile = show_mobile;
        this.saved = saved;
        this.deleted = deleted;
    }

    public CaseModel() {
    }


    public String getCase_type() {
        return case_type;
    }

    public void setCase_type(String case_type) {
        this.case_type = case_type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCase_id() {
        return case_id;
    }

    public void setCase_id(String case_id) {
        this.case_id = case_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isShow_mobile() {
        return show_mobile;
    }

    public void setShow_mobile(boolean show_mobile) {
        this.show_mobile = show_mobile;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isShow_profile() {
        return show_profile;
    }

    public void setShow_profile(boolean show_profile) {
        this.show_profile = show_profile;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
