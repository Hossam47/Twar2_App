package com.hossam.emergency.ui.replies;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hossam.emergency.R;
import com.hossam.emergency.ui.comments.CommentModel;
import com.hossam.emergency.utils.CoolTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterReplies extends RecyclerView.Adapter<AdapterReplies.ViewHolder> {

    ArrayList<ReplyModel> replyModels;
    Activity activity;
    ControllerReply controllerReply;
    ReplyModel replyModel;
    ReactionsRepliesController reactionsRepliesController;

    public AdapterReplies(ArrayList<ReplyModel> replyModels, Activity activity, CommentModel commentModel) {
        this.replyModels = replyModels;
        this.activity = activity;

        controllerReply = new ControllerReply(activity);
        reactionsRepliesController = new ReactionsRepliesController(activity);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reply, parent, false);
        ViewHolder viewHolder = new ViewHolder(layout);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        replyModel = replyModels.get(position);

        controllerReply.setReplyData(holder, replyModel);
        controllerReply.getImageReplyUser(holder, replyModel);
        controllerReply.intializeReactions(holder, replyModel);
        controllerReply.onLongDeleteReply(holder, replyModel);
    }

    @Override
    public int getItemCount() {
        return replyModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_reply_items)
        CircleImageView imageReplyItems;
        @BindView(R.id.name_reply_items)
        CoolTextView nameReplyItems;
        @BindView(R.id.time_reply_items)
        CoolTextView timeReplyItems;
        @BindView(R.id.reply_text)
        RelativeLayout replyText;
        @BindView(R.id.reply_reply_items)
        CoolTextView replyReplyItems;
        @BindView(R.id.container_reply)
        RelativeLayout containerReply;
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

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
