package com.hossam.emergency.info_dialog;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.hossam.emergency.R;
import com.hossam.emergency.animations.AnimationsUtils;
import com.hossam.emergency.dialogs.InitDialog;
import com.hossam.emergency.dialogs.ProviderControllerDialog;
import com.hossam.emergency.utils.StringResouces;

public class InformationDialog implements ProviderControllerDialog {

    private final Activity activity;
    private final InitDialog initDialog = new InitDialog();
    private View view;
    private final AnimationsUtils animationsUtils = AnimationsUtils.getInstance();
    private final StringResouces stringResouces = StringResouces.getInstance();
    private final SharedPreferences sharedPref;
    private final String SHARED_PREF = "Dialog_FILE";
    private Button accept, cancel;
    private CheckBox checkBox;

    public InformationDialog(Activity activity) {
        this.activity = activity;
        sharedPref = activity.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);

        initView();
    }

    @Override
    public void initView() {

        view = initDialog.initDialog(R.layout.information_location_dialog, activity);

        accept = view.findViewById(R.id.accept_btn_location_dialog);
        cancel = view.findViewById(R.id.cancel_btn_location_dialog);
        checkBox = view.findViewById(R.id.check_show_message);

    }

    @Override
    public void startDialog() {
        animationsUtils.animationReaction(view, activity);
        initDialog.showDialog();

        checkBox.setChecked(getCheckValue());
    }

    @Override
    public void endDialog() {
        initDialog.cancelDialog();

    }

    public void saveCheck(boolean check) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("CHECK_VALUE", check);
        editor.commit();

    }

    public boolean getCheckValue() {
        return sharedPref.getBoolean("CHECK_VALUE", false);
    }

    public void initCheckBox() {

        if (checkBox.isChecked() || !checkBox.isChecked()) {

            saveCheck(checkBox.isChecked());
        }
    }


    public Button getAcceptButton() {
        return accept;
    }

    public Button getCancelButton() {
        return cancel;
    }


}
