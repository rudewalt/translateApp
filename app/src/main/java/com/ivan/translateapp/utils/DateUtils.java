package com.ivan.translateapp.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    private static final String dateFormat = "yyyy-MM-dd HH:mm:ss";
    private static final String TAG = DateUtils.class.toString();

    public static Date parse(String stringDate) {
        if (stringDate.equals(""))
            return null;

        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        try {
            date = format.parse(stringDate);

        } catch (ParseException e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }

        return date;
    }

    public static String getCurrentDateTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);
        return df.format(c.getTime());
    }

}
