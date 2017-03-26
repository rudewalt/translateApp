package com.ivan.translateapp.domain;

/**
 * Created by Ivan on 26.03.2017.
 */

public class Translation {

    private String text;
    private String translated;
    private String direction;

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
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }



}
