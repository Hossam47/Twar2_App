package com.hossam.emergency.phone_dialog;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.hossam.emergency.R;
import com.hossam.emergency.animations.AnimationsUtils;
import com.hossam.emergency.ui.cases.CaseModel;
import com.hossam.emergency.dialogs.InitDialog;
import com.hossam.emergency.dialogs.ProviderControllerDialog;
import com.hossam.emergency.firebase.UserMainInformation;
import com.hossam.emergency.ui.sign_up.User;
import com.hossam.emergency.utils.IntentUtils;
import com.hossam.emergency.utils.StringResouces;

import static com.hossam.emergency.firebase.FirebaseContract.getUserReference;

public class PhoneDilaog implements ProviderControllerDialog, ProviderPhoneDialog {


    private final Activity activity;
    private final InitDialog initDialog = new InitDialog();
    private View view;
    private final AnimationsUtils animationsUtils = AnimationsUtils.getInstance();

    private RelativeLayout call, cancel;
    private ImageView image;
    private TextView name;

    private final IntentUtils intentUtils = IntentUtils.getInstance();
    private final UserMainInformation userMainInformation = UserMainInformation.getInstance();
    private final StringResouces stringResouces = StringResouces.getInstance();

    public PhoneDilaog(Activity activity) {
        this.activity = activity;
        initView();
    }


    @Override
    public void initView() {

        view = initDialog.initDialog(R.layout.phone_dialog_layout, activity);

        call = view.findViewById(R.id.call_btn__phone_layout);
        cancel = view.findViewById(R.id.cancel_btn__phone_layout);
        image = view.findViewById(R.id.image_profile_phone_dialog);
        name = view.findViewById(R.id.name_profile_phone_layout);

    }

    @Override
    public void controllerPhoneDialog(final CaseModel caseModel) {

        getUserReference().child(caseModel.getUser_id()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    User user = dataSnapshot.getValue(User.class);

                    onCallClick(user, caseModel);
                    initUserView(user, caseModel);
                    onCancelClick(user, caseModel);


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onCallClick(final User user, final CaseModel caseModel) {

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (caseModel.isShow_mobile()) {

                    intentUtils.intentCall(user.getPhone(), activity);

                } else if (!caseModel.isShow_mobile()) {

                    Toast.makeText(activity, stringResouces.getStringResource(R.string.number_private, activity), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public void initUserView(User user, CaseModel caseModel) {

        userMainInformation.getUserInformation(user, name, image, activity);

    }

    @Override
    public void onCancelClick(User user, CaseModel caseModel) {

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endDialog();
            }
        });

    }



    @Override
    public void startDialog() {
        animationsUtils.animationReaction(view, activity);
        initDialog.showDialog();

    }

    @Override
    public void endDialog() {
        initDialog.cancelDialog();
    }


}
