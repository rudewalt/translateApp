package com.ivan.translateapp.domain.interactor;


import com.ivan.translateapp.data.repository.IHistoryRepository;
import com.ivan.translateapp.data.repository.ISettingsRepository;
import com.ivan.translateapp.data.repository.ITranslationRepository;
import com.ivan.translateapp.domain.Language;
import com.ivan.translateapp.domain.Translation;
import com.ivan.translateapp.domain.exception.NoInternetConnectionException;
import com.ivan.translateapp.utils.ConnectivityUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import io.reactivex.Completable;
import io.reactivex.Single;


public class MainInteractor implements IMainInteractor {

    //начальные значения языков, при первом запуске приложения
    private static final String DEFAULT_FROM_LANGUAGE = "en";
    private static final String DEFAULT_TO_LANGUAGE = "ru";

    //значение локали по умолчанию
    private static final String DEFAULT_LANGUAGE_CODE = "en";

    private static final String EMPTY_STRING = "";
    private static final String LANGUAGE_CODE_SEPARATOR = ";";

    //ключи для settingsRepository
    private static final String FROM_LANGUAGE_KEY = "fromLanguage";
    private static final String TO_LANGUAGE_KEY = "toLanguage";
    private static final String LANGUAGES_SET_KEY = "language_codes";

    private final ITranslationRepository iTranslationRepository;
    private final IHistoryRepository iHistoryRepository;
    private final ISettingsRepository iSettingsRepository;
    private final Locale locale;
    private final ConnectivityUtils connectivityUtils;
    private boolean isOffline;

    public MainInteractor(
            ITranslationRepository iTranslationRepository,
            IHistoryRepository iHistoryRepository,
            ISettingsRepository iSettingsRepository,
            Locale locale,
            ConnectivityUtils connectivityUtils) {
        this.iTranslationRepository = iTranslationRepository;
        this.iHistoryRepository = iHistoryRepository;
        this.iSettingsRepository = iSettingsRepository;
        this.locale = locale;
        this.connectivityUtils = connectivityUtils;
    }

    private static String getCurrentLanguage(Locale locale) {
        final String undefinedLocale = "und";

        if (locale == null || locale.getLanguage().equals(undefinedLocale))
            return DEFAULT_LANGUAGE_CODE;

        //язык может быть с указанием региона, нам нужен только язык
        String[] splitted = locale.getLanguage().split("-");
        if (splitted.length > 0)
            return splitted[0];

        return DEFAULT_LANGUAGE_CODE;
    }

    @Override
    public Single<List<Language>> getLanguages() {
        boolean isOnline = connectivityUtils.isOnline();

        if (isOnline) {
            //если есть интернет - грузим языки из api и сохраняем в настройки
            return
                    iTranslationRepository
                            .getLanguages(getCurrentLanguage(locale))
                            .map(this::sortLanguages)
                            .flatMap(languages ->
                                    saveToSettings(languages).toSingle(() -> languages));
        } else {
            //если нет - грузим из настроек

            return
                    iSettingsRepository
                            .getStringSet(LANGUAGES_SET_KEY)
                            .flatMap(this::mapToLanguages);
        }
    }

    @Override
    public Single<Translation> translateText(String text, String fromLanguage, String toLanguage) {
        String textToTranslate = text.trim();

        return
                ensureIsOnline()
                        .flatMap(x ->
                                Single.zip(
                                        iTranslationRepository.getTranslation(textToTranslate, fromLanguage, toLanguage),
                                        iHistoryRepository.isFavourite(textToTranslate, fromLanguage, toLanguage),
                                        (translation, isFavourite) -> {
                                            translation.setFavorite(isFavourite);
                                            return translation;
                                        }
                                ));
    }

    @Override
    public Single<Boolean> isFavorite(Translation translation) {
        return iHistoryRepository.
                isFavourite(translation.getText(), translation.getFromLanguage(), translation.getToLanguage());
    }

    @Override
    public Completable saveTranslation(Translation translation) {
        if (translation == null
                || translation.getText().equals(EMPTY_STRING)
                || translation.getTranslated().equals(EMPTY_STRING)
                || translation.getFromLanguage().equals(EMPTY_STRING)
                || translation.getToLanguage().equals(EMPTY_STRING))
            return Completable.complete();

        return iHistoryRepository.add(translation);
    }

    @Override
    public Completable saveTranslationDirection(String fromLanguage, String toLanguage) {
        if (fromLanguage.equals(EMPTY_STRING) || toLanguage.equals(EMPTY_STRING))
            return Completable.complete();

        return
                Completable.concatArray(
                        iSettingsRepository.putValue(FROM_LANGUAGE_KEY, fromLanguage),
                        iSettingsRepository.putValue(TO_LANGUAGE_KEY, toLanguage));
    }

    @Override
    public Single<List<String>> restoreTranslationDirection() {
        return
                Single.zip(
                        iSettingsRepository.getValue(FROM_LANGUAGE_KEY),
                        iSettingsRepository.getValue(TO_LANGUAGE_KEY),
                        (fromLanguage, toLanguage) ->
                                new ArrayList<String>() {{
                                    add(!fromLanguage.equals(EMPTY_STRING) ? fromLanguage : DEFAULT_FROM_LANGUAGE);
                                    add(!toLanguage.equals(EMPTY_STRING) ? toLanguage : DEFAULT_TO_LANGUAGE);
                                }}
                );
    }

    private Single<Boolean> ensureIsOnline() {
        return Single.fromCallable(() -> {
            if (connectivityUtils.isOnline())
                return true;

            throw new NoInternetConnectionException();
        });
    }

    private List<Language> sortLanguages(List<Language> languages) {
        Collections.sort(languages, (language1, language2) -> language1.getTitle().compareTo(language2.getTitle()));

        return languages;
    }

    //сохраняем в настройку языки при каждом запуске
    //чтобы без интернета можно было запуститься с заполненными языками
    //но есть проблема, если locale на телефоне изменится, то title будут некудышными
    private Completable saveToSettings(List<Language> languages) {
        HashSet<String> languageSet = new HashSet<>();
        for (Language language : languages) {
            languageSet.add(language.getTitle() + LANGUAGE_CODE_SEPARATOR + language.getLanguage());
        }

        return
                iSettingsRepository.putStringSet(LANGUAGES_SET_KEY, languageSet);
    }

    private Single<List<Language>> mapToLanguages(Set<String> languageSet) {
        if (languageSet.size() == 0)
            return Single.just(new ArrayList<>());

        List<Language> languages = new ArrayList<>();
        for (String str : languageSet) {
            String[] splitted = str.split(LANGUAGE_CODE_SEPARATOR);
            languages.add(new Language(splitted[0], splitted[1]));
        }

        return Single.just(languages);
    }
}
