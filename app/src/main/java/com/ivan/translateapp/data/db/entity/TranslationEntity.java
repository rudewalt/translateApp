package com.ivan.translateapp.data.db.entity;


import java.util.Date;


public class TranslationEntity {
    private final String text;
    private final String translated;
    private final String fromLanguage;
    private final String toLanguage;
    private final Date createDate;
    private final Date addToFavoriteDate;
    private final boolean isHistory;
    private final boolean isFavorite;

    public TranslationEntity(String text, String translated, String fromLanguage,
                             String toLanguage, Date createDate, Date addToFavoriteDate,
                             boolean isHistory, boolean isFavorite) {
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
        return createDate;
    }

    public Date getAddToFavoriteDate() {
        return addToFavoriteDate;
    }

    public Boolean isFavorite() {
        return isFavorite;
    }

    public Boolean isHistory() {
        return isHistory;
    }
}
