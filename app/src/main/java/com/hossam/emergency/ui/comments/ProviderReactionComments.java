package com.hossam.emergency.ui.comments;

import android.widget.ImageView;
import android.widget.TextView;

public interface ProviderReactionComments {

    void checkReaction(String id, boolean type);

    void pushReaction(String id, boolean type);

    void countReaction(String id, TextView useful, TextView shit);

    void initColoredReaction(String id, ImageView icon);

    void coloredReaction(ImageView icon, boolean type);

    void countUpdatedNumbers(String caseId, boolean update);
}
