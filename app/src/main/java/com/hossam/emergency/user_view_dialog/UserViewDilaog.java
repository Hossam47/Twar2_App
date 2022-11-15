package com.hossam.emergency.user_view_dialog;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hossam.emergency.R;
import com.hossam.emergency.animations.AnimationsUtils;
import com.hossam.emergency.ui.chat.ConstChat;
import com.hossam.emergency.dialogs.InitDialog;
import com.hossam.emergency.dialogs.ProviderControllerDialog;
import com.hossam.emergency.firebase.UserMainInformation;
import com.hossam.emergency.ui.messanger.MessangerActivity;
import com.hossam.emergency.ui.sign_up.User;
import com.hossam.emergency.utils.StringResouces;

public class UserViewDilaog implements ProviderControllerDialog, ProviderUserViewDialog {


    private final Activity activity;
    private final InitDialog initDialog = new InitDialog();
    private View view;
    private final AnimationsUtils animationsUtils = AnimationsUtils.getInstance();

    private RelativeLayout message, cancel;
    private ImageView image;
    private TextView name;

    private final UserMainInformation userMainInformation = UserMainInformation.getInstance();
    private final StringResouces stringResouces = StringResouces.getInstance();

    private final ConstChat constChat = ConstChat.getInstance();

    public UserViewDilaog(Activity activity) {
        this.activity = activity;
        initView();
    }


    @Override
    public void initView() {

        view = initDialog.initDialog(R.layout.view_user_dialog, activity);

        message = view.findViewById(R.id.message_user_view_layout);
        image = view.findViewById(R.id.image_user_view_dialog);
        name = view.findViewById(R.id.name_user_view_layout);

    }


    @Override
    public void initMainDialogView(User user) {
        userMainInformation.getUserInformation(user, name, image, activity);
    }

    @Override
    public void onClickMessageButton(final User user) {

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity, MessangerActivity.class);
                intent.putExtra("other_id", user.getId());
                activity.startActivity(intent);
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
