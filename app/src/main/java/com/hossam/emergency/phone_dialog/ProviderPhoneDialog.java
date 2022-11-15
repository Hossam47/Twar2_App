package com.hossam.emergency.phone_dialog;

import com.hossam.emergency.ui.cases.CaseModel;
import com.hossam.emergency.ui.sign_up.User;

public interface ProviderPhoneDialog {

    void controllerPhoneDialog(CaseModel caseModel);

    void onCallClick(User user, CaseModel caseModel);

    void initUserView(User user, CaseModel caseModel);

    void onCancelClick(User user, CaseModel caseModel);
}
