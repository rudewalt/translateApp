package com.ivan.translateapp.domain;

/**
 * Created by Ivan on 26.03.2017.
 */

public class Language {
    public String title;
    public String language;

    public Language(String title, String language){
        this.title = title;
        this.language = language;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString(){
        return title;
    }
}
