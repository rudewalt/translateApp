package com.ivan.translateapp.data.db.tables;

import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

/**
 * Use a single table for translation and favourites
 * Translation table (id,create_date,text,translated, from_language, to_language,is_favourite,add_to_favourite_date,is_hidden)
 */

public class TranslationTable {

    @NonNull
    public static final String TABLE = "Translations";

    @NonNull
    public static final String COLUMN_ID = "id";

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
    public static final String COLUMN_IS_FAVOURITE = "is_favourite";
    @NonNull
    public static final String COLUMN_ADD_TO_FAVOURITE_DATE = "add_to_favourite_date";
    @NonNull
    public static final String COLUMN_IS_HIDDEN = "is_hidden";


    public static String getCreateTableQuery(){
        return "CREATE TABLE "+ TABLE +"("
                + COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_CREATE_DATE + " TEXT NOT NULL, "
                + COLUMN_TEXT + " TEXT NOT NULL, "
                + COLUMN_TRANSLATED + " TEXT NOT NULL, "
                + COLUMN_FROM_LANGUAGE + " TEXT NOT NULL, "
                + COLUMN_TO_LANGUAGE  + " TEXT NOT NULL, "
                + COLUMN_IS_FAVOURITE + " INTEGER NULL, "
                + COLUMN_ADD_TO_FAVOURITE_DATE + " TEXT NULL, "
                + COLUMN_IS_HIDDEN + " INTEGER)";
    }
}
