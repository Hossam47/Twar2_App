package com.hossam.emergency.ui.comments;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdView;
import com.hossam.emergency.R;
import com.hossam.emergency.utils.CoolTextView;
import com.hossam.emergency.utils.UtilsRecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class CommentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<CommentModel> commentsList;
    Activity activity;
    CommentModel commentModel;
    UtilsRecyclerView utilsRecyclerView = UtilsRecyclerView.getInstance();
    ControllerCommentsAdapter controllerCommentsAdapter;

    private static final int LIST_AD_DELTA = 4;
    private static final int CONTENT = 0;
    private static final int AD = 1;


    public CommentsAdapter(ArrayList<CommentModel> commentsList, Activity activity) {
        this.commentsList = commentsList;
        this.activity = activity;

        controllerCommentsAdapter = new ControllerCommentsAdapter(activity);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        if (viewType == CONTENT) {
            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_comments, parent, false);
            CommentsAdapter.ViewHolder viewHolder = new CommentsAdapter.ViewHolder(layout);

            return viewHolder;
        } else {
            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_feed_comments, parent, false);
            CommentsAdapter.AdsHolder adsHolder = new CommentsAdapter.AdsHolder(layout);

            return adsHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        utilsRecyclerView.fadeAnimationRecyclerView(viewHolder, activity);


        if (getItemViewType(position) == CONTENT) {

            CommentsAdapter.ViewHolder holder = (CommentsAdapter.ViewHolder) viewHolder;

            commentModel = commentsList.get(getRealPosition(position));

            controllerCommentsAdapter.initUser(commentModel.getUser_id(), holder);
            controllerCommentsAdapter.initComment(commentModel, holder);
            controllerCommentsAdapter.onUserImageClicked(commentModel.getUser_id(), holder);
            controllerCommentsAdapter.pushReaction(commentModel, holder);
            controllerCommentsAdapter.countReactionComment(commentModel, holder);
            controllerCommentsAdapter.onClickReply(commentModel, holder);
            controllerCommentsAdapter.countRepliesOnComment(commentModel, holder);
            controllerCommentsAdapter.onLongclickComment(holder, commentModel, activity);

        } else {

            CommentsAdapter.AdsHolder adsHolder = (CommentsAdapter.AdsHolder) viewHolder;

            controllerCommentsAdapter.initBannerFeedAds(adsHolder, activity);
        }

    }

    @Override
    public int getItemViewType(int position) {

        if (position > 0 && position % LIST_AD_DELTA == 0) {
            return AD;
        }
        return CONTENT;

    }

    @Override
    public int getItemCount() {

        int additionalContent = 0;

        if (commentsList.size() > 0 && LIST_AD_DELTA > 0 && commentsList.size() > LIST_AD_DELTA) {
            additionalContent = commentsList.size() / LIST_AD_DELTA;
        }
        return commentsList.size() + additionalContent;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.image_comments_items)
        CircleImageView imageCommentsItems;

        @BindView(R.id.name_comments_items)
        CoolTextView nameCommentsItems;

        @BindView(R.id.time_comments_items)
        CoolTextView timeCommentsItems;

        @BindView(R.id.container_comment)
        RelativeLayout container_comment;

        @BindView(R.id.comment_comments_items)
        CoolTextView commentCommentsItems;

        @BindView(R.id.useful_text)
        CoolTextView usefulText;

        @BindView(R.id.useful_number)
        CoolTextView usefulNumber;

        @BindView(R.id.useful_container)
        LinearLayout usefulContainer;

        @BindView(R.id.haped_text)
        CoolTextView hapedText;

        @BindView(R.id.haped_number)
        CoolTextView hapedNumber;

        @BindView(R.id.haped_container)
        LinearLayout hapedContainer;

        @BindView(R.id.reply_text)
        CoolTextView replyText;

        @BindView(R.id.reply_number)
        CoolTextView replyNumber;

        @BindView(R.id.reply_container)
        LinearLayout replyContainer;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }

    public static class AdsHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ads_feed_banner_comments)
        AdView ads_feed_banner_comments;

        public AdsHolder(@NonNull View adsView) {
            super(adsView);
            ButterKnife.bind(this, adsView);
        }
    }


    private int getRealPosition(int position) {
        if (LIST_AD_DELTA == 0) {
            return position;
        } else {
            return position - position / LIST_AD_DELTA;
        }
    }
}
