package com.ivan.translateapp.data.repository;

import com.ivan.translateapp.domain.Translation;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by Ivan on 26.03.2017.
 */

public interface IHistoryRepository {

    Observable<List<Translation>> getHistory();

    Observable<List<Translation>> getFavorites();

    void add(Translation translation);

    void update(Translation translation);

    void deleteHistory();

    void deleteFavorites();

    void delete(Translation translation);

    Observable<Boolean> isFavourite(String text, String fromLanguage, String toLanguage);

    void saveSetting(String fromLanguage, String toLanguage);

    Observable<String> getSetting(String key);
}
