package com.ivan.translateapp.data.repository;

import io.reactivex.Observable;

/**
 * Created by Ivan on 15.04.2017.
 */

public interface ISettingsRepository {
    Observable<String> getValue(String key);

    void setValue(String key, String value);
}
