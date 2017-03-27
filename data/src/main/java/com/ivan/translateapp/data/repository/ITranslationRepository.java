package com.ivan.translateapp.data.repository;

import com.ivan.translateapp.domain.Translation;

import io.reactivex.Observable;

/**
 * Created by Ivan on 26.03.2017.
 */

public interface ITranslationRepository {

    Observable<Translation> getHistory();

    Observable<Translation> getFavourites();

    void add(Translation translation);

    void addToFavourites(Translation translation);
}
