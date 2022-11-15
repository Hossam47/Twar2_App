package com.hossam.emergency.ui.detalis_cases;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.hossam.emergency.R;
import com.hossam.emergency.adapters.ImageCaseDetalisAdapter;
import com.hossam.emergency.algorithem.SortAlgorithms;
import com.hossam.emergency.animations.AnimationsUtils;
import com.hossam.emergency.ui.cases.CaseModel;
import com.hossam.emergency.ui.comments.CommentModel;
import com.hossam.emergency.ui.comments.CommentsAdapter;
import com.hossam.emergency.dialogs.InitDialog;
import com.hossam.emergency.firebase.FirebaseNotification;
import com.hossam.emergency.firebase.UserMainInformation;
import com.hossam.emergency.ui.following_cases.FollowingCasesModel;
import com.hossam.emergency.gps_provider.GpsController;
import com.hossam.emergency.ui.messanger.MessangerActivity;
import com.hossam.emergency.models.ImageModel;
import com.hossam.emergency.notification.NotificationModel;
import com.hossam.emergency.phone_dialog.PhoneDilaog;
import com.hossam.emergency.reactions.ViewModel;
import com.hossam.emergency.services.Twar2App;
import com.hossam.emergency.time.GetTime;
import com.hossam.emergency.utils.FillColor;
import com.hossam.emergency.utils.IntentUtils;
import com.hossam.emergency.utils.StringResouces;
import com.hossam.emergency.utils.ToastStyle;

import java.util.ArrayList;
import java.util.Date;

import static com.hossam.emergency.firebase.FirebaseContract.getCasesCountryReference;
import static com.hossam.emergency.firebase.FirebaseContract.getCasesReference;
import static com.hossam.emergency.firebase.FirebaseContract.getCommentsReference;
import static com.hossam.emergency.firebase.FirebaseContract.getCurrentUserID;
import static com.hossam.emergency.firebase.FirebaseContract.getFollowingReference;
import static com.hossam.emergency.firebase.FirebaseContract.getNotificationReference;
import static com.hossam.emergency.firebase.FirebaseContract.getViewsReference;
import static com.hossam.emergency.time.GetTime.getInstance;
import static com.hossam.emergency.time.GetTime.getUTCTimetamp;

public class ControllerDetailsCases implements ProviderDetalisCases {

    Activity activity;
    UIDetalisCases uiDetalisCases;
    CaseModel caseModel;
    ArrayList<ImageModel> imageList = new ArrayList<>();
    ArrayList<CommentModel> commentList = new ArrayList<>();
    GetTime getTime = getInstance();
    ImageCaseDetalisAdapter imageAdapter;
    CommentsAdapter commentsAdapter;
    UserMainInformation userMainInformation = UserMainInformation.getInstance();
    SortAlgorithms sortAlgorithms = new SortAlgorithms();
    IntentUtils intentUtils = IntentUtils.getInstance();
    StringResouces stringResouces = StringResouces.getInstance();
    GpsController gpsController;
    AnimationsUtils animationsUtils = AnimationsUtils.getInstance();
    StringResouces resouces = StringResouces.getInstance();
    FillColor fillColor = FillColor.getInstance();
    ToastStyle toastStyle;
    FirebaseNotification firebaseNotification = FirebaseNotification.getInstance();
//    SharedPreferences sharedPref;
//    String SHARED_PREF = "RATE_FILE";
//    private View view;
//    private final InitDialog initDialog = new InitDialog();

    Twar2App twar2App;

    public ControllerDetailsCases(Activity activity, UIDetalisCases uiDetalisCases, CaseModel caseModel) {
        this.activity = activity;
        this.uiDetalisCases = uiDetalisCases;
        this.caseModel = caseModel;

        gpsController = new GpsController(activity);
        toastStyle = new ToastStyle(activity);
        twar2App = (Twar2App) activity.getApplication();
//        sharedPref = activity.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        styleData();
        initData();
        initFollowCase(uiDetalisCases);
        initImageList();
        initCommentsList();
        pushComment();
        callPhonePost();
        mapViewPost();
        streetViewPost();
        getCaseDistance();
        manageDetalisActivity();
        checkFollowCase(uiDetalisCases);
        directMessageCase();
        initPushComment();
        initUI();
    }

