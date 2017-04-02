package com.ivan.translateapp.utils;

import android.support.annotation.NonNull;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Ivan on 02.04.2017.
 */

public class DateUtils {
    private static final String TAG = DateUtils.class.toString();

    public static final String dateFormat = "yyyy-MM-dd HH:mm:ss";

    public static Date parse(@NonNull String stringDate){
        if(stringDate.equals(""))
            return null;

        Date date;
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        try {
            date = format.parse(stringDate);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e(TAG,e.getMessage());
        } finally {
            return null;
        }
    }

    //TODO to utils
    public static String getCurrentDateTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);
        return df.format(c.getTime());
    }

}
