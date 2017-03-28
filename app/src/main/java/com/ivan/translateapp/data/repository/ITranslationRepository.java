package com.ivan.translateapp.data.repository;

import com.ivan.translateapp.domain.Language;
import com.ivan.translateapp.domain.Translation;

import io.reactivex.Observable;


/**
 * Created by Ivan on 28.03.2017.
 */

public interface ITranslationRepository {

    Observable<Translation> getTranslation(String text, String toLanguage, String fromLanguage);

    Observable<Language> getLanguages(String userInterface);

}
