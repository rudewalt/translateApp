package com.ivan.translateapp.domain.interactor;


import com.ivan.translateapp.data.repository.IHistoryRepository;
import com.ivan.translateapp.data.repository.ITranslationRepository;
import com.ivan.translateapp.domain.Language;
import com.ivan.translateapp.domain.Translation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;


import io.reactivex.Observable;

/**
 * Created by Ivan on 28.03.2017.
 */

public class MainInteractor implements IMainInteractor {

    private static final String EMPTY_STRING = "";
    private static final String FROM_LANGUAGE_KEY = "fromLanguage";
    private static final String TO_LANGUAGE_KEY = "toLanguage";

    private ITranslationRepository iTranslationRepository;
    private IHistoryRepository iHistoryRepository;
    private Locale locale;

    public MainInteractor(ITranslationRepository iTranslationRepository, IHistoryRepository iHistoryRepository, Locale locale) {
        this.iTranslationRepository = iTranslationRepository;
        this.iHistoryRepository = iHistoryRepository;
        this.locale = locale;
    }

    @Override
    public Observable<List<Language>> getLanguages() {

        return
                iTranslationRepository
                        .getLanguages(locale.getLanguage())
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
    public void saveTranslation(Translation translation) {
        if(translation == null
                || translation.getText().equals(EMPTY_STRING)
                || translation.getTranslated().equals(EMPTY_STRING)
                || translation.getFromLanguage().equals(EMPTY_STRING)
                || translation.getToLanguage().equals(EMPTY_STRING))
            return;

        iHistoryRepository.add(translation);
    }

    @Override
    public void saveTranslationDirection(String fromLanguage, String toLanguage) {
        if(fromLanguage.equals(EMPTY_STRING) || toLanguage.equals(EMPTY_STRING))
            return;

        iHistoryRepository.saveSetting(FROM_LANGUAGE_KEY, fromLanguage);
        iHistoryRepository.saveSetting(TO_LANGUAGE_KEY, toLanguage);
    }

    @Override
    public Observable<List<String>> restoreTranslationDirection(){
        return
        Observable.zip(
                iHistoryRepository.getSetting(FROM_LANGUAGE_KEY),
                iHistoryRepository.getSetting(TO_LANGUAGE_KEY),
                (fromLanguage, toLanguage)-> new ArrayList<String>(){{add(fromLanguage); add(toLanguage);}}
        );
    }

    private List<Language> sortLanguages(List<Language> languages) {
        Collections.sort(languages, (language1, language2) -> language1.getTitle().compareTo(language2.getTitle()));

        return languages;
    }
}
