package com.ivan.translateapp.data.entity;


        import android.support.annotation.NonNull;
        import android.support.annotation.Nullable;

        import com.ivan.translateapp.data.db.tables.TranslationTable;
        import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
        import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.Date;

/**
 * Created by Ivan on 26.03.2017.
 */

@StorIOSQLiteType(table = TranslationTable.TABLE)
public class TranslationEntity {

    @Nullable
    @StorIOSQLiteColumn(name = TranslationTable.COLUMN_ID, key = true)
    Long id;

    @NonNull
    @StorIOSQLiteColumn(name = TranslationTable.COLUMN_TEXT)
    String text;

    @NonNull
    @StorIOSQLiteColumn(name = TranslationTable.COLUMN_TRANSLATED)
    String translated;

    @NonNull
    @StorIOSQLiteColumn(name = TranslationTable.COLUMN_FROM_LANGUAGE)
    String fromLanguage;

    @NonNull
    @StorIOSQLiteColumn(name = TranslationTable.COLUMN_TO_LANGUAGE)
    String toLanguage;

    @NonNull
    @StorIOSQLiteColumn(name = TranslationTable.COLUMN_CREATE_DATE)
    String createDate;

    @NonNull
    @StorIOSQLiteColumn(name = TranslationTable.COLUMN_ADD_TO_FAVOURITE_DATE)
    String addToFavourite;

    @Nullable
    @StorIOSQLiteColumn(name = TranslationTable.COLUMN_IS_HIDDEN)
    Integer isHidden;

    @StorIOSQLiteColumn(name = TranslationTable.COLUMN_IS_FAVOURITE)
    Integer isFavourite;

    public String getFromLanguage() {
        return fromLanguage;
    }


    public String getToLanguage() {
        return toLanguage;
    }


    public String getText() {
        return text;
    }

    public String getTranslated() {
        return translated;
    }

    public Date getCreateDate() {
        //TODO extract to date utils
        Date date;
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        try {
            date = format.parse(createDate);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            //Log.e()
        } finally {
            return null;
        }
    }

    public Date getAddToFavouriteDate() {
        //TODO extract to date utils
        Date date;
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        try {
            date = format.parse(addToFavourite);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            //Log.e()
        } finally {
            return null;
        }
    }

    public Boolean getIsFavourite() {
        return
                isFavourite != null && isFavourite == 1;
    }

    public Boolean getIsHidden() {
        return
                isHidden != null && isHidden == 1;
    }

    TranslationEntity() {
    }
}
