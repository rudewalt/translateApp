package com.ivan.translateapp.data.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import io.reactivex.Completable;
import io.reactivex.Observable;


public class SettingsRepository implements ISettingsRepository {

    private static final String SHARED_PREF_FILENAME = "translateAppSharedPref";
    private static final String EMPTY_STRING = "";

    private SharedPreferences sharedPref;

    public SettingsRepository(@NonNull Context context) {
        sharedPref = context.getSharedPreferences(SHARED_PREF_FILENAME, Context.MODE_PRIVATE);
    }


    @Override
    public Observable<String> getValue(String key) {
        return Observable.fromCallable(() -> sharedPref.getString(key, EMPTY_STRING));
    }

    @Override
    public Completable setValue(String key, String value) {
        return
                Completable.fromAction(() -> {
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(key, value);
                    editor.commit();
                });
    }
}
