package com.ivan.translateapp.data.db.entity;


import com.ivan.translateapp.utils.DateUtils;

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
    private int isHistory;
    private int isFavourite;

    public TranslationEntity(String text, String translated, String fromLanguage, String toLanguage, String createDate, String addToFavouriteDate, Integer isHistory, Integer isFavourite) {
        this.text = text;
        this.translated = translated;
        this.fromLanguage = fromLanguage;
        this.toLanguage = toLanguage;
        this.createDate = createDate;
        this.addToFavouriteDate = addToFavouriteDate;
        this.isHistory = isHistory;
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
        return DateUtils.parse(createDate);
    }

    public Date getAddToFavouriteDate() {
        return DateUtils.parse(addToFavouriteDate);
    }

    public Boolean isFavourite() {
        return
                isFavourite == 1;
    }

    public Boolean isHistory() {
        return
                isHistory == 1;
    }

    public TranslationEntity() {
    }
}
