package com.hossam.emergency.ui.detalis_cases;

public interface ProviderDetalisCases {

    void initData();

    void initImageList();

    void initCommentsList();

    void pushComment();

    void initPushComment();

    void pushCommentNotification(String comment_id);

    void styleData();

    void pushView();

    void initFollowCase(final UIDetalisCases uiDetalisCases);

    void checkFollowCase(final UIDetalisCases uiDetalisCases);

    void directMessageCase();

    void followingCases();

    void callPhonePost();

    void streetViewPost();

    void mapViewPost();

    void getCaseDistance();

    void manageDetalisActivity();

    void initUI();
}
