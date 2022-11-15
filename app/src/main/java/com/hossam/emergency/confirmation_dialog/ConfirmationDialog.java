package com.hossam.emergency.confirmation_dialog;

import android.app.Activity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hossam.emergency.R;
import com.hossam.emergency.animations.AnimationsUtils;
import com.hossam.emergency.ui.cases.CaseModel;
import com.hossam.emergency.dialogs.InitDialog;
import com.hossam.emergency.dialogs.ProviderControllerDialog;
import com.hossam.emergency.firebase.UserMainInformation;
import com.hossam.emergency.utils.IntentUtils;
import com.hossam.emergency.utils.StringResouces;
import com.hossam.emergency.utils.ToastStyle;

import static com.hossam.emergency.firebase.FirebaseContract.getCasesReference;

public class ConfirmationDialog implements ProviderControllerDialog, ProviderConfirmationDialog {

    ToastStyle toastStyle;
    private final Activity activity;
    private final InitDialog initDialog = new InitDialog();
    private View view;
    private final AnimationsUtils animationsUtils = AnimationsUtils.getInstance();
    private RelativeLayout yes, cancel;
    private TextView title;
    private final IntentUtils intentUtils = IntentUtils.getInstance();
    private final UserMainInformation userMainInformation = UserMainInformation.getInstance();
    private final StringResouces stringResouces = StringResouces.getInstance();

    public ConfirmationDialog(Activity activity) {
        this.activity = activity;
        toastStyle = new ToastStyle(activity);
        initView();
    }

    @Override
    public void initView() {

        view = initDialog.initDialog(R.layout.confirmation_dialog, activity);

        title = view.findViewById(R.id.title_confirmation_dialog);
        yes = view.findViewById(R.id.yes_confirmation_dialog);
        cancel = view.findViewById(R.id.cancel_confirmation_dialog);

    }

    @Override
    public void changeTitle(TextView title) {

    }

    @Override
    public void onClickDelete(RelativeLayout relativeLayout, final CaseModel model) {

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getCasesReference().child(model.getCase_id()).child("deleted").setValue(true);

                toastStyle.positiveToast(stringResouces.getStringResource(R.string.delete_post_message, activity));
            }
        });
    }

    @Override
    public void onClickSaved(RelativeLayout relativeLayout, final CaseModel model) {

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getCasesReference().child(model.getCase_id()).child("saved").setValue(true);

                toastStyle.positiveToast(stringResouces.getStringResource(R.string.saved_post_message, activity));
            }
        });

    }


    @Override
    public void onClickCancel() {

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                endDialog();
            }
        });
    }

    @Override
    public void startDialog() {
        animationsUtils.animationReactionInterpolator(view, activity);
        initDialog.showDialog();

    }

    @Override
    public void endDialog() {
        initDialog.cancelDialog();
    }

}
