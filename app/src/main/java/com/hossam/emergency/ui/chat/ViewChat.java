package com.hossam.emergency.ui.chat;

import com.firebase.ui.database.FirebaseRecyclerAdapter;

public interface ViewChat {

    void onRecyclerViewReady(FirebaseRecyclerAdapter<ChatModel, PresenterChat.ViewHolder> firebaseAdapter);

    void onClickMessage(ChatModel chatModel);


}
