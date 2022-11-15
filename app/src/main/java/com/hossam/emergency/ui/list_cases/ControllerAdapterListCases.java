package com.hossam.emergency.ui.list_cases;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.hossam.emergency.R;
import com.hossam.emergency.animations.AnimationsUtils;
import com.hossam.emergency.ui.cases.CaseModel;
import com.hossam.emergency.ui.detalis_cases.DetailsCases;
import com.hossam.emergency.firebase.UserMainInformation;
import com.hossam.emergency.ui.following_cases.FollowingCasesModel;
import com.hossam.emergency.gps_provider.GpsController;
import com.hossam.emergency.models.ImageModel;
import com.hossam.emergency.process.ImageProcess;
import com.hossam.emergency.reactions.ReactionController;
import com.hossam.emergency.services.Twar2App;
import com.hossam.emergency.ui.sign_up.User;
import com.hossam.emergency.time.GetTime;
import com.hossam.emergency.utils.FillColor;
import com.hossam.emergency.utils.StringResouces;
import com.hossam.emergency.utils.ToastStyle;
import com.hossam.emergency.utils.utils_activity.DisplayImageActivity;

import java.util.ArrayList;
import java.util.Date;


import static com.hossam.emergency.firebase.FirebaseContract.getCasesCountryReference;
import static com.hossam.emergency.firebase.FirebaseContract.getCasesReference;
import static com.hossam.emergency.firebase.FirebaseContract.getCurrentUserID;
import static com.hossam.emergency.firebase.FirebaseContract.getFollowingReference;
import static com.hossam.emergency.firebase.FirebaseContract.getUserReference;
import static com.hossam.emergency.time.GetTime.getUTCTimetamp;

public class ControllerAdapterListCases implements ProviderAdapterListCases {

    private final Activity activity;
    private final UserMainInformation information = UserMainInformation.getInstance();
    private final GetTime getTime = GetTime.getInstance();
    private final ImageProcess imageProcess = ImageProcess.getInstance();
    private final GpsController gpsController;
    private final ReactionController reactionController;
    private final StringResouces resouces = StringResouces.getInstance();
    private final FillColor fillColor = FillColor.getInstance();
    private final AnimationsUtils animationsUtils = AnimationsUtils.getInstance();
    private final ToastStyle toastStyle;

    Twar2App twar2App;

    public ControllerAdapterListCases(Activity activity) {
        this.activity = activity;
        reactionController = new ReactionController(activity);
        gpsController = new GpsController(activity);
        toastStyle = new ToastStyle(activity);

        twar2App = (Twar2App) activity.getApplication();

    }

    @Override
    public void mainInformation(CaseModel caseModel, AdapterListCases.ViewHolder holder) {


        information.getUserInformation(caseModel.getUser_id(), holder.name, holder.image, activity);
        initReaction(caseModel, holder);
        utilsReaction(caseModel, holder);
        initComments(caseModel, holder);
        initViewReaction(caseModel, holder);
        initCaseType(caseModel, holder);
        setLocationDistance(caseModel, holder);

        holder.title.setText(caseModel.getTitle());
        holder.desc.setText(caseModel.getDescription());
        holder.time.setText(getTime.getStyleTime(activity).format(new Date(caseModel.getTime())));
    }

