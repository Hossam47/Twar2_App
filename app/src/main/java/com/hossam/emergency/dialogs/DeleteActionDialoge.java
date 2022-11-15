package com.hossam.emergency.dialogs;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.hossam.emergency.R;
import com.hossam.emergency.animations.AnimationsUtils;
import com.hossam.emergency.ui.comments.CommentModel;
import com.hossam.emergency.ui.replies.ReplyModel;
import com.hossam.emergency.utils.CoolButton;
import com.hossam.emergency.utils.StringResouces;
import com.hossam.emergency.utils.ToastStyle;

import static com.hossam.emergency.firebase.FirebaseContract.getCommentsReference;
import static com.hossam.emergency.firebase.FirebaseContract.getRepliesReference;

public class DeleteActionDialoge implements ProviderControllerDialog {


    public InitDialog initDialog = new InitDialog();
    private final Activity activity;
    private final AnimationsUtils animationsUtils = AnimationsUtils.getInstance();
    private final StringResouces resouces = StringResouces.getInstance();
    private ToastStyle toastStyle;
    private View view;

    //view
    private ImageView close_btn;
    private CoolButton btn_delete;

    private CommentModel commentModel;
    private ReplyModel replyModel;


    public DeleteActionDialoge(Activity activity, CommentModel commentModel) {
        this.activity = activity;
        this.commentModel = commentModel;
        initView();

    }

    public DeleteActionDialoge(Activity activity, ReplyModel replyModel) {
        this.activity = activity;
        this.replyModel = replyModel;
        initView();
        deleteReply();
    }

    public DeleteActionDialoge(Activity activity) {
        this.activity = activity;
        toastStyle = new ToastStyle(activity);
        initView();

    }

    @Override
    public void initView() {

        view = initDialog.initDialog(R.layout.delete_actions_dialog, activity);
        toastStyle = new ToastStyle(activity);

        close_btn = view.findViewById(R.id.image_delete_dialog);
        btn_delete = view.findViewById(R.id.btn_delete_password);


        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endDialog();
            }
        });


    }

    public void deleteComment() {


        btn_delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                getCommentsReference().child(commentModel.getCase_id())
                        .child(commentModel.getComment_id()).child("deleted").setValue(true);

                toastStyle.positiveToast("Your comment is deleted");

                endDialog();
            }
        });
    }

    public void deleteReply() {

        btn_delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                getRepliesReference().child(replyModel.getComment_id())
                        .child(replyModel.getReply_id()).child("deleted").setValue(true);

                toastStyle.positiveToast("Your reply is deleted");

                endDialog();

            }
        });
    }

    @Override
    public void startDialog() {
        animationsUtils.animationReaction(view, activity);
        initDialog.showDialog();
    }

    @Override
    public void endDialog() {
        initDialog.cancelDialog();

    }


}
