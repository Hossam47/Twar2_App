package com.hossam.emergency.ui.main;

import static com.hossam.emergency.firebase.FirebaseContract.getCasesCountryReference;
import static com.hossam.emergency.firebase.FirebaseContract.getCasesReference;
import static com.hossam.emergency.firebase.FirebaseContract.getChatsReference;
import static com.hossam.emergency.firebase.FirebaseContract.getCurrentUserID;
import static com.hossam.emergency.firebase.FirebaseContract.getMainReference;
import static com.hossam.emergency.firebase.FirebaseContract.getNotificationReference;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.hossam.emergency.R;
import com.hossam.emergency.animations.AnimationsUtils;
import com.hossam.emergency.firebase.UserMainInformation;
import com.hossam.emergency.gps_provider.GpsController;
import com.hossam.emergency.interfaces.InitController;
import com.hossam.emergency.interfaces.ProviderFirebaseRecyclerAdapter;
import com.hossam.emergency.navigation.ControllerNav;
import com.hossam.emergency.notification.NotificationActivity;
import com.hossam.emergency.process.GPSAvailability;
import com.hossam.emergency.reactions.ReactionController;
import com.hossam.emergency.services.Twar2App;
import com.hossam.emergency.time.GetTime;
import com.hossam.emergency.ui.cases.CaseModel;
import com.hossam.emergency.ui.chat.ChatActivity;
import com.hossam.emergency.ui.detalis_cases.DetailsCases;
import com.hossam.emergency.ui.list_cases.ListCasesActivity;
import com.hossam.emergency.utils.FillColor;
import com.hossam.emergency.utils.StringResouces;
import com.hossam.emergency.utils.UtilsRecyclerView;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ControllerMain implements ProviderMain, InitController, ProviderFirebaseRecyclerAdapter {

    Activity activity;
    UiMainActivity uiMainActivity;
    UtilsRecyclerView utilsRecyclerView = UtilsRecyclerView.getInstance();
    UserMainInformation information = UserMainInformation.getInstance();
    GPSAvailability gpsAvailability = GPSAvailability.getInstance();
    ReactionController reactionController;
    FillColor fillColor = FillColor.getInstance();
    StringResouces stringResouces = StringResouces.getInstance();
    AnimationsUtils animationsUtils = AnimationsUtils.getInstance();
    GpsController gpsController;
    GetTime getTime = GetTime.getInstance();
    Query query = getCasesReference().orderByChild("deleted").equalTo(false).limitToFirst(5);
    Twar2App twar2App;
    ControllerNav controllerNav;


    public ControllerMain(Activity activity, UiMainActivity uiMainActivity) {
        this.activity = activity;
        this.uiMainActivity = uiMainActivity;

        controllerNav = new ControllerNav(activity);
        gpsController = new GpsController(activity);
        reactionController = new ReactionController(activity);
        getMainReference().child("token").setValue(FirebaseInstanceId.getInstance().getToken());
        twar2App = (Twar2App) activity.getApplication();
    }

    public ControllerMain(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void initController() {

        initRecyclerView(query);
        initServices();
        initMainData();
        initSeeMore();
        initNavUser();
        onClickFireContainer();
        onClickInboxContainer();
        onClickNotificationContainer();
        getCountFire();
        getCountInbox();
        getCountNotification();
        refreshToken();
    }

    @Override
    public void initRecyclerView(Query query) {

        FirebaseRecyclerAdapter<CaseModel, ViewHolder> firebaseAdapter
                = new FirebaseRecyclerAdapter<CaseModel, ViewHolder>(

                CaseModel.class,
                R.layout.im_case_items,
                ViewHolder.class,
                getCasesReference().orderByChild("deleted").equalTo(false).limitToFirst(5)

        ) {
            @Override
            protected void populateViewHolder(ViewHolder holder, CaseModel model, int position) {
                utilsRecyclerView.fadeAnimationRecyclerView(holder, activity);

                if (!model.isDeleted() && !model.isSaved()) {

                    setRecyclerData(holder, model);

                } else if (model.isDeleted() || model.isSaved()) {

                    holder.container.setLayoutParams(new RelativeLayout.LayoutParams(0, 0));
                    holder.container.setLayoutParams(new RelativeLayout.MarginLayoutParams(0, 0));
                    holder.container.setVisibility(View.GONE);
                }

            }

        };

        uiMainActivity.getRecyclerView().setAdapter(firebaseAdapter);
        firebaseAdapter.notifyDataSetChanged();
    }


    @Override
    public void initMainData() {

    }

    @Override
    public void onClickFireContainer() {

        uiMainActivity.getFireContainer().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationsUtils.animationReaction(uiMainActivity.getFireContainer(), activity);
                activity.startActivity(new Intent(activity, ListCasesActivity.class));
            }
        });
    }

    @Override
    public void onClickInboxContainer() {

        uiMainActivity.getInboxContainer().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationsUtils.animationReaction(uiMainActivity.getInboxContainer(), activity);
                activity.startActivity(new Intent(activity, ChatActivity.class));
            }
        });
    }

    @Override
    public void onClickNotificationContainer() {

        uiMainActivity.getNotificationContainer().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationsUtils.animationReaction(uiMainActivity.getNotificationContainer(), activity);
                activity.startActivity(new Intent(activity, NotificationActivity.class));
            }
        });
    }

    @Override
    public void getCountFire() {

        getCasesReference().orderByChild("deleted").equalTo(false).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                uiMainActivity.getCountFire().setText(dataSnapshot.getChildrenCount() + "");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void getCountInbox() {

        getChatsReference().child(getCurrentUserID()).orderByChild("deleted").equalTo(false).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                uiMainActivity.getCountInbox().setText(dataSnapshot.getChildrenCount() + "");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void getCountNotification() {

        getNotificationReference().child(getCurrentUserID()).orderByChild("seen").equalTo(false).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                uiMainActivity.getCountNotification().setText(dataSnapshot.getChildrenCount() + "");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void initSeeMore() {

        uiMainActivity.getSee_more().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activity.startActivity(new Intent(activity, ListCasesActivity.class));
            }
        });
    }


    @Override
    public void setCasesMarkers(final GoogleMap googleMap) {

        gpsController.setCasesMarkers(googleMap, true);

    }

    @Override
    public void initNavUser() {

        controllerNav.initNavUi();
    }

    @Override
    public void profileImageCacheUpdated() {

    }

    @Override
    public void refreshToken() {

        getMainReference().child("token").setValue(FirebaseInstanceId.getInstance().getToken());

    }

    @Override
    public void setRecyclerData(RecyclerView.ViewHolder viewHolder, Object model1) {

        final CaseModel model = (CaseModel) model1;
        ViewHolder holder = (ViewHolder) viewHolder;

        information.getUserInformation(model.getUser_id(), holder.name, holder.image, activity);
        getCurrentTime(model, holder.time);
        holder.title_case.setText(model.getTitle());
        gpsController.getCurrentDistance(model.getLatitude(), model.getLongitude(), holder.distance_case);
        reactionController.countReaction(model.getCase_id(), holder.fire_numbers);
        reactionController.countComments(model.getCase_id(), holder.comments_numbers);

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity, DetailsCases.class);
                intent.putExtra("case_model", model);
                activity.startActivity(intent);
            }
        });

    }

    private void getCurrentTime(CaseModel caseModel, TextView time) {

        Date resultdate = new Date(caseModel.getTime());
        time.setText(String.valueOf(getTime.getStyleTime(activity).format(resultdate)));
    }

    @Override
    public void initServices() {

        if (gpsAvailability.checkNetwork(activity)) {

            fillColor.connectedIcon(uiMainActivity.getNetwork_image(), activity);
            uiMainActivity.getNetwork_state().setText(stringResouces.getStringResource(R.string.network_services_on, activity));

        } else {

            fillColor.unConnectedIcon(uiMainActivity.getNetwork_image(), activity);
            uiMainActivity.getNetwork_state().setText(stringResouces.getStringResource(R.string.network_services_off, activity));
        }


        if (gpsAvailability.checkGPS(activity)) {

            fillColor.gpsOnIcon(uiMainActivity.getGps_image(), activity);
            uiMainActivity.getGps_state().setText(stringResouces.getStringResource(R.string.gps_services_on, activity));

        } else {

            fillColor.gpsOffIcon(uiMainActivity.getGps_image(), activity);
            uiMainActivity.getGps_state().setText(stringResouces.getStringResource(R.string.gps_services_off, activity));
        }


    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_im_items)
        ImageView image;

        @BindView(R.id.name_im_items)
        TextView name;

        @BindView(R.id.time_im_items)
        TextView time;

        @BindView(R.id.case_im_items)
        com.borjabravo.readmoretextview.ReadMoreTextView title_case;

        @BindView(R.id.location_im_items)
        TextView distance_case;

        @BindView(R.id.fire_numbers)
        TextView fire_numbers;

        @BindView(R.id.comments_numbers)
        TextView comments_numbers;

        @BindView(R.id.container_im_items)
        RelativeLayout container;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }

    }

}
