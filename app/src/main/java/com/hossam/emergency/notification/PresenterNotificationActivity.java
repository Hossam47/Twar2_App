package com.hossam.emergency.notification;

import static com.hossam.emergency.firebase.FirebaseContract.getCasesReference;
import static com.hossam.emergency.firebase.FirebaseContract.getCurrentUserID;
import static com.hossam.emergency.firebase.FirebaseContract.getNotificationReference;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.hossam.emergency.R;
import com.hossam.emergency.firebase.UserMainInformation;
import com.hossam.emergency.time.GetTime;
import com.hossam.emergency.ui.cases.CaseModel;
import com.hossam.emergency.ui.detalis_cases.DetailsCases;
import com.hossam.emergency.ui.messanger.MessangerActivity;
import com.hossam.emergency.utils.CoolTextView;
import com.hossam.emergency.utils.FillColor;
import com.hossam.emergency.utils.StringResouces;
import com.hossam.emergency.utils.ToastStyle;
import com.hossam.emergency.utils.UtilsRecyclerView;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PresenterNotificationActivity implements ProviderPresenterNotificationActivity {

    private final Activity activity;
    private final ViewNotificationActivity viewNotificationActivity;
    private final FillColor fillColor = FillColor.getInstance();
    private final StringResouces resouces = StringResouces.getInstance();
    private final GetTime getTime = GetTime.getInstance();
    private final RecyclerView recyclerView;
    private final UserMainInformation userMainInformation = UserMainInformation.getInstance();
    private final UiNotificationActivity uiNotificationActivity;
    private final UtilsRecyclerView utilsRecyclerView = UtilsRecyclerView.getInstance();
    private int currentItems, scrollOutItems, totalItems;
    private boolean isScrolling, showedAllItems;

    public PresenterNotificationActivity(Activity activity, ViewNotificationActivity viewNotificationActivity,
                                         RecyclerView recyclerView, UiNotificationActivity uiNotificationActivity) {
        this.activity = activity;
        this.viewNotificationActivity = viewNotificationActivity;
        this.recyclerView = recyclerView;
        this.uiNotificationActivity = uiNotificationActivity;

//        updatedLoadMore(uiNotificationActivity.getLayoutManager());
    }

    @Override
    public void initRecyclerView() {

        FirebaseRecyclerAdapter<NotificationModel, PresenterNotificationActivity.ViewHolder> firebaseAdapter
                = new FirebaseRecyclerAdapter<NotificationModel, PresenterNotificationActivity.ViewHolder>(
                NotificationModel.class,
                R.layout.notification_items,
                PresenterNotificationActivity.ViewHolder.class,
                getNotificationReference().child(getCurrentUserID()).orderByChild("time")

        ) {
            @Override
            protected void populateViewHolder(PresenterNotificationActivity.ViewHolder holder, NotificationModel model, int position) {

                initMainData(holder, model, position);


            }

        };

        viewNotificationActivity.initNotificationRecyclerView(firebaseAdapter);
        noNotifications(uiNotificationActivity.getNo_notifications_container());
    }

    @Override
    public void initMainData(final PresenterNotificationActivity.ViewHolder holder, final NotificationModel model, int position) {


        initNotificationImage(holder, model);
        initNotificationTitle(holder, model);
        initNotificationTime(holder, model);
        initNotificationDesc(holder, model);
        initUserImage(holder, model);
        initSeenNotificaton(holder, model);

        holder.notification_total_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                converterToCase(holder, model);
                converterToComment(holder, model);
                converterToMessage(holder, model);
                seenNotificaton(model);
            }
        });

        utilsRecyclerView.fadeAnimationRecyclerView(holder,activity);
    }


    @Override
    public void initNotificationTime(PresenterNotificationActivity.ViewHolder holder, NotificationModel model) {

        holder.time_notification_item.setText(getTime.getStyleTime(activity).format(new Date(model.getTime())));

    }

    @Override
    public void initNotificationDesc(PresenterNotificationActivity.ViewHolder holder, NotificationModel model) {

//        holder.descriptionNotificationItem.setText(model.getText());
    }

    @Override
    public void converterToCase(ViewHolder holder, NotificationModel model) {

        if (model.getType() == 0) {

            getCasesReference().child(model.getCase_id()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    CaseModel caseModel = dataSnapshot.getValue(CaseModel.class);


                    if (caseModel != null && !caseModel.isDeleted()) {

                        Intent intent = new Intent(activity, DetailsCases.class);
                        intent.putExtra("case_model", caseModel);
                        activity.startActivity(intent);

                    } else {

                        new ToastStyle(activity).negativeToast("This cases is deleted");
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

    }

    @Override
    public void converterToComment(ViewHolder holder, NotificationModel model) {

        if (model.getType() == 3) {

            getCasesReference().child(model.getCase_id()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    CaseModel caseModel = dataSnapshot.getValue(CaseModel.class);

                    if (!caseModel.isDeleted()) {

                        Intent intent = new Intent(activity, DetailsCases.class);
                        intent.putExtra("case_model", caseModel);
                        activity.startActivity(intent);

                    } else {

                        new ToastStyle(activity).negativeToast("This cases is deleted");

                    }
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

    }

    @Override
    public void converterToMessage(ViewHolder holder, NotificationModel model) {

        if (model.getType() == 2) {

            Intent intent = new Intent(activity, MessangerActivity.class);
            intent.putExtra("other_id", model.getPusher_id());
            activity.startActivity(intent);

        }
    }

    @Override
    public void initUserImage(ViewHolder holder, NotificationModel model) {

        userMainInformation.getUserImageAndNameInformation(model.getPusher_id(),
                holder.user_image_notification_item, holder.name_notification_item, activity);
    }

    @Override
    public void initSeenNotificaton(ViewHolder holder, NotificationModel model) {

        if (model.isSeen()) {

            holder.notification_total_container.setBackgroundColor(activity.getResources().getColor(R.color.white));
        }
    }

    @Override
    public void seenNotificaton(NotificationModel model) {

        getNotificationReference().child(getCurrentUserID()).child(model.getNotification_id()).child("seen").setValue(true);
    }

    @Override
    public void noNotifications(final ImageView container) {

        getNotificationReference().child(getCurrentUserID()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot.getChildrenCount();

                if (dataSnapshot.getChildrenCount() > 0) {

                    container.setVisibility(View.GONE);
                    uiNotificationActivity.getRotateLoadingAlerts().stop();
                } else {

                    container.setVisibility(View.VISIBLE);
                    uiNotificationActivity.getRotateLoadingAlerts().stop();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void updatedLoadMore(final RecyclerView.LayoutManager layoutManager) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                }

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                }

            }

            @Override
            public void onScrolled(final RecyclerView recyclerView, int dx, int dy) {

                currentItems = layoutManager.getChildCount();
                scrollOutItems = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                totalItems = layoutManager.getItemCount();

                Log.v("scroll_detalis", "currentItems " + currentItems + " scrollOutItems " + scrollOutItems + " totalItems " + totalItems);

                if (isScrolling) {

                    if ((currentItems + scrollOutItems == totalItems) && showedAllItems == false && totalItems >= 20) {

//                        styleBarUi.getProgressListCases().setVisibility(View.VISIBLE);

                        new ToastStyle(activity).positiveToast("Loading....");

//                        getItemsIndex += 20;
                        isScrolling = false;
                        ((LinearLayoutManager) layoutManager).setStackFromEnd(true);


                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                initRecyclerView();
//                            styleBarUi.getProgressListCases().setVisibility(View.GONE);

                            }
                        }, 3000);


                        getNotificationReference().child(getCurrentUserID()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                if (dataSnapshot.exists()) {

                                    if (totalItems == dataSnapshot.getChildrenCount()) {

                                        showedAllItems = true;

                                        Log.v("notification_count", "recycler " + totalItems +
                                                " firebase_count " + dataSnapshot.getChildrenCount());
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
//


                }

            }

        });

    }

    @Override
    public void initNotificationImage(PresenterNotificationActivity.ViewHolder holder, NotificationModel model) {

        switch (model.getType()) {

            case 0:
                fillColor.caseNotification(holder.imageNotificationItem, activity);
                break;

            case 1:
                fillColor.savedCaseNotification(holder.imageNotificationItem, activity);
                break;

            case 2:
                fillColor.messageNotification(holder.imageNotificationItem, activity);
                break;

            case 3:
                fillColor.commentNotification(holder.imageNotificationItem, activity);
                break;

        }

    }

    @Override
    public void initNotificationTitle(PresenterNotificationActivity.ViewHolder holder, NotificationModel model) {

        switch (model.getType()) {

            case 0:
                holder.titleNotificationItem.setText(resouces.getStringResource(R.string.case_notification, activity));
                break;

            case 1:
                holder.titleNotificationItem.setText(resouces.getStringResource(R.string.saved_case_notification, activity));
                break;

            case 2:
                holder.titleNotificationItem.setText(resouces.getStringResource(R.string.message_notification, activity));
                break;

            case 3:
                holder.titleNotificationItem.setText(resouces.getStringResource(R.string.comment_notification, activity));
                break;


        }

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.user_image_notification_item)
        ImageView user_image_notification_item;

        @BindView(R.id.image_notification_item)
        ImageView imageNotificationItem;

        @BindView(R.id.title_notification_item)
        CoolTextView titleNotificationItem;

        @BindView(R.id.name_notification_item)
        CoolTextView name_notification_item;

        @BindView(R.id.container_title_notification_item)
        LinearLayout containerTitleNotificationItem;

        @BindView(R.id.notification_total_container)
        RelativeLayout notification_total_container;

//        @BindView(R.id.description_notification_item)
//        CoolTextView descriptionNotificationItem;

        @BindView(R.id.time_notification_item)
        CoolTextView time_notification_item;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }

    }

}
