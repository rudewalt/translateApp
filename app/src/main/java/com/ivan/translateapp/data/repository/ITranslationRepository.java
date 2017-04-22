package com.ivan.translateapp.data.repository;

import com.ivan.translateapp.domain.Language;
import com.ivan.translateapp.domain.Translation;

import java.util.List;

import io.reactivex.Single;


/**
 * Репозиторий для вызова api для получения языков и переводов
 */

public interface ITranslationRepository {

    Single<Translation> getTranslation(String text, String fromLanguage, String toLanguage);

    Single<List<Language>> getLanguages(String userInterface);

}
