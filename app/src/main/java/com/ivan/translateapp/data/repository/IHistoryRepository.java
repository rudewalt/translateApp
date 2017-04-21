package com.ivan.translateapp.data.repository;

import com.ivan.translateapp.domain.Translation;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 *  Репозиторий для сохранения переводов в историю
 */

public interface IHistoryRepository {

    Single<List<Translation>> getHistory();

    Single<List<Translation>> getFavorites();

    Completable add(Translation translation);

    Completable update(Translation translation);

    Completable deleteHistory();

    Completable deleteFavorites();

    Completable delete(Translation translation);

    Single<Boolean> isFavourite(String text, String fromLanguage, String toLanguage);
}
