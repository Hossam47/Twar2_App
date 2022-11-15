package com.hossam.emergency.confirmation_dialog;

import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hossam.emergency.ui.cases.CaseModel;

public interface ProviderConfirmationDialog {


    void changeTitle(TextView title);

    void onClickDelete(RelativeLayout relativeLayout, final CaseModel model);

    void onClickSaved(RelativeLayout relativeLayout, final CaseModel model);

    void onClickCancel();
}
