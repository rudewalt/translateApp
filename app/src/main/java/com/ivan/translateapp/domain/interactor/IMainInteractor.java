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

    Observable<List<Language>> getLanguages();

    Observable<Translation> translateText(String text, String fromLanguage, String toLanguage);

    void saveTranslation(Translation translation);

    void saveTranslationDirection(String fromLanguage, String toLanguage);

    Observable<List<String>> restoreTranslationDirection();
}