    @Override
    public void loadCaseImage(CaseModel model, final ImageView imageView) {

        getCasesReference().child(model.getCase_id()).child("images").limitToFirst(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final ArrayList<ImageModel> imageModels = new ArrayList<>();

                if (dataSnapshot.exists()) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        ImageModel model = snapshot.getValue(ImageModel.class);
                        imageModels.add(model);
//                        imageProcess.loadImage(imageView, model.getUrl(), activity);

                        Glide.with(activity).load(model.getUrl()).into(imageView);
                    }

                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            // displayImage.displayMultiImage(activity, imageModels);
//                            displayImageDialog.onDisplayImage(imageModels.get(0).getUrl());
//                            displayImageDialog.startDialog();

                            Intent intent = new Intent(activity, DisplayImageActivity.class);
                            intent.putExtra("image_url", imageModels.get(0).getUrl());
                            activity.overridePendingTransition(R.anim.hold, R.anim.fade_in);
                            activity.startActivity(intent);

                        }
                    });

                } else {
                    imageView.setLayoutParams(new RelativeLayout.LayoutParams(0, 0));
                    imageView.setLayoutParams(new RelativeLayout.MarginLayoutParams(0, 0));
                    imageView.setVisibility(View.GONE);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    @Override
    public void onClickCard(AdapterListCases.ViewHolder holder, final CaseModel caseModel) {

        holder.case_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity, DetailsCases.class);
                intent.putExtra("case_model", caseModel);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public void initReaction(final CaseModel caseModel, final AdapterListCases.ViewHolder holder) {

        holder.fire_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reactionController.checkReaction(caseModel.getCase_id(), holder.fire_image);
            }
        });

        reactionController.countReaction(caseModel.getCase_id(), holder.fire_number);
    }

    @Override
    public void initCaseType(CaseModel caseModel, AdapterListCases.ViewHolder holder) {

        if (caseModel.getCase_type() != null)
            switch (caseModel.getCase_type()) {

                case "Accident":
                    holder.case_type_name.setText("#".concat(activity.getResources().getString(R.string.accident_type_case)));
                    holder.case_type_container.setCardBackgroundColor(ContextCompat.getColor(activity,R.color.orange_solid));
                    break;

                case "Kidnapping":
                    holder.case_type_name.setText("#".concat(activity.getResources().getString(R.string.kidnapping_type_case)));
                    holder.case_type_container.setCardBackgroundColor(ContextCompat.getColor(activity,R.color.red_solid));
                    break;

                case "Helping":
                    holder.case_type_name.setText("#".concat(activity.getResources().getString(R.string.helping_type_case)));
                    holder.case_type_container.setCardBackgroundColor(activity.getResources().getColor(R.color.green_solid));
                    break;

                case "Lost / Found":
                    holder.case_type_name.setText("#".concat(activity.getResources().getString(R.string.lost_found_type_case)));
                    holder.case_type_container.setCardBackgroundColor(activity.getResources().getColor(R.color.grey));
                    break;
            }
    }

    @Override
    public void utilsReaction(CaseModel caseModel, AdapterListCases.ViewHolder holder) {

        reactionController.initColoredReaction(caseModel.getCase_id(), holder.fire_image);
    }

    @Override
    public void initComments(final CaseModel caseModel, AdapterListCases.ViewHolder holder) {

        holder.comment_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity, DetailsCases.class);
                intent.putExtra("case_model", caseModel);
                activity.startActivity(intent);
            }
        });

        reactionController.countComments(caseModel.getCase_id(), holder.comment_number);

        reactionController.initColoredCommentsReaction(caseModel.getCase_id(), holder.comment_image);
    }

    @Override
    public void initViewReaction(CaseModel caseModel, AdapterListCases.ViewHolder holder) {

        reactionController.countViews(caseModel.getCase_id(), holder.view_number);
        reactionController.initColoredViewReaction(caseModel.getCase_id(), holder.view_image);
    }

    @Override
    public void setLocationDistance(CaseModel caseModel, AdapterListCases.ViewHolder holder) {

        gpsController.getCurrentDistance(caseModel.getLatitude(), caseModel.getLongitude(), holder.distance);

    }

    @Override
    public void checkFollowCase(final CaseModel caseModel, final AdapterListCases.ViewHolder holder) {

        holder.follow_case_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFollowingReference().child(getCurrentUserID()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {

                            if (dataSnapshot.hasChild(caseModel.getCase_id())) {

                                getFollowingReference().child(getCurrentUserID()).child(caseModel.getCase_id()).setValue(null);
                                initFollowCase(caseModel, holder);
                                toastStyle.negativeToast("Unfollow");

                            } else {

                                getFollowingReference().child(getCurrentUserID()).child(caseModel.getCase_id())
                                        .setValue(new FollowingCasesModel(caseModel.getCase_id(), getUTCTimetamp()));
                                initFollowCase(caseModel, holder);
                                toastStyle.positiveToast("Now you are following this case ");
                            }
                        } else {

                            getFollowingReference().child(getCurrentUserID()).child(caseModel.getCase_id())
                                    .setValue(new FollowingCasesModel(caseModel.getCase_id(), getUTCTimetamp()));
                            initFollowCase(caseModel, holder);
                            toastStyle.positiveToast("Now you are following this case ");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                animationsUtils.animationReaction(holder.follow_case_container, activity);
            }
        });
    }

    @Override
    public void initFollowCase(final CaseModel caseModel, final AdapterListCases.ViewHolder holder) {

        getFollowingReference().child(getCurrentUserID()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    if (dataSnapshot.hasChild(caseModel.getCase_id())) {

                        fillColor.fillColorRed50(holder.icon_follow_case, activity);
                        fillColor.connectedIcon(holder.icon_follow_case, activity);
                        holder.follow_case_container.setBackground(activity.getResources().getDrawable(R.drawable.sq_line_green));
                        holder.title_follow_case.setText(resouces.getStringResource(R.string.following_case_list, activity));

                    } else {

                        fillColor.fillColorBlue(holder.icon_follow_case, activity);
                        fillColor.followIcon(holder.icon_follow_case, activity);
                        holder.follow_case_container.setBackground(activity.getResources().getDrawable(R.drawable.sq_line_blue));
                        holder.title_follow_case.setText(resouces.getStringResource(R.string.follow_case_list, activity));
                    }
                } else {
                    fillColor.fillColorBlue(holder.icon_follow_case, activity);
                    fillColor.followIcon(holder.icon_follow_case, activity);
                    holder.follow_case_container.setBackground(activity.getResources().getDrawable(R.drawable.sq_line_blue));
                    holder.title_follow_case.setText(resouces.getStringResource(R.string.follow_case_list, activity));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void initFollowSavedCase(CaseModel caseModel, AdapterListCases.ViewHolder holder) {

        if (caseModel.isSaved()) {
            holder.follow_case_container.setVisibility(View.GONE);
        } else {
            holder.follow_case_container.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void initBannerFeedAds(AdapterListCases.AdsHolder holder, Activity activity) {

//        nativeListcasesAds(holder.adContainer, activity);

//        casesBannerAdsGoogle(holder.ads_feed_banner_cases);
    }


    @Override
    public void displayProfileImage(final CaseModel model, AdapterListCases.ViewHolder holder, final Activity activity) {

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserReference().child(model.getUser_id()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {

                            if (model.isShow_profile()) {

                                User user = dataSnapshot.getValue(User.class);

                                Intent intent = new Intent(activity, DisplayImageActivity.class);
                                intent.putExtra("image_url", user.getImage());
                                activity.startActivity(intent);
//                                ArrayList<User> users = new ArrayList<>();
//                                users.add(user);
//
//                                displayImage.displaySingleImage(activity, users);

                            } else if (!model.isShow_profile()) {
                                Toast.makeText(activity, "Private Image", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });


    }


}
