package com.hossam.emergency.reactions;


public class ViewModel {

    private String view_id, user_id, case_id;
    private long time;

    public ViewModel() {
    }

    public ViewModel(String view_id, String user_id, String case_id, long time) {
        this.view_id = view_id;
        this.user_id = user_id;
        this.case_id = case_id;
        this.time = time;
    }

    public String getView_id() {
        return view_id;
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
