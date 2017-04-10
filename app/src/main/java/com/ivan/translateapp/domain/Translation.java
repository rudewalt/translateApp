package com.ivan.translateapp.domain;

/**
 * Created by Ivan on 26.03.2017.
 */

public class Translation {

    private String text;
    private String translated;
    private String fromLanguage;
    private String toLanguage;
    private boolean isFavourite;
    private boolean isHistory;

    public Translation() {
    }

    public Translation(String text, String translated, String fromLanguage, String toLanguage, boolean isHistory, boolean isFavourite) {
        this.text = text;
        this.translated = translated;
        this.fromLanguage = fromLanguage;
        this.toLanguage = toLanguage;
        this.isHistory = isHistory;
        this.isFavourite = isFavourite;
    }

    public String getFromLanguage() {
        return fromLanguage;
    }

    public void setFromLanguage(String fromLanguage) {
        this.fromLanguage = fromLanguage;
    }


    public String getToLanguage() {
        return toLanguage;
    }

    public void setToLanguage(String toLanguage) {
        this.toLanguage = toLanguage;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTranslated() {
        return translated;
    }

    public void setTranslated(String translated) {
        this.translated = translated;
    }

    public String getDirection() {
        return String.format("%1$s-%2$s", fromLanguage, toLanguage);
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public boolean isHistory() {
        return isHistory;
    }

    public void setHistory(boolean history) {
        isHistory = history;
    }

}
