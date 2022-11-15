package com.hossam.emergency.time;

import android.app.Activity;
import android.content.Context;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.ocpsoft.prettytime.PrettyTime;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Locale;

/**
 * Created by hossam on 11/9/17.
 */

public class GetTime {

    private static final GetTime timeInstance = new GetTime();

    private GetTime() {
    }

    public static GetTime getInstance() {
        return timeInstance;
    }


    public static long getUTCTimetamp() {

        DateTime dateTime = new DateTime(DateTimeZone.UTC);

        return dateTime.getMillis();
    }

    public static long getTimeStamp() {

        return new Timestamp(System.currentTimeMillis()).getTime();
    }

    public PrettyTime getStyleTime(Context context) {

        PrettyTime prettyTime = new PrettyTime(new DateTime(DateTimeZone.UTC).toDate());

        if (checkLanguage(context).equals("ar")) {
            prettyTime.setLocale(new Locale("ar"));
        } else {
            prettyTime.setLocale(new Locale("us"));

        }
        return prettyTime;
    }

    private String checkLanguage(Context activity) {
        String config = activity.getResources().getConfiguration().locale.getDisplayLanguage();
        if (config.equals("العربية")) {
            return "ar";
        } else {
            return "en";
        }
    }

    public Date convertTime(long time) {
        return new Date(time);

    }

}
