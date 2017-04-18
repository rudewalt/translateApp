package com.ivan.translateapp.utils;

import java.lang.reflect.Field;

/**
 * Created by Ivan on 19.04.2017.
 */

public class ResourceUtils {
    public static int getResId(String resName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
