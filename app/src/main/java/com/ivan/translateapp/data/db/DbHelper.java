package com.ivan.translateapp.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.ivan.translateapp.data.db.entity.TranslationEntity;
import com.ivan.translateapp.data.db.tables.TranslationsTable;
import com.ivan.translateapp.domain.Translation;
import com.ivan.translateapp.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DbHelper extends SQLiteOpenHelper {

    private static final String TAG = DbHelper.class.toString();

    private static final int DB_VERSION = 6;
    private static final String DB_NAME = "translateApp_db";

    //скрипт создания таблички переводов с составным ключом (текст,язык текста, язык перевода)
    private static final String SQL_CREATE_TRANSLATIONS =
            "CREATE TABLE " + TranslationsTable.TABLE + "("
                    + TranslationsTable.COLUMN_TEXT + " TEXT NOT NULL, "
                    + TranslationsTable.COLUMN_CREATE_DATE + " TEXT NOT NULL, "
                    + TranslationsTable.COLUMN_TRANSLATED + " TEXT NOT NULL, "
                    + TranslationsTable.COLUMN_FROM_LANGUAGE + " TEXT NOT NULL, "
                    + TranslationsTable.COLUMN_TO_LANGUAGE + " TEXT NOT NULL, "
                    + TranslationsTable.COLUMN_IS_FAVORITE + " INTEGER NULL, "
                    + TranslationsTable.COLUMN_ADD_TO_FAVORITE_DATE + " TEXT NULL, "
                    + TranslationsTable.COLUMN_IS_HISTORY + " INTEGER, "
                    + "PRIMARY KEY (" + TranslationsTable.COLUMN_TEXT + "," + TranslationsTable.COLUMN_FROM_LANGUAGE + "," + TranslationsTable.COLUMN_TO_LANGUAGE + "))";

    private static final String SQL_GET_HISTORY =
            "SELECT * FROM " + TranslationsTable.TABLE + " WHERE " + TranslationsTable.COLUMN_IS_HISTORY + " = 1";

    private static final String SQL_GET_FAVORITES =
            "SELECT * FROM " + TranslationsTable.TABLE + " WHERE " + TranslationsTable.COLUMN_IS_FAVORITE + " = 1";

    public static final String SQL_DELETE_HISTORY =
            "DELETE FROM " + TranslationsTable.TABLE + " WHERE " + TranslationsTable.COLUMN_IS_HISTORY + " = 1 and " + TranslationsTable.COLUMN_IS_FAVORITE + "=0"
                    + " UPDATE " + TranslationsTable.TABLE + " SET " + TranslationsTable.COLUMN_IS_HISTORY + "=0;";

    public static final String SQL_DELETE_FAVORITES =
            "DELETE FROM " + TranslationsTable.TABLE + " WHERE " + TranslationsTable.COLUMN_IS_FAVORITE + "=1";


    public DbHelper(@NonNull Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    private static String getTranslationsByKeyQuery() {
        return "SELECT * FROM " + TranslationsTable.TABLE +
                " WHERE " + TranslationsTable.COLUMN_TEXT + "=? AND " + TranslationsTable.COLUMN_FROM_LANGUAGE + "=? AND " + TranslationsTable.COLUMN_TO_LANGUAGE + "=?";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TRANSLATIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TranslationsTable.TABLE);
        onCreate(db);
    }

    public void saveTranslation(Translation translation) {
        ContentValues contentValues = getTranslationContentValues(translation);
        SQLiteDatabase dataBase = getWritableDatabase();

        long id = dataBase.insertWithOnConflict(TranslationsTable.TABLE, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
        if (id == -1) {
            ContentValues contentValuesForUpdate = getContentValuesForUpdate(translation);
            String whereCondition = TranslationsTable.COLUMN_TEXT + "=? AND "
                    + TranslationsTable.COLUMN_FROM_LANGUAGE + "=? AND "
                    + TranslationsTable.COLUMN_TO_LANGUAGE + "=?";

            int rowsAffected = dataBase.update(TranslationsTable.TABLE, contentValuesForUpdate, whereCondition,
                    new String[]{translation.getText(), translation.getFromLanguage(), translation.getToLanguage()});

            Log.d(TAG, rowsAffected + " rows updated");
        }
    }

    public void delete(Translation translation) {
        SQLiteDatabase dataBase = getWritableDatabase();

        if (translation.isFavorite()) {
            ContentValues contentValues = getTranslationContentValues(translation);
            dataBase.update(TranslationsTable.TABLE, contentValues, "text=?", new String[]{translation.getText()});
        } else {
            dataBase.delete(TranslationsTable.TABLE, "text=?", new String[]{translation.getText()});
        }
    }

    public List<TranslationEntity> getAllHistory() {
        return getTranslation(SQL_GET_HISTORY, null);
    }

    public List<TranslationEntity> getAllFavorites() {
        return getTranslation(SQL_GET_FAVORITES, null);
    }

    public TranslationEntity getTranslation(String text, String fromLanguage, String toLanguage) {
        final int first = 0;
        List<TranslationEntity> queryResult =
                getTranslation(getTranslationsByKeyQuery(), new String[]{text, fromLanguage, toLanguage});

        return
                queryResult.size() > 0
                        ? queryResult.get(first)
                        : null;
    }

    public void deleteHistory() {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(SQL_DELETE_HISTORY);
    }

    public void deleteFavorites() {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(SQL_DELETE_FAVORITES);
    }

    private List<TranslationEntity> getTranslation(String query, String[] params) {
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
        String addToFavoriteDateString = cursor.getString(cursor.getColumnIndex(TranslationsTable.COLUMN_ADD_TO_FAVORITE_DATE));
        Date addToFavoriteDate = addToFavoriteDateString != null && addToFavoriteDateString.length() > 0
                ? DateUtils.parse(addToFavoriteDateString)
                : null;

        return
                new TranslationEntity(
                        cursor.getString(cursor.getColumnIndex(TranslationsTable.COLUMN_TEXT)),
                        cursor.getString(cursor.getColumnIndex(TranslationsTable.COLUMN_TRANSLATED)),
                        cursor.getString(cursor.getColumnIndex(TranslationsTable.COLUMN_FROM_LANGUAGE)),
                        cursor.getString(cursor.getColumnIndex(TranslationsTable.COLUMN_TO_LANGUAGE)),
                        DateUtils.parse(cursor.getString(cursor.getColumnIndex(TranslationsTable.COLUMN_CREATE_DATE))),
                        addToFavoriteDate,
                        cursor.getInt(cursor.getColumnIndex(TranslationsTable.COLUMN_IS_HISTORY)) == 1,
                        cursor.getInt(cursor.getColumnIndex(TranslationsTable.COLUMN_IS_FAVORITE)) == 1
                );
    }

    private ContentValues getTranslationContentValues(Translation translation) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(TranslationsTable.COLUMN_FROM_LANGUAGE, translation.getFromLanguage());
        contentValues.put(TranslationsTable.COLUMN_TO_LANGUAGE, translation.getToLanguage());
        contentValues.put(TranslationsTable.COLUMN_TEXT, translation.getText());
        contentValues.put(TranslationsTable.COLUMN_TRANSLATED, translation.getTranslated());
        contentValues.put(TranslationsTable.COLUMN_CREATE_DATE, DateUtils.getCurrentDateTime());
        contentValues.put(TranslationsTable.COLUMN_IS_HISTORY, translation.isHistory() ? 1 : 0);
        contentValues.put(TranslationsTable.COLUMN_IS_FAVORITE, translation.isFavorite() ? 1 : 0);
        contentValues.put(TranslationsTable.COLUMN_ADD_TO_FAVORITE_DATE, translation.isFavorite() ? DateUtils.getCurrentDateTime() : null);

        return contentValues;
    }

    private ContentValues getContentValuesForUpdate(Translation translation) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TranslationsTable.COLUMN_IS_HISTORY, translation.isHistory() ? 1 : 0);
        contentValues.put(TranslationsTable.COLUMN_IS_FAVORITE, translation.isFavorite() ? 1 : 0);
        contentValues.put(TranslationsTable.COLUMN_ADD_TO_FAVORITE_DATE, translation.isFavorite() ? DateUtils.getCurrentDateTime() : null);
        return contentValues;
    }
}
