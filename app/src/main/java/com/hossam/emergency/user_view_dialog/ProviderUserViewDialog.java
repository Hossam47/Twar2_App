package com.hossam.emergency.user_view_dialog;

import com.hossam.emergency.ui.sign_up.User;

public interface ProviderUserViewDialog {

    void initMainDialogView(User user);

    void onClickMessageButton(User user);
}
