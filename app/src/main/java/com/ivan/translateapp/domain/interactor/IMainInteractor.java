package com.ivan.translateapp.domain.interactor;

import com.ivan.translateapp.domain.Language;
import com.ivan.translateapp.domain.Translation;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;


/**
 * Интерактор для хранения бизнес-логики, связанной с переводами
 */

public interface IMainInteractor {

    Single<List<Language>> getLanguages();

    Single<Translation> translateText(String text, String fromLanguage, String toLanguage);

    Single<Boolean> isFavorite(Translation translation);

    Completable saveTranslation(Translation translation);

    Completable saveTranslationDirection(String fromLanguage, String toLanguage);

    Single<List<String>> restoreTranslationDirection();
}
