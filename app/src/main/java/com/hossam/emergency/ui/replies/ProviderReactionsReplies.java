package com.hossam.emergency.ui.replies;

import android.widget.ImageView;
import android.widget.TextView;

public interface ProviderReactionsReplies {

    void checkReaction(ReplyModel model, boolean type);

    void pushReaction(ReplyModel replyModel, boolean type);

    void countReaction(ReplyModel replyModel, TextView useful, TextView shit);

    void initColoredReaction(String id, ImageView icon);

    void coloredReaction(ImageView icon, boolean type);

    void countUpdatedNumbers(String caseId, boolean update);
}
