package com.hossam.emergency.dialogs;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by hossam on 2/12/18.
 */

public class InitDialog implements ProviderDialog {

    private androidx.appcompat.app.AlertDialog dialog;

    @Override
    public View initDialog(int layout, Activity activity) {

        LayoutInflater inflater = activity.getLayoutInflater();

        View view = inflater.inflate(layout, null);

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(activity);

        builder.setCancelable(true);

        builder.setView(view);

        dialog = builder.create();

        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return view;
    }

    @Override
    public void showDialog() {
        dialog.show();

    }

    @Override
    public void cancelDialog() {

        dialog.dismiss();
        dialog.cancel();

    }

    @Override
    public void hideDialog(boolean hide) {

        dialog.setCancelable(hide);

    }
}
