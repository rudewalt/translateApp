package com.ivan.translateapp.data.db.entity;


import com.ivan.translateapp.utils.DateUtils;

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
    private String addToFavoriteDate;
    private int isHistory;
    private int isFavorite;

    public TranslationEntity(String text, String translated, String fromLanguage, String toLanguage, String createDate, String addToFavoriteDate, Integer isHistory, Integer isFavorite) {
        this.text = text;
        this.translated = translated;
        this.fromLanguage = fromLanguage;
        this.toLanguage = toLanguage;
        this.createDate = createDate;
        this.addToFavoriteDate = addToFavoriteDate;
        this.isHistory = isHistory;
        this.isFavorite = isFavorite;
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

    public Date getAddToFavoriteDate() {
        return DateUtils.parse(addToFavoriteDate);
    }

    public Boolean isFavorite() {
        return
                isFavorite == 1;
    }

    public Boolean isHistory() {
        return
                isHistory == 1;
    }
}
