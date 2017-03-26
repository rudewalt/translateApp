package com.ivan.translateapp.data.cache;

import com.ivan.translateapp.data.dto.SupportedLanguagesDTO;

import io.reactivex.Observable;

/**
 * Created by Ivan on 26.03.2017.
 */

public interface NetworkCache {
    Observable<SupportedLanguagesDTO> get();

    void put(SupportedLanguagesDTO supportedLanguages);

    void clearCache();
}
