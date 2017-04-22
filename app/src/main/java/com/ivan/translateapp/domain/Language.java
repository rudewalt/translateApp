package com.ivan.translateapp.domain;

public class Language {
    public final String title;
    public final String language;

    public Language(String title, String language) {
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
    public String toString() {
        return title;
    }
}
