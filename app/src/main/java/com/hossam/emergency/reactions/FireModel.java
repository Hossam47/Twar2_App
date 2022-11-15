package com.hossam.emergency.reactions;

public class FireModel {

    private String user_id, case_id;
    private long time;

    public FireModel() {
    }

    public FireModel(String user_id, String case_id, long time) {
        this.user_id = user_id;
        this.case_id = case_id;
        this.time = time;
    }


    public String getUser_id() {
        return user_id;
    }

    public String getCase_id() {
        return case_id;
    }

    public long getTime() {
        return time;
    }
}
