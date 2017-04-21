package com.ivan.translateapp.data.net;

import com.ivan.translateapp.domain.Language;
import com.ivan.translateapp.domain.Translation;

import java.util.List;

import io.reactivex.Single;

/**
 * Интерфейс над Api Яндекс-переводчика.
 */

public interface ITranslateService {

    Single<List<Language>> getLanguages(String ui);

    Single<Language> detectLanguage(String text);

    Single<Translation> translate(String text, String fromLanguage, String toLanguage);
}
