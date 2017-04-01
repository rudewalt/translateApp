package com.ivan.translateapp.data.repository;

import com.ivan.translateapp.data.net.ITranslateService;
import com.ivan.translateapp.domain.Language;
import com.ivan.translateapp.domain.Translation;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Ivan on 30.03.2017.
 */

public class TranslationRepository implements ITranslationRepository {

    private ITranslateService translateService;

    public TranslationRepository(ITranslateService translateService) {
        this.translateService = translateService;
    }

    @Override
    public Observable<Translation> getTranslation(String text, String fromLanguage, String toLanguage) {
        return
                translateService.translate(text,fromLanguage, toLanguage);
    }

    @Override
    public Observable<List<Language>> getLanguages(String userInterface) {
        return
                translateService.getLanguages(userInterface);
    }
}
