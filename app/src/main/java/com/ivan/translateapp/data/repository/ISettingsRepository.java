package com.ivan.translateapp.data.repository;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Created by Ivan on 15.04.2017.
 */

public interface ISettingsRepository {
    Observable<String> getValue(String key);

    Completable setValue(String key, String value);
}
