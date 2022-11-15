package com.hossam.emergency.ui.messanger;

import android.widget.ImageView;
import android.widget.TextView;

import com.hossam.emergency.ui.chat.ChatModel;

public interface ProviderMessengerAdapter {

    void initReceiveMainData(MessageModel messageModel, MessagerAdapter.ReceivedMessageHolder holder);

    void initSenderMainData(MessageModel messageModel, MessagerAdapter.SentMessageHolder holder);

    void initMessageText(MessageModel messageModel, TextView textView);

    void initMessageTime(MessageModel messageModel, TextView textView);

    void initMessageImage(MessageModel messageModel, ImageView imageView);

    void initMessageSeen(MessageModel messageModel, TextView textView);

    void initVisibility(MessageModel messageModel, ChatModel chatModel);

    void showMessageImage(MessageModel messageModel, ImageView imageView);
}
