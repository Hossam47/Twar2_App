package com.hossam.emergency.ui.replies;

import android.app.Activity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.hossam.emergency.R;
import com.hossam.emergency.animations.AnimationsUtils;
import com.hossam.emergency.ui.comments.CommentModel;
import com.hossam.emergency.dialogs.InitDialog;
import com.hossam.emergency.dialogs.ProviderControllerDialog;
import com.hossam.emergency.firebase.UserMainInformation;
import com.hossam.emergency.time.GetTime;
import com.hossam.emergency.utils.CoolTextView;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.hossam.emergency.firebase.FirebaseContract.getCommentsReference;
import static com.hossam.emergency.firebase.FirebaseContract.getCurrentUserID;
import static com.hossam.emergency.firebase.FirebaseContract.getRepliesReference;
import static com.hossam.emergency.time.GetTime.getUTCTimetamp;

public class ReplyDialog implements ProviderControllerDialog, ProviderReplies {


    @BindView(R.id.close_reply_dialog_layout)
    ImageView closeReplyDialogLayout;

    @BindView(R.id.comment_replies)
    CoolTextView commentReplies;

    @BindView(R.id.replies_recycler_view)
    RecyclerView repliesRecyclerView;

    @BindView(R.id.reply_edit_text_replies)
    AutoCompleteTextView replyEditTextReplies;

    @BindView(R.id.sender_icon_text_replies)
    ImageView senderIconTextReplies;

    @BindView(R.id.message_container_replies)
    RelativeLayout messageContainerReplies;

    @BindView(R.id.image_reply_items)
    CircleImageView imageReplyItems;

    @BindView(R.id.name_item_reply)
    CoolTextView nameItemReply;

    @BindView(R.id.time_item_reply)
    CoolTextView timeItemReply;

    private final Activity activity;
    private View view;
    private final CommentModel commentModel;
    private final AnimationsUtils animationsUtils = AnimationsUtils.getInstance();
    private final InitDialog initDialog = new InitDialog();
    private RecyclerView.LayoutManager layoutManager;
    private AdapterReplies adapterReplies;
    private final GetTime getTime = GetTime.getInstance();
    private final UserMainInformation information = UserMainInformation.getInstance();
    //view

    public ReplyDialog(Activity activity, CommentModel commentModel) {
        this.activity = activity;
        this.commentModel = commentModel;
        initView();
    }


    @Override
    public void initView() {

        view = initDialog.initDialog(R.layout.reply_view_layout, activity);
        ButterKnife.bind(this, view);


    }


    @Override
    public void controllerReplyDialog() {

        getUserData();
        initRecyclerReplies();
        getComment();
        insertReply();
        onCancelDialog();
    }

    @Override
    public void getUserData() {

        information.getUserInformation(commentModel.getUser_id(), nameItemReply, imageReplyItems, activity);

        timeItemReply.setText(getTime.getStyleTime(activity).format(new Date(commentModel.getTimestamp())));

    }

    @Override
    public void getComment() {
        getCommentsReference().child(commentModel.getCase_id()).child(commentModel.getComment_id())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {

                            CommentModel commentModel = dataSnapshot.getValue(CommentModel.class);
                            commentReplies.setText(commentModel.getComment());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    @Override
    public void initRecyclerReplies() {

        layoutManager = new LinearLayoutManager(activity);
        repliesRecyclerView.setLayoutManager(layoutManager);

        getRepliesReference().child(commentModel.getComment_id()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<ReplyModel> replyModels = new ArrayList<>();

                if (dataSnapshot.exists()) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        ReplyModel replyModel = snapshot.getValue(ReplyModel.class);

                        if (!replyModel.isDeleted()) {

                            replyModels.add(replyModel);
                        }

                    }

                    adapterReplies = new AdapterReplies(replyModels, activity, commentModel);
                    repliesRecyclerView.setAdapter(adapterReplies);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void insertReply() {

        senderIconTextReplies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (!TextUtils.isEmpty(replyEditTextReplies.getText())) {

                DatabaseReference reply_ref = getRepliesReference().child(commentModel.getComment_id()).push();

                String reply_id = reply_ref.getKey();

                reply_ref.setValue(

                        new ReplyModel(commentModel.getComment_id(), reply_id, replyEditTextReplies.getText().toString()
                                , getCurrentUserID(), commentModel.getCase_id(), getUTCTimetamp(), false)
                );

                replyEditTextReplies.getText().clear();

//                } else {
//
//                    Toast.makeText(activity, "اكتب حاجة يا بنى ادم ", Toast.LENGTH_SHORT).show();
//                }
            }
        });

    }

    @Override
    public void onCancelDialog() {

        closeReplyDialogLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endDialog();
            }
        });


    }


    @Override
    public void startDialog() {
        animationsUtils.animationReactionFade(view, activity);
        initDialog.showDialog();
    }

    @Override
    public void endDialog() {
        initDialog.cancelDialog();

    }
}
