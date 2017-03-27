package com.ivan.translateapp.data.net.yandex;

import com.ivan.translateapp.data.net.ITranslateService;
import com.ivan.translateapp.data.net.yandex.mapper.LanguageDTOMapper;
import com.ivan.translateapp.data.net.yandex.mapper.SupportedLanguagesDTOMapper;
import com.ivan.translateapp.data.net.yandex.mapper.TranslateResultDTOMapper;
import com.ivan.translateapp.domain.Language;
import com.ivan.translateapp.domain.Translation;

import java.util.List;

import io.reactivex.Observable;

/**
 * Implementation of ITranslateService
 */

public class YandexTranslateService implements ITranslateService {
    private static String UI = "ru";

    //TODO Inject with dagger 2
    private IYandexTranslateApiInterface apiInterface = YandexTranslateApiModule.getApiInterface();
    private LanguageDTOMapper languageDtoMapper = new LanguageDTOMapper();
    private SupportedLanguagesDTOMapper supportedLanguagesDTOMapper = new SupportedLanguagesDTOMapper();
    private TranslateResultDTOMapper translateResultDTOMapper = new TranslateResultDTOMapper();

    //TODO cache all network calls

    //TODO add error handling, check response code https://medium.com/@sasa_sekulic/quick-and-easy-guide-to-retrofit-2-0-setup-or-migration-with-rxjava-ab7a11bc17df#.odr8e3qq9

    public YandexTranslateService() {

    }

    @Override
    public Observable<List<Language>> getLanguages(String ui) {
        Observable<List<Language>> languages =
                apiInterface.getLanguages(UI).map(supportedLanguagesDTOMapper);

        return languages;
    }

    @Override
    public Observable<Language> detectLanguage(String text) {
        Observable<Language> detectedLanguage =
                apiInterface.detectLanguage(text).map(languageDtoMapper);

        return detectedLanguage;
    }

    @Override
    public Observable<Translation> translate(String text, String toLanguage, String fromLanguage) {
        String direction = fromLanguage == null || fromLanguage.isEmpty()
                ? toLanguage
                : String.format("%1$s-%2$s", fromLanguage, toLanguage);

        int includeDetectedLanguage = 1;

        Observable<Translation> translation = apiInterface
                .translate(text, direction, includeDetectedLanguage)
                .map(translateResultDTOMapper)
                .doOnNext(t -> {
                    t.setText(text);
                    t.setToLanguage(toLanguage);
                    t.setFromLanguage(fromLanguage);
                });

        return translation;
    }
}
