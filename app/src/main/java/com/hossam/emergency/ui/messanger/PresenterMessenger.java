package com.hossam.emergency.ui.messanger;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.hossam.emergency.R;
import com.hossam.emergency.algorithem.SortAlgorithms;
import com.hossam.emergency.firebase.FirebaseNotification;
import com.hossam.emergency.firebase.UserMainInformation;
import com.hossam.emergency.notification.NotificationModel;
import com.hossam.emergency.process.CompressionImages;
import com.hossam.emergency.ui.chat.ChatModel;
import com.hossam.emergency.ui.chat.ConstChat;
import com.hossam.emergency.ui.sign_up.User;
import com.hossam.emergency.utils.FillColor;
import com.hossam.emergency.utils.ToastStyle;

import java.util.ArrayList;

import static com.hossam.emergency.firebase.FirebaseContract.getChatsReference;
import static com.hossam.emergency.firebase.FirebaseContract.getCurrentUserID;
import static com.hossam.emergency.firebase.FirebaseContract.getMessengerReference;
import static com.hossam.emergency.firebase.FirebaseContract.getNotificationReference;
import static com.hossam.emergency.firebase.FirebaseContract.getUserReference;
import static com.hossam.emergency.time.GetTime.getUTCTimetamp;

public class PresenterMessenger implements ProviderPresenterMessenger {

    private final ToastStyle toastStyle;
    private final Context context;
    private final ViewMessenger viewMessenger;
    private ChatModel chatModelCurrent;
    private MessagerAdapter messagerAdapter;
    private final UiMessengerActivity uiMessengerActivity;
    private final UserMainInformation userMainInformation = UserMainInformation.getInstance();
    private final FillColor fillColor = FillColor.getInstance();
    private boolean existence = false;
    private final ConstChat constChat = ConstChat.getInstance();
    private final SortAlgorithms sortAlgorithms = new SortAlgorithms();
    private ArrayList<Uri> path;
    private final CompressionImages compressionImages;
    private final FirebaseNotification firebaseNotification = FirebaseNotification.getInstance();
    private String other_id = null;

    public PresenterMessenger(Context context, ViewMessenger viewMessenger, UiMessengerActivity uiMessengerActivity) {
        this.context = context;
        this.viewMessenger = viewMessenger;
        this.uiMessengerActivity = uiMessengerActivity;
        toastStyle = new ToastStyle(context);
        compressionImages = new CompressionImages(context);
    }

    @Override
    public void converterData(ChatModel chatModel) {

        if (chatModel != null) {
            getMessengerData(chatModel);
            this.chatModelCurrent = chatModel;
        }
    }

    @Override
    public void getMessengerData(final ChatModel chatModel) {

        getMessengerReference().child(chatModel.getChat_id()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<MessageModel> messageModels = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    MessageModel messageModel = snapshot.getValue(MessageModel.class);
                    messageModels.add(messageModel);
                }

                messagerAdapter = new MessagerAdapter(sortAlgorithms.sortMessagesTime(messageModels), chatModel, context);
                viewMessenger.initMessengerRecyclerView(messagerAdapter, messageModels.size());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    @Override
    public void checkUserChatExist(final String otherUserID) {

        getChatsReference().child(getCurrentUserID()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    ChatModel chatModel = snapshot.getValue(ChatModel.class);

                    if (otherUserID.equals(chatModel.getOther_user())) {

                        chatModelCurrent = chatModel;
                        existence = true;
                        break;
                    }
                }

                if (existence) {

                    getMessengerData(chatModelCurrent);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public ChatModel creatorChat(String otherUserID) {

        String chat_id = getChatsReference().push().getKey();

        this.chatModelCurrent = new ChatModel(getCurrentUserID(), otherUserID, chat_id, getUTCTimetamp(), false);
        constChat.startChatUsers(otherUserID, chatModelCurrent);
        getMessengerData(this.chatModelCurrent);

        return chatModelCurrent;
    }

    @Override
    public void usernameChat(String other_id, Toolbar toolbar) {

        userMainInformation.getUserInformation(other_id, toolbar);
    }


    @Override
    public void initMessageComponent() {

        uiMessengerActivity.getMessageEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) {
                    fillColor.fillColorOrangeTrans(uiMessengerActivity.getIconSenderMessenger(), context);
                }

                if (count == 0) {
                    fillColor.fillColorGrey(uiMessengerActivity.getIconSenderMessenger(), context);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        uiMessengerActivity.getImageIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewMessenger.onClickedViewImages();
            }
        });
    }

    @Override
    public void pushUserMessage() {

        DatabaseReference reference = null;
        String message = uiMessengerActivity.getMessageEditText().getText().toString();

        if (message.length() > 0) {

            if (chatModelCurrent != null) {

                reference = getMessengerReference().child(chatModelCurrent.getChat_id());

            } else if (chatModelCurrent == null) {

                reference = getMessengerReference().child(creatorChat(getOther_id()).getChat_id());

                getMessengerData(chatModelCurrent);
            }


            String message_id = reference.push().getKey();

            MessageModel messageModel = new MessageModel(false, getCurrentUserID(), "", message,
                    message_id, chatModelCurrent.getChat_id(), 0, 0, getUTCTimetamp());

            if (getPath() != null) {

                compressionImages.uploadingSingleImages(getPath(), message_id, chatModelCurrent.getChat_id());
                fillColor.fillColorGrey(uiMessengerActivity.getImageIcon(), context);
            }

            reference.child(message_id).setValue(messageModel);
            pushNotificationUserMessage(message_id);

        } else {

            toastStyle.negativeToast(context.getResources().getString(R.string.write_message));

        }
    }

    @Override
    public void pushNotificationUserMessage(final String message_id) {

        DatabaseReference reference = getNotificationReference().child(chatModelCurrent.getOther_user()).push();

        final String notification_id = reference.getKey();

        // TODO: 9/24/18  make checker if user not online , else send notification

        getUserReference().child(chatModelCurrent.getOther_user()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);

                if (!user.isOnline()) {

                    firebaseNotification.pushCommentNotification(new NotificationModel(
                            getCurrentUserID(),
                            chatModelCurrent.getOther_user(),
                            "",
                            "",
                            chatModelCurrent.getChat_id(),
                            message_id,
                            notification_id,
                            "New message !",
                            getUTCTimetamp(),
                            2,
                            false
                    ));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onClickSenderMessages() {

        uiMessengerActivity.getIconSenderMessenger().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pushUserMessage();
                fillColor.fillColorGrey(uiMessengerActivity.getIconSenderMessenger(), context);
                uiMessengerActivity.getMessageEditText().setText("");

            }
        });
    }

    public ArrayList<Uri> getPath() {
        return path;
    }

    public void setPath(ArrayList<Uri> path) {
        this.path = path;
    }

    public String getOther_id() {
        return other_id;
    }

    public void setOther_id(String other_id) {
        this.other_id = other_id;
    }
}
