package com.ivan.translateapp.domain.interactor;

import com.ivan.translateapp.domain.Translation;

import java.util.List;

import io.reactivex.Observable;


/**
 * Created by Ivan on 27.03.2017.
 */

public interface IHistoryInteractor {

    Observable<List<Translation>> getHistory();

    Observable<List<Translation>> getFavourites();

    void delete(Translation translation);

    void clearHistory();

    void deleteFromFavourites(Translation translation);
}
