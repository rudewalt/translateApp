package com.ivan.translateapp.data.cache;

import io.reactivex.Observable;

/**
 * Created by Ivan on 26.03.2017.
 */

public interface KeyCache<T> {
    Observable<T> get();

    void put(T object);

    void clearCache();
}
