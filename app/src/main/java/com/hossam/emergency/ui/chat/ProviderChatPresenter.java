package com.hossam.emergency.ui.chat;

import androidx.recyclerview.widget.RecyclerView;

public interface ProviderChatPresenter {

    void initChatsRecyclerView(RecyclerView recyclerView);

    void getMainUserData(ChatModel chatModel, PresenterChat.ViewHolder holder);

    void getLastMessage(ChatModel chatModel, PresenterChat.ViewHolder holder);

    void noChats(boolean show);

}
