package com.hossam.emergency.ui.chat;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.hossam.emergency.R;
import com.hossam.emergency.firebase.UserMainInformation;
import com.hossam.emergency.ui.messanger.MessageModel;
import com.hossam.emergency.time.GetTime;
import com.hossam.emergency.utils.CoolTextView;
import com.victor.loading.rotate.RotateLoading;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.hossam.emergency.firebase.FirebaseContract.getChatsReference;
import static com.hossam.emergency.firebase.FirebaseContract.getCurrentUserID;
import static com.hossam.emergency.firebase.FirebaseContract.getMessengerReference;

public class PresenterChat implements ProviderChatPresenter {

    GetTime getTime = GetTime.getInstance();
    private final Activity activity;
    private final ViewChat viewChat;
    private final UserMainInformation userMainInformation = UserMainInformation.getInstance();
    private final RotateLoading rotateLoading;
    private final ImageView nodata;

    public PresenterChat(Activity activity, ViewChat viewChat, RotateLoading rotateLoading, ImageView nodata) {
        this.activity = activity;
        this.viewChat = viewChat;
        this.rotateLoading = rotateLoading;
        this.nodata = nodata;

    }

    @Override
    public void initChatsRecyclerView(RecyclerView recyclerView) {

        final FirebaseRecyclerAdapter<ChatModel, PresenterChat.ViewHolder> firebaseAdapter
                = new FirebaseRecyclerAdapter<ChatModel, PresenterChat.ViewHolder>(

                ChatModel.class,
                R.layout.chat_item_layout,
                PresenterChat.ViewHolder.class,
                getChatsReference().child(getCurrentUserID()).orderByChild("time")

        ) {
            @Override
            protected void populateViewHolder(PresenterChat.ViewHolder holder, ChatModel model, int position) {

                if (!model.isDeleted()) {

                    getMainUserData(model, holder);
                    getLastMessage(model, holder);


                } else if (model.isDeleted()) {

                    holder.chatContainerItem.setLayoutParams(new RelativeLayout.LayoutParams(0, 0));
                    holder.chatContainerItem.setLayoutParams(new RelativeLayout.MarginLayoutParams(0, 0));
                    holder.chatContainerItem.setVisibility(View.GONE);
                }


            }

        };

        if (firebaseAdapter.getItemCount() <= 0) {
            noChats(true);
        }
        recyclerView.setAdapter(firebaseAdapter);
        firebaseAdapter.notifyDataSetChanged();

    }

    @Override
    public void getMainUserData(final ChatModel chatModel, PresenterChat.ViewHolder holder) {

        userMainInformation.getUserInformation(chatModel.getOther_user(), holder.nameChatItem, holder.imageChatItem, activity);

        holder.timeChatItem.setText(getTime.getStyleTime(activity).format(new Date(chatModel.getTime())));

        holder.chatContainerItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewChat.onClickMessage(chatModel);
            }
        });


    }

    @Override
    public void getLastMessage(ChatModel chatModel, final PresenterChat.ViewHolder holder) {

        getMessengerReference().child(chatModel.getChat_id()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        MessageModel messageModel = snapshot.getValue(MessageModel.class);
                        holder.messageChatItem.setText(messageModel.getMessage());
                        holder.timeChatItem.setText(getTime.getStyleTime(activity).format(new Date(messageModel.getTime())));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void noChats(boolean show) {

        getChatsReference().child(getCurrentUserID()).orderByChild("time").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.getChildrenCount() > 0) {
                    nodata.setVisibility(View.GONE);
                    rotateLoading.stop();
                } else {

                    nodata.setVisibility(View.VISIBLE);
                    rotateLoading.stop();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_chat_item)
        CircleImageView imageChatItem;

        @BindView(R.id.name_chat_item)
        CoolTextView nameChatItem;

        @BindView(R.id.time_chat_item)
        CoolTextView timeChatItem;

//        @BindView(R.id.name_container_chat_item)
//        LinearLayout nameContainerChatItem;

        @BindView(R.id.message_chat_item)
        CoolTextView messageChatItem;

        @BindView(R.id.chat_container_item)
        RelativeLayout chatContainerItem;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }

    }
}
