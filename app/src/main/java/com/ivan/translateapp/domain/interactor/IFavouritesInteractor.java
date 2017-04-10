package com.ivan.translateapp.domain.interactor;

import com.ivan.translateapp.domain.Translation;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Ivan on 07.04.2017.
 */

public interface IFavouritesInteractor {
    Observable<List<Translation>> getFavourites();
    void delete(Translation translation);
    void clear();
    void save(Translation translation);
}
