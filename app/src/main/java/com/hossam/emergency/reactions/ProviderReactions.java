package com.hossam.emergency.reactions;

import android.widget.ImageView;
import android.widget.TextView;

public interface ProviderReactions {

    void checkReaction(String id, ImageView imageView);

    void pushReaction(String id);

    void countReaction(String id, TextView count);

    void initColoredReaction(String id, ImageView icon);

    void countComments(String id, TextView count);

    void initColoredCommentsReaction(String id, ImageView icon);

    void initColoredViewReaction(String id, ImageView icon);

    void countViews(String id, TextView count);

    void coloredReaction(ImageView icon, boolean type);

    void coloredViewReaction(ImageView icon, boolean type);

    void coloredCommentReaction(ImageView icon, boolean type);

    void fireNumbers(String caseId, boolean update);
}
