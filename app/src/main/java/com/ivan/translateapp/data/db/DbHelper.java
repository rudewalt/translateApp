package com.ivan.translateapp.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.ivan.translateapp.data.db.entity.KeyValueEntity;
import com.ivan.translateapp.data.db.entity.TranslationEntity;
import com.ivan.translateapp.data.db.tables.KeyValueTable;
import com.ivan.translateapp.data.db.tables.TranslationTable;
import com.ivan.translateapp.domain.Translation;
import com.ivan.translateapp.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;


public class DbHelper extends SQLiteOpenHelper {

    private static final String TAG = DbHelper.class.toString();

    private static final String EMPTY_STRING = "";
    private static final int DB_VERSION = 5;
    private static final String DB_NAME = "translateApp_db";

    public DbHelper(@NonNull Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TranslationTable.getCreateTableQuery());
        db.execSQL(KeyValueTable.getCreateTableQuery());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TranslationTable.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + KeyValueTable.TABLE);
        onCreate(db);
    }

    /*
          try insert, if fail then try to update
     */
    public void saveTranslation(Translation translation) {
        ContentValues contentValues = getTranslationContentValues(translation);
        SQLiteDatabase dataBase = getWritableDatabase();

        long id = dataBase.insertWithOnConflict(TranslationTable.TABLE, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
        if (id == -1) {
            ContentValues contentValuesForUpdate = getContentValuesForUpdate(translation);
            String whereCondition = TranslationTable.COLUMN_TEXT + "=? AND "
                    + TranslationTable.COLUMN_FROM_LANGUAGE + "=? AND "
                    + TranslationTable.COLUMN_TO_LANGUAGE + "=?";

            int rowsAffected = dataBase.update(TranslationTable.TABLE, contentValuesForUpdate, whereCondition,
                    new String[]{translation.getText(), translation.getFromLanguage(), translation.getToLanguage()});

            Log.d(TAG, rowsAffected + " rows updated");
        }
    }

    public void delete(Translation translation) {
        SQLiteDatabase dataBase = getWritableDatabase();

        if (translation.isFavourite()) {
            ContentValues contentValues = getTranslationContentValues(translation);
            long id = dataBase.update(TranslationTable.TABLE, contentValues, "text=?", new String[]{translation.getText()});
        } else {
            dataBase.delete(TranslationTable.TABLE, "text=?", new String[]{translation.getText()});
        }
    }

    public List<TranslationEntity> getAllHistory() {
        return getTranslations(TranslationTable.getAllHistory(), null);
    }

    public List<TranslationEntity> getAllFavorites() {
        return getTranslations(TranslationTable.getAllFavorites(), null);
    }

    public TranslationEntity getTranslations(String text, String fromLanguage, String toLanguage) {
        final int first = 0;
        List<TranslationEntity> queryResult =
                getTranslations(TranslationTable.getByKey(), new String[]{text, fromLanguage, toLanguage});

        return
                queryResult.size() > 0
                        ? queryResult.get(first)
                        : null;
    }

    public void deleteHistory() {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(TranslationTable.clearHistory());
    }

    public void deleteFavorites() {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(TranslationTable.clearFavorites());
    }

    public void saveKeyValue(String key, String value) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KeyValueTable.COLUMN_KEY, key);
        contentValues.put(KeyValueTable.COLUMN_VALUE, value);

        SQLiteDatabase dataBase = getWritableDatabase();
        long id = dataBase.insertWithOnConflict(KeyValueTable.TABLE, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
        if (id == -1) {
            String whereCondition = KeyValueTable.COLUMN_KEY + "=?";
            int rowsAffected = dataBase.update(KeyValueTable.TABLE, contentValues, whereCondition, new String[]{key});
        }
    }

    public KeyValueEntity getKeyValue(String key) {
        SQLiteDatabase dataBase = getReadableDatabase();
        Cursor cursor = dataBase.rawQuery(KeyValueTable.getKeyValuePairQuery(), new String[]{key});
        if (cursor.moveToFirst()) {
            KeyValueEntity entity = getKeyValueEntity(cursor);
            return entity;
        }

        return new KeyValueEntity(key, EMPTY_STRING);
    }

    private List<TranslationEntity> getTranslations(String query, String[] params) {
        List<TranslationEntity> result = new ArrayList<>();
        SQLiteDatabase dataBase = getReadableDatabase();
        Cursor cursor = dataBase.rawQuery(query, params);
        if (cursor.moveToFirst()) {
            do {
                TranslationEntity entity = getTranslationEntity(cursor);
                result.add(entity);

            } while (cursor.moveToNext());
        }
        return result;
    }

    private TranslationEntity getTranslationEntity(Cursor cursor) {
        TranslationEntity entity = new TranslationEntity(
                cursor.getString(cursor.getColumnIndex(TranslationTable.COLUMN_TEXT)),
                cursor.getString(cursor.getColumnIndex(TranslationTable.COLUMN_TRANSLATED)),
                cursor.getString(cursor.getColumnIndex(TranslationTable.COLUMN_FROM_LANGUAGE)),
                cursor.getString(cursor.getColumnIndex(TranslationTable.COLUMN_TO_LANGUAGE)),
                cursor.getString(cursor.getColumnIndex(TranslationTable.COLUMN_CREATE_DATE)),
                cursor.getString(cursor.getColumnIndex(TranslationTable.COLUMN_ADD_TO_FAVORITE_DATE)),
                cursor.getInt(cursor.getColumnIndex(TranslationTable.COLUMN_IS_HISTORY)),
                cursor.getInt(cursor.getColumnIndex(TranslationTable.COLUMN_IS_FAVORITE))
        );

        return entity;
    }

    private KeyValueEntity getKeyValueEntity(Cursor cursor){
        KeyValueEntity keyValueEntity = new KeyValueEntity(
                cursor.getString(cursor.getColumnIndex(KeyValueTable.COLUMN_KEY)),
                cursor.getString(cursor.getColumnIndex(KeyValueTable.COLUMN_VALUE)));

        return keyValueEntity;
    }

    private ContentValues getTranslationContentValues(Translation translation) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(TranslationTable.COLUMN_FROM_LANGUAGE, translation.getFromLanguage());
        contentValues.put(TranslationTable.COLUMN_TO_LANGUAGE, translation.getToLanguage());
        contentValues.put(TranslationTable.COLUMN_TEXT, translation.getText());
        contentValues.put(TranslationTable.COLUMN_TRANSLATED, translation.getTranslated());
        contentValues.put(TranslationTable.COLUMN_CREATE_DATE, DateUtils.getCurrentDateTime());
        contentValues.put(TranslationTable.COLUMN_IS_HISTORY, translation.isHistory() ? 1 : 0);
        contentValues.put(TranslationTable.COLUMN_IS_FAVORITE, translation.isFavourite() ? 1 : 0);
        contentValues.put(TranslationTable.COLUMN_ADD_TO_FAVORITE_DATE, translation.isFavourite() ? DateUtils.getCurrentDateTime() : null);

        return contentValues;
    }

    private ContentValues getContentValuesForUpdate(Translation translation) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TranslationTable.COLUMN_IS_HISTORY, translation.isHistory() ? 1 : 0);
        contentValues.put(TranslationTable.COLUMN_IS_FAVORITE, translation.isFavourite() ? 1 : 0);
        contentValues.put(TranslationTable.COLUMN_ADD_TO_FAVORITE_DATE, translation.isFavourite() ? DateUtils.getCurrentDateTime() : null);
        return contentValues;
    }


}
