package com.ivan.translateapp.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.support.annotation.NonNull;
import android.util.Log;

import com.ivan.translateapp.data.db.entity.TranslationEntity;
import com.ivan.translateapp.data.db.tables.TranslationTable;
import com.ivan.translateapp.domain.Translation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class DbHelper extends SQLiteOpenHelper {

    private static final String  TAG = "DbHelper";

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "translateApp_db";

    public DbHelper(@NonNull Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TranslationTable.getCreateTableQuery());
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

    public List<TranslationEntity> get(){
        List<TranslationEntity> result = new ArrayList<>();

        Cursor cursor = getReadableDatabase().rawQuery(TranslationTable.getAllQuery(), new String[]{});
        if(cursor.moveToFirst()) {
            do {
                TranslationEntity entity = new TranslationEntity(
                        cursor.getString(cursor.getColumnIndex(TranslationTable.COLUMN_TEXT)),
                        cursor.getString(cursor.getColumnIndex(TranslationTable.COLUMN_TRANSLATED)),
                        cursor.getString(cursor.getColumnIndex(TranslationTable.COLUMN_FROM_LANGUAGE)),
                        cursor.getString(cursor.getColumnIndex(TranslationTable.COLUMN_TO_LANGUAGE)),
                        cursor.getString(cursor.getColumnIndex(TranslationTable.COLUMN_CREATE_DATE)),
                        cursor.getString(cursor.getColumnIndex(TranslationTable.COLUMN_ADD_TO_FAVOURITE_DATE)),
                        cursor.getInt(cursor.getColumnIndex(TranslationTable.COLUMN_IS_HIDDEN)),
                        cursor.getInt(cursor.getColumnIndex(TranslationTable.COLUMN_IS_FAVOURITE))
                );

                result.add(entity);

            } while (cursor.moveToNext());
        }
        return result;
    }

    private String getCurrentDateTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return df.format(c.getTime());
    }
}
