package com.hossam.emergency.ui.intro_main;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.hossam.emergency.R;
import com.hossam.emergency.interfaces.ActivityHelper;
import com.hossam.emergency.services.Twar2App;
import com.hossam.emergency.ui.sign_up.User;
import com.hossam.emergency.utils.CoolButton;
import com.hossam.emergency.utils.CoolTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.dreierf.materialintroscreen.SlideFragment;

import static com.hossam.emergency.firebase.FirebaseContract.getMainReference;

public class InitializeSlide extends SlideFragment implements ActivityHelper {

    @BindView(R.id.initialize_title)
    CoolTextView initializeTitle;
    @BindView(R.id.progress_bar_initialize)
    ProgressBar progressBarInitialize;
    Unbinder unbinder;
    Twar2App twar2App;
    boolean move = false;
    int progressStatus = 0;
    @BindView(R.id.text_progress)
    CoolTextView textProgress;
    @BindView(R.id.initialize_btn)
    CoolButton initializeBtn;
    private final Handler handler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.initialize_slide_layout, container, false);
        unbinder = ButterKnife.bind(this, view);

        bindActivity();


        return view;
    }


    @Override
    public void bindActivity() {

        twar2App = (Twar2App) getActivity().getApplicationContext();


        if (twar2App.getUserCountry().equals("egypt_cases")) {

            initializeTitle.setText(getResources().getString(R.string.already_init));
            initializeBtn.setVisibility(View.GONE);
            move = true;
            canMoveFurther();

        } else {

            initializeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    initializeBtn.setVisibility(View.GONE);
                    progressBarInitialize.setVisibility(View.VISIBLE);

                    initActivity();
                }
            });
        }

    }

    @Override
    public void initActivity() {


        getMainReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);

                if (user.getCountry() != null) {

                    twar2App.saveCachesCountry(user.getCountry());

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        new Thread(new Runnable() {
            public void run() {

                while (progressStatus < 100) {
                    progressStatus += 1;
                    // Update the progress bar and display the
                    //current value in the text view
                    handler.post(new Runnable() {
                        public void run() {
                            progressBarInitialize.setProgress(progressStatus);
                            textProgress.setText(progressStatus + "/" + progressBarInitialize.getMax());
                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        Thread.sleep(25);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                move = true;
                canMoveFurther();

            }
        }).start();

    }

    @Override
    public int backgroundColor() {
        return R.color.white_s;
    }

    @Override
    public int buttonsColor() {
        return R.color.intro_color2;
    }

    @Override
    public boolean canMoveFurther() {
        return move;
    }

    @Override
    public String cantMoveFurtherErrorMessage() {
        return "Waiting";
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
