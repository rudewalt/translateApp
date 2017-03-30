package com.ivan.translateapp.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.support.annotation.NonNull;
import android.util.Log;

import com.ivan.translateapp.data.db.entity.TranslationEntity;
import com.ivan.translateapp.data.db.tables.TranslationTable;
import com.ivan.translateapp.domain.Translation;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class DbHelper extends SQLiteOpenHelper {

    private static final String  TAG = "DbHelper";

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "translateApp_db";

    public DbHelper(@NonNull Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TranslationTable.getCreateTableQuery()); //history
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // no impl
    }

    public void put(Translation translation) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TranslationTable.COLUMN_FROM_LANGUAGE, translation.getFromLanguage());
        contentValues.put(TranslationTable.COLUMN_TO_LANGUAGE, translation.getToLanguage());
        contentValues.put(TranslationTable.COLUMN_TEXT, translation.getText());
        contentValues.put(TranslationTable.COLUMN_TRANSLATED, translation.getTranslated());
        contentValues.put(TranslationTable.COLUMN_CREATE_DATE, getCurrentDateTime());

        long id = getWritableDatabase().insert(TranslationTable.TABLE,null,contentValues);
        Log.d(TAG,"Row inserted id" + id);
    }

    public void find(Translation translation){
        //String table, String[] columns, String selection,       String[] selectionArgs, String groupBy, String having,                String orderBy
        //getReadableDatabase().query(false,TranslationTable.TABLE);
    }

    private String getCurrentDateTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        return df.format(c.getTime());
    }
}
