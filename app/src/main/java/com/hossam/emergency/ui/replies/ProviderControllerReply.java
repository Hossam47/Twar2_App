package com.hossam.emergency.ui.replies;

public interface ProviderControllerReply {


    void setReplyData(AdapterReplies.ViewHolder holder, ReplyModel replyModel);

    void getImageReplyUser(AdapterReplies.ViewHolder holder, ReplyModel replyModel);

    void intializeReactions(AdapterReplies.ViewHolder holder, ReplyModel replyModel);

    void onLongDeleteReply(AdapterReplies.ViewHolder holder, ReplyModel replyModel);


}
