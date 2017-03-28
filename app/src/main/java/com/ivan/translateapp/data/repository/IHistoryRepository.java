package com.ivan.translateapp.data.repository;

import com.ivan.translateapp.domain.Translation;

import io.reactivex.Observable;

/**
 * Created by Ivan on 26.03.2017.
 */

public interface IHistoryRepository {

    Observable<Translation> getHistory();

    Observable<Translation> getFavourites();

    void add(Translation translation);

    void addToFavourites(Translation translation);

    void clear();

    void delete(Translation translation);

    void deleteFromFavourites(Translation translation);
}
