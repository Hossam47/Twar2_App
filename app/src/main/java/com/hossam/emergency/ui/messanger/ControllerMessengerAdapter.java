package com.hossam.emergency.ui.messanger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hossam.emergency.firebase.UserMainInformation;
import com.hossam.emergency.process.ImageProcess;
import com.hossam.emergency.time.GetTime;
import com.hossam.emergency.ui.chat.ChatModel;
import com.hossam.emergency.utils.utils_activity.DisplayImageActivity;

import java.util.Date;

import static com.hossam.emergency.firebase.FirebaseContract.getCurrentUserID;
import static com.hossam.emergency.firebase.FirebaseContract.getMessengerReference;

public class ControllerMessengerAdapter implements ProviderMessengerAdapter {

    private final Context context;
    private final ChatModel chatModel;
    private final UserMainInformation userMainInformation = UserMainInformation.getInstance();
    private final GetTime getTime = GetTime.getInstance();
    private final ImageProcess imageProcess = ImageProcess.getInstance();

    public ControllerMessengerAdapter(Context context, ChatModel chatModel) {
        this.context = context;
        this.chatModel = chatModel;
    }

    // current user
    @Override
    public void initSenderMainData(MessageModel messageModel, MessagerAdapter.SentMessageHolder holder) {

        userMainInformation.getCurrentUserInformation(holder.imageSenderMessenger, context);

    }

    @Override
    public void initMessageText(MessageModel messageModel, TextView textView) {

        textView.setText(messageModel.getMessage());
    }

    @Override
    public void initReceiveMainData(MessageModel messageModel, MessagerAdapter.ReceivedMessageHolder holder) {

        userMainInformation.getUserImageInformation(messageModel.getUser_id(), holder.imageReceiver, context);
    }


    @Override
    public void initMessageTime(MessageModel messageModel, TextView textView) {

        textView.setText(getTime.getStyleTime(context).format(new Date(messageModel.getTime())));

    }

    @Override
    public void initMessageImage(MessageModel messageModel, ImageView imageView) {

        if (!messageModel.getImage().equals("")) {

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 360);
            imageView.setLayoutParams(layoutParams);

            imageProcess.loadImage(imageView, messageModel.getImage(), context);
        }

    }

    @Override
    public void initMessageSeen(MessageModel messageModel, TextView textView) {

        textView.setText(messageModel.isSeen() ? "Seen" : "Unseen");
    }

    @Override
    public void initVisibility(MessageModel messageModel, ChatModel chatModel) {

        if (messageModel.getUser_id().equals(getCurrentUserID())) {

            getMessengerReference().child(chatModel.getChat_id())
                    .child(messageModel.getMessage_id()).child("seen").setValue(true);

        }
    }

    @Override
    public void showMessageImage(final MessageModel messageModel, ImageView imageView) {

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, DisplayImageActivity.class);
                intent.putExtra("image_url", messageModel.getImage());
                context.startActivity(intent);
            }
        });
    }
}
