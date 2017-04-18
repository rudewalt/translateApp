package com.ivan.translateapp.domain.interactor;

import com.ivan.translateapp.domain.Language;
import com.ivan.translateapp.domain.Translation;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;


/**
 * Created by Ivan on 27.03.2017.
 */

public interface IMainInteractor {

    Observable<List<Language>> getLanguages();

    Observable<Translation> translateText(String text, String fromLanguage, String toLanguage);

    Observable<Boolean> isFavorite(Translation translation);

    Completable saveTranslation(Translation translation);

    Completable saveTranslationDirection(String fromLanguage, String toLanguage);

    Observable<List<String>> restoreTranslationDirection();
}
