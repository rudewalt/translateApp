package com.ivan.translateapp.domain.interactor;

import com.ivan.translateapp.domain.Translation;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;


/**
 * Created by Ivan on 27.03.2017.
 */

public interface IHistoryInteractor {

    Observable<List<Translation>> getHistory();

    Observable<List<Translation>> getFavorites();

    Completable delete(Translation translation);

    Completable saveChanges(Translation translation);
}
