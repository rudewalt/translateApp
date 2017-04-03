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

    Observable<List<Translation>> getFavourites();

    void add(Translation translation);

    void update(Translation translation);

    void addToFavourites(Translation translation);

    void clear();

    void delete(Translation translation);

    void deleteFromFavourites(Translation translation);

    Observable<Boolean> isFavourite(String text, String fromLanguage, String toLanguage);
}
