package com.ivan.translateapp.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ivan.translateapp.domain.Language;

import java.util.List;

/**
 * Created by Ivan on 31.03.2017.
 */

public class LanguageAdapter extends BaseAdapter {

    private List<Language> languages;

    public LanguageAdapter(List<Language> languages1){

        this.languages = languages1;
    }

    @Override
    public int getCount() {
        return languages.size();
    }

    @Override
    public Object getItem(int position) {
        return languages.get(position).getTitle();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
