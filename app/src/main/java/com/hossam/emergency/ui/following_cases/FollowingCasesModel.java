package com.hossam.emergency.ui.following_cases;

public class FollowingCasesModel {

    private String caseId;
    private long timestamp;

    public FollowingCasesModel(String caseId, long timestamp) {
        this.caseId = caseId;
        this.timestamp = timestamp;
    }

    public FollowingCasesModel() {
    }

    public String getCaseId() {
        return caseId;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
