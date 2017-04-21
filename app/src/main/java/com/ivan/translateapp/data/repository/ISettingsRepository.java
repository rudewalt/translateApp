package com.ivan.translateapp.data.repository;

import java.util.Set;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Репозиторий для сохранения настроек и начальных данных приложения
 */

public interface ISettingsRepository {
    Single<String> getValue(String key);

    Completable putValue(String key, String value);

    Completable putStringSet(String key, Set<String> stringSet);

    Single<Set<String>> getStringSet(String key);
}
