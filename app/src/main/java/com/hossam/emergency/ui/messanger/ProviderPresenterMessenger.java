package com.hossam.emergency.ui.messanger;

import androidx.appcompat.widget.Toolbar;

import com.hossam.emergency.ui.chat.ChatModel;

public interface ProviderPresenterMessenger {

    void converterData(ChatModel chatModel);

    void getMessengerData(ChatModel chatModel);

    ChatModel creatorChat(String other_id);

    void usernameChat(String other_id, Toolbar toolbar);

    void checkUserChatExist(String otherUserID);

    void initMessageComponent();

    void pushUserMessage();

    void pushNotificationUserMessage(String message_id);

    void onClickSenderMessages();


}
