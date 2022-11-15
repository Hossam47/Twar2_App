package com.hossam.emergency.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

import com.hossam.emergency.R;


@SuppressLint("AppCompatCustomView")
public class CoolButton extends Button {


    String customFont;
    Typeface tf;


    public CoolButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        style(context, attrs);
    }

    public CoolButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        style(context, attrs);
    }


    private void style(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomFontTextView);
        int fontName;

        if (checkLanguage(context).equals("ar")) {

            fontName = R.string.tajawal;

        } else {

            fontName = R.string.quicksand;

        }

        customFont = getResources().getString(fontName);

        if (fontName == 4) {

            tf = Typeface.createFromAsset(context.getAssets(),
                    customFont + ".otf");
        } else {
            tf = Typeface.createFromAsset(context.getAssets(),
                    customFont + ".ttf");
        }

        setTypeface(tf);
        a.recycle();
    }


    private String checkLanguage(Context context) {

        String config = context.getResources().getConfiguration().locale.getDisplayLanguage();

        if (config.equals("العربية")) {

            return "ar";

        } else {

            return "en";
        }

    }

}
