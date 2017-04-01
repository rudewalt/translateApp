package com.ivan.translateapp.data.db.entity;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ivan on 26.03.2017.
 */

public class TranslationEntity {

    private String text;
    private String translated;
    private String fromLanguage;
    private String toLanguage;
    private String createDate;
    private String addToFavouriteDate;
    private int isHidden;
    private int isFavourite;

    public TranslationEntity(String text, String translated, String fromLanguage, String toLanguage, String createDate, String addToFavouriteDate, Integer isHidden, Integer isFavourite) {
        this.text = text;
        this.translated = translated;
        this.fromLanguage = fromLanguage;
        this.toLanguage = toLanguage;
        this.createDate = createDate;
        this.addToFavouriteDate = addToFavouriteDate;
        this.isHidden = isHidden;
        this.isFavourite = isFavourite;
    }

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
            date = format.parse(addToFavouriteDate);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            //Log.e()
        } finally {
            return null;
        }
    }

    public Boolean isFavourite() {
        return
                isFavourite == 1;
    }

    public Boolean isHidden() {
        return
                isHidden == 1;
    }

    public TranslationEntity() {
    }
}
