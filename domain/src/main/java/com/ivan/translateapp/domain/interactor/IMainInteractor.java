package com.ivan.translateapp.domain.interactor;

import com.ivan.translateapp.domain.Language;
import com.ivan.translateapp.domain.Translation;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;


/**
 * Created by Ivan on 27.03.2017.
 */

public interface IMainInteractor {

    Single<List<Language>> getLanguages();

    Single<Translation> translateText(String text);

    void addToFavourites(Translation translation);
}
