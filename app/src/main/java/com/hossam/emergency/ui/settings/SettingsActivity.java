package com.hossam.emergency.ui.settings;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.hossam.emergency.R;
import com.hossam.emergency.interfaces.ActivityHelper;
import com.hossam.emergency.utils.CoolTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends AppCompatActivity implements ActivityHelper {

    @BindView(R.id.toolbar_settings)
    Toolbar toolbarSettings;
    @BindView(R.id.language_text)
    CoolTextView languageText;

    @BindView(R.id.arabic_radio)
    RadioButton arabicRadio;
    @BindView(R.id.english_radio)
    RadioButton englishRadio;
    @BindView(R.id.language_radio)
    RadioGroup languageRadio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        bindActivity();
        initActivity();
    }

    @Override
    public void bindActivity() {
        setSupportActionBar(toolbarSettings);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void initActivity() {


    }

    public void onSaveSettingsClick(View view) {

        int radio_id = languageRadio.getCheckedRadioButtonId();


    }

    public void onCheckedRadioButton(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {

            case R.id.english_radio:
                if (checked)
                    Toast.makeText(this, "English", Toast.LENGTH_SHORT).show();
                break;
            case R.id.arabic_radio:
                if (checked)
                    Toast.makeText(this, "Arabic", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
