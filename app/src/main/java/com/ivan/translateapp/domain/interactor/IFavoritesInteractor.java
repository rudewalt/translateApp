package com.ivan.translateapp.domain.interactor;

import com.ivan.translateapp.domain.Translation;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Ivan on 07.04.2017.
 */

public interface IFavoritesInteractor {
    Observable<List<Translation>> getFavorites();
    void delete(Translation translation);
    void clear();
    void save(Translation translation);
}
