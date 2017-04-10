package com.ivan.translateapp.data.db.tables;

import android.support.annotation.NonNull;

/**
 * Created by Ivan on 10.04.2017.
 */

public class KeyValueTable {

    @NonNull
    public static final String TABLE = "KeyValueStorage";

    @NonNull
    public static final String COLUMN_KEY = "key";
    @NonNull
    public static final String COLUMN_VALUE = "value";

    @NonNull
    public static String getCreateTableQuery() {
        return "CREATE TABLE " + TABLE + "("
                + COLUMN_KEY + " TEXT NOT NULL PRIMARY KEY, "
                + COLUMN_VALUE + " TEXT NOT NULL)";
    }

    public static String getKeyValuePairQuery(){
        return "SELECT * FROM "+TABLE + " WHERE "+COLUMN_KEY+"=?";
    }
}
