package com.ivan.translateapp.data.db.tables;

import android.support.annotation.NonNull;

/**
 * Single table for translation and favourites
 * Translation table (id,create_date,text,translated, from_language, to_language,is_favourite,add_to_favourite_date,is_history)
 */

public class TranslationTable {

    @NonNull
    public static final String TABLE = "Translations";

    @NonNull
    public static final String COLUMN_CREATE_DATE = "create_date";
    @NonNull
    public static final String COLUMN_TEXT = "text";
    @NonNull
    public static final String COLUMN_TRANSLATED = "translated";
    @NonNull
    public static final String COLUMN_FROM_LANGUAGE = "from_language";
    @NonNull
    public static final String COLUMN_TO_LANGUAGE = "to_language";
    @NonNull
    public static final String COLUMN_IS_FAVORITE = "is_favorite";
    @NonNull
    public static final String COLUMN_ADD_TO_FAVORITE_DATE = "add_to_favourite_date";
    @NonNull
    public static final String COLUMN_IS_HISTORY = "is_history";


    @NonNull
    public static String getCreateTableQuery() {
        return "CREATE TABLE " + TABLE + "("
                + COLUMN_TEXT + " TEXT NOT NULL, "
                + COLUMN_CREATE_DATE + " TEXT NOT NULL, "
                + COLUMN_TRANSLATED + " TEXT NOT NULL, "
                + COLUMN_FROM_LANGUAGE + " TEXT NOT NULL, "
                + COLUMN_TO_LANGUAGE + " TEXT NOT NULL, "
                + COLUMN_IS_FAVORITE + " INTEGER NULL, "
                + COLUMN_ADD_TO_FAVORITE_DATE + " TEXT NULL, "
                + COLUMN_IS_HISTORY + " INTEGER, "
                + "PRIMARY KEY (" + COLUMN_TEXT + "," + COLUMN_FROM_LANGUAGE + "," + COLUMN_TO_LANGUAGE + "))";
    }

    @NonNull
    public static String getAllHistory() {
        return "SELECT * FROM " + TABLE + " WHERE " + COLUMN_IS_HISTORY + " = 1";
    }

    public static String getAllFavorites() {
        return "SELECT * FROM " + TABLE + " WHERE " + COLUMN_IS_FAVORITE + " = 1";
    }

    public static String getByKey() {
        return "SELECT * FROM " + TABLE + " WHERE " + COLUMN_TEXT + "=? AND " + COLUMN_FROM_LANGUAGE + "=? AND " + COLUMN_TO_LANGUAGE + "=?";
    }

    public static String clearHistory() {
        return "DELETE FROM " + TABLE + " WHERE " + COLUMN_IS_HISTORY + " = 1 and " + COLUMN_IS_FAVORITE + "=0"
                + " UPDATE " + TABLE + " SET " + COLUMN_IS_HISTORY + "=0;";
    }

    public static String clearFavorites() {
        return "DELETE FROM " + TABLE + " WHERE " + COLUMN_IS_FAVORITE + "=1";
    }
}
