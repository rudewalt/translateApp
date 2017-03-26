package com.ivan.translateapp.data.net.yandex;

import com.ivan.translateapp.data.net.TranslateService;
import com.ivan.translateapp.domain.Language;
import com.ivan.translateapp.domain.Translation;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Ivan on 26.03.2017.
 */

public class YandexTranslateService implements TranslateService {



    @Override
    public Observable<List<Language>> getLanguages(String ui) {
        return null;
    }

    @Override
    public Observable<Language> detectLanguage(String text) {
        return null;
    }

    @Override
    public Observable<Translation> translate(String text, String toLanguage, String fromLanguage) {
        return null;
    }
}
