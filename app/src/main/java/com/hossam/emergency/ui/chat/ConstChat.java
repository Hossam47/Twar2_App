package com.hossam.emergency.ui.chat;

import static com.hossam.emergency.firebase.FirebaseContract.getChatsReference;
import static com.hossam.emergency.firebase.FirebaseContract.getCurrentUserID;
import static com.hossam.emergency.time.GetTime.getUTCTimetamp;

public class ConstChat {
    private static final ConstChat ourInstance = new ConstChat();

    private ConstChat() {
    }

    public static ConstChat getInstance() {
        return ourInstance;
    }

    public void startChatUsers(String other_id, ChatModel chatModel) {

        /**
         * in case current user
         * */
        getChatsReference().child(getCurrentUserID()).child(chatModel.getChat_id()).setValue(new ChatModel(getCurrentUserID(),
                other_id, chatModel.getChat_id(), getUTCTimetamp(), false));


        /**
         * in case other user
         * */
        getChatsReference().child(other_id).child(chatModel.getChat_id()).setValue(new ChatModel(other_id, getCurrentUserID(),
                chatModel.getChat_id(), getUTCTimetamp(), false));

    }
}
