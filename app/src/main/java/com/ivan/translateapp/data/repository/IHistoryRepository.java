package com.ivan.translateapp.data.repository;

import com.ivan.translateapp.domain.Translation;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by Ivan on 26.03.2017.
 */

public interface IHistoryRepository {

    Observable<List<Translation>> getHistory();

    Observable<List<Translation>> getFavorites();

    Completable add(Translation translation);

    Completable update(Translation translation);

    Completable deleteHistory();

    Completable deleteFavorites();

    Completable delete(Translation translation);

    Observable<Boolean> isFavourite(String text, String fromLanguage, String toLanguage);

    Completable saveSetting(String fromLanguage, String toLanguage);

    Observable<String> getSetting(String key);
}
