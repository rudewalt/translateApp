package com.ivan.translateapp.domain.interactor;


import com.ivan.translateapp.data.repository.IHistoryRepository;
import com.ivan.translateapp.data.repository.ISettingsRepository;
import com.ivan.translateapp.data.repository.ITranslationRepository;
import com.ivan.translateapp.domain.Language;
import com.ivan.translateapp.domain.Translation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Created by Ivan on 28.03.2017.
 */

public class MainInteractor implements IMainInteractor {

    private static final String DEFAULT_LANGUAGE_CODE = "en";
    private static final String EMPTY_STRING = "";
    private static final String FROM_LANGUAGE_KEY = "fromLanguage";
    private static final String TO_LANGUAGE_KEY = "toLanguage";

    private ITranslationRepository iTranslationRepository;
    private IHistoryRepository iHistoryRepository;
    private ISettingsRepository iSettingsRepository;
    private Locale locale;

    public MainInteractor(
            ITranslationRepository iTranslationRepository,
            IHistoryRepository iHistoryRepository,
            ISettingsRepository iSettingsRepository,
            Locale locale) {
        this.iTranslationRepository = iTranslationRepository;
        this.iHistoryRepository = iHistoryRepository;
        this.iSettingsRepository = iSettingsRepository;
        this.locale = locale;
    }

    private static String getCurrentLanguage(Locale locale) {
        final String undefinedLocale = "und";

        if (locale == null || locale.getLanguage().equals(undefinedLocale))
            return DEFAULT_LANGUAGE_CODE;

        String[] splitted = locale.getLanguage().split("-");
        if (splitted.length > 0)
            return splitted[0];

        return DEFAULT_LANGUAGE_CODE;
    }

    @Override
    public Observable<List<Language>> getLanguages() {

        return
                iTranslationRepository
                        .getLanguages(getCurrentLanguage(locale))
                        .map(this::sortLanguages);
    }

    @Override
    public Observable<Translation> translateText(String text, String fromLanguage, String toLanguage) {
        String textToTranslate = text.trim();

        return
                Observable.combineLatest(
                        iTranslationRepository.getTranslation(textToTranslate, fromLanguage, toLanguage),
                        iHistoryRepository.isFavourite(textToTranslate, fromLanguage, toLanguage),
                        (translation, isFavourite) -> {
                            translation.setFavorite(isFavourite);
                            return translation;
                        }
                );
    }

    @Override
    public Observable<Boolean> isFavorite(Translation translation) {
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
                        iSettingsRepository.setValue(FROM_LANGUAGE_KEY, fromLanguage),
                        iSettingsRepository.setValue(TO_LANGUAGE_KEY, toLanguage)
                );
    }

    @Override
    public Observable<List<String>> restoreTranslationDirection() {
        return
                Observable.zip(
                        iSettingsRepository.getValue(FROM_LANGUAGE_KEY),
                        iSettingsRepository.getValue(TO_LANGUAGE_KEY),
                        (fromLanguage, toLanguage) -> {
                            if (fromLanguage.equals(EMPTY_STRING) || toLanguage.equals(EMPTY_STRING))
                                return new ArrayList<String>();

                            return new ArrayList<String>() {{
                                add(fromLanguage);
                                add(toLanguage);
                            }};
                        }
                );
    }

    private List<Language> sortLanguages(List<Language> languages) {
        Collections.sort(languages, (language1, language2) -> language1.getTitle().compareTo(language2.getTitle()));

        return languages;
    }
}
