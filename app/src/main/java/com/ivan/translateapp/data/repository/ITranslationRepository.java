package com.ivan.translateapp.data.repository;

import com.ivan.translateapp.domain.Language;
import com.ivan.translateapp.domain.Translation;

import java.util.List;

import io.reactivex.Observable;


/**
 * Created by Ivan on 28.03.2017.
 */

public interface ITranslationRepository {

    Observable<Translation> getTranslation(String text, String toLanguage, String fromLanguage);

    Observable<List<Language>> getLanguages(String userInterface);

}