    @Override
    public void initData() {

        uiDetalisCases.getTitleDetails().setText(caseModel.getTitle());
        uiDetalisCases.getDesc().setText(caseModel.getDescription());
        uiDetalisCases.getTimeDetails().setText(String.valueOf(getTime.getStyleTime(activity).format(new Date(caseModel.getTime()))));
        userMainInformation.getUserInformation(caseModel.getUser_id(), uiDetalisCases.getNameDetails(), uiDetalisCases.getImageDetails(), activity);

//        connections.connectURL(NOTIFICATION_URL);
//        connections.sendCaseNotification(caseModel);
    }

    @Override
    public void initImageList() {

        imageList = new ArrayList<>();

        getCasesReference().child(caseModel.getCase_id()).child("images").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        ImageModel model = snapshot.getValue(ImageModel.class);
                        imageList.add(model);
                    }

                    sortAlgorithms.sortImage(imageList);

                    imageAdapter = new ImageCaseDetalisAdapter(activity, imageList);
                    uiDetalisCases.getRecyclerView().setAdapter(imageAdapter);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void initCommentsList() {

        getCommentsReference().child(caseModel.getCase_id()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                commentList = new ArrayList<>();

                if (dataSnapshot.exists()) {

                    if (commentList != null) {
                        commentList.clear();
                    }

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        CommentModel model = snapshot.getValue(CommentModel.class);

                        if (!model.isDeleted()) {
                            commentList.add(model);
                        }
                    }

                    sortAlgorithms.sortComments(commentList);

                    commentsAdapter = new CommentsAdapter(commentList, activity);
                    uiDetalisCases.getRecyclerView_Comments().setAdapter(commentsAdapter);

                }

                uiDetalisCases.getNumber_comments()
                        .setText(stringResouces.getStringResource(R.string.numbers_comment, activity) + " " + commentList.size());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void pushComment() {

        uiDetalisCases.getSenderIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(uiDetalisCases.getComment().getText())) {

                    DatabaseReference comments_path = getCommentsReference().child(caseModel.getCase_id());

                    String comment_id = comments_path.push().getKey();

                    comments_path.child(comment_id).setValue(
                            new CommentModel(
                                    comment_id, uiDetalisCases.getComment().getText().toString()
                                    , getCurrentUserID(), caseModel.getCase_id(), getUTCTimetamp(), false
                            )
                    );

                    uiDetalisCases.getComment().setText("");

                    followingCases();

                    pushCommentNotification(comment_id);

                    fillColor.fillColorGrey(uiDetalisCases.getSenderIcon(), activity);
                }
            }
        });


    }

    @Override
    public void initPushComment() {

        uiDetalisCases.getComment().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) {
                    fillColor.fillColorOrangeTrans(uiDetalisCases.getSenderIcon(), activity);
                }

                if (count == 0) {
                    fillColor.fillColorGrey(uiDetalisCases.getSenderIcon(), activity);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void pushCommentNotification(String comment_id) {

        DatabaseReference reference = getNotificationReference().child(caseModel.getUser_id()).push();

        if (!caseModel.getUser_id().equals(getCurrentUserID())) {

            String notification_id = reference.getKey();

            firebaseNotification.pushCommentNotification(new NotificationModel(
                    getCurrentUserID(),
                    caseModel.getUser_id(),
                    caseModel.getCase_id(),
                    comment_id,
                    "",
                    "",
                    notification_id,
                    "Comment on " + caseModel.getTitle(),
                    getUTCTimetamp(),
                    3,
                    false
            ));

        }
    }

    @Override
    public void styleData() {

        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //fillColor.fillColorGrey(uiDetalisCases.getSenderIcon(), activity);

    }

    @Override
    public void pushView() {

        if (!caseModel.getUser_id().equals(getCurrentUserID())) {

            DatabaseReference reference = getViewsReference().child(caseModel.getCase_id()).push();
            String view_id = reference.getKey();
            reference.setValue(new ViewModel(view_id, getCurrentUserID(), caseModel.getCase_id(), getUTCTimetamp()));
        }
    }

    @Override
    public void initFollowCase(final UIDetalisCases uiDetalisCases) {

        getFollowingReference().child(getCurrentUserID()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    if (dataSnapshot.hasChild(caseModel.getCase_id())) {

                        fillColor.fillColorRed50(uiDetalisCases.getIconFollowDetailsPartOne(), activity);
                        fillColor.connectedIcon(uiDetalisCases.getIconFollowDetailsPartOne(), activity);
                        uiDetalisCases.getFollowCaseDetailsPartOne().setBackground(activity.getResources().getDrawable(R.drawable.sq_line_green));
                        uiDetalisCases.getTitleFollowDetailsPartOne().setText(resouces.getStringResource(R.string.following_case_list, activity));

                    } else {

                        fillColor.fillColorBlue(uiDetalisCases.getIconFollowDetailsPartOne(), activity);
                        fillColor.followIcon(uiDetalisCases.getIconFollowDetailsPartOne(), activity);
                        uiDetalisCases.getFollowCaseDetailsPartOne().setBackground(activity.getResources().getDrawable(R.drawable.sq_line_blue));
                        uiDetalisCases.getTitleFollowDetailsPartOne().setText(resouces.getStringResource(R.string.follow_case_list, activity));
                    }
                } else {

                    fillColor.fillColorBlue(uiDetalisCases.getIconFollowDetailsPartOne(), activity);
                    fillColor.followIcon(uiDetalisCases.getIconFollowDetailsPartOne(), activity);
                    uiDetalisCases.getFollowCaseDetailsPartOne().setBackground(activity.getResources().getDrawable(R.drawable.sq_line_blue));
                    uiDetalisCases.getTitleFollowDetailsPartOne().setText(resouces.getStringResource(R.string.follow_case_list, activity));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void checkFollowCase(final UIDetalisCases uiDetalisCases) {

        uiDetalisCases.getFollowCaseDetailsPartOne().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFollowingReference().child(getCurrentUserID()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {

                            if (dataSnapshot.hasChild(caseModel.getCase_id())) {

                                getFollowingReference().child(getCurrentUserID()).child(caseModel.getCase_id()).setValue(null);
                                initFollowCase(uiDetalisCases);
                                toastStyle.negativeToast("Unfollow");

                            } else {

                                getFollowingReference().child(getCurrentUserID()).child(caseModel.getCase_id())
                                        .setValue(new FollowingCasesModel(caseModel.getCase_id(), getUTCTimetamp()));
                                initFollowCase(uiDetalisCases);
                                toastStyle.positiveToast("Now you are following this case ");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                animationsUtils.animationReaction(uiDetalisCases.getFollowCaseDetailsPartOne(), activity);
            }
        });

    }

    @Override
    public void directMessageCase() {

        uiDetalisCases.getDirectMessageBtnContainer().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!getCurrentUserID().equals(caseModel.getUser_id())) {

                    Intent intent = new Intent(activity, MessangerActivity.class);
                    intent.putExtra("other_id", caseModel.getUser_id());
                    activity.startActivity(intent);

                    animationsUtils.animationReaction(uiDetalisCases.getFollowCaseDetailsPartOne(), activity);
                }
            }
        });

    }

    @Override
    public void followingCases() {
        getFollowingReference().child(getCurrentUserID()).child(caseModel.getCase_id())
                .setValue(new FollowingCasesModel(caseModel.getCase_id(), GetTime.getUTCTimetamp()));
    }

    @Override
    public void callPhonePost() {

        uiDetalisCases.getCall_container().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!getCurrentUserID().equals(caseModel.getUser_id())) {

                    PhoneDilaog phoneDilaog = new PhoneDilaog(activity);
                    animationsUtils.animationReaction(uiDetalisCases.getCall_container(), activity);
                    phoneDilaog.controllerPhoneDialog(caseModel);
                    phoneDilaog.startDialog();
                }
