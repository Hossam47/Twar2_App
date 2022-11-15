package com.hossam.emergency.street_view_dialog;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.hossam.emergency.R;
import com.hossam.emergency.animations.AnimationsUtils;
import com.hossam.emergency.ui.cases.CaseModel;
import com.hossam.emergency.dialogs.InitDialog;
import com.hossam.emergency.dialogs.ProviderControllerDialog;
import com.hossam.emergency.utils.StringResouces;

public class StreetViewDialog implements ProviderControllerDialog, ProviderStreetViewDialog {


    private final Activity activity;
    public InitDialog initDialog = new InitDialog();
    private View view;
    private final AnimationsUtils animationsUtils = AnimationsUtils.getInstance();
    private final StringResouces stringResouces = StringResouces.getInstance();


    //view
    private ImageView close_btn;


    public StreetViewDialog(Activity activity) {
        this.activity = activity;
        initView();
    }

    @Override
    public void initView() {
        view = initDialog.initDialog(R.layout.street_view_dialog_layout, activity);

        close_btn = view.findViewById(R.id.close_street_dialog_layout);
    }


    @Override
    public void controllerStreetDialog(final CaseModel caseModel) {

        android.app.FragmentManager fragmentManager = activity.getFragmentManager();

        StreetViewPanoramaFragment streetViewPanoramaFragment = (StreetViewPanoramaFragment)
                fragmentManager.findFragmentById(R.id.street_view_fragment_dialog_layout);

        streetViewPanoramaFragment.getStreetViewPanoramaAsync(new OnStreetViewPanoramaReadyCallback() {
            @Override
            public void onStreetViewPanoramaReady(final StreetViewPanorama streetViewPanorama) {

                streetViewPanorama.setPosition(new LatLng(caseModel.getLatitude(), caseModel.getLongitude()));
                streetViewPanorama.setStreetNamesEnabled(true);
                streetViewPanorama.setUserNavigationEnabled(true);
                streetViewPanorama.setZoomGesturesEnabled(true);

            }
        });

        onCancelDialog();

    }

    @Override
    public void onCancelDialog() {

        close_btn.setOnClickListener(new View.OnClickListener() {
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
