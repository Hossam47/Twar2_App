package com.hossam.emergency.ui.comments;

import android.app.Activity;

public interface ProviderCommentsAdapter {

    void initUser(String user_id, CommentsAdapter.ViewHolder holder);

    void initComment(CommentModel model, CommentsAdapter.ViewHolder holder);

    void onUserImageClicked(String user_id, CommentsAdapter.ViewHolder holder);

    void pushReaction(CommentModel model, CommentsAdapter.ViewHolder holder);

    void countReactionComment(CommentModel model, CommentsAdapter.ViewHolder holder);

    void countRepliesOnComment(CommentModel model, CommentsAdapter.ViewHolder holder);

    void onClickReply(CommentModel model, CommentsAdapter.ViewHolder holder);

    void initBannerFeedAds(CommentsAdapter.AdsHolder adsHolder, Activity activity);

    void onLongclickComment(CommentsAdapter.ViewHolder holder, CommentModel model, Activity activity);

}
