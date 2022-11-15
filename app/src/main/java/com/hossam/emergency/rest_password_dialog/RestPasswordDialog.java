package com.hossam.emergency.rest_password_dialog;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.hossam.emergency.R;
import com.hossam.emergency.animations.AnimationsUtils;
import com.hossam.emergency.dialogs.InitDialog;
import com.hossam.emergency.dialogs.ProviderControllerDialog;
import com.hossam.emergency.utils.CoolButton;
import com.hossam.emergency.utils.StringResouces;
import com.hossam.emergency.utils.ToastStyle;

import static com.hossam.emergency.firebase.FirebaseContract.mAuthentication;

public class RestPasswordDialog implements ProviderControllerDialog, ProviderRestPassword {


    public InitDialog initDialog = new InitDialog();
    private final Activity activity;
    private final AnimationsUtils animationsUtils = AnimationsUtils.getInstance();
    private final StringResouces resouces = StringResouces.getInstance();
    private final ToastStyle toastStyle;
    private View view;

    //view
    private ImageView close_btn;
    private EditText email_rest;
    private CoolButton btn_rest;


    public RestPasswordDialog(Activity activity) {
        this.activity = activity;
        toastStyle = new ToastStyle(activity);
        initView();
    }

    @Override
    public void initView() {
        view = initDialog.initDialog(R.layout.rest_password_dialog, activity);

        close_btn = view.findViewById(R.id.btn_close_rest_dialog);
        email_rest = view.findViewById(R.id.reset_password_edit);
        btn_rest = view.findViewById(R.id.btn_rest_password);


        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endDialog();
            }
        });
    }

    @Override
    public void restPasswordTransaction() {

        btn_rest.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(email_rest.getText())) {

                    toastStyle.negativeToast(resouces.getStringResource(R.string.write_pass_login_message, activity));

                } else {

                    mAuthentication().sendPasswordResetEmail(email_rest.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            toastStyle.positiveToast(resouces.getStringResource(R.string.sent_pass_login_message, activity));

                            endDialog();


                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            toastStyle.negativeToast(resouces.getStringResource(R.string.account_found_login_message, activity));
                        }
                    });


                }

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
