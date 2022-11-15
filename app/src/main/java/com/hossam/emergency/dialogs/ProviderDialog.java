package com.hossam.emergency.dialogs;

import android.app.Activity;
import android.view.View;

/**
 * Created by hossam on 2/12/18.
 */

public interface ProviderDialog {

    View initDialog(int layout, Activity activity);

    void showDialog();

    void cancelDialog();

    void hideDialog(boolean hide);
}
