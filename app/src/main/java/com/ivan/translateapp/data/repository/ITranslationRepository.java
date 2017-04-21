package com.ivan.translateapp.data.repository;

import com.ivan.translateapp.domain.Language;
import com.ivan.translateapp.domain.Translation;

import java.util.List;

import io.reactivex.Single;


/**
 * Created by Ivan on 28.03.2017.
 */

public interface ITranslationRepository {

    Single<Translation> getTranslation(String text, String fromLanguage, String toLanguage);

    Single<List<Language>> getLanguages(String userInterface);

}
