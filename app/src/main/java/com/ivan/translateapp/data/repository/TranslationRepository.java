package com.ivan.translateapp.data.repository;

import com.ivan.translateapp.data.net.ITranslateService;
import com.ivan.translateapp.domain.Language;
import com.ivan.translateapp.domain.Translation;

import java.util.List;

import io.reactivex.Single;

/**
 * Реализация репозитория для вызова api для получения языков и переводов
 */

public class TranslationRepository implements ITranslationRepository {

    private final ITranslateService translateService;

    public TranslationRepository(ITranslateService translateService) {
        this.translateService = translateService;
    }

    @Override
    public Single<Translation> getTranslation(String text, String fromLanguage, String toLanguage) {
        return
                translateService.translate(text, fromLanguage, toLanguage);
    }

    @Override
    public Single<List<Language>> getLanguages(String userInterface) {
        return
                translateService.getLanguages(userInterface);
    }
}
