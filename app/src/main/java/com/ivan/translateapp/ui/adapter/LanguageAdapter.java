package com.ivan.translateapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;

import com.ivan.translateapp.domain.Language;

import java.util.List;


public class LanguageAdapter extends ArrayAdapter<Language> {

    private LayoutInflater inflater;
    private int layout;
    private List<Language> languages;


    public LanguageAdapter(Context context, int resource, List<Language> languages) {
        super(context, resource, languages);
        this.languages = languages;
        layout = resource;
        inflater = LayoutInflater.from(context);
    }

    public int getPosition(String language) {
        int notFound = -1;

        for (int index = 0; index < languages.size(); index++) {
            if (languages.get(index).getLanguage().equals(language))
                return index;
        }

        return notFound;
    }
}
