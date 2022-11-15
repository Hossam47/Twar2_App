package com.hossam.emergency.edit_case;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.hossam.emergency.R;
import com.hossam.emergency.ui.cases.CaseModel;
import com.hossam.emergency.interfaces.ActivityHelper;
import com.hossam.emergency.utils.CoolButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditCaseActivity extends AppCompatActivity implements MVPEditCase.View, ActivityHelper {

    @BindView(R.id.title_case)
    AutoCompleteTextView titleCase;
    @BindView(R.id.desc_case)
    AutoCompleteTextView descCase;
    @BindView(R.id.show_profile_case)
    CheckBox showProfileCase;
    @BindView(R.id.show_mobile_case)
    CheckBox showMobileCase;
    @BindView(R.id.lunch_case)
    CoolButton lunchCase;
    @BindView(R.id.toolbar_case)
    Toolbar toolbarCase;
    boolean mobile, profile;
    private CaseModel caseModel;
    private PresenterEditCase presenterEditCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_case);
        ButterKnife.bind(this);


        initActivity();
    }

    @Override
    public void bindActivity() {
        setSupportActionBar(toolbarCase);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(caseModel.getTitle());
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        presenterEditCase.setOldData();
        initUiNewCaseData();
    }

    @Override
    public void initActivity() {

    }

    @Override
    public void initUiOldCasedata(String title, String desc, boolean show_mobile, boolean show_image) {

        descCase.setText(caseModel.getDescription());
        titleCase.setText(caseModel.getTitle());
        showMobileCase.setChecked(show_mobile);
        showProfileCase.setChecked(show_image);
    }

    @Override
    public void initUiNewCaseData() {

        showMobileCase.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                mobile = isChecked;
            }
        });

        showProfileCase.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                profile = isChecked;
            }
        });
    }

    public void saveEditCaseData(View view) {

        presenterEditCase.validNewData(titleCase.getText().toString(), descCase.getText().toString(),
                showMobileCase.isChecked(), showProfileCase.isChecked());


    }

    @Override
    protected void onStart() {
        super.onStart();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {

            caseModel = (CaseModel) bundle.getSerializable("case_model");
            presenterEditCase = new PresenterEditCase(this, caseModel, this);

            bindActivity();
        }
    }
}
