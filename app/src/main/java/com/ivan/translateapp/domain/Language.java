package com.ivan.translateapp.domain;

/**
 * Created by Ivan on 26.03.2017.
 */
//TODO переименовать и перенести в модель
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

    public String getLanguage() {
        return language;
    }

    @Override
    public String toString(){
        return title;
    }
}
