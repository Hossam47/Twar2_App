package com.hossam.emergency.utils;

import android.widget.EditText;

public class GetTextFromComponent {
    private static final GetTextFromComponent ourInstance = new GetTextFromComponent();

    private GetTextFromComponent() {
    }

    public static GetTextFromComponent getInstance() {
        return ourInstance;
    }

    public String getTextFromComponent(EditText editText) {

        return editText.getText().toString();
    }
}
