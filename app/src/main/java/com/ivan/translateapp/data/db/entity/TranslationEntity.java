package com.ivan.translateapp.data.db.entity;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ivan on 26.03.2017.
 */

public class TranslationEntity {

    Long id;

    String text;
    String translated;
    String fromLanguage;
    String toLanguage;
    String createDate;
    String addToFavourite;
    Integer isHidden;
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
