package com.ivan.translateapp.domain.interactor;

import com.ivan.translateapp.domain.Translation;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;


public interface IHistoryInteractor {

    Single<List<Translation>> getHistory();

    Single<List<Translation>> getFavorites();

    Completable delete(Translation translation);

    Completable saveChanges(Translation translation);
}
