package com.hossam.emergency.display_image_dialog;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.hossam.emergency.R;
import com.hossam.emergency.animations.AnimationsUtils;
import com.hossam.emergency.dialogs.InitDialog;
import com.hossam.emergency.dialogs.ProviderControllerDialog;
import com.hossam.emergency.process.ImageProcess;

public class DisplayImageDialog implements ProviderControllerDialog, DisplayImageDialogProvider {


    ImageProcess imageProcess = ImageProcess.getInstance();
    private final Activity activity;
    private final InitDialog initDialog = new InitDialog();
    private View view;
    private final AnimationsUtils animationsUtils = AnimationsUtils.getInstance();
    private ImageView image;
    private ImageView close;


    public DisplayImageDialog(Activity activity) {
        this.activity = activity;
        initView();
    }


    @Override
    public void initView() {
        view = initDialog.initDialog(R.layout.display_image_dialog_layout, activity);

        image = view.findViewById(R.id.content_display_image_dialog_layout);
        close = view.findViewById(R.id.close_display_image_dialog_layout);

        controllerImageDisplayDailog();

    }


    @Override
    public void controllerImageDisplayDailog() {

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endDialog();
            }
        });
    }

    @Override
    public void onDisplayImage(String url) {

        imageProcess.loadImage(image, url, activity);


    }

    @Override
    public void onDownloadImage() {

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
