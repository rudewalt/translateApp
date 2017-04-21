package com.ivan.translateapp.data.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import java.util.HashSet;
import java.util.Set;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;


/**
 * Репозиторий, для сохранения настроек, начальных значений.
 * Решил использовать для хранения SharedPreference по следующим причинам:
 * 1) малый объем сохраняемых данных
 * 2) не нужен поиск
 */
public class SettingsRepository implements ISettingsRepository {

    private static final String SHARED_PREF_FILENAME = "translateAppSharedPref";
    private static final String EMPTY_STRING = "";

    private SharedPreferences sharedPref;

    public SettingsRepository(@NonNull Context context) {
        sharedPref = context.getSharedPreferences(SHARED_PREF_FILENAME, Context.MODE_PRIVATE);
    }

    @Override
    public Single<String> getValue(String key) {
        return Single.fromCallable(() -> sharedPref.getString(key, EMPTY_STRING));
    }

    @Override
    public Completable putValue(String key, String value) {
        return
                Completable.fromAction(() -> {
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(key, value);
                    editor.commit();
                });
    }

    @Override
    public Completable putStringSet(String key, Set<String> stringSet){
        return
                Completable.fromAction(() -> {
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putStringSet(key, stringSet);
                    editor.commit();
                });
    }

    public Single<Set<String>> getStringSet(String key){
        return Single.fromCallable(() -> sharedPref.getStringSet(key, new HashSet<>()));
    }
}
