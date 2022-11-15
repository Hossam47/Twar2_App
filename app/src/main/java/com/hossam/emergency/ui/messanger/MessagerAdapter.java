package com.hossam.emergency.ui.messanger;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hossam.emergency.R;
import com.hossam.emergency.process.ImageProcess;
import com.hossam.emergency.ui.chat.ChatModel;
import com.hossam.emergency.utils.CoolTextView;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.hossam.emergency.constants.DimenConstant.image_width;
import static com.hossam.emergency.firebase.FirebaseContract.getCurrentUserID;


/**
 * Created by hossam on 2/26/18.
 */

public class MessagerAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private List<MessageModel> messageList = Collections.emptyList();
    private final Context context;
    private final ImageProcess imageProcess = ImageProcess.getInstance();

    ControllerMessengerAdapter controllerMessengerAdapter;
    private final ChatModel chatModel;

    public MessagerAdapter(List<MessageModel> messageList, ChatModel chatModel, Context context) {
        this.messageList = messageList;
        this.context = context;
        this.chatModel = chatModel;

        controllerMessengerAdapter = new ControllerMessengerAdapter(context, chatModel);
    }

    @Override
    public int getItemViewType(int position) {
        MessageModel model = messageList.get(position);

        if (model.getUser_id().equals(getCurrentUserID())) {
            // If the current user is the sender of the message

            return VIEW_TYPE_MESSAGE_RECEIVED;

        } else {

            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_SENT;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;

        if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sender_chat_items, parent, false);

            return new SentMessageHolder(view);

        } else if (viewType == VIEW_TYPE_MESSAGE_SENT) {

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reciver_chat_items, parent, false);

            return new ReceivedMessageHolder(view);
        }


        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MessageModel model = messageList.get(position);

        switch (holder.getItemViewType()) {

            case VIEW_TYPE_MESSAGE_RECEIVED:

                SentMessageHolder sentMessageHolder = ((SentMessageHolder) holder);

                controllerMessengerAdapter.initSenderMainData(model, sentMessageHolder);
                controllerMessengerAdapter.initMessageText(model, sentMessageHolder.messageMessageSender);
                controllerMessengerAdapter.initMessageImage(model, sentMessageHolder.imageMessageSender);
                controllerMessengerAdapter.initMessageTime(model, sentMessageHolder.timeMessageSender);

                if (!model.getImage().equals("")) {

                    sentMessageHolder.imageMessageSender.getLayoutParams().width = image_width;
                    sentMessageHolder.imageMessageSender.requestLayout();
                    sentMessageHolder.imageMessageSender.setScaleType(ImageView.ScaleType.FIT_CENTER);

                    imageProcess.loadImage(sentMessageHolder.imageMessageSender, model.getImage(), context);
                    controllerMessengerAdapter.showMessageImage(model, sentMessageHolder.imageMessageSender);

                }

                //utilsRecyclerView.fadeAnimationRecyclerView(sentMessageHolder, activity);

                break;

            case VIEW_TYPE_MESSAGE_SENT:

                ReceivedMessageHolder receivedMessageHolder = ((ReceivedMessageHolder) holder);

                controllerMessengerAdapter.initReceiveMainData(model, receivedMessageHolder);
                controllerMessengerAdapter.initMessageText(model, receivedMessageHolder.messageReceiver);
                controllerMessengerAdapter.initMessageImage(model, receivedMessageHolder.imageMessageReceiver);
                controllerMessengerAdapter.initMessageTime(model, receivedMessageHolder.timeReceiver);
                controllerMessengerAdapter.initVisibility(model, chatModel);

//                receivedMessageHolder.time.setText(GetTime.getTimeFromTimeStamp(model.getTime(), activity));
//
//                receivedMessageHolder.message.setText(model.getText());
//
//                getUserData(model.getUser(), receivedMessageHolder.name, receivedMessageHolder.image);
//
                if (!model.getImage().equals("")) {


                    receivedMessageHolder.imageMessageReceiver.getLayoutParams().width = image_width;
                    receivedMessageHolder.imageMessageReceiver.requestLayout();
                    receivedMessageHolder.imageMessageReceiver.setScaleType(ImageView.ScaleType.FIT_CENTER);

                    imageProcess.loadImage(receivedMessageHolder.imageMessageReceiver, model.getImage(), context);
                    controllerMessengerAdapter.showMessageImage(model, receivedMessageHolder.imageMessageReceiver);
                }

                //utilsRecyclerView.fadeAnimationRecyclerView(receivedMessageHolder, activity);

                break;

        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public static class SentMessageHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_sender_messenger)
        CircleImageView imageSenderMessenger;

//        @BindView(R.id.name_message_sender)
//        CoolTextView nameMessageSender;

        @BindView(R.id.time_message_sender)
        CoolTextView timeMessageSender;

//        @BindView(R.id.seen_message_sender)
//        CoolTextView seenMessageSender;

//        @BindView(R.id.name_container_message_sender)
//        RelativeLayout nameContainerMessageSender;

        @BindView(R.id.message_message_sender)
        CoolTextView messageMessageSender;

        @BindView(R.id.image_message_sender)
        ImageView imageMessageSender;

//        @BindView(R.id.container_message_sender)
//        CardView containerMessageSender;

        public SentMessageHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }


    }

    public class ReceivedMessageHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_receiver)
        CircleImageView imageReceiver;

//        @BindView(R.id.name_receiver)
//        CoolTextView nameReceiver;

        @BindView(R.id.time_receiver)
        CoolTextView timeReceiver;

//        @BindView(R.id.seen_receiver)
//        CoolTextView seenReceiver;

//        @BindView(R.id.name_container)
//        LinearLayout nameContainer;

        @BindView(R.id.message_receiver)
        CoolTextView messageReceiver;

        @BindView(R.id.image_message_receiver)
        ImageView imageMessageReceiver;
//
//        @BindView(R.id.container_message_receiver)
//        CardView containerMessageReceiver;

        public ReceivedMessageHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }

    }
}