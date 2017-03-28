package com.ivan.translateapp.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.ivan.translateapp.data.db.tables.TranslationTable;


public class DbOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "translateApp_db";

    public DbOpenHelper(@NonNull Context context){
        super(context,DB_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TranslationTable.getCreateTableQuery()); //history
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // no impl
    }
}