//                if (caseModel.isShow_mobile()) {
//
//                    getUserReference().child(caseModel.getUser_id()).addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//
//                            User user = dataSnapshot.getValue(User.class);
//                            intentUtils.intentCall(user.getPhone().toString(), activity);
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });
//
//                } else {
//
//                    Toast.makeText(activity, stringResouces.getStringResource(R.string.number_private, activity), Toast.LENGTH_SHORT).show();
//                }
            }
        });


    }

    @Override
    public void streetViewPost() {

        uiDetalisCases.getStreet_container().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                StreetViewDialog streetViewDialog = new StreetViewDialog(activity);
//
//                animationsUtils.animationReaction(uiDetalisCases.getStreet_container(), activity);
//                streetViewDialog.controllerStreetDialog(caseModel);
//                streetViewDialog.startDialog();

                intentUtils.intentStreetView(caseModel.getLatitude(), caseModel.getLongitude(), activity);

            }
        });

    }

    @Override
    public void mapViewPost() {

        uiDetalisCases.getMap_container().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                animationsUtils.animationReaction(uiDetalisCases.getMap_container(), activity);
                intentUtils.intentCaseMap(caseModel, activity);
//
//                phoneDilaog.controllerPhoneDialog(caseModel);
//                phoneDilaog.startDialog();
            }
        });
    }

    @Override
    public void getCaseDistance() {

        gpsController.getCurrentDistance(caseModel.getLatitude(), caseModel.getLongitude(), uiDetalisCases.getDistanceDetails());
    }

    @Override
    public void manageDetalisActivity() {

        if (caseModel.isSaved()) {

            uiDetalisCases.getSaved_message_container().setVisibility(View.VISIBLE);
            uiDetalisCases.getMessageContainer().setVisibility(View.GONE);
            uiDetalisCases.getContainerMainFeature().setVisibility(View.GONE);
            uiDetalisCases.getFollowCaseDetailsPartOne().setVisibility(View.GONE);

        } else if (!caseModel.isSaved()) {

            uiDetalisCases.getSaved_message_container().setVisibility(View.GONE);
            uiDetalisCases.getMessageContainer().setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void initUI() {

//        view = initDialog.initDialog(R.layout.rate_dialog, activity);
//        initDialog.hideDialog(false);
//
//        RelativeLayout ok_btn = view.findViewById(R.id.yes_rate_dialog);
//        RelativeLayout cancel_btn = view.findViewById(R.id.cancel_rate_dialog);
//        final CheckBox checkBox = view.findViewById(R.id.check_rate);
//
//        if (!getCheckRate()) {
//
//            initDialog.showDialog();
//        }
//
//        ok_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                activity.startActivity(new Intent(Intent.ACTION_VIEW,
//                        Uri.parse("https://play.google.com/store/apps/details?id=" + activity.getPackageName())));
//
//                saveCheckRate(true);
//                initDialog.cancelDialog();
//            }
//        });
//
//        cancel_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                saveCheckRate(checkBox.isChecked());
//                Toast.makeText(activity, activity.getResources().getString(R.string.response_rate), Toast.LENGTH_LONG).show();
////                toastStyle.negativeToast(activity.getResources().getString(R.string.response_rate));
//                initDialog.cancelDialog();
//
//            }
//        });


    }


//    private void saveCheckRate(boolean check) {
//        SharedPreferences.Editor editor = sharedPref.edit();
//        editor.putBoolean("check_value", check);
//        editor.commit();
//
//    }

//    private boolean getCheckRate() {
//        return sharedPref.getBoolean("check_value", false);
//    }
}
